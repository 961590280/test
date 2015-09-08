// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JooneTools.java

package org.joone.helpers.factory;

import java.io.*;
import java.util.*;
import org.joone.engine.*;
import org.joone.engine.learning.ComparingSynapse;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.engine.listeners.ErrorBasedTerminator;
import org.joone.io.*;
import org.joone.net.*;

// Referenced classes of package org.joone.helpers.factory:
//			ImmediateDirectSynapse

public class JooneTools
{

	public static final int LINEAR = 1;
	public static final int LOGISTIC = 2;
	public static final int SOFTMAX = 3;
	public static final int WTA = 4;
	public static final int GAUSSIAN = 5;
	public static final int BPROP_ONLINE = 0;
	public static final int BPROP_BATCH = 1;
	public static final int RPROP = 2;
	public static final String inputAdded = "inputAdded";
	public static final String targetAdded = "inputAdded";

	public JooneTools()
	{
	}

	public static NeuralNet create_standard(int nodes[], int outputType)
		throws IllegalArgumentException
	{
		NeuralNet nnet = new NeuralNet();
		if (nodes == null || nodes.length < 2)
			throw new IllegalArgumentException("create_standard: Nodes is empty");
		Layer layers[] = new Layer[nodes.length];
		layers[0] = new LinearLayer();
		layers[0].setRows(nodes[0]);
		layers[0].setLayerName("input");
		nnet.addLayer(layers[0], 0);
		if (nodes.length > 2)
		{
			for (int i = 1; i < nodes.length - 1; i++)
			{
				layers[i] = new SigmoidLayer();
				layers[i].setRows(nodes[i]);
				layers[i].setLayerName((new StringBuilder("hidden")).append(i).toString());
				nnet.addLayer(layers[i], 1);
			}

		}
		int outp = nodes.length - 1;
		switch (outputType)
		{
		case 1: // '\001'
			layers[outp] = new LinearLayer();
			break;

		case 2: // '\002'
			layers[outp] = new SigmoidLayer();
			break;

		case 3: // '\003'
			layers[outp] = new SoftmaxLayer();
			break;

		default:
			throw new IllegalArgumentException("create_standard: output type not supported");
		}
		layers[outp].setRows(nodes[outp]);
		layers[outp].setLayerName("output");
		nnet.addLayer(layers[outp], 2);
		for (int i = 0; i < layers.length - 1; i++)
			connect(layers[i], new FullSynapse(), layers[i + 1]);

		Monitor mon = nnet.getMonitor();
		mon.addLearner(0, "org.joone.engine.BasicLearner");
		mon.addLearner(1, "org.joone.engine.BatchLearner");
		mon.addLearner(2, "org.joone.engine.RpropLearner");
		mon.setLearningRate(0.69999999999999996D);
		mon.setMomentum(0.69999999999999996D);
		return nnet;
	}

	public static NeuralNet create_timeDelay(int nodes[], int taps, int outputType)
		throws IllegalArgumentException
	{
		NeuralNet nnet = new NeuralNet();
		if (nodes == null || nodes.length < 2)
			throw new IllegalArgumentException("create_standard: nodes: not enough elements");
		Layer layers[] = new Layer[nodes.length];
		layers[0] = new DelayLayer();
		layers[0].setRows(nodes[0]);
		((DelayLayer)layers[0]).setTaps(taps);
		layers[0].setLayerName("input");
		nnet.addLayer(layers[0], 0);
		if (nodes.length > 2)
		{
			for (int i = 1; i < nodes.length - 1; i++)
			{
				layers[i] = new SigmoidLayer();
				layers[i].setRows(nodes[i]);
				layers[i].setLayerName((new StringBuilder("hidden")).append(i).toString());
				nnet.addLayer(layers[i], 1);
			}

		}
		int outp = nodes.length - 1;
		switch (outputType)
		{
		case 1: // '\001'
			layers[outp] = new LinearLayer();
			break;

		case 2: // '\002'
			layers[outp] = new SigmoidLayer();
			break;

		case 3: // '\003'
			layers[outp] = new SoftmaxLayer();
			break;

		default:
			throw new IllegalArgumentException("create_standard: output type not supported");
		}
		layers[outp].setRows(nodes[outp]);
		layers[outp].setLayerName("output");
		nnet.addLayer(layers[outp], 2);
		for (int i = 0; i < layers.length - 1; i++)
			connect(layers[i], new FullSynapse(), layers[i + 1]);

		Monitor mon = nnet.getMonitor();
		mon.addLearner(0, "org.joone.engine.BasicLearner");
		mon.addLearner(1, "org.joone.engine.BatchLearner");
		mon.addLearner(2, "org.joone.engine.RpropLearner");
		mon.setLearningRate(0.69999999999999996D);
		mon.setMomentum(0.69999999999999996D);
		return nnet;
	}

