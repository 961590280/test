// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FullSynapse.java

package org.joone.engine;


// Referenced classes of package org.joone.engine:
//			Synapse, LearnableSynapse, Matrix, Learner

public class FullSynapse extends Synapse
	implements LearnableSynapse
{

	private static final long serialVersionUID = 0x4c9710953001c312L;

	public FullSynapse()
	{
		learnable = true;
	}

	protected void backward(double pattern[])
	{
		int m_rows = getInputDimension();
		int m_cols = getOutputDimension();
		for (int x = 0; x < m_rows; x++)
		{
			double s = 0.0D;
			for (int y = 0; y < m_cols; y++)
				s += pattern[y] * array.value[x][y];

			bouts[x] = s;
		}

		myLearner.requestWeightUpdate(pattern, inps);
	}

	protected void forward(double pattern[])
	{
		int m_rows = getInputDimension();
		int m_cols = getOutputDimension();
		for (int y = 0; y < m_cols; y++)
		{
			double s = 0.0D;
			for (int x = 0; x < m_rows; x++)
				s += pattern[x] * array.value[x][y];

			outs[y] = s;
		}

	}

	protected void setArrays(int rows, int cols)
	{
		inps = new double[rows];
		outs = new double[cols];
		bouts = new double[rows];
	}

	protected void setDimensions(int rows, int cols)
	{
		int m_rows = getInputDimension();
		int m_cols = getOutputDimension();
		int irows;
		if (rows == -1)
			irows = m_rows;
		else
			irows = rows;
		int icols;
		if (cols == -1)
			icols = m_cols;
		else
			icols = cols;
		array = new Matrix(irows, icols);
		setArrays(irows, icols);
	}

	/**
	 * @deprecated Method getLearner is deprecated
	 */

	public Learner getLearner()
	{
		learnable = true;
		return super.getLearner();
	}
}
