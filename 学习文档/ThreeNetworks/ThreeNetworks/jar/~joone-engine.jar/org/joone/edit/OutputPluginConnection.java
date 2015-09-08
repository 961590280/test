// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OutputPluginConnection.java

package org.joone.edit;

import CH.ifa.draw.figures.ArrowTip;
import CH.ifa.draw.framework.Figure;
import java.awt.Color;
import org.joone.util.OutputConverterPlugIn;

// Referenced classes of package org.joone.edit:
//			LayerConnection, OutputPluginLayerFigure, OutputLayerFigure

public class OutputPluginConnection extends LayerConnection
{

	static final long serialVersionUID = 0x3b2a1108c9cce969L;

	public OutputPluginConnection()
	{
		setEndDecoration(null);
		setStartDecoration(new ArrowTip());
		setAttribute("FrameColor", Color.magenta);
	}

	public boolean canConnect(Figure start, Figure end)
	{
		boolean retValue = super.canConnect(start, end);
		if (!(end instanceof OutputPluginLayerFigure))
			retValue = false;
		return retValue;
	}

	public void handleConnect(Figure start, Figure end)
	{
		OutputPluginLayerFigure source = (OutputPluginLayerFigure)end;
		OutputConverterPlugIn myPlugin = source.getPluginLayer();
		if (start instanceof OutputLayerFigure)
		{
			OutputLayerFigure target = (OutputLayerFigure)start;
			target.addPreConn(source, myPlugin);
			source.addPostConn(target);
		} else
		{
			OutputPluginLayerFigure target = (OutputPluginLayerFigure)start;
			target.addPreConn(source, myPlugin);
			source.addPostConn(target);
		}
		source.notifyPostConn();
	}

	public void handleDisconnect(Figure start, Figure end)
	{
		if (end == null)
			return;
		OutputPluginLayerFigure source = (OutputPluginLayerFigure)end;
		if (start instanceof OutputPluginLayerFigure)
		{
			OutputPluginLayerFigure target = (OutputPluginLayerFigure)start;
			source.removePostConn(target);
			if (target != null)
				target.removePreConn(source, source.getPluginLayer());
		} else
		{
			OutputLayerFigure target = (OutputLayerFigure)start;
			if (target != null)
				target.removePreConn(source, source.getPluginLayer());
			source.removePostConn(target);
		}
	}
}
