// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SigmoidLayer.java

package org.joone.engine;

import org.joone.exception.JooneRuntimeException;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.engine:
//			SimpleLayer, LearnableLayer, Learner, Matrix

public class SigmoidLayer extends SimpleLayer
	implements LearnableLayer
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/engine/SigmoidLayer);
	private static final long serialVersionUID = 0x8740bbd377674d20L;
	private double flatSpotConstant;

	public SigmoidLayer()
	{
		flatSpotConstant = 0.0D;
		learnable = true;
	}

	public SigmoidLayer(String ElemName)
	{
		this();
		setLayerName(ElemName);
	}

	public void backward(double pattern[])
		throws JooneRuntimeException
	{
		super.backward(pattern);
		int n = getRows();
		double flatSpotConst = getFlatSpotConstant();
		for (int x = 0; x < n; x++)
			gradientOuts[x] = pattern[x] * (outs[x] * (1.0D - outs[x]) + flatSpotConst);

		myLearner.requestBiasUpdate(gradientOuts);
	}

	public void forward(double pattern[])
		throws JooneRuntimeException
	{
		int currentlyProcessed = -1;
		int n = getRows();
		try
		{
			for (currentlyProcessed = 0; currentlyProcessed < n; currentlyProcessed++)
			{
				double in = pattern[currentlyProcessed] + bias.value[currentlyProcessed][0];
				outs[currentlyProcessed] = 1.0D / (1.0D + Math.exp(-in));
			}

		}
		catch (Exception aioobe)
		{
			String msg;
			log.error(msg = (new StringBuilder("Exception thrown while processing the element ")).append(currentlyProcessed).append(" of the array. Value is : ").append(pattern[currentlyProcessed]).append(" Exception thrown is ").append(aioobe.getClass().getName()).append(". Message is ").append(aioobe.getMessage()).toString());
			throw new JooneRuntimeException(msg, aioobe);
		}
	}

	/**
	 * @deprecated Method getLearner is deprecated
	 */

	public Learner getLearner()
	{
		learnable = true;
		return super.getLearner();
	}

	public void setFlatSpotConstant(double aConstant)
	{
		flatSpotConstant = aConstant;
	}

	public double getFlatSpotConstant()
	{
		return flatSpotConstant;
	}

}
