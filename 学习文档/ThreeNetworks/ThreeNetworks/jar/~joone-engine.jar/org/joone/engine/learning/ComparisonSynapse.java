// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ComparisonSynapse.java

package org.joone.engine.learning;

import java.io.IOException;
import java.util.TreeSet;
import org.joone.engine.*;
import org.joone.io.StreamInputSynapse;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NetCheck;

// Referenced classes of package org.joone.engine.learning:
//			TeacherSynapse

public class ComparisonSynapse extends Synapse
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/engine/learning/TeacherSynapse);
	private StreamInputSynapse desired;
	protected transient Fifo fifo;
	protected transient boolean firstTime;
	private static final long serialVersionUID = 0xedef7e9c461252deL;

	public ComparisonSynapse()
	{
		firstTime = true;
		firstTime = true;
	}

	protected void backward(double ad[])
	{
	}

	protected void forward(double pActual[])
	{
		if (m_pattern.getCount() == 1 || m_pattern.isMarkedAsStoppingPattern())
			try
			{
				desired.gotoFirstLine();
			}
			catch (IOException ioe)
			{
				warnLogger((new StringBuilder("IOException while forwarding the influx. Message is : ")).append(ioe.getMessage()).toString(), ioe, log);
			}
		if (m_pattern.isMarkedAsStoppingPattern())
		{
			stopTheNet();
			return;
		}
		firstTime = false;
		outs = new double[pActual.length];
		Pattern pattDesired = desired.fwdGet();
		if (m_pattern.getCount() != pattDesired.getCount())
		{
			new NetErrorManager(getMonitor(), (new StringBuilder("ComparisonSynapse: No matching patterns - input#")).append(m_pattern.getCount()).append(" desired#").append(pattDesired.getCount()).toString());
			return;
		}
		double pTarget[] = pattDesired.getArray();
		if (pTarget != null)
		{
			outs = new double[getOutputDimension()];
			int i = 0;
			int n;
			for (n = 0; i < pActual.length; n++)
			{
				outs[n] = pActual[i];
				i++;
			}

			for (i = 0; i < pTarget.length;)
			{
				outs[n] = pTarget[i];
				i++;
				n++;
			}

			pushValue(outs, m_pattern.getCount());
		}
	}

	protected void stopTheNet()
	{
		pushStop();
		firstTime = true;
	}

	public Pattern fwdGet()
	{
		ComparisonSynapse comparisonsynapse = this;
		JVM INSTR monitorenter ;
		  goto _L1
_L3:
		try
		{
			wait();
			continue; /* Loop/switch isn't completed */
		}
		catch (InterruptedException ie)
		{
			warnLogger((new StringBuilder("wait() was interrupted. Message is : ")).append(ie.getMessage()).toString(), log);
		}
		return null;
_L1:
		if (getFifo().empty()) goto _L3; else goto _L2
_L2:
		Pattern errPatt;
		errPatt = (Pattern)fifo.pop();
		notifyAll();
		errPatt;
		comparisonsynapse;
		JVM INSTR monitorexit ;
		return;
		comparisonsynapse;
		JVM INSTR monitorexit ;
		throw ;
	}

	public void fwdPut(Pattern pattern)
	{
		if (!isEnabled())
		{
			if (pattern.isMarkedAsStoppingPattern())
				stopTheNet();
			return;
		} else
		{
			super.fwdPut(pattern);
			items = 0;
			return;
		}
	}

	public StreamInputSynapse getDesired()
	{
		return desired;
	}

	private Fifo getFifo()
	{
		if (fifo == null)
			fifo = new Fifo();
		return fifo;
	}

	public Pattern revGet()
	{
		return null;
	}

	public void revPut(Pattern pattern1)
	{
	}

	protected void setArrays(int i, int j)
	{
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

	public void resetInput()
	{
		if (getDesired() != null)
			getDesired().resetInput();
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

	public TreeSet check()
	{
		TreeSet checks = super.check();
		if (desired == null)
			checks.add(new NetCheck(0, "Desired Input has not been set.", this));
		else
			checks.addAll(desired.check());
		return checks;
	}

	public void reset()
	{
		super.reset();
		if (getDesired() != null)
			getDesired().reset();
	}

	public void setMonitor(Monitor newMonitor)
	{
		super.setMonitor(newMonitor);
		if (getMonitor() != null)
			getMonitor().setSupervised(true);
	}

	public void netStoppedError(NeuralNetEvent e, String error)
	{
		pushStop();
		firstTime = true;
		reset();
	}

	private void pushStop()
	{
		double arr[] = new double[getOutputDimension()];
		pushValue(arr, -1);
	}

	private void pushValue(double arr[], int count)
	{
		Pattern patt = new Pattern(arr);
		patt.setCount(count);
		synchronized (this)
		{
			getFifo().push(patt);
			notify();
		}
	}

	public int getOutputDimension()
	{
		return getInputDimension() * 2;
	}

	public void init()
	{
		super.init();
		if (getDesired() != null)
			getDesired().init();
	}

}
