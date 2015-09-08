// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ToBinaryPlugin.java

package org.joone.util;

import java.util.*;
import org.joone.engine.Pattern;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.util:
//			ConverterPlugIn

public class ToBinaryPlugin extends ConverterPlugIn
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/util/ToBinaryPlugin);
	private List theConvertedSeries;
	private double upperBit;
	private double lowerBit;

	public ToBinaryPlugin()
	{
		theConvertedSeries = new ArrayList();
		upperBit = 1.0D;
		lowerBit = 0.0D;
	}

	public ToBinaryPlugin(String anAdvancedSerieSelector)
	{
		super(anAdvancedSerieSelector);
		theConvertedSeries = new ArrayList();
		upperBit = 1.0D;
		lowerBit = 0.0D;
	}

	protected boolean convert(int serie)
	{
		boolean retValue = false;
		int mySerie = serie;
		boolean myHasPositiveValues = false;
		boolean myHasNegativeValues = false;
		for (int i = 0; i < theConvertedSeries.size(); i++)
			if (((int[])theConvertedSeries.get(i))[0] < serie)
				mySerie += ((int[])theConvertedSeries.get(i))[1];

		int mySize = 0;
		double myBinaries[][] = new double[getInputVector().size()][];
		for (int i = 0; i < getInputVector().size(); i++)
		{
			double myArray[] = ((Pattern)getInputVector().get(i)).getArray();
			myBinaries[i] = getBinary(myArray[mySerie]);
			if (myBinaries[i].length > mySize)
				mySize = myBinaries[i].length;
			if (myArray[mySerie] > 0.0D)
				myHasPositiveValues = true;
			else
			if (myArray[mySerie] < 0.0D)
				myHasNegativeValues = true;
		}

		int mySignBitLenght = !myHasPositiveValues || !myHasNegativeValues ? 0 : 1;
		for (int i = 0; i < getInputVector().size(); i++)
		{
			double myArray[] = ((Pattern)getInputVector().get(i)).getArray();
			double myNewArray[] = new double[(myArray.length - 1) + mySize + mySignBitLenght];
			for (int j = 0; j < myArray.length; j++)
				if (j < mySerie)
					myNewArray[j] = myArray[j];
				else
				if (j > mySerie)
					myNewArray[(j + mySize + mySignBitLenght) - 1] = myArray[j];

			for (int j = 0; j < mySize + mySignBitLenght; j++)
				if (j >= myBinaries[i].length)
				{
					myNewArray[mySerie + j] = getLowerBit();
					if (j == mySize && myArray[mySerie] < 0.0D)
						myNewArray[mySerie + j] = getUpperBit();
				} else
				{
					myNewArray[mySerie + j] = myBinaries[i][j];
				}

			((Pattern)getInputVector().get(i)).setArray(myNewArray);
			retValue = true;
		}

		theConvertedSeries.add(new int[] {
			serie, (mySize + mySignBitLenght) - 1
		});
		return retValue;
	}

	protected boolean apply()
	{
		theConvertedSeries = new ArrayList();
		return super.apply();
	}

	protected double[] getBinary(double aNumber)
	{
		aNumber = Math.floor(aNumber);
		aNumber = Math.abs(aNumber);
		double myTemp = aNumber;
		int mySize = 0;
		for (; myTemp > 0.0D; myTemp = Math.floor(myTemp))
		{
			mySize++;
			myTemp /= 2D;
		}

		double myBinary[] = new double[mySize];
		for (int i = 0; i < mySize; i++)
		{
			myTemp = aNumber / 2D;
			aNumber = Math.floor(myTemp);
			if (myTemp > aNumber)
				myBinary[i] = getUpperBit();
			else
				myBinary[i] = getLowerBit();
		}

		return myBinary;
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
