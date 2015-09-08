// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NetChecker.java

package org.joone.net;

import java.util.Iterator;
import java.util.TreeSet;

// Referenced classes of package org.joone.net:
//			NeuralNet, NetCheck

public class NetChecker
{

	private final NeuralNet netToCheck;

	public NetChecker(NeuralNet netToCheckArg)
	{
		netToCheck = netToCheckArg;
	}

	public TreeSet check()
	{
		return netToCheck.check();
	}

	public boolean hasErrors()
	{
		TreeSet checks = netToCheck.check();
		for (Iterator iter = checks.iterator(); iter.hasNext();)
		{
			NetCheck netCheck = (NetCheck)iter.next();
			if (netCheck.isFatal())
				return true;
		}

		return false;
	}
}
