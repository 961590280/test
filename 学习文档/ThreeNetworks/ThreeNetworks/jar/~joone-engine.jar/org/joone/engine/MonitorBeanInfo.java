// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MonitorBeanInfo.java

package org.joone.engine;

import java.beans.*;

// Referenced classes of package org.joone.engine:
//			Monitor

public class MonitorBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_batchSize = 0;
	private static final int PROPERTY_currentCicle = 1;
	private static final int PROPERTY_globalError = 2;
	private static final int PROPERTY_learning = 3;
	private static final int PROPERTY_learningMode = 4;
	private static final int PROPERTY_learningRate = 5;
	private static final int PROPERTY_momentum = 6;
	private static final int PROPERTY_preLearning = 7;
	private static final int PROPERTY_singleThreadMode = 8;
	private static final int PROPERTY_supervised = 9;
	private static final int PROPERTY_totCicles = 10;
	private static final int PROPERTY_trainingPatterns = 11;
	private static final int PROPERTY_useRMSE = 12;
	private static final int PROPERTY_validation = 13;
	private static final int PROPERTY_validationPatterns = 14;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public MonitorBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/engine/Monitor, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[15];
		try
		{
			properties[0] = new PropertyDescriptor("batchSize", org/joone/engine/Monitor, "getBatchSize", "setBatchSize");
			properties[1] = new PropertyDescriptor("currentCicle", org/joone/engine/Monitor, "getCurrentCicle", "setCurrentCicle");
			properties[1].setExpert(true);
			properties[1].setHidden(true);
			properties[2] = new PropertyDescriptor("globalError", org/joone/engine/Monitor, "getGlobalError", "setGlobalError");
			properties[2].setExpert(true);
			properties[2].setHidden(true);
			properties[3] = new PropertyDescriptor("learning", org/joone/engine/Monitor, "isLearning", "setLearning");
			properties[4] = new PropertyDescriptor("learningMode", org/joone/engine/Monitor, "getLearningMode", "setLearningMode");
			properties[5] = new PropertyDescriptor("learningRate", org/joone/engine/Monitor, "getLearningRate", "setLearningRate");
			properties[6] = new PropertyDescriptor("momentum", org/joone/engine/Monitor, "getMomentum", "setMomentum");
			properties[7] = new PropertyDescriptor("preLearning", org/joone/engine/Monitor, "getPreLearning", "setPreLearning");
			properties[7].setDisplayName("pre-learning cycles");
			properties[8] = new PropertyDescriptor("singleThreadMode", org/joone/engine/Monitor, "isSingleThreadMode", "setSingleThreadMode");
			properties[9] = new PropertyDescriptor("supervised", org/joone/engine/Monitor, "isSupervised", "setSupervised");
			properties[10] = new PropertyDescriptor("totCicles", org/joone/engine/Monitor, "getTotCicles", "setTotCicles");
			properties[10].setDisplayName("epochs");
			properties[11] = new PropertyDescriptor("trainingPatterns", org/joone/engine/Monitor, "getTrainingPatterns", "setTrainingPatterns");
			properties[11].setDisplayName("training patterns");
			properties[12] = new PropertyDescriptor("useRMSE", org/joone/engine/Monitor, "isUseRMSE", "setUseRMSE");
			properties[13] = new PropertyDescriptor("validation", org/joone/engine/Monitor, "isValidation", "setValidation");
			properties[14] = new PropertyDescriptor("validationPatterns", org/joone/engine/Monitor, "getValidationPatterns", "setValidationPatterns");
			properties[14].setDisplayName("validation patterns");
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
