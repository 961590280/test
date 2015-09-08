// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Synapse.java

package org.joone.engine;

import java.io.Serializable;
import java.util.*;
import org.joone.inspection.Inspectable;
import org.joone.inspection.implementations.WeightsInspection;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.engine:
//			InputPatternListener, OutputPatternListener, LearnableSynapse, Matrix, 
//			Pattern, Monitor, Learner

public abstract class Synapse
	implements InputPatternListener, OutputPatternListener, LearnableSynapse, Serializable, Inspectable
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/engine/Synapse);
	private static int synapseCount = 0;
	private String fieldName;
	private double learningRate;
	private double momentum;
	private int inputDimension;
	private int outputDimension;
	private boolean inputFull;
	private boolean outputFull;
	private Monitor monitor;
	private int ignoreBefore;
	private boolean loopBack;
	protected Matrix array;
	protected int m_batch;
	protected boolean enabled;
	protected transient double inps[];
	protected transient double outs[];
	protected transient double bouts[];
	protected transient int items;
	protected transient int bitems;
	protected transient Pattern m_pattern;
	protected transient Pattern b_pattern;
	protected transient int count;
	protected transient boolean notFirstTime;
	protected transient boolean notFirstTimeB;
	protected transient Learner myLearner;
	protected volatile transient Object fwdLock;
	protected volatile transient Object revLock;
	protected boolean learnable;
	private static final long serialVersionUID = 0xae387d8f51cb2492L;

	public Synapse()
	{
		fieldName = (new StringBuilder("Synapse ")).append(++synapseCount).toString();
		learningRate = 0.0D;
		momentum = 0.0D;
		inputDimension = 0;
		outputDimension = 0;
		ignoreBefore = -1;
		loopBack = false;
		m_batch = 0;
		enabled = true;
		inps = null;
		outs = null;
		items = 0;
		bitems = 0;
		count = 0;
		myLearner = null;
		fwdLock = new Object();
		revLock = new Object();
		learnable = false;
	}

	public void addNoise(double amplitude)
	{
		if (array == null)
			warnLogger("Cannot add noise because weight array is null.", log);
		else
			array.addNoise(amplitude);
	}

	public void randomize(double amplitude)
	{
		if (array == null)
		{
			warnLogger("Cannot randomize weights because weight array is null.", log);
			return;
		} else
		{
			array.randomizeConditionally(amplitude);
			return;
		}
	}

	protected abstract void backward(double ad[]);

	public boolean canCountSteps()
	{
		return false;
	}

	protected abstract void forward(double ad[]);

	public Pattern fwdGet()
	{
		if (!isEnabled())
			return null;
		Object lock = getFwdLock();
		Object obj = lock;
		JVM INSTR monitorenter ;
		if (!notFirstTime && loopBack) goto _L2; else goto _L1
_L4:
		try
		{
			lock.wait();
			continue; /* Loop/switch isn't completed */
		}
		catch (InterruptedException e)
		{
			reset();
			lock.notify();
		}
		return null;
_L1:
		if (items == 0) goto _L4; else goto _L3
_L3:
		Pattern p;
		items--;
		p = fwdPattern_produce();
		lock.notify();
		p;
		obj;
		JVM INSTR monitorexit ;
		return;
_L2:
		items = bitems = count = 0;
		notFirstTime = true;
		lock.notify();
		obj;
		JVM INSTR monitorexit ;
		return null;
		obj;
		JVM INSTR monitorexit ;
		throw ;
	}

	protected Pattern fwdPattern_produce()
	{
		m_pattern.setArray(outs);
		if (isLoopBack())
			m_pattern.setCount(0);
		return m_pattern;
	}

	public void fwdPut(Pattern pattern)
	{
		if (!isEnabled())
			return;
		Object lock = getFwdLock();
		synchronized (lock)
		{
			while (items > 0) 
			{
				try
				{
					lock.wait();
					continue;
				}
				catch (InterruptedException e)
				{
					reset();
					lock.notify();
				}
				return;
			}
			fwdPattern_consume(pattern);
			items++;
			lock.notify();
		}
	}

	protected void fwdPattern_consume(Pattern pattern)
	{
		m_pattern = pattern;
		count = m_pattern.getCount();
		inps = pattern.getArray();
		forward(inps);
	}

	public void reset()
	{
		items = bitems = 0;
		notFirstTime = false;
		notFirstTimeB = false;
	}

	public int getIgnoreBefore()
	{
		return ignoreBefore;
	}

	public int getInputDimension()
	{
		return inputDimension;
	}

	public double getLearningRate()
	{
		if (monitor != null)
			return monitor.getLearningRate();
		else
			return 0.0D;
	}

	public double getMomentum()
	{
		if (monitor != null)
			return monitor.getMomentum();
		else
			return 0.0D;
	}

	public Monitor getMonitor()
	{
		return monitor;
	}

	public String getName()
	{
		return fieldName;
	}

	public int getOutputDimension()
	{
		return outputDimension;
	}

	protected Object readResolve()
	{
		setArrays(getInputDimension(), getOutputDimension());
		return this;
	}

	public Pattern revGet()
	{
		if (!isEnabled())
			return null;
		Object lock = getRevLock();
		Object obj = lock;
		JVM INSTR monitorenter ;
		if (!notFirstTimeB && loopBack) goto _L2; else goto _L1
_L4:
		try
		{
			lock.wait();
			continue; /* Loop/switch isn't completed */
		}
		catch (InterruptedException e)
		{
			reset();
			lock.notify();
		}
		return null;
_L1:
		if (bitems == 0) goto _L4; else goto _L3
_L3:
		Pattern p;
		bitems--;
		p = revPattern_consume();
		lock.notify();
		p;
		obj;
		JVM INSTR monitorexit ;
		return;
_L2:
		lock.notify();
		obj;
		JVM INSTR monitorexit ;
		return null;
		obj;
		JVM INSTR monitorexit ;
		throw ;
	}

	protected Pattern revPattern_consume()
	{
		b_pattern.setArray(bouts);
		return b_pattern;
	}

	public void revPut(Pattern pattern)
	{
		if (!isEnabled())
			return;
		Object lock = getRevLock();
		synchronized (lock)
		{
			while (bitems > 0) 
			{
				try
				{
					lock.wait();
					continue;
				}
				catch (InterruptedException e)
				{
					reset();
					lock.notify();
				}
				return;
			}
			revPattern_produce(pattern);
			bitems++;
			notFirstTimeB = true;
			lock.notify();
		}
	}

	protected void revPattern_produce(Pattern pattern)
	{
		b_pattern = pattern;
		count = b_pattern.getCount();
		backward(pattern.getArray());
	}

	protected abstract void setArrays(int i, int j);

	protected abstract void setDimensions(int i, int j);

	public void setIgnoreBefore(int newIgnoreBefore)
	{
		ignoreBefore = newIgnoreBefore;
	}

	public void setInputDimension(int newInputDimension)
	{
		if (inputDimension != newInputDimension)
		{
			inputDimension = newInputDimension;
			setDimensions(newInputDimension, -1);
		}
	}

	/**
	 * @deprecated Method setLearningRate is deprecated
	 */

	public void setLearningRate(double newLearningRate)
	{
		learningRate = newLearningRate;
	}

	/**
	 * @deprecated Method setMomentum is deprecated
	 */

	public void setMomentum(double newMomentum)
	{
		momentum = newMomentum;
	}

	public void setMonitor(Monitor newMonitor)
	{
		monitor = newMonitor;
	}

	public void setName(String name)
	{
		fieldName = name;
	}

	public void setOutputDimension(int newOutputDimension)
	{
		if (outputDimension != newOutputDimension)
		{
			outputDimension = newOutputDimension;
			setDimensions(-1, newOutputDimension);
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

	public boolean isLoopBack()
	{
		return loopBack;
	}

	public void setLoopBack(boolean loopBack)
	{
		this.loopBack = loopBack;
	}

	public TreeSet check()
	{
		TreeSet checks = new TreeSet();
		return checks;
	}

	public Collection Inspections()
	{
		Collection col = new ArrayList(1);
		col.add(new WeightsInspection(array));
		return col;
	}

	public String InspectableTitle()
	{
		return getName();
	}

	public boolean isInputFull()
	{
		return inputFull;
	}

	public void setInputFull(boolean inputFull)
	{
		this.inputFull = inputFull;
	}

	public boolean isOutputFull()
	{
		return outputFull;
	}

	public void setOutputFull(boolean outputFull)
	{
		this.outputFull = outputFull;
	}

	public Matrix getWeights()
	{
		return array;
	}

	public void setWeights(Matrix newWeights)
	{
		array = newWeights;
	}

	public Learner getLearner()
	{
		if (!learnable)
			return null;
		else
			return getMonitor().getLearner();
	}

	public void initLearner()
	{
		myLearner = getLearner();
		if (myLearner != null)
			myLearner.registerLearnable(this);
	}

	protected final Object getFwdLock()
	{
		if (fwdLock == null)
			synchronized (this)
			{
				if (fwdLock == null)
					fwdLock = new Object();
			}
		return fwdLock;
	}

	protected final Object getRevLock()
	{
		if (revLock == null)
			synchronized (this)
			{
				if (revLock == null)
					revLock = new Object();
			}
		return revLock;
	}

	public void init()
	{
		initLearner();
		if (getWeights() != null)
			getWeights().clearDelta();
		getFwdLock();
		getRevLock();
	}

	protected String getSynapseNameSafely()
	{
		String name = getName();
		if (name == null)
			name = "";
		return name;
	}

	protected void warnLogger(String msg, ILogger logger)
	{
		String message = (new StringBuilder("[")).append(getSynapseNameSafely()).append("] ").append(msg).toString();
		logger.warn(message);
	}

	protected void warnLogger(String msg, Exception ex, ILogger logger)
	{
		String message = (new StringBuilder("[")).append(getSynapseNameSafely()).append("] ").append(msg).toString();
		logger.warn(message, ex);
	}

}
