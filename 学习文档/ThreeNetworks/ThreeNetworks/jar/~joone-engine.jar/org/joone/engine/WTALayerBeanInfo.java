// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WTALayerBeanInfo.java

package org.joone.engine;

import java.beans.*;

// Referenced classes of package org.joone.engine:
//			WTALayer, InputPatternListener, OutputPatternListener, NeuralLayer

public class WTALayerBeanInfo extends SimpleBeanInfo
{

	private static BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/engine/WTALayer, null);
	private static final int PROPERTY_allInputs = 0;
	private static final int PROPERTY_allOutputs = 1;
	private static final int PROPERTY_inputLayer = 2;
	private static final int PROPERTY_layerHeight = 3;
	private static final int PROPERTY_layerName = 4;
	private static final int PROPERTY_layerWidth = 5;
	private static final int PROPERTY_learner = 6;
	private static final int PROPERTY_monitor = 7;
	private static final int PROPERTY_outputLayer = 8;
	private static final int PROPERTY_rows = 9;
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

	public WTALayerBeanInfo()
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
			properties[0] = new PropertyDescriptor("allInputs", org/joone/engine/WTALayer, "getAllInputs", "setAllInputs");
			properties[0].setExpert(true);
			properties[1] = new PropertyDescriptor("allOutputs", org/joone/engine/WTALayer, "getAllOutputs", "setAllOutputs");
			properties[1].setExpert(true);
			properties[2] = new PropertyDescriptor("inputLayer", org/joone/engine/WTALayer, "isInputLayer", null);
			properties[2].setExpert(true);
			properties[3] = new PropertyDescriptor("layerHeight", org/joone/engine/WTALayer, "getLayerHeight", "setLayerHeight");
			properties[4] = new PropertyDescriptor("layerName", org/joone/engine/WTALayer, "getLayerName", "setLayerName");
			properties[5] = new PropertyDescriptor("layerWidth", org/joone/engine/WTALayer, "getLayerWidth", "setLayerWidth");
			properties[6] = new PropertyDescriptor("learner", org/joone/engine/WTALayer, "getLearner", null);
			properties[6].setExpert(true);
			properties[7] = new PropertyDescriptor("monitor", org/joone/engine/WTALayer, "getMonitor", "setMonitor");
			properties[7].setExpert(true);
			properties[8] = new PropertyDescriptor("outputLayer", org/joone/engine/WTALayer, "isOutputLayer", null);
			properties[8].setExpert(true);
			properties[9] = new PropertyDescriptor("rows", org/joone/engine/WTALayer, "getRows", "setRows");
			properties[9].setHidden(true);
		}
		catch (IntrospectionException e)
		{
			e.printStackTrace();
		}
		methods = new MethodDescriptor[10];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/engine/WTALayer.getMethod("addInputSynapse", new Class[] {
				org/joone/engine/InputPatternListener
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/engine/WTALayer.getMethod("addNoise", new Class[] {
				Double.TYPE
			}));
			methods[1].setDisplayName("");
			methods[2] = new MethodDescriptor(org/joone/engine/WTALayer.getMethod("addOutputSynapse", new Class[] {
				org/joone/engine/OutputPatternListener
			}));
			methods[2].setDisplayName("");
			methods[3] = new MethodDescriptor(org/joone/engine/WTALayer.getMethod("copyInto", new Class[] {
				org/joone/engine/NeuralLayer
			}));
			methods[3].setDisplayName("");
			methods[4] = new MethodDescriptor(org/joone/engine/WTALayer.getMethod("removeAllInputs", new Class[0]));
			methods[4].setDisplayName("");
			methods[5] = new MethodDescriptor(org/joone/engine/WTALayer.getMethod("removeAllOutputs", new Class[0]));
			methods[5].setDisplayName("");
			methods[6] = new MethodDescriptor(org/joone/engine/WTALayer.getMethod("removeInputSynapse", new Class[] {
				org/joone/engine/InputPatternListener
			}));
			methods[6].setDisplayName("");
			methods[7] = new MethodDescriptor(org/joone/engine/WTALayer.getMethod("removeOutputSynapse", new Class[] {
				org/joone/engine/OutputPatternListener
			}));
			methods[7].setDisplayName("");
			methods[8] = new MethodDescriptor(org/joone/engine/WTALayer.getMethod("run", new Class[0]));
			methods[8].setDisplayName("");
			methods[9] = new MethodDescriptor(org/joone/engine/WTALayer.getMethod("start", new Class[0]));
			methods[9].setDisplayName("");
		}
		catch (Exception exception) { }
	}
}
