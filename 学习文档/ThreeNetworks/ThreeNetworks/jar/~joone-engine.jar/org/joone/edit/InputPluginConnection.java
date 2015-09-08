// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InputPluginConnection.java

package org.joone.edit;

import CH.ifa.draw.figures.ArrowTip;
import CH.ifa.draw.framework.Figure;
import java.awt.Color;
import org.joone.io.StreamInputSynapse;
import org.joone.util.ConverterPlugIn;

// Referenced classes of package org.joone.edit:
//			LayerConnection, InputPluginLayerFigure, InputLayerFigure

public class InputPluginConnection extends LayerConnection
{

	private static final long serialVersionUID = 0x2285f269f1475237L;

	public InputPluginConnection()
	{
		setEndDecoration(null);
		setStartDecoration(new ArrowTip());
		setAttribute("FrameColor", Color.magenta);
	}

	public boolean canConnect(Figure start, Figure end)
	{
		boolean retValue = super.canConnect(start, end);
		if (!(end instanceof InputPluginLayerFigure))
			retValue = false;
		if (start instanceof InputLayerFigure)
		{
			InputLayerFigure ilf = (InputLayerFigure)start;
			StreamInputSynapse sis = ilf.getInputLayer();
			if (sis.getPlugIn() != null)
				retValue = false;
		}
		if (start instanceof InputPluginLayerFigure)
		{
			InputPluginLayerFigure ilf = (InputPluginLayerFigure)start;
			ConverterPlugIn cpi = ilf.getPluginLayer();
			if (cpi.getNextPlugIn() != null)
				retValue = false;
		}
		return retValue;
	}

	public void handleConnect(Figure start, Figure end)
	{
		InputPluginLayerFigure source = (InputPluginLayerFigure)end;
		ConverterPlugIn myPlugin = source.getPluginLayer();
		if (start instanceof InputLayerFigure)
		{
			InputLayerFigure target = (InputLayerFigure)start;
			target.addPreConn(source, myPlugin);
			source.addPostConn(target);
		} else
		{
			InputPluginLayerFigure target = (InputPluginLayerFigure)start;
			target.addPreConn(source, myPlugin);
			source.addPostConn(target);
		}
		source.notifyPostConn();
	}

	public void handleDisconnect(Figure start, Figure end)
	{
		if (end == null)
			return;
		InputPluginLayerFigure source = (InputPluginLayerFigure)end;
		if (start instanceof InputPluginLayerFigure)
		{
			InputPluginLayerFigure target = (InputPluginLayerFigure)start;
			source.removePostConn(target);
			if (target != null)
				target.removePreConn(source, source.getPluginLayer());
		} else
		{
			InputLayerFigure target = (InputLayerFigure)start;
			if (target != null)
				target.removePreConn(source, source.getPluginLayer());
			source.removePostConn(target);
		}
	}
}
