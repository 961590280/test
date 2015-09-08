// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   XORMemory.java

package org.joone.samples.engine.xor;

import java.io.PrintStream;
import org.joone.engine.*;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.io.MemoryInputSynapse;
import org.joone.net.NeuralNet;

public class XORMemory
	implements NeuralNetListener
{

	private double inputArray[][] = {
		{
			0.0D, 0.0D, 0.0D
		}, {
			0.0D, 1.0D, 1.0D
		}, {
			1.0D, 0.0D, 1.0D
		}, {
			1.0D, 1.0D, 0.0D
		}
	};
	private long mills;

	public XORMemory()
	{
	}

	public static void main(String args[])
	{
		XORMemory xor = new XORMemory();
		if (args.length == 0)
			xor.Go(10000);
		else
			xor.Go((new Integer(args[0])).intValue());
	}

	public void Go(int epochs)
	{
		LinearLayer input = new LinearLayer();
		SigmoidLayer hidden = new SigmoidLayer();
		SigmoidLayer output = new SigmoidLayer();
		input.setLayerName("input");
		hidden.setLayerName("hidden");
		output.setLayerName("output");
		input.setRows(2);
		hidden.setRows(3);
		output.setRows(1);
		FullSynapse synapse_IH = new FullSynapse();
		FullSynapse synapse_HO = new FullSynapse();
		synapse_IH.setName("IH");
		synapse_HO.setName("HO");
		input.addOutputSynapse(synapse_IH);
		hidden.addInputSynapse(synapse_IH);
		hidden.addOutputSynapse(synapse_HO);
		output.addInputSynapse(synapse_HO);
		MemoryInputSynapse inputStream = new MemoryInputSynapse();
		inputStream.setInputArray(inputArray);
		inputStream.setAdvancedColumnSelector("1,2");
		input.addInputSynapse(inputStream);
		TeachingSynapse trainer = new TeachingSynapse();
		MemoryInputSynapse samples = new MemoryInputSynapse();
		samples.setInputArray(inputArray);
		samples.setAdvancedColumnSelector("3");
		trainer.setDesired(samples);
		output.addOutputSynapse(trainer);
		NeuralNet nnet = new NeuralNet();
		nnet.addLayer(input, 0);
		nnet.addLayer(hidden, 1);
		nnet.addLayer(output, 2);
		Monitor monitor = nnet.getMonitor();
		monitor.setTrainingPatterns(4);
		monitor.setTotCicles(epochs);
		monitor.setLearningRate(0.80000000000000004D);
		monitor.setMomentum(0.5D);
		monitor.setLearning(true);
		monitor.setSingleThreadMode(true);
		monitor.addNeuralNetListener(this);
		mills = System.currentTimeMillis();
		nnet.go();
	}

	public void netStopped(NeuralNetEvent e)
	{
		long delay = System.currentTimeMillis() - mills;
		System.out.println((new StringBuilder("Training finished after ")).append(delay).append(" ms").toString());
		System.exit(0);
	}

	public void cicleTerminated(NeuralNetEvent neuralnetevent)
	{
	}

	public void netStarted(NeuralNetEvent e)
	{
		System.out.println("Training...");
	}

	public void errorChanged(NeuralNetEvent e)
	{
		Monitor mon = (Monitor)e.getSource();
		long c = mon.getCurrentCicle();
		long cl = c / 1000L;
		if (cl * 1000L == c)
			System.out.println((new StringBuilder(String.valueOf(c))).append(" cycles remaining - Error = ").append(mon.getGlobalError()).toString());
	}

	public void netStoppedError(NeuralNetEvent neuralnetevent, String s)
	{
	}
}
