// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LinearLayer.java

package org.joone.engine;

import java.util.ArrayList;
import java.util.Collection;
import org.joone.inspection.implementations.BiasInspection;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.engine:
//			SimpleLayer

public class LinearLayer extends SimpleLayer
{

	private double beta;
	private static final ILogger log = LoggerFactory.getLogger(org/joone/engine/LinearLayer);
	private static final long serialVersionUID = 0x1f211ffd9ee950c8L;

	public LinearLayer()
	{
		beta = 1.0D;
	}

	public LinearLayer(String ElemName)
	{
		super(ElemName);
		beta = 1.0D;
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
			outs[x] = beta * pattern[x];

	}

	public double getBeta()
	{
		return beta;
	}

	public void setBeta(double newBeta)
	{
		beta = newBeta;
	}

	public Collection Inspections()
	{
		Collection col = new ArrayList();
		col.add(new BiasInspection(null));
		return col;
	}

}
