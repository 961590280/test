// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RbfGaussianParameters.java

package org.joone.engine;

import java.io.Serializable;

public class RbfGaussianParameters
	implements Serializable
{

	private double theMean[];
	private double theStdDeviation;

	public RbfGaussianParameters()
	{
	}

	public RbfGaussianParameters(double aMean[], double aStdDeviation)
	{
		theMean = aMean;
		theStdDeviation = aStdDeviation;
	}

	public double[] getMean()
	{
		return theMean;
	}

	public void setMean(double aMean[])
	{
		theMean = aMean;
	}

	public double getStdDeviation()
	{
		return theStdDeviation;
	}

	public void setStdDeviation(double aStdDeviation)
	{
		theStdDeviation = aStdDeviation;
	}
}
