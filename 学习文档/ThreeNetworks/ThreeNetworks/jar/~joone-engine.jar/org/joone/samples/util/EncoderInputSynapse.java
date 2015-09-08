// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EncoderInputSynapse.java

package org.joone.samples.util;

import org.joone.exception.JooneRuntimeException;
import org.joone.io.MemoryInputTokenizer;
import org.joone.io.StreamInputSynapse;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

public class EncoderInputSynapse extends StreamInputSynapse
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/samples/util/EncoderInputSynapse);
	private int size;
	private double upperBit;
	private double lowerBit;

	public EncoderInputSynapse()
	{
		size = 0;
		upperBit = 1.0D;
		lowerBit = 0.0D;
	}

	public EncoderInputSynapse(double aLowerBit, double anUpperBit)
	{
		size = 0;
		upperBit = 1.0D;
		lowerBit = 0.0D;
		lowerBit = aLowerBit;
		upperBit = anUpperBit;
	}

	protected void initInputStream()
		throws JooneRuntimeException
	{
		setAdvancedColumnSelector((new StringBuilder("1-")).append(size).toString());
		setTokens(new MemoryInputTokenizer(createEncoderArray()));
	}

	public void setSize(int aSize)
	{
		size = aSize;
	}

	public int getSize()
	{
		return size;
	}

	public int getNumOfPatterns()
	{
		return size;
	}

	protected double[][] createEncoderArray()
	{
		int myPatterns = getNumOfPatterns();
		double myInstance[][] = new double[myPatterns][getSize()];
		for (int i = 0; i < myPatterns; i++)
		{
			for (int j = 0; j < getSize(); j++)
				myInstance[i][j] = getLowerBit();

			myInstance[i][i] = getUpperBit();
		}

		return myInstance;
	}

	public void setUpperBit(double aValue)
	{
		upperBit = aValue;
	}

	public double getUpperBit()
	{
		return upperBit;
	}

	public void setLowerBit(double aValue)
	{
		lowerBit = aValue;
	}

	public double getLowerBit()
	{
		return lowerBit;
	}

}
