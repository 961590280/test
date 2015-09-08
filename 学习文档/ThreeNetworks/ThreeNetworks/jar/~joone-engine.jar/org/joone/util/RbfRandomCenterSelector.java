// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RbfRandomCenterSelector.java

package org.joone.util;

import java.util.Vector;
import org.joone.engine.*;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.util:
//			ConverterPlugIn

public class RbfRandomCenterSelector extends ConverterPlugIn
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/util/RbfRandomCenterSelector);
	private RbfGaussianLayer theRbfGaussianLayer;
	private Vector thePatterns;

	public RbfRandomCenterSelector(RbfGaussianLayer aRbfGaussianLayer)
	{
		thePatterns = null;
		theRbfGaussianLayer = aRbfGaussianLayer;
		setAdvancedSerieSelector("1");
	}

	protected boolean convert(int serie)
	{
		if (thePatterns == null)
			thePatterns = getInputVector();
		return false;
	}

	public RbfGaussianParameters[] getGaussianParameters()
	{
		if (thePatterns.size() < theRbfGaussianLayer.getRows())
			log.warn("There are more neurons in RBF layer than training patterns -> not all nodes in RBF layer will be assigned a unique center.");
		int myCenters[] = new int[theRbfGaussianLayer.getRows()];
		for (int i = 0; i < theRbfGaussianLayer.getRows(); i++)
		{
			int myCenter = (int)(Math.random() * (double)thePatterns.size());
			if (i < thePatterns.size())
			{
				boolean myNonSelected = true;
				do
				{
					if (!myNonSelected)
					{
						myCenter = (int)(Math.random() * (double)thePatterns.size());
						myNonSelected = true;
					}
					for (int j = 0; j < i; j++)
						if (myCenters[j] == myCenter)
							myNonSelected = false;

				} while (!myNonSelected);
			}
			myCenters[i] = myCenter;
		}

		double myD = getMaxDistance(thePatterns, myCenters);
		double myStdDeviation = myD / Math.sqrt(2 * theRbfGaussianLayer.getRows());
		RbfGaussianParameters myParameters[] = new RbfGaussianParameters[theRbfGaussianLayer.getRows()];
		for (int i = 0; i < theRbfGaussianLayer.getRows(); i++)
		{
			double myCenter[] = (double[])((Pattern)thePatterns.get(myCenters[i])).getArray().clone();
			myParameters[i] = new RbfGaussianParameters(myCenter, myStdDeviation);
		}

		return myParameters;
	}

	protected double getMaxDistance(Vector aPatterns, int anIndexes[])
	{
		double myMax = -1D;
		for (int i = 0; i < anIndexes.length - 1; i++)
		{
			for (int j = i + 1; j < anIndexes.length; j++)
			{
				double myDistance = getDistance((Pattern)aPatterns.get(anIndexes[i]), (Pattern)aPatterns.get(anIndexes[j]));
				if (myDistance > myMax)
					myMax = myDistance;
			}

		}

		return myMax;
	}

	protected double getDistance(Pattern aCenter1, Pattern aCenter2)
	{
		double myDistance = 0.0D;
		for (int i = 0; i < aCenter1.getArray().length; i++)
		{
			double myDiff = aCenter1.getArray()[i] - aCenter2.getArray()[i];
			myDistance += myDiff * myDiff;
		}

		return Math.sqrt(myDistance);
	}

}
