// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ToolElement.java

package org.joone.edit;

import java.util.Hashtable;

public class ToolElement
{

	protected Hashtable params;
	protected String type;

	public ToolElement()
	{
		params = new Hashtable();
	}

	public ToolElement(String newType)
	{
		this();
		type = newType;
	}

	public Hashtable getParams()
	{
		return params;
	}

	public void setParam(Object key, Object value)
	{
		if (!params.containsKey(key))
			params.put(key, value);
	}

	public Object getParam(Object key)
	{
		return params.get(key);
	}

	public String getType()
	{
		return type;
	}

	public void setType(String newType)
	{
		type = newType;
	}
}
