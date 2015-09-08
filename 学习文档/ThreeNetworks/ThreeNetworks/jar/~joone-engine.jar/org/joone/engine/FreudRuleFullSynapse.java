// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FreudRuleFullSynapse.java

package org.joone.engine;


// Referenced classes of package org.joone.engine:
//			FullSynapse, Matrix, Learner

/**
 * @deprecated Class FreudRuleFullSynapse is deprecated
 */

public class FreudRuleFullSynapse extends FullSynapse
{

	private static final long serialVersionUID = 0x3cf1cd0839c1c2e3L;

	public FreudRuleFullSynapse()
	{
	}

	protected void backward(double pattern[])
	{
		int m_rows = getInputDimension();
		int m_cols = getOutputDimension();
		for (int x = 0; x < m_rows; x++)
		{
			double s = 0.0D;
			double fr = 1 - (m_rows - x - 1) / m_rows;
			fr *= getLearningRate();
			setLearningRate(fr);
			for (int y = 0; y < m_cols; y++)
				s += pattern[y] * array.value[x][y];

			bouts[x] = s;
		}

		myLearner.requestWeightUpdate(pattern, inps);
	}
}
