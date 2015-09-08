// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DesiredLayerConnection.java

package org.joone.edit;

import CH.ifa.draw.figures.ArrowTip;
import CH.ifa.draw.framework.Figure;
import java.awt.Color;
import org.joone.engine.learning.ComparingElement;
import org.joone.io.StreamInputSynapse;

// Referenced classes of package org.joone.edit:
//			LayerConnection, InputLayerFigure, TeacherLayerFigure

public class DesiredLayerConnection extends LayerConnection
{

	private static final long serialVersionUID = 0x1f82a2123163e402L;

	public DesiredLayerConnection()
	{
		setEndDecoration(null);
		setStartDecoration(new ArrowTip());
		setAttribute("FrameColor", Color.yellow);
	}

	public boolean canConnect(Figure start, Figure end)
	{
		boolean retValue = super.canConnect(start, end);
		if (!(end instanceof InputLayerFigure))
			retValue = false;
		TeacherLayerFigure tlf = (TeacherLayerFigure)start;
		ComparingElement ts = (ComparingElement)tlf.getOutputLayer();
		if (ts.getDesired() != null)
			retValue = false;
		return retValue;
	}

	public void handleConnect(Figure start, Figure end)
	{
		InputLayerFigure source = (InputLayerFigure)end;
		TeacherLayerFigure target = (TeacherLayerFigure)start;
		org.joone.engine.InputPatternListener myInputSynapse = source.getInputLayer();
		target.addPreConn(source, myInputSynapse);
		source.addPostConn(target);
		source.notifyPostConn();
		setAttribute("FrameColor", Color.yellow);
	}

	public void handleDisconnect(Figure start, Figure end)
	{
		InputLayerFigure source = (InputLayerFigure)end;
		TeacherLayerFigure target = (TeacherLayerFigure)start;
		if (target != null)
			target.removePreConn(source, getSynapse());
		if (source != null)
		{
			source.removePostConn(target);
			source.getInputLayer().setInputFull(false);
		}
	}
}
