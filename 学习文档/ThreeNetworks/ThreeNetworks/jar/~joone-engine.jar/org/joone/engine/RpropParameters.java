// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RpropParameters.java

package org.joone.engine;


public class RpropParameters
{

	private double theInitialDelta;
	private double theMaxDelta;
	private double theMinDelta;
	private double theEtaInc;
	private double theEtaDec;
	private int theBatchSize;

	public RpropParameters()
	{
		theInitialDelta = 0.10000000000000001D;
		theMaxDelta = 50D;
		theMinDelta = 9.9999999999999995E-007D;
		theEtaInc = 1.2D;
		theEtaDec = 0.5D;
		theBatchSize = 1;
	}

	public double getInitialDelta(int i, int j)
	{
		return theInitialDelta;
	}

	public void setInitialDelta(double anInitialDelta)
	{
		theInitialDelta = anInitialDelta;
	}

	public double getMaxDelta()
	{
		return theMaxDelta;
	}

	public void setMaxDelta(double aMaxDelta)
	{
		theMaxDelta = aMaxDelta;
	}

	public double getMinDelta()
	{
		return theMinDelta;
	}

	public void setMinDelta(double aMinDelta)
	{
		theMinDelta = aMinDelta;
	}

	public double getEtaInc()
	{
		return theEtaInc;
	}

	public void setEtaInc(double anEtaInc)
	{
		theEtaInc = anEtaInc;
	}

	public double getEtaDec()
	{
		return theEtaDec;
	}

	public void setEtaDec(double anEtaDec)
	{
		theEtaDec = anEtaDec;
	}

	public int getBatchSize()
	{
		return theBatchSize;
	}

	public void setBatchSize(int aBatchsize)
	{
		theBatchSize = aBatchsize;
	}
}
