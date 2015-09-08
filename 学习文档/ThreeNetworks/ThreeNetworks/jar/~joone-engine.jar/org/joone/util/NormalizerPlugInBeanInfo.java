// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NormalizerPlugInBeanInfo.java

package org.joone.util;

import java.beans.*;

// Referenced classes of package org.joone.util:
//			NormalizerPlugIn

public class NormalizerPlugInBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_advancedSerieSelector = 0;
	private static final int PROPERTY_dataMax = 1;
	private static final int PROPERTY_dataMin = 2;
	private static final int PROPERTY_max = 3;
	private static final int PROPERTY_min = 4;
	private static final int PROPERTY_name = 5;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public NormalizerPlugInBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/util/NormalizerPlugIn, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[6];
		try
		{
			properties[0] = new PropertyDescriptor("advancedSerieSelector", org/joone/util/NormalizerPlugIn, "getAdvancedSerieSelector", "setAdvancedSerieSelector");
			properties[1] = new PropertyDescriptor("dataMax", org/joone/util/NormalizerPlugIn, "getDataMax", "setDataMax");
			properties[1].setDisplayName("InputDataMax");
			properties[2] = new PropertyDescriptor("dataMin", org/joone/util/NormalizerPlugIn, "getDataMin", "setDataMin");
			properties[2].setDisplayName("InputDataMin");
			properties[3] = new PropertyDescriptor("max", org/joone/util/NormalizerPlugIn, "getMax", "setMax");
			properties[3].setDisplayName("OutputDataMax");
			properties[4] = new PropertyDescriptor("min", org/joone/util/NormalizerPlugIn, "getMin", "setMin");
			properties[4].setDisplayName("OutputDataMin");
			properties[5] = new PropertyDescriptor("name", org/joone/util/NormalizerPlugIn, "getName", "setName");
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
