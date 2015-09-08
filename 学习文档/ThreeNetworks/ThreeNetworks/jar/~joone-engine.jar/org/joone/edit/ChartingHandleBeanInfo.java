// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ChartingHandleBeanInfo.java

package org.joone.edit;

import java.beans.*;
import org.joone.engine.Pattern;

// Referenced classes of package org.joone.edit:
//			ChartingHandle

public class ChartingHandleBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_serie = 0;
	private static final int PROPERTY_greenColor = 1;
	private static final int PROPERTY_redColor = 2;
	private static final int PROPERTY_chartSynapse = 3;
	private static final int PROPERTY_blueColor = 4;
	private static final int PROPERTY_enabled = 5;
	private static final int PROPERTY_name = 6;
	private static final int PROPERTY_monitor = 7;
	private static final int METHOD_fwdPut0 = 0;
	private static final int METHOD_revGet1 = 1;
	private static final int METHOD_check2 = 2;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public ChartingHandleBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/edit/ChartingHandle, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[8];
		try
		{
			properties[0] = new PropertyDescriptor("serie", org/joone/edit/ChartingHandle, "getSerie", "setSerie");
			properties[1] = new PropertyDescriptor("greenColor", org/joone/edit/ChartingHandle, "getGreenColor", "setGreenColor");
			properties[1].setDisplayName("Color (green)");
			properties[2] = new PropertyDescriptor("redColor", org/joone/edit/ChartingHandle, "getRedColor", "setRedColor");
			properties[2].setDisplayName("Color (red)");
			properties[3] = new PropertyDescriptor("chartSynapse", org/joone/edit/ChartingHandle, "getChartSynapse", "setChartSynapse");
			properties[3].setExpert(true);
			properties[4] = new PropertyDescriptor("blueColor", org/joone/edit/ChartingHandle, "getBlueColor", "setBlueColor");
			properties[4].setDisplayName("Color (blue)");
			properties[5] = new PropertyDescriptor("enabled", org/joone/edit/ChartingHandle, "isEnabled", "setEnabled");
			properties[6] = new PropertyDescriptor("name", org/joone/edit/ChartingHandle, "getName", "setName");
			properties[7] = new PropertyDescriptor("monitor", org/joone/edit/ChartingHandle, "getMonitor", "setMonitor");
			properties[7].setExpert(true);
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
		MethodDescriptor methods[] = new MethodDescriptor[3];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/edit/ChartingHandle.getMethod("fwdPut", new Class[] {
				org/joone/engine/Pattern
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/edit/ChartingHandle.getMethod("revGet", new Class[0]));
			methods[1].setDisplayName("");
			methods[2] = new MethodDescriptor(org/joone/edit/ChartingHandle.getMethod("check", new Class[0]));
			methods[2].setDisplayName("");
		}
		catch (Exception exception) { }
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
