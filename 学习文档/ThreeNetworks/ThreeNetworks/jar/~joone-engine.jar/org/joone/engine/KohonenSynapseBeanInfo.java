// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   KohonenSynapseBeanInfo.java

package org.joone.engine;

import java.beans.*;

// Referenced classes of package org.joone.engine:
//			KohonenSynapse, NeuralNetEvent, Pattern

public class KohonenSynapseBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_enabled = 0;
	private static final int PROPERTY_loopBack = 1;
	private static final int PROPERTY_monitor = 2;
	private static final int PROPERTY_name = 3;
	private static final int PROPERTY_orderingPhase = 4;
	private static final int PROPERTY_timeConstant = 5;
	private static final int PROPERTY_weights = 6;
	private static final int METHOD_addNoise0 = 0;
	private static final int METHOD_canCountSteps1 = 1;
	private static final int METHOD_check2 = 2;
	private static final int METHOD_cicleTerminated3 = 3;
	private static final int METHOD_errorChanged4 = 4;
	private static final int METHOD_fwdGet5 = 5;
	private static final int METHOD_fwdPut6 = 6;
	private static final int METHOD_netStarted7 = 7;
	private static final int METHOD_netStopped8 = 8;
	private static final int METHOD_netStoppedError9 = 9;
	private static final int METHOD_randomize10 = 10;
	private static final int METHOD_reset11 = 11;
	private static final int METHOD_revGet12 = 12;
	private static final int METHOD_revPut13 = 13;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public KohonenSynapseBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/engine/KohonenSynapse, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[7];
		try
		{
			properties[0] = new PropertyDescriptor("enabled", org/joone/engine/KohonenSynapse, "isEnabled", "setEnabled");
			properties[1] = new PropertyDescriptor("loopBack", org/joone/engine/KohonenSynapse, "isLoopBack", "setLoopBack");
			properties[2] = new PropertyDescriptor("monitor", org/joone/engine/KohonenSynapse, "getMonitor", "setMonitor");
			properties[2].setHidden(true);
			properties[3] = new PropertyDescriptor("name", org/joone/engine/KohonenSynapse, "getName", "setName");
			properties[4] = new PropertyDescriptor("orderingPhase", org/joone/engine/KohonenSynapse, "getOrderingPhase", "setOrderingPhase");
			properties[4].setDisplayName("ordering phase (epochs)");
			properties[5] = new PropertyDescriptor("timeConstant", org/joone/engine/KohonenSynapse, "getTimeConstant", "setTimeConstant");
			properties[6] = new PropertyDescriptor("weights", org/joone/engine/KohonenSynapse, "getWeights", "setWeights");
			properties[6].setHidden(true);
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
		MethodDescriptor methods[] = new MethodDescriptor[14];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/engine/KohonenSynapse.getMethod("addNoise", new Class[] {
				Double.TYPE
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/engine/KohonenSynapse.getMethod("canCountSteps", new Class[0]));
			methods[1].setDisplayName("");
			methods[2] = new MethodDescriptor(org/joone/engine/KohonenSynapse.getMethod("check", new Class[0]));
			methods[2].setDisplayName("");
			methods[3] = new MethodDescriptor(org/joone/engine/KohonenSynapse.getMethod("cicleTerminated", new Class[] {
				org/joone/engine/NeuralNetEvent
			}));
			methods[3].setDisplayName("");
			methods[4] = new MethodDescriptor(org/joone/engine/KohonenSynapse.getMethod("errorChanged", new Class[] {
				org/joone/engine/NeuralNetEvent
			}));
			methods[4].setDisplayName("");
			methods[5] = new MethodDescriptor(org/joone/engine/KohonenSynapse.getMethod("fwdGet", new Class[0]));
			methods[5].setDisplayName("");
			methods[6] = new MethodDescriptor(org/joone/engine/KohonenSynapse.getMethod("fwdPut", new Class[] {
				org/joone/engine/Pattern
			}));
			methods[6].setDisplayName("");
			methods[7] = new MethodDescriptor(org/joone/engine/KohonenSynapse.getMethod("netStarted", new Class[] {
				org/joone/engine/NeuralNetEvent
			}));
			methods[7].setDisplayName("");
			methods[8] = new MethodDescriptor(org/joone/engine/KohonenSynapse.getMethod("netStopped", new Class[] {
				org/joone/engine/NeuralNetEvent
			}));
			methods[8].setDisplayName("");
			methods[9] = new MethodDescriptor(org/joone/engine/KohonenSynapse.getMethod("netStoppedError", new Class[] {
				org/joone/engine/NeuralNetEvent, java/lang/String
			}));
			methods[9].setDisplayName("");
			methods[10] = new MethodDescriptor(org/joone/engine/KohonenSynapse.getMethod("randomize", new Class[] {
				Double.TYPE
			}));
			methods[10].setDisplayName("");
			methods[11] = new MethodDescriptor(org/joone/engine/KohonenSynapse.getMethod("reset", new Class[0]));
			methods[11].setDisplayName("");
			methods[12] = new MethodDescriptor(org/joone/engine/KohonenSynapse.getMethod("revGet", new Class[0]));
			methods[12].setDisplayName("");
			methods[13] = new MethodDescriptor(org/joone/engine/KohonenSynapse.getMethod("revPut", new Class[] {
				org/joone/engine/Pattern
			}));
			methods[13].setDisplayName("");
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
