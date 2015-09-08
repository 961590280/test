// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertyNameDialog.java

package org.joone.edit;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Vector;

// Referenced classes of package org.joone.edit:
//			WindowCloser, ErrorDialog

public class PropertyNameDialog extends Dialog
	implements ActionListener
{

	private Button okButton;
	private Button cancelButton;
	private java.awt.List list;
	private Vector properties;
	private PropertyDescriptor result;
	private static final long serialVersionUID = 0x691f9502f0d117a7L;

	PropertyNameDialog(Frame frame, Object source, String message, Class match, boolean readable)
	{
		super(frame, "PropertyNameDialog", true);
		new WindowCloser(this);
		setLayout(null);
		PropertyDescriptor allProperties[];
		try
		{
			allProperties = Introspector.getBeanInfo(source.getClass()).getPropertyDescriptors();
			if (allProperties == null)
			{
				new ErrorDialog(frame, "PropertyNameDialog : couldn't find PropertyDescriptors");
				return;
			}
		}
		catch (Exception ex)
		{
			new ErrorDialog(frame, (new StringBuilder("PropertyNameDialog: Unexpected exception: \n")).append(ex).toString());
			return;
		}
		properties = new Vector();
		for (int i = 0; i < allProperties.length; i++)
		{
			PropertyDescriptor pd = allProperties[i];
			if ((!readable || pd.isBound()) && (!readable || pd.getReadMethod() != null) && (readable || pd.getWriteMethod() != null) && (match == null || isSubclass(match, pd.getPropertyType())))
				properties.addElement(pd);
		}

		int width = 300;
		if (properties.isEmpty())
		{
			new ErrorDialog(frame, "No suitable properties");
			return;
		}
		int height = 200;
		Label l = new Label(message, 1);
		l.setBounds(2, 30, width - 4, 25);
		add(l);
		list = new java.awt.List(8, false);
		for (int i = 0; i < properties.size(); i++)
		{
			PropertyDescriptor pd = (PropertyDescriptor)properties.elementAt(i);
			list.add(pd.getDisplayName());
		}

		list.select(0);
		list.setBounds(10, 60, width - 20, height - 60);
		add(list);
		height += 10;
		cancelButton = new Button("Cancel");
		cancelButton.addActionListener(this);
		add(cancelButton);
		cancelButton.setBounds(width / 2 - 70, height - 5, 60, 30);
		okButton = new Button("OK");
		okButton.addActionListener(this);
		add(okButton);
		okButton.setBounds(width / 2 + 10, height - 5, 60, 30);
		height += 55;
		list.setBounds(10, 60, width - 20, height - 130);
		int x = frame.getLocation().x + 30;
		int y = frame.getLocation().y + 50;
		setBounds(x, y, width, height);
		show();
	}

	static boolean isSubclass(Class a, Class b)
	{
		if (a == b)
			return true;
		if (a == null || b == null)
			return false;
		for (Class x = a; x != null; x = x.getSuperclass())
		{
			if (x == b)
				return true;
			if (b.isInterface())
			{
				Class interfaces[] = x.getInterfaces();
				for (int i = 0; i < interfaces.length; i++)
					if (interfaces[i] == b)
						return true;

			}
		}

		return false;
	}

	public PropertyDescriptor getResult()
	{
		return result;
	}

	public void actionPerformed(ActionEvent evt)
	{
		if (evt.getSource() == okButton && list != null)
		{
			int index = list.getSelectedIndex();
			if (index >= 0)
				result = (PropertyDescriptor)properties.elementAt(index);
		}
		dispose();
	}
}
