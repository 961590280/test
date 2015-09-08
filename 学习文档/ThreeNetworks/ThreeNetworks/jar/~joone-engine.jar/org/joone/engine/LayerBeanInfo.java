// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LayerBeanInfo.java

package org.joone.engine;

import java.beans.*;

// Referenced classes of package org.joone.engine:
//			Layer, InputPatternListener, OutputPatternListener, NeuralLayer

public class LayerBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_allInputs = 0;
	private static final int PROPERTY_allOutputs = 1;
	private static final int PROPERTY_bias = 2;
	private static final int PROPERTY_inputLayer = 3;
	private static final int PROPERTY_layerName = 4;
	private static final int PROPERTY_learner = 5;
	private static final int PROPERTY_monitor = 6;
	private static final int PROPERTY_outputLayer = 7;
	private static final int PROPERTY_rows = 8;
	private static final int PROPERTY_running = 9;
	private static final int METHOD_addInputSynapse0 = 0;
	private static final int METHOD_addNoise1 = 1;
	private static final int METHOD_addOutputSynapse2 = 2;
	private static final int METHOD_copyInto3 = 3;
	private static final int METHOD_randomize4 = 4;
	private static final int METHOD_removeAllInputs5 = 5;
	private static final int METHOD_removeAllOutputs6 = 6;
	private static final int METHOD_removeInputSynapse7 = 7;
	private static final int METHOD_removeOutputSynapse8 = 8;
	private static final int METHOD_run9 = 9;
	private static final int METHOD_start10 = 10;
	private static final int METHOD_stop11 = 11;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public LayerBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/engine/Layer, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[10];
		try
		{
			properties[0] = new PropertyDescriptor("allInputs", org/joone/engine/Layer, "getAllInputs", "setAllInputs");
			properties[0].setExpert(true);
			properties[1] = new PropertyDescriptor("allOutputs", org/joone/engine/Layer, "getAllOutputs", "setAllOutputs");
			properties[1].setExpert(true);
			properties[2] = new PropertyDescriptor("bias", org/joone/engine/Layer, "getBias", "setBias");
			properties[2].setExpert(true);
			properties[3] = new PropertyDescriptor("inputLayer", org/joone/engine/Layer, "isInputLayer", null);
			properties[3].setExpert(true);
			properties[4] = new PropertyDescriptor("layerName", org/joone/engine/Layer, "getLayerName", "setLayerName");
			properties[4].setDisplayName("Name");
			properties[5] = new PropertyDescriptor("learner", org/joone/engine/Layer, "getLearner", null);
			properties[5].setExpert(true);
			properties[6] = new PropertyDescriptor("monitor", org/joone/engine/Layer, "getMonitor", "setMonitor");
			properties[6].setExpert(true);
			properties[7] = new PropertyDescriptor("outputLayer", org/joone/engine/Layer, "isOutputLayer", null);
			properties[7].setExpert(true);
			properties[8] = new PropertyDescriptor("rows", org/joone/engine/Layer, "getRows", "setRows");
			properties[9] = new PropertyDescriptor("running", org/joone/engine/Layer, "isRunning", null);
			properties[9].setExpert(true);
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
		MethodDescriptor methods[] = new MethodDescriptor[12];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/engine/Layer.getMethod("addInputSynapse", new Class[] {
				org/joone/engine/InputPatternListener
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/engine/Layer.getMethod("addNoise", new Class[] {
				Double.TYPE
			}));
			methods[1].setDisplayName("");
			methods[2] = new MethodDescriptor(org/joone/engine/Layer.getMethod("addOutputSynapse", new Class[] {
				org/joone/engine/OutputPatternListener
			}));
			methods[2].setDisplayName("");
			methods[3] = new MethodDescriptor(org/joone/engine/Layer.getMethod("copyInto", new Class[] {
				org/joone/engine/NeuralLayer
			}));
			methods[3].setDisplayName("");
			methods[4] = new MethodDescriptor(org/joone/engine/Layer.getMethod("randomize", new Class[] {
				Double.TYPE
			}));
			methods[4].setDisplayName("");
			methods[5] = new MethodDescriptor(org/joone/engine/Layer.getMethod("removeAllInputs", new Class[0]));
			methods[5].setDisplayName("");
			methods[6] = new MethodDescriptor(org/joone/engine/Layer.getMethod("removeAllOutputs", new Class[0]));
			methods[6].setDisplayName("");
			methods[7] = new MethodDescriptor(org/joone/engine/Layer.getMethod("removeInputSynapse", new Class[] {
				org/joone/engine/InputPatternListener
			}));
			methods[7].setDisplayName("");
			methods[8] = new MethodDescriptor(org/joone/engine/Layer.getMethod("removeOutputSynapse", new Class[] {
				org/joone/engine/OutputPatternListener
			}));
			methods[8].setDisplayName("");
			methods[9] = new MethodDescriptor(org/joone/engine/Layer.getMethod("run", new Class[0]));
			methods[9].setDisplayName("");
			methods[10] = new MethodDescriptor(org/joone/engine/Layer.getMethod("start", new Class[0]));
			methods[10].setDisplayName("");
			methods[11] = new MethodDescriptor(org/joone/engine/Layer.getMethod("stop", new Class[0]));
			methods[11].setDisplayName("");
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
