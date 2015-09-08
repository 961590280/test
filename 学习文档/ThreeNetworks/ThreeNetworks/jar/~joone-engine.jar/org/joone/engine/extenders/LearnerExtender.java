// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LearnerExtender.java

package org.joone.engine.extenders;

import org.joone.engine.ExtendableLearner;

public abstract class LearnerExtender
{

	private boolean theMode;
	private ExtendableLearner theLearner;

	public LearnerExtender()
	{
		theMode = true;
	}

	public void setLearner(ExtendableLearner aLearner)
	{
		theLearner = aLearner;
	}

	protected ExtendableLearner getLearner()
	{
		return theLearner;
	}

	public boolean isEnabled()
	{
		return theMode;
	}

	public void setEnabled(boolean aMode)
	{
		theMode = aMode;
	}

	public abstract void preBiasUpdate(double ad[]);

	public abstract void postBiasUpdate(double ad[]);

	public abstract void preWeightUpdate(double ad[], double ad1[]);

	public abstract void postWeightUpdate(double ad[], double ad1[]);
}