	public static NeuralNet create_unsupervised(int nodes[], int outputType)
		throws IllegalArgumentException
	{
		NeuralNet nnet = new NeuralNet();
		if (nodes == null || nodes.length < 3)
			throw new IllegalArgumentException("create_unsupervised: nodes: not enough elements");
		Layer layers[] = new Layer[2];
		layers[0] = new LinearLayer();
		layers[0].setRows(nodes[0]);
		layers[0].setLayerName("input");
		nnet.addLayer(layers[0], 0);
		switch (outputType)
		{
		case 4: // '\004'
			layers[1] = new WTALayer();
			((WTALayer)layers[1]).setLayerWidth(nodes[1]);
			((WTALayer)layers[1]).setLayerHeight(nodes[2]);
			break;

		case 5: // '\005'
			layers[1] = new GaussianLayer();
			((GaussianLayer)layers[1]).setLayerWidth(nodes[1]);
			((GaussianLayer)layers[1]).setLayerHeight(nodes[2]);
			break;

		default:
			throw new IllegalArgumentException("create_unsupervised: output type not supported");
		}
		layers[1].setLayerName("output");
		nnet.addLayer(layers[1], 2);
		connect(layers[0], new KohonenSynapse(), layers[1]);
		Monitor mon = nnet.getMonitor();
		mon.setLearningRate(0.69999999999999996D);
		return nnet;
	}

	public static double[] interrogate(NeuralNet nnet, double input[])
	{
		nnet.removeAllInputs();
		nnet.removeAllOutputs();
		DirectSynapse inputSynapse = new DirectSynapse();
		ImmediateDirectSynapse outputSynapse = new ImmediateDirectSynapse();
		nnet.addInputSynapse(inputSynapse);
		nnet.addOutputSynapse(outputSynapse);
		Pattern inputPattern = new Pattern(input);
		inputPattern.setCount(1);
		nnet.getMonitor().setLearning(false);
		boolean stm = nnet.getMonitor().isSingleThreadMode();
		nnet.singleStepForward(inputPattern);
		double result[] = outputSynapse.getResult();
		nnet.getMonitor().setSingleThreadMode(stm);
		return result;
	}

	public static double train(NeuralNet nnet, double input[][], double desired[][], int epochs, double stopRMSE, int epochs_btw_reports, Object stdOut, 
			boolean async)
	{
		MemoryInputSynapse memInput = new MemoryInputSynapse();
		memInput.setInputArray(input);
		memInput.setAdvancedColumnSelector((new StringBuilder("1-")).append(input[0].length).toString());
		MemoryInputSynapse memTarget = null;
		if (desired != null)
		{
			memTarget = new MemoryInputSynapse();
			memTarget.setInputArray(desired);
			memTarget.setAdvancedColumnSelector((new StringBuilder("1-")).append(desired[0].length).toString());
		}
		Monitor mon = nnet.getMonitor();
		if (mon.isValidation())
		{
			if (mon.getValidationPatterns() == 0)
				mon.setValidationPatterns(input.length);
		} else
		if (mon.getTrainingPatterns() == 0)
			mon.setTrainingPatterns(input.length);
		return train_on_stream(nnet, memInput, memTarget, epochs, stopRMSE, epochs_btw_reports, stdOut, async);
	}

