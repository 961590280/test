// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Monitor.java

package org.joone.engine;

import java.io.Serializable;
import java.util.*;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NetCheck;

// Referenced classes of package org.joone.engine:
//			NeuralNetEvent, NeuralNetListener, NetStoppedEventNotifier, Learner, 
//			BasicLearner, LearnerFactory

public class Monitor
	implements Serializable
{

	private static final long serialVersionUID = 0x2860a07c0a83eb1dL;
	private int preLearning;
	private boolean learning;
	private int currentCicle;
	private int run;
	private int saveCurrentCicle;
	private int saveRun;
	private int patterns;
	private int validationPatterns;
	private int totCicles;
	private double learningRate;
	private double momentum;
	private double globalError;
	private int batchSize;
	private boolean useRMSE;
	private LearnerFactory theLearnerFactory;
	private Monitor parent;
	private transient Vector internalListeners;
	private transient Vector netListeners;
	private transient boolean firstTime;
	private transient boolean exporting;
	private transient boolean validation;
	private transient boolean running;
	private transient boolean trainingDataForValidation;
	private static final ILogger log = LoggerFactory.getLogger(org/joone/engine/Monitor);
	private boolean supervisioned;
	private boolean singleThreadMode;
	public int learningMode;
	private List learners;
	private Hashtable params;

	public Monitor()
	{
		preLearning = 0;
		learning = false;
		run = 0;
		batchSize = 0;
		useRMSE = true;
		theLearnerFactory = null;
		internalListeners = new Vector();
		netListeners = new Vector();
		firstTime = true;
		exporting = false;
		validation = false;
		running = false;
		trainingDataForValidation = false;
		supervisioned = false;
		singleThreadMode = true;
		learningMode = 0;
		firstTime = true;
		netListeners = new Vector();
		internalListeners = new Vector();
		parent = null;
	}

	public void addNeuralNetListener(NeuralNetListener l)
	{
		addNeuralNetListener(l, true);
	}

	public synchronized void addNeuralNetListener(NeuralNetListener l, boolean removable)
	{
		if (parent != null)
		{
			parent.addNeuralNetListener(l, removable);
		} else
		{
			if (getListeners().contains(l))
			{
				log.warn("Neural net listener already registered");
				return;
			}
			netListeners.addElement(l);
			if (!removable && !getNoDetachListeners().contains(l))
				getNoDetachListeners().addElement(l);
		}
	}

	private Vector getNoDetachListeners()
	{
		if (internalListeners == null)
			internalListeners = new Vector();
		return internalListeners;
	}

	private Vector getListeners()
	{
		if (netListeners == null)
			netListeners = new Vector();
		return netListeners;
	}

	public void fireCicleTerminated()
	{
		if (parent != null)
		{
			parent.fireCicleTerminated();
		} else
		{
			Object list[] = getListenersArrayQuickly();
			if (list == null)
				return;
			NeuralNetEvent event = new NeuralNetEvent(this);
			for (int i = 0; i < list.length; i++)
			{
				NeuralNetListener listener = (NeuralNetListener)list[i];
				listener.cicleTerminated(event);
			}

		}
	}

	public void fireNetStarted()
	{
		if (parent != null)
		{
			parent.fireNetStarted();
		} else
		{
			Object list[] = getListenersArraySafely();
			if (list == null)
				return;
			NeuralNetEvent event = new NeuralNetEvent(this);
			for (int i = 0; i < list.length; i++)
			{
				NeuralNetListener listener = (NeuralNetListener)list[i];
				listener.netStarted(event);
			}

		}
	}

	public void fireNetStopped()
	{
		if (parent != null)
		{
			parent.fireNetStopped();
		} else
		{
			Object list[] = getListenersArraySafely();
			if (list == null)
				return;
			NeuralNetEvent event = new NeuralNetEvent(this);
			for (int i = 0; i < list.length; i++)
			{
				NeuralNetListener listener = (NeuralNetListener)list[i];
				listener.netStopped(event);
			}

		}
	}

	public void fireNetStoppedError(String errMsg)
	{
		if (parent != null)
		{
			parent.fireNetStoppedError(errMsg);
		} else
		{
			Object list[] = getListenersArraySafely();
			if (list == null)
				return;
			NeuralNetEvent event = new NeuralNetEvent(this);
			for (int i = 0; i < list.length; i++)
			{
				NeuralNetListener listener = (NeuralNetListener)list[i];
				listener.netStoppedError(event, errMsg);
			}

			if (running)
			{
				log.error((new StringBuilder("Neural net stopped due to the following error: ")).append(errMsg).toString());
				log.debug((new StringBuilder("\tepoch:")).append(currentCicle).toString());
				log.debug((new StringBuilder("\tcycle:")).append(run).toString());
				log.debug((new StringBuilder("\tlearning:")).append(isLearning()).toString());
				log.debug((new StringBuilder("\tvalidation:")).append(isValidation()).toString());
				log.debug((new StringBuilder("\ttrainingPatterns:")).append(getTrainingPatterns()).toString());
				log.debug((new StringBuilder("\tvalidationPatterns:")).append(getValidationPatterns()).toString());
			}
		}
	}

	public void fireErrorChanged()
	{
		if (parent != null)
		{
			parent.fireErrorChanged();
		} else
		{
			Object list[] = getListenersArrayQuickly();
			if (list == null)
				return;
			NeuralNetEvent event = new NeuralNetEvent(this);
			for (int i = 0; i < list.length; i++)
			{
				NeuralNetListener listener = (NeuralNetListener)list[i];
				listener.errorChanged(event);
			}

		}
	}

	private Object[] getListenersArrayQuickly()
	{
		int size = getListeners().size();
		if (size == 0)
			return null;
		Monitor monitor = this;
		JVM INSTR monitorenter ;
		Object list[] = getListeners().toArray();
		return list;
		monitor;
		JVM INSTR monitorexit ;
		throw ;
	}

	private Object[] getListenersArraySafely()
	{
label0:
		{
			synchronized (this)
			{
				int size = getListeners().size();
				if (size != 0)
					break label0;
			}
			return null;
		}
		getListeners().toArray();
		monitor;
		JVM INSTR monitorexit ;
		return;
	}

	public synchronized int getCurrentCicle()
	{
		if (parent != null)
			return parent.getCurrentCicle();
		else
			return currentCicle;
	}

	public double getGlobalError()
	{
		if (parent != null)
			return parent.getGlobalError();
		else
			return globalError;
	}

	public synchronized double getLearningRate()
	{
		if (parent != null)
			return parent.getLearningRate();
		else
			return learningRate;
	}

	public double getMomentum()
	{
		if (parent != null)
			return parent.getMomentum();
		else
			return momentum;
	}

	public int getTrainingPatterns()
	{
		if (parent != null)
			return parent.getTrainingPatterns();
		else
			return patterns;
	}

	public void setTrainingPatterns(int newPatterns)
	{
		if (parent != null)
			parent.setTrainingPatterns(newPatterns);
		else
			patterns = newPatterns;
	}

	public int getPreLearning()
	{
		if (parent != null)
			return parent.getPreLearning();
		else
			return preLearning;
	}

	public synchronized int getStep()
	{
		if (parent != null)
			return parent.getStep();
		else
			return run;
	}

	public int getTotCicles()
	{
		if (parent != null)
			return parent.getTotCicles();
		else
			return totCicles;
	}

	public synchronized void Go()
	{
		if (parent != null)
		{
			parent.Go();
		} else
		{
			setSingleThreadMode(false);
			run = getNumOfPatterns();
			currentCicle = totCicles;
			firstTime = false;
			running = true;
			notifyAll();
		}
	}

	public boolean isLearning()
	{
		return learning;
	}

	public boolean isLearningCicle(int num)
	{
		if (parent != null)
		{
			boolean learn = parent.isLearningCicle(num);
			return learn & isLearning();
		}
		if (num > preLearning)
			return isLearning();
		else
			return false;
	}

	public synchronized void resetCycle()
	{
		run = 0;
	}

	public synchronized boolean nextStep()
	{
		if (parent != null)
			return parent.nextStep();
		  goto _L1
_L3:
		if (firstTime)
			break MISSING_BLOCK_LABEL_142;
		if (currentCicle > 0)
		{
			fireCicleTerminated();
			currentCicle--;
			if (currentCicle < 0)
				currentCicle = 0;
			run = getNumOfPatterns();
		}
		if (currentCicle != 0)
			continue; /* Loop/switch isn't completed */
		running = false;
		if (!isSupervised() || !isLearning() && !isValidation())
			(new NetStoppedEventNotifier(this)).start();
		if (saveRun == 0)
		{
			saveRun = getNumOfPatterns();
			saveCurrentCicle = totCicles;
		}
		run = 0;
		firstTime = true;
		return false;
		try
		{
			wait();
		}
		catch (InterruptedException e)
		{
			run = 0;
			firstTime = true;
			return false;
		}
_L1:
		if (run == 0) goto _L3; else goto _L2
_L2:
		if (run == getNumOfPatterns() && currentCicle == totCicles)
			fireNetStarted();
		if (run > 0)
			run--;
		return true;
	}

	protected Object readResolve()
	{
		firstTime = true;
		return this;
	}

	public synchronized void removeNeuralNetListener(NeuralNetListener l)
	{
		if (parent != null)
		{
			parent.removeNeuralNetListener(l);
		} else
		{
			getListeners().removeElement(l);
			getNoDetachListeners().removeElement(l);
		}
	}

	public synchronized void runAgain()
	{
		if (parent != null)
		{
			parent.runAgain();
		} else
		{
			run = getNumOfPatterns();
			currentCicle = saveCurrentCicle;
			firstTime = false;
			running = true;
			notifyAll();
		}
	}

	public synchronized void setCurrentCicle(int newCurrentCicle)
	{
		if (parent != null)
			parent.setCurrentCicle(newCurrentCicle);
		else
			currentCicle = newCurrentCicle;
	}

	public void setGlobalError(double newGlobalError)
	{
		if (parent != null)
		{
			parent.setGlobalError(newGlobalError);
		} else
		{
			globalError = newGlobalError;
			fireErrorChanged();
		}
	}

	public void setLearning(boolean newLearning)
	{
		learning = newLearning;
	}

	public synchronized void setLearningRate(double newLearningRate)
	{
		if (parent != null)
			parent.setLearningRate(newLearningRate);
		else
			learningRate = newLearningRate;
	}

	public void setMomentum(double newMomentum)
	{
		if (parent != null)
			parent.setMomentum(newMomentum);
		else
			momentum = newMomentum;
	}

	public void setPreLearning(int newPreLearning)
	{
		if (parent != null)
			parent.setPreLearning(newPreLearning);
		else
			preLearning = newPreLearning;
	}

	public void setTotCicles(int newTotCicles)
	{
		if (parent != null)
			parent.setTotCicles(newTotCicles);
		else
			totCicles = newTotCicles;
	}

	public synchronized void Stop()
	{
		if (parent != null)
		{
			parent.Stop();
		} else
		{
			saveRun = run;
			saveCurrentCicle = currentCicle;
			run = 0;
			currentCicle = 0;
		}
	}

	public boolean isExporting()
	{
		if (parent != null)
			return parent.isExporting();
		else
			return exporting;
	}

	public void setExporting(boolean exporting)
	{
		if (parent != null)
			parent.setExporting(exporting);
		else
			this.exporting = exporting;
	}

	public synchronized int getRun()
	{
		return run;
	}

	public boolean isValidation()
	{
		if (parent != null)
			return parent.isValidation();
		else
			return validation;
	}

	public void setValidation(boolean validation)
	{
		if (parent != null)
			parent.setValidation(validation);
		else
			this.validation = validation;
	}

	public boolean isTrainingDataForValidation()
	{
		if (parent != null)
			return parent.isTrainingDataForValidation();
		else
			return trainingDataForValidation;
	}

	public void setTrainingDataForValidation(boolean aMode)
	{
		if (parent != null)
			parent.setTrainingDataForValidation(aMode);
		else
			trainingDataForValidation = aMode;
	}

	public void removeAllListeners()
	{
		if (parent != null)
			parent.removeAllListeners();
		else
		if (internalListeners != null)
			netListeners = (Vector)internalListeners.clone();
		else
			netListeners = null;
	}

	public Monitor getParent()
	{
		return parent;
	}

	public void setParent(Monitor parent)
	{
		this.parent = parent;
	}

	public int getValidationPatterns()
	{
		if (parent != null)
			return parent.getValidationPatterns();
		else
			return validationPatterns;
	}

	public void setValidationPatterns(int newPatterns)
	{
		if (parent != null)
			parent.setValidationPatterns(newPatterns);
		else
			validationPatterns = newPatterns;
	}

	public int getNumOfPatterns()
	{
		if (parent != null)
			return parent.getNumOfPatterns();
		if (isValidation() && !isTrainingDataForValidation())
			return validationPatterns;
		else
			return patterns;
	}

	public TreeSet check()
	{
		TreeSet checks = new TreeSet();
		if (getLearningRate() <= 0.0D && isLearning())
			checks.add(new NetCheck(0, "Learning Rate must be greater than zero.", this));
		if (isValidation() && getValidationPatterns() <= 0)
			checks.add(new NetCheck(0, "Validation Patterns not set.", this));
		if (isLearning() && getTrainingPatterns() <= 0)
			checks.add(new NetCheck(0, "Training Patterns not set.", this));
		if (!isValidation() && getTrainingPatterns() <= 0)
			checks.add(new NetCheck(0, "Training Patterns not set.", this));
		if (getTotCicles() <= 0)
			checks.add(new NetCheck(0, "TotCicles (epochs) not set.", this));
		return checks;
	}

	public boolean isSupervised()
	{
		if (parent != null)
			return parent.isSupervised();
		else
			return supervisioned;
	}

	public void setSupervised(boolean supervised)
	{
		if (parent != null)
			parent.setSupervised(supervised);
		else
			supervisioned = supervised;
	}

	public int getBatchSize()
	{
		if (parent != null)
			return parent.getBatchSize();
		else
			return batchSize;
	}

	public void setBatchSize(int i)
	{
		if (parent != null)
			parent.setBatchSize(i);
		else
			batchSize = i;
	}

	public int getLearningMode()
	{
		return learningMode;
	}

	public void setLearningMode(int learningMode)
	{
		this.learningMode = learningMode;
	}

	public Learner getLearner(int index)
	{
		Learner myLearner = null;
		List learnersVect = getLearners();
		if (index < learnersVect.size() && index >= 0)
		{
			String myClassName = (String)learnersVect.get(index);
			try
			{
				Class myClass = Class.forName(myClassName);
				myLearner = (Learner)myClass.newInstance();
			}
			catch (ClassNotFoundException cnfe)
			{
				log.error((new StringBuilder("Class ")).append(myClassName).append(" not found").toString());
			}
			catch (InstantiationException ie)
			{
				log.error((new StringBuilder("Error instantiating the class ")).append(myClassName).toString());
			}
			catch (IllegalAccessException iae)
			{
				log.error((new StringBuilder("Illegal access instantiating the class ")).append(myClassName).toString());
			}
		}
		if (myLearner == null)
			myLearner = new BasicLearner();
		myLearner.setMonitor(this);
		return myLearner;
	}

	public Learner getLearner()
	{
		Learner myLearner = null;
		if (theLearnerFactory != null)
		{
			myLearner = theLearnerFactory.getLearner(this);
			myLearner.setMonitor(this);
		}
		if (myLearner == null)
			myLearner = getLearner(getLearningMode());
		return myLearner;
	}

	protected List getLearners()
	{
		if (learners == null)
			learners = new ArrayList(10);
		return learners;
	}

	protected void setLearners(List learners)
	{
		this.learners = learners;
	}

	public void addLearner(int i, String learner)
	{
		if (!getLearners().contains(learner))
			getLearners().add(i, learner);
	}

	public Object getParam(String key)
	{
		if (params == null)
			return null;
		else
			return params.get(key);
	}

	public void setParam(String key, Object obj)
	{
		if (params == null)
			params = new Hashtable();
		else
		if (params.containsKey(key))
			params.remove(key);
		params.put(key, obj);
	}

	public String[] getKeys()
	{
		if (params == null)
			return null;
		String keys[] = new String[params.keySet().size()];
		Enumeration myEnum = params.keys();
		for (int i = 0; myEnum.hasMoreElements(); i++)
			keys[i] = (String)myEnum.nextElement();

		return keys;
	}

	public void setUseRMSE(boolean aMode)
	{
		useRMSE = aMode;
	}

	public boolean isUseRMSE()
	{
		return useRMSE;
	}

	public void setLearnerFactory(LearnerFactory aFactory)
	{
		theLearnerFactory = aFactory;
	}

	public boolean isSingleThreadMode()
	{
		if (parent != null)
			return parent.isSingleThreadMode();
		else
			return singleThreadMode;
	}

	public void setSingleThreadMode(boolean singleThreadMode)
	{
		if (parent != null)
			parent.setSingleThreadMode(singleThreadMode);
		else
			this.singleThreadMode = singleThreadMode;
	}

}
