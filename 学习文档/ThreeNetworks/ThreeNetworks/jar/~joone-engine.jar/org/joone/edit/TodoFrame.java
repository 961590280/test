// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TodoFrame.java

package org.joone.edit;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.TreeSet;
import javax.swing.*;
import org.joone.net.*;

// Referenced classes of package org.joone.edit:
//			JoonEdit, NeuralNetDrawing

public class TodoFrame extends JFrame
{

	private JoonEdit owner;
	private JPanel centerPanel;

	public TodoFrame(Frame ownerArg)
	{
		owner = null;
		centerPanel = new JPanel();
		owner = (JoonEdit)ownerArg;
		setTitle("To Do List");
		setResizable(false);
		JRootPane rp = getRootPane();
		rp.setLayout(new BorderLayout());
		centerPanel.setLayout(new GridLayout(0, 1));
		rp.add(centerPanel, "Center");
		Button b = new Button("Revalidate");
		b.addActionListener(new ActionListener() {

			final TodoFrame this$0;

			public void actionPerformed(ActionEvent e)
			{
				check();
				pack();
				repaint();
			}

			
			{
				this$0 = TodoFrame.this;
				super();
			}
		});
		JPanel jp2 = new JPanel();
		jp2.add(b);
		rp.add(jp2, "South");
	}

	public void show()
	{
		check();
		pack();
		setIconImage(owner.getIconImage());
		setLocation((owner.getWidth() - owner.getX() - getWidth()) / 2, (owner.getHeight() - owner.getY() - getHeight()) / 2);
		super.show();
	}

	private void check()
	{
		NeuralNetDrawing nnd = (NeuralNetDrawing)owner.drawing();
		NeuralNet nn = nnd.getNeuralNet();
		NetChecker nc = new NetChecker(nn);
		TreeSet checks = nc.check();
		centerPanel.removeAll();
		if (checks.isEmpty())
		{
			centerPanel.add(new JLabel("Network checks out okay."));
		} else
		{
			NetCheck netCheck;
			for (Iterator iter = checks.iterator(); iter.hasNext(); centerPanel.add(new JLabel(netCheck.toString())))
				netCheck = (NetCheck)iter.next();

		}
	}

}
