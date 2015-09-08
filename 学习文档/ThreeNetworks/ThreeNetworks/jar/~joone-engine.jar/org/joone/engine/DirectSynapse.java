// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DirectSynapse.java

package org.joone.engine;

import java.util.TreeSet;
import org.joone.net.NetCheck;

// Referenced classes of package org.joone.engine:
//			Synapse, Pattern

public class DirectSynapse extends Synapse
{

	private static final long serialVersionUID = 0x2abdfef3ac138296L;

	public DirectSynapse()
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
		if (rows > -1)
			setArrays(rows, rows);
		else
		if (cols > -1)
			setArrays(cols, cols);
	}

	public void revPut(Pattern pattern1)
	{
	}

	public Pattern revGet()
	{
		return null;
	}

	public TreeSet check()
	{
		TreeSet checks = super.check();
		if (getInputDimension() != getOutputDimension())
			checks.add(new NetCheck(0, "Connected layers are not the same size.", this));
		return checks;
	}
}
