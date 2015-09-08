// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RpropExtender.java

package org.joone.engine.extenders;

import org.joone.engine.*;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.engine.extenders:
//			DeltaRuleExtender, UpdateWeightExtender

public class RpropExtender extends DeltaRuleExtender
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/engine/extenders/RpropExtender);
	protected double theDeltas[][];
	protected double thePreviousGradients[][];
	protected RpropParameters theRpropParameters;
	protected double theSummedGradients[][];

	public RpropExtender()
	{
	}

	public void reinit()
	{
		ExtendableLearner learner = getLearner();
		if (learner.getMonitor().getLearningRate() != 1.0D)
			log.warn("RPROP learning rate should be equal to 1.");
		LearnableLayer layer = learner.getLayer();
		if (layer != null)
		{
			thePreviousGradients = new double[layer.getRows()][1];
			theSummedGradients = new double[thePreviousGradients.length][1];
			theDeltas = new double[thePreviousGradients.length][1];
		} else
		{
			LearnableSynapse synapse = learner.getSynapse();
			if (synapse != null)
			{
				int myRows = synapse.getInputDimension();
				int myCols = synapse.getOutputDimension();
				thePreviousGradients = new double[myRows][myCols];
				theSummedGradients = new double[myRows][myCols];
				theDeltas = new double[myRows][myCols];
			}
		}
		RpropParameters parameters = getParameters();
		for (int i = 0; i < theDeltas.length; i++)
		{
			for (int j = 0; j < theDeltas[0].length; j++)
				theDeltas[i][j] = parameters.getInitialDelta(i, j);

		}

	}

	public double getDelta(double currentGradientOuts[], int j, double aPreviousDelta)
	{
		double myDelta = 0.0D;
		theSummedGradients[j][0] -= aPreviousDelta;
		ExtendableLearner learner = getLearner();
		if (learner.getUpdateWeightExtender().storeWeightsBiases())
		{
			RpropParameters parameters = getParameters();
			if (thePreviousGradients[j][0] * theSummedGradients[j][0] > 0.0D)
			{
				theDeltas[j][0] = Math.min(theDeltas[j][0] * parameters.getEtaInc(), parameters.getMaxDelta());
				myDelta = -1D * sign(theSummedGradients[j][0]) * theDeltas[j][0];
				thePreviousGradients[j][0] = theSummedGradients[j][0];
			} else
			if (thePreviousGradients[j][0] * theSummedGradients[j][0] < 0.0D)
			{
				theDeltas[j][0] = Math.max(theDeltas[j][0] * parameters.getEtaDec(), parameters.getMinDelta());
				myDelta = -1D * learner.getLayer().getBias().delta[j][0];
				thePreviousGradients[j][0] = 0.0D;
			} else
			{
				myDelta = -1D * sign(theSummedGradients[j][0]) * theDeltas[j][0];
				thePreviousGradients[j][0] = theSummedGradients[j][0];
			}
			theSummedGradients[j][0] = 0.0D;
		}
		return myDelta;
	}

	public double getDelta(double currentInps[], int j, double currentPattern[], int k, double aPreviousDelta)
	{
		double myDelta = 0.0D;
		theSummedGradients[j][k] -= aPreviousDelta;
		ExtendableLearner learner = getLearner();
		if (learner.getUpdateWeightExtender().storeWeightsBiases())
		{
			RpropParameters parameters = getParameters();
			if (thePreviousGradients[j][k] * theSummedGradients[j][k] > 0.0D)
			{
				theDeltas[j][k] = Math.min(theDeltas[j][k] * parameters.getEtaInc(), parameters.getMaxDelta());
				myDelta = -1D * sign(theSummedGradients[j][k]) * theDeltas[j][k];
				thePreviousGradients[j][k] = theSummedGradients[j][k];
			} else
			if (thePreviousGradients[j][k] * theSummedGradients[j][k] < 0.0D)
			{
				theDeltas[j][k] = Math.max(theDeltas[j][k] * parameters.getEtaDec(), parameters.getMinDelta());
				myDelta = -1D * learner.getSynapse().getWeights().delta[j][k];
				thePreviousGradients[j][k] = 0.0D;
			} else
			{
				myDelta = -1D * sign(theSummedGradients[j][k]) * theDeltas[j][k];
				thePreviousGradients[j][k] = theSummedGradients[j][k];
			}
			theSummedGradients[j][k] = 0.0D;
		}
		return myDelta;
	}

	public void postBiasUpdate(double ad[])
	{
	}

	public void postWeightUpdate(double ad[], double ad1[])
	{
	}

	public void preBiasUpdate(double currentGradientOuts[])
	{
		if (theDeltas == null || theDeltas.length != getLearner().getLayer().getRows())
			reinit();
	}

	public void preWeightUpdate(double currentPattern[], double currentInps[])
	{
		LearnableSynapse synapse = getLearner().getSynapse();
		if (theDeltas == null || theDeltas.length != synapse.getInputDimension() || theDeltas[0].length != synapse.getOutputDimension())
			reinit();
	}

	public RpropParameters getParameters()
	{
		if (theRpropParameters == null)
			theRpropParameters = new RpropParameters();
		return theRpropParameters;
	}

	public void setParameters(RpropParameters aParameters)
	{
		theRpropParameters = aParameters;
	}

	protected double sign(double d)
	{
		if (d > 0.0D)
			return 1.0D;
		return d >= 0.0D ? 0.0D : -1D;
	}

}
