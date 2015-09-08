// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InputConnectorBeanInfo.java

package org.joone.io;

import java.beans.*;
import org.joone.engine.Pattern;
import org.joone.util.ConverterPlugIn;

// Referenced classes of package org.joone.io:
//			InputConnector

public class InputConnectorBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_advancedColumnSelector = 0;
	private static final int PROPERTY_buffered = 1;
	private static final int PROPERTY_enabled = 2;
	private static final int PROPERTY_firstRow = 3;
	private static final int PROPERTY_lastRow = 4;
	private static final int PROPERTY_name = 5;
	private static final int PROPERTY_stepCounter = 6;
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

	public InputConnectorBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/io/InputConnector, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[7];
		try
		{
			properties[0] = new PropertyDescriptor("advancedColumnSelector", org/joone/io/InputConnector, "getAdvancedColumnSelector", "setAdvancedColumnSelector");
			properties[1] = new PropertyDescriptor("buffered", org/joone/io/InputConnector, "isBuffered", "setBuffered");
			properties[2] = new PropertyDescriptor("enabled", org/joone/io/InputConnector, "isEnabled", "setEnabled");
			properties[3] = new PropertyDescriptor("firstRow", org/joone/io/InputConnector, "getFirstRow", "setFirstRow");
			properties[4] = new PropertyDescriptor("lastRow", org/joone/io/InputConnector, "getLastRow", "setLastRow");
			properties[5] = new PropertyDescriptor("name", org/joone/io/InputConnector, "getName", "setName");
			properties[6] = new PropertyDescriptor("stepCounter", org/joone/io/InputConnector, "isStepCounter", "setStepCounter");
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
		MethodDescriptor methods[] = new MethodDescriptor[18];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/io/InputConnector.getMethod("addNoise", new Class[] {
				Double.TYPE
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/io/InputConnector.getMethod("canCountSteps", new Class[0]));
			methods[1].setDisplayName("");
			methods[2] = new MethodDescriptor(org/joone/io/InputConnector.getMethod("check", new Class[0]));
			methods[2].setDisplayName("");
			methods[3] = new MethodDescriptor(org/joone/io/InputConnector.getMethod("fwdGet", new Class[0]));
			methods[3].setDisplayName("");
			methods[4] = new MethodDescriptor(org/joone/io/InputConnector.getMethod("fwdPut", new Class[] {
				org/joone/engine/Pattern
			}));
			methods[4].setDisplayName("");
			methods[5] = new MethodDescriptor(org/joone/io/InputConnector.getMethod("gotoFirstLine", new Class[0]));
			methods[5].setDisplayName("");
			methods[6] = new MethodDescriptor(org/joone/io/InputConnector.getMethod("gotoLine", new Class[] {
				Integer.TYPE
			}));
			methods[6].setDisplayName("");
			methods[7] = new MethodDescriptor(org/joone/io/InputConnector.getMethod("initLearner", new Class[0]));
			methods[7].setDisplayName("");
			methods[8] = new MethodDescriptor(org/joone/io/InputConnector.getMethod("InspectableTitle", new Class[0]));
			methods[8].setDisplayName("");
			methods[9] = new MethodDescriptor(org/joone/io/InputConnector.getMethod("Inspections", new Class[0]));
			methods[9].setDisplayName("");
			methods[10] = new MethodDescriptor(org/joone/io/InputConnector.getMethod("numColumns", new Class[0]));
			methods[10].setDisplayName("");
			methods[11] = new MethodDescriptor(org/joone/io/InputConnector.getMethod("randomize", new Class[] {
				Double.TYPE
			}));
			methods[11].setDisplayName("");
			methods[12] = new MethodDescriptor(org/joone/io/InputConnector.getMethod("readAll", new Class[0]));
			methods[12].setDisplayName("");
			methods[13] = new MethodDescriptor(org/joone/io/InputConnector.getMethod("reset", new Class[0]));
			methods[13].setDisplayName("");
			methods[14] = new MethodDescriptor(org/joone/io/InputConnector.getMethod("resetInput", new Class[0]));
			methods[14].setDisplayName("");
			methods[15] = new MethodDescriptor(org/joone/io/InputConnector.getMethod("revGet", new Class[0]));
			methods[15].setDisplayName("");
			methods[16] = new MethodDescriptor(org/joone/io/InputConnector.getMethod("revPut", new Class[] {
				org/joone/engine/Pattern
			}));
			methods[16].setDisplayName("");
			methods[17] = new MethodDescriptor(org/joone/io/InputConnector.getMethod("setPlugin", new Class[] {
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
