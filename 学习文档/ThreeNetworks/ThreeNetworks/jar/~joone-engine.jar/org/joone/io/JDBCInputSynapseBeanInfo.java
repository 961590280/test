// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JDBCInputSynapseBeanInfo.java

package org.joone.io;

import java.beans.*;

// Referenced classes of package org.joone.io:
//			JDBCInputSynapse

public class JDBCInputSynapseBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_advancedColumnSelector = 0;
	private static final int PROPERTY_buffered = 1;
	private static final int PROPERTY_dbURL = 2;
	private static final int PROPERTY_driverName = 3;
	private static final int PROPERTY_enabled = 4;
	private static final int PROPERTY_firstRow = 5;
	private static final int PROPERTY_inputPatterns = 6;
	private static final int PROPERTY_lastRow = 7;
	private static final int PROPERTY_maxBufSize = 8;
	private static final int PROPERTY_monitor = 9;
	private static final int PROPERTY_name = 10;
	private static final int PROPERTY_plugIn = 11;
	private static final int PROPERTY_SQLQuery = 12;
	private static final int PROPERTY_stepCounter = 13;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public JDBCInputSynapseBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/io/JDBCInputSynapse, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[14];
		try
		{
			properties[0] = new PropertyDescriptor("advancedColumnSelector", org/joone/io/JDBCInputSynapse, "getAdvancedColumnSelector", "setAdvancedColumnSelector");
			properties[0].setDisplayName("Advanced Column Selector");
			properties[1] = new PropertyDescriptor("buffered", org/joone/io/JDBCInputSynapse, "isBuffered", "setBuffered");
			properties[2] = new PropertyDescriptor("dbURL", org/joone/io/JDBCInputSynapse, "getdbURL", "setdbURL");
			properties[3] = new PropertyDescriptor("driverName", org/joone/io/JDBCInputSynapse, "getdriverName", "setdriverName");
			properties[4] = new PropertyDescriptor("enabled", org/joone/io/JDBCInputSynapse, "isEnabled", "setEnabled");
			properties[5] = new PropertyDescriptor("firstRow", org/joone/io/JDBCInputSynapse, "getFirstRow", "setFirstRow");
			properties[6] = new PropertyDescriptor("inputPatterns", org/joone/io/JDBCInputSynapse, "getInputPatterns", "setInputPatterns");
			properties[6].setExpert(true);
			properties[7] = new PropertyDescriptor("lastRow", org/joone/io/JDBCInputSynapse, "getLastRow", "setLastRow");
			properties[8] = new PropertyDescriptor("maxBufSize", org/joone/io/JDBCInputSynapse, "getMaxBufSize", "setMaxBufSize");
			properties[9] = new PropertyDescriptor("monitor", org/joone/io/JDBCInputSynapse, "getMonitor", "setMonitor");
			properties[9].setExpert(true);
			properties[10] = new PropertyDescriptor("name", org/joone/io/JDBCInputSynapse, "getName", "setName");
			properties[11] = new PropertyDescriptor("plugIn", org/joone/io/JDBCInputSynapse, "getPlugIn", "setPlugIn");
			properties[11].setExpert(true);
			properties[12] = new PropertyDescriptor("SQLQuery", org/joone/io/JDBCInputSynapse, "getSQLQuery", "setSQLQuery");
			properties[13] = new PropertyDescriptor("stepCounter", org/joone/io/JDBCInputSynapse, "isStepCounter", "setStepCounter");
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