	public static void train_unsupervised(NeuralNet nnet, double input[][], int epochs, int epochs_btw_reports, Object stdOut, boolean async)
	{
		nnet.getMonitor().setSupervised(false);
		train(nnet, input, null, epochs, 0.0D, epochs_btw_reports, stdOut, async);
	}

	public static double train_on_stream(NeuralNet nnet, StreamInputSynapse input, StreamInputSynapse desired, int epochs, double stopRMSE, int epochs_btw_reports, Object stdOut, 
			boolean async)
	{
		nnet.removeAllInputs();
		nnet.removeAllOutputs();
		nnet.addInputSynapse(input);
		if (desired != null)
		{
			TeachingSynapse teacher = new TeachingSynapse();
			teacher.setDesired(desired);
			nnet.addOutputSynapse(teacher);
			nnet.setTeacher(teacher);
		}
		return train_complete(nnet, epochs, stopRMSE, epochs_btw_reports, stdOut, async);
	}

	public static double train_complete(NeuralNet nnet, int epochs, double stopRMSE, int epochs_btw_reports, Object stdOut, boolean async)
	{
		nnet.removeAllListeners();
		Monitor mon = nnet.getMonitor();
		if (stdOut != null)
			mon.addNeuralNetListener(createListener(nnet, stdOut, epochs_btw_reports));
		ErrorBasedTerminator term = null;
		if (stopRMSE > 0.0D)
		{
			term = new ErrorBasedTerminator(stopRMSE);
			term.setNeuralNet(nnet);
			mon.addNeuralNetListener(term);
		}
		mon.setTotCicles(epochs);
		mon.setLearning(!mon.isValidation());
		TreeSet tree = nnet.check();
		if (tree.isEmpty() || !errorsFound(tree))
		{
			nnet.go(!async);
			if (async)
				return 0.0D;
			NeuralNetAttributes attrib = nnet.getDescriptor();
			if (term != null)
				if (term.isStopRequestPerformed())
					attrib.setLastEpoch(term.getStoppedCycle());
				else
					attrib.setLastEpoch(mon.getTotCicles());
			if (mon.isValidation())
				attrib.setValidationError(mon.getGlobalError());
			else
				attrib.setTrainingError(mon.getGlobalError());
			return mon.getGlobalError();
		} else
		{
			throw new IllegalArgumentException((new StringBuilder("Cannot start, errors found:")).append(tree.toString()).toString());
		}
	}

	protected static boolean errorsFound(TreeSet tree)
	{
		boolean error = false;
		for (Iterator elements = tree.iterator(); elements.hasNext();)
		{
			NetCheck check = (NetCheck)elements.next();
			if (check.isFatal())
			{
				error = true;
				break;
			}
		}

		return error;
	}

	public static double test(NeuralNet nnet, double input[][], double desired[][])
	{
		nnet.getMonitor().setValidation(true);
		return train(nnet, input, desired, 1, 0.0D, 0, null, false);
	}

	public static double test_on_stream(NeuralNet nnet, StreamInputSynapse input, StreamInputSynapse desired)
	{
		nnet.getMonitor().setValidation(true);
		return train_on_stream(nnet, input, desired, 1, 0.0D, 0, null, false);
	}

