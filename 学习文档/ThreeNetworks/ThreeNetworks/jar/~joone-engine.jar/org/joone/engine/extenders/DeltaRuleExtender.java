// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DeltaRuleExtender.java

package org.joone.engine.extenders;


// Referenced classes of package org.joone.engine.extenders:
//			LearnerExtender

public abstract class DeltaRuleExtender extends LearnerExtender
{

	public DeltaRuleExtender()
	{
	}

	public abstract double getDelta(double ad[], int i, double d);

	public abstract double getDelta(double ad[], int i, double ad1[], int j, double d);
}
