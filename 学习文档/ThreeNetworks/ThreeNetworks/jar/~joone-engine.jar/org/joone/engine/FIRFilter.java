// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FIRFilter.java

package org.joone.engine;

import java.io.Serializable;

// Referenced classes of package org.joone.engine:
//			Matrix

public class FIRFilter
	implements Serializable
{

	protected int m_taps;
	protected double memory[];
	protected double backmemory[];
	protected double outs[];
	protected double bouts[];
	protected Matrix array;
	public double lrate;
	public double momentum;
	private static final long serialVersionUID = 0x233d6e8ed7ac35fbL;

	public FIRFilter(int taps)
	{
		outs = new double[taps];
		bouts = new double[taps];
		memory = new double[taps];
		backmemory = new double[taps];
		array = new Matrix(taps, 1);
		m_taps = taps - 1;
	}

	public void addNoise(double amplitude)
	{
		array.addNoise(amplitude);
	}

	protected double backDelay(double pattern[])
	{
		for (int y = 0; y < m_taps; y++)
		{
			backmemory[y] = backmemory[y + 1];
			backmemory[y] += pattern[y];
		}

		backmemory[m_taps] = pattern[m_taps];
		return backmemory[0];
	}

	protected double[] backFilter(double input)
	{
		for (int x = 0; x <= m_taps; x++)
		{
			bouts[x] = input * array.value[x][0];
			double dw = lrate * input * outs[x] + momentum * array.delta[x][0];
			array.value[x][0] += dw;
			array.delta[x][0] = dw;
		}

		return bouts;
	}

	public double backward(double input)
	{
		return backDelay(backFilter(input));
	}

	protected double[] Delay(double input)
	{
		for (int y = m_taps; y > 0; y--)
		{
			memory[y] = memory[y - 1];
			outs[y] = memory[y];
		}

		memory[0] = input;
		outs[0] = input;
		return outs;
	}

	protected double Filter(double pattern[])
	{
		double s = 0.0D;
		for (int x = 0; x <= m_taps; x++)
			s += pattern[x] * array.value[x][0];

		return s;
	}

	public double forward(double input)
	{
		return Filter(Delay(input));
	}
}
