// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TeacherSynapse.java

package org.joone.engine.learning;

import org.joone.engine.Monitor;
import org.joone.engine.Pattern;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.engine.learning:
//			AbstractTeacherSynapse

public class TeacherSynapse extends AbstractTeacherSynapse
{

	protected static final ILogger log = LoggerFactory.getLogger(org/joone/engine/learning/TeacherSynapse);
	protected transient double GlobalError;
	private static final long serialVersionUID = 0xedef7e9c461252deL;

	public TeacherSynapse()
	{
		GlobalError = 0.0D;
	}

	protected double calculateError(double aDesired, double anOutput, int anIndex)
	{
		double myError = aDesired - anOutput;
		GlobalError += myError * myError;
		return myError;
	}

	protected double calculateGlobalError()
	{
		double myError = GlobalError / (double)getMonitor().getNumOfPatterns();
		if (getMonitor().isUseRMSE())
			myError = Math.sqrt(myError);
		GlobalError = 0.0D;
		return myError;
	}

	public void fwdPut(Pattern pattern)
	{
		super.fwdPut(pattern);
		if (pattern.isMarkedAsStoppingPattern())
			GlobalError = 0.0D;
	}

}
