// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OutputConverterPlugInBeanInfo.java

package org.joone.util;

import java.beans.*;

// Referenced classes of package org.joone.util:
//			OutputConverterPlugIn

public class OutputConverterPlugInBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_advancedSerieSelector = 0;
	private static final int PROPERTY_name = 1;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public OutputConverterPlugInBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/util/OutputConverterPlugIn, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[2];
		try
		{
			properties[0] = new PropertyDescriptor("advancedSerieSelector", org/joone/util/OutputConverterPlugIn, "getAdvancedSerieSelector", "setAdvancedSerieSelector");
			properties[0].setDisplayName("Advanced Serie Selector");
			properties[1] = new PropertyDescriptor("name", org/joone/util/OutputConverterPlugIn, "getName", "setName");
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
