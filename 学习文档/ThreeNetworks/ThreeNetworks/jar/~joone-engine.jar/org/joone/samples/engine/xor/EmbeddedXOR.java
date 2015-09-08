// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EmbeddedXOR.java

package org.joone.samples.engine.xor;

import java.io.*;
import org.joone.engine.Layer;
import org.joone.engine.Monitor;
import org.joone.io.MemoryInputSynapse;
import org.joone.io.MemoryOutputSynapse;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NeuralNet;
import org.joone.util.UnNormalizerOutputPlugIn;

public class EmbeddedXOR
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/samples/engine/xor/EmbeddedXOR);
	private double inputArray[][] = {
		{
			0.0D, 0.0D
		}, {
			0.0D, 1.0D
		}, {
			1.0D, 0.0D
		}, {
			1.0D, 1.0D
		}
	};
	private MemoryOutputSynapse memOut;
	private static String xorNet = "org/joone/samples/engine/xor/xor.snet";

	public EmbeddedXOR()
	{
	}

	public static void main(String args[])
	{
		EmbeddedXOR xor = new EmbeddedXOR();
		xor.Go(xorNet);
	}

	private void Go(String fileName)
	{
		NeuralNet xor = restoreNeuralNet(fileName);
		if (xor != null)
		{
			Layer input = xor.getInputLayer();
			input.removeAllInputs();
			MemoryInputSynapse memInp = new MemoryInputSynapse();
			memInp.setFirstRow(1);
			memInp.setAdvancedColumnSelector("1,2");
			input.addInputSynapse(memInp);
			memInp.setInputArray(inputArray);
			Layer output = xor.getOutputLayer();
			output.removeAllOutputs();
			memOut = new MemoryOutputSynapse();
			UnNormalizerOutputPlugIn outPlugin = new UnNormalizerOutputPlugIn();
			outPlugin.setAdvancedSerieSelector("1");
			outPlugin.setOutDataMin(1.0D);
			outPlugin.setOutDataMax(2D);
			memOut.addPlugIn(outPlugin);
			output.addOutputSynapse(memOut);
			xor.getMonitor().setTotCicles(1);
			xor.getMonitor().setTrainingPatterns(4);
			xor.getMonitor().setLearning(false);
			interrogate(xor, 10);
			log.info("Finished");
		}
	}

	private void interrogate(NeuralNet net, int times)
	{
		int cc = net.getMonitor().getTrainingPatterns();
		for (int t = 0; t < times; t++)
		{
			log.info((new StringBuilder("Launch #")).append(t + 1).toString());
			net.go();
			for (int i = 0; i < cc; i++)
			{
				double pattern[] = memOut.getNextPattern();
				log.info((new StringBuilder("    Output Pattern #")).append(i + 1).append(" = ").append(pattern[0]).toString());
			}

			net.stop();
		}

	}

	private NeuralNet restoreNeuralNet(String fileName)
	{
		NeuralNet nnet = null;
		try
		{
			FileInputStream stream = new FileInputStream(fileName);
			ObjectInput input = new ObjectInputStream(stream);
			nnet = (NeuralNet)input.readObject();
		}
		catch (Exception e)
		{
			log.warn((new StringBuilder("Exception was thrown. Message is : ")).append(e.getMessage()).toString(), e);
		}
		return nnet;
	}

}
