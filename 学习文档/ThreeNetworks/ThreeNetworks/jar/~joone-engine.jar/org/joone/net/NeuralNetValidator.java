// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NeuralNetValidator.java

package org.joone.net;

import java.util.Vector;
import org.joone.engine.*;

// Referenced classes of package org.joone.net:
//			NeuralNet, NeuralNetAttributes, NeuralValidationListener, NeuralValidationEvent

public class NeuralNetValidator
	implements Runnable, NeuralNetListener
{

	private final Vector listeners = new Vector();
	private final NeuralNet nnet;
	private Thread myThread;
	private int currentCycle;
	private int totCycles;
	private boolean useTrainingData;

	public NeuralNetValidator(NeuralNet nn)
	{
		myThread = null;
		useTrainingData = false;
		nnet = nn;
	}

	public synchronized void addValidationListener(NeuralValidationListener newListener)
	{
		if (!listeners.contains(newListener))
			listeners.addElement(newListener);
	}

	protected void validate()
	{
		totCycles = nnet.getMonitor().getTotCicles();
		currentCycle = nnet.getMonitor().getCurrentCicle();
		nnet.getMonitor().addNeuralNetListener(this);
		nnet.getMonitor().setLearning(false);
		nnet.getMonitor().setValidation(true);
		nnet.getMonitor().setTrainingDataForValidation(useTrainingData);
		nnet.getMonitor().setTotCicles(1);
		nnet.go();
	}

	public void fireNetValidated()
	{
		double error = nnet.getMonitor().getGlobalError();
		nnet.getDescriptor().setValidationError(error);
		Object list[];
		synchronized (this)
		{
			list = listeners.toArray();
		}
		for (int i = 0; i < list.length; i++)
		{
			NeuralValidationListener nvl = (NeuralValidationListener)list[i];
			nvl.netValidated(new NeuralValidationEvent(nnet));
		}

	}

	public void useTrainingData(boolean anUse)
	{
		useTrainingData = anUse;
	}

	public void start()
	{
		if (myThread == null)
		{
			myThread = new Thread(this, "Validator");
			myThread.start();
		}
	}

	public void run()
	{
		validate();
		myThread = null;
	}

	public void netStopped(NeuralNetEvent e)
	{
		fireNetValidated();
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

	public void netStoppedError(NeuralNetEvent neuralnetevent, String s)
	{
	}

	public NeuralNet getNeuralNet()
	{
		return nnet;
	}
}
