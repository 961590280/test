// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JDBCOutputSynapseBeanInfo.java

package org.joone.io;

import java.beans.*;

// Referenced classes of package org.joone.io:
//			JDBCOutputSynapse

public class JDBCOutputSynapseBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_buffered = 0;
	private static final int PROPERTY_dbURL = 1;
	private static final int PROPERTY_driverName = 2;
	private static final int PROPERTY_enabled = 3;
	private static final int PROPERTY_monitor = 4;
	private static final int PROPERTY_name = 5;
	private static final int PROPERTY_SQLAmendment = 6;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public JDBCOutputSynapseBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/io/JDBCOutputSynapse, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[7];
		try
		{
			properties[0] = new PropertyDescriptor("buffered", org/joone/io/JDBCOutputSynapse, "isBuffered", "setBuffered");
			properties[1] = new PropertyDescriptor("dbURL", org/joone/io/JDBCOutputSynapse, "getdbURL", "setdbURL");
			properties[2] = new PropertyDescriptor("driverName", org/joone/io/JDBCOutputSynapse, "getdriverName", "setdriverName");
			properties[3] = new PropertyDescriptor("enabled", org/joone/io/JDBCOutputSynapse, "isEnabled", "setEnabled");
			properties[4] = new PropertyDescriptor("monitor", org/joone/io/JDBCOutputSynapse, "getMonitor", "setMonitor");
			properties[4].setExpert(true);
			properties[5] = new PropertyDescriptor("name", org/joone/io/JDBCOutputSynapse, "getName", "setName");
			properties[6] = new PropertyDescriptor("SQLAmendment", org/joone/io/JDBCOutputSynapse, "getSQLAmendment", "setSQLAmendment");
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
