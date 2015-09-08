// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertyDialog.java

package org.joone.edit;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyEditor;

// Referenced classes of package org.joone.edit:
//			WindowCloser

class PropertyDialog extends Dialog
	implements ActionListener
{

	private Button doneButton;
	private Component body;
	private static final int vPad = 5;
	private static final int hPad = 4;
	private static final long serialVersionUID = 0xbb0ef652b33cca8aL;

	PropertyDialog(Frame frame, PropertyEditor pe, int x, int y)
	{
		super(frame, pe.getClass().getName(), true);
		new WindowCloser(this);
		setLayout(null);
		body = pe.getCustomEditor();
		add(body);
		doneButton = new Button("Done");
		doneButton.addActionListener(this);
		add(doneButton);
		setLocation(x, y);
		show();
	}

	public void actionPerformed(ActionEvent evt)
	{
		dispose();
	}

	public void doLayout()
	{
		Insets ins = getInsets();
		Dimension bodySize = body.getPreferredSize();
		Dimension buttonSize = doneButton.getPreferredSize();
		int width = ins.left + 8 + ins.right + bodySize.width;
		int height = ins.top + 15 + ins.bottom + bodySize.height + buttonSize.height;
		body.setBounds(ins.left + 4, ins.top + 5, bodySize.width, bodySize.height);
		doneButton.setBounds((width - buttonSize.width) / 2, ins.top + 8 + bodySize.height, buttonSize.width, buttonSize.height);
		setSize(width, height);
	}
}
