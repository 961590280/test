// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LoggerFactory.java

package org.joone.log;

import java.security.AccessControlException;

// Referenced classes of package org.joone.log:
//			ILogger, JooneLogger

public class LoggerFactory
{

	public LoggerFactory()
	{
	}

	public static ILogger getLogger(Class cls)
	{
		ILogger iLog = null;
		String logger = null;
		try
		{
			logger = System.getProperty("org.joone.logger");
			if (logger != null)
			{
				iLog = (ILogger)Class.forName(logger).newInstance();
				iLog.setParentClass(cls);
			}
		}
		catch (AccessControlException accesscontrolexception) { }
		catch (ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace();
			return null;
		}
		catch (InstantiationException ie)
		{
			ie.printStackTrace();
			return null;
		}
		catch (IllegalAccessException iae)
		{
			iae.printStackTrace();
			return null;
		}
		if (logger == null)
		{
			iLog = new JooneLogger();
			iLog.setParentClass(cls);
		}
		return iLog;
	}
}
