// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MatrixBeanInfo.java

package org.joone.engine;

import java.beans.*;

// Referenced classes of package org.joone.engine:
//			Matrix

public class MatrixBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_enabled = 0;
	private static final int PROPERTY_fixed = 1;
	private static final int PROPERTY_m_cols = 2;
	private static final int PROPERTY_m_rows = 3;
	private static final int PROPERTY_value = 4;
	private static final int METHOD_addNoise0 = 0;
	private static final int METHOD_clear1 = 1;
	private static final int METHOD_clone2 = 2;
	private static final int METHOD_disableAll3 = 3;
	private static final int METHOD_enableAll4 = 4;
	private static final int METHOD_fixAll5 = 5;
	private static final int METHOD_randomize6 = 6;
	private static final int METHOD_unfixAll7 = 7;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public MatrixBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/engine/Matrix, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[5];
		try
		{
			properties[0] = new PropertyDescriptor("enabled", org/joone/engine/Matrix, "getEnabled", "setEnabled");
			properties[1] = new PropertyDescriptor("fixed", org/joone/engine/Matrix, "getFixed", "setFixed");
			properties[2] = new PropertyDescriptor("m_cols", org/joone/engine/Matrix, "getM_cols", "setM_cols");
			properties[3] = new PropertyDescriptor("m_rows", org/joone/engine/Matrix, "getM_rows", "setM_rows");
			properties[4] = new PropertyDescriptor("value", org/joone/engine/Matrix, "getValue", "setValue");
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
		MethodDescriptor methods[] = new MethodDescriptor[8];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/engine/Matrix.getMethod("addNoise", new Class[] {
				Double.TYPE
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/engine/Matrix.getMethod("clear", new Class[0]));
			methods[1].setDisplayName("");
			methods[2] = new MethodDescriptor(org/joone/engine/Matrix.getMethod("clone", new Class[0]));
			methods[2].setDisplayName("");
			methods[3] = new MethodDescriptor(org/joone/engine/Matrix.getMethod("disableAll", new Class[0]));
			methods[3].setDisplayName("");
			methods[4] = new MethodDescriptor(org/joone/engine/Matrix.getMethod("enableAll", new Class[0]));
			methods[4].setDisplayName("");
			methods[5] = new MethodDescriptor(org/joone/engine/Matrix.getMethod("fixAll", new Class[0]));
			methods[5].setDisplayName("");
			methods[6] = new MethodDescriptor(org/joone/engine/Matrix.getMethod("randomize", new Class[] {
				Double.TYPE, Double.TYPE
			}));
			methods[6].setDisplayName("");
			methods[7] = new MethodDescriptor(org/joone/engine/Matrix.getMethod("unfixAll", new Class[0]));
			methods[7].setDisplayName("");
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