	public static double[][] compare(NeuralNet nnet, double input[][], double desired[][])
	{
		MemoryInputSynapse memInput = new MemoryInputSynapse();
		memInput.setInputArray(input);
		memInput.setAdvancedColumnSelector((new StringBuilder("1-")).append(input[0].length).toString());
		MemoryInputSynapse memTarget = null;
		if (desired != null)
		{
			memTarget = new MemoryInputSynapse();
			memTarget.setInputArray(desired);
			memTarget.setAdvancedColumnSelector((new StringBuilder("1-")).append(desired[0].length).toString());
		}
		Monitor mon = nnet.getMonitor();
		nnet.getMonitor().setValidation(true);
		if (mon.getValidationPatterns() == 0)
			mon.setValidationPatterns(input.length);
		return compare_on_stream(nnet, memInput, memTarget);
	}

	public static double[][] compare_on_stream(NeuralNet nnet, StreamInputSynapse input, StreamInputSynapse desired)
	{
		nnet.removeAllInputs();
		nnet.removeAllOutputs();
		nnet.addInputSynapse(input);
		ComparingSynapse teacher = new ComparingSynapse();
		teacher.setDesired(desired);
		nnet.addOutputSynapse(teacher);
		MemoryOutputSynapse outStream = new MemoryOutputSynapse();
		teacher.addResultSynapse(outStream);
		train_complete(nnet, 1, 0.0D, 0, null, false);
		Vector results = outStream.getAllPatterns();
		int rows = results.size();
		int columns = ((Pattern)results.get(0)).getArray().length;
		double output[][] = new double[rows][columns];
		for (int i = 0; i < rows; i++)
			output[i] = ((Pattern)results.get(i)).getArray();

		return output;
	}

	public static double[][] getDataFromStream(StreamInputSynapse dataSet, int firstRow, int lastRow, int firstCol, int lastCol)
	{
		dataSet.Inspections();
		Vector data = dataSet.getInputPatterns();
		int rows = (lastRow - firstRow) + 1;
		int columns = (lastCol - firstCol) + 1;
		double array[][] = new double[rows][columns];
		for (int r = 0; r < rows; r++)
		{
			double temp[] = ((Pattern)data.get((r + firstRow) - 1)).getArray();
			for (int c = 0; c < columns; c++)
				array[r][c] = temp[(c + firstCol) - 1];

		}

		return array;
	}

	public static void save(NeuralNet nnet, String fileName)
		throws FileNotFoundException, IOException
	{
		FileOutputStream stream = new FileOutputStream(fileName);
		save_toStream(nnet, stream);
	}

	public static void save(NeuralNet nnet, File fileName)
		throws FileNotFoundException, IOException
	{
		FileOutputStream stream = new FileOutputStream(fileName);
		save_toStream(nnet, stream);
	}

	public static void save_toStream(NeuralNet nnet, OutputStream stream)
		throws IOException
	{
		ObjectOutput output = new ObjectOutputStream(stream);
		output.writeObject(nnet);
		output.close();
	}

	public static NeuralNet load(String fileName)
		throws FileNotFoundException, IOException, ClassNotFoundException
	{
		File NNFile = new File(fileName);
		FileInputStream fin = new FileInputStream(NNFile);
		NeuralNet nnet = load_fromStream(fin);
		fin.close();
		return nnet;
	}

	public static NeuralNet load_fromStream(InputStream stream)
		throws IOException, ClassNotFoundException
	{
		ObjectInputStream oin = new ObjectInputStream(stream);
		NeuralNet nnet = (NeuralNet)oin.readObject();
		oin.close();
		return nnet;
	}

