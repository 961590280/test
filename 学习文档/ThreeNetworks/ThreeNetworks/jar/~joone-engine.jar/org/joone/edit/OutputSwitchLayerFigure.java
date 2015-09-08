// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OutputSwitchLayerFigure.java

package org.joone.edit;

import CH.ifa.draw.figures.TextFigure;
import CH.ifa.draw.framework.ConnectionFigure;
import CH.ifa.draw.standard.ConnectionHandle;
import CH.ifa.draw.standard.RelativeLocator;
import java.awt.Color;
import java.awt.Font;
import java.util.Vector;
import org.joone.engine.OutputPatternListener;
import org.joone.engine.OutputSwitchSynapse;

// Referenced classes of package org.joone.edit:
//			OutputLayerFigure, LayerConnection, GenericFigure, LayerFigure, 
//			UpdatableTextFigure

public class OutputSwitchLayerFigure extends OutputLayerFigure
{

	static final long serialVersionUID = 0x4ebbe3da996508b9L;

	public OutputSwitchLayerFigure()
	{
	}

	protected Vector addHandles(Vector handles)
	{
		handles.addElement(new ConnectionHandle(this, RelativeLocator.east(), new LayerConnection()));
		return handles;
	}

	protected void initContent()
	{
		fPostConn = new Vector();
		fPreConn = new Vector();
		Font fb = new Font("Helvetica", 1, 12);
		myOutputLayer = (OutputPatternListener)createLayer();
		TextFigure name = new UpdatableTextFigure() {

			final OutputSwitchLayerFigure this$0;

			public void setText(String newText)
			{
				super.setText(newText);
				getOutputLayer().setName(newText);
			}

			public void update()
			{
				setText(getOutputLayer().getName());
			}

			
			{
				this$0 = OutputSwitchLayerFigure.this;
				super();
			}
		};
		name.setFont(fb);
		name.setText((new StringBuilder("Output Switch ")).append(++numLayers).toString());
		name.setAttribute("TextColor", Color.blue);
		add(name);
	}

	public boolean canConnect(GenericFigure start, ConnectionFigure conn)
	{
		return super.canConnect(start, conn);
	}

	public void addPostConn(LayerFigure layerFigure, OutputPatternListener synapse)
	{
		OutputSwitchSynapse oss = (OutputSwitchSynapse)getOutputLayer();
		oss.addOutputSynapse(synapse);
	}

	public void removePostConn(LayerFigure figure, OutputPatternListener conn)
	{
		removePostConn(figure);
		OutputSwitchSynapse oss = (OutputSwitchSynapse)getOutputLayer();
		String sName = conn.getName();
		oss.removeOutputSynapse(sName);
	}
}
