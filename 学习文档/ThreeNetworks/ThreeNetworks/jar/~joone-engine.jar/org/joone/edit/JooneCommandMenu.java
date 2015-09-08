// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JooneCommandMenu.java

package org.joone.edit;

import CH.ifa.draw.util.Command;
import CH.ifa.draw.util.CommandMenu;
import java.awt.Component;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.util.Vector;
import javax.swing.*;

public class JooneCommandMenu extends CommandMenu
{

	private Vector fCommands;

	public JooneCommandMenu(String name)
	{
		super(name);
		fCommands = new Vector(10);
	}

	public synchronized void add(Command command)
	{
		JMenuItem m = new JMenuItem(command.name());
		m.addActionListener(this);
		add(m);
		fCommands.addElement(command);
	}

	public synchronized void add(Command command, MenuShortcut shortcut)
	{
		JMenuItem m = new JMenuItem(command.name(), shortcut.getKey());
		m.setName(command.name());
		m.addActionListener(this);
		add(m);
		fCommands.addElement(command);
	}

	public synchronized void add(Command command, MenuShortcut shortcut, KeyStroke keyStroke)
	{
		JMenuItem m = new JMenuItem(command.name(), shortcut.getKey());
		m.setName(command.name());
		m.addActionListener(this);
		m.setAccelerator(keyStroke);
		add(m);
		fCommands.addElement(command);
	}

	public synchronized void checkEnabled()
	{
		int j = 0;
		for (int i = 0; i < getMenuComponentCount(); i++)
			if (!(getMenuComponent(i) instanceof JSeparator))
			{
				Command cmd = (Command)fCommands.elementAt(j);
				getMenuComponent(i).setEnabled(cmd.isExecutable());
				j++;
			}

	}

	public void actionPerformed(ActionEvent e)
	{
		int j = 0;
		Object source = e.getSource();
		for (int i = 0; i < getItemCount(); i++)
		{
			JMenuItem item = getItem(i);
			if (getMenuComponent(i) instanceof JSeparator)
				continue;
			if (source == item)
			{
				Command cmd = (Command)fCommands.elementAt(j);
				cmd.execute();
				break;
			}
			j++;
		}

	}
}
