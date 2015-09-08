// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SangerSynapse.java

package org.joone.engine;


// Referenced classes of package org.joone.engine:
//			FullSynapse, Pattern, Matrix, Learner

public class SangerSynapse extends FullSynapse
{

	private static final long serialVersionUID = 0x13aa7febba215a39L;

	public SangerSynapse()
	{
		learnable = false;
	}

	protected void backward(double pattern[])
	{
		double outArray[] = b_pattern.getOutArray();
		int m_rows = getInputDimension();
		int m_cols = getOutputDimension();
		for (int x = 0; x < m_rows; x++)
		{
			double s = 0.0D;
			for (int y = 0; y < m_cols; y++)
			{
				s += array.value[x][y] * outArray[y];
				double dw = getLearningRate() * outArray[y];
				dw *= inps[x] - s;
				array.value[x][y] += dw;
				array.delta[x][y] = dw;
			}

		}

	}

	/**
	 * @deprecated Method getLearner is deprecated
	 */

	public Learner getLearner()
	{
		learnable = false;
		return super.getLearner();
	}
}
