// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   XOR_multipleInputs.java

package org.joone.samples.engine.multipleInputs;

import java.io.File;
import java.io.PrintStream;
import org.joone.engine.*;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.io.*;
import org.joone.net.NeuralNet;

public class XOR_multipleInputs
	implements NeuralNetListener
{

	private NeuralNet nnet;
	private FileInputSynapse inputSynapse1;
	private FileInputSynapse inputSynapse2;
	private FileInputSynapse inputSynapse3;
	private FileInputSynapse desiredSynapse1;
	private FileInputSynapse desiredSynapse2;
	private FileInputSynapse desiredSynapse3;
	private InputSwitchSynapse inputSw;
	private InputSwitchSynapse desiredSw;
	private static String inputFileName = "org/joone/samples/engine/multipleInputs/xor.txt";

	public XOR_multipleInputs()
	{
		nnet = null;
	}

	public static void main(String args[])
	{
		XOR_multipleInputs xor = new XOR_multipleInputs();
		xor.initNeuralNet();
		xor.train();
		xor.interrogate();
	}

	public void train()
	{
		Monitor monitor = nnet.getMonitor();
		monitor.setLearningRate(0.80000000000000004D);
		monitor.setMomentum(0.29999999999999999D);
		monitor.setTrainingPatterns(9);
		monitor.setTotCicles(2000);
		monitor.setLearning(true);
		nnet.addNeuralNetListener(this);
		nnet.go(true);
		System.out.println((new StringBuilder("Network stopped. Last RMSE=")).append(nnet.getMonitor().getGlobalError()).toString());
	}

	private void interrogate()
	{
		Monitor monitor = nnet.getMonitor();
		monitor.setTotCicles(1);
		monitor.setLearning(false);
		FileOutputSynapse output = new FileOutputSynapse();
		output.setFileName("/tmp/xorOut.txt");
		if (nnet != null)
		{
			nnet.addOutputSynapse(output);
			System.out.println(nnet.check());
			nnet.go(true);
		}
	}

	protected void initNeuralNet()
	{
		LinearLayer input = new LinearLayer();
		SigmoidLayer hidden = new SigmoidLayer();
		SigmoidLayer output = new SigmoidLayer();
		input.setRows(2);
		hidden.setRows(3);
		output.setRows(1);
		input.setLayerName("inputLayer");
		hidden.setLayerName("hiddenLayer");
		output.setLayerName("outputLayer");
		FullSynapse synapse_IH = new FullSynapse();
		FullSynapse synapse_HO = new FullSynapse();
		input.addOutputSynapse(synapse_IH);
		hidden.addInputSynapse(synapse_IH);
		hidden.addOutputSynapse(synapse_HO);
		output.addInputSynapse(synapse_HO);
		inputSynapse1 = new FileInputSynapse();
		inputSynapse2 = new FileInputSynapse();
		inputSynapse3 = new FileInputSynapse();
		inputSynapse1.setInputFile(new File(inputFileName));
		inputSynapse1.setName("input1");
		inputSynapse1.setAdvancedColumnSelector("1-2");
		inputSynapse1.setFirstRow(1);
		inputSynapse1.setLastRow(4);
		inputSynapse2.setInputFile(new File(inputFileName));
		inputSynapse2.setName("input2");
		inputSynapse2.setAdvancedColumnSelector("1-2");
		inputSynapse2.setFirstRow(2);
		inputSynapse2.setLastRow(4);
		inputSynapse3.setInputFile(new File(inputFileName));
		inputSynapse3.setName("input3");
		inputSynapse3.setAdvancedColumnSelector("1-2");
		inputSynapse3.setFirstRow(3);
		inputSynapse3.setLastRow(4);
		inputSw = new MultipleInputSynapse();
		inputSw.addInputSynapse(inputSynapse1);
		inputSw.addInputSynapse(inputSynapse2);
		inputSw.addInputSynapse(inputSynapse3);
		input.addInputSynapse(inputSw);
		desiredSynapse1 = new FileInputSynapse();
		desiredSynapse2 = new FileInputSynapse();
		desiredSynapse3 = new FileInputSynapse();
		desiredSynapse1.setInputFile(new File(inputFileName));
		desiredSynapse1.setName("desired1");
		desiredSynapse1.setAdvancedColumnSelector("3");
		desiredSynapse1.setFirstRow(1);
		desiredSynapse1.setLastRow(4);
		desiredSynapse2.setInputFile(new File(inputFileName));
		desiredSynapse2.setName("desired2");
		desiredSynapse2.setAdvancedColumnSelector("3");
		desiredSynapse2.setFirstRow(2);
		desiredSynapse2.setLastRow(4);
		desiredSynapse3.setInputFile(new File(inputFileName));
		desiredSynapse3.setName("desired3");
		desiredSynapse3.setAdvancedColumnSelector("3");
		desiredSynapse3.setFirstRow(3);
		desiredSynapse3.setLastRow(4);
		desiredSw = new MultipleInputSynapse();
		desiredSw.addInputSynapse(desiredSynapse1);
		desiredSw.addInputSynapse(desiredSynapse2);
		desiredSw.addInputSynapse(desiredSynapse3);
		TeachingSynapse trainer = new TeachingSynapse();
		trainer.setDesired(desiredSw);
		nnet = new NeuralNet();
		nnet.addLayer(input, 0);
		nnet.addLayer(hidden, 1);
		nnet.addLayer(output, 2);
		nnet.setTeacher(trainer);
		output.addOutputSynapse(trainer);
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
		if (mon.isLearning())
			System.out.println("Training...");
	}

	public void netStopped(NeuralNetEvent e)
	{
		System.out.println("Stopped");
	}

	public void netStoppedError(NeuralNetEvent neuralnetevent, String s)
	{
	}

}
