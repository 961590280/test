// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ParityInputSynapse.java

package org.joone.samples.util;

import org.joone.exception.JooneRuntimeException;
import org.joone.io.MemoryInputTokenizer;
import org.joone.io.StreamInputSynapse;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

public class ParityInputSynapse extends StreamInputSynapse
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/samples/util/ParityInputSynapse);
	private int paritySize;
	private double upperBit;
	private double lowerBit;

	public ParityInputSynapse()
	{
		paritySize = 2;
		upperBit = 1.0D;
		lowerBit = 0.0D;
	}

	protected void initInputStream()
		throws JooneRuntimeException
	{
		setAdvancedColumnSelector((new StringBuilder("1-")).append(paritySize + 1).toString());
		setTokens(new MemoryInputTokenizer(createParityArray()));
	}

	public void setParitySize(int aSize)
	{
		paritySize = aSize;
	}

	public int getParitySize()
	{
		return paritySize;
	}

	public int getNumOfPatterns()
	{
		return (int)Math.pow(2D, paritySize);
	}

	protected double[][] createParityArray()
	{
		int myPatterns = getNumOfPatterns();
		double myParityInstance[][] = new double[myPatterns][paritySize + 1];
		for (int i = 0; i < myPatterns; i++)
		{
			int myDesiredOutput = 0;
			int myTemp = i;
			for (int j = 0; j < paritySize; j++)
			{
				int myBit = myTemp % 2;
				myTemp /= 2;
				if (myBit == 1)
					myDesiredOutput = (myDesiredOutput + 1) % 2;
				if (myBit == 0)
					myParityInstance[i][j] = getLowerBit();
				else
					myParityInstance[i][j] = getUpperBit();
			}

			if (myDesiredOutput == 0)
				myParityInstance[i][paritySize] = getLowerBit();
			else
				myParityInstance[i][paritySize] = getUpperBit();
		}

		return myParityInstance;
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
