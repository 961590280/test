// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DelegationSelectionTool.java

package org.joone.edit;

import CH.ifa.draw.contrib.CustomSelectionTool;
import CH.ifa.draw.figures.TextFigure;
import CH.ifa.draw.figures.TextTool;
import CH.ifa.draw.framework.*;
import java.awt.Component;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;

// Referenced classes of package org.joone.edit:
//			ConcreteGenericFigure, LayerConnection, PropertySheet

public class DelegationSelectionTool extends CustomSelectionTool
{

	private TextTool myTextTool;
	private PropertySheet myPsp;

	public DelegationSelectionTool(DrawingView view)
	{
		super(view);
		setTextTool(new TextTool(view, new TextFigure()));
	}

	protected void handleMouseDoubleClick(MouseEvent e, int x, int y)
	{
		Figure figure = drawing().findFigureInside(e.getX(), e.getY());
		if (figure != null && (figure instanceof TextFigure))
		{
			TextFigure txtFig = (TextFigure)figure;
			if (!txtFig.readOnly())
			{
				getTextTool().activate();
				getTextTool().mouseDown(e, x, y);
			}
		}
	}

	public void setPropertyPanel(PropertySheet psp)
	{
		myPsp = psp;
	}

	public PropertySheet getPropertyPanel()
	{
		return myPsp;
	}

	protected void handleMouseClick(MouseEvent e, int x, int y)
	{
		deactivate();
	}

	public void deactivate()
	{
		super.deactivate();
		if (getTextTool().isActivated())
			getTextTool().deactivate();
	}

	protected void setTextTool(TextTool newTextTool)
	{
		myTextTool = newTextTool;
	}

	protected TextTool getTextTool()
	{
		return myTextTool;
	}

	protected void showPopupMenu(Figure figure, int x, int y, Component comp)
	{
		deactivate();
		Figure figure2 = drawing().findFigure(x, y);
		if (figure2 != null)
		{
			if (figure2 instanceof ConcreteGenericFigure)
				((ConcreteGenericFigure)figure2).getPopupMenu(myPsp, view()).show(comp, x, y);
			if (figure2 instanceof LayerConnection)
			{
				JPopupMenu pop = ((LayerConnection)figure2).getPopupMenu(myPsp, view());
				if (pop != null)
					pop.show(comp, x, y);
			}
		}
	}
}
