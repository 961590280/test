// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GaussianLayerBeanInfo.java

package org.joone.engine;

import java.beans.*;

// Referenced classes of package org.joone.engine:
//			GaussianLayer, InputPatternListener, OutputPatternListener, NeuralLayer

public class GaussianLayerBeanInfo extends SimpleBeanInfo
{

	private static BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/engine/GaussianLayer, null);
	private static final int PROPERTY_allInputs = 0;
	private static final int PROPERTY_allOutputs = 1;
	private static final int PROPERTY_initialGaussianSize = 2;
	private static final int PROPERTY_inputLayer = 3;
	private static final int PROPERTY_layerHeight = 4;
	private static final int PROPERTY_layerName = 5;
	private static final int PROPERTY_layerWidth = 6;
	private static final int PROPERTY_learner = 7;
	private static final int PROPERTY_orderingPhase = 8;
	private static final int PROPERTY_outputLayer = 9;
	private static final int PROPERTY_rows = 10;
	private static final int PROPERTY_timeConstant = 11;
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

	public GaussianLayerBeanInfo()
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
		properties = new PropertyDescriptor[12];
		try
		{
			properties[0] = new PropertyDescriptor("allInputs", org/joone/engine/GaussianLayer, "getAllInputs", "setAllInputs");
			properties[0].setExpert(true);
			properties[1] = new PropertyDescriptor("allOutputs", org/joone/engine/GaussianLayer, "getAllOutputs", "setAllOutputs");
			properties[1].setExpert(true);
			properties[2] = new PropertyDescriptor("initialGaussianSize", org/joone/engine/GaussianLayer, "getInitialGaussianSize", "setInitialGaussianSize");
			properties[3] = new PropertyDescriptor("inputLayer", org/joone/engine/GaussianLayer, "isInputLayer", null);
			properties[3].setExpert(true);
			properties[4] = new PropertyDescriptor("layerHeight", org/joone/engine/GaussianLayer, "getLayerHeight", "setLayerHeight");
			properties[5] = new PropertyDescriptor("layerName", org/joone/engine/GaussianLayer, "getLayerName", "setLayerName");
			properties[6] = new PropertyDescriptor("layerWidth", org/joone/engine/GaussianLayer, "getLayerWidth", "setLayerWidth");
			properties[7] = new PropertyDescriptor("learner", org/joone/engine/GaussianLayer, "getLearner", null);
			properties[7].setExpert(true);
			properties[8] = new PropertyDescriptor("orderingPhase", org/joone/engine/GaussianLayer, "getOrderingPhase", "setOrderingPhase");
			properties[8].setDisplayName("ordering phase (epochs)");
			properties[9] = new PropertyDescriptor("outputLayer", org/joone/engine/GaussianLayer, "isOutputLayer", null);
			properties[9].setExpert(true);
			properties[10] = new PropertyDescriptor("rows", org/joone/engine/GaussianLayer, "getRows", "setRows");
			properties[10].setHidden(true);
			properties[11] = new PropertyDescriptor("timeConstant", org/joone/engine/GaussianLayer, "getTimeConstant", "setTimeConstant");
		}
		catch (IntrospectionException e)
		{
			e.printStackTrace();
		}
		methods = new MethodDescriptor[10];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/engine/GaussianLayer.getMethod("addInputSynapse", new Class[] {
				org/joone/engine/InputPatternListener
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/engine/GaussianLayer.getMethod("addNoise", new Class[] {
				Double.TYPE
			}));
			methods[1].setDisplayName("");
			methods[2] = new MethodDescriptor(org/joone/engine/GaussianLayer.getMethod("addOutputSynapse", new Class[] {
				org/joone/engine/OutputPatternListener
			}));
			methods[2].setDisplayName("");
			methods[3] = new MethodDescriptor(org/joone/engine/GaussianLayer.getMethod("copyInto", new Class[] {
				org/joone/engine/NeuralLayer
			}));
			methods[3].setDisplayName("");
			methods[4] = new MethodDescriptor(org/joone/engine/GaussianLayer.getMethod("removeAllInputs", new Class[0]));
			methods[4].setDisplayName("");
			methods[5] = new MethodDescriptor(org/joone/engine/GaussianLayer.getMethod("removeAllOutputs", new Class[0]));
			methods[5].setDisplayName("");
			methods[6] = new MethodDescriptor(org/joone/engine/GaussianLayer.getMethod("removeInputSynapse", new Class[] {
				org/joone/engine/InputPatternListener
			}));
			methods[6].setDisplayName("");
			methods[7] = new MethodDescriptor(org/joone/engine/GaussianLayer.getMethod("removeOutputSynapse", new Class[] {
				org/joone/engine/OutputPatternListener
			}));
			methods[7].setDisplayName("");
			methods[8] = new MethodDescriptor(org/joone/engine/GaussianLayer.getMethod("run", new Class[0]));
			methods[8].setDisplayName("");
			methods[9] = new MethodDescriptor(org/joone/engine/GaussianLayer.getMethod("start", new Class[0]));
			methods[9].setDisplayName("");
		}
		catch (Exception exception) { }
	}
}
