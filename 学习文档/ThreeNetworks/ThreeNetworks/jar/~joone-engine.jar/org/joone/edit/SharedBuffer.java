// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SharedBuffer.java

package org.joone.edit;

import org.joone.engine.Fifo;
import org.joone.engine.Pattern;

// Referenced classes of package org.joone.edit:
//			ChartingHandle

public class SharedBuffer
{

	private final int ERROR = 0;
	private final int CYCLE = 1;
	private double buffer[][];
	private Fifo fifoBuffer;
	private ChartingHandle handle;

	public SharedBuffer()
	{
		fifoBuffer = new Fifo();
	}

	public synchronized double[][] get()
	{
		while (fifoBuffer.isEmpty()) 
			try
			{
				wait();
			}
			catch (InterruptedException interruptedexception) { }
		buffer = extractBuffer(fifoBuffer);
		notifyAll();
		return buffer;
	}

	public synchronized double[][] getNoWait()
	{
		if (fifoBuffer.isEmpty())
		{
			return null;
		} else
		{
			buffer = extractBuffer(fifoBuffer);
			return buffer;
		}
	}

	private double[][] extractBuffer(Fifo fifo)
	{
		int size = fifo.size();
		double buff[][] = new double[size + 1][2];
		for (int i = 0; i < size; i++)
		{
			Pattern patt = (Pattern)fifo.pop();
			buff[i][1] = patt.getCount();
			buff[i][0] = patt.getArray()[0];
		}

		buff[size][0] = 0.0D;
		buff[size][1] = -1D;
		return buff;
	}

	public synchronized void put(double value, double cycle)
	{
		Pattern patt = new Pattern(value);
		patt.setCount((new Double(cycle)).intValue());
		fifoBuffer.push(patt);
		notifyAll();
	}

	public ChartingHandle getHandle()
	{
		return handle;
	}

	public void setHandle(ChartingHandle handle)
	{
		this.handle = handle;
	}
}
