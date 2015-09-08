// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UpdateWeightExtender.java

package org.joone.engine.extenders;


// Referenced classes of package org.joone.engine.extenders:
//			LearnerExtender

public abstract class UpdateWeightExtender extends LearnerExtender
{

	public UpdateWeightExtender()
	{
	}

	public abstract void updateBias(int i, double d);

	public abstract void updateWeight(int i, int j, double d);

	public abstract boolean storeWeightsBiases();
}
