// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ValidationLayerConnection.java

package org.joone.edit;

import CH.ifa.draw.figures.ArrowTip;
import CH.ifa.draw.framework.Figure;
import java.awt.Color;

// Referenced classes of package org.joone.edit:
//			LayerConnection, InputLayerFigure, LearningSwitchLayerFigure

public class ValidationLayerConnection extends LayerConnection
{

	private static final long serialVersionUID = 0x23f786c0a86fbb84L;

	public ValidationLayerConnection()
	{
		setEndDecoration(null);
		setStartDecoration(new ArrowTip());
		setAttribute("FrameColor", Color.blue);
	}

	public boolean canConnect(Figure start, Figure end)
	{
		boolean retValue = super.canConnect(start, end);
		if (!(end instanceof InputLayerFigure))
			retValue = false;
		return retValue;
	}

	public void handleConnect(Figure start, Figure end)
	{
		InputLayerFigure source = (InputLayerFigure)end;
		LearningSwitchLayerFigure target = (LearningSwitchLayerFigure)start;
		mySynapse = source.getInputLayer();
		target.addPreValConn(source, mySynapse);
		source.addPostConn(target);
		source.notifyPostConn();
	}

	public void handleDisconnect(Figure start, Figure end)
	{
		InputLayerFigure source = (InputLayerFigure)end;
		LearningSwitchLayerFigure target = (LearningSwitchLayerFigure)start;
		if (target != null)
			target.removePreValConn(source, getSynapse());
		if (source != null)
			source.removePostConn(target);
	}
}
