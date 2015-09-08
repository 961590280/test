// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileOutputSynapseBeanInfo.java

package org.joone.io;

import java.beans.*;

// Referenced classes of package org.joone.io:
//			FileOutputSynapse

public class FileOutputSynapseBeanInfo extends SimpleBeanInfo
{

	private static BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/io/FileOutputSynapse, null);
	private static final int PROPERTY_append = 0;
	private static final int PROPERTY_separator = 1;
	private static final int PROPERTY_name = 2;
	private static final int PROPERTY_buffered = 3;
	private static final int PROPERTY_fileName = 4;
	private static final int PROPERTY_enabled = 5;
	private static final int PROPERTY_monitor = 6;
	private static PropertyDescriptor properties[];
	private static EventSetDescriptor eventSets[] = new EventSetDescriptor[0];
	private static MethodDescriptor methods[] = new MethodDescriptor[0];
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public FileOutputSynapseBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		return properties;
	}

	private static EventSetDescriptor[] getEdescriptor()
	{
		return eventSets;
	}

	private static MethodDescriptor[] getMdescriptor()
	{
		return methods;
	}

	public BeanDescriptor getBeanDescriptor()
	{
		return beanDescriptor;
	}

	public PropertyDescriptor[] getPropertyDescriptors()
	{
		return properties;
	}

	public EventSetDescriptor[] getEventSetDescriptors()
	{
		return eventSets;
	}

	public MethodDescriptor[] getMethodDescriptors()
	{
		return methods;
	}

	public int getDefaultPropertyIndex()
	{
		return -1;
	}

	public int getDefaultEventIndex()
	{
		return -1;
	}

	static 
	{
		properties = new PropertyDescriptor[7];
		try
		{
			properties[0] = new PropertyDescriptor("append", org/joone/io/FileOutputSynapse, "isAppend", "setAppend");
			properties[1] = new PropertyDescriptor("separator", org/joone/io/FileOutputSynapse, "getSeparator", "setSeparator");
			properties[1].setExpert(true);
			properties[2] = new PropertyDescriptor("name", org/joone/io/FileOutputSynapse, "getName", "setName");
			properties[3] = new PropertyDescriptor("buffered", org/joone/io/FileOutputSynapse, "isBuffered", "setBuffered");
			properties[4] = new PropertyDescriptor("fileName", org/joone/io/FileOutputSynapse, "getFileName", "setFileName");
			properties[5] = new PropertyDescriptor("enabled", org/joone/io/FileOutputSynapse, "isEnabled", "setEnabled");
			properties[6] = new PropertyDescriptor("monitor", org/joone/io/FileOutputSynapse, "getMonitor", "setMonitor");
			properties[6].setExpert(true);
		}
		catch (IntrospectionException introspectionexception) { }
	}
}
