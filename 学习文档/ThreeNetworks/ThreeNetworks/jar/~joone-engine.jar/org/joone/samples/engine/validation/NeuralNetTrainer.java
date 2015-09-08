// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NeuralNetTrainer.java

package org.joone.samples.engine.validation;

import java.util.Vector;
import org.joone.engine.*;
import org.joone.net.*;

public class NeuralNetTrainer
	implements Runnable, NeuralNetListener, NeuralValidationListener
{

	private Vector listeners;
	private NeuralNet nnet;
	private Thread myThread;

	public NeuralNetTrainer(NeuralNet nn)
	{
		myThread = null;
		listeners = new Vector();
		nnet = cloneNet(nn);
	}

	public void addValidationListener(NeuralValidationListener newListener)
	{
		if (!listeners.contains(newListener))
			listeners.addElement(newListener);
	}

	protected void train()
	{
		nnet.getMonitor().addNeuralNetListener(this);
		nnet.getMonitor().setLearning(true);
		nnet.getMonitor().setValidation(false);
		nnet.go(true);
		validate();
	}

	protected void validate()
	{
		NeuralNet newNet = cloneNet(nnet);
		NeuralNetValidator nnv = new NeuralNetValidator(newNet);
		nnv.addValidationListener(this);
		nnv.start();
	}

	private NeuralNet cloneNet(NeuralNet net)
	{
		net.getMonitor().setExporting(true);
		NeuralNet newNet = net.cloneNet();
		net.getMonitor().setExporting(false);
		newNet.removeAllListeners();
		return newNet;
	}

	private void fireNetValidated(NeuralValidationEvent event)
	{
		NeuralNet NN = (NeuralNet)event.getSource();
		NN.terminate(false);
		for (int i = 0; i < listeners.size(); i++)
		{
			NeuralValidationListener nvl = (NeuralValidationListener)listeners.elementAt(i);
			nvl.netValidated(new NeuralValidationEvent(NN));
		}

	}

	public void start()
	{
		if (myThread == null)
		{
			myThread = new Thread(this, "Trainer");
			myThread.start();
		}
	}

	public void run()
	{
		train();
		myThread = null;
	}

	public void netStopped(NeuralNetEvent neuralnetevent)
	{
	}

	public void netValidated(NeuralValidationEvent event)
	{
		fireNetValidated(event);
	}

	public void cicleTerminated(NeuralNetEvent neuralnetevent)
	{
	}

	public void netStarted(NeuralNetEvent neuralnetevent)
	{
	}

	public void errorChanged(NeuralNetEvent neuralnetevent)
	{
	}

	public void netStoppedError(NeuralNetEvent e, String error)
	{
		System.exit(1);
	}
}
