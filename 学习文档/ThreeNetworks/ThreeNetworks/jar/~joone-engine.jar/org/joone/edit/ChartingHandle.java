// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ChartingHandle.java

package org.joone.edit;

import java.io.Serializable;
import java.util.TreeSet;
import org.joone.engine.*;
import org.joone.net.NetCheck;

// Referenced classes of package org.joone.edit:
//			ChartInterface

public class ChartingHandle
	implements OutputPatternListener, Serializable
{

	private ChartInterface chartSynapse;
	private int serie;
	private int RedColor;
	private int GreenColor;
	private int BlueColor;
	private String name;
	private Monitor mon;
	private int inputDimension;
	private boolean outputFull;
	private boolean enabled;
	private static final long serialVersionUID = 0x524032853b77b39L;

	public ChartingHandle()
	{
		serie = 1;
		RedColor = 0;
		GreenColor = 0;
		BlueColor = 200;
		enabled = true;
		chartSynapse = null;
		mon = null;
		inputDimension = 0;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setInputDimension(int newInputDimension)
	{
		inputDimension = newInputDimension;
		if (chartSynapse != null)
			chartSynapse.setInputDimension(newInputDimension);
	}

	public int getInputDimension()
	{
		return inputDimension;
	}

	public Monitor getMonitor()
	{
		return mon;
	}

	public void setMonitor(Monitor newMonitor)
	{
		mon = newMonitor;
		if (chartSynapse != null)
			chartSynapse.setMonitor(newMonitor);
	}

	protected void backward(double ad[])
	{
	}

	protected void forward(double ad[])
	{
	}

	public void fwdPut(Pattern pattern)
	{
		if (isEnabled() && chartSynapse != null)
		{
			if (getSerie() > pattern.getArray().length)
			{
				new NetErrorManager(getMonitor(), (new StringBuilder("ChartingHandle: '")).append(getName()).append("' - Serie greater than the pattern's length").toString());
				return;
			}
			chartSynapse.fwdPut(pattern, this);
		}
	}

	public Pattern revGet()
	{
		return null;
	}

	public TreeSet check()
	{
		TreeSet checks = new TreeSet();
		if (chartSynapse == null)
			checks.add(new NetCheck(1, "Handle has no ChartOutput synapses attached.", this));
		return checks;
	}

	public boolean isOutputFull()
	{
		return outputFull;
	}

	public void setOutputFull(boolean outputFull)
	{
		this.outputFull = outputFull;
	}

	public ChartInterface getChartSynapse()
	{
		return chartSynapse;
	}

	public void setChartSynapse(ChartInterface chartSynapse)
	{
		if (chartSynapse != getChartSynapse())
		{
			if (getChartSynapse() != null)
				getChartSynapse().removeHandle(this);
			this.chartSynapse = chartSynapse;
			if (chartSynapse != null)
				chartSynapse.setMonitor(mon);
		}
	}

	public int getSerie()
	{
		return serie;
	}

	public void setSerie(int serie)
	{
		this.serie = serie;
	}

	public int getRedColor()
	{
		return RedColor;
	}

	public void setRedColor(int RedColor)
	{
		this.RedColor = RedColor;
	}

	public int getGreenColor()
	{
		return GreenColor;
	}

	public void setGreenColor(int GreenColor)
	{
		this.GreenColor = GreenColor;
	}

	public int getBlueColor()
	{
		return BlueColor;
	}

	public void setBlueColor(int BlueColor)
	{
		this.BlueColor = BlueColor;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public void init()
	{
	}
}
