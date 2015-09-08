// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NeuralNetBeanInfo.java

package org.joone.net;

import java.beans.*;

// Referenced classes of package org.joone.net:
//			NeuralNet

public class NeuralNetBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_descriptor = 0;
	private static final int PROPERTY_inputLayer = 1;
	private static final int PROPERTY_layerName = 2;
	private static final int PROPERTY_layers = 3;
	private static final int PROPERTY_macroPlugin = 4;
	private static final int PROPERTY_monitor = 5;
	private static final int PROPERTY_outputLayer = 6;
	private static final int PROPERTY_scriptingEnabled = 7;
	private static final int PROPERTY_teacher = 8;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public NeuralNetBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/net/NeuralNet, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[9];
		try
		{
			properties[0] = new PropertyDescriptor("descriptor", org/joone/net/NeuralNet, "getDescriptor", "setDescriptor");
			properties[1] = new PropertyDescriptor("inputLayer", org/joone/net/NeuralNet, "getInputLayer", "setInputLayer");
			properties[2] = new PropertyDescriptor("layerName", org/joone/net/NeuralNet, "getLayerName", "setLayerName");
			properties[3] = new PropertyDescriptor("layers", org/joone/net/NeuralNet, "getLayers", "setLayers");
			properties[4] = new PropertyDescriptor("macroPlugin", org/joone/net/NeuralNet, "getMacroPlugin", "setMacroPlugin");
			properties[5] = new PropertyDescriptor("monitor", org/joone/net/NeuralNet, "getMonitor", "setMonitor");
			properties[5].setExpert(true);
			properties[6] = new PropertyDescriptor("outputLayer", org/joone/net/NeuralNet, "getOutputLayer", "setOutputLayer");
			properties[7] = new PropertyDescriptor("scriptingEnabled", org/joone/net/NeuralNet, "isScriptingEnabled", "setScriptingEnabled");
			properties[8] = new PropertyDescriptor("teacher", org/joone/net/NeuralNet, "getTeacher", "setTeacher");
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
