// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ImmediateDirectSynapse.java

package org.joone.helpers.factory;

import org.joone.engine.DirectSynapse;

public class ImmediateDirectSynapse extends DirectSynapse
{

	private double result[];

	public ImmediateDirectSynapse()
	{
	}

	protected void forward(double pattern[])
	{
		super.forward(pattern);
		setResult(outs);
	}

	public double[] getResult()
	{
		return result;
	}

	public void setResult(double result[])
	{
		this.result = result;
	}
}
