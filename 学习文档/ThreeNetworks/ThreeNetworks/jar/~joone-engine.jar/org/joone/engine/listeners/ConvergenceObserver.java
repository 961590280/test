// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConvergenceObserver.java

package org.joone.engine.listeners;

import java.util.ArrayList;
import java.util.List;
import org.joone.engine.Monitor;
import org.joone.util.MonitorPlugin;

// Referenced classes of package org.joone.engine.listeners:
//			ConvergenceEvent, ConvergenceListener

public abstract class ConvergenceObserver extends MonitorPlugin
{

	protected boolean disableCurrentConvergence;
	private List listeners;

	public ConvergenceObserver()
	{
		disableCurrentConvergence = false;
		listeners = new ArrayList();
	}

	public void addConvergenceListener(ConvergenceListener aListener)
	{
		if (!listeners.contains(aListener))
			listeners.add(aListener);
	}

	public void removeConvergenceListener(ConvergenceListener aListener)
	{
		listeners.remove(aListener);
	}

	protected void fireNetConverged(Monitor aMonitor)
	{
		Object myList[];
		synchronized (this)
		{
			myList = listeners.toArray();
		}
		ConvergenceEvent myEvent = new ConvergenceEvent(aMonitor);
		for (int i = 0; i < myList.length; i++)
			((ConvergenceListener)myList[i]).netConverged(myEvent, this);

	}

	public void disableCurrentConvergence()
	{
		disableCurrentConvergence = true;
	}
}
