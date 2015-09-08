// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ChartOutputSynapseBeanInfo.java

package org.joone.edit;

import java.beans.*;
import org.joone.engine.Pattern;

// Referenced classes of package org.joone.edit:
//			ChartOutputSynapse

public class ChartOutputSynapseBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_title = 0;
	private static final int PROPERTY_serie = 1;
	private static final int PROPERTY_maxYaxis = 2;
	private static final int PROPERTY_resizable = 3;
	private static final int PROPERTY_enabled = 4;
	private static final int PROPERTY_maxXaxis = 5;
	private static final int PROPERTY_show = 6;
	private static final int PROPERTY_name = 7;
	private static final int METHOD_fwdPut0 = 0;
	private static final int METHOD_revGet1 = 1;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public ChartOutputSynapseBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/edit/ChartOutputSynapse, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[8];
		try
		{
			properties[0] = new PropertyDescriptor("title", org/joone/edit/ChartOutputSynapse, "getTitle", "setTitle");
			properties[1] = new PropertyDescriptor("serie", org/joone/edit/ChartOutputSynapse, "getSerie", "setSerie");
			properties[2] = new PropertyDescriptor("maxYaxis", org/joone/edit/ChartOutputSynapse, "getMaxYaxis", "setMaxYaxis");
			properties[3] = new PropertyDescriptor("resizable", org/joone/edit/ChartOutputSynapse, "isResizable", "setResizable");
			properties[4] = new PropertyDescriptor("enabled", org/joone/edit/ChartOutputSynapse, "isEnabled", "setEnabled");
			properties[5] = new PropertyDescriptor("maxXaxis", org/joone/edit/ChartOutputSynapse, "getMaxXaxis", "setMaxXaxis");
			properties[6] = new PropertyDescriptor("show", org/joone/edit/ChartOutputSynapse, "isShow", "setShow");
			properties[7] = new PropertyDescriptor("name", org/joone/edit/ChartOutputSynapse, "getName", "setName");
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
		MethodDescriptor methods[] = new MethodDescriptor[2];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/edit/ChartOutputSynapse.getMethod("fwdPut", new Class[] {
				org/joone/engine/Pattern
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/edit/ChartOutputSynapse.getMethod("revGet", new Class[0]));
			methods[1].setDisplayName("");
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
