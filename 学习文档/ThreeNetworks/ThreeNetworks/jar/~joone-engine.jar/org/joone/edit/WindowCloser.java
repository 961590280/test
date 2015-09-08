// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WindowCloser.java

package org.joone.edit;

import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowCloser
	implements WindowListener
{

	private boolean exitOnClose;

	public WindowCloser(Window w)
	{
		this(w, false);
	}

	public WindowCloser(Window w, boolean exitOnClose)
	{
		this.exitOnClose = exitOnClose;
		w.addWindowListener(this);
	}

	public void windowOpened(WindowEvent windowevent)
	{
	}

	public void windowClosing(WindowEvent e)
	{
		if (exitOnClose)
			System.exit(0);
		e.getWindow().dispose();
	}

	public void windowClosed(WindowEvent e)
	{
		if (exitOnClose)
			System.exit(0);
	}

	public void windowIconified(WindowEvent windowevent)
	{
	}

	public void windowDeiconified(WindowEvent windowevent)
	{
	}

	public void windowActivated(WindowEvent windowevent)
	{
	}

	public void windowDeactivated(WindowEvent windowevent)
	{
	}
}
