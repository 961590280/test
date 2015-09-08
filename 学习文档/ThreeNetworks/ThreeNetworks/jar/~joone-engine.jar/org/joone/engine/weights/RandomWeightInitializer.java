// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RandomWeightInitializer.java

package org.joone.engine.weights;

import org.joone.engine.Matrix;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.engine.weights:
//			WeightInitializer

public class RandomWeightInitializer
	implements WeightInitializer
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/engine/weights/RandomWeightInitializer);
	private static final long serialVersionUID = 0x157aa55b889e031dL;
	private double upperBound;
	private double lowerBound;

	public RandomWeightInitializer(double aBoundary)
	{
		upperBound = 0.0D;
		lowerBound = 0.0D;
		if (aBoundary < 0.0D)
		{
			log.warn((new StringBuilder("Boundary smaller than zero. Domain set to [")).append(aBoundary).append(", ").append(-aBoundary).append("].").toString());
			aBoundary = Math.abs(aBoundary);
		}
		upperBound = aBoundary;
		lowerBound = -aBoundary;
	}

	public RandomWeightInitializer(double aLowerBound, double anUpperBound)
	{
		upperBound = 0.0D;
		lowerBound = 0.0D;
		if (aLowerBound > anUpperBound)
		{
			log.warn((new StringBuilder("Lower bound is larger than upper bound. Domain set to [")).append(anUpperBound).append(", ").append(aLowerBound).append("].").toString());
			upperBound = aLowerBound;
			lowerBound = anUpperBound;
		} else
		{
			upperBound = anUpperBound;
			lowerBound = aLowerBound;
		}
	}

	public void initialize(Matrix aMatrix)
	{
		for (int x = 0; x < aMatrix.getM_rows(); x++)
		{
			for (int y = 0; y < aMatrix.getM_cols(); y++)
				if (aMatrix.enabled[x][y] && !aMatrix.fixed[x][y])
					aMatrix.value[x][y] = lowerBound + Math.random() * (upperBound - lowerBound);

		}

	}

	public double getLowerBound()
	{
		return lowerBound;
	}

	public void setLowerBound(double aLowerBound)
	{
		lowerBound = aLowerBound;
	}

	public double getUpperBound()
	{
		return upperBound;
	}

	public void setUpperBound(double anUpperBound)
	{
		upperBound = anUpperBound;
	}

}
