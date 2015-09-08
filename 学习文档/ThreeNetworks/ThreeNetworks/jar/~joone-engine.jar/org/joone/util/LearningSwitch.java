// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LearningSwitch.java

package org.joone.util;

import java.util.TreeSet;
import org.joone.engine.Monitor;
import org.joone.engine.Pattern;
import org.joone.io.*;
import org.joone.net.NetCheck;

public class LearningSwitch extends InputSwitchSynapse
{

	private StreamInputSynapse trainingSet;
	private StreamInputSynapse validationSet;
	private boolean validation;
	private static final long serialVersionUID = 0xdf885ec4c0d22439L;
	private int validationPatterns;
	private int trainingPatterns;

	public LearningSwitch()
	{
		validation = false;
	}

	public synchronized boolean addTrainingSet(StreamInputSynapse tSet)
	{
		if (trainingSet != null)
			return false;
		if (super.addInputSynapse(tSet))
		{
			trainingSet = tSet;
			super.setDefaultSynapse(trainingSet);
			super.reset();
			validation = false;
			return true;
		} else
		{
			return false;
		}
	}

	public synchronized boolean addValidationSet(StreamInputSynapse vSet)
	{
		if (validationSet != null)
			return false;
		if (super.addInputSynapse(vSet))
		{
			validationSet = vSet;
			return true;
		} else
		{
			return false;
		}
	}

	public synchronized void removeTrainingSet()
	{
		if (trainingSet != null)
		{
			super.removeInputSynapse(trainingSet.getName());
			trainingSet = null;
		}
	}

	public synchronized void removeValidationSet()
	{
		if (validationSet != null)
		{
			super.removeInputSynapse(validationSet.getName());
			validationSet = null;
		}
	}

	public Pattern fwdGet()
	{
		if (getMonitor().isValidation() && !getMonitor().isTrainingDataForValidation())
			super.setActiveSynapse(validationSet);
		else
			super.setActiveSynapse(trainingSet);
		return super.fwdGet();
	}

	public Pattern fwdGet(InputConnector conn)
	{
		if (getMonitor().isValidation() && !getMonitor().isTrainingDataForValidation())
			super.setActiveSynapse(validationSet);
		else
			super.setActiveSynapse(trainingSet);
		return super.fwdGet(conn);
	}

	public TreeSet check()
	{
		TreeSet checks = super.check();
		if (trainingSet == null)
			checks.add(new NetCheck(0, "Training set parameter not set", this));
		if (validationSet == null)
			checks.add(new NetCheck(0, "Validation set parameter not set", this));
		return checks;
	}

	public StreamInputSynapse getTrainingSet()
	{
		return trainingSet;
	}

	public void setTrainingSet(StreamInputSynapse trainingSet)
	{
		this.trainingSet = trainingSet;
	}

	public StreamInputSynapse getValidationSet()
	{
		return validationSet;
	}

	public void setValidationSet(StreamInputSynapse validationSet)
	{
		this.validationSet = validationSet;
	}
}
