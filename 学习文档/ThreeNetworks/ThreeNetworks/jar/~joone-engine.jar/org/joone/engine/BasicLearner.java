// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BasicLearner.java

package org.joone.engine;

import org.joone.engine.extenders.MomentumExtender;
import org.joone.engine.extenders.OnlineModeExtender;

// Referenced classes of package org.joone.engine:
//			ExtendableLearner

public class BasicLearner extends ExtendableLearner
{

	public BasicLearner()
	{
		setUpdateWeightExtender(new OnlineModeExtender());
		addDeltaRuleExtender(new MomentumExtender());
	}
}
