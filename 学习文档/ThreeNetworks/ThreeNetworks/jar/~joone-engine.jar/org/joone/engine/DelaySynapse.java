// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DelaySynapse.java

package org.joone.engine;

import java.util.TreeSet;
import org.joone.net.NetCheck;

// Referenced classes of package org.joone.engine:
//			Synapse, FIRFilter

public class DelaySynapse extends Synapse
{

	protected FIRFilter fir[][];
	private int taps;
	private static final long serialVersionUID = 0x72be4b8508335374L;

	public DelaySynapse()
	{
	}

	public void addNoise(double amplitude)
	{
		int m_cols = getOutputDimension();
		int m_rows = getInputDimension();
		for (int y = 0; y < m_cols; y++)
		{
			for (int x = 0; x < m_rows; x++)
				fir[x][y].addNoise(amplitude);

		}

	}

	protected void backward(double pattern[])
	{
		int m_rows = getInputDimension();
		int m_cols = getOutputDimension();
		for (int x = 0; x < m_rows; x++)
		{
			double sum = 0.0D;
			for (int y = 0; y < m_cols; y++)
			{
				fir[x][y].lrate = getLearningRate();
				fir[x][y].momentum = getMomentum();
				sum += fir[x][y].backward(pattern[y]);
			}

			bouts[x] = sum;
		}

	}

	protected void forward(double pattern[])
	{
		int m_rows = getInputDimension();
		int m_cols = getOutputDimension();
		for (int y = 0; y < m_cols; y++)
		{
			double sum = 0.0D;
			for (int x = 0; x < m_rows; x++)
				sum += fir[x][y].forward(pattern[x]);

			outs[y] = sum;
		}

	}

	public int getTaps()
	{
		return taps;
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
		fir = new FIRFilter[irows][icols];
		for (int x = 0; x < irows; x++)
		{
			for (int y = 0; y < icols; y++)
				fir[x][y] = new FIRFilter(getTaps());

		}

		setArrays(irows, icols);
	}

	public void setTaps(int newTaps)
	{
		taps = newTaps;
		setDimensions(-1, -1);
	}

	public TreeSet check()
	{
		TreeSet checks = super.check();
		if (getTaps() == 0)
			checks.add(new NetCheck(0, "The Taps parameter cannot be equal to zero.", this));
		return checks;
	}
}
