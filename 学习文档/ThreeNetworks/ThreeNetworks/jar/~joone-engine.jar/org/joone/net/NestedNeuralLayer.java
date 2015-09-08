// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NestedNeuralLayer.java

package org.joone.net;

import java.io.*;
import java.util.*;
import org.joone.engine.*;
import org.joone.io.StreamInputSynapse;
import org.joone.io.StreamOutputSynapse;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.net:
//			NeuralNet, NetCheck

public class NestedNeuralLayer extends Layer
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/net/NestedNeuralLayer);
	static final long serialVersionUID = 0xccb08718797804ddL;
	private String sNeuralNet;
	private NeuralNet NestedNeuralNet;
	private LinearLayer lin;
	private transient File embeddedNet;

	public NestedNeuralLayer()
	{
		this("");
	}

	public NestedNeuralLayer(String elemName)
	{
		embeddedNet = null;
		NestedNeuralNet = new NeuralNet();
		lin = new LinearLayer();
		lin.setLayerName("Nested LinearLayer");
		NestedNeuralNet.addLayer(lin, 0);
		sNeuralNet = "";
		setLayerNameInternal(elemName);
	}

	protected void setDimensions()
	{
	}

	protected void forward(double ad[])
	{
	}

	protected void backward(double ad[])
	{
	}

	public String getNeuralNet()
	{
		return sNeuralNet;
	}

	public void setNeuralNet(String NNFile)
	{
		sNeuralNet = NNFile;
		try
		{
			NeuralNet newNeuralNet = readNeuralNet();
			if (newNeuralNet != null)
				setNestedNeuralNet(newNeuralNet);
		}
		catch (Exception e)
		{
			log.warn((new StringBuilder("Exception thrown. Message is : ")).append(e.getMessage()).toString(), e);
		}
	}

	public void start()
	{
		NestedNeuralNet.start();
	}

	public void stop()
	{
		NestedNeuralNet.stop();
	}

	public int getRows()
	{
		return NestedNeuralNet.getRows();
	}

	public void setRows(int p1)
	{
		NestedNeuralNet.setRows(p1);
	}

	public void addNoise(double p1)
	{
		if (isLearning())
			NestedNeuralNet.addNoise(p1);
	}

	public void randomize(double amplitude)
	{
		if (isLearning())
			NestedNeuralNet.randomize(amplitude);
	}

	public Matrix getBias()
	{
		return NestedNeuralNet.getBias();
	}

	public Vector getAllOutputs()
	{
		return NestedNeuralNet.getAllOutputs();
	}

	public String getLayerName()
	{
		return NestedNeuralNet.getLayerName();
	}

	public void removeOutputSynapse(OutputPatternListener p1)
	{
		NestedNeuralNet.removeOutputSynapse(p1);
	}

	public void setAllInputs(Vector p1)
	{
		NestedNeuralNet.setAllInputs(p1);
	}

	public void removeAllOutputs()
	{
		NestedNeuralNet.removeAllOutputs();
	}

	public Vector getAllInputs()
	{
		return NestedNeuralNet.getAllInputs();
	}

	public boolean addOutputSynapse(OutputPatternListener p1)
	{
		return NestedNeuralNet.addOutputSynapse(p1);
	}

	public void setBias(Matrix p1)
	{
		NestedNeuralNet.setBias(p1);
	}

	public void removeInputSynapse(InputPatternListener p1)
	{
		NestedNeuralNet.removeInputSynapse(p1);
	}

	public void setLayerName(String p1)
	{
		setLayerNameInternal(p1);
	}

	private void setLayerNameInternal(String p1)
	{
		NestedNeuralNet.setLayerName(p1);
	}

	public boolean addInputSynapse(InputPatternListener p1)
	{
		return NestedNeuralNet.addInputSynapse(p1);
	}

	public void setAllOutputs(Vector p1)
	{
		NestedNeuralNet.setAllOutputs(p1);
	}

	public void setMonitor(Monitor p1)
	{
		getMonitor().setParent(p1);
	}

	public Monitor getMonitor()
	{
		return NestedNeuralNet.getMonitor();
	}

	public void removeAllInputs()
	{
		NestedNeuralNet.removeAllInputs();
	}

	public NeuralLayer copyInto(NeuralLayer p1)
	{
		return NestedNeuralNet.copyInto(p1);
	}

	private NeuralNet readNeuralNet()
		throws IOException, ClassNotFoundException
	{
		if (sNeuralNet == null)
			return null;
		if (sNeuralNet.equals(""))
		{
			return null;
		} else
		{
			File NNFile = new File(sNeuralNet);
			FileInputStream fin = new FileInputStream(NNFile);
			ObjectInputStream oin = new ObjectInputStream(fin);
			NeuralNet newNeuralNet = (NeuralNet)oin.readObject();
			oin.close();
			fin.close();
			return newNeuralNet;
		}
	}

	public boolean isRunning()
	{
		if (NestedNeuralNet == null)
			return false;
		else
			return NestedNeuralNet.isRunning();
	}

	public NeuralNet getNestedNeuralNet()
	{
		return NestedNeuralNet;
	}

	public void setNestedNeuralNet(NeuralNet newNeuralNet)
	{
		newNeuralNet.removeAllListeners();
		newNeuralNet.setLayerName(NestedNeuralNet.getLayerName());
		newNeuralNet.setTeacher(null);
		newNeuralNet.setAllInputs(NestedNeuralNet.getAllInputs());
		newNeuralNet.setAllOutputs(NestedNeuralNet.getAllOutputs());
		Monitor extMonitor = getMonitor();
		lin = null;
		NestedNeuralNet = newNeuralNet;
		NestedNeuralNet.setMonitor(new Monitor());
		setMonitor(extMonitor);
	}

	public boolean isLearning()
	{
		return NestedNeuralNet.getMonitor().isLearning();
	}

	public void setLearning(boolean learning)
	{
		NestedNeuralNet.getMonitor().setLearning(learning);
	}

	public TreeSet check()
	{
		return setErrorSource(NestedNeuralNet.check());
	}

	public File getEmbeddedNet()
	{
		return embeddedNet;
	}

	public void setEmbeddedNet(File embeddedNet)
	{
		if (embeddedNet != null)
		{
			if (!sNeuralNet.equals(embeddedNet.getAbsolutePath()))
			{
				this.embeddedNet = embeddedNet;
				setNeuralNet(embeddedNet.getAbsolutePath());
			}
		} else
		{
			this.embeddedNet = null;
			sNeuralNet = "";
		}
	}

	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		embeddedNet = new File(sNeuralNet);
	}

	private TreeSet setErrorSource(TreeSet errors)
	{
		if (!errors.isEmpty())
		{
			for (Iterator iter = errors.iterator(); iter.hasNext();)
			{
				NetCheck nc = (NetCheck)iter.next();
				if (!(nc.getSource() instanceof Monitor) && !(nc.getSource() instanceof StreamInputSynapse) && !(nc.getSource() instanceof StreamOutputSynapse))
					nc.setSource(this);
			}

		}
		return errors;
	}

	public void fwdRun(Pattern pattIn)
	{
		NestedNeuralNet.singleStepForward(pattIn);
	}

	public void revRun(Pattern pattIn)
	{
		NestedNeuralNet.singleStepBackward(pattIn);
	}

}
