// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MinMaxExtractorPlugInBeanInfo.java

package org.joone.util;

import java.beans.*;

// Referenced classes of package org.joone.util:
//			MinMaxExtractorPlugIn

public class MinMaxExtractorPlugInBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_advancedSerieSelector = 0;
	private static final int PROPERTY_minChangePercentage = 1;
	private static final int PROPERTY_name = 2;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public MinMaxExtractorPlugInBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/util/MinMaxExtractorPlugIn, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[3];
		try
		{
			properties[0] = new PropertyDescriptor("advancedSerieSelector", org/joone/util/MinMaxExtractorPlugIn, "getAdvancedSerieSelector", "setAdvancedSerieSelector");
			properties[1] = new PropertyDescriptor("minChangePercentage", org/joone/util/MinMaxExtractorPlugIn, "getMinChangePercentage", "setMinChangePercentage");
			properties[2] = new PropertyDescriptor("name", org/joone/util/MinMaxExtractorPlugIn, "getName", "setName");
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
