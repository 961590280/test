// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ImmediateEmbeddedXOR.java

package org.joone.samples.engine.xor;

import java.io.*;
import org.joone.engine.*;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NeuralNet;

public class ImmediateEmbeddedXOR
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/samples/engine/xor/ImmediateEmbeddedXOR);
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
	private static String xorNet = "org/joone/samples/engine/xor/xor.snet";

	public ImmediateEmbeddedXOR()
	{
	}

	public static void main(String args[])
	{
		ImmediateEmbeddedXOR xor = new ImmediateEmbeddedXOR();
		xor.Go(xorNet);
	}

	private void Go(String fileName)
	{
		NeuralNet xor = restoreNeuralNet(fileName);
		if (xor != null)
		{
			Layer input = xor.getInputLayer();
			input.removeAllInputs();
			Layer output = xor.getOutputLayer();
			output.removeAllOutputs();
			DirectSynapse memOut = new DirectSynapse();
			output.addOutputSynapse(memOut);
			xor.getMonitor().setLearning(false);
			for (int n = 0; n < 100; n++)
			{
				log.debug((new StringBuilder("Launch #")).append(n).toString());
				for (int i = 0; i < 4; i++)
				{
					Pattern iPattern = new Pattern(inputArray[i]);
					iPattern.setCount(i + 1);
					xor.singleStepForward(iPattern);
					Pattern pattern = memOut.fwdGet();
					log.debug((new StringBuilder("Output Pattern #")).append(i + 1).append(" = ").append(pattern.getArray()[0]).toString());
				}

			}

			log.debug("Finished");
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
			log.warn((new StringBuilder("Exception thrown while restoring the Neural Net. Message is : ")).append(e.getMessage()).toString(), e);
		}
		return nnet;
	}

}
