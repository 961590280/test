// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LinearLayerBeanInfo.java

package org.joone.engine;

import java.beans.*;

// Referenced classes of package org.joone.engine:
//			LinearLayer

public class LinearLayerBeanInfo extends SimpleBeanInfo
{

	private static BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/engine/LinearLayer, null);
	private static final int PROPERTY_allInputs = 0;
	private static final int PROPERTY_allOutputs = 1;
	private static final int PROPERTY_beta = 2;
	private static final int PROPERTY_bias = 3;
	private static final int PROPERTY_inputLayer = 4;
	private static final int PROPERTY_layerName = 5;
	private static final int PROPERTY_learner = 6;
	private static final int PROPERTY_monitor = 7;
	private static final int PROPERTY_outputLayer = 8;
	private static final int PROPERTY_rows = 9;
	private static PropertyDescriptor properties[];
	private static EventSetDescriptor eventSets[] = new EventSetDescriptor[0];
	private static MethodDescriptor methods[] = new MethodDescriptor[0];
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public LinearLayerBeanInfo()
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
		properties = new PropertyDescriptor[10];
		try
		{
			properties[0] = new PropertyDescriptor("allInputs", org/joone/engine/LinearLayer, "getAllInputs", "setAllInputs");
			properties[0].setExpert(true);
			properties[1] = new PropertyDescriptor("allOutputs", org/joone/engine/LinearLayer, "getAllOutputs", "setAllOutputs");
			properties[1].setExpert(true);
			properties[2] = new PropertyDescriptor("beta", org/joone/engine/LinearLayer, "getBeta", "setBeta");
			properties[3] = new PropertyDescriptor("bias", org/joone/engine/LinearLayer, "getBias", "setBias");
			properties[3].setExpert(true);
			properties[4] = new PropertyDescriptor("inputLayer", org/joone/engine/LinearLayer, "isInputLayer", null);
			properties[4].setExpert(true);
			properties[5] = new PropertyDescriptor("layerName", org/joone/engine/LinearLayer, "getLayerName", "setLayerName");
			properties[6] = new PropertyDescriptor("learner", org/joone/engine/LinearLayer, "getLearner", null);
			properties[6].setExpert(true);
			properties[7] = new PropertyDescriptor("monitor", org/joone/engine/LinearLayer, "getMonitor", "setMonitor");
			properties[7].setExpert(true);
			properties[8] = new PropertyDescriptor("outputLayer", org/joone/engine/LinearLayer, "isOutputLayer", null);
			properties[8].setExpert(true);
			properties[9] = new PropertyDescriptor("rows", org/joone/engine/LinearLayer, "getRows", "setRows");
		}
		catch (IntrospectionException introspectionexception) { }
	}
}
