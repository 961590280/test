// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   VisADOutputSynapseBeanInfo.java

package org.joone.edit.visad;

import java.beans.*;

// Referenced classes of package org.joone.edit.visad:
//			VisADOutputSynapse

public class VisADOutputSynapseBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_enabled = 0;
	private static final int PROPERTY_maxXaxis = 1;
	private static final int PROPERTY_maxYaxis = 2;
	private static final int PROPERTY_name = 3;
	private static final int PROPERTY_resizable = 4;
	private static final int PROPERTY_serie = 5;
	private static final int PROPERTY_show = 6;
	private static final int PROPERTY_title = 7;
	private static final int METHOD_revGet0 = 0;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public VisADOutputSynapseBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/edit/visad/VisADOutputSynapse, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[8];
		try
		{
			properties[0] = new PropertyDescriptor("enabled", org/joone/edit/visad/VisADOutputSynapse, "isEnabled", "setEnabled");
			properties[1] = new PropertyDescriptor("maxXaxis", org/joone/edit/visad/VisADOutputSynapse, "getMaxXaxis", "setMaxXaxis");
			properties[1].setExpert(true);
			properties[2] = new PropertyDescriptor("maxYaxis", org/joone/edit/visad/VisADOutputSynapse, "getMaxYaxis", "setMaxYaxis");
			properties[2].setExpert(true);
			properties[3] = new PropertyDescriptor("name", org/joone/edit/visad/VisADOutputSynapse, "getName", "setName");
			properties[4] = new PropertyDescriptor("resizable", org/joone/edit/visad/VisADOutputSynapse, "isResizable", "setResizable");
			properties[5] = new PropertyDescriptor("serie", org/joone/edit/visad/VisADOutputSynapse, "getSerie", "setSerie");
			properties[6] = new PropertyDescriptor("show", org/joone/edit/visad/VisADOutputSynapse, "isShow", "setShow");
			properties[7] = new PropertyDescriptor("title", org/joone/edit/visad/VisADOutputSynapse, "getTitle", "setTitle");
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
		MethodDescriptor methods[] = new MethodDescriptor[1];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/edit/visad/VisADOutputSynapse.getMethod("revGet", new Class[0]));
			methods[0].setDisplayName("");
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
