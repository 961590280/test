// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AbstractTeacherSynapse.java

package org.joone.engine.learning;

import java.io.IOException;
import java.util.TreeSet;
import org.joone.engine.*;
import org.joone.io.StreamInputSynapse;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NetCheck;

public abstract class AbstractTeacherSynapse extends Synapse
{

	private static final long serialVersionUID = 0xcf68ded0815a9768L;
	protected static final ILogger log = LoggerFactory.getLogger(org/joone/engine/learning/AbstractTeacherSynapse);
	private transient boolean firstTime;
	private transient int patterns;
	protected StreamInputSynapse desired;
	protected transient Fifo error;
	protected transient int currEpoch;

	public AbstractTeacherSynapse()
	{
		firstTime = true;
		patterns = 0;
		currEpoch = 0;
		setFirstTime(true);
	}

	protected void setFirstTime(boolean aValue)
	{
		firstTime = aValue;
	}

	protected boolean isFirstTime()
	{
		return firstTime;
	}

	protected void backward(double ad[])
	{
	}

	protected void pushError(double error, int count)
	{
		Pattern ptnErr = new Pattern(error);
		ptnErr.setCount(count);
		synchronized (this)
		{
			getError().push(ptnErr);
			notify();
		}
	}

	private Fifo getError()
	{
		if (error == null)
			error = new Fifo();
		return error;
	}

	protected void stopTheNet()
	{
		pushError(0.0D, -1);
		patterns = 0;
		setFirstTime(true);
		if (getMonitor() != null)
			(new NetStoppedEventNotifier(getMonitor())).start();
	}

	protected int getSeenPatterns()
	{
		return patterns;
	}

	protected void setSeenPatterns(int aValue)
	{
		patterns = aValue;
	}

	protected void incSeenPatterns()
	{
		patterns++;
	}

	public Pattern fwdGet()
	{
		AbstractTeacherSynapse abstractteachersynapse = this;
		JVM INSTR monitorenter ;
		  goto _L1
_L3:
		try
		{
			wait();
			continue; /* Loop/switch isn't completed */
		}
		catch (InterruptedException ie) { }
		return null;
_L1:
		if (getError().empty()) goto _L3; else goto _L2
_L2:
		Pattern errPatt;
		errPatt = (Pattern)error.pop();
		notify();
		errPatt;
		abstractteachersynapse;
		JVM INSTR monitorexit ;
		return;
		abstractteachersynapse;
		JVM INSTR monitorexit ;
		throw ;
	}

	public StreamInputSynapse getDesired()
	{
		return desired;
	}

	public boolean setDesired(StreamInputSynapse newDesired)
	{
		if (newDesired == null)
		{
			if (desired != null)
				desired.setInputFull(false);
			desired = null;
		} else
		{
			if (newDesired.isInputFull())
				return false;
			desired = newDesired;
			desired.setStepCounter(false);
			desired.setOutputDimension(getInputDimension());
			desired.setInputFull(true);
		}
		return true;
	}

	protected Object readResolve()
	{
		super.readResolve();
		setFirstTime(true);
		if (getMonitor() != null)
			getMonitor().setSupervised(true);
		return this;
	}

	protected void setArrays(int i, int j)
	{
	}

	protected void setDimensions(int i, int j)
	{
	}

	public void setInputDimension(int newInputDimension)
	{
		super.setInputDimension(newInputDimension);
		if (getDesired() != null)
			getDesired().setOutputDimension(newInputDimension);
	}

	public void reset()
	{
		super.reset();
		setSeenPatterns(0);
		setFirstTime(true);
		if (getDesired() != null)
			getDesired().reset();
	}

	public void resetInput()
	{
		if (getDesired() != null)
			getDesired().resetInput();
	}

	public void setMonitor(Monitor newMonitor)
	{
		super.setMonitor(newMonitor);
		if (getMonitor() != null)
			getMonitor().setSupervised(true);
	}

