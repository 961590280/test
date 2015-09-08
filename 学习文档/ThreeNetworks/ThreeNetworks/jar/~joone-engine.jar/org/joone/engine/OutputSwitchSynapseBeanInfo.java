// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OutputSwitchSynapseBeanInfo.java

package org.joone.engine;

import java.beans.*;

// Referenced classes of package org.joone.engine:
//			OutputSwitchSynapse, OutputPatternListener, Pattern

public class OutputSwitchSynapseBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_allOutputs = 0;
	private static final int PROPERTY_inputDimension = 1;
	private static final int PROPERTY_activeOutput = 2;
	private static final int PROPERTY_defaultOutput = 3;
	private static final int PROPERTY_enabled = 4;
	private static final int PROPERTY_name = 5;
	private static final int PROPERTY_monitor = 6;
	private static final int METHOD_reset0 = 0;
	private static final int METHOD_removeOutputSynapse1 = 1;
	private static final int METHOD_addOutputSynapse2 = 2;
	private static final int METHOD_resetOutput3 = 3;
	private static final int METHOD_fwdPut4 = 4;
	private static final int METHOD_revGet5 = 5;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public OutputSwitchSynapseBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/engine/OutputSwitchSynapse, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[7];
		try
		{
			properties[0] = new PropertyDescriptor("allOutputs", org/joone/engine/OutputSwitchSynapse, "getAllOutputs", null);
			properties[1] = new PropertyDescriptor("inputDimension", org/joone/engine/OutputSwitchSynapse, "getInputDimension", "setInputDimension");
			properties[1].setExpert(true);
			properties[2] = new PropertyDescriptor("activeOutput", org/joone/engine/OutputSwitchSynapse, "getActiveOutput", "setActiveOutput");
			properties[3] = new PropertyDescriptor("defaultOutput", org/joone/engine/OutputSwitchSynapse, "getDefaultOutput", "setDefaultOutput");
			properties[4] = new PropertyDescriptor("enabled", org/joone/engine/OutputSwitchSynapse, "isEnabled", "setEnabled");
			properties[5] = new PropertyDescriptor("name", org/joone/engine/OutputSwitchSynapse, "getName", "setName");
			properties[6] = new PropertyDescriptor("monitor", org/joone/engine/OutputSwitchSynapse, "getMonitor", "setMonitor");
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
		MethodDescriptor methods[] = new MethodDescriptor[6];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/engine/OutputSwitchSynapse.getMethod("reset", new Class[0]));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/engine/OutputSwitchSynapse.getMethod("removeOutputSynapse", new Class[] {
				java/lang/String
			}));
			methods[1].setDisplayName("");
			methods[2] = new MethodDescriptor(org/joone/engine/OutputSwitchSynapse.getMethod("addOutputSynapse", new Class[] {
				org/joone/engine/OutputPatternListener
			}));
			methods[2].setDisplayName("");
			methods[3] = new MethodDescriptor(org/joone/engine/OutputSwitchSynapse.getMethod("resetOutput", new Class[0]));
			methods[3].setDisplayName("");
			methods[4] = new MethodDescriptor(org/joone/engine/OutputSwitchSynapse.getMethod("fwdPut", new Class[] {
				org/joone/engine/Pattern
			}));
			methods[4].setDisplayName("");
			methods[5] = new MethodDescriptor(org/joone/engine/OutputSwitchSynapse.getMethod("revGet", new Class[0]));
			methods[5].setDisplayName("");
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
