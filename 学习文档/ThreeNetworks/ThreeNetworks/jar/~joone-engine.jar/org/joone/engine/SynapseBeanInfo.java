// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SynapseBeanInfo.java

package org.joone.engine;

import java.beans.*;

// Referenced classes of package org.joone.engine:
//			Synapse, Pattern

public class SynapseBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_enabled = 0;
	private static final int PROPERTY_learner = 1;
	private static final int PROPERTY_loopBack = 2;
	private static final int PROPERTY_monitor = 3;
	private static final int PROPERTY_name = 4;
	private static final int PROPERTY_outputFull = 5;
	private static final int PROPERTY_weights = 6;
	private static final int METHOD_addNoise0 = 0;
	private static final int METHOD_canCountSteps1 = 1;
	private static final int METHOD_fwdGet2 = 2;
	private static final int METHOD_fwdPut3 = 3;
	private static final int METHOD_randomize4 = 4;
	private static final int METHOD_revGet5 = 5;
	private static final int METHOD_revPut6 = 6;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public SynapseBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/engine/Synapse, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[7];
		try
		{
			properties[0] = new PropertyDescriptor("enabled", org/joone/engine/Synapse, "isEnabled", "setEnabled");
			properties[1] = new PropertyDescriptor("learner", org/joone/engine/Synapse, "getLearner", null);
			properties[1].setExpert(true);
			properties[2] = new PropertyDescriptor("loopBack", org/joone/engine/Synapse, "isLoopBack", "setLoopBack");
			properties[3] = new PropertyDescriptor("monitor", org/joone/engine/Synapse, "getMonitor", "setMonitor");
			properties[3].setExpert(true);
			properties[4] = new PropertyDescriptor("name", org/joone/engine/Synapse, "getName", "setName");
			properties[5] = new PropertyDescriptor("outputFull", org/joone/engine/Synapse, "isOutputFull", "setOutputFull");
			properties[5].setExpert(true);
			properties[6] = new PropertyDescriptor("weights", org/joone/engine/Synapse, "getWeights", "setWeights");
			properties[6].setExpert(true);
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
		MethodDescriptor methods[] = new MethodDescriptor[7];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/engine/Synapse.getMethod("addNoise", new Class[] {
				Double.TYPE
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/engine/Synapse.getMethod("canCountSteps", new Class[0]));
			methods[1].setDisplayName("");
			methods[2] = new MethodDescriptor(org/joone/engine/Synapse.getMethod("fwdGet", new Class[0]));
			methods[2].setDisplayName("");
			methods[3] = new MethodDescriptor(org/joone/engine/Synapse.getMethod("fwdPut", new Class[] {
				org/joone/engine/Pattern
			}));
			methods[3].setDisplayName("");
			methods[4] = new MethodDescriptor(org/joone/engine/Synapse.getMethod("randomize", new Class[] {
				Double.TYPE
			}));
			methods[4].setDisplayName("");
			methods[5] = new MethodDescriptor(org/joone/engine/Synapse.getMethod("revGet", new Class[0]));
			methods[5].setDisplayName("");
			methods[6] = new MethodDescriptor(org/joone/engine/Synapse.getMethod("revPut", new Class[] {
				org/joone/engine/Pattern
			}));
			methods[6].setDisplayName("");
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
