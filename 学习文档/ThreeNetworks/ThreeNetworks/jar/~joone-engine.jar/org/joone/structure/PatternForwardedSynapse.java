// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Nakayama.java

package org.joone.structure;

import org.joone.engine.Pattern;
import org.joone.engine.Synapse;

// Referenced classes of package org.joone.structure:
//			Nakayama

class PatternForwardedSynapse extends Synapse
{

	protected Nakayama nakayama;

	public PatternForwardedSynapse(Nakayama aNakayama)
	{
		nakayama = aNakayama;
	}

	public synchronized void fwdPut(Pattern pattern)
	{
		if (pattern.getCount() > -1)
		{
			nakayama.patternFinished();
			items++;
		}
	}

	protected void backward(double ad[])
	{
	}

	protected void forward(double ad[])
	{
	}

	protected void setArrays(int i, int j)
	{
	}

	protected void setDimensions(int i, int j)
	{
	}
}
