// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PatternBeanInfo.java

package org.joone.engine;

import java.beans.*;

// Referenced classes of package org.joone.engine:
//			Pattern

public class PatternBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_count = 0;
	private static final int PROPERTY_values = 1;
	private static final int METHOD_clone0 = 0;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public PatternBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/engine/Pattern, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[2];
		try
		{
			properties[0] = new PropertyDescriptor("count", org/joone/engine/Pattern, "getCount", "setCount");
			properties[1] = new PropertyDescriptor("values", org/joone/engine/Pattern, "getValues", "setValues");
		}
		catch (IntrospectionException introspectionexception) { }
		return properties;
	}

	private static EventSetDescriptor[] getEdescriptor()
	{
		EventSetDescriptor eventSets[] = new EventSetDescriptor[0];
		return eventSets;
	}

	private static MethodDescriptor[] getMdescriptor()
	{
		MethodDescriptor methods[] = new MethodDescriptor[1];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/engine/Pattern.getMethod("clone", new Class[0]));
			methods[0].setDisplayName("");
		}
		catch (Exception exception) { }
		return methods;
	}

	public BeanDescriptor getBeanDescriptor()
	{
		return getBdescriptor();
	}

	public PropertyDescriptor[] getPropertyDescriptors()
	{
		return getPdescriptor();
	}

	public EventSetDescriptor[] getEventSetDescriptors()
	{
		return getEdescriptor();
	}

	public MethodDescriptor[] getMethodDescriptors()
	{
		return getMdescriptor();
	}

	public int getDefaultPropertyIndex()
	{
		return -1;
	}

	public int getDefaultEventIndex()
	{
		return -1;
	}
}
