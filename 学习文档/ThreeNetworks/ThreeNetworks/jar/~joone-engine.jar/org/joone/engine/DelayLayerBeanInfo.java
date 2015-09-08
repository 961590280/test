// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DelayLayerBeanInfo.java

package org.joone.engine;

import java.beans.*;

// Referenced classes of package org.joone.engine:
//			DelayLayer, InputPatternListener, OutputPatternListener, NeuralLayer

public class DelayLayerBeanInfo extends SimpleBeanInfo
{

	private static BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/engine/DelayLayer, null);
	private static final int PROPERTY_allInputs = 0;
	private static final int PROPERTY_allOutputs = 1;
	private static final int PROPERTY_bias = 2;
	private static final int PROPERTY_inputLayer = 3;
	private static final int PROPERTY_layerName = 4;
	private static final int PROPERTY_learner = 5;
	private static final int PROPERTY_monitor = 6;
	private static final int PROPERTY_outputLayer = 7;
	private static final int PROPERTY_rows = 8;
	private static final int PROPERTY_taps = 9;
	private static PropertyDescriptor properties[];
	private static EventSetDescriptor eventSets[] = new EventSetDescriptor[0];
	private static final int METHOD_addInputSynapse0 = 0;
	private static final int METHOD_addNoise1 = 1;
	private static final int METHOD_addOutputSynapse2 = 2;
	private static final int METHOD_copyInto3 = 3;
	private static final int METHOD_removeAllInputs4 = 4;
	private static final int METHOD_removeAllOutputs5 = 5;
	private static final int METHOD_removeInputSynapse6 = 6;
	private static final int METHOD_removeOutputSynapse7 = 7;
	private static final int METHOD_run8 = 8;
	private static final int METHOD_start9 = 9;
	private static MethodDescriptor methods[];
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public DelayLayerBeanInfo()
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
			properties[0] = new PropertyDescriptor("allInputs", org/joone/engine/DelayLayer, "getAllInputs", "setAllInputs");
			properties[0].setExpert(true);
			properties[1] = new PropertyDescriptor("allOutputs", org/joone/engine/DelayLayer, "getAllOutputs", "setAllOutputs");
			properties[1].setExpert(true);
			properties[2] = new PropertyDescriptor("bias", org/joone/engine/DelayLayer, "getBias", "setBias");
			properties[2].setExpert(true);
			properties[3] = new PropertyDescriptor("inputLayer", org/joone/engine/DelayLayer, "isInputLayer", null);
			properties[3].setExpert(true);
			properties[4] = new PropertyDescriptor("layerName", org/joone/engine/DelayLayer, "getLayerName", "setLayerName");
			properties[5] = new PropertyDescriptor("learner", org/joone/engine/DelayLayer, "getLearner", null);
			properties[5].setExpert(true);
			properties[6] = new PropertyDescriptor("monitor", org/joone/engine/DelayLayer, "getMonitor", "setMonitor");
			properties[6].setExpert(true);
			properties[7] = new PropertyDescriptor("outputLayer", org/joone/engine/DelayLayer, "isOutputLayer", null);
			properties[7].setExpert(true);
			properties[8] = new PropertyDescriptor("rows", org/joone/engine/DelayLayer, "getRows", "setRows");
			properties[9] = new PropertyDescriptor("taps", org/joone/engine/DelayLayer, "getTaps", "setTaps");
		}
		catch (IntrospectionException introspectionexception) { }
		methods = new MethodDescriptor[10];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/engine/DelayLayer.getMethod("addInputSynapse", new Class[] {
				org/joone/engine/InputPatternListener
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/engine/DelayLayer.getMethod("addNoise", new Class[] {
				Double.TYPE
			}));
			methods[1].setDisplayName("");
			methods[2] = new MethodDescriptor(org/joone/engine/DelayLayer.getMethod("addOutputSynapse", new Class[] {
				org/joone/engine/OutputPatternListener
			}));
			methods[2].setDisplayName("");
			methods[3] = new MethodDescriptor(org/joone/engine/DelayLayer.getMethod("copyInto", new Class[] {
				org/joone/engine/NeuralLayer
			}));
			methods[3].setDisplayName("");
			methods[4] = new MethodDescriptor(org/joone/engine/DelayLayer.getMethod("removeAllInputs", new Class[0]));
			methods[4].setDisplayName("");
			methods[5] = new MethodDescriptor(org/joone/engine/DelayLayer.getMethod("removeAllOutputs", new Class[0]));
			methods[5].setDisplayName("");
			methods[6] = new MethodDescriptor(org/joone/engine/DelayLayer.getMethod("removeInputSynapse", new Class[] {
				org/joone/engine/InputPatternListener
			}));
			methods[6].setDisplayName("");
			methods[7] = new MethodDescriptor(org/joone/engine/DelayLayer.getMethod("removeOutputSynapse", new Class[] {
				org/joone/engine/OutputPatternListener
			}));
			methods[7].setDisplayName("");
			methods[8] = new MethodDescriptor(org/joone/engine/DelayLayer.getMethod("run", new Class[0]));
			methods[8].setDisplayName("");
			methods[9] = new MethodDescriptor(org/joone/engine/DelayLayer.getMethod("start", new Class[0]));
			methods[9].setDisplayName("");
		}
		catch (Exception exception) { }
	}
}
