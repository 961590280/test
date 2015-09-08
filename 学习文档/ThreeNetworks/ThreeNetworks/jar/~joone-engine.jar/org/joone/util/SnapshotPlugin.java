// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SnapshotPlugin.java

package org.joone.util;

import java.io.Serializable;
import org.joone.engine.Monitor;

// Referenced classes of package org.joone.util:
//			MonitorPlugin

public abstract class SnapshotPlugin extends MonitorPlugin
	implements Serializable
{

	private static final long serialVersionUID = 0xbd7008b48eb1aff1L;

	protected SnapshotPlugin()
	{
		setRate(100);
	}

	protected final void manageStart(Monitor mon)
	{
		if (getRate() == 0)
		{
			return;
		} else
		{
			doStart();
			doSnapshot();
			return;
		}
	}

	protected abstract void doStart();

	protected final void manageCycle(Monitor mon)
	{
		doSnapshot();
	}

	protected abstract void doSnapshot();

	protected final void manageStop(Monitor mon)
	{
		if (getRate() == 0)
			return;
		int current = mon.getTotCicles() - mon.getCurrentCicle();
		if (current % getRate() != 0)
			doSnapshot();
		doStop();
	}

	protected abstract void doStop();

	protected final void manageError(Monitor monitor)
	{
	}
}
