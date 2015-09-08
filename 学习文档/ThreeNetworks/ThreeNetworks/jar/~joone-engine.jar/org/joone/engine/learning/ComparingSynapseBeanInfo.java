// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ComparingSynapseBeanInfo.java

package org.joone.engine.learning;

import java.beans.*;
import org.joone.engine.OutputPatternListener;
import org.joone.engine.Pattern;

// Referenced classes of package org.joone.engine.learning:
//			ComparingSynapse

public class ComparingSynapseBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_enabled = 0;
	private static final int PROPERTY_name = 1;
	private static final int METHOD_fwdPut0 = 0;
	private static final int METHOD_revGet1 = 1;
	private static final int METHOD_addResultSynapse2 = 2;
	private static final int METHOD_removeResultSynapse3 = 3;
	private static final int METHOD_start4 = 4;
	private static final int METHOD_stop5 = 5;
	private static final int METHOD_resetInput6 = 6;
	private static final int METHOD_check7 = 7;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public ComparingSynapseBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/engine/learning/ComparingSynapse, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[2];
		try
		{
			properties[0] = new PropertyDescriptor("enabled", org/joone/engine/learning/ComparingSynapse, "isEnabled", "setEnabled");
			properties[1] = new PropertyDescriptor("name", org/joone/engine/learning/ComparingSynapse, "getName", "setName");
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
		MethodDescriptor methods[] = new MethodDescriptor[8];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/engine/learning/ComparingSynapse.getMethod("fwdPut", new Class[] {
				org/joone/engine/Pattern
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/engine/learning/ComparingSynapse.getMethod("revGet", new Class[0]));
			methods[1].setDisplayName("");
			methods[2] = new MethodDescriptor(org/joone/engine/learning/ComparingSynapse.getMethod("addResultSynapse", new Class[] {
				org/joone/engine/OutputPatternListener
			}));
			methods[2].setDisplayName("");
			methods[3] = new MethodDescriptor(org/joone/engine/learning/ComparingSynapse.getMethod("removeResultSynapse", new Class[] {
				org/joone/engine/OutputPatternListener
			}));
			methods[3].setDisplayName("");
			methods[4] = new MethodDescriptor(org/joone/engine/learning/ComparingSynapse.getMethod("start", new Class[0]));
			methods[4].setDisplayName("");
			methods[5] = new MethodDescriptor(org/joone/engine/learning/ComparingSynapse.getMethod("stop", new Class[0]));
			methods[5].setDisplayName("");
			methods[6] = new MethodDescriptor(org/joone/engine/learning/ComparingSynapse.getMethod("resetInput", new Class[0]));
			methods[6].setDisplayName("");
			methods[7] = new MethodDescriptor(org/joone/engine/learning/ComparingSynapse.getMethod("check", new Class[0]));
			methods[7].setDisplayName("");
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
