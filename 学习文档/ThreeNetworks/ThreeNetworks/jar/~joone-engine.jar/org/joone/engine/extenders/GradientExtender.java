// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GradientExtender.java

package org.joone.engine.extenders;


// Referenced classes of package org.joone.engine.extenders:
//			LearnerExtender

public abstract class GradientExtender extends LearnerExtender
{

	public GradientExtender()
	{
	}

	public abstract double getGradientBias(double ad[], int i, double d);

	public abstract double getGradientWeight(double ad[], int i, double ad1[], int j, double d);
}
