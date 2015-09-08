// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultipleValidationSample.java

package org.joone.samples.engine.validation;

import java.io.*;
import org.joone.engine.*;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.io.MemoryOutputSynapse;
import org.joone.io.XLSInputSynapse;
import org.joone.net.*;
import org.joone.util.LearningSwitch;

// Referenced classes of package org.joone.samples.engine.validation:
//			NeuralNetTrainer, NeuralNetFactory

public class MultipleValidationSample
	implements NeuralValidationListener
{

	NeuralNet nnet;
	boolean ready;
	int totNets;
	int returnedNets;
	double totRMSE;
	double minRMSE;
	long mStart;
	int trainingLCP;
	int validationLCP;
	int totCycles;
	FileWriter wr;
	private static String filePath = "org/joone/samples/engine/validation";
	String xorNet;
	private static long fSLEEP_INTERVAL = 20L;

	public MultipleValidationSample()
	{
		totNets = 10;
		returnedNets = 0;
		totRMSE = 0.0D;
		minRMSE = 99D;
		trainingLCP = 1;
		validationLCP = 16;
		totCycles = 1000;
		wr = null;
		xorNet = (new StringBuilder(String.valueOf(filePath))).append("/trainedXOR.snet").toString();
	}

	public static void main(String args[])
	{
		MultipleValidationSample sampleNet = new MultipleValidationSample();
		sampleNet.start();
	}

	private void start()
	{
		try
		{
			wr = new FileWriter(new File("/tmp/memory.txt"));
			while (trainingLCP <= validationLCP) 
			{
				startValidation(trainingLCP, validationLCP);
				trainingLCP++;
				wr.flush();
			}
			wr.close();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		System.out.println("Done.");
		System.exit(0);
	}

	private synchronized void startValidation(int trnP, int valP)
	{
		nnet = initializeModularParity(trnP, valP);
		nnet.getMonitor().setTrainingPatterns(trnP);
		nnet.getMonitor().setValidationPatterns(valP);
		try
		{
			mStart = System.currentTimeMillis();
			returnedNets = 0;
			totRMSE = 0.0D;
			minRMSE = 99D;
			int n = totNets;
			for (int i = 0; i < 1; i++)
				test(n--);

			while (n > 0) 
			{
				while (!ready) 
					try
					{
						wait();
					}
					catch (InterruptedException interruptedexception) { }
				ready = false;
				test(n--);
				long mem = getMemoryUse();
				wr.write((new StringBuilder(String.valueOf(mem))).append("\r\n").toString());
			}
			while (returnedNets < totNets) 
				try
				{
					wait();
				}
				catch (InterruptedException interruptedexception1) { }
			displayResults();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	private void test(int n)
	{
		nnet.randomize(0.5D);
		nnet.setParam("ID", new Integer(n));
		NeuralNetTrainer trainer = new NeuralNetTrainer(nnet);
		trainer.addValidationListener(this);
		trainer.start();
	}

	public synchronized void netValidated(NeuralValidationEvent event)
	{
		NeuralNet NN = (NeuralNet)event.getSource();
		int n = ((Integer)NN.getParam("ID")).intValue();
		double rmse = NN.getMonitor().getGlobalError();
		totRMSE += rmse;
		if (minRMSE > rmse)
			minRMSE = rmse;
		returnedNets++;
		ready = true;
		notifyAll();
	}

	private void displayResults()
	{
		double aveRMSE = totRMSE / (double)totNets;
		long mTot = System.currentTimeMillis() - mStart;
		System.out.println("---------------------------------------------------------");
		System.out.println((new StringBuilder("Training Patterns: ")).append(trainingLCP).toString());
		System.out.println((new StringBuilder("Average Generalization Error: ")).append(aveRMSE).toString());
		System.out.println((new StringBuilder("Minimum Generalization Error: ")).append(minRMSE).toString());
		System.out.println((new StringBuilder("Elapsed Time: ")).append(mTot).append(" Miliseconds").toString());
		System.out.println("---------------------------------------------------------");
	}

	private static long getMemoryUse()
	{
		long totalMemory = Runtime.getRuntime().totalMemory();
		long freeMemory = Runtime.getRuntime().freeMemory();
		return totalMemory - freeMemory;
	}

	private static void collectGarbage()
	{
		try
		{
			System.gc();
			Thread.currentThread();
			Thread.sleep(fSLEEP_INTERVAL);
			System.runFinalization();
			Thread.currentThread();
			Thread.sleep(fSLEEP_INTERVAL);
		}
		catch (InterruptedException ex)
		{
			ex.printStackTrace();
		}
	}

	private NeuralNet initializeSimpleParity(int learningPatternNumber, int testPatternNumber)
	{
		NeuralNet network = new NeuralNet();
		double learningData[][] = constructLearningData(learningPatternNumber);
		double testData[][] = constructTestData(testPatternNumber);
		LinearLayer input = new LinearLayer();
		SigmoidLayer hidden = new SigmoidLayer();
		SigmoidLayer output = new SigmoidLayer();
		input.setLayerName("Input Layer");
		hidden.setLayerName("Hidden Layer");
		output.setLayerName("Output Layer");
		input.setRows(4);
		hidden.setRows(4);
		output.setRows(1);
		FullSynapse synapseIH = new FullSynapse();
		synapseIH.setName("IH Synapse");
		FullSynapse synapseHO = new FullSynapse();
		synapseHO.setName("HO Synapse");
		NeuralNetFactory.connect(input, synapseIH, hidden);
		NeuralNetFactory.connect(hidden, synapseHO, output);
		org.joone.io.MemoryInputSynapse learningInputSynapse = NeuralNetFactory.createInput("Learning Input Synapse", learningData, 1, 1, 4);
		org.joone.io.MemoryInputSynapse testInputSynapse = NeuralNetFactory.createInput("Test Input Synapse", testData, 1, 1, 4);
		LearningSwitch inputSwitch = NeuralNetFactory.createSwitch("Input Switch Synapse", learningInputSynapse, testInputSynapse);
		input.addInputSynapse(inputSwitch);
		org.joone.io.MemoryInputSynapse learningDesiredSynapse = NeuralNetFactory.createInput("Learning Desired Synapse", learningData, 1, 5, 5);
		org.joone.io.MemoryInputSynapse testDesiredSynapse = NeuralNetFactory.createInput("Test Desired Synapse", testData, 1, 5, 5);
		LearningSwitch learningSwitch = NeuralNetFactory.createSwitch("Learning Switch Synapse", learningDesiredSynapse, testDesiredSynapse);
		TeachingSynapse trainer = new TeachingSynapse();
		trainer.setName("Simple Parity Trainer Synapse");
		output.addOutputSynapse(trainer);
		trainer.setDesired(learningSwitch);
		MemoryOutputSynapse outputMemoryData = new MemoryOutputSynapse();
		outputMemoryData.setName("Output Data");
		output.addOutputSynapse(outputMemoryData);
		network.addLayer(input);
		network.addLayer(hidden);
		network.addLayer(output);
		network.setTeacher(trainer);
		network.getMonitor().setLearningRate(0.69999999999999996D);
		network.getMonitor().setMomentum(0.5D);
		network.getMonitor().setTotCicles(totCycles);
		return network;
	}

	private double[][] constructLearningData(int learningPatternNumber)
	{
		int columns = 5;
		double learningData[][] = new double[learningPatternNumber][columns];
		double simpleParityData[][] = {
			{
				0.0D, 0.0D, 0.0D, 0.0D, 1.0D
			}, {
				0.0D, 0.0D, 0.0D, 1.0D, 0.0D
			}, {
				0.0D, 0.0D, 1.0D, 0.0D, 0.0D
			}, {
				0.0D, 0.0D, 1.0D, 1.0D, 1.0D
			}, {
				0.0D, 1.0D, 0.0D, 0.0D, 0.0D
			}, {
				0.0D, 1.0D, 0.0D, 1.0D, 1.0D
			}, {
				0.0D, 1.0D, 1.0D, 0.0D, 1.0D
			}, {
				0.0D, 1.0D, 1.0D, 1.0D, 0.0D
			}, {
				1.0D, 0.0D, 0.0D, 0.0D, 0.0D
			}, {
				1.0D, 0.0D, 0.0D, 1.0D, 1.0D
			}, {
				1.0D, 0.0D, 1.0D, 0.0D, 1.0D
			}, {
				1.0D, 0.0D, 1.0D, 1.0D, 0.0D
			}, {
				1.0D, 1.0D, 0.0D, 0.0D, 1.0D
			}, {
				1.0D, 1.0D, 0.0D, 1.0D, 0.0D
			}, {
				1.0D, 1.0D, 1.0D, 0.0D, 0.0D
			}, {
				1.0D, 1.0D, 1.0D, 1.0D, 1.0D
			}
		};
		for (int i = 0; i < learningPatternNumber; i++)
		{
			learningData[i][0] = simpleParityData[i][0];
			learningData[i][1] = simpleParityData[i][1];
			learningData[i][2] = simpleParityData[i][2];
			learningData[i][3] = simpleParityData[i][3];
			learningData[i][4] = simpleParityData[i][4];
		}

		return learningData;
	}

	private double[][] constructTestData(int testPatternNumber)
	{
		int columns = 5;
		double testData[][] = new double[testPatternNumber][columns];
		double simpleParityData[][] = {
			{
				0.0D, 0.0D, 0.0D, 0.0D, 1.0D
			}, {
				0.0D, 0.0D, 0.0D, 1.0D, 0.0D
			}, {
				0.0D, 0.0D, 1.0D, 0.0D, 0.0D
			}, {
				0.0D, 0.0D, 1.0D, 1.0D, 1.0D
			}, {
				0.0D, 1.0D, 0.0D, 0.0D, 0.0D
			}, {
				0.0D, 1.0D, 0.0D, 1.0D, 1.0D
			}, {
				0.0D, 1.0D, 1.0D, 0.0D, 1.0D
			}, {
				0.0D, 1.0D, 1.0D, 1.0D, 0.0D
			}, {
				1.0D, 0.0D, 0.0D, 0.0D, 0.0D
			}, {
				1.0D, 0.0D, 0.0D, 1.0D, 1.0D
			}, {
				1.0D, 0.0D, 1.0D, 0.0D, 1.0D
			}, {
				1.0D, 0.0D, 1.0D, 1.0D, 0.0D
			}, {
				1.0D, 1.0D, 0.0D, 0.0D, 1.0D
			}, {
				1.0D, 1.0D, 0.0D, 1.0D, 0.0D
			}, {
				1.0D, 1.0D, 1.0D, 0.0D, 0.0D
			}, {
				1.0D, 1.0D, 1.0D, 1.0D, 1.0D
			}
		};
		for (int i = 0; i < testPatternNumber; i++)
		{
			testData[i][0] = simpleParityData[i][0];
			testData[i][1] = simpleParityData[i][1];
			testData[i][2] = simpleParityData[i][2];
			testData[i][3] = simpleParityData[i][3];
			testData[i][4] = simpleParityData[i][4];
		}

		return testData;
	}

	private NeuralNet initializeModularParity(int learningPatternNumber, int testPatternNumber)
	{
		NeuralNet network = new NeuralNet();
		NestedNeuralLayer firstNetwork = new NestedNeuralLayer();
		NestedNeuralLayer secondNetwork = new NestedNeuralLayer();
		firstNetwork.setNeuralNet(xorNet);
		secondNetwork.setNeuralNet(xorNet);
		firstNetwork.setLayerName("First Network");
		secondNetwork.setLayerName("Second Network");
		double learningData[][] = constructLearningData(learningPatternNumber);
		double testData[][] = constructTestData(testPatternNumber);
		LinearLayer inputFirst = new LinearLayer();
		LinearLayer inputSecond = new LinearLayer();
		SigmoidLayer hidden = new SigmoidLayer();
		SigmoidLayer output = new SigmoidLayer();
		inputFirst.setLayerName("First Input Third Network Layer");
		inputSecond.setLayerName("Second Input Third Network Layer");
		hidden.setLayerName("Hidden Third Network Layer");
		output.setLayerName("Output Third Network Layer");
		inputFirst.setRows(1);
		inputSecond.setRows(1);
		hidden.setRows(2);
		output.setRows(1);
		DirectSynapse firstSynapseOI = new DirectSynapse();
		firstSynapseOI.setName("First OI Synapse");
		DirectSynapse secondSynapseOI = new DirectSynapse();
		secondSynapseOI.setName("First OI Synapse");
		FullSynapse firstSynapseIH = new FullSynapse();
		firstSynapseIH.setName("First IH Synapse");
		FullSynapse secondSynapseIH = new FullSynapse();
		secondSynapseIH.setName("Second IH Synapse");
		FullSynapse synapseHO = new FullSynapse();
		synapseHO.setName("HO Synapse");
		NeuralNetFactory.connect(firstNetwork, firstSynapseOI, inputFirst);
		NeuralNetFactory.connect(secondNetwork, secondSynapseOI, inputSecond);
		NeuralNetFactory.connect(inputFirst, firstSynapseIH, hidden);
		NeuralNetFactory.connect(inputSecond, secondSynapseIH, hidden);
		NeuralNetFactory.connect(hidden, synapseHO, output);
		org.joone.io.MemoryInputSynapse firstLearningInputSynapse = NeuralNetFactory.createInput("First Learning Input Synapse", learningData, 1, 1, 2);
		org.joone.io.MemoryInputSynapse firstTestInputSynapse = NeuralNetFactory.createInput("First Test Input Synapse", testData, 1, 1, 2);
		LearningSwitch firstInputSwitch = NeuralNetFactory.createSwitch("First Input Switch Synapse", firstLearningInputSynapse, firstTestInputSynapse);
		firstNetwork.addInputSynapse(firstInputSwitch);
		org.joone.io.MemoryInputSynapse secondLearningInputSynapse = NeuralNetFactory.createInput("Second Learning Input Synapse", learningData, 1, 3, 4);
		org.joone.io.MemoryInputSynapse secondTestInputSynapse = NeuralNetFactory.createInput("Second Test Input Synapse", testData, 1, 3, 4);
		LearningSwitch secondInputSwitch = NeuralNetFactory.createSwitch("Second Input Switch Synapse", secondLearningInputSynapse, secondTestInputSynapse);
		secondInputSwitch.setStepCounter(false);
		secondNetwork.addInputSynapse(secondInputSwitch);
		org.joone.io.MemoryInputSynapse learningDesiredSynapse = NeuralNetFactory.createInput("Learning Desired Synapse", learningData, 1, 5, 5);
		org.joone.io.MemoryInputSynapse testDesiredSynapse = NeuralNetFactory.createInput("Test Desired Synapse", testData, 1, 5, 5);
		LearningSwitch learningSwitch = NeuralNetFactory.createSwitch("Learning Switch Synapse", learningDesiredSynapse, testDesiredSynapse);
		TeachingSynapse trainer = new TeachingSynapse();
		trainer.setName("Modular Parity Trainer Synapse");
		output.addOutputSynapse(trainer);
		trainer.setDesired(learningSwitch);
		MemoryOutputSynapse outputMemoryData = new MemoryOutputSynapse();
		outputMemoryData.setName("Output Data");
		output.addOutputSynapse(outputMemoryData);
		network.addLayer(firstNetwork);
		network.addLayer(secondNetwork);
		network.addLayer(inputFirst);
		network.addLayer(inputSecond);
		network.addLayer(hidden);
		network.addLayer(output);
		network.setTeacher(trainer);
		network.getMonitor().setLearningRate(0.5D);
		network.getMonitor().setMomentum(0.5D);
		network.getMonitor().setTotCicles(totCycles);
		return network;
	}

	private NeuralNet initializeNetworkI(int learningPatternNumber, int testPatternNumber)
	{
		NeuralNet network = new NeuralNet();
		LinearLayer input = new LinearLayer();
		SigmoidLayer hidden = new SigmoidLayer();
		SigmoidLayer output = new SigmoidLayer();
		input.setLayerName("Input Layer");
		hidden.setLayerName("Hidden Layer");
		output.setLayerName("Output Layer");
		input.setRows(2);
		hidden.setRows(2);
		output.setRows(1);
		FullSynapse synapseIH = new FullSynapse();
		synapseIH.setName("IH Synapse");
		FullSynapse synapseHO = new FullSynapse();
		synapseHO.setName("HO Synapse");
		NeuralNetFactory.connect(input, synapseIH, hidden);
		NeuralNetFactory.connect(hidden, synapseHO, output);
		XLSInputSynapse learningInputSynapse = new XLSInputSynapse();
		learningInputSynapse.setName("Learning Input Synapse");
		learningInputSynapse.setInputFile(new File("/tmp/wine.xls"));
		learningInputSynapse.setAdvancedColumnSelector("6,7");
		learningInputSynapse.setSheetName("wine.data");
		learningInputSynapse.setFirstRow(2);
		learningInputSynapse.setLastRow(100);
		XLSInputSynapse testInputSynapse = new XLSInputSynapse();
		testInputSynapse.setName("Test Input Synapse");
		testInputSynapse.setInputFile(new File("/tmp/wine.xls"));
		testInputSynapse.setAdvancedColumnSelector("6,7");
		testInputSynapse.setSheetName("wine.data");
		testInputSynapse.setFirstRow(2);
		testInputSynapse.setLastRow(100);
		LearningSwitch inputSwitch = NeuralNetFactory.createSwitch("Input Switch Synapse", learningInputSynapse, testInputSynapse);
		input.addInputSynapse(inputSwitch);
		XLSInputSynapse learningDesiredSynapse = new XLSInputSynapse();
		learningDesiredSynapse.setName("Learning Desired Synapse");
		learningDesiredSynapse.setInputFile(new File("/tmp/wine.xls"));
		learningDesiredSynapse.setAdvancedColumnSelector("8");
		learningDesiredSynapse.setSheetName("wine.data");
		learningDesiredSynapse.setFirstRow(2);
		learningDesiredSynapse.setLastRow(100);
		XLSInputSynapse testDesiredSynapse = new XLSInputSynapse();
		testDesiredSynapse.setName("Test Desired Synapse");
		testDesiredSynapse.setInputFile(new File("/tmp/wine.xls"));
		testDesiredSynapse.setAdvancedColumnSelector("8");
		testDesiredSynapse.setSheetName("wine.data");
		testDesiredSynapse.setFirstRow(2);
		testDesiredSynapse.setLastRow(100);
		LearningSwitch learningSwitch = NeuralNetFactory.createSwitch("Learning Switch Synapse", learningDesiredSynapse, testDesiredSynapse);
		TeachingSynapse trainer = new TeachingSynapse();
		trainer.setName("EETNN Trainer Synapse");
		output.addOutputSynapse(trainer);
		trainer.setDesired(learningSwitch);
		network.addLayer(input);
		network.addLayer(hidden);
		network.addLayer(output);
		network.setTeacher(trainer);
		return network;
	}

}
