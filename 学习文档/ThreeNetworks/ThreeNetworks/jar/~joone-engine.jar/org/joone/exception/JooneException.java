// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JooneException.java

package org.joone.exception;


public class JooneException extends Exception
{

	private Exception initialException;

	public JooneException()
	{
		initialException = null;
	}

	public JooneException(String s)
	{
		super(s);
		initialException = null;
	}
}
