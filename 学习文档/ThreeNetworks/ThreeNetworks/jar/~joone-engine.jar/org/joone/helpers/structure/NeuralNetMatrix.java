// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NeuralNetMatrix.java

package org.joone.helpers.structure;

import java.io.*;
import java.util.*;
import org.joone.engine.*;
import org.joone.net.NeuralNet;

// Referenced classes of package org.joone.helpers.structure:
//			Connection

public class NeuralNetMatrix
{

	private ArrayList layers;
	private ArrayList connectionSet;
	private Monitor monitor;
	private Layer inputLayer;
	private Layer outputLayer;
	private int inputLayerInd;
	private int outputLayerInd;
	transient Hashtable synTemp;
	int translation[];

	public NeuralNetMatrix()
	{
		inputLayer = null;
		outputLayer = null;
		inputLayerInd = -1;
		outputLayerInd = -1;
		translation = null;
	}

	public NeuralNetMatrix(NeuralNet net)
	{
		inputLayer = null;
		outputLayer = null;
		inputLayerInd = -1;
		outputLayerInd = -1;
		translation = null;
		setNeuralNet(net);
	}

	public void setNeuralNet(NeuralNet net)
	{
		int n = net.getLayers().size();
		inputLayer = net.findInputLayer();
		outputLayer = net.findOutputLayer();
		monitor = net.getMonitor();
		layers = new ArrayList(net.getLayers());
		synTemp = new Hashtable();
		for (int i = 0; i < n; i++)
		{
			Layer ly = (Layer)layers.get(i);
			checkInputs(i, ly);
			checkOutputs(i, ly);
		}

		Enumeration enumerat = synTemp.keys();
		connectionSet = new ArrayList();
		while (enumerat.hasMoreElements()) 
		{
			Object key = enumerat.nextElement();
			Connection tsyn = (Connection)synTemp.get(key);
			int x = tsyn.getInput();
			int y = tsyn.getOutput();
			if (x * y > 0)
				connectionSet.add(tsyn);
		}
	}

	public Synapse[][] getConnectionMatrix()
	{
		Synapse connectionMatrix[][] = new Synapse[layers.size()][layers.size()];
		for (int n = 0; n < connectionSet.size(); n++)
		{
			Connection tsyn = (Connection)connectionSet.get(n);
			int x = tsyn.getInput();
			int y = tsyn.getOutput();
			connectionMatrix[x - 1][y - 1] = tsyn.getSynapse();
		}

		return connectionMatrix;
	}

	public boolean isThereAnyPath(Layer fromLayer, Layer toLayer)
	{
		boolean retValue = false;
		int iFrom = getLayerInd(fromLayer);
		int iTo = getLayerInd(toLayer);
		retValue = isThereAnyPath(iFrom, iTo, getConnectionMatrix());
		return retValue;
	}

	private boolean isThereAnyPath(int iFrom, int iTo, Synapse matrix[][])
	{
		boolean retValue = false;
		for (int t = 0; t < layers.size() && !retValue; t++)
		{
			Synapse conn = matrix[iFrom][t];
			if (conn != null && !conn.isLoopBack())
				if (t == iTo)
					retValue = true;
				else
					retValue = isThereAnyPath(t, iTo, matrix);
		}

		return retValue;
	}

	public Synapse[][] getOrderedConnectionMatrix()
	{
		getOrderedLayers();
		Synapse connectionMatrix[][] = new Synapse[layers.size()][layers.size()];
		for (int n = 0; n < connectionSet.size(); n++)
		{
			Connection tsyn = (Connection)connectionSet.get(n);
			int x = tsyn.getInput();
			int y = tsyn.getOutput();
			connectionMatrix[translation[x - 1]][translation[y - 1]] = tsyn.getSynapse();
		}

		return connectionMatrix;
	}

	public boolean[][] getBinaryOrderedConnectionMatrix()
	{
		getOrderedLayers();
		boolean booleanConnectionMatrix[][] = new boolean[layers.size()][layers.size()];
		for (int n = 0; n < connectionSet.size(); n++)
		{
			Connection tsyn = (Connection)connectionSet.get(n);
			int x = tsyn.getInput();
			int y = tsyn.getOutput();
			booleanConnectionMatrix[translation[x - 1]][translation[y - 1]] = true;
		}

		return booleanConnectionMatrix;
	}

