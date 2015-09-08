// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Parity_Structure_Nakayama.java

package org.joone.samples.engine.parity;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Vector;
import org.joone.engine.*;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.engine.listeners.DeltaBasedConvergenceObserver;
import org.joone.io.MemoryInputSynapse;
import org.joone.io.MemoryOutputSynapse;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NeuralNet;
import org.joone.structure.Nakayama;

public class Parity_Structure_Nakayama
	implements NeuralNetListener, Serializable
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/structure/Nakayama);
	private NeuralNet nnet;
	private MemoryInputSynapse inputSynapse;
	private MemoryInputSynapse desiredOutputSynapse;
	private MemoryOutputSynapse outputSynapse;
	private Nakayama nakayama;
	private double inputArray[][] = {
		{
			0.0D, 0.0D, 0.0D, 0.0D
		}, {
			0.0D, 0.0D, 0.0D, 1.0D
		}, {
			0.0D, 0.0D, 1.0D, 0.0D
		}, {
			0.0D, 0.0D, 1.0D, 1.0D
		}, {
			0.0D, 1.0D, 0.0D, 0.0D
		}, {
			0.0D, 1.0D, 0.0D, 1.0D
		}, {
			0.0D, 1.0D, 1.0D, 0.0D
		}, {
			0.0D, 1.0D, 1.0D, 1.0D
		}, {
			1.0D, 0.0D, 0.0D, 0.0D
		}, {
			1.0D, 0.0D, 0.0D, 1.0D
		}, {
			1.0D, 0.0D, 1.0D, 0.0D
		}, {
			1.0D, 0.0D, 1.0D, 1.0D
		}, {
			1.0D, 1.0D, 0.0D, 0.0D
		}, {
			1.0D, 1.0D, 0.0D, 1.0D
		}, {
			1.0D, 1.0D, 1.0D, 0.0D
		}, {
			1.0D, 1.0D, 1.0D, 1.0D
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
		}, {
			1.0D
		}, {
			0.0D
		}, {
			0.0D
		}, {
			1.0D
		}, {
			1.0D
		}, {
			0.0D
		}, {
			0.0D
		}, {
			1.0D
		}, {
			0.0D
		}, {
			1.0D
		}, {
			1.0D
		}, {
			0.0D
		}
	};

	public Parity_Structure_Nakayama()
	{
		nnet = null;
	}

	public static void main(String args[])
	{
		Parity_Structure_Nakayama parity = new Parity_Structure_Nakayama();
		parity.initNeuralNet();
		parity.train();
	}

	public void train()
	{
		inputSynapse.setInputArray(inputArray);
		inputSynapse.setAdvancedColumnSelector("1-4");
		desiredOutputSynapse.setInputArray(desiredOutputArray);
		desiredOutputSynapse.setAdvancedColumnSelector("1");
		Monitor monitor = nnet.getMonitor();
		monitor.setUseRMSE(false);
		monitor.setLearningRate(0.5D);
		monitor.setMomentum(0.29999999999999999D);
		monitor.setTrainingPatterns(inputArray.length);
		monitor.setTotCicles(5000);
		monitor.setLearning(true);
		monitor.addLearner(0, "org.joone.engine.RpropLearner");
		monitor.addLearner(1, "org.joone.engine.BatchLearner");
		monitor.addLearner(2, "org.joone.engine.BasicLearner");
		monitor.setBatchSize(inputArray.length);
		monitor.setLearningMode(2);
		monitor.setSingleThreadMode(false);
		nnet.addNeuralNetListener(this);
		nnet.go();
	}

	private void test()
	{
		nnet.getMonitor().setTotCicles(1);
		nnet.getMonitor().setLearning(false);
		nnet.getMonitor().setSingleThreadMode(false);
		outputSynapse.setEnabled(true);
		nnet.removeAllListeners();
		nnet.go(true);
		Vector patts = outputSynapse.getAllPatterns();
		System.out.println("\nResults:");
		for (int i = patts.size(); i > 0; i--)
		{
			Pattern pattern = (Pattern)patts.elementAt(patts.size() - i);
			System.out.println((new StringBuilder("Output Pattern #")).append(patts.size() - i).append(" = ").append(pattern.getArray()[0]).toString());
		}

		System.out.println((new StringBuilder("Final RMSE: ")).append(nnet.getMonitor().getGlobalError()).toString());
	}

	protected void initNeuralNet()
	{
		LinearLayer input = new LinearLayer();
		SigmoidLayer hiddenSigmoid = new SigmoidLayer();
		SineLayer hiddenSine = new SineLayer();
		GaussLayer hiddenGauss = new GaussLayer();
		SigmoidLayer output = new SigmoidLayer();
		input.setRows(4);
		hiddenSigmoid.setRows(8);
		hiddenSine.setRows(8);
		hiddenGauss.setRows(8);
		output.setRows(1);
		FullSynapse synapse_IHSIGMOID = new FullSynapse();
		FullSynapse synapse_IHSINE = new FullSynapse();
		FullSynapse synapse_IHGAUSS = new FullSynapse();
		FullSynapse synapse_HSIGMOIDO = new FullSynapse();
		FullSynapse synapse_HSINEO = new FullSynapse();
		FullSynapse synapse_HGAUSSO = new FullSynapse();
		input.addOutputSynapse(synapse_IHSIGMOID);
		input.addOutputSynapse(synapse_IHSINE);
		input.addOutputSynapse(synapse_IHGAUSS);
		hiddenSigmoid.addInputSynapse(synapse_IHSIGMOID);
		hiddenSine.addInputSynapse(synapse_IHSINE);
		hiddenGauss.addInputSynapse(synapse_IHGAUSS);
		hiddenSigmoid.addOutputSynapse(synapse_HSIGMOIDO);
		hiddenSine.addOutputSynapse(synapse_HSINEO);
		hiddenGauss.addOutputSynapse(synapse_HGAUSSO);
		output.addInputSynapse(synapse_HSIGMOIDO);
		output.addInputSynapse(synapse_HSINEO);
		output.addInputSynapse(synapse_HGAUSSO);
		inputSynapse = new MemoryInputSynapse();
		input.addInputSynapse(inputSynapse);
		outputSynapse = new MemoryOutputSynapse();
		output.addOutputSynapse(outputSynapse);
		outputSynapse.setEnabled(false);
		desiredOutputSynapse = new MemoryInputSynapse();
		TeachingSynapse trainer = new TeachingSynapse();
		trainer.setDesired(desiredOutputSynapse);
		nnet = new NeuralNet();
		nnet.addLayer(input, 0);
		nnet.addLayer(hiddenSigmoid, 1);
		nnet.addLayer(hiddenSine, 1);
		nnet.addLayer(hiddenGauss, 1);
		nnet.addLayer(output, 2);
		nnet.setTeacher(trainer);
		output.addOutputSynapse(trainer);
		nakayama = new Nakayama(nnet);
		nakayama.addLayer(hiddenSigmoid);
		nakayama.addLayer(hiddenSine);
		nakayama.addLayer(hiddenGauss);
		DeltaBasedConvergenceObserver myObserver = new DeltaBasedConvergenceObserver();
		myObserver.setSize(0.00050000000000000001D);
		myObserver.setNeuralNet(nnet);
		myObserver.addConvergenceListener(nakayama);
		nnet.addNeuralNetListener(myObserver);
	}

	public void cicleTerminated(NeuralNetEvent neuralnetevent)
	{
	}

	public void errorChanged(NeuralNetEvent e)
	{
		Monitor mon = (Monitor)e.getSource();
		int c = mon.getTotCicles() - mon.getCurrentCicle();
		if (c % 100 == 0)
			System.out.println((new StringBuilder("Cycle: ")).append(c).append(" (R)MSE:").append(mon.getGlobalError()).toString());
	}

	public void netStarted(NeuralNetEvent neuralnetevent)
	{
	}

	public void netStopped(NeuralNetEvent e)
	{
		test();
	}

	public void netStoppedError(NeuralNetEvent neuralnetevent, String s)
	{
	}

}
