// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   XLSInputSynapseBeanInfo.java

package org.joone.io;

import java.beans.*;

// Referenced classes of package org.joone.io:
//			XLSInputSynapse

public class XLSInputSynapseBeanInfo extends SimpleBeanInfo
{

	private static BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/io/XLSInputSynapse, null);
	private static final int PROPERTY_advancedColumnSelector = 0;
	private static final int PROPERTY_buffered = 1;
	private static final int PROPERTY_decimalPoint = 2;
	private static final int PROPERTY_enabled = 3;
	private static final int PROPERTY_firstRow = 4;
	private static final int PROPERTY_inputFile = 5;
	private static final int PROPERTY_inputPatterns = 6;
	private static final int PROPERTY_lastRow = 7;
	private static final int PROPERTY_monitor = 8;
	private static final int PROPERTY_name = 9;
	private static final int PROPERTY_plugIn = 10;
	private static final int PROPERTY_sheetName = 11;
	private static final int PROPERTY_stepCounter = 12;
	private static PropertyDescriptor properties[];
	private static EventSetDescriptor eventSets[] = new EventSetDescriptor[0];
	private static MethodDescriptor methods[] = new MethodDescriptor[0];
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public XLSInputSynapseBeanInfo()
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
		properties = new PropertyDescriptor[13];
		try
		{
			properties[0] = new PropertyDescriptor("advancedColumnSelector", org/joone/io/XLSInputSynapse, "getAdvancedColumnSelector", "setAdvancedColumnSelector");
			properties[0].setDisplayName("Advanced Column Selector");
			properties[1] = new PropertyDescriptor("buffered", org/joone/io/XLSInputSynapse, "isBuffered", "setBuffered");
			properties[2] = new PropertyDescriptor("decimalPoint", org/joone/io/XLSInputSynapse, "getDecimalPoint", "setDecimalPoint");
			properties[2].setExpert(true);
			properties[3] = new PropertyDescriptor("enabled", org/joone/io/XLSInputSynapse, "isEnabled", "setEnabled");
			properties[4] = new PropertyDescriptor("firstRow", org/joone/io/XLSInputSynapse, "getFirstRow", "setFirstRow");
			properties[5] = new PropertyDescriptor("inputFile", org/joone/io/XLSInputSynapse, "getInputFile", "setInputFile");
			properties[6] = new PropertyDescriptor("inputPatterns", org/joone/io/XLSInputSynapse, "getInputPatterns", "setInputPatterns");
			properties[6].setExpert(true);
			properties[7] = new PropertyDescriptor("lastRow", org/joone/io/XLSInputSynapse, "getLastRow", "setLastRow");
			properties[8] = new PropertyDescriptor("monitor", org/joone/io/XLSInputSynapse, "getMonitor", "setMonitor");
			properties[8].setExpert(true);
			properties[9] = new PropertyDescriptor("name", org/joone/io/XLSInputSynapse, "getName", "setName");
			properties[10] = new PropertyDescriptor("plugIn", org/joone/io/XLSInputSynapse, "getPlugIn", "setPlugIn");
			properties[10].setExpert(true);
			properties[11] = new PropertyDescriptor("sheetName", org/joone/io/XLSInputSynapse, "getSheetName", "setSheetName");
			properties[12] = new PropertyDescriptor("stepCounter", org/joone/io/XLSInputSynapse, "isStepCounter", "setStepCounter");
		}
		catch (IntrospectionException e)
		{
			e.printStackTrace();
		}
	}
}
