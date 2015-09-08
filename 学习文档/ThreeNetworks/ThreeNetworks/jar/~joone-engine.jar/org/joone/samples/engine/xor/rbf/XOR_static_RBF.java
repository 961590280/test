// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   XOR_static_RBF.java

package org.joone.samples.engine.xor.rbf;

import java.io.PrintStream;
import org.joone.engine.*;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.io.MemoryInputSynapse;
import org.joone.io.MemoryOutputSynapse;
import org.joone.net.NeuralNet;

public class XOR_static_RBF
	implements NeuralNetListener
{

	private NeuralNet nnet;
	RbfGaussianLayer hidden;
	private MemoryInputSynapse inputSynapse;
	private MemoryInputSynapse desiredOutputSynapse;
	private MemoryOutputSynapse outputSynapse;
	private boolean randomCenters;
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
			1.0D
		}, {
			0.0D
		}, {
			0.0D
		}, {
			1.0D
		}
	};

	public XOR_static_RBF()
	{
		nnet = null;
		hidden = null;
		randomCenters = false;
	}

	public static void main(String args[])
	{
		XOR_static_RBF xor = new XOR_static_RBF();
		xor.initNeuralNet();
		xor.train();
		xor.test();
	}

	public void train()
	{
		inputSynapse.setInputArray(inputArray);
		inputSynapse.setAdvancedColumnSelector("1,2");
		desiredOutputSynapse.setInputArray(desiredOutputArray);
		desiredOutputSynapse.setAdvancedColumnSelector("1");
		Monitor monitor = nnet.getMonitor();
		monitor.setLearningRate(0.29999999999999999D);
		monitor.setMomentum(0.80000000000000004D);
		monitor.setTrainingPatterns(inputArray.length);
		monitor.setTotCicles(200);
		monitor.setLearning(true);
		nnet.addNeuralNetListener(this);
		nnet.go(true);
	}

	protected void initNeuralNet()
	{
		LinearLayer input = new LinearLayer();
		hidden = new RbfGaussianLayer();
		BiasedLinearLayer output = new BiasedLinearLayer();
		input.setRows(2);
		hidden.setRows(2);
		output.setRows(1);
		if (!randomCenters)
		{
			RbfGaussianParameters myParameters[] = new RbfGaussianParameters[2];
			double myMean0[] = {
				0.0D, 0.0D
			};
			myParameters[0] = new RbfGaussianParameters(myMean0, Math.sqrt(0.5D));
			double myMean1[] = {
				1.0D, 1.0D
			};
			myParameters[1] = new RbfGaussianParameters(myMean1, Math.sqrt(0.5D));
			hidden.setGaussianParameters(myParameters);
		}
		RbfInputSynapse synapse_IH = new RbfInputSynapse();
		FullSynapse synapse_HO = new FullSynapse();
		input.addOutputSynapse(synapse_IH);
		hidden.addInputSynapse(synapse_IH);
		hidden.addOutputSynapse(synapse_HO);
		output.addInputSynapse(synapse_HO);
		inputSynapse = new MemoryInputSynapse();
		input.addInputSynapse(inputSynapse);
		if (randomCenters)
			hidden.useRandomCenter(inputSynapse);
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

	public void test()
	{
		outputSynapse = new MemoryOutputSynapse();
		nnet.getOutputLayer().addOutputSynapse(outputSynapse);
		nnet.getMonitor().setTotCicles(1);
		nnet.getMonitor().setTrainingPatterns(4);
		nnet.getMonitor().setLearning(false);
		nnet.removeAllListeners();
		nnet.go();
		System.out.println("Outputs");
		System.out.println("-------");
		for (int i = 0; i < 4; i++)
		{
			double myPattern[] = outputSynapse.getNextPattern();
			System.out.println((new StringBuilder("Output: ")).append(myPattern[0]).toString());
		}

		System.out.println("Centers RBF neurons: ");
		RbfGaussianParameters myParams[] = hidden.getGaussianParameters();
		for (int i = 0; i < myParams.length; i++)
		{
			String myText = (new StringBuilder(String.valueOf(i + 1))).append(": [center: ").toString();
			for (int j = 0; j < myParams[i].getMean().length; j++)
				myText = (new StringBuilder(String.valueOf(myText))).append(myParams[i].getMean()[j]).append(", ").toString();

			myText = (new StringBuilder(String.valueOf(myText))).append("Std dev: ").append(myParams[i].getStdDeviation()).append("]").toString();
			System.out.println(myText);
		}

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

	public void netStopped(NeuralNetEvent neuralnetevent)
	{
	}

	public void netStoppedError(NeuralNetEvent neuralnetevent, String s)
	{
	}
}
