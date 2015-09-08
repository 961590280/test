// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AbstractEventNotifier.java

package org.joone.engine;


// Referenced classes of package org.joone.engine:
//			Monitor

public abstract class AbstractEventNotifier
	implements Runnable
{

	protected Monitor monitor;
	private Thread myThread;

	public AbstractEventNotifier(Monitor mon)
	{
		monitor = mon;
	}

	public abstract void run();

	public synchronized void start()
	{
		if (myThread == null)
		{
			myThread = new Thread(this);
			myThread.start();
		}
	}
}
