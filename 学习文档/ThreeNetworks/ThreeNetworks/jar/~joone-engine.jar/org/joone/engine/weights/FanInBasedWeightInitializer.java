// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FanInBasedWeightInitializer.java

package org.joone.engine.weights;

import org.joone.engine.Matrix;

// Referenced classes of package org.joone.engine.weights:
//			WeightInitializer

public class FanInBasedWeightInitializer
	implements WeightInitializer
{

	private double lowerBound;
	private double upperBound;
	private boolean sqrtFanIn;

	public FanInBasedWeightInitializer()
	{
		lowerBound = -2.3999999999999999D;
		upperBound = 2.3999999999999999D;
		sqrtFanIn = false;
	}

	public FanInBasedWeightInitializer(double aBoundary)
	{
		lowerBound = -2.3999999999999999D;
		upperBound = 2.3999999999999999D;
		sqrtFanIn = false;
		lowerBound = -aBoundary;
		upperBound = aBoundary;
	}

	public FanInBasedWeightInitializer(double aLowerBound, double anUpperBound)
	{
		lowerBound = -2.3999999999999999D;
		upperBound = 2.3999999999999999D;
		sqrtFanIn = false;
		lowerBound = aLowerBound;
		upperBound = anUpperBound;
	}

	public void initialize(Matrix aMatrix)
	{
		int m_rows = aMatrix.getM_rows();
		double divVal = isSqrtFanIn() ? Math.sqrt(m_rows) : m_rows;
		for (int x = 0; x < m_rows; x++)
		{
			for (int y = 0; y < aMatrix.getM_cols(); y++)
				if (aMatrix.enabled[x][y] && !aMatrix.fixed[x][y])
					aMatrix.value[x][y] = lowerBound / divVal + Math.random() * ((upperBound - lowerBound) / divVal);

		}

	}

	public void setSqrtFanIn(boolean aMode)
	{
		sqrtFanIn = aMode;
	}

	public boolean isSqrtFanIn()
	{
		return sqrtFanIn;
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
