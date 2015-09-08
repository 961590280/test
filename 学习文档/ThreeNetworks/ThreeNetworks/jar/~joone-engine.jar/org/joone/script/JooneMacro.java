// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JooneMacro.java

package org.joone.script;

import java.io.Serializable;

public class JooneMacro
	implements Serializable, Cloneable
{

	static final long serialVersionUID = 0x5848ce6f61ac4635L;
	private String text;
	private boolean event;
	private String name;

	public JooneMacro()
	{
	}

	public String toString()
	{
		return text;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public boolean isEventMacro()
	{
		return event;
	}

	public void setEventMacro(boolean newValue)
	{
		event = newValue;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public synchronized Object clone()
	{
		JooneMacro newMacro = new JooneMacro();
		newMacro.setText(text);
		newMacro.setName(name);
		newMacro.setEventMacro(event);
		return newMacro;
	}
}
