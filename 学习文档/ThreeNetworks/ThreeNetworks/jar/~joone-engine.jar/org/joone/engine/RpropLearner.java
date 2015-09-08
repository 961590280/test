// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RpropLearner.java

package org.joone.engine;

import org.joone.engine.extenders.BatchModeExtender;
import org.joone.engine.extenders.RpropExtender;

// Referenced classes of package org.joone.engine:
//			ExtendableLearner, RpropParameters

public class RpropLearner extends ExtendableLearner
{

	private RpropExtender theRpropExtender;

	public RpropLearner()
	{
		setUpdateWeightExtender(new BatchModeExtender());
		theRpropExtender = new RpropExtender();
		addDeltaRuleExtender(theRpropExtender);
	}

	public RpropLearner(RpropParameters aParameters)
	{
		theRpropExtender = new RpropExtender();
		theRpropExtender.setParameters(aParameters);
	}

	/**
	 * @deprecated Method reinit is deprecated
	 */

	protected void reinit()
	{
		theRpropExtender.reinit();
	}

	public RpropParameters getParameters()
	{
		return theRpropExtender.getParameters();
	}

	public void setParameters(RpropParameters aParameters)
	{
		theRpropExtender.setParameters(aParameters);
	}

	protected double sign(double d)
	{
		if (d > 0.0D)
			return 1.0D;
		return d >= 0.0D ? 0.0D : -1D;
	}
}
