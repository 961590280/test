// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MemoryOutputSynapse.java

package org.joone.io;

import java.util.Vector;
import org.joone.engine.*;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.io:
//			StreamOutputSynapse

public class MemoryOutputSynapse extends StreamOutputSynapse
{

	static final long serialVersionUID = 0x12a20cb971e30933L;
	private static final ILogger log = LoggerFactory.getLogger(org/joone/engine/Synapse);
	private Fifo patterns;
	private boolean zeroPattern;

	public MemoryOutputSynapse()
	{
		zeroPattern = false;
	}

	private synchronized Vector getPatterns()
	{
		if (patterns == null)
			patterns = new Fifo();
		return (Vector)patterns.clone();
	}

	public synchronized double[] getLastPattern()
	{
		while (!zeroPattern) 
			try
			{
				wait();
			}
			catch (InterruptedException interruptedexception) { }
		int size = patterns.size();
		zeroPattern = false;
		if (size > 0)
		{
			Pattern pOut = (Pattern)patterns.elementAt(size - 1);
			return pOut.getArray();
		} else
		{
			return null;
		}
	}

	public synchronized double[] getNextPattern()
	{
		while (getPatterns().isEmpty()) 
			try
			{
				wait();
			}
			catch (InterruptedException interruptedexception) { }
		Pattern pOut = (Pattern)patterns.pop();
		return pOut.getArray();
	}

	public synchronized Vector getAllPatterns()
	{
		getLastPattern();
		return getPatterns();
	}

	public synchronized void write(Pattern pattern)
	{
		count = pattern.getCount();
		if (count == 1)
			patterns = new Fifo();
		if (!pattern.isMarkedAsStoppingPattern())
		{
			patterns.push(pattern);
			zeroPattern = false;
		} else
		{
			zeroPattern = true;
		}
		notifyAll();
	}

}