	public static void completeNetwork(NeuralNet nnet, String sInput, String sTarget)
	{
		nnet.setParam("inputAdded", null);
		nnet.setParam("inputAdded", null);
		Layer inputLayer = nnet.getInputLayer();
		if (inputLayer == null)
			return;
		Vector inputs = inputLayer.getAllInputs();
		boolean found = false;
		if (inputs != null)
		{
			for (int i = 0; i < inputs.size(); i++)
			{
				if (!(inputs.elementAt(i) instanceof InputPatternListener))
					continue;
				found = true;
				break;
			}

		}
		if (!found)
		{
			InputPatternListener inputData = (InputPatternListener)nnet.getParam(sInput);
			if (inputData != null)
			{
				nnet.addInputSynapse(inputData);
				nnet.setParam("inputAdded", new Boolean(true));
			}
		}
		if (!nnet.getMonitor().isSupervised())
			return;
		TeachingSynapse teacher = (TeachingSynapse)nnet.getTeacher();
		if (teacher == null)
		{
			teacher = new TeachingSynapse();
			teacher.setName("Teacher");
			nnet.setTeacher(teacher);
			nnet.getOutputLayer().addOutputSynapse(teacher);
		}
		if (teacher.getDesired() == null)
		{
			StreamInputSynapse targetData = (StreamInputSynapse)nnet.getParam(sTarget);
			if (targetData != null)
			{
				teacher.setDesired(targetData);
				nnet.setParam("inputAdded", new Boolean(true));
			}
		}
	}

	protected static void connect(Layer l1, Synapse syn, Layer l2)
	{
		l1.addOutputSynapse(syn);
		l2.addInputSynapse(syn);
	}

	protected static Pattern stopPattern(int size)
	{
		Pattern stop = Pattern.makeStopPattern(size);
		return stop;
	}

	protected static NeuralNetListener createListener(NeuralNet nnet, final Object stdOut, int interval)
	{
		NeuralNetListener listener = new NeuralNetListener(interval, nnet) {

			Object output;
			int interv;
			NeuralNet neuralNet;
			private final int val$interval;

			public void netStarted(NeuralNetEvent e)
			{
				if (output == null)
					return;
				if (output instanceof PrintStream)
					((PrintStream)output).println("Network started");
				else
				if (output instanceof NeuralNetListener)
				{
					e.setNeuralNet(neuralNet);
					((NeuralNetListener)output).netStarted(e);
				}
			}

			public void cicleTerminated(NeuralNetEvent e)
			{
				if (output == null)
					return;
				Monitor mon = (Monitor)e.getSource();
				int epoch = mon.getCurrentCicle() - 1;
				if (interval == 0 || epoch % interval > 0)
					return;
				if (output instanceof PrintStream)
				{
					((PrintStream)output).print((new StringBuilder("Epoch n.")).append(mon.getTotCicles() - epoch).append(" terminated").toString());
					if (mon.isSupervised())
						((PrintStream)output).print((new StringBuilder(" - rmse: ")).append(mon.getGlobalError()).toString());
					((PrintStream)output).println("");
				} else
				if (output instanceof NeuralNetListener)
				{
					e.setNeuralNet(neuralNet);
					((NeuralNetListener)output).cicleTerminated(e);
				}
			}

			public void errorChanged(NeuralNetEvent e)
			{
				if (output == null)
					return;
				Monitor mon = (Monitor)e.getSource();
				int epoch = mon.getCurrentCicle() - 1;
				if (interval == 0 || epoch % interval > 0)
					return;
				if (output instanceof NeuralNetListener)
				{
					e.setNeuralNet(neuralNet);
					((NeuralNetListener)output).errorChanged(e);
				}
			}

			public void netStopped(NeuralNetEvent e)
			{
				if (output == null)
					return;
				if (output instanceof PrintStream)
					((PrintStream)output).println("Network stopped");
				else
				if (output instanceof NeuralNetListener)
				{
					e.setNeuralNet(neuralNet);
					((NeuralNetListener)output).netStopped(e);
				}
			}

			public void netStoppedError(NeuralNetEvent e, String error)
			{
				if (output == null)
					return;
				if (output instanceof PrintStream)
					((PrintStream)output).println((new StringBuilder("Network stopped with error:")).append(error).toString());
				else
				if (output instanceof NeuralNetListener)
				{
					e.setNeuralNet(neuralNet);
					((NeuralNetListener)output).netStoppedError(e, error);
				}
			}

			
			{
				interval = i;
				super();
				output = final_obj;
				interv = i;
				neuralNet = neuralnet;
			}
		};
		return listener;
	}
}
