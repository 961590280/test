// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TeachingSynapseBeanInfo.java

package org.joone.engine.learning;

import java.beans.*;
import org.joone.engine.OutputPatternListener;
import org.joone.engine.Pattern;
import org.joone.io.StreamInputSynapse;

// Referenced classes of package org.joone.engine.learning:
//			TeachingSynapse

public class TeachingSynapseBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_desired = 0;
	private static final int PROPERTY_enabled = 1;
	private static final int PROPERTY_monitor = 2;
	private static final int PROPERTY_name = 3;
	private static final int METHOD_addResultSynapse0 = 0;
	private static final int METHOD_check1 = 1;
	private static final int METHOD_fwdPut2 = 2;
	private static final int METHOD_getTheTeacherSynapse3 = 3;
	private static final int METHOD_init4 = 4;
	private static final int METHOD_removeResultSynapse5 = 5;
	private static final int METHOD_resetInput6 = 6;
	private static final int METHOD_revGet7 = 7;
	private static final int METHOD_setDesired8 = 8;
	private static final int METHOD_stop9 = 9;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public TeachingSynapseBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/engine/learning/TeachingSynapse, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[4];
		try
		{
			properties[0] = new PropertyDescriptor("desired", org/joone/engine/learning/TeachingSynapse, "getDesired", null);
			properties[0].setExpert(true);
			properties[1] = new PropertyDescriptor("enabled", org/joone/engine/learning/TeachingSynapse, "isEnabled", "setEnabled");
			properties[2] = new PropertyDescriptor("monitor", org/joone/engine/learning/TeachingSynapse, "getMonitor", "setMonitor");
			properties[2].setExpert(true);
			properties[3] = new PropertyDescriptor("name", org/joone/engine/learning/TeachingSynapse, "getName", "setName");
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
		MethodDescriptor methods[] = new MethodDescriptor[10];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/engine/learning/TeachingSynapse.getMethod("addResultSynapse", new Class[] {
				org/joone/engine/OutputPatternListener
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/engine/learning/TeachingSynapse.getMethod("check", new Class[0]));
			methods[1].setDisplayName("");
			methods[2] = new MethodDescriptor(org/joone/engine/learning/TeachingSynapse.getMethod("fwdPut", new Class[] {
				org/joone/engine/Pattern
			}));
			methods[2].setDisplayName("");
			methods[3] = new MethodDescriptor(org/joone/engine/learning/TeachingSynapse.getMethod("getTheTeacherSynapse", new Class[0]));
			methods[3].setDisplayName("");
			methods[4] = new MethodDescriptor(org/joone/engine/learning/TeachingSynapse.getMethod("init", new Class[0]));
			methods[4].setDisplayName("");
			methods[5] = new MethodDescriptor(org/joone/engine/learning/TeachingSynapse.getMethod("removeResultSynapse", new Class[] {
				org/joone/engine/OutputPatternListener
			}));
			methods[5].setDisplayName("");
			methods[6] = new MethodDescriptor(org/joone/engine/learning/TeachingSynapse.getMethod("resetInput", new Class[0]));
			methods[6].setDisplayName("");
			methods[7] = new MethodDescriptor(org/joone/engine/learning/TeachingSynapse.getMethod("revGet", new Class[0]));
			methods[7].setDisplayName("");
			methods[8] = new MethodDescriptor(org/joone/engine/learning/TeachingSynapse.getMethod("setDesired", new Class[] {
				org/joone/io/StreamInputSynapse
			}));
			methods[8].setDisplayName("");
			methods[9] = new MethodDescriptor(org/joone/engine/learning/TeachingSynapse.getMethod("stop", new Class[0]));
			methods[9].setDisplayName("");
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