	public Layer[] getOrderedLayers()
	{
		Synapse connMatrix[][] = getConnectionMatrix();
		if (connMatrix == null)
			return null;
		int ord[] = new int[layers.size()];
		ArrayList inputLayers = getInputLayers(connMatrix);
		for (int i = 0; i < inputLayers.size(); i++)
		{
			int ind = ((Integer)inputLayers.get(i)).intValue();
			ord[ind] = 1;
		}

		for (boolean changed = assignOrderToLayers(ord, connMatrix); changed; changed = assignOrderToLayers(ord, connMatrix));
		translation = new int[layers.size()];
		Layer ordLayers[] = new Layer[layers.size()];
		int n = 1;
		for (int d = 0; d < layers.size();)
		{
			for (int x = 0; x < ord.length; x++)
				if (ord[x] == n)
				{
					ordLayers[d] = (Layer)layers.get(x);
					translation[x] = d;
					d++;
				}

			n++;
		}

		return ordLayers;
	}

	private boolean assignOrderToLayers(int ord[], Synapse connMatrix[][])
	{
		boolean changed = false;
		for (int x = 0; x < ord.length; x++)
		{
			int currLayer = ord[x];
			if (currLayer > 0)
			{
				for (int y = 0; y < connMatrix[x].length; y++)
					if (connMatrix[x][y] != null && !connMatrix[x][y].isLoopBack() && currLayer >= ord[y])
					{
						ord[y] = currLayer + 1;
						changed = true;
					}

			}
		}

		return changed;
	}

	public ArrayList getInputLayers(Synapse connMatrix[][])
	{
		ArrayList inputs = new ArrayList();
		for (int y = 0; y < connMatrix.length; y++)
		{
			boolean found = false;
			for (int x = 0; x < connMatrix[y].length; x++)
			{
				if (connMatrix[x][y] == null || connMatrix[x][y].isLoopBack())
					continue;
				found = true;
				break;
			}

			if (!found)
				inputs.add(new Integer(y));
		}

		return inputs;
	}

	public Serializable cloneElement(Serializable element)
	{
		Object theCLone;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = new ObjectOutputStream(bos);
		out.writeObject(element);
		out.close();
		byte buf[] = bos.toByteArray();
		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buf));
		theCLone = in.readObject();
		in.close();
		return (Serializable)theCLone;
		IOException e;
		e;
		e.printStackTrace();
		break MISSING_BLOCK_LABEL_85;
		e;
		e.printStackTrace();
		return null;
	}

	private void checkInputs(int n, Layer ly)
	{
		Vector inps = ly.getAllInputs();
		if (inps == null)
			return;
		for (int i = 0; i < inps.size(); i++)
		{
			InputPatternListener ipl = (InputPatternListener)inps.elementAt(i);
			if (ipl != null && (ipl instanceof Synapse))
			{
				Connection temp = getSynapse((Synapse)ipl);
				temp.setOutput(n + 1);
				temp.setOutIndex(i);
			}
		}

	}

	private void checkOutputs(int n, Layer ly)
	{
		Vector outs = ly.getAllOutputs();
		if (outs == null)
			return;
		for (int i = 0; i < outs.size(); i++)
		{
			OutputPatternListener opl = (OutputPatternListener)outs.elementAt(i);
			if (opl != null && (opl instanceof Synapse))
			{
				Connection temp = getSynapse((Synapse)opl);
				temp.setInput(n + 1);
				temp.setInpIndex(i);
			}
		}

	}

	private Connection getSynapse(Synapse s)
	{
		Connection temp = (Connection)synTemp.get(s);
		if (temp == null)
		{
			temp = new Connection();
			temp.setSynapse(s);
			synTemp.put(s, temp);
		}
		return temp;
	}

	public ArrayList getLayers()
	{
		return layers;
	}

	public void setLayers(ArrayList layers)
	{
		this.layers = layers;
	}

	public ArrayList getConnectionSet()
	{
		return connectionSet;
	}

	public void setConnectionSet(ArrayList connectionSet)
	{
		this.connectionSet = connectionSet;
	}

	public Monitor getMonitor()
	{
		return monitor;
	}

	public void setMonitor(Monitor monitor)
	{
		this.monitor = monitor;
	}

	public Layer getInputLayer()
	{
		return inputLayer;
	}

	public void setInputLayer(Layer inputLayer)
	{
		this.inputLayer = inputLayer;
	}

	public Layer getOutputLayer()
	{
		return outputLayer;
	}

	public void setOutputLayer(Layer outputLayer)
	{
		this.outputLayer = outputLayer;
	}

	public int getNumLayers()
	{
		return layers.size();
	}

	public int getInputLayerInd()
	{
		if (inputLayerInd == -1)
			inputLayerInd = getLayerInd(inputLayer);
		return inputLayerInd;
	}

	public int getOutputLayerInd()
	{
		if (outputLayerInd == -1)
			outputLayerInd = getLayerInd(outputLayer);
		return outputLayerInd;
	}

	public int getLayerInd(Layer layer)
	{
		int layerInd = -1;
		for (int i = 0; i < layers.size(); i++)
		{
			Layer ly = (Layer)layers.get(i);
			if (ly != layer)
				continue;
			layerInd = i;
			break;
		}

		return layerInd;
	}
}
