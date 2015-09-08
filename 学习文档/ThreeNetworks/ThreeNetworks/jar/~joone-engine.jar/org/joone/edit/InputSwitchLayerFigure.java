// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InputSwitchLayerFigure.java

package org.joone.edit;

import CH.ifa.draw.figures.TextFigure;
import CH.ifa.draw.framework.ConnectionFigure;
import CH.ifa.draw.standard.ConnectionHandle;
import CH.ifa.draw.standard.RelativeLocator;
import java.awt.Color;
import java.awt.Font;
import java.util.Vector;
import org.joone.engine.InputPatternListener;
import org.joone.io.InputSwitchSynapse;
import org.joone.io.StreamInputSynapse;

// Referenced classes of package org.joone.edit:
//			InputLayerFigure, InputLayerConnection, GenericFigure, LayerFigure, 
//			UpdatableTextFigure

public class InputSwitchLayerFigure extends InputLayerFigure
{

	private static final long serialVersionUID = 0x1cf1c0f2a08ee2b8L;

	public InputSwitchLayerFigure()
	{
	}

	protected Vector addHandles(Vector handles)
	{
		handles.addElement(new ConnectionHandle(this, RelativeLocator.east(), new InputLayerConnection()));
		return handles;
	}

	protected void initContent()
	{
		fPostConn = new Vector();
		fPreConn = new Vector();
		Font fb = new Font("Helvetica", 1, 12);
		myInputLayer = (InputPatternListener)createLayer();
		TextFigure name = new UpdatableTextFigure() {

			final InputSwitchLayerFigure this$0;

			public void setText(String newText)
			{
				super.setText(newText);
				getInputLayer().setName(newText);
			}

			public void update()
			{
				setText(getInputLayer().getName());
			}

			
			{
				this$0 = InputSwitchLayerFigure.this;
				super();
			}
		};
		name.setFont(fb);
		name.setText((new StringBuilder("InputSwitch ")).append(++numLayers).toString());
		name.setAttribute("TextColor", Color.blue);
		add(name);
	}

	public boolean canConnect(GenericFigure start, ConnectionFigure conn)
	{
		return super.canConnect(start, conn);
	}

	public boolean addPreConn(LayerFigure layerFigure, InputPatternListener synapse)
	{
		boolean ret = super.addPreConn(layerFigure, synapse);
		InputSwitchSynapse iss = (InputSwitchSynapse)getInputLayer();
		iss.addInputSynapse((StreamInputSynapse)synapse);
		return ret;
	}

	public void removePreConn(LayerFigure figure, InputPatternListener conn)
	{
		removePreConn(figure);
		InputSwitchSynapse iss = (InputSwitchSynapse)getInputLayer();
		String sName = conn.getName();
		iss.removeInputSynapse(sName);
	}
}
