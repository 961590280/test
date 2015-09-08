// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Validation_using_stream.java

package org.joone.samples.engine.helpers;

import java.io.File;
import java.io.PrintStream;
import org.joone.engine.*;
import org.joone.helpers.factory.JooneTools;
import org.joone.io.FileInputSynapse;
import org.joone.net.NeuralNet;
import org.joone.net.NeuralNetAttributes;
import org.joone.util.NormalizerPlugIn;

public class Validation_using_stream
	implements NeuralNetListener
{

	private static final String fileName = "org/joone/samples/engine/helpers/wine.txt";
	private static final int trainingRows = 150;
	private double inputTrain[][];
	private double desiredTrain[][];
	private double inputTest[][];
	private double desiredTest[][];

	public Validation_using_stream()
	{
	}

	public static void main(String args[])
	{
		Validation_using_stream me = new Validation_using_stream();
		me.go();
	}

	private void go()
	{
		FileInputSynapse fileIn = new FileInputSynapse();
		fileIn.setInputFile(new File("org/joone/samples/engine/helpers/wine.txt"));
		fileIn.setAdvancedColumnSelector("1-14");
		NormalizerPlugIn normIn = new NormalizerPlugIn();
		normIn.setAdvancedSerieSelector("2-14");
		normIn.setMin(-1D);
		normIn.setMax(1.0D);
		fileIn.addPlugIn(normIn);
		NormalizerPlugIn normOut = new NormalizerPlugIn();
		normOut.setAdvancedSerieSelector("1");
		fileIn.addPlugIn(normOut);
		inputTrain = JooneTools.getDataFromStream(fileIn, 1, 150, 2, 14);
		desiredTrain = JooneTools.getDataFromStream(fileIn, 1, 150, 1, 1);
		inputTest = JooneTools.getDataFromStream(fileIn, 151, 178, 2, 14);
		desiredTest = JooneTools.getDataFromStream(fileIn, 151, 178, 1, 1);
		int nodes[] = {
			13, 4, 1
		};
		NeuralNet nnet = JooneTools.create_standard(nodes, 2);
		nnet.getMonitor().setLearningRate(0.29999999999999999D);
		nnet.getMonitor().setMomentum(0.5D);
		JooneTools.train(nnet, inputTrain, desiredTrain, 5000, 0.01D, 100, this, false);
		NeuralNetAttributes attrib = nnet.getDescriptor();
		System.out.println((new StringBuilder("Last training rmse=")).append(attrib.getTrainingError()).append(" at epoch ").append(attrib.getLastEpoch()).toString());
		double out[][] = JooneTools.compare(nnet, inputTest, desiredTest);
		System.out.println((new StringBuilder("Comparion of the last ")).append(out.length).append(" rows:").toString());
		int cols = out[0].length / 2;
		for (int i = 0; i < out.length; i++)
		{
			System.out.print("\nOutput: ");
			for (int x = 0; x < cols; x++)
				System.out.print((new StringBuilder(String.valueOf(out[i][x]))).append(" ").toString());

			System.out.print("\tTarget: ");
			for (int x = cols; x < cols * 2; x++)
				System.out.print((new StringBuilder(String.valueOf(out[i][x]))).append(" ").toString());

		}

	}

	public void cicleTerminated(NeuralNetEvent e)
	{
		Monitor mon = (Monitor)e.getSource();
		int epoch = (mon.getTotCicles() - mon.getCurrentCicle()) + 1;
		double trainErr = mon.getGlobalError();
		NeuralNet n = e.getNeuralNet().cloneNet();
		double testErr = JooneTools.test(n, inputTest, desiredTest);
		System.out.println((new StringBuilder("Epoch ")).append(epoch).append(":\n\tTraining error=").append(trainErr).append("\n\tValidation error=").append(testErr).toString());
	}

	public void errorChanged(NeuralNetEvent neuralnetevent)
	{
	}

	public void netStarted(NeuralNetEvent e)
	{
		System.out.println("Training...");
	}

	public void netStopped(NeuralNetEvent e)
	{
		System.out.println("Training stopped.");
	}

	public void netStoppedError(NeuralNetEvent e, String error)
	{
		System.out.println((new StringBuilder("Training stopped with error ")).append(error).toString());
	}
}
