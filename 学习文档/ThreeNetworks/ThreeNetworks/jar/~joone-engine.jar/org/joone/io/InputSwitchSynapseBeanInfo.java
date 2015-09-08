// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InputSwitchSynapseBeanInfo.java

package org.joone.io;

import java.beans.*;
import org.joone.engine.Pattern;

// Referenced classes of package org.joone.io:
//			InputSwitchSynapse, StreamInputSynapse

public class InputSwitchSynapseBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_activeInput = 0;
	private static final int PROPERTY_advancedColumnSelector = 1;
	private static final int PROPERTY_allInputs = 2;
	private static final int PROPERTY_defaultInput = 3;
	private static final int PROPERTY_monitor = 4;
	private static final int PROPERTY_name = 5;
	private static final int METHOD_addInputSynapse0 = 0;
	private static final int METHOD_addNoise1 = 1;
	private static final int METHOD_canCountSteps2 = 2;
	private static final int METHOD_check3 = 3;
	private static final int METHOD_fwdGet4 = 4;
	private static final int METHOD_fwdPut5 = 5;
	private static final int METHOD_gotoFirstLine6 = 6;
	private static final int METHOD_gotoLine7 = 7;
	private static final int METHOD_randomize8 = 8;
	private static final int METHOD_readAll9 = 9;
	private static final int METHOD_removeAllInputs10 = 10;
	private static final int METHOD_removeInputSynapse11 = 11;
	private static final int METHOD_reset12 = 12;
	private static final int METHOD_resetInput13 = 13;
	private static final int METHOD_revGet14 = 14;
	private static final int METHOD_revPut15 = 15;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public InputSwitchSynapseBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/io/InputSwitchSynapse, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[6];
		try
		{
			properties[0] = new PropertyDescriptor("activeInput", org/joone/io/InputSwitchSynapse, "getActiveInput", "setActiveInput");
			properties[1] = new PropertyDescriptor("advancedColumnSelector", org/joone/io/InputSwitchSynapse, "getAdvancedColumnSelector", "setAdvancedColumnSelector");
			properties[1].setHidden(true);
			properties[2] = new PropertyDescriptor("allInputs", org/joone/io/InputSwitchSynapse, "getAllInputs", "setAllInputs");
			properties[2].setExpert(true);
			properties[3] = new PropertyDescriptor("defaultInput", org/joone/io/InputSwitchSynapse, "getDefaultInput", "setDefaultInput");
			properties[4] = new PropertyDescriptor("monitor", org/joone/io/InputSwitchSynapse, "getMonitor", "setMonitor");
			properties[4].setExpert(true);
			properties[5] = new PropertyDescriptor("name", org/joone/io/InputSwitchSynapse, "getName", "setName");
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
		MethodDescriptor methods[] = new MethodDescriptor[16];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/io/InputSwitchSynapse.getMethod("addInputSynapse", new Class[] {
				org/joone/io/StreamInputSynapse
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/io/InputSwitchSynapse.getMethod("addNoise", new Class[] {
				Double.TYPE
			}));
			methods[1].setDisplayName("");
			methods[2] = new MethodDescriptor(org/joone/io/InputSwitchSynapse.getMethod("canCountSteps", new Class[0]));
			methods[2].setDisplayName("");
			methods[3] = new MethodDescriptor(org/joone/io/InputSwitchSynapse.getMethod("check", new Class[0]));
			methods[3].setDisplayName("");
			methods[4] = new MethodDescriptor(org/joone/io/InputSwitchSynapse.getMethod("fwdGet", new Class[0]));
			methods[4].setDisplayName("");
			methods[5] = new MethodDescriptor(org/joone/io/InputSwitchSynapse.getMethod("fwdPut", new Class[] {
				org/joone/engine/Pattern
			}));
			methods[5].setDisplayName("");
			methods[6] = new MethodDescriptor(org/joone/io/InputSwitchSynapse.getMethod("gotoFirstLine", new Class[0]));
			methods[6].setDisplayName("");
			methods[7] = new MethodDescriptor(org/joone/io/InputSwitchSynapse.getMethod("gotoLine", new Class[] {
				Integer.TYPE
			}));
			methods[7].setDisplayName("");
			methods[8] = new MethodDescriptor(org/joone/io/InputSwitchSynapse.getMethod("randomize", new Class[] {
				Double.TYPE
			}));
			methods[8].setDisplayName("");
			methods[9] = new MethodDescriptor(org/joone/io/InputSwitchSynapse.getMethod("readAll", new Class[0]));
			methods[9].setDisplayName("");
			methods[10] = new MethodDescriptor(org/joone/io/InputSwitchSynapse.getMethod("removeAllInputs", new Class[0]));
			methods[10].setDisplayName("");
			methods[11] = new MethodDescriptor(org/joone/io/InputSwitchSynapse.getMethod("removeInputSynapse", new Class[] {
				java/lang/String
			}));
			methods[11].setDisplayName("");
			methods[12] = new MethodDescriptor(org/joone/io/InputSwitchSynapse.getMethod("reset", new Class[0]));
			methods[12].setDisplayName("");
			methods[13] = new MethodDescriptor(org/joone/io/InputSwitchSynapse.getMethod("resetInput", new Class[0]));
			methods[13].setDisplayName("");
			methods[14] = new MethodDescriptor(org/joone/io/InputSwitchSynapse.getMethod("revGet", new Class[0]));
			methods[14].setDisplayName("");
			methods[15] = new MethodDescriptor(org/joone/io/InputSwitchSynapse.getMethod("revPut", new Class[] {
				org/joone/engine/Pattern
			}));
			methods[15].setDisplayName("");
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
