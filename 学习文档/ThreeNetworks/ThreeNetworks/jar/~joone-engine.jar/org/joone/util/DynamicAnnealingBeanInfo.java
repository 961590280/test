// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DynamicAnnealingBeanInfo.java

package org.joone.util;

import java.beans.*;
import org.joone.engine.NeuralNetEvent;

// Referenced classes of package org.joone.util:
//			DynamicAnnealing

public class DynamicAnnealingBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_name = 0;
	private static final int PROPERTY_step = 1;
	private static final int PROPERTY_rate = 2;
	private static final int METHOD_cicleTerminated0 = 0;
	private static final int METHOD_netStopped1 = 1;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public DynamicAnnealingBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/util/DynamicAnnealing, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[3];
		try
		{
			properties[0] = new PropertyDescriptor("name", org/joone/util/DynamicAnnealing, "getName", "setName");
			properties[1] = new PropertyDescriptor("step", org/joone/util/DynamicAnnealing, "getStep", "setStep");
			properties[1].setDisplayName("change %");
			properties[2] = new PropertyDescriptor("rate", org/joone/util/DynamicAnnealing, "getRate", "setRate");
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
		MethodDescriptor methods[] = new MethodDescriptor[2];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/util/DynamicAnnealing.getMethod("cicleTerminated", new Class[] {
				org/joone/engine/NeuralNetEvent
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/util/DynamicAnnealing.getMethod("netStopped", new Class[] {
				org/joone/engine/NeuralNetEvent
			}));
			methods[1].setDisplayName("");
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
