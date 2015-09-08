// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Log4JLogger.java

package org.joone.log;

import org.apache.log4j.Logger;

// Referenced classes of package org.joone.log:
//			ILogger

public class Log4JLogger
	implements ILogger
{

	private Logger log;

	public Log4JLogger()
	{
		log = null;
	}

	public void debug(Object obj)
	{
		log.debug(obj);
	}

	public void debug(Object obj, Throwable thr)
	{
		log.debug(obj, thr);
	}

	public void error(Object obj)
	{
		log.error(obj);
	}

	public void error(Object obj, Throwable thr)
	{
		log.error(obj, thr);
	}

	public void fatal(Object obj)
	{
		log.fatal(obj);
	}

	public void fatal(Object obj, Throwable thr)
	{
		log.fatal(obj, thr);
	}

	public void info(Object obj)
	{
		log.info(obj);
	}

	public void info(Object obj, Throwable thr)
	{
		log.info(obj, thr);
	}

	public void warn(Object obj)
	{
		log.warn(obj);
	}

	public void warn(Object obj, Throwable thr)
	{
		log.warn(obj, thr);
	}

	public void setParentClass(Class cls)
	{
		log = Logger.getLogger(cls);
	}
}
