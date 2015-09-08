// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NetCheck.java

package org.joone.net;

import org.joone.engine.*;

public class NetCheck
	implements Comparable
{

	public static final int FATAL = 0;
	public static final int WARNING = 1;
	private int severity;
	private String message;
	private Object source;

	public NetCheck(int severityArg, String messageArg, Object objectArg)
	{
		setSeverity(severityArg);
		setMessage(messageArg);
		setSource(objectArg);
	}

	public String toString()
	{
		String className = getSource().getClass().getName();
		className = className.substring(1 + className.lastIndexOf("."));
		String instanceName;
		if (getSource() instanceof NeuralLayer)
			instanceName = ((NeuralLayer)getSource()).getLayerName();
		else
		if (getSource() instanceof InputPatternListener)
			instanceName = ((InputPatternListener)getSource()).getName();
		else
		if (getSource() instanceof OutputPatternListener)
			instanceName = ((OutputPatternListener)getSource()).getName();
		else
		if (getSource() instanceof Monitor)
			instanceName = "Monitor";
		else
			instanceName = getSource().toString();
		StringBuffer checkMessage = new StringBuffer();
		if (isFatal())
			checkMessage.append("FATAL - ");
		else
		if (isWarning())
			checkMessage.append("WARNING - ");
		checkMessage.append(className);
		checkMessage.append(" - ");
		if (instanceName != null && !instanceName.trim().equals(""))
		{
			checkMessage.append(instanceName);
			checkMessage.append(" - ");
		}
		checkMessage.append(getMessage());
		return checkMessage.toString();
	}

	public boolean isWarning()
	{
		return getSeverity() == 1;
	}

	public boolean isFatal()
	{
		return getSeverity() == 0;
	}

	public int compareTo(Object o)
	{
		if (o instanceof NetCheck)
		{
			NetCheck nc = (NetCheck)o;
			return toString().compareTo(nc.toString());
		} else
		{
			return 0;
		}
	}

	public Object getSource()
	{
		return source;
	}

	public void setSource(Object source)
	{
		this.source = source;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public int getSeverity()
	{
		return severity;
	}

	public void setSeverity(int severity)
	{
		this.severity = severity;
	}
}
