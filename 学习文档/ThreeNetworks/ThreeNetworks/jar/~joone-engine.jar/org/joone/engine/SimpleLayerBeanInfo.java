// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SimpleLayerBeanInfo.java

package org.joone.engine;

import java.beans.*;

// Referenced classes of package org.joone.engine:
//			SimpleLayer

public class SimpleLayerBeanInfo extends SimpleBeanInfo
{

	private static BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/engine/SimpleLayer, null);
	private static final int PROPERTY_allInputs = 0;
	private static final int PROPERTY_allOutputs = 1;
	private static final int PROPERTY_bias = 2;
	private static final int PROPERTY_inputLayer = 3;
	private static final int PROPERTY_layerName = 4;
	private static final int PROPERTY_learner = 5;
	private static final int PROPERTY_monitor = 6;
	private static final int PROPERTY_outputLayer = 7;
	private static final int PROPERTY_rows = 8;
	private static PropertyDescriptor properties[];
	private static EventSetDescriptor eventSets[] = new EventSetDescriptor[0];
	private static MethodDescriptor methods[] = new MethodDescriptor[0];
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public SimpleLayerBeanInfo()
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

	static 
	{
		properties = new PropertyDescriptor[9];
		try
		{
			properties[0] = new PropertyDescriptor("allInputs", org/joone/engine/SimpleLayer, "getAllInputs", "setAllInputs");
			properties[0].setExpert(true);
			properties[1] = new PropertyDescriptor("allOutputs", org/joone/engine/SimpleLayer, "getAllOutputs", "setAllOutputs");
			properties[1].setExpert(true);
			properties[2] = new PropertyDescriptor("bias", org/joone/engine/SimpleLayer, "getBias", "setBias");
			properties[2].setExpert(true);
			properties[3] = new PropertyDescriptor("inputLayer", org/joone/engine/SimpleLayer, "isInputLayer", null);
			properties[3].setExpert(true);
			properties[4] = new PropertyDescriptor("layerName", org/joone/engine/SimpleLayer, "getLayerName", "setLayerName");
			properties[4].setDisplayName("Name");
			properties[5] = new PropertyDescriptor("learner", org/joone/engine/SimpleLayer, "getLearner", null);
			properties[5].setExpert(true);
			properties[6] = new PropertyDescriptor("monitor", org/joone/engine/SimpleLayer, "getMonitor", "setMonitor");
			properties[6].setExpert(true);
			properties[7] = new PropertyDescriptor("outputLayer", org/joone/engine/SimpleLayer, "isOutputLayer", null);
			properties[7].setExpert(true);
			properties[8] = new PropertyDescriptor("rows", org/joone/engine/SimpleLayer, "getRows", "setRows");
		}
		catch (IntrospectionException introspectionexception) { }
	}
}
