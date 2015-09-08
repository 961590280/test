// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   XOR_using_NeuralNet_RPROP.java

package org.joone.samples.engine.xor;

import java.io.PrintStream;
import java.util.Vector;
import org.joone.engine.*;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.io.MemoryInputSynapse;
import org.joone.io.MemoryOutputSynapse;
import org.joone.net.NeuralNet;

public class XOR_using_NeuralNet_RPROP
	implements NeuralNetListener
{

	private NeuralNet nnet;
	private MemoryInputSynapse inputSynapse;
	private MemoryInputSynapse desiredOutputSynapse;
	private MemoryOutputSynapse outputSynapse;
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

	public XOR_using_NeuralNet_RPROP()
	{
		nnet = null;
	}

	public static void main(String args[])
	{
		XOR_using_NeuralNet_RPROP xor = new XOR_using_NeuralNet_RPROP();
		xor.initNeuralNet();
		xor.train();
	}

	public void train()
	{
		inputSynapse.setInputArray(inputArray);
		inputSynapse.setAdvancedColumnSelector("1,2");
		desiredOutputSynapse.setInputArray(desiredOutputArray);
		desiredOutputSynapse.setAdvancedColumnSelector("1");
		Monitor monitor = nnet.getMonitor();
		monitor.setLearningRate(1.0D);
		monitor.setTrainingPatterns(inputArray.length);
		monitor.setTotCicles(500);
		monitor.addLearner(0, "org.joone.engine.RpropLearner");
		monitor.setBatchSize(monitor.getTrainingPatterns());
		monitor.setLearningMode(0);
		monitor.setLearning(true);
		nnet.addNeuralNetListener(this);
		nnet.go();
	}

	protected void initNeuralNet()
	{
		LinearLayer input = new LinearLayer();
		SigmoidLayer hidden = new SigmoidLayer();
		SigmoidLayer output = new SigmoidLayer();
		input.setRows(2);
		hidden.setRows(3);
		output.setRows(1);
		FullSynapse synapse_IH = new FullSynapse();
		FullSynapse synapse_HO = new FullSynapse();
		input.addOutputSynapse(synapse_IH);
		hidden.addInputSynapse(synapse_IH);
		hidden.addOutputSynapse(synapse_HO);
		output.addInputSynapse(synapse_HO);
		inputSynapse = new MemoryInputSynapse();
		input.addInputSynapse(inputSynapse);
		outputSynapse = new MemoryOutputSynapse();
		output.addOutputSynapse(outputSynapse);
		desiredOutputSynapse = new MemoryInputSynapse();
		TeachingSynapse trainer = new TeachingSynapse();
		trainer.setDesired(desiredOutputSynapse);
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

	public void netStarted(NeuralNetEvent neuralnetevent)
	{
	}

	public void netStopped(NeuralNetEvent e)
	{
		Monitor mon = (Monitor)e.getSource();
		Vector patts = outputSynapse.getAllPatterns();
		Pattern pattern = (Pattern)patts.elementAt(patts.size() - 1);
		System.out.println((new StringBuilder("Output Pattern = ")).append(pattern.getArray()[0]).append(" Error: ").append(mon.getGlobalError()).toString());
	}

	public void netStoppedError(NeuralNetEvent neuralnetevent, String s)
	{
	}
}
