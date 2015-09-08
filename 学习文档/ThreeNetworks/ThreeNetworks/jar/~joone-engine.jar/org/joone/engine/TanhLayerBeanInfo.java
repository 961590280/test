// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TanhLayerBeanInfo.java

package org.joone.engine;

import java.beans.*;

// Referenced classes of package org.joone.engine:
//			TanhLayer

public class TanhLayerBeanInfo extends SimpleBeanInfo
{

	private static BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/engine/TanhLayer, null);
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

	public TanhLayerBeanInfo()
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
		properties = new PropertyDescriptor[9];
		try
		{
			properties[0] = new PropertyDescriptor("allInputs", org/joone/engine/TanhLayer, "getAllInputs", "setAllInputs");
			properties[0].setExpert(true);
			properties[1] = new PropertyDescriptor("allOutputs", org/joone/engine/TanhLayer, "getAllOutputs", "setAllOutputs");
			properties[1].setExpert(true);
			properties[2] = new PropertyDescriptor("bias", org/joone/engine/TanhLayer, "getBias", "setBias");
			properties[2].setExpert(true);
			properties[3] = new PropertyDescriptor("inputLayer", org/joone/engine/TanhLayer, "isInputLayer", null);
			properties[3].setExpert(true);
			properties[4] = new PropertyDescriptor("layerName", org/joone/engine/TanhLayer, "getLayerName", "setLayerName");
			properties[5] = new PropertyDescriptor("learner", org/joone/engine/TanhLayer, "getLearner", null);
			properties[5].setExpert(true);
			properties[6] = new PropertyDescriptor("monitor", org/joone/engine/TanhLayer, "getMonitor", "setMonitor");
			properties[6].setExpert(true);
			properties[7] = new PropertyDescriptor("outputLayer", org/joone/engine/TanhLayer, "isOutputLayer", null);
			properties[7].setExpert(true);
			properties[8] = new PropertyDescriptor("rows", org/joone/engine/TanhLayer, "getRows", "setRows");
		}
		catch (IntrospectionException introspectionexception) { }
	}
}
