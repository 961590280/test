// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertyText.java

package org.joone.edit;

import java.awt.event.*;
import java.beans.PropertyEditor;
import javax.swing.JTextField;

class PropertyText extends JTextField
	implements KeyListener, FocusListener
{

	private PropertyEditor editor;
	private static final long serialVersionUID = 0xc227e8308b86c914L;

	public PropertyText(String text)
	{
		super(text);
		addKeyListener(this);
		addFocusListener(this);
	}

	public void repaint()
	{
		super.repaint();
	}

	protected void updateEditor()
	{
		try
		{
			editor.setAsText(getText());
		}
		catch (IllegalArgumentException illegalargumentexception) { }
	}

	public void focusGained(FocusEvent focusevent)
	{
	}

	public void focusLost(FocusEvent e)
	{
		updateEditor();
	}

	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == 10)
			updateEditor();
	}

	public void keyPressed(KeyEvent keyevent)
	{
	}

	public void keyTyped(KeyEvent keyevent)
	{
	}

	public PropertyEditor getEditor()
	{
		return editor;
	}

	public void setEditor(PropertyEditor editor)
	{
		this.editor = editor;
	}
}
