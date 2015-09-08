// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StreamInputSynapseBeanInfo.java

package org.joone.io;

import java.beans.*;
import org.joone.engine.Pattern;
import org.joone.util.ConverterPlugIn;

// Referenced classes of package org.joone.io:
//			StreamInputSynapse

public class StreamInputSynapseBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_advancedColumnSelector = 0;
	private static final int PROPERTY_buffered = 1;
	private static final int PROPERTY_decimalPoint = 2;
	private static final int PROPERTY_enabled = 3;
	private static final int PROPERTY_firstRow = 4;
	private static final int PROPERTY_inputPatterns = 5;
	private static final int PROPERTY_lastRow = 6;
	private static final int PROPERTY_learningRate = 7;
	private static final int PROPERTY_maxBufSize = 8;
	private static final int PROPERTY_name = 9;
	private static final int PROPERTY_stepCounter = 10;
	private static final int METHOD_addNoise0 = 0;
	private static final int METHOD_canCountSteps1 = 1;
	private static final int METHOD_check2 = 2;
	private static final int METHOD_fwdGet3 = 3;
	private static final int METHOD_fwdPut4 = 4;
	private static final int METHOD_gotoFirstLine5 = 5;
	private static final int METHOD_gotoLine6 = 6;
	private static final int METHOD_initLearner7 = 7;
	private static final int METHOD_InspectableTitle8 = 8;
	private static final int METHOD_Inspections9 = 9;
	private static final int METHOD_numColumns10 = 10;
	private static final int METHOD_randomize11 = 11;
	private static final int METHOD_readAll12 = 12;
	private static final int METHOD_reset13 = 13;
	private static final int METHOD_resetInput14 = 14;
	private static final int METHOD_revGet15 = 15;
	private static final int METHOD_revPut16 = 16;
	private static final int METHOD_setPlugin17 = 17;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public StreamInputSynapseBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/io/StreamInputSynapse, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[11];
		try
		{
			properties[0] = new PropertyDescriptor("advancedColumnSelector", org/joone/io/StreamInputSynapse, "getAdvancedColumnSelector", "setAdvancedColumnSelector");
			properties[1] = new PropertyDescriptor("buffered", org/joone/io/StreamInputSynapse, "isBuffered", "setBuffered");
			properties[2] = new PropertyDescriptor("decimalPoint", org/joone/io/StreamInputSynapse, "getDecimalPoint", "setDecimalPoint");
			properties[3] = new PropertyDescriptor("enabled", org/joone/io/StreamInputSynapse, "isEnabled", "setEnabled");
			properties[4] = new PropertyDescriptor("firstRow", org/joone/io/StreamInputSynapse, "getFirstRow", "setFirstRow");
			properties[5] = new PropertyDescriptor("inputPatterns", org/joone/io/StreamInputSynapse, "getInputPatterns", "setInputPatterns");
			properties[5].setExpert(true);
			properties[6] = new PropertyDescriptor("lastRow", org/joone/io/StreamInputSynapse, "getLastRow", "setLastRow");
			properties[7] = new PropertyDescriptor("learningRate", org/joone/io/StreamInputSynapse, "getLearningRate", "setLearningRate");
			properties[7].setHidden(true);
			properties[8] = new PropertyDescriptor("maxBufSize", org/joone/io/StreamInputSynapse, "getMaxBufSize", "setMaxBufSize");
			properties[9] = new PropertyDescriptor("name", org/joone/io/StreamInputSynapse, "getName", "setName");
			properties[10] = new PropertyDescriptor("stepCounter", org/joone/io/StreamInputSynapse, "isStepCounter", "setStepCounter");
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
		MethodDescriptor methods[] = new MethodDescriptor[18];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/io/StreamInputSynapse.getMethod("addNoise", new Class[] {
				Double.TYPE
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/io/StreamInputSynapse.getMethod("canCountSteps", new Class[0]));
			methods[1].setDisplayName("");
			methods[2] = new MethodDescriptor(org/joone/io/StreamInputSynapse.getMethod("check", new Class[0]));
			methods[2].setDisplayName("");
			methods[3] = new MethodDescriptor(org/joone/io/StreamInputSynapse.getMethod("fwdGet", new Class[0]));
			methods[3].setDisplayName("");
			methods[4] = new MethodDescriptor(org/joone/io/StreamInputSynapse.getMethod("fwdPut", new Class[] {
				org/joone/engine/Pattern
			}));
			methods[4].setDisplayName("");
			methods[5] = new MethodDescriptor(org/joone/io/StreamInputSynapse.getMethod("gotoFirstLine", new Class[0]));
			methods[5].setDisplayName("");
			methods[6] = new MethodDescriptor(org/joone/io/StreamInputSynapse.getMethod("gotoLine", new Class[] {
				Integer.TYPE
			}));
			methods[6].setDisplayName("");
			methods[7] = new MethodDescriptor(org/joone/io/StreamInputSynapse.getMethod("initLearner", new Class[0]));
			methods[7].setDisplayName("");
			methods[8] = new MethodDescriptor(org/joone/io/StreamInputSynapse.getMethod("InspectableTitle", new Class[0]));
			methods[8].setDisplayName("");
			methods[9] = new MethodDescriptor(org/joone/io/StreamInputSynapse.getMethod("Inspections", new Class[0]));
			methods[9].setDisplayName("");
			methods[10] = new MethodDescriptor(org/joone/io/StreamInputSynapse.getMethod("numColumns", new Class[0]));
			methods[10].setDisplayName("");
			methods[11] = new MethodDescriptor(org/joone/io/StreamInputSynapse.getMethod("randomize", new Class[] {
				Double.TYPE
			}));
			methods[11].setDisplayName("");
			methods[12] = new MethodDescriptor(org/joone/io/StreamInputSynapse.getMethod("readAll", new Class[0]));
			methods[12].setDisplayName("");
			methods[13] = new MethodDescriptor(org/joone/io/StreamInputSynapse.getMethod("reset", new Class[0]));
			methods[13].setDisplayName("");
			methods[14] = new MethodDescriptor(org/joone/io/StreamInputSynapse.getMethod("resetInput", new Class[0]));
			methods[14].setDisplayName("");
			methods[15] = new MethodDescriptor(org/joone/io/StreamInputSynapse.getMethod("revGet", new Class[0]));
			methods[15].setDisplayName("");
			methods[16] = new MethodDescriptor(org/joone/io/StreamInputSynapse.getMethod("revPut", new Class[] {
				org/joone/engine/Pattern
			}));
			methods[16].setDisplayName("");
			methods[17] = new MethodDescriptor(org/joone/io/StreamInputSynapse.getMethod("setPlugin", new Class[] {
				org/joone/util/ConverterPlugIn
			}));
			methods[17].setDisplayName("");
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
