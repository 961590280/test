// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MonitorPluginBeanInfo.java

package org.joone.util;

import java.beans.*;
import org.joone.engine.NeuralNetEvent;

// Referenced classes of package org.joone.util:
//			MonitorPlugin

public class MonitorPluginBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_name = 0;
	private static final int PROPERTY_rate = 1;
	private static final int METHOD_cicleTerminated0 = 0;
	private static final int METHOD_netStopped1 = 1;
	private static final int METHOD_netStarted2 = 2;
	private static final int METHOD_errorChanged3 = 3;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public MonitorPluginBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/util/MonitorPlugin, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[2];
		try
		{
			properties[0] = new PropertyDescriptor("name", org/joone/util/MonitorPlugin, "getName", "setName");
			properties[1] = new PropertyDescriptor("rate", org/joone/util/MonitorPlugin, "getRate", "setRate");
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
		MethodDescriptor methods[] = new MethodDescriptor[4];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/util/MonitorPlugin.getMethod("cicleTerminated", new Class[] {
				org/joone/engine/NeuralNetEvent
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/util/MonitorPlugin.getMethod("netStopped", new Class[] {
				org/joone/engine/NeuralNetEvent
			}));
			methods[1].setDisplayName("");
			methods[2] = new MethodDescriptor(org/joone/util/MonitorPlugin.getMethod("netStarted", new Class[] {
				org/joone/engine/NeuralNetEvent
			}));
			methods[2].setDisplayName("");
			methods[3] = new MethodDescriptor(org/joone/util/MonitorPlugin.getMethod("errorChanged", new Class[] {
				org/joone/engine/NeuralNetEvent
			}));
			methods[3].setDisplayName("");
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
