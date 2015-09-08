// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ImageInputSynapseBeanInfo.java

package org.joone.io;

import java.beans.*;
import org.joone.engine.Pattern;

// Referenced classes of package org.joone.io:
//			ImageInputSynapse

public class ImageInputSynapseBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_buffered = 0;
	private static final int PROPERTY_enabled = 1;
	private static final int PROPERTY_firstRow = 2;
	private static final int PROPERTY_lastRow = 3;
	private static final int PROPERTY_maxBufSize = 4;
	private static final int PROPERTY_name = 5;
	private static final int PROPERTY_plugIn = 6;
	private static final int PROPERTY_stepCounter = 7;
	private static final int PROPERTY_fileFilter = 8;
	private static final int PROPERTY_desiredWidth = 9;
	private static final int PROPERTY_desiredHeight = 10;
	private static final int PROPERTY_imageInput = 11;
	private static final int PROPERTY_imageDirectory = 12;
	private static final int PROPERTY_colourMode = 13;
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

	public ImageInputSynapseBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/io/ImageInputSynapse, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[14];
		try
		{
			properties[0] = new PropertyDescriptor("buffered", org/joone/io/ImageInputSynapse, "isBuffered", "setBuffered");
			properties[1] = new PropertyDescriptor("enabled", org/joone/io/ImageInputSynapse, "isEnabled", "setEnabled");
			properties[2] = new PropertyDescriptor("firstRow", org/joone/io/ImageInputSynapse, "getFirstRow", "setFirstRow");
			properties[3] = new PropertyDescriptor("lastRow", org/joone/io/ImageInputSynapse, "getLastRow", "setLastRow");
			properties[4] = new PropertyDescriptor("maxBufSize", org/joone/io/ImageInputSynapse, "getMaxBufSize", "setMaxBufSize");
			properties[5] = new PropertyDescriptor("name", org/joone/io/ImageInputSynapse, "getName", "setName");
			properties[6] = new PropertyDescriptor("plugIn", org/joone/io/ImageInputSynapse, "getPlugIn", "setPlugIn");
			properties[6].setExpert(true);
			properties[7] = new PropertyDescriptor("stepCounter", org/joone/io/ImageInputSynapse, "isStepCounter", "setStepCounter");
			properties[8] = new PropertyDescriptor("fileFilter", org/joone/io/ImageInputSynapse, "getFileFilter", "setFileFilter");
			properties[9] = new PropertyDescriptor("scaleToWidth", org/joone/io/ImageInputSynapse, "getDesiredWidth", "setDesiredWidth");
			properties[10] = new PropertyDescriptor("scaleToHeight", org/joone/io/ImageInputSynapse, "getDesiredHeight", "setDesiredHeight");
			properties[11] = new PropertyDescriptor("imageInput", org/joone/io/ImageInputSynapse, null, "setImageInput");
			properties[11].setExpert(true);
			properties[12] = new PropertyDescriptor("imageDirectory", org/joone/io/ImageInputSynapse, "getImageDirectory", "setImageDirectory");
			properties[13] = new PropertyDescriptor("colourMode", org/joone/io/ImageInputSynapse, "getColourMode", "setColourMode");
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
		MethodDescriptor methods[] = new MethodDescriptor[15];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/io/ImageInputSynapse.getMethod("addNoise", new Class[] {
				Double.TYPE
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/io/ImageInputSynapse.getMethod("canCountSteps", new Class[0]));
			methods[1].setDisplayName("");
			methods[2] = new MethodDescriptor(org/joone/io/ImageInputSynapse.getMethod("check", new Class[0]));
			methods[2].setDisplayName("");
			methods[3] = new MethodDescriptor(org/joone/io/ImageInputSynapse.getMethod("fwdGet", new Class[0]));
			methods[3].setDisplayName("");
			methods[4] = new MethodDescriptor(org/joone/io/ImageInputSynapse.getMethod("fwdPut", new Class[] {
				org/joone/engine/Pattern
			}));
			methods[4].setDisplayName("");
			methods[5] = new MethodDescriptor(org/joone/io/ImageInputSynapse.getMethod("gotoFirstLine", new Class[0]));
			methods[5].setDisplayName("");
			methods[6] = new MethodDescriptor(org/joone/io/ImageInputSynapse.getMethod("gotoLine", new Class[] {
				Integer.TYPE
			}));
			methods[6].setDisplayName("");
			methods[7] = new MethodDescriptor(org/joone/io/ImageInputSynapse.getMethod("initLearner", new Class[0]));
			methods[7].setDisplayName("");
			methods[8] = new MethodDescriptor(org/joone/io/ImageInputSynapse.getMethod("numColumns", new Class[0]));
			methods[8].setDisplayName("");
			methods[9] = new MethodDescriptor(org/joone/io/ImageInputSynapse.getMethod("randomize", new Class[] {
				Double.TYPE
			}));
			methods[9].setDisplayName("");
			methods[10] = new MethodDescriptor(org/joone/io/ImageInputSynapse.getMethod("readAll", new Class[0]));
			methods[10].setDisplayName("");
			methods[11] = new MethodDescriptor(org/joone/io/ImageInputSynapse.getMethod("reset", new Class[0]));
			methods[11].setDisplayName("");
			methods[12] = new MethodDescriptor(org/joone/io/ImageInputSynapse.getMethod("resetInput", new Class[0]));
			methods[12].setDisplayName("");
			methods[13] = new MethodDescriptor(org/joone/io/ImageInputSynapse.getMethod("revGet", new Class[0]));
			methods[13].setDisplayName("");
			methods[14] = new MethodDescriptor(org/joone/io/ImageInputSynapse.getMethod("revPut", new Class[] {
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
