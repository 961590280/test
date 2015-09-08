// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   XOR_using_NeuralNet.java

package org.joone.samples.engine.xor;

import java.io.PrintStream;
import org.joone.engine.*;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.io.*;
import org.joone.net.NeuralNet;

public class XOR_using_NeuralNet
	implements NeuralNetListener
{

	private NeuralNet nnet;
	private MemoryInputSynapse inputSynapse;
	private MemoryInputSynapse desiredOutputSynapse;
	private MemoryOutputSynapse outputSynapse;
	LinearLayer input;
	SigmoidLayer hidden;
	SigmoidLayer output;
	boolean singleThreadMode;
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
	private double desiredOutputArray[][] = {
		{
			0.0D
		}, {
			1.0D
		}, {
			1.0D
		}, {
			0.0D
		}
	};

	public XOR_using_NeuralNet()
	{
		nnet = null;
		singleThreadMode = true;
	}

	public static void main(String args[])
	{
		XOR_using_NeuralNet xor = new XOR_using_NeuralNet();
		xor.initNeuralNet();
		xor.train();
		xor.interrogate();
	}

	public void train()
	{
		inputSynapse.setInputArray(inputArray);
		inputSynapse.setAdvancedColumnSelector("1,2");
		desiredOutputSynapse.setInputArray(desiredOutputArray);
		desiredOutputSynapse.setAdvancedColumnSelector("1");
		Monitor monitor = nnet.getMonitor();
		monitor.setLearningRate(0.80000000000000004D);
		monitor.setMomentum(0.29999999999999999D);
		monitor.setTrainingPatterns(inputArray.length);
		monitor.setTotCicles(5000);
		monitor.setLearning(true);
		long initms = System.currentTimeMillis();
		nnet.getMonitor().setSingleThreadMode(singleThreadMode);
		nnet.go(true);
		System.out.println((new StringBuilder("Total time= ")).append(System.currentTimeMillis() - initms).append(" ms").toString());
	}

	private void interrogate()
	{
		inputSynapse.setInputArray(inputArray);
		inputSynapse.setAdvancedColumnSelector("1,2");
		Monitor monitor = nnet.getMonitor();
		monitor.setTrainingPatterns(4);
		monitor.setTotCicles(1);
		monitor.setLearning(false);
		FileOutputSynapse foutput = new FileOutputSynapse();
		foutput.setFileName("/tmp/xorOut.txt");
		if (nnet != null)
		{
			nnet.addOutputSynapse(foutput);
			System.out.println(nnet.check());
			nnet.getMonitor().setSingleThreadMode(singleThreadMode);
			nnet.go();
		}
	}

	protected void initNeuralNet()
	{
		input = new LinearLayer();
		hidden = new SigmoidLayer();
		output = new SigmoidLayer();
		input.setRows(2);
		hidden.setRows(3);
		output.setRows(1);
		input.setLayerName("L.input");
		hidden.setLayerName("L.hidden");
		output.setLayerName("L.output");
		FullSynapse synapse_IH = new FullSynapse();
		FullSynapse synapse_HO = new FullSynapse();
		input.addOutputSynapse(synapse_IH);
		hidden.addInputSynapse(synapse_IH);
		hidden.addOutputSynapse(synapse_HO);
		output.addInputSynapse(synapse_HO);
		inputSynapse = new MemoryInputSynapse();
		input.addInputSynapse(inputSynapse);
		desiredOutputSynapse = new MemoryInputSynapse();
		TeachingSynapse trainer = new TeachingSynapse();
		trainer.setDesired(desiredOutputSynapse);
		nnet = new NeuralNet();
		nnet.addLayer(input, 0);
		nnet.addLayer(hidden, 1);
		nnet.addLayer(output, 2);
		nnet.setTeacher(trainer);
		output.addOutputSynapse(trainer);
		nnet.addNeuralNetListener(this);
	}

	public void cicleTerminated(NeuralNetEvent neuralnetevent)
	{
	}

	public void errorChanged(NeuralNetEvent e)
	{
		Monitor mon = (Monitor)e.getSource();
		if (mon.getCurrentCicle() % 100 == 0)
			System.out.println((new StringBuilder("Epoch: ")).append(mon.getTotCicles() - mon.getCurrentCicle()).append(" RMSE:").append(mon.getGlobalError()).toString());
	}

	public void netStarted(NeuralNetEvent e)
	{
		Monitor mon = (Monitor)e.getSource();
		System.out.print("Network started for ");
		if (mon.isLearning())
			System.out.println("training.");
		else
			System.out.println("interrogation.");
	}

	public void netStopped(NeuralNetEvent e)
	{
		Monitor mon = (Monitor)e.getSource();
		System.out.println((new StringBuilder("Network stopped. Last RMSE=")).append(mon.getGlobalError()).toString());
	}

	public void netStoppedError(NeuralNetEvent e, String error)
	{
		System.out.println((new StringBuilder("Network stopped due the following error: ")).append(error).toString());
	}
}
