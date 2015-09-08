// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ContextLayer.java

package org.joone.engine;

import java.util.ArrayList;
import java.util.Collection;
import org.joone.inspection.implementations.BiasInspection;

// Referenced classes of package org.joone.engine:
//			SimpleLayer

public class ContextLayer extends SimpleLayer
{

	private double beta;
	private double timeConstant;
	private static final long serialVersionUID = 0x863d3280cb926194L;

	public ContextLayer()
	{
		beta = 1.0D;
		timeConstant = 0.5D;
	}

	public ContextLayer(String name)
	{
		super(name);
		beta = 1.0D;
		timeConstant = 0.5D;
	}

	public void backward(double pattern[])
	{
		int n = getRows();
		for (int x = 0; x < n; x++)
			gradientOuts[x] = pattern[x] * beta;

	}

	public void forward(double pattern[])
	{
		int n = getRows();
		for (int x = 0; x < n; x++)
			outs[x] = beta * (pattern[x] + timeConstant * outs[x]);

	}

	public double getBeta()
	{
		return beta;
	}

	public void setBeta(double beta)
	{
		this.beta = beta;
	}

	public double getTimeConstant()
	{
		return timeConstant;
	}

	public void setTimeConstant(double timeConstant)
	{
		this.timeConstant = timeConstant;
	}

	public Collection Inspections()
	{
		Collection col = new ArrayList();
		col.add(new BiasInspection(null));
		return col;
	}
}
