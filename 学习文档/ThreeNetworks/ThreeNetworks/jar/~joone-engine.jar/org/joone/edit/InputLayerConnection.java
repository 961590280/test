// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InputLayerConnection.java

package org.joone.edit;

import CH.ifa.draw.framework.Figure;
import org.joone.engine.InputPatternListener;
import org.joone.io.InputConnector;

// Referenced classes of package org.joone.edit:
//			LayerConnection, InputLayerFigure, LayerFigure, OutputLayerFigure, 
//			InputPluginLayerFigure, InputConnectorLayerFigure

public class InputLayerConnection extends LayerConnection
{

	private static final long serialVersionUID = 0x1c0c3cff23a58f8aL;

	public InputLayerConnection()
	{
	}

	public boolean canConnect(Figure start, Figure end)
	{
		boolean retVal = (start instanceof InputLayerFigure) && (end instanceof LayerFigure);
		retVal = retVal && !(end instanceof OutputLayerFigure);
		retVal = retVal && !(end instanceof InputPluginLayerFigure);
		if (retVal)
		{
			InputLayerFigure source = (InputLayerFigure)start;
			InputPatternListener ipl = source.getInputLayer();
			if (!(end instanceof InputConnectorLayerFigure))
			{
				retVal = !ipl.isInputFull();
			} else
			{
				InputConnectorLayerFigure target = (InputConnectorLayerFigure)end;
				InputConnector ic = (InputConnector)target.getInputLayer();
				retVal = !ic.isOutputFull();
			}
		}
		return retVal;
	}

	public void handleConnect(Figure start, Figure end)
	{
		InputLayerFigure source = (InputLayerFigure)start;
		LayerFigure target = (LayerFigure)end;
		InputPatternListener ipl = source.getInputLayer();
		target.addPreConn(source, ipl);
		source.addPostConn(target);
		source.notifyPostConn();
	}

	public void handleDisconnect(Figure start, Figure end)
	{
		InputLayerFigure source = (InputLayerFigure)start;
		LayerFigure target = (LayerFigure)end;
		if (target != null)
			target.removePreConn(source, source.getInputLayer());
		if (source != null)
			source.removePostConn(target);
	}
}
