// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TeacherLayerFigure.java

package org.joone.edit;

import CH.ifa.draw.standard.ConnectionHandle;
import CH.ifa.draw.standard.RelativeLocator;
import java.awt.Color;
import java.util.Vector;
import org.joone.engine.InputPatternListener;
import org.joone.engine.OutputPatternListener;
import org.joone.engine.learning.ComparingElement;
import org.joone.io.StreamInputSynapse;

// Referenced classes of package org.joone.edit:
//			OutputLayerFigure, SpecialConnectionHandle, DesiredLayerConnection, LayerConnection, 
//			InputLayerFigure, LayerFigure

public class TeacherLayerFigure extends OutputLayerFigure
{

	private static final long serialVersionUID = 0x9d9110c73423b516L;

	public TeacherLayerFigure()
	{
	}

	protected Vector addHandles(Vector handles)
	{
		handles.addElement(new SpecialConnectionHandle(this, RelativeLocator.north(), new DesiredLayerConnection(), Color.red));
		handles.addElement(new ConnectionHandle(this, RelativeLocator.east(), new LayerConnection()));
		return handles;
	}

	public boolean addPreConn(LayerFigure figure, InputPatternListener synapse)
	{
		boolean ret = super.addPreConn(figure, synapse);
		if (figure instanceof InputLayerFigure)
		{
			ComparingElement ts = (ComparingElement)getOutputLayer();
			ts.setDesired((StreamInputSynapse)synapse);
		}
		return ret;
	}

	public void removePreConn(LayerFigure figure, InputPatternListener conn)
	{
		super.removePreConn(figure);
		if (figure instanceof InputLayerFigure)
		{
			ComparingElement ts = (ComparingElement)getOutputLayer();
			ts.setDesired(null);
		}
	}

	public void addPostConn(LayerFigure figure, OutputPatternListener synapse)
	{
		super.addPostConn(figure, synapse);
		if (!fPostConn.contains(figure))
		{
			ComparingElement ts = (ComparingElement)getOutputLayer();
			ts.addResultSynapse(synapse);
		}
	}

	public void removePostConn(LayerFigure figure, OutputPatternListener conn)
	{
		removePostConn(figure);
		ComparingElement ts = (ComparingElement)getOutputLayer();
		ts.removeResultSynapse(conn);
	}
}
