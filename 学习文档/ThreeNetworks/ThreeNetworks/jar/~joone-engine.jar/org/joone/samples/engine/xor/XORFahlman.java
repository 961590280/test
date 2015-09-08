// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   XORFahlman.java

package org.joone.samples.engine.xor;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Vector;
import org.joone.engine.*;
import org.joone.engine.learning.FahlmanTeacherSynapse;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.io.InputConnector;
import org.joone.net.*;
import org.joone.samples.util.ParityInputSynapse;
import org.joone.util.LearningSwitch;

public class XORFahlman
	implements Serializable, NeuralNetListener, NeuralValidationListener
{

	private NeuralNet nnet;
	private Vector validationCycles;
	private long mills;

	public XORFahlman()
	{
		nnet = null;
		validationCycles = new Vector();
	}

	public static void main(String args[])
	{
		XORFahlman xor = new XORFahlman();
		xor.Go();
	}

	public void Go()
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
		ParityInputSynapse inputStream = new ParityInputSynapse();
		inputStream.setParitySize(2);
		InputConnector myInputData = new InputConnector();
		myInputData.setInputSynapse(inputStream);
		myInputData.setAdvancedColumnSelector("1-2");
		InputConnector myInputValData = new InputConnector();
		myInputValData.setInputSynapse(inputStream);
		myInputValData.setAdvancedColumnSelector("1-2");
		LearningSwitch mySwitch = new LearningSwitch();
		mySwitch.addTrainingSet(myInputData);
		mySwitch.addValidationSet(myInputValData);
		input.addInputSynapse(mySwitch);
		FahlmanTeacherSynapse myFahlman = new FahlmanTeacherSynapse();
		InputConnector myDesiredData = new InputConnector();
		myDesiredData.setInputSynapse(inputStream);
		myDesiredData.setAdvancedColumnSelector("3");
		InputConnector myDesiredValData = new InputConnector();
		myDesiredValData.setInputSynapse(inputStream);
		myDesiredValData.setAdvancedColumnSelector("3");
		LearningSwitch myOutputSwitch = new LearningSwitch();
		myOutputSwitch.addTrainingSet(myDesiredData);
		myOutputSwitch.addValidationSet(myDesiredValData);
		TeachingSynapse trainer = new TeachingSynapse(myFahlman);
		trainer.setDesired(myOutputSwitch);
		output.addOutputSynapse(trainer);
		nnet = new NeuralNet();
		nnet.addLayer(input, 0);
		nnet.addLayer(hidden, 1);
		nnet.addLayer(output, 2);
		nnet.setTeacher(trainer);
		nnet.getMonitor().setTrainingPatterns(4);
		nnet.getMonitor().setValidationPatterns(4);
		nnet.getMonitor().setTotCicles(10000);
		nnet.getMonitor().setLearning(true);
		nnet.getMonitor().setLearningRate(0.80000000000000004D);
		nnet.getMonitor().setMomentum(0.29999999999999999D);
		mills = System.currentTimeMillis();
		nnet.addNeuralNetListener(this);
		nnet.go();
	}

	public void netStopped(NeuralNetEvent e)
	{
		long delay = System.currentTimeMillis() - mills;
		System.out.println((new StringBuilder("Training finished after ")).append(delay).append(" ms").toString());
		System.exit(0);
	}

	public void cicleTerminated(NeuralNetEvent e)
	{
		Monitor mon = (Monitor)e.getSource();
		long c = mon.getCurrentCicle();
		if (c % 200L == 0L)
		{
			nnet.getMonitor().setExporting(true);
			NeuralNet myClone = nnet.cloneNet();
			nnet.getMonitor().setExporting(false);
			myClone.removeAllListeners();
			myClone.getMonitor().setParam("FAHLMAN_CRITERION", Boolean.TRUE);
			NeuralNetValidator myValidator = new NeuralNetValidator(myClone);
			myValidator.addValidationListener(this);
			validationCycles.add(new Integer(nnet.getMonitor().getTotCicles() - nnet.getMonitor().getCurrentCicle()));
			myValidator.start();
		}
	}

	public void netStarted(NeuralNetEvent e)
	{
		System.out.println("Training...");
	}

	public void errorChanged(NeuralNetEvent e)
	{
		Monitor mon = (Monitor)e.getSource();
		long c = mon.getCurrentCicle();
		if (c % 100L == 0L)
			System.out.println((new StringBuilder(String.valueOf(c))).append(" cycles remaining - Error = ").append(mon.getGlobalError()).toString());
	}

	public void netStoppedError(NeuralNetEvent neuralnetevent, String s)
	{
	}

	public void netValidated(NeuralValidationEvent event)
	{
		Monitor myMonitor = ((NeuralNet)event.getSource()).getMonitor();
		if (myMonitor.getParam("FAHLMAN_CRITERION") != null && ((Boolean)myMonitor.getParam("FAHLMAN_CRITERION")).booleanValue() && nnet.isRunning())
		{
			nnet.stop();
			System.out.println((new StringBuilder("Fahlman criterion fulfilled (at cycle ")).append(((Integer)validationCycles.get(0)).intValue()).append(")...").toString());
		}
		validationCycles.remove(0);
	}
}
