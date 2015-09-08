// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OnlineModeExtender.java

package org.joone.engine.extenders;

import org.joone.engine.*;

// Referenced classes of package org.joone.engine.extenders:
//			UpdateWeightExtender

public class OnlineModeExtender extends UpdateWeightExtender
{

	public OnlineModeExtender()
	{
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

	public void updateBias(int j, double aDelta)
	{
		Matrix bias = getLearner().getLayer().getBias();
		bias.delta[j][0] = aDelta;
		bias.value[j][0] += aDelta;
	}

	public void updateWeight(int j, int k, double aDelta)
	{
		Matrix weights = getLearner().getSynapse().getWeights();
		weights.delta[j][k] = aDelta;
		weights.value[j][k] += aDelta;
	}

	public boolean storeWeightsBiases()
	{
		return true;
	}
}
