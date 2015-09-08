// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Wrapper.java

package org.joone.edit;

import java.util.Vector;

// Referenced classes of package org.joone.edit:
//			UpdatableFigure

public class Wrapper
{

	protected Object bean;
	protected Vector changedProperties;
	protected String beanName;
	protected UpdatableFigure figure;

	public Wrapper(UpdatableFigure uf, Object b, String name)
	{
		figure = uf;
		bean = b;
		beanName = name;
	}

	Object getBean()
	{
		return bean;
	}

	String getBeanName()
	{
		return beanName;
	}

	public Vector getChangedProperties()
	{
		if (changedProperties == null)
			changedProperties = new Vector();
		return changedProperties;
	}

	public void updateFigure()
	{
		if (figure != null)
			figure.update();
	}
}
