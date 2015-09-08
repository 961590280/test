// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LogarithmicLayer.java

package org.joone.engine;

import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.engine:
//			SimpleLayer, LearnableLayer, Matrix, Learner

public class LogarithmicLayer extends SimpleLayer
	implements LearnableLayer
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/engine/LogarithmicLayer);
	private static final long serialVersionUID = 0xbad81fe82fef9b64L;

	public LogarithmicLayer()
	{
		learnable = true;
	}

	public LogarithmicLayer(String elemName)
	{
		this();
		setLayerName(elemName);
	}

	protected void forward(double pattern[])
	{
		int n = getRows();
		for (int x = 0; x < n; x++)
		{
			double myNeuronInput = pattern[x] + getBias().value[x][0];
			if (myNeuronInput >= 0.0D)
				outs[x] = Math.log(1.0D + myNeuronInput);
			else
				outs[x] = -Math.log(1.0D - myNeuronInput);
		}

	}

	protected void backward(double pattern[])
	{
		super.backward(pattern);
		int n = getRows();
		for (int x = 0; x < n; x++)
		{
			double deriv;
			if (outs[x] >= 0.0D)
				deriv = 1.0D / (1.0D + outs[x]);
			else
				deriv = 1.0D / (1.0D - outs[x]);
			gradientOuts[x] = pattern[x] * deriv;
		}

		myLearner.requestBiasUpdate(gradientOuts);
	}

	/**
	 * @deprecated Method getLearner is deprecated
	 */

	public Learner getLearner()
	{
		learnable = true;
		return super.getLearner();
	}

}
