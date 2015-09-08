// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertySelector.java

package org.joone.edit;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyEditor;
import javax.swing.JComboBox;

class PropertySelector extends JComboBox
	implements ItemListener
{

	PropertyEditor editor;
	private static final long serialVersionUID = 0x82046f3d1b2f6c13L;

	PropertySelector(PropertyEditor pe)
	{
		editor = pe;
		String tags[] = editor.getTags();
		for (int i = 0; i < tags.length; i++)
			addItem(tags[i]);

		setSelectedIndex(0);
		setSelectedItem(editor.getAsText());
		addItemListener(this);
	}

	public void itemStateChanged(ItemEvent evt)
	{
		String s = (String)getSelectedItem();
		editor.setAsText(s);
	}

	public void repaint()
	{
		super.repaint();
	}
}
