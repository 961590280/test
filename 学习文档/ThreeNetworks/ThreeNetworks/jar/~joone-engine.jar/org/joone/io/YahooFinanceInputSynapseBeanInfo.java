// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   YahooFinanceInputSynapseBeanInfo.java

package org.joone.io;

import java.beans.*;
import org.joone.engine.Pattern;

// Referenced classes of package org.joone.io:
//			YahooFinanceInputSynapse

public class YahooFinanceInputSynapseBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_advancedColumnSelector = 0;
	private static final int PROPERTY_buffered = 1;
	private static final int PROPERTY_enabled = 2;
	private static final int PROPERTY_endDate = 3;
	private static final int PROPERTY_firstRow = 4;
	private static final int PROPERTY_lastRow = 5;
	private static final int PROPERTY_maxBufSize = 6;
	private static final int PROPERTY_name = 7;
	private static final int PROPERTY_period = 8;
	private static final int PROPERTY_plugIn = 9;
	private static final int PROPERTY_startDate = 10;
	private static final int PROPERTY_stepCounter = 11;
	private static final int PROPERTY_stockData = 12;
	private static final int PROPERTY_symbol = 13;
	private static final int METHOD_addNoise0 = 0;
	private static final int METHOD_canCountSteps1 = 1;
	private static final int METHOD_check2 = 2;
	private static final int METHOD_fwdGet3 = 3;
	private static final int METHOD_fwdPut4 = 4;
	private static final int METHOD_gotoFirstLine5 = 5;
	private static final int METHOD_gotoLine6 = 6;
	private static final int METHOD_initLearner7 = 7;
	private static final int METHOD_numColumns8 = 8;
	private static final int METHOD_randomize9 = 9;
	private static final int METHOD_readAll10 = 10;
	private static final int METHOD_reset11 = 11;
	private static final int METHOD_resetInput12 = 12;
	private static final int METHOD_revGet13 = 13;
	private static final int METHOD_revPut14 = 14;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public YahooFinanceInputSynapseBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/io/YahooFinanceInputSynapse, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[14];
		try
		{
			properties[0] = new PropertyDescriptor("advancedColumnSelector", org/joone/io/YahooFinanceInputSynapse, "getAdvancedColumnSelector", "setAdvancedColumnSelector");
			properties[1] = new PropertyDescriptor("buffered", org/joone/io/YahooFinanceInputSynapse, "isBuffered", "setBuffered");
			properties[2] = new PropertyDescriptor("enabled", org/joone/io/YahooFinanceInputSynapse, "isEnabled", "setEnabled");
			properties[3] = new PropertyDescriptor("endDate", org/joone/io/YahooFinanceInputSynapse, "getEndDate", "setEndDate");
			properties[4] = new PropertyDescriptor("firstRow", org/joone/io/YahooFinanceInputSynapse, "getFirstRow", "setFirstRow");
			properties[5] = new PropertyDescriptor("lastRow", org/joone/io/YahooFinanceInputSynapse, "getLastRow", "setLastRow");
			properties[6] = new PropertyDescriptor("maxBufSize", org/joone/io/YahooFinanceInputSynapse, "getMaxBufSize", "setMaxBufSize");
			properties[7] = new PropertyDescriptor("name", org/joone/io/YahooFinanceInputSynapse, "getName", "setName");
			properties[8] = new PropertyDescriptor("period", org/joone/io/YahooFinanceInputSynapse, "getPeriod", "setPeriod");
			properties[9] = new PropertyDescriptor("plugIn", org/joone/io/YahooFinanceInputSynapse, "getPlugIn", "setPlugIn");
			properties[9].setExpert(true);
			properties[10] = new PropertyDescriptor("startDate", org/joone/io/YahooFinanceInputSynapse, "getStartDate", "setStartDate");
			properties[11] = new PropertyDescriptor("stepCounter", org/joone/io/YahooFinanceInputSynapse, "isStepCounter", "setStepCounter");
			properties[12] = new PropertyDescriptor("stockData", org/joone/io/YahooFinanceInputSynapse, "getStockData", null);
			properties[13] = new PropertyDescriptor("symbol", org/joone/io/YahooFinanceInputSynapse, "getSymbol", "setSymbol");
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
		MethodDescriptor methods[] = new MethodDescriptor[15];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/io/YahooFinanceInputSynapse.getMethod("addNoise", new Class[] {
				Double.TYPE
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/io/YahooFinanceInputSynapse.getMethod("canCountSteps", new Class[0]));
			methods[1].setDisplayName("");
			methods[2] = new MethodDescriptor(org/joone/io/YahooFinanceInputSynapse.getMethod("check", new Class[0]));
			methods[2].setDisplayName("");
			methods[3] = new MethodDescriptor(org/joone/io/YahooFinanceInputSynapse.getMethod("fwdGet", new Class[0]));
			methods[3].setDisplayName("");
			methods[4] = new MethodDescriptor(org/joone/io/YahooFinanceInputSynapse.getMethod("fwdPut", new Class[] {
				org/joone/engine/Pattern
			}));
			methods[4].setDisplayName("");
			methods[5] = new MethodDescriptor(org/joone/io/YahooFinanceInputSynapse.getMethod("gotoFirstLine", new Class[0]));
			methods[5].setDisplayName("");
			methods[6] = new MethodDescriptor(org/joone/io/YahooFinanceInputSynapse.getMethod("gotoLine", new Class[] {
				Integer.TYPE
			}));
			methods[6].setDisplayName("");
			methods[7] = new MethodDescriptor(org/joone/io/YahooFinanceInputSynapse.getMethod("initLearner", new Class[0]));
			methods[7].setDisplayName("");
			methods[8] = new MethodDescriptor(org/joone/io/YahooFinanceInputSynapse.getMethod("numColumns", new Class[0]));
			methods[8].setDisplayName("");
			methods[9] = new MethodDescriptor(org/joone/io/YahooFinanceInputSynapse.getMethod("randomize", new Class[] {
				Double.TYPE
			}));
			methods[9].setDisplayName("");
			methods[10] = new MethodDescriptor(org/joone/io/YahooFinanceInputSynapse.getMethod("readAll", new Class[0]));
			methods[10].setDisplayName("");
			methods[11] = new MethodDescriptor(org/joone/io/YahooFinanceInputSynapse.getMethod("reset", new Class[0]));
			methods[11].setDisplayName("");
			methods[12] = new MethodDescriptor(org/joone/io/YahooFinanceInputSynapse.getMethod("resetInput", new Class[0]));
			methods[12].setDisplayName("");
			methods[13] = new MethodDescriptor(org/joone/io/YahooFinanceInputSynapse.getMethod("revGet", new Class[0]));
			methods[13].setDisplayName("");
			methods[14] = new MethodDescriptor(org/joone/io/YahooFinanceInputSynapse.getMethod("revPut", new Class[] {
				org/joone/engine/Pattern
			}));
			methods[14].setDisplayName("");
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