	public void netStoppedError()
	{
		pushError(0.0D, -1);
		setSeenPatterns(0);
		setFirstTime(true);
		reset();
	}

	public void init()
	{
		super.init();
		setSeenPatterns(0);
		setFirstTime(true);
		currEpoch = 0;
		getError().clear();
		if (getDesired() != null)
			getDesired().init();
	}

	public TreeSet check()
	{
		TreeSet checks = super.check();
		if (getDesired() == null)
			checks.add(new NetCheck(0, "Desired Input has not been set.", this));
		else
			checks.addAll(getDesired().check());
		return checks;
	}

	public void revPut(Pattern pattern1)
	{
	}

	public Pattern revGet()
	{
		if (isEnabled())
			return super.fwdGet();
		else
			return null;
	}

	public void fwdPut(Pattern pattern)
	{
		int step = pattern.getCount();
		boolean stoppingPattern = pattern.isMarkedAsStoppingPattern();
		if (!getMonitor().isSingleThreadMode() && (getMonitor() == null || !isEnabled()))
		{
			if (stoppingPattern)
				stopTheNet();
			return;
		}
		super.fwdPut(pattern);
		if (!stoppingPattern)
		{
			if (!getMonitor().isLearningCicle(step))
				items = 0;
		} else
		{
			items = 0;
		}
	}

	protected void forward(double pattern[])
	{
		if (m_pattern.getCount() == 1 || m_pattern.isMarkedAsStoppingPattern())
			try
			{
				desired.gotoFirstLine();
				if (!isFirstTime() && getSeenPatterns() == getMonitor().getNumOfPatterns())
				{
					double myGlobalError = calculateGlobalError();
					currEpoch++;
					pushError(myGlobalError, currEpoch);
					getMonitor().setGlobalError(myGlobalError);
					epochFinished();
					setSeenPatterns(0);
				}
			}
			catch (IOException ioe)
			{
				new NetErrorManager(getMonitor(), (new StringBuilder("TeacherSynapse: IOException while forwarding the influx. Message is : ")).append(ioe.getMessage()).toString());
				return;
			}
		if (m_pattern.isMarkedAsStoppingPattern())
		{
			if (!getMonitor().isSingleThreadMode())
				stopTheNet();
			else
				pushError(0.0D, -1);
			return;
		}
		setFirstTime(false);
		outs = new double[pattern.length];
		Pattern pattDesired = desired.fwdGet();
		if (m_pattern.getCount() != pattDesired.getCount())
			try
			{
				desired.gotoLine(m_pattern.getCount());
				pattDesired = desired.fwdGet();
				if (m_pattern.getCount() != pattDesired.getCount())
				{
					new NetErrorManager(getMonitor(), (new StringBuilder("TeacherSynapse: No matching patterns - input#")).append(m_pattern.getCount()).append(" desired#").append(pattDesired.getCount()).toString());
					return;
				}
			}
			catch (IOException ioe)
			{
				new NetErrorManager(getMonitor(), (new StringBuilder("TeacherSynapse: IOException while forwarding the influx. Message is : ")).append(ioe.getMessage()).toString());
				return;
			}
		if (getMonitor().getPreLearning() < m_pattern.getCount())
		{
			double pDesired[] = pattDesired.getArray();
			if (pDesired != null)
				if (pDesired.length != outs.length)
				{
					warnLogger("Size output pattern mismatches size desired pattern. Zero-valued desired pattern sized error pattern will be backpropagated.", log);
					outs = new double[pDesired.length];
				} else
				{
					constructErrorPattern(pDesired, pattern);
				}
		}
		incSeenPatterns();
	}

	protected void constructErrorPattern(double aDesired[], double anOutput[])
	{
		for (int x = 0; x < aDesired.length; x++)
			outs[x] = calculateError(aDesired[x], anOutput[x], x);

	}

	protected abstract double calculateError(double d, double d1, int i);

	protected abstract double calculateGlobalError();

	protected void epochFinished()
	{
	}

}
