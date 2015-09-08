// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BufferedSynapse.java

package org.joone.engine;


// Referenced classes of package org.joone.engine:
//			Synapse, Fifo, Pattern

public class BufferedSynapse extends Synapse
{

	private transient Fifo fifo;
	private static final long serialVersionUID = 0x900b64de82015e7eL;

	public BufferedSynapse()
	{
	}

	protected void backward(double ad[])
	{
	}

	protected void forward(double pattern[])
	{
		outs = pattern;
	}

	public Pattern fwdGet()
	{
		Object obj = getFwdLock();
		JVM INSTR monitorenter ;
		  goto _L1
_L3:
		try
		{
			fwdLock.wait();
			continue; /* Loop/switch isn't completed */
		}
		catch (InterruptedException e) { }
		return null;
_L1:
		if (items == 0) goto _L3; else goto _L2
_L2:
		Pattern pat;
		pat = (Pattern)fifo.pop();
		items = fifo.size();
		fwdLock.notifyAll();
		pat;
		obj;
		JVM INSTR monitorexit ;
		return;
		obj;
		JVM INSTR monitorexit ;
		throw ;
	}

	public synchronized void fwdPut(Pattern pattern)
	{
		m_pattern = pattern;
		inps = pattern.getArray();
		forward(inps);
		m_pattern.setArray(outs);
		fifo.push(m_pattern);
		items = fifo.size();
		notifyAll();
	}

	public synchronized Pattern revGet()
	{
		return null;
	}

	public synchronized void revPut(Pattern pattern1)
	{
	}

	protected void setArrays(int i, int j)
	{
	}

	protected void setDimensions(int i, int j)
	{
	}
}
