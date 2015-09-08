// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OutputSwitchSynapse.java

package org.joone.engine;

import java.io.Serializable;
import java.util.TreeSet;
import java.util.Vector;

// Referenced classes of package org.joone.engine:
//			OutputPatternListener, Monitor, NeuralElement, Pattern

public class OutputSwitchSynapse
	implements OutputPatternListener, Serializable
{

	protected Vector outputs;
	private String name;
	private Monitor mon;
	private int inputDimension;
	private boolean outputFull;
	private boolean enabled;
	private static final long serialVersionUID = 0x28553b304d5f638aL;
	private OutputPatternListener activeSynapse;
	private OutputPatternListener defaultSynapse;

	public OutputSwitchSynapse()
	{
		enabled = true;
		outputs = new Vector();
		activeSynapse = defaultSynapse = null;
		mon = null;
		inputDimension = 0;
	}

	public void reset()
	{
		setActiveSynapse(getDefaultSynapse());
	}

	public boolean removeOutputSynapse(String outputName)
	{
		boolean retValue = false;
		OutputPatternListener opl = getOutputSynapse(outputName);
		if (opl != null)
		{
			outputs.removeElement(opl);
			opl.setOutputFull(false);
			if (outputs.size() > 0)
			{
				if (getActiveOutput().equalsIgnoreCase(outputName))
					setActiveSynapse((OutputPatternListener)outputs.elementAt(0));
				if (getDefaultOutput().equalsIgnoreCase(outputName))
					setDefaultSynapse((OutputPatternListener)outputs.elementAt(0));
			} else
			{
				setActiveOutput("");
				setDefaultOutput("");
			}
			retValue = true;
		}
		return retValue;
	}

	protected OutputPatternListener getOutputSynapse(String outputName)
	{
		OutputPatternListener out = null;
		int i;
		for (i = 0; i < outputs.size(); i++)
		{
			out = (OutputPatternListener)outputs.elementAt(i);
			if (out.getName().equalsIgnoreCase(outputName))
				break;
		}

		if (i < outputs.size())
			return out;
		else
			return null;
	}

	public boolean addOutputSynapse(OutputPatternListener newOutput)
	{
		boolean retValue = false;
		if (!outputs.contains(newOutput) && !newOutput.isOutputFull())
		{
			outputs.addElement(newOutput);
			newOutput.setInputDimension(inputDimension);
			newOutput.setMonitor(mon);
			newOutput.setOutputFull(true);
			if (outputs.size() == 1)
			{
				setDefaultOutput(newOutput.getName());
				setActiveOutput(newOutput.getName());
			}
			retValue = true;
		}
		return retValue;
	}

	public String getActiveOutput()
	{
		if (activeSynapse != null)
			return activeSynapse.getName();
		else
			return "";
	}

	public void setActiveOutput(String newActiveOutput)
	{
		OutputPatternListener out = getOutputSynapse(newActiveOutput);
		activeSynapse = out;
	}

	public String getDefaultOutput()
	{
		if (defaultSynapse != null)
			return defaultSynapse.getName();
		else
			return "";
	}

	public void setDefaultOutput(String newDefaultOutput)
	{
		OutputPatternListener out = getOutputSynapse(newDefaultOutput);
		defaultSynapse = out;
	}

	protected OutputPatternListener getActiveSynapse()
	{
		return activeSynapse;
	}

	protected void setActiveSynapse(OutputPatternListener activeSynapse)
	{
		this.activeSynapse = activeSynapse;
	}

	protected OutputPatternListener getDefaultSynapse()
	{
		return defaultSynapse;
	}

	protected void setDefaultSynapse(OutputPatternListener defaultSynapse)
	{
		this.defaultSynapse = defaultSynapse;
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
		for (int i = 0; i < outputs.size(); i++)
		{
			OutputPatternListener out = (OutputPatternListener)outputs.elementAt(i);
			out.setInputDimension(newInputDimension);
		}

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
		for (int i = 0; i < outputs.size(); i++)
		{
			OutputPatternListener out = (OutputPatternListener)outputs.elementAt(i);
			out.setMonitor(newMonitor);
		}

	}

	protected void backward(double ad[])
	{
	}

	protected void forward(double ad[])
	{
	}

	public Vector getAllOutputs()
	{
		return outputs;
	}

	public void resetOutput()
	{
		reset();
	}

	public void fwdPut(Pattern pattern)
	{
		if (isEnabled() && activeSynapse != null)
			activeSynapse.fwdPut(pattern);
	}

	public Pattern revGet()
	{
		if (isEnabled() && activeSynapse != null)
			return activeSynapse.revGet();
		else
			return null;
	}

	public TreeSet check()
	{
		TreeSet checks = new TreeSet();
		return checks;
	}

	public boolean isOutputFull()
	{
		return outputFull;
	}

	public void setOutputFull(boolean outputFull)
	{
		this.outputFull = outputFull;
		for (int i = 0; i < outputs.size(); i++)
		{
			OutputPatternListener out = (OutputPatternListener)outputs.elementAt(i);
			out.setOutputFull(outputFull);
		}

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
		for (int i = 0; i < outputs.size(); i++)
			if (outputs.elementAt(i) instanceof NeuralElement)
				((NeuralElement)outputs.elementAt(i)).init();

	}
}
