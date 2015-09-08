// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileInputSynapseBeanInfo.java

package org.joone.io;

import java.beans.*;
import org.joone.engine.Pattern;

// Referenced classes of package org.joone.io:
//			FileInputSynapse

public class FileInputSynapseBeanInfo extends SimpleBeanInfo
{

	private static BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/io/FileInputSynapse, null);
	private static final int PROPERTY_advancedColumnSelector = 0;
	private static final int PROPERTY_buffered = 1;
	private static final int PROPERTY_decimalPoint = 2;
	private static final int PROPERTY_enabled = 3;
	private static final int PROPERTY_firstRow = 4;
	private static final int PROPERTY_inputFile = 5;
	private static final int PROPERTY_inputPatterns = 6;
	private static final int PROPERTY_lastRow = 7;
	private static final int PROPERTY_maxBufSize = 8;
	private static final int PROPERTY_monitor = 9;
	private static final int PROPERTY_name = 10;
	private static final int PROPERTY_plugIn = 11;
	private static final int PROPERTY_stepCounter = 12;
	private static PropertyDescriptor properties[];
	private static EventSetDescriptor eventSets[] = new EventSetDescriptor[0];
	private static final int METHOD_addNoise0 = 0;
	private static final int METHOD_canCountSteps1 = 1;
	private static final int METHOD_fwdGet2 = 2;
	private static final int METHOD_fwdPut3 = 3;
	private static final int METHOD_gotoFirstLine4 = 4;
	private static final int METHOD_gotoLine5 = 5;
	private static final int METHOD_readAll6 = 6;
	private static final int METHOD_revGet7 = 7;
	private static final int METHOD_revPut8 = 8;
	private static MethodDescriptor methods[];
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public FileInputSynapseBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		return properties;
	}

	private static EventSetDescriptor[] getEdescriptor()
	{
		return eventSets;
	}

	private static MethodDescriptor[] getMdescriptor()
	{
		return methods;
	}

	public BeanDescriptor getBeanDescriptor()
	{
		return beanDescriptor;
	}

	public PropertyDescriptor[] getPropertyDescriptors()
	{
		return properties;
	}

	public EventSetDescriptor[] getEventSetDescriptors()
	{
		return eventSets;
	}

	public MethodDescriptor[] getMethodDescriptors()
	{
		return methods;
	}

	public int getDefaultPropertyIndex()
	{
		return -1;
	}

	public int getDefaultEventIndex()
	{
		return -1;
	}

	static 
	{
		properties = new PropertyDescriptor[13];
		try
		{
			properties[0] = new PropertyDescriptor("advancedColumnSelector", org/joone/io/FileInputSynapse, "getAdvancedColumnSelector", "setAdvancedColumnSelector");
			properties[0].setDisplayName("Advanced Column Selector");
			properties[1] = new PropertyDescriptor("buffered", org/joone/io/FileInputSynapse, "isBuffered", "setBuffered");
			properties[2] = new PropertyDescriptor("decimalPoint", org/joone/io/FileInputSynapse, "getDecimalPoint", "setDecimalPoint");
			properties[2].setExpert(true);
			properties[3] = new PropertyDescriptor("enabled", org/joone/io/FileInputSynapse, "isEnabled", "setEnabled");
			properties[4] = new PropertyDescriptor("firstRow", org/joone/io/FileInputSynapse, "getFirstRow", "setFirstRow");
			properties[5] = new PropertyDescriptor("inputFile", org/joone/io/FileInputSynapse, "getInputFile", "setInputFile");
			properties[6] = new PropertyDescriptor("inputPatterns", org/joone/io/FileInputSynapse, "getInputPatterns", "setInputPatterns");
			properties[6].setExpert(true);
			properties[7] = new PropertyDescriptor("lastRow", org/joone/io/FileInputSynapse, "getLastRow", "setLastRow");
			properties[8] = new PropertyDescriptor("maxBufSize", org/joone/io/FileInputSynapse, "getMaxBufSize", "setMaxBufSize");
			properties[9] = new PropertyDescriptor("monitor", org/joone/io/FileInputSynapse, "getMonitor", "setMonitor");
			properties[9].setExpert(true);
			properties[10] = new PropertyDescriptor("name", org/joone/io/FileInputSynapse, "getName", "setName");
			properties[11] = new PropertyDescriptor("plugIn", org/joone/io/FileInputSynapse, "getPlugIn", "setPlugIn");
			properties[11].setExpert(true);
			properties[12] = new PropertyDescriptor("stepCounter", org/joone/io/FileInputSynapse, "isStepCounter", "setStepCounter");
		}
		catch (IntrospectionException e)
		{
			e.printStackTrace();
		}
		methods = new MethodDescriptor[9];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/io/FileInputSynapse.getMethod("addNoise", new Class[] {
				Double.TYPE
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/io/FileInputSynapse.getMethod("canCountSteps", new Class[0]));
			methods[1].setDisplayName("");
			methods[2] = new MethodDescriptor(org/joone/io/FileInputSynapse.getMethod("fwdGet", new Class[0]));
			methods[2].setDisplayName("");
			methods[3] = new MethodDescriptor(org/joone/io/FileInputSynapse.getMethod("fwdPut", new Class[] {
				org/joone/engine/Pattern
			}));
			methods[3].setDisplayName("");
			methods[4] = new MethodDescriptor(org/joone/io/FileInputSynapse.getMethod("gotoFirstLine", new Class[0]));
			methods[4].setDisplayName("");
			methods[5] = new MethodDescriptor(org/joone/io/FileInputSynapse.getMethod("gotoLine", new Class[] {
				Integer.TYPE
			}));
			methods[5].setDisplayName("");
			methods[6] = new MethodDescriptor(org/joone/io/FileInputSynapse.getMethod("readAll", new Class[0]));
			methods[6].setDisplayName("");
			methods[7] = new MethodDescriptor(org/joone/io/FileInputSynapse.getMethod("revGet", new Class[0]));
			methods[7].setDisplayName("");
			methods[8] = new MethodDescriptor(org/joone/io/FileInputSynapse.getMethod("revPut", new Class[] {
				org/joone/engine/Pattern
			}));
			methods[8].setDisplayName("");
		}
		catch (Exception exception) { }
	}
}
