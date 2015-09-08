// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ErrorBasedConvergenceObserver.java

package org.joone.engine.listeners;

import org.joone.engine.Monitor;

// Referenced classes of package org.joone.engine.listeners:
//			ConvergenceObserver

public class ErrorBasedConvergenceObserver extends ConvergenceObserver
{

	private double percentage;
	private int cycles;
	private int cycleCounter;
	private double lastError;

	public ErrorBasedConvergenceObserver()
	{
		percentage = -1D;
		cycles = 5;
		cycleCounter = 0;
		lastError = -1D;
	}

	public void setPercentage(double aPercentage)
	{
		percentage = aPercentage;
	}

	public double getPercentage()
	{
		return percentage;
	}

	public void setCycles(int aCylces)
	{
		cycles = aCylces;
	}

	public int getCycles()
	{
		return cycles;
	}

	protected void manageStop(Monitor monitor)
	{
	}

	protected void manageCycle(Monitor monitor)
	{
	}

	protected void manageStart(Monitor monitor)
	{
	}

	protected void manageError(Monitor mon)
	{
		if (percentage < 0.0D || cycles <= 0)
			return;
		double myCurrentError = mon.getGlobalError();
		if (lastError >= 0.0D)
		{
			double myPercentage = ((lastError - myCurrentError) * 100D) / lastError;
			if (myPercentage <= percentage && myPercentage >= 0.0D)
			{
				cycleCounter++;
			} else
			{
				disableCurrentConvergence = false;
				cycleCounter = 0;
			}
			if (cycleCounter == cycles)
			{
				if (!disableCurrentConvergence)
					fireNetConverged(mon);
				cycleCounter = 0;
			}
		}
		lastError = myCurrentError;
	}

	protected void manageStopError(Monitor monitor, String s)
	{
	}
}
