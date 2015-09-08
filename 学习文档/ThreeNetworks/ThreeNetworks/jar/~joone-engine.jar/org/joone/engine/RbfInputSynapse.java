// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RbfInputSynapse.java

package org.joone.engine;


// Referenced classes of package org.joone.engine:
//			Synapse

public class RbfInputSynapse extends Synapse
{

	public RbfInputSynapse()
	{
	}

	protected void backward(double ad[])
	{
	}

	protected void forward(double pattern[])
	{
		outs = pattern;
	}

	protected void setArrays(int rows, int cols)
	{
		inps = new double[rows];
		outs = new double[rows];
		bouts = new double[rows];
	}

	protected void setDimensions(int rows, int cols)
	{
		if (rows == -1)
			rows = getInputDimension();
		if (cols == -1)
			cols = getOutputDimension();
		setArrays(rows, cols);
	}
}
