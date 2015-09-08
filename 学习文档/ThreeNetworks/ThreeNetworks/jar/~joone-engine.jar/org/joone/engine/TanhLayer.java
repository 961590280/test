// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TanhLayer.java

package org.joone.engine;

import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.engine:
//			SimpleLayer, LearnableLayer, Learner, Matrix

public class TanhLayer extends SimpleLayer
	implements LearnableLayer
{

	private static final long serialVersionUID = 0xe337f984bd529b0eL;
	private static final ILogger log = LoggerFactory.getLogger(org/joone/engine/TanhLayer);
	private double flatSpotConstant;

	public TanhLayer()
	{
		flatSpotConstant = 0.0D;
		learnable = true;
	}

	public TanhLayer(String name)
	{
		this();
		setLayerName(name);
	}

	public void backward(double pattern[])
	{
		super.backward(pattern);
		int n = getRows();
		double flatSpotConst = getFlatSpotConstant();
		for (int x = 0; x < n; x++)
			gradientOuts[x] = pattern[x] * ((1.0D + outs[x]) * (1.0D - outs[x]) + flatSpotConst);

		myLearner.requestBiasUpdate(gradientOuts);
	}

	public void forward(double pattern[])
	{
		int n = getRows();
		for (int x = 0; x < n; x++)
			outs[x] = -1D + 2D / (1.0D + Math.exp(-2D * (pattern[x] + bias.value[x][0])));

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
