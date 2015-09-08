// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JooneCopyCommand.java

package org.joone.edit;

import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.FigureEnumeration;
import CH.ifa.draw.standard.CopyCommand;
import java.util.NoSuchElementException;

// Referenced classes of package org.joone.edit:
//			ConcreteGenericFigure

public class JooneCopyCommand extends CopyCommand
{

	public JooneCopyCommand(String name, DrawingView view)
	{
		super(name, view);
	}

	public boolean isExecutable()
	{
		if (fView.selectionCount() > 0)
		{
			FigureEnumeration fe = fView.selectionElements();
			CH.ifa.draw.framework.Figure f;
			try
			{
				do
					f = fe.nextFigure();
				while (!(f instanceof ConcreteGenericFigure));
			}
			catch (NoSuchElementException nosuchelementexception)
			{
				return true;
			}
			return false;
		} else
		{
			return false;
		}
	}
}
