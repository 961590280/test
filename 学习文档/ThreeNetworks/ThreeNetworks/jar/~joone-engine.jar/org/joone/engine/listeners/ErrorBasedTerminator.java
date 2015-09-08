// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ErrorBasedTerminator.java

package org.joone.engine.listeners;

import org.joone.engine.Monitor;
import org.joone.net.NeuralNet;
import org.joone.util.MonitorPlugin;

public class ErrorBasedTerminator extends MonitorPlugin
{

	private double errorLevel;
	private int stoppedCycle;
	private boolean stopRequested;

	public ErrorBasedTerminator()
	{
		stoppedCycle = -1;
		stopRequested = false;
	}

	public ErrorBasedTerminator(double anErrorLevel)
	{
		stoppedCycle = -1;
		stopRequested = false;
		errorLevel = anErrorLevel;
	}

	public void setErrorLevel(double anErrorLevel)
	{
		errorLevel = anErrorLevel;
	}

	public double getErrorLevel()
	{
		return errorLevel;
	}

	protected void manageStop(Monitor monitor)
	{
	}

	protected void manageCycle(Monitor monitor)
	{
	}

	protected void manageStart(Monitor mon)
	{
		stoppedCycle = -1;
		stopRequested = false;
	}

	protected void manageError(Monitor mon)
	{
		if (mon.getGlobalError() <= errorLevel)
		{
			if (!isStopRequestPerformed())
			{
				stoppedCycle = (mon.getTotCicles() - mon.getCurrentCicle()) + 1;
				stopRequested = true;
			}
			getNeuralNet().stop();
		}
	}

	protected void manageStopError(Monitor monitor, String s)
	{
	}

	public int getStoppedCycle()
	{
		return stoppedCycle;
	}

	public boolean isStopRequestPerformed()
	{
		return stopRequested;
	}
}
