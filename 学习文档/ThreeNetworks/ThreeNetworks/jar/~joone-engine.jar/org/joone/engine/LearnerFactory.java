// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LearnerFactory.java

package org.joone.engine;

import java.io.Serializable;

// Referenced classes of package org.joone.engine:
//			Monitor, Learner

public interface LearnerFactory
	extends Serializable
{

	public abstract Learner getLearner(Monitor monitor);
}
