// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BatchLearner.java

package org.joone.engine;

import org.joone.engine.extenders.BatchModeExtender;
import org.joone.engine.extenders.MomentumExtender;
import org.joone.engine.extenders.UpdateWeightExtender;

// Referenced classes of package org.joone.engine:
//			ExtendableLearner, LearnableLayer, LearnableSynapse

public class BatchLearner extends ExtendableLearner
{

	public BatchLearner()
	{
		setUpdateWeightExtender(new BatchModeExtender());
		addDeltaRuleExtender(new MomentumExtender());
	}

	/**
	 * @deprecated Method BatchLearner is deprecated
	 */

	public BatchLearner(int batchSize)
	{
		setBatchSizeInternal(batchSize);
	}

	/**
	 * @deprecated Method initiateNewBatch is deprecated
	 */

	public void initiateNewBatch()
	{
		if (learnable instanceof LearnableLayer)
			theUpdateWeightExtender.preBiasUpdate(null);
		else
		if (learnable instanceof LearnableSynapse)
			theUpdateWeightExtender.preWeightUpdate(null, null);
	}

	/**
	 * @deprecated Method setBatchSize is deprecated
	 */

	public void setBatchSize(int newBatchSize)
	{
		setBatchSizeInternal(newBatchSize);
	}

	/**
	 * @deprecated Method setBatchSizeInternal is deprecated
	 */

	private void setBatchSizeInternal(int newBatchSize)
	{
		((BatchModeExtender)theUpdateWeightExtender).setBatchSize(newBatchSize);
	}

	/**
	 * @deprecated Method getBatchSize is deprecated
	 */

	public int getBatchSize()
	{
		return ((BatchModeExtender)theUpdateWeightExtender).getBatchSize();
	}
}
