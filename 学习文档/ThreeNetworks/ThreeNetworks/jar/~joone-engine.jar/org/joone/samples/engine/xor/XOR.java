// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   XOR.java

package org.joone.samples.engine.xor;

import java.io.File;
import java.io.PrintStream;
import org.joone.engine.*;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.io.FileInputSynapse;
import org.joone.io.FileOutputSynapse;
import org.joone.net.NeuralNet;

public class XOR
	implements NeuralNetListener
{

	private static String inputData = "org/joone/samples/engine/xor/xor.txt";
	private static String outputFile = "/tmp/xorout.txt";

	public XOR()
	{
	}

	public static void main(String args[])
	{
		XOR xor = new XOR();
		xor.Go(inputData, outputFile);
	}

	public void Go(String inputFile, String outputFile)
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
		FileInputSynapse inputStream = new FileInputSynapse();
		inputStream.setAdvancedColumnSelector("1,2");
		inputStream.setInputFile(new File(inputFile));
		input.addInputSynapse(inputStream);
		TeachingSynapse trainer = new TeachingSynapse();
		FileInputSynapse samples = new FileInputSynapse();
		samples.setInputFile(new File(inputFile));
		samples.setAdvancedColumnSelector("3");
		trainer.setDesired(samples);
		FileOutputSynapse error = new FileOutputSynapse();
		error.setFileName(outputFile);
		trainer.addResultSynapse(error);
		output.addOutputSynapse(trainer);
		NeuralNet nnet = new NeuralNet();
		nnet.addLayer(input, 0);
		nnet.addLayer(hidden, 1);
		nnet.addLayer(output, 2);
		nnet.setTeacher(trainer);
		Monitor monitor = nnet.getMonitor();
		monitor.setLearningRate(0.80000000000000004D);
		monitor.setMomentum(0.29999999999999999D);
		monitor.addNeuralNetListener(this);
		monitor.setTrainingPatterns(4);
		monitor.setTotCicles(2000);
		monitor.setLearning(true);
		nnet.go();
	}

	public void netStopped(NeuralNetEvent e)
	{
		System.out.println("Training finished");
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
		if (mon.getCurrentCicle() % 200 == 0)
			System.out.println((new StringBuilder(String.valueOf(mon.getCurrentCicle()))).append(" epochs remaining - RMSE = ").append(mon.getGlobalError()).toString());
	}

	public void netStoppedError(NeuralNetEvent neuralnetevent, String s)
	{
	}

}
