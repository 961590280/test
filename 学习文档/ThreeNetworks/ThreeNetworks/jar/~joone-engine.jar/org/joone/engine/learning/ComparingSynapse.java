// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ComparingSynapse.java

package org.joone.engine.learning;

import java.util.Iterator;
import java.util.TreeSet;
import org.joone.engine.*;
import org.joone.io.StreamInputSynapse;
import org.joone.io.StreamOutputSynapse;
import org.joone.net.NetCheck;

// Referenced classes of package org.joone.engine.learning:
//			ComparingElement, ComparisonSynapse

public class ComparingSynapse
	implements ComparingElement
{

	private ComparisonSynapse theComparisonSynapse;
	private LinearLayer theLinearLayer;
	private boolean enabled;
	private boolean outputFull;
	private StreamInputSynapse desired;
	private Monitor monitor;
	private String name;
	private static final long serialVersionUID = 0x849512fbd7d9603eL;

	public ComparingSynapse()
	{
		enabled = true;
		outputFull = false;
	}

	public void fwdPut(Pattern pattern)
	{
		if (!getTheLinearLayer().isRunning())
			getTheLinearLayer().start();
		getTheComparisonSynapse().fwdPut(pattern);
	}

	public StreamInputSynapse getDesired()
	{
		return desired;
	}

	public int getInputDimension()
	{
		return getTheComparisonSynapse().getInputDimension();
	}

	public Monitor getMonitor()
	{
		return monitor;
	}

	public LinearLayer getTheLinearLayer()
	{
		if (theLinearLayer == null)
		{
			theLinearLayer = new LinearLayer();
			theLinearLayer.setLayerName("Comparing LinearLayer");
			if (monitor != null)
				theLinearLayer.setMonitor(monitor);
			theLinearLayer.setRows(1);
			theLinearLayer.addInputSynapse(getTheComparisonSynapse());
		}
		return theLinearLayer;
	}

	public ComparisonSynapse getTheComparisonSynapse()
	{
		if (theComparisonSynapse == null)
		{
			theComparisonSynapse = new ComparisonSynapse();
			theComparisonSynapse.setName("Teacher Synapse");
			if (monitor != null)
				theComparisonSynapse.setMonitor(monitor);
		}
		return theComparisonSynapse;
	}

	public Pattern revGet()
	{
		return null;
	}

	public boolean setDesired(StreamInputSynapse fn)
	{
		desired = fn;
		if (getTheComparisonSynapse().setDesired(fn))
		{
			if (monitor != null && desired != null)
				desired.setMonitor(monitor);
			return true;
		} else
		{
			return false;
		}
	}

	public boolean addResultSynapse(OutputPatternListener listener)
	{
		if (listener != null)
			return getTheLinearLayer().addOutputSynapse(listener);
		else
			return false;
	}

	public void removeResultSynapse(OutputPatternListener listener)
	{
		if (listener != null)
			getTheLinearLayer().removeOutputSynapse(listener);
	}

	public void setInputDimension(int newInputDimension)
	{
		getTheComparisonSynapse().setInputDimension(newInputDimension);
		getTheLinearLayer().setRows(newInputDimension * 2);
	}

	public void setMonitor(Monitor newMonitor)
	{
		monitor = newMonitor;
		if (monitor != null)
		{
			getTheComparisonSynapse().setMonitor(newMonitor);
			getTheLinearLayer().setMonitor(newMonitor);
			if (desired != null)
				desired.setMonitor(newMonitor);
		}
	}

	public void stop()
	{
		getTheLinearLayer().stop();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String newName)
	{
		name = newName;
	}

	protected void forward(double ad[])
	{
	}

	protected void setArrays(int i, int j)
	{
	}

	protected void setDimensions(int i, int j)
	{
	}

	protected void backward(double ad[])
	{
	}

	public void setTheComparisonSynapse(ComparisonSynapse theComparisonSynapse)
	{
		this.theComparisonSynapse = theComparisonSynapse;
	}

	public void setTheLinearLayer(LinearLayer newTheLinearLayer)
	{
		theLinearLayer = newTheLinearLayer;
	}

	public void resetInput()
	{
		getTheComparisonSynapse().resetInput();
	}

	public TreeSet check()
	{
		TreeSet checks = new TreeSet();
		if (theLinearLayer != null)
			checks.addAll(setErrorSource(theLinearLayer.check()));
		if (theComparisonSynapse != null)
			checks.addAll(setErrorSource(theComparisonSynapse.check()));
		return checks;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public boolean isOutputFull()
	{
		return outputFull;
	}

	public void setOutputFull(boolean outputFull)
	{
		this.outputFull = outputFull;
	}

	public void init()
	{
		if (theComparisonSynapse != null)
			theComparisonSynapse.init();
	}

	private TreeSet setErrorSource(TreeSet errors)
	{
		if (!errors.isEmpty())
		{
			for (Iterator iter = errors.iterator(); iter.hasNext();)
			{
				NetCheck nc = (NetCheck)iter.next();
				Object source = nc.getSource();
				if (!(source instanceof Monitor) && !(source instanceof StreamInputSynapse) && !(source instanceof StreamOutputSynapse))
					nc.setSource(this);
			}

		}
		return errors;
	}
}
