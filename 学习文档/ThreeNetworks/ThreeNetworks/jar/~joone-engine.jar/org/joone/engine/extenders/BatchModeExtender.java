// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BatchModeExtender.java

package org.joone.engine.extenders;

import org.joone.engine.*;

// Referenced classes of package org.joone.engine.extenders:
//			UpdateWeightExtender

public class BatchModeExtender extends UpdateWeightExtender
{

	private int theBatchSize;
	private int theRows;
	private int theColumns;
	private Matrix theMatrix;
	private int theCounter;

	public BatchModeExtender()
	{
		theBatchSize = -1;
		theRows = -1;
		theColumns = -1;
		theCounter = 0;
	}

	public void postBiasUpdate(double currentGradientOuts[])
	{
		if (storeWeightsBiases())
		{
			for (int x = 0; x < theRows; x++)
				theMatrix.value[x][0] += theMatrix.delta[x][0];

			getLearner().getLayer().setBias((Matrix)theMatrix.clone());
			resetDelta(theMatrix);
			theCounter = 0;
		}
	}

	public void postWeightUpdate(double currentPattern[], double currentInps[])
	{
		if (storeWeightsBiases())
		{
			for (int x = 0; x < theRows; x++)
			{
				for (int y = 0; y < theColumns; y++)
					theMatrix.value[x][y] += theMatrix.delta[x][y];

			}

			getLearner().getSynapse().setWeights((Matrix)theMatrix.clone());
			resetDelta(theMatrix);
			theCounter = 0;
		}
	}

	public void preBiasUpdate(double currentGradientOuts[])
	{
		if (theRows != getLearner().getLayer().getRows())
			initiateNewBatch();
		theCounter++;
	}

	public void preWeightUpdate(double currentPattern[], double currentInps[])
	{
		LearnableSynapse synapse = getLearner().getSynapse();
		if (theRows != synapse.getInputDimension() || theColumns != synapse.getOutputDimension())
			initiateNewBatch();
		theCounter++;
	}

	public void updateBias(int i, double aDelta)
	{
		theMatrix.delta[i][0] += aDelta;
	}

	public void updateWeight(int j, int k, double aDelta)
	{
		theMatrix.delta[j][k] += aDelta;
	}

	protected void resetDelta(Matrix aMatrix)
	{
		for (int r = 0; r < aMatrix.delta.length; r++)
		{
			for (int c = 0; c < aMatrix.delta[0].length; c++)
				aMatrix.delta[r][c] = 0.0D;

		}

	}

	protected void initiateNewBatch()
	{
		LearnableLayer layer = getLearner().getLayer();
		if (layer != null)
		{
			theRows = layer.getRows();
			theMatrix = (Matrix)layer.getBias().clone();
		} else
		{
			LearnableSynapse synapse = getLearner().getSynapse();
			if (synapse != null)
			{
				theRows = synapse.getInputDimension();
				theColumns = synapse.getOutputDimension();
				theMatrix = (Matrix)synapse.getWeights().clone();
			}
		}
		resetDelta(theMatrix);
		theCounter = 0;
	}

	/**
	 * @deprecated Method setBatchSize is deprecated
	 */

	public void setBatchSize(int aBatchSize)
	{
		theBatchSize = aBatchSize;
	}

	/**
	 * @deprecated Method getBatchSize is deprecated
	 */

	public int getBatchSize()
	{
		if (theBatchSize < 0)
			return getLearner().getMonitor().getBatchSize();
		else
			return theBatchSize;
	}

	public boolean storeWeightsBiases()
	{
		return theCounter >= getBatchSize();
	}
}
