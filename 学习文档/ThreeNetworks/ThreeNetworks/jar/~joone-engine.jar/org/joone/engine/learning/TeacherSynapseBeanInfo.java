// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TeacherSynapseBeanInfo.java

package org.joone.engine.learning;

import java.beans.*;
import org.joone.engine.Pattern;

// Referenced classes of package org.joone.engine.learning:
//			TeacherSynapse

public class TeacherSynapseBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_desired = 0;
	private static final int PROPERTY_enabled = 1;
	private static final int PROPERTY_inputDimension = 2;
	private static final int PROPERTY_name = 3;
	private static final int PROPERTY_outputDimension = 4;
	private static final int METHOD_addNoise0 = 0;
	private static final int METHOD_canCountSteps1 = 1;
	private static final int METHOD_fwdGet2 = 2;
	private static final int METHOD_fwdPut3 = 3;
	private static final int METHOD_randomize4 = 4;
	private static final int METHOD_revGet5 = 5;
	private static final int METHOD_revPut6 = 6;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public TeacherSynapseBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/engine/learning/TeacherSynapse, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[5];
		try
		{
			properties[0] = new PropertyDescriptor("desired", org/joone/engine/learning/TeacherSynapse, "getDesired", null);
			properties[0].setExpert(true);
			properties[1] = new PropertyDescriptor("enabled", org/joone/engine/learning/TeacherSynapse, "isEnabled", "setEnabled");
			properties[2] = new PropertyDescriptor("inputDimension", org/joone/engine/learning/TeacherSynapse, "getInputDimension", "setInputDimension");
			properties[2].setExpert(true);
			properties[3] = new PropertyDescriptor("name", org/joone/engine/learning/TeacherSynapse, "getName", "setName");
			properties[4] = new PropertyDescriptor("outputDimension", org/joone/engine/learning/TeacherSynapse, "getOutputDimension", "setOutputDimension");
			properties[4].setExpert(true);
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
		MethodDescriptor methods[] = new MethodDescriptor[7];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/engine/learning/TeacherSynapse.getMethod("addNoise", new Class[] {
				Double.TYPE
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/engine/learning/TeacherSynapse.getMethod("canCountSteps", new Class[0]));
			methods[1].setDisplayName("");
			methods[2] = new MethodDescriptor(org/joone/engine/learning/TeacherSynapse.getMethod("fwdGet", new Class[0]));
			methods[2].setDisplayName("");
			methods[3] = new MethodDescriptor(org/joone/engine/learning/TeacherSynapse.getMethod("fwdPut", new Class[] {
				org/joone/engine/Pattern
			}));
			methods[3].setDisplayName("");
			methods[4] = new MethodDescriptor(org/joone/engine/learning/TeacherSynapse.getMethod("randomize", new Class[] {
				Double.TYPE
			}));
			methods[4].setDisplayName("");
			methods[5] = new MethodDescriptor(org/joone/engine/learning/TeacherSynapse.getMethod("revGet", new Class[0]));
			methods[5].setDisplayName("");
			methods[6] = new MethodDescriptor(org/joone/engine/learning/TeacherSynapse.getMethod("revPut", new Class[] {
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
