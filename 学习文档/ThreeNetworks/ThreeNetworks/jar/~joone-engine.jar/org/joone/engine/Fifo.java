// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Fifo.java

package org.joone.engine;

import java.util.EmptyStackException;
import java.util.Vector;

public class Fifo extends Vector
{

	private static final long serialVersionUID = 0xc95aa913144eb674L;

	public Fifo()
	{
	}

	public boolean empty()
	{
		return size() == 0;
	}

	public synchronized Object peek()
	{
		int len = size();
		if (len == 0)
			throw new EmptyStackException();
		else
			return elementAt(0);
	}

	public synchronized Object pop()
	{
		Object obj = peek();
		removeElementAt(0);
		return obj;
	}

	public Object push(Object item)
	{
		addElement(item);
		return item;
	}

	public synchronized int search(Object o)
	{
		int i = lastIndexOf(o);
		if (i >= 0)
			return size() - i;
		else
			return -1;
	}
}
