// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SnapshotRecorderBeanInfo.java

package org.joone.util;

import java.beans.*;
import org.joone.edit.JooneFileChooserEditor;
import org.joone.engine.NeuralNetEvent;

// Referenced classes of package org.joone.util:
//			SnapshotRecorder, SnapshotFormatEditor

public class SnapshotRecorderBeanInfo extends SimpleBeanInfo
{

	private static final int PROPERTY_name = 0;
	private static final int PROPERTY_rate = 1;
	private static final int PROPERTY_filename = 2;
	private static final int PROPERTY_format = 3;
	private static final int METHOD_netStarted0 = 0;
	private static final int METHOD_cicleTerminated1 = 1;
	private static final int METHOD_netStopped2 = 2;
	private static final int defaultPropertyIndex = -1;
	private static final int defaultEventIndex = -1;

	public SnapshotRecorderBeanInfo()
	{
	}

	private static BeanDescriptor getBdescriptor()
	{
		BeanDescriptor beanDescriptor = new BeanDescriptor(org/joone/util/SnapshotRecorder, null);
		return beanDescriptor;
	}

	private static PropertyDescriptor[] getPdescriptor()
	{
		PropertyDescriptor properties[] = new PropertyDescriptor[4];
		try
		{
			properties[0] = new PropertyDescriptor("name", org/joone/util/SnapshotRecorder, "getName", "setName");
			properties[1] = new PropertyDescriptor("rate", org/joone/util/SnapshotRecorder, "getRate", "setRate");
			properties[2] = new PropertyDescriptor("filename", org/joone/util/SnapshotRecorder, "getFilename", "setFilename");
			properties[2].setPropertyEditorClass(org/joone/edit/JooneFileChooserEditor);
			properties[3] = new PropertyDescriptor("format", org/joone/util/SnapshotRecorder, "getFormat", "setFormat");
			properties[3].setPropertyEditorClass(org/joone/util/SnapshotFormatEditor);
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
		MethodDescriptor methods[] = new MethodDescriptor[3];
		try
		{
			methods[0] = new MethodDescriptor(org/joone/util/SnapshotRecorder.getMethod("netStarted", new Class[] {
				org/joone/engine/NeuralNetEvent
			}));
			methods[0].setDisplayName("");
			methods[1] = new MethodDescriptor(org/joone/util/SnapshotRecorder.getMethod("cicleTerminated", new Class[] {
				org/joone/engine/NeuralNetEvent
			}));
			methods[1].setDisplayName("");
			methods[2] = new MethodDescriptor(org/joone/util/SnapshotRecorder.getMethod("netStopped", new Class[] {
				org/joone/engine/NeuralNetEvent
			}));
			methods[2].setDisplayName("");
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
