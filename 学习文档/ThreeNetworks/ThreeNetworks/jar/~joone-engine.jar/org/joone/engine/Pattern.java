// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Pattern.java

package org.joone.engine;

import java.io.Serializable;

public class Pattern
	implements Cloneable, Serializable
{

	private int count;
	private double array[];
	private double outArray[];
	private static final long serialVersionUID = 0xf7899a5392b2c78eL;

	public Pattern()
	{
	}

	public Pattern(double arr[])
	{
		array = arr;
	}

	public Pattern(double singleValue)
	{
		array = (new double[] {
			singleValue
		});
	}

	public static Pattern makeZeroPattern(int dim)
	{
		return new Pattern(new double[dim]);
	}

	public static Pattern makeStopPattern(int dim)
	{
		Pattern p = makeZeroPattern(dim);
		p.markAsStoppingPattern();
		return p;
	}

	public synchronized Object clone()
	{
		Pattern cPat;
		try
		{
			cPat = (Pattern)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			cPat = new Pattern();
			cPat.count = count;
		}
		cPat.array = (double[])array.clone();
		if (outArray != null)
			cPat.setOutArray((double[])outArray.clone());
		return cPat;
	}

	public synchronized double[] getArray()
	{
		return array;
	}

	public synchronized int getCount()
	{
		return count;
	}

	public synchronized void setArray(double arr[])
	{
		array = (double[])arr.clone();
	}

	public synchronized void setCount(int n)
	{
		count = n;
	}

	public void markAsStoppingPattern()
	{
		setCount(-1);
	}

	public boolean isMarkedAsStoppingPattern()
	{
		return getCount() == -1;
	}

	public void setValue(int point, double value)
	{
		array[point] = value;
	}

	public double[] getOutArray()
	{
		return (double[])outArray.clone();
	}

	public void setOutArray(double outArray[])
	{
		this.outArray = (double[])outArray.clone();
	}

	public double[] getValues()
	{
		return array;
	}

	public void setValues(double values[])
	{
		array = values;
	}
}
