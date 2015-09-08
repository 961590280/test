// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JooneRuntimeException.java

package org.joone.exception;


public class JooneRuntimeException extends RuntimeException
{

	private Throwable initialException;
	private String msg;

	public JooneRuntimeException(String aMessage)
	{
		super(aMessage);
		initialException = null;
		msg = null;
	}

	public JooneRuntimeException(Throwable anInitialException)
	{
		initialException = null;
		msg = null;
		initialException = anInitialException;
	}

	public JooneRuntimeException(String aMessage, Throwable anInitialException)
	{
		super(aMessage);
		initialException = null;
		msg = null;
		initialException = anInitialException;
	}

	public String getLocalizedMessage()
	{
		return super.getLocalizedMessage();
	}

	public String getMessage()
	{
		StringBuffer buf = new StringBuffer(super.getMessage());
		return buf.toString();
	}
}
