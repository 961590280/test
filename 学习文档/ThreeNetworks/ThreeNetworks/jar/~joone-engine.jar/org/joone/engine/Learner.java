// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Learner.java

package org.joone.engine;

import java.io.Serializable;

// Referenced classes of package org.joone.engine:
//			Learnable, Monitor

public interface Learner
	extends Serializable
{

	public abstract void registerLearnable(Learnable learnable);

	public abstract void requestBiasUpdate(double ad[]);

	public abstract void requestWeightUpdate(double ad[], double ad1[]);

	public abstract void setMonitor(Monitor monitor);
}
