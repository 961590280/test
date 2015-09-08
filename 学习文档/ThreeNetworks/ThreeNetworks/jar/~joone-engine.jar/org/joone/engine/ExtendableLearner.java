// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ExtendableLearner.java

package org.joone.engine;

import java.util.ArrayList;
import java.util.List;
import org.joone.engine.extenders.DeltaRuleExtender;
import org.joone.engine.extenders.GradientExtender;
import org.joone.engine.extenders.UpdateWeightExtender;

// Referenced classes of package org.joone.engine:
//			AbstractLearner, LearnableLayer, LearnableSynapse, Matrix, 
//			Monitor

public class ExtendableLearner extends AbstractLearner
{

	protected List theDeltaRuleExtenders;
	protected List theGradientExtenders;
	protected UpdateWeightExtender theUpdateWeightExtender;

	public ExtendableLearner()
	{
		theDeltaRuleExtenders = new ArrayList();
		theGradientExtenders = new ArrayList();
	}

	public final void requestBiasUpdate(double currentGradientOuts[])
	{
		preBiasUpdate(currentGradientOuts);
		int rowsNum = getLayer().getRows();
		for (int i = 0; i < rowsNum; i++)
		{
			double myDelta = getDelta(currentGradientOuts, i);
			updateBias(i, myDelta);
		}

		postBiasUpdate(currentGradientOuts);
	}

	public final void requestWeightUpdate(double currentPattern[], double currentInps[])
	{
		preWeightUpdate(currentPattern, currentInps);
		LearnableSynapse synapse = getSynapse();
		Matrix weights = synapse.getWeights();
		boolean isEnabled[][] = weights.getEnabled();
		boolean isFixed[][] = weights.getFixed();
		int inputDim = synapse.getInputDimension();
		int outputDim = synapse.getOutputDimension();
		for (int x = 0; x < inputDim; x++)
		{
			for (int y = 0; y < outputDim; y++)
				if (!isFixed[x][y] && isEnabled[x][y])
				{
					double myDelta = getDelta(currentInps, x, currentPattern, y);
					updateWeight(x, y, myDelta);
				}

		}

		postWeightUpdate(currentPattern, currentInps);
	}

	protected void updateBias(int j, double aDelta)
	{
		theUpdateWeightExtender.updateBias(j, aDelta);
	}

	protected void updateWeight(int j, int k, double aDelta)
	{
		theUpdateWeightExtender.updateWeight(j, k, aDelta);
	}

	protected double getDelta(double currentGradientOuts[], int j)
	{
		double myDelta = getDefaultDelta(currentGradientOuts, j);
		int dreSize = theDeltaRuleExtenders.size();
		for (int i = 0; i < dreSize; i++)
		{
			DeltaRuleExtender dre = (DeltaRuleExtender)theDeltaRuleExtenders.get(i);
			if (dre.isEnabled())
				myDelta = dre.getDelta(currentGradientOuts, j, myDelta);
		}

		return myDelta;
	}

	public double getDefaultDelta(double currentGradientOuts[], int j)
	{
		return getLearningRate(j) * getGradientBias(currentGradientOuts, j);
	}

	protected double getDelta(double currentInps[], int j, double currentPattern[], int k)
	{
		double myDelta = getDefaultDelta(currentInps, j, currentPattern, k);
		for (int i = 0; i < theDeltaRuleExtenders.size(); i++)
		{
			DeltaRuleExtender deltaRuleExtender = (DeltaRuleExtender)theDeltaRuleExtenders.get(i);
			if (deltaRuleExtender.isEnabled())
				myDelta = deltaRuleExtender.getDelta(currentInps, j, currentPattern, k, myDelta);
		}

		return myDelta;
	}

	public double getDefaultDelta(double currentInps[], int j, double currentPattern[], int k)
	{
		return getLearningRate(j, k) * getGradientWeight(currentInps, j, currentPattern, k);
	}

	protected double getLearningRate(int j)
	{
		return getMonitor().getLearningRate();
	}

	protected double getLearningRate(int j, int k)
	{
		return getMonitor().getLearningRate();
	}

	public double getGradientBias(double currentGradientOuts[], int j)
	{
		double myGradient = getDefaultGradientBias(currentGradientOuts, j);
		for (int i = 0; i < theGradientExtenders.size(); i++)
		{
			GradientExtender gradientExtender = (GradientExtender)theGradientExtenders.get(i);
			if (gradientExtender.isEnabled())
				myGradient = gradientExtender.getGradientBias(currentGradientOuts, j, myGradient);
		}

		return myGradient;
	}

	public double getDefaultGradientBias(double currentGradientOuts[], int j)
	{
		return currentGradientOuts[j];
	}

	public double getGradientWeight(double currentInps[], int j, double currentPattern[], int k)
	{
		double myGradient = getDefaultGradientWeight(currentInps, j, currentPattern, k);
		for (int i = 0; i < theGradientExtenders.size(); i++)
		{
			GradientExtender ge = (GradientExtender)theGradientExtenders.get(i);
			if (ge.isEnabled())
				myGradient = ge.getGradientWeight(currentInps, j, currentPattern, k, myGradient);
		}

		return myGradient;
	}

