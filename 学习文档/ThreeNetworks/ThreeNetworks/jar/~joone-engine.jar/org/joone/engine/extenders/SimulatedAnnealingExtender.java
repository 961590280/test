// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SimulatedAnnealingExtender.java

package org.joone.engine.extenders;

import org.joone.engine.ExtendableLearner;
import org.joone.engine.Monitor;

// Referenced classes of package org.joone.engine.extenders:
//			DeltaRuleExtender, UpdateWeightExtender

public class SimulatedAnnealingExtender extends DeltaRuleExtender
{

	private double theN;
	private double theK;
	private double theBoundary;

	public SimulatedAnnealingExtender()
	{
		theN = 0.29999999999999999D;
		theK = 0.002D;
		theBoundary = 0.5D;
	}

	public double getDelta(double currentGradientOuts[], int j, double aPreviousDelta)
	{
		ExtendableLearner learner = getLearner();
		if (learner.getUpdateWeightExtender().storeWeightsBiases())
		{
			Monitor monitor = learner.getMonitor();
			int myCycle = monitor.getTotCicles() - monitor.getCurrentCicle();
			aPreviousDelta += getN() * getRandom() * Math.pow(2D, -1D * getK() * (double)myCycle);
		}
		return aPreviousDelta;
	}

	public double getDelta(double currentInps[], int j, double currentPattern[], int k, double aPreviousDelta)
	{
		ExtendableLearner learner = getLearner();
		if (learner.getUpdateWeightExtender().storeWeightsBiases())
		{
			Monitor monitor = learner.getMonitor();
			int myCycle = monitor.getTotCicles() - monitor.getCurrentCicle();
			aPreviousDelta += getN() * getRandom() * Math.pow(2D, -1D * getK() * (double)myCycle);
		}
		return aPreviousDelta;
	}

	public void postBiasUpdate(double ad[])
	{
	}

	public void postWeightUpdate(double ad[], double ad1[])
	{
	}

	public void preBiasUpdate(double ad[])
	{
	}

	public void preWeightUpdate(double ad[], double ad1[])
	{
	}

	public double getN()
	{
		return theN;
	}

	public void setN(double aN)
	{
		theN = aN;
	}

	public double getK()
	{
		return theK;
	}

	public void setK(double aK)
	{
		theK = aK;
	}

	public double getRandomBoundary()
	{
		return theBoundary;
	}

	public void setRandomBoundary(double aBoundary)
	{
		theBoundary = aBoundary;
	}

	protected double getRandom()
	{
		double randomBoundary = getRandomBoundary();
		return Math.random() * 2D * randomBoundary - randomBoundary;
	}
}
