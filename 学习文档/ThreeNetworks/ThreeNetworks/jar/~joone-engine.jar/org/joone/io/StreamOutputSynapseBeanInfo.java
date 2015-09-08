// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StreamOutputSynapseBeanInfo.java

package org.joone.io;

import java.beans.*;

// Referenced classes of package org.joone.io:
//			StreamOutputSynapse

public class StreamOutputSynapseBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_buffered = 0;
	private static final int PROPERTY_enabled = 1;
	private static final int PROPERTY_separator = 2;
	private static final int PROPERTY_name = 3;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public StreamOutputSynapseBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/io/StreamOutputSynapse, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[4];
		try
		{
			properties[0] = new PropertyDescriptor("buffered", org/joone/io/StreamOutputSynapse, "isBuffered", "setBuffered");
			properties[1] = new PropertyDescriptor("enabled", org/joone/io/StreamOutputSynapse, "isEnabled", "setEnabled");
			properties[2] = new PropertyDescriptor("separator", org/joone/io/StreamOutputSynapse, "getSeparator", "setSeparator");
			properties[3] = new PropertyDescriptor("name", org/joone/io/StreamOutputSynapse, "getName", "setName");
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
		MethodDescriptor methods[] = new MethodDescriptor[0];
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
