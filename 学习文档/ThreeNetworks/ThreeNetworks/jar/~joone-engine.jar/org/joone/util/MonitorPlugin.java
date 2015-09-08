// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MonitorPlugin.java

package org.joone.util;

import java.io.Serializable;
import org.joone.engine.*;
import org.joone.net.NeuralNet;

public abstract class MonitorPlugin
	implements Serializable, NeuralNetListener
{

	private String name;
	private static final long serialVersionUID = 0xd32e9761ebff098L;
	private int rate;
	private NeuralNet neuralNet;

	public MonitorPlugin()
	{
		rate = 1;
	}

	public void cicleTerminated(NeuralNetEvent e)
	{
		Monitor mon = (Monitor)e.getSource();
		if (toBeManaged(mon))
			manageCycle(mon);
	}

	public void netStopped(NeuralNetEvent e)
	{
		Monitor mon = (Monitor)e.getSource();
		manageStop(mon);
	}

	public void netStarted(NeuralNetEvent e)
	{
		Monitor mon = (Monitor)e.getSource();
		manageStart(mon);
	}

	public void errorChanged(NeuralNetEvent e)
	{
		Monitor mon = (Monitor)e.getSource();
		if (toBeManaged(mon))
			manageError(mon);
	}

	public void netStoppedError(NeuralNetEvent e, String error)
	{
		Monitor mon = (Monitor)e.getSource();
		manageStopError(mon, error);
	}

	protected boolean toBeManaged(Monitor monitor)
	{
		if (getRate() == 0)
			return false;
		int currentCycle = (monitor.getTotCicles() - monitor.getCurrentCicle()) + 1;
		int cl = currentCycle / getRate();
		return cl * getRate() == currentCycle;
	}

	protected abstract void manageStop(Monitor monitor);

	protected abstract void manageCycle(Monitor monitor);

	protected abstract void manageStart(Monitor monitor);

	protected abstract void manageError(Monitor monitor);

	protected abstract void manageStopError(Monitor monitor, String s);

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getRate()
	{
		return rate;
	}

	public void setRate(int rate)
	{
		this.rate = rate;
	}

	public NeuralNet getNeuralNet()
	{
		return neuralNet;
	}

	public void setNeuralNet(NeuralNet neuralNet)
	{
		this.neuralNet = neuralNet;
	}
}
