// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DynamicAnnealing.java

package org.joone.util;

import org.joone.engine.Monitor;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.util:
//			MonitorPlugin

public class DynamicAnnealing extends MonitorPlugin
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/util/DynamicAnnealing);
	private double lastError;
	private double step;
	private static final long serialVersionUID = 0xb3c01773e7909bebL;

	public DynamicAnnealing()
	{
		lastError = 0.0D;
		step = 0.0D;
	}

	protected void manageCycle(Monitor mon)
	{
		double actError = mon.getGlobalError();
		if (actError > lastError && lastError > 0.0D && step > 0.0D)
		{
			double err = mon.getLearningRate() * (1.0D - step / 100D);
			mon.setLearningRate(err);
			int currentCycle = (mon.getTotCicles() - mon.getCurrentCicle()) + 1;
			log.info((new StringBuilder("DynamicAnnealing: changed the learning rate to ")).append(err).append(" at cycle n.").append(currentCycle).toString());
		}
		lastError = actError;
	}

	protected void manageStop(Monitor monitor)
	{
	}

	public double getStep()
	{
		return step;
	}

	public void setStep(double step)
	{
		this.step = step;
		if (step >= 100D)
			this.step = 99D;
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
