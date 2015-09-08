// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UnNormalizerOutputPlugInBeanInfo.java

package org.joone.util;

import java.beans.*;

// Referenced classes of package org.joone.util:
//			UnNormalizerOutputPlugIn

public class UnNormalizerOutputPlugInBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_advancedSerieSelector = 0;
	private static final int PROPERTY_inDataMax = 1;
	private static final int PROPERTY_inDataMin = 2;
	private static final int PROPERTY_name = 3;
	private static final int PROPERTY_outDataMax = 4;
	private static final int PROPERTY_outDataMin = 5;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public UnNormalizerOutputPlugInBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/util/UnNormalizerOutputPlugIn, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[6];
		try
		{
			properties[0] = new PropertyDescriptor("advancedSerieSelector", org/joone/util/UnNormalizerOutputPlugIn, "getAdvancedSerieSelector", "setAdvancedSerieSelector");
			properties[0].setDisplayName("Advanced Serie Selector");
			properties[1] = new PropertyDescriptor("inDataMax", org/joone/util/UnNormalizerOutputPlugIn, "getInDataMax", "setInDataMax");
			properties[2] = new PropertyDescriptor("inDataMin", org/joone/util/UnNormalizerOutputPlugIn, "getInDataMin", "setInDataMin");
			properties[3] = new PropertyDescriptor("name", org/joone/util/UnNormalizerOutputPlugIn, "getName", "setName");
			properties[4] = new PropertyDescriptor("outDataMax", org/joone/util/UnNormalizerOutputPlugIn, "getOutDataMax", "setOutDataMax");
			properties[5] = new PropertyDescriptor("outDataMin", org/joone/util/UnNormalizerOutputPlugIn, "getOutDataMin", "setOutDataMin");
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
