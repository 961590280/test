// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   XLSOutputSynapseBeanInfo.java

package org.joone.io;

import java.beans.*;

// Referenced classes of package org.joone.io:
//			XLSOutputSynapse

public class XLSOutputSynapseBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_sheetName = 0;
	private static final int PROPERTY_buffered = 1;
	private static final int PROPERTY_fileName = 2;
	private static final int PROPERTY_enabled = 3;
	private static final int PROPERTY_separator = 4;
	private static final int PROPERTY_name = 5;
	private static final int PROPERTY_monitor = 6;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public XLSOutputSynapseBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/io/XLSOutputSynapse, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[7];
		try
		{
			properties[0] = new PropertyDescriptor("sheetName", org/joone/io/XLSOutputSynapse, "getSheetName", "setSheetName");
			properties[1] = new PropertyDescriptor("buffered", org/joone/io/XLSOutputSynapse, "isBuffered", "setBuffered");
			properties[2] = new PropertyDescriptor("fileName", org/joone/io/XLSOutputSynapse, "getFileName", "setFileName");
			properties[3] = new PropertyDescriptor("enabled", org/joone/io/XLSOutputSynapse, "isEnabled", "setEnabled");
			properties[4] = new PropertyDescriptor("separator", org/joone/io/XLSOutputSynapse, "getSeparator", "setSeparator");
			properties[4].setExpert(true);
			properties[5] = new PropertyDescriptor("name", org/joone/io/XLSOutputSynapse, "getName", "setName");
			properties[6] = new PropertyDescriptor("monitor", org/joone/io/XLSOutputSynapse, "getMonitor", "setMonitor");
			properties[6].setExpert(true);
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
