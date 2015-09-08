// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MessageDialog.java

package org.joone.edit;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Referenced classes of package org.joone.edit:
//			WindowCloser

public class MessageDialog extends Dialog
	implements ActionListener
{

	private static final long serialVersionUID = 0xb69d1bbf80692e8bL;

	public MessageDialog(Frame frame, String title, String message)
	{
		this(frame, title, message, false);
	}

	public MessageDialog(Frame frame, String title, String message, boolean leftIndented)
	{
		super(frame, title, true);
		new WindowCloser(this);
		GridBagLayout gridBag = new GridBagLayout();
		setLayout(gridBag);
		GridBagConstraints cons = new GridBagConstraints();
		cons.gridwidth = 0;
		if (leftIndented)
			cons.anchor = 17;
		int height;
		for (height = 5; message.length() > 0; height += 20)
		{
			int ix = message.indexOf('\n');
			String line;
			if (ix >= 0)
			{
				line = message.substring(0, ix);
				message = message.substring(ix + 1);
			} else
			{
				line = message;
				message = "";
			}
			Label l = new Label(line);
			gridBag.setConstraints(l, cons);
			add(l);
		}

		cons.anchor = 10;
		Button b = new Button("Continue");
		b.addActionListener(this);
		gridBag.setConstraints(b, cons);
		add(b);
		height += 25;
		height += 35;
		int x = frame.getLocation().x + 30;
		int y = frame.getLocation().y + 100;
		setBounds(x, y, 400, height + 5);
		show();
	}

	public void actionPerformed(ActionEvent evt)
	{
		dispose();
	}
}
