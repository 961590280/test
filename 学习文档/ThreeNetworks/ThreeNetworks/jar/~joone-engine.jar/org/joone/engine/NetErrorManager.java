// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NetErrorManager.java

package org.joone.engine;


// Referenced classes of package org.joone.engine:
//			Monitor

public class NetErrorManager
{

	public NetErrorManager(Monitor mon, String errMsg)
	{
		if (mon != null)
			mon.fireNetStoppedError(errMsg);
	}
}
