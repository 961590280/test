// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ChartHandleConnection.java

package org.joone.edit;

import CH.ifa.draw.framework.Figure;
import org.joone.engine.OutputPatternListener;

// Referenced classes of package org.joone.edit:
//			LayerConnection, OutputLayerFigure, ChartInterface, ChartHandleLayerFigure, 
//			ChartingHandle

public class ChartHandleConnection extends LayerConnection
{

	private static final long serialVersionUID = 0x1c0c3cff23a58f8aL;

	public ChartHandleConnection()
	{
	}

	public boolean canConnect(Figure start, Figure end)
	{
		boolean retVal = end instanceof OutputLayerFigure;
		if (retVal)
		{
			OutputPatternListener target = ((OutputLayerFigure)end).getOutputLayer();
			retVal = target instanceof ChartInterface;
			if (retVal)
			{
				ChartingHandle handle = (ChartingHandle)((ChartHandleLayerFigure)start).getOutputLayer();
				retVal = handle.getChartSynapse() == null;
			}
		}
		return retVal;
	}
}
