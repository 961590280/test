// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ErrorLayerConnection.java

package org.joone.edit;

import CH.ifa.draw.framework.Figure;
import org.joone.engine.Synapse;

// Referenced classes of package org.joone.edit:
//			LayerConnection, TeacherLayerFigure, OutputLayerFigure

public class ErrorLayerConnection extends LayerConnection
{

	private static final long serialVersionUID = 0x95ee62d1942f45efL;

	public ErrorLayerConnection()
	{
	}

	public void handleDisconnect(Figure start, Figure end)
	{
		super.handleDisconnect(start, end);
	}

	public void handleConnect(Figure start, Figure end)
	{
		TeacherLayerFigure source = (TeacherLayerFigure)start;
		OutputLayerFigure target = (OutputLayerFigure)end;
		mySynapse = (Synapse)target.getOutputLayer();
		target.addPreConn(source, mySynapse);
		source.addPostConn(target, mySynapse);
		source.notifyPostConn();
	}

	public boolean canConnect(Figure start, Figure end)
	{
		boolean retValue = super.canConnect(start, end);
		if (!(end instanceof OutputLayerFigure))
			retValue = false;
		return retValue;
	}
}
