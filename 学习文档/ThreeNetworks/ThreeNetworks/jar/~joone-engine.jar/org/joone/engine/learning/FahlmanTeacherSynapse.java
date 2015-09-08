// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FahlmanTeacherSynapse.java

package org.joone.engine.learning;

import org.joone.engine.Monitor;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.engine.learning:
//			TeacherSynapse

public class FahlmanTeacherSynapse extends TeacherSynapse
{

	public static final String CRITERION = "FAHLMAN_CRITERION";
	protected static final ILogger log = LoggerFactory.getLogger(org/joone/engine/learning/FahlmanTeacherSynapse);
	private double upperBit;
	private double lowerBit;
	private double lowerBitPercentage;
	private double upperBitPercentage;

	public FahlmanTeacherSynapse()
	{
		upperBit = 1.0D;
		lowerBit = 0.0D;
		lowerBitPercentage = 0.40000000000000002D;
		upperBitPercentage = 0.40000000000000002D;
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

	public void setUpperBitPercentage(double aValue)
	{
		upperBitPercentage = aValue;
	}

	public double getUpperBitPercentage()
	{
		return upperBitPercentage;
	}

	public void setLowerBitPercentage(double aValue)
	{
		lowerBitPercentage = aValue;
	}

	public double getLowerBitPercentage()
	{
		return lowerBitPercentage;
	}

	protected double calculateError(double aDesired, double anOutput, int anIndex)
	{
		if (getMonitor().isValidation())
		{
			double myRange = upperBit - lowerBit;
			if (aDesired == lowerBit)
			{
				myRange *= lowerBitPercentage;
				if (anOutput < lowerBit || anOutput > lowerBit + myRange)
					getMonitor().setParam("FAHLMAN_CRITERION", Boolean.FALSE);
			} else
			if (aDesired == upperBit)
			{
				myRange *= upperBitPercentage;
				if (anOutput < upperBit - myRange || anOutput > upperBit)
					getMonitor().setParam("FAHLMAN_CRITERION", Boolean.FALSE);
			} else
			{
				warnLogger((new StringBuilder("The values for upper and/or lower bit are not correctly set. No match for desired output ")).append(aDesired).append(".").toString(), log);
				getMonitor().setParam("FAHLMAN_CRITERION", Boolean.FALSE);
			}
		}
		return super.calculateError(aDesired, anOutput, anIndex);
	}

}