	public double getDefaultGradientWeight(double currentInps[], int j, double currentPattern[], int k)
	{
		return currentInps[j] * currentPattern[k];
	}

	protected final void preBiasUpdate(double currentGradientOuts[])
	{
		preBiasUpdateImpl(currentGradientOuts);
		if (theUpdateWeightExtender != null && theUpdateWeightExtender.isEnabled())
			theUpdateWeightExtender.preBiasUpdate(currentGradientOuts);
		for (int i = 0; i < theDeltaRuleExtenders.size(); i++)
		{
			DeltaRuleExtender deltaRuleExtender = (DeltaRuleExtender)theDeltaRuleExtenders.get(i);
			if (deltaRuleExtender.isEnabled())
				deltaRuleExtender.preBiasUpdate(currentGradientOuts);
		}

		for (int i = 0; i < theGradientExtenders.size(); i++)
		{
			GradientExtender ge = (GradientExtender)theGradientExtenders.get(i);
			if (ge.isEnabled())
				ge.preBiasUpdate(currentGradientOuts);
		}

	}

	protected void preBiasUpdateImpl(double ad[])
	{
	}

	protected final void preWeightUpdate(double currentPattern[], double currentInps[])
	{
		preWeightUpdateImpl(currentPattern, currentInps);
		if (theUpdateWeightExtender != null && theUpdateWeightExtender.isEnabled())
			theUpdateWeightExtender.preWeightUpdate(currentInps, currentPattern);
		for (int i = 0; i < theDeltaRuleExtenders.size(); i++)
		{
			DeltaRuleExtender deltaRuleExtender = (DeltaRuleExtender)theDeltaRuleExtenders.get(i);
			if (deltaRuleExtender.isEnabled())
				deltaRuleExtender.preWeightUpdate(currentInps, currentPattern);
		}

		for (int i = 0; i < theGradientExtenders.size(); i++)
		{
			GradientExtender ge = (GradientExtender)theGradientExtenders.get(i);
			if (ge.isEnabled())
				ge.preWeightUpdate(currentInps, currentPattern);
		}

	}

	protected void preWeightUpdateImpl(double ad[], double ad1[])
	{
	}

	protected final void postBiasUpdate(double currentGradientOuts[])
	{
		for (int i = 0; i < theGradientExtenders.size(); i++)
		{
			GradientExtender ge = (GradientExtender)theGradientExtenders.get(i);
			if (ge.isEnabled())
				ge.postBiasUpdate(currentGradientOuts);
		}

		for (int i = 0; i < theDeltaRuleExtenders.size(); i++)
		{
			DeltaRuleExtender deltaRuleExtender = (DeltaRuleExtender)theDeltaRuleExtenders.get(i);
			if (deltaRuleExtender.isEnabled())
				deltaRuleExtender.postBiasUpdate(currentGradientOuts);
		}

		if (theUpdateWeightExtender != null && theUpdateWeightExtender.isEnabled())
			theUpdateWeightExtender.postBiasUpdate(currentGradientOuts);
		postBiasUpdateImpl(currentGradientOuts);
	}

	protected void postBiasUpdateImpl(double ad[])
	{
	}

	protected final void postWeightUpdate(double currentPattern[], double currentInps[])
	{
		for (int i = 0; i < theGradientExtenders.size(); i++)
		{
			GradientExtender ge = (GradientExtender)theGradientExtenders.get(i);
			if (ge.isEnabled())
				ge.postWeightUpdate(currentInps, currentPattern);
		}

		for (int i = 0; i < theDeltaRuleExtenders.size(); i++)
		{
			DeltaRuleExtender dre = (DeltaRuleExtender)theDeltaRuleExtenders.get(i);
			if (dre.isEnabled())
				dre.postWeightUpdate(currentInps, currentPattern);
		}

		if (theUpdateWeightExtender != null && theUpdateWeightExtender.isEnabled())
			theUpdateWeightExtender.postWeightUpdate(currentInps, currentPattern);
		postWeightUpdateImpl(currentInps, currentInps);
	}

	protected void postWeightUpdateImpl(double ad[], double ad1[])
	{
	}

	public void addDeltaRuleExtender(DeltaRuleExtender aDeltaRuleExtender)
	{
		theDeltaRuleExtenders.add(aDeltaRuleExtender);
		aDeltaRuleExtender.setLearner(this);
	}

	public void addGradientExtender(GradientExtender aGradientExtender)
	{
		theGradientExtenders.add(aGradientExtender);
		aGradientExtender.setLearner(this);
	}

	public void setUpdateWeightExtender(UpdateWeightExtender anUpdateWeightExtender)
	{
		theUpdateWeightExtender = anUpdateWeightExtender;
		theUpdateWeightExtender.setLearner(this);
	}

	public UpdateWeightExtender getUpdateWeightExtender()
	{
		return theUpdateWeightExtender;
	}
}
