// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BiasedLinearLayer.java

package org.joone.engine;


// Referenced classes of package org.joone.engine:
//			SimpleLayer, LearnableLayer, Learner, Matrix

public class BiasedLinearLayer extends SimpleLayer
	implements LearnableLayer
{

	public BiasedLinearLayer()
	{
	}

	public BiasedLinearLayer(String anElemName)
	{
		super(anElemName);
	}

	public void backward(double pattern[])
	{
		int n = getRows();
		for (int x = 0; x < n; x++)
			gradientOuts[x] = pattern[x];

		myLearner.requestBiasUpdate(gradientOuts);
	}

	public void forward(double pattern[])
	{
		int n = getRows();
		for (int x = 0; x < n; x++)
			outs[x] = pattern[x] + bias.value[x][0];

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
