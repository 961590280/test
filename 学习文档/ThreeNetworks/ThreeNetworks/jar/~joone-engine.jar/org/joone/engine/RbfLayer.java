// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RbfLayer.java

package org.joone.engine;


// Referenced classes of package org.joone.engine:
//			Layer

public abstract class RbfLayer extends Layer
{

	public RbfLayer()
	{
	}

	public RbfLayer(String anElemName)
	{
		super(anElemName);
	}

	protected void setDimensions()
	{
		outs = new double[getRows()];
		gradientInps = new double[getRows()];
	}

	protected void adjustSizeToFwdPattern(double aPattern[])
	{
		inps = new double[aPattern.length];
	}
}
