// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpecialConnectionHandle.java

package org.joone.edit;

import CH.ifa.draw.framework.*;
import CH.ifa.draw.standard.ConnectionHandle;
import java.awt.*;

public class SpecialConnectionHandle extends ConnectionHandle
{

	private Color ovalColor;

	public SpecialConnectionHandle(Figure owner, Locator l, ConnectionFigure prototype, Color color)
	{
		super(owner, l, prototype);
		ovalColor = color;
	}

	public void draw(Graphics g)
	{
		Rectangle r = displayBox();
		g.setColor(ovalColor);
		g.drawRect(r.x, r.y, r.width, r.height);
	}
}
