// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertyPanel.java

package org.joone.edit;

import com.l2fprod.common.propertysheet.Property;
import com.l2fprod.common.propertysheet.PropertySheetPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.*;
import java.util.ArrayList;
import javax.swing.*;

// Referenced classes of package org.joone.edit:
//			Wrapper

public class PropertyPanel extends JPanel
{

	final PropertySheetPanel sheet = new PropertySheetPanel();
	Wrapper source;

	public PropertyPanel()
	{
		setLayout(new BorderLayout());
		sheet.setMode(0);
		sheet.setToolBarVisible(false);
		sheet.setDescriptionVisible(false);
		sheet.setPreferredSize(new Dimension(60, 170));
		JScrollPane scroll = new JScrollPane(sheet);
		add(scroll, "Center");
	}

	public void setTarget(Wrapper wrapper)
	{
		source = wrapper;
		Object target = wrapper.getBean();
		PropertyChangeListener listener = (PropertyChangeListener)sheet.getClientProperty("listener");
		if (listener != null)
			sheet.removePropertySheetChangeListener(listener);
		try
		{
			BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());
			sheet.setMode(0);
			PropertyDescriptor props[] = beanInfo.getPropertyDescriptors();
			sheet.setProperties(getFilteredProperties(props));
			Property properties[] = sheet.getProperties();
			int i = 0;
			for (int c = properties.length; i < c; i++)
				try
				{
					properties[i].readFromObject(target);
				}
				catch (Exception exception) { }

			setListener(target, sheet);
		}
		catch (IntrospectionException ex)
		{
			ex.printStackTrace();
		}
	}

	public void update()
	{
		source.updateFigure();
	}

	private PropertyDescriptor[] getFilteredProperties(PropertyDescriptor origin[])
	{
		ArrayList props = new ArrayList();
		for (int i = 0; i < origin.length; i++)
		{
			PropertyDescriptor prop = origin[i];
			java.lang.reflect.Method getter = prop.getReadMethod();
			java.lang.reflect.Method setter = prop.getWriteMethod();
			if (!prop.isHidden() && !prop.isExpert() && setter != null && getter != null)
				props.add(prop);
		}

		return (PropertyDescriptor[])props.toArray(new PropertyDescriptor[props.size()]);
	}

	private void setListener(final Object theObject, PropertySheetPanel sheet)
	{
		PropertyChangeListener listener = new PropertyChangeListener() {

			final PropertyPanel this$0;
			private final Object val$theObject;

			public void propertyChange(PropertyChangeEvent evt)
			{
				Property prop = (Property)evt.getSource();
				prop.writeToObject(theObject);
				if (theObject instanceof JComponent)
					((JComponent)theObject).repaint();
				source.updateFigure();
			}

			
			{
				this$0 = PropertyPanel.this;
				theObject = obj;
				super();
			}
		};
		sheet.addPropertySheetChangeListener(listener);
		sheet.putClientProperty("listener", listener);
		sheet.repaint();
	}
}
