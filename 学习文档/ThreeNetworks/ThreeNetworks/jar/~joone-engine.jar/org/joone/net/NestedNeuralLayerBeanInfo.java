// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NestedNeuralLayerBeanInfo.java

package org.joone.net;

import java.beans.*;
import org.joone.edit.JooneFileChooserEditor;
import org.joone.engine.*;

// Referenced classes of package org.joone.net:
//			NestedNeuralLayer

public class NestedNeuralLayerBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_embeddedNet = 0;
	private static final int PROPERTY_layerName = 1;
	private static final int PROPERTY_learning = 2;
	private static final int PROPERTY_monitor = 3;
	private static final int PROPERTY_nestedNeuralNet = 4;
	private static final int PROPERTY_neuralNet = 5;
	private static final int PROPERTY_rows = 6;
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

	public NestedNeuralLayerBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/net/NestedNeuralLayer, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[7];
		try
		{
			properties[0] = new PropertyDescriptor("embeddedNet", org/joone/net/NestedNeuralLayer, "getEmbeddedNet", "setEmbeddedNet");
			properties[1] = new PropertyDescriptor("layerName", org/joone/net/NestedNeuralLayer, "getLayerName", "setLayerName");
			properties[2] = new PropertyDescriptor("learning", org/joone/net/NestedNeuralLayer, "isLearning", "setLearning");
			properties[3] = new PropertyDescriptor("monitor", org/joone/net/NestedNeuralLayer, "getMonitor", "setMonitor");
			properties[3].setExpert(true);
			properties[4] = new PropertyDescriptor("nestedNeuralNet", org/joone/net/NestedNeuralLayer, "getNestedNeuralNet", "setNestedNeuralNet");
			properties[4].setHidden(true);
			properties[5] = new PropertyDescriptor("neuralNet", org/joone/net/NestedNeuralLayer, "getNeuralNet", "setNeuralNet");
			properties[5].setHidden(true);
			properties[5].setDisplayName("Nested ANN");
			properties[5].setPropertyEditorClass(org/joone/edit/JooneFileChooserEditor);
			properties[6] = new PropertyDescriptor("rows", org/joone/net/NestedNeuralLayer, "getRows", "setRows");
			properties[6].setHidden(true);
		}
		catch (IntrospectionException e)
		{
			e.printStackTrace();
		}
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
			methods[0] = new MethodDescriptor(org/joone/net/NestedNeuralLayer.getMethod("addInputSynapse", new Class[] {
				org/joone/engine/InputPatternListener
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/net/NestedNeuralLayer.getMethod("addNoise", new Class[] {
				Double.TYPE
			}));
			methods[1].setDisplayName("");
			methods[2] = new MethodDescriptor(org/joone/net/NestedNeuralLayer.getMethod("addOutputSynapse", new Class[] {
				org/joone/engine/OutputPatternListener
			}));
			methods[2].setDisplayName("");
			methods[3] = new MethodDescriptor(org/joone/net/NestedNeuralLayer.getMethod("copyInto", new Class[] {
				org/joone/engine/NeuralLayer
			}));
			methods[3].setDisplayName("");
			methods[4] = new MethodDescriptor(org/joone/net/NestedNeuralLayer.getMethod("randomize", new Class[] {
				Double.TYPE
			}));
			methods[4].setDisplayName("");
			methods[5] = new MethodDescriptor(org/joone/net/NestedNeuralLayer.getMethod("removeAllInputs", new Class[0]));
			methods[5].setDisplayName("");
			methods[6] = new MethodDescriptor(org/joone/net/NestedNeuralLayer.getMethod("removeAllOutputs", new Class[0]));
			methods[6].setDisplayName("");
			methods[7] = new MethodDescriptor(org/joone/net/NestedNeuralLayer.getMethod("removeInputSynapse", new Class[] {
				org/joone/engine/InputPatternListener
			}));
			methods[7].setDisplayName("");
			methods[8] = new MethodDescriptor(org/joone/net/NestedNeuralLayer.getMethod("removeOutputSynapse", new Class[] {
				org/joone/engine/OutputPatternListener
			}));
			methods[8].setDisplayName("");
			methods[9] = new MethodDescriptor(org/joone/net/NestedNeuralLayer.getMethod("run", new Class[0]));
			methods[9].setDisplayName("");
			methods[10] = new MethodDescriptor(org/joone/net/NestedNeuralLayer.getMethod("start", new Class[0]));
			methods[10].setDisplayName("");
			methods[11] = new MethodDescriptor(org/joone/net/NestedNeuralLayer.getMethod("stop", new Class[0]));
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
