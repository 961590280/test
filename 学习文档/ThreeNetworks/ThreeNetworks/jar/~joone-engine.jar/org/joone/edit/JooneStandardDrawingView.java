// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JooneStandardDrawingView.java

package org.joone.edit;

import CH.ifa.draw.framework.DrawingEditor;
import CH.ifa.draw.standard.StandardDrawingView;

public class JooneStandardDrawingView extends StandardDrawingView
{

	private boolean modified;

	public JooneStandardDrawingView(DrawingEditor editor, int width, int height)
	{
		super(editor, width, height);
		modified = false;
	}

	public void repairDamage()
	{
		super.repairDamage();
		modified = true;
	}

	public boolean isModified()
	{
		return modified;
	}

	public void setModified(boolean mod)
	{
		modified = mod;
	}
}
