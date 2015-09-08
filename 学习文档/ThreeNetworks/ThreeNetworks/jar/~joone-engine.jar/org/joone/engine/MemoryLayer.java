// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MemoryLayer.java

package org.joone.engine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.TreeSet;

// Referenced classes of package org.joone.engine:
//			Layer, OutputPatternListener

public abstract class MemoryLayer extends Layer
{

	protected double memory[];
	protected double backmemory[];
	private int taps;
	private static final long serialVersionUID = 0x4b9a64ef5935a314L;

	public MemoryLayer()
	{
		taps = 0;
	}

	public MemoryLayer(String ElemName)
	{
		super(ElemName);
		taps = 0;
	}

	public int getDimension()
	{
		return getRows() * (getTaps() + 1);
	}

	public int getTaps()
	{
		return taps;
	}

	protected void setDimensions()
	{
		inps = new double[getRows()];
		outs = new double[getRows() * (getTaps() + 1)];
		gradientInps = new double[getRows() * (getTaps() + 1)];
		gradientOuts = new double[getRows()];
		memory = new double[getRows() * (getTaps() + 1)];
		backmemory = new double[getRows() * (getTaps() + 1)];
	}

	protected void setOutputDimension(OutputPatternListener syn)
	{
		int n = getRows() * (getTaps() + 1);
		if (syn.getInputDimension() != n)
			syn.setInputDimension(n);
	}

	public void setTaps(int newTaps)
	{
		taps = newTaps;
		setDimensions();
		setConnDimensions();
	}

	protected void sumBackInput(double pattern[])
	{
		int length = getRows() * (getTaps() + 1);
		for (int x = 0; x < length; x++)
			gradientInps[x] += pattern[x];

	}

	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		setDimensions();
	}

	protected void backward(double ad[])
	{
	}

	protected void forward(double ad[])
	{
	}

	public TreeSet check()
	{
		return super.check();
	}
}
