// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AbstractLearner.java

package org.joone.engine;


// Referenced classes of package org.joone.engine:
//			Learner, LearnableLayer, LearnableSynapse, Learnable, 
//			Monitor

public abstract class AbstractLearner
	implements Learner
{

	protected Learnable learnable;
	protected LearnableLayer learnableLayer;
	protected LearnableSynapse learnableSynapse;
	protected Monitor monitor;

	public AbstractLearner()
	{
		learnable = null;
		learnableLayer = null;
		learnableSynapse = null;
	}

	public void registerLearnable(Learnable aLearnable)
	{
		learnable = aLearnable;
		if (aLearnable instanceof LearnableLayer)
			learnableLayer = (LearnableLayer)aLearnable;
		else
		if (aLearnable instanceof LearnableSynapse)
			learnableSynapse = (LearnableSynapse)aLearnable;
	}

	public void setMonitor(Monitor mon)
	{
		monitor = mon;
	}

	public Monitor getMonitor()
	{
		return monitor;
	}

	public LearnableLayer getLayer()
	{
		return learnableLayer;
	}

	public LearnableSynapse getSynapse()
	{
		return learnableSynapse;
	}
}
