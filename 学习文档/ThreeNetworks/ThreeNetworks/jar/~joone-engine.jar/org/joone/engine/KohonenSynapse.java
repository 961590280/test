// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   KohonenSynapse.java

package org.joone.engine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.TreeSet;

// Referenced classes of package org.joone.engine:
//			FullSynapse, NeuralNetListener, Pattern, Matrix, 
//			Monitor, NeuralNetEvent, Learner

public class KohonenSynapse extends FullSynapse
	implements NeuralNetListener
{

	private static final long serialVersionUID = 0xbb13ad7baa68e0b9L;
	double currentLearningRate;
	private double timeConstant;
	private int orderingPhase;

	public KohonenSynapse()
	{
		currentLearningRate = 1.0D;
		timeConstant = 200D;
		orderingPhase = 1000;
		learnable = false;
	}

	protected void backward(double pattern[])
	{
		double dFalloff = 0.0D;
		int num_outs = getOutputDimension();
		double o_pattern[] = b_pattern.getOutArray();
		for (int x = 0; x < num_outs; x++)
		{
			dFalloff = o_pattern[x];
			adjustNodeWeight(x, currentLearningRate, dFalloff, inps);
		}

	}

	protected void forward(double pattern[])
	{
		double temp = 0.0D;
		double curDist = 0.0D;
		int num_outs = getOutputDimension();
		for (int x = 0; x < num_outs; x++)
		{
			curDist = 0.0D;
			for (int inputs = 0; inputs < pattern.length; inputs++)
			{
				temp = array.value[inputs][x] - pattern[inputs];
				temp *= temp;
				curDist += temp;
			}

			outs[x] = curDist;
		}

	}

	private void adjustNodeWeight(int curnode, double learningRate, double distanceFalloff, double pattern[])
	{
		int output = curnode;
		for (int w = 0; w < getInputDimension(); w++)
		{
			double wt = array.value[w][output];
			double vw = pattern[w];
			wt += distanceFalloff * learningRate * (vw - wt);
			array.value[w][output] = wt;
		}

	}

	public void setMonitor(Monitor newMonitor)
	{
		super.setMonitor(newMonitor);
		if (getMonitor() != null)
			getMonitor().addNeuralNetListener(this, false);
	}

	public void cicleTerminated(NeuralNetEvent e)
	{
		int currentCycle = getMonitor().getTotCicles() - getMonitor().getCurrentCicle();
		if (currentCycle < getOrderingPhase())
			currentLearningRate = getMonitor().getLearningRate() * Math.exp(-((double)currentCycle / getTimeConstant()));
		else
			currentLearningRate = 0.01D;
	}

	public void errorChanged(NeuralNetEvent neuralnetevent)
	{
	}

	public void netStarted(NeuralNetEvent e)
	{
		currentLearningRate = getMonitor().getLearningRate();
	}

	public void netStopped(NeuralNetEvent neuralnetevent)
	{
	}

	public void netStoppedError(NeuralNetEvent neuralnetevent, String s)
	{
	}

	public TreeSet check()
	{
		TreeSet checks = super.check();
		return checks;
	}

	public int getOrderingPhase()
	{
		return orderingPhase;
	}

	public void setOrderingPhase(int orderingPhase)
	{
		this.orderingPhase = orderingPhase;
	}

	public double getTimeConstant()
	{
		return timeConstant;
	}

	public void setTimeConstant(double timeConstant)
	{
		this.timeConstant = timeConstant;
	}

	/**
	 * @deprecated Method getLearner is deprecated
	 */

	public Learner getLearner()
	{
		learnable = false;
		return super.getLearner();
	}

	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		if (getMonitor() != null)
			getMonitor().addNeuralNetListener(this, false);
	}
}
