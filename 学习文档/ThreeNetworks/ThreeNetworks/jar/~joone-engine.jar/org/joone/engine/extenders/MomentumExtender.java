// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MomentumExtender.java

package org.joone.engine.extenders;

import org.joone.engine.*;

// Referenced classes of package org.joone.engine.extenders:
//			DeltaRuleExtender, UpdateWeightExtender

public class MomentumExtender extends DeltaRuleExtender
{

	public MomentumExtender()
	{
	}

	public double getDelta(double currentGradientOuts[], int j, double aPreviousDelta)
	{
		ExtendableLearner learner = getLearner();
		if (learner.getUpdateWeightExtender().storeWeightsBiases())
			aPreviousDelta += learner.getMonitor().getMomentum() * learner.getLayer().getBias().delta[j][0];
		return aPreviousDelta;
	}

	public double getDelta(double currentInps[], int j, double currentPattern[], int k, double aPreviousDelta)
	{
		ExtendableLearner learner = getLearner();
		if (learner.getUpdateWeightExtender().storeWeightsBiases())
			aPreviousDelta += learner.getMonitor().getMomentum() * learner.getSynapse().getWeights().delta[j][k];
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
}
