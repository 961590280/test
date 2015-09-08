// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   URLInputSynapseBeanInfo.java

package org.joone.io;

import java.beans.*;

// Referenced classes of package org.joone.io:
//			URLInputSynapse

public class URLInputSynapseBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_advancedColumnSelector = 0;
	private static final int PROPERTY_buffered = 1;
	private static final int PROPERTY_decimalPoint = 2;
	private static final int PROPERTY_enabled = 3;
	private static final int PROPERTY_firstRow = 4;
	private static final int PROPERTY_inputPatterns = 5;
	private static final int PROPERTY_lastRow = 6;
	private static final int PROPERTY_maxBufSize = 7;
	private static final int PROPERTY_monitor = 8;
	private static final int PROPERTY_name = 9;
	private static final int PROPERTY_plugIn = 10;
	private static final int PROPERTY_stepCounter = 11;
	private static final int PROPERTY_URL = 12;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public URLInputSynapseBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/io/URLInputSynapse, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[13];
		try
		{
			properties[0] = new PropertyDescriptor("advancedColumnSelector", org/joone/io/URLInputSynapse, "getAdvancedColumnSelector", "setAdvancedColumnSelector");
			properties[0].setDisplayName("Advanced Column Selector");
			properties[1] = new PropertyDescriptor("buffered", org/joone/io/URLInputSynapse, "isBuffered", "setBuffered");
			properties[2] = new PropertyDescriptor("decimalPoint", org/joone/io/URLInputSynapse, "getDecimalPoint", "setDecimalPoint");
			properties[3] = new PropertyDescriptor("enabled", org/joone/io/URLInputSynapse, "isEnabled", "setEnabled");
			properties[4] = new PropertyDescriptor("firstRow", org/joone/io/URLInputSynapse, "getFirstRow", "setFirstRow");
			properties[5] = new PropertyDescriptor("inputPatterns", org/joone/io/URLInputSynapse, "getInputPatterns", "setInputPatterns");
			properties[5].setExpert(true);
			properties[6] = new PropertyDescriptor("lastRow", org/joone/io/URLInputSynapse, "getLastRow", "setLastRow");
			properties[7] = new PropertyDescriptor("maxBufSize", org/joone/io/URLInputSynapse, "getMaxBufSize", "setMaxBufSize");
			properties[8] = new PropertyDescriptor("monitor", org/joone/io/URLInputSynapse, "getMonitor", "setMonitor");
			properties[8].setExpert(true);
			properties[9] = new PropertyDescriptor("name", org/joone/io/URLInputSynapse, "getName", "setName");
			properties[10] = new PropertyDescriptor("plugIn", org/joone/io/URLInputSynapse, "getPlugIn", "setPlugIn");
			properties[10].setExpert(true);
			properties[11] = new PropertyDescriptor("stepCounter", org/joone/io/URLInputSynapse, "isStepCounter", "setStepCounter");
			properties[12] = new PropertyDescriptor("URL", org/joone/io/URLInputSynapse, "getURL", "setURL");
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
