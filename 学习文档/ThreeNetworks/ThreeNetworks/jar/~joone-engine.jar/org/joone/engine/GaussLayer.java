// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GaussLayer.java

package org.joone.engine;

import org.joone.exception.JooneRuntimeException;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.engine:
//			SimpleLayer, LearnableLayer, Matrix, Learner

public class GaussLayer extends SimpleLayer
	implements LearnableLayer
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/engine/GaussLayer);

	public GaussLayer()
	{
		learnable = true;
	}

	public GaussLayer(String aName)
	{
		this();
		setLayerName(aName);
	}

	protected void forward(double aPattern[])
		throws JooneRuntimeException
	{
		int myRows = getRows();
		int i = 0;
		try
		{
			for (i = 0; i < myRows; i++)
			{
				double myNeuronInput = aPattern[i] + getBias().value[i][0];
				outs[i] = Math.exp(-myNeuronInput * myNeuronInput);
			}

		}
		catch (Exception aioobe)
		{
			String msg;
			log.error(msg = (new StringBuilder("Exception thrown while processing the element ")).append(i).append(" of the array. Value is : ").append(aPattern[i]).append(" Exception thrown is ").append(aioobe.getClass().getName()).append(". Message is ").append(aioobe.getMessage()).toString());
			throw new JooneRuntimeException(msg, aioobe);
		}
	}

	public void backward(double aPattern[])
		throws JooneRuntimeException
	{
		super.backward(aPattern);
		int myRows = getRows();
		int i = 0;
		for (i = 0; i < myRows; i++)
			gradientOuts[i] = aPattern[i] * -2D * inps[i] * outs[i];

		myLearner.requestBiasUpdate(gradientOuts);
	}

}
