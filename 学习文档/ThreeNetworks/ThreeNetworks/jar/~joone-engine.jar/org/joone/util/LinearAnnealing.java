// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LinearAnnealing.java

package org.joone.util;

import org.joone.engine.Monitor;

// Referenced classes of package org.joone.util:
//			MonitorPlugin

public class LinearAnnealing extends MonitorPlugin
{

	private double learningRateInitial;
	private double learningRateFinal;
	private double momentumInitial;
	private double momentumFinal;
	private static final long serialVersionUID = 0xcb72afe395fd4471L;

	public LinearAnnealing()
	{
	}

	public double getLearningRateFinal()
	{
		return learningRateFinal;
	}

	public double getLearningRateInitial()
	{
		return learningRateInitial;
	}

	public double getMomentumFinal()
	{
		return momentumFinal;
	}

	public double getMomentumInitial()
	{
		return momentumInitial;
	}

	public void setLearningRateFinal(double newLearningRateFinal)
	{
		learningRateFinal = newLearningRateFinal;
	}

	public void setLearningRateInitial(double newLearningRateInitial)
	{
		learningRateInitial = newLearningRateInitial;
	}

	public void setMomentumFinal(double newMomentumFinal)
	{
		momentumFinal = newMomentumFinal;
	}

	public void setMomentumInitial(double newMomentumInitial)
	{
		momentumInitial = newMomentumInitial;
	}

	protected void manageCycle(Monitor mon)
	{
		double stepLR = (getLearningRateInitial() - getLearningRateFinal()) / (double)mon.getTotCicles();
		double stepMom = (getMomentumInitial() - getMomentumFinal()) / (double)mon.getTotCicles();
		int currCicle = mon.getTotCicles() - mon.getCurrentCicle();
		mon.setLearningRate(getLearningRateInitial() - stepLR * (double)currCicle);
		mon.setMomentum(getMomentumInitial() - stepMom * (double)currCicle);
	}

	protected void manageStop(Monitor monitor)
	{
	}

	protected void manageStart(Monitor monitor)
	{
	}

	protected void manageError(Monitor monitor)
	{
	}

	protected void manageStopError(Monitor monitor, String s)
	{
	}
}
