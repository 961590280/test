// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ImageOutputSynapseBeanInfo.java

package org.joone.io;

import java.beans.*;

// Referenced classes of package org.joone.io:
//			ImageOutputSynapse

public class ImageOutputSynapseBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_buffered = 0;
	private static final int PROPERTY_colourMode = 1;
	private static final int PROPERTY_enabled = 2;
	private static final int PROPERTY_height = 3;
	private static final int PROPERTY_imageFileType = 4;
	private static final int PROPERTY_monitor = 5;
	private static final int PROPERTY_name = 6;
	private static final int PROPERTY_outputDirectory = 7;
	private static final int PROPERTY_width = 8;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public ImageOutputSynapseBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/io/ImageOutputSynapse, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[9];
		try
		{
			properties[0] = new PropertyDescriptor("buffered", org/joone/io/ImageOutputSynapse, "isBuffered", "setBuffered");
			properties[1] = new PropertyDescriptor("colourMode", org/joone/io/ImageOutputSynapse, "getColourMode", "setColourMode");
			properties[2] = new PropertyDescriptor("enabled", org/joone/io/ImageOutputSynapse, "isEnabled", "setEnabled");
			properties[3] = new PropertyDescriptor("height", org/joone/io/ImageOutputSynapse, "getHeight", "setHeight");
			properties[4] = new PropertyDescriptor("imageFileType", org/joone/io/ImageOutputSynapse, "getImageFileType", "setImageFileType");
			properties[5] = new PropertyDescriptor("monitor", org/joone/io/ImageOutputSynapse, "getMonitor", "setMonitor");
			properties[5].setExpert(true);
			properties[6] = new PropertyDescriptor("name", org/joone/io/ImageOutputSynapse, "getName", "setName");
			properties[7] = new PropertyDescriptor("outputDirectory", org/joone/io/ImageOutputSynapse, "getOutputDirectory", "setOutputDirectory");
			properties[8] = new PropertyDescriptor("width", org/joone/io/ImageOutputSynapse, "getWidth", "setWidth");
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
