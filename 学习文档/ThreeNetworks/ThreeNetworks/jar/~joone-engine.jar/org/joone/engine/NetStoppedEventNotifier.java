// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NetStoppedEventNotifier.java

package org.joone.engine;


// Referenced classes of package org.joone.engine:
//			AbstractEventNotifier, Monitor

public class NetStoppedEventNotifier extends AbstractEventNotifier
{

	public NetStoppedEventNotifier(Monitor mon)
	{
		super(mon);
	}

	public void run()
	{
		if (monitor != null)
			monitor.fireNetStopped();
	}
}
