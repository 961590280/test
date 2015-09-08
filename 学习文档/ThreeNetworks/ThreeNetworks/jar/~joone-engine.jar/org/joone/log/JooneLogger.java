// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JooneLogger.java

package org.joone.log;

import java.io.PrintStream;

// Referenced classes of package org.joone.log:
//			ILogger

public class JooneLogger
	implements ILogger
{

	protected Class pClass;

	public JooneLogger()
	{
	}

	public void debug(Object obj)
	{
		System.out.println(formatMsg("DEBUG", (String)obj));
	}

	public void debug(Object obj, Throwable thr)
	{
		System.out.println(formatMsg("DEBUG", (String)obj));
		thr.printStackTrace();
	}

	public void error(Object obj)
	{
		System.out.println(formatMsg("ERROR", (String)obj));
	}

	public void error(Object obj, Throwable thr)
	{
		System.out.println(formatMsg("ERROR", (String)obj));
		thr.printStackTrace();
	}

	public void fatal(Object obj)
	{
		System.out.println(formatMsg("FATAL", (String)obj));
	}

	public void fatal(Object obj, Throwable thr)
	{
		System.out.println(formatMsg("FATAL", (String)obj));
		thr.printStackTrace();
	}

	public void info(Object obj)
	{
		System.out.println(formatMsg("INFO", (String)obj));
	}

	public void info(Object obj, Throwable thr)
	{
		System.out.println(formatMsg("INFO", (String)obj));
		thr.printStackTrace();
	}

	public void warn(Object obj)
	{
		System.out.println(formatMsg("WARN", (String)obj));
	}

	public void warn(Object obj, Throwable thr)
	{
		System.out.println(formatMsg("WARN", (String)obj));
		thr.printStackTrace();
	}

	public void setParentClass(Class cls)
	{
		pClass = cls;
	}

	protected String formatMsg(String sev, String msg)
	{
		return (new StringBuilder("[")).append(Thread.currentThread().getName()).append("] [").append(sev).append("] - ").append(pClass.getName()).append(" - ").append(msg).toString();
	}
}
