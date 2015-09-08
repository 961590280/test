// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SimpleLayer.java

package org.joone.engine;

import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.engine:
//			Layer, Monitor

public abstract class SimpleLayer extends Layer
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/engine/SimpleLayer);
	private double lrate;
	private double momentum;
	private static final long serialVersionUID = 0xdc354a3ce613ead1L;

	public SimpleLayer()
	{
	}

	public SimpleLayer(String ElemName)
	{
		super(ElemName);
	}

	protected void backward(double parm1[])
	{
		if (monitor != null)
		{
			lrate = monitor.getLearningRate();
			momentum = monitor.getMomentum();
		}
	}

	public double getLearningRate()
	{
		return lrate;
	}

	public double getMomentum()
	{
		return momentum;
	}

	protected void setDimensions()
	{
		inps = new double[getRows()];
		outs = new double[getRows()];
		gradientInps = new double[getRows()];
		gradientOuts = new double[getRows()];
	}

	public void setMonitor(Monitor parm1)
	{
		super.setMonitor(parm1);
		if (parm1 != null)
		{
			lrate = monitor.getLearningRate();
			momentum = monitor.getMomentum();
		}
	}

	public double getLrate()
	{
		return lrate;
	}

	public void setLrate(double newLrate)
	{
		lrate = newLrate;
	}

	public void setMomentum(double newMomentum)
	{
		momentum = newMomentum;
	}

}
