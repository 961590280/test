// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertyCanvas.java

package org.joone.edit;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyEditor;
import javax.swing.JFrame;
import javax.swing.JPanel;

// Referenced classes of package org.joone.edit:
//			JooneFileChooserEditor, JooneFileChooser, PropertyDialog

class PropertyCanvas extends JPanel
	implements MouseListener
{

	private static boolean ignoreClick = false;
	private JFrame frame;
	private PropertyEditor editor;
	private static final long serialVersionUID = 0xadd589ef9ff278acL;

	PropertyCanvas(JFrame frame, PropertyEditor pe)
	{
		this.frame = frame;
		editor = pe;
		addMouseListener(this);
	}

	public void paint(Graphics g)
	{
		Rectangle box = new Rectangle(2, 2, getSize().width - 4, getSize().height - 4);
		editor.paintValue(g, box);
	}

	public void mouseClicked(MouseEvent evt)
	{
		if (ignoreClick)
			break MISSING_BLOCK_LABEL_104;
		ignoreClick = true;
		int x = frame.getLocation().x - 30;
		int y = frame.getLocation().y + 50;
		if (editor instanceof JooneFileChooserEditor)
		{
			JooneFileChooser fileChooser = (JooneFileChooser)editor.getCustomEditor();
			fileChooser.showOpenDialog(this);
		} else
		{
			new PropertyDialog(frame, editor, x, y);
		}
		break MISSING_BLOCK_LABEL_100;
		Exception exception;
		exception;
		ignoreClick = false;
		throw exception;
		ignoreClick = false;
	}

	public void mousePressed(MouseEvent mouseevent)
	{
	}

	public void mouseReleased(MouseEvent mouseevent)
	{
	}

	public void mouseEntered(MouseEvent mouseevent)
	{
	}

	public void mouseExited(MouseEvent mouseevent)
	{
	}

}
