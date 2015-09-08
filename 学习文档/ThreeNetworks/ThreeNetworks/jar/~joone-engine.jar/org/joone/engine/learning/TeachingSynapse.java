// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TeachingSynapse.java

package org.joone.engine.learning;

import java.util.Iterator;
import java.util.TreeSet;
import org.joone.engine.*;
import org.joone.io.StreamInputSynapse;
import org.joone.io.StreamOutputSynapse;
import org.joone.net.NetCheck;

// Referenced classes of package org.joone.engine.learning:
//			ComparingElement, AbstractTeacherSynapse, TeacherSynapse

public class TeachingSynapse
	implements ComparingElement
{

	protected AbstractTeacherSynapse theTeacherSynapse;
	private LinearLayer theLinearLayer;
	private boolean enabled;
	private boolean outputFull;
	private AbstractTeacherSynapse theTeacherToUse;
	private StreamInputSynapse desired;
	private Monitor monitor;
	private String name;
	private static final long serialVersionUID = 0x849512fbd7d9603eL;

	public TeachingSynapse()
	{
		enabled = true;
		outputFull = false;
		theTeacherToUse = null;
	}

	public TeachingSynapse(TeacherSynapse aTeacher)
	{
		enabled = true;
		outputFull = false;
		theTeacherToUse = null;
		theTeacherToUse = aTeacher;
	}

	public void fwdPut(Pattern pattern)
	{
		Monitor mon = getMonitor();
		if (!mon.isLearning() && !mon.isValidation())
			return;
		if (!mon.isSingleThreadMode() && !getTheLinearLayer().isRunning())
			getTheLinearLayer().start();
		boolean firstTime = getTheTeacherSynapse().getSeenPatterns() == 0;
		getTheTeacherSynapse().fwdPut(pattern);
		if (mon.isSingleThreadMode())
		{
			if (pattern.isMarkedAsStoppingPattern())
			{
				getTheLinearLayer().fwdRun(null);
				getTheLinearLayer().fwdRun(null);
			}
			if (pattern.getCount() == 1 && !firstTime)
				getTheLinearLayer().fwdRun(null);
		}
	}

	public StreamInputSynapse getDesired()
	{
		return desired;
	}

	public int getInputDimension()
	{
		return getTheTeacherSynapse().getInputDimension();
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
			theLinearLayer.setLayerName("(R)MSE Layer");
			if (monitor != null)
				theLinearLayer.setMonitor(monitor);
			theLinearLayer.setRows(1);
			theLinearLayer.addInputSynapse(getTheTeacherSynapse());
		}
		return theLinearLayer;
	}

	public AbstractTeacherSynapse getTheTeacherSynapse()
	{
		if (theTeacherSynapse == null)
		{
			if (theTeacherToUse != null)
			{
				theTeacherSynapse = theTeacherToUse;
			} else
			{
				theTeacherSynapse = new TeacherSynapse();
				theTeacherSynapse.setName("Teacher Synapse");
			}
			if (monitor != null)
				theTeacherSynapse.setMonitor(monitor);
		}
		return theTeacherSynapse;
	}

	public Pattern revGet()
	{
		return getTheTeacherSynapse().revGet();
	}

	public boolean setDesired(StreamInputSynapse fn)
	{
		desired = fn;
		if (getTheTeacherSynapse().setDesired(fn))
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
		getTheTeacherSynapse().setInputDimension(newInputDimension);
	}

	public void setMonitor(Monitor newMonitor)
	{
		monitor = newMonitor;
		if (theTeacherSynapse != null)
			theTeacherSynapse.setMonitor(newMonitor);
		if (theLinearLayer != null)
			theLinearLayer.setMonitor(newMonitor);
		if (desired != null)
			desired.setMonitor(newMonitor);
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

	public void setTheTeacherSynapse(TeacherSynapse newTheTeacherSynapse)
	{
		theTeacherSynapse = newTheTeacherSynapse;
	}

	public void setTheLinearLayer(LinearLayer newTheLinearLayer)
	{
		theLinearLayer = newTheLinearLayer;
	}

	public void resetInput()
	{
		getTheTeacherSynapse().resetInput();
	}

	public TreeSet check()
	{
		TreeSet checks = new TreeSet();
		if (!isOutputFull())
			checks.add(new NetCheck(0, "the Teacher seems to be not attached", this));
		if (theLinearLayer != null)
			checks.addAll(setErrorSource(theLinearLayer.check()));
		if (theTeacherSynapse != null)
			checks.addAll(setErrorSource(theTeacherSynapse.check()));
		return checks;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		getTheTeacherSynapse().setEnabled(enabled);
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
		if (theTeacherSynapse != null)
			theTeacherSynapse.init();
	}

	private TreeSet setErrorSource(TreeSet errors)
	{
		if (!errors.isEmpty())
		{
			for (Iterator iter = errors.iterator(); iter.hasNext();)
			{
				NetCheck nc = (NetCheck)iter.next();
				if (!(nc.getSource() instanceof Monitor) && !(nc.getSource() instanceof StreamInputSynapse) && !(nc.getSource() instanceof StreamOutputSynapse))
					nc.setSource(this);
			}

		}
		return errors;
	}
}
