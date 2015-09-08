// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LearningSwitchBeanInfo.java

package org.joone.util;

import java.beans.*;

// Referenced classes of package org.joone.util:
//			LearningSwitch

public class LearningSwitchBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_activeInput = 0;
	private static final int PROPERTY_allInputs = 1;
	private static final int PROPERTY_defaultInput = 2;
	private static final int PROPERTY_monitor = 3;
	private static final int PROPERTY_name = 4;
	private static final int PROPERTY_trainingSet = 5;
	private static final int PROPERTY_validationSet = 6;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public LearningSwitchBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/util/LearningSwitch, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[7];
		try
		{
			properties[0] = new PropertyDescriptor("activeInput", org/joone/util/LearningSwitch, "getActiveInput", "setActiveInput");
			properties[0].setExpert(true);
			properties[1] = new PropertyDescriptor("allInputs", org/joone/util/LearningSwitch, "getAllInputs", "setAllInputs");
			properties[1].setExpert(true);
			properties[2] = new PropertyDescriptor("defaultInput", org/joone/util/LearningSwitch, "getDefaultInput", "setDefaultInput");
			properties[2].setExpert(true);
			properties[3] = new PropertyDescriptor("monitor", org/joone/util/LearningSwitch, "getMonitor", "setMonitor");
			properties[3].setExpert(true);
			properties[4] = new PropertyDescriptor("name", org/joone/util/LearningSwitch, "getName", "setName");
			properties[5] = new PropertyDescriptor("trainingSet", org/joone/util/LearningSwitch, "getTrainingSet", "setTrainingSet");
			properties[5].setExpert(true);
			properties[6] = new PropertyDescriptor("validationSet", org/joone/util/LearningSwitch, "getValidationSet", "setValidationSet");
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
		MethodDescriptor methods[] = new MethodDescriptor[0];
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
