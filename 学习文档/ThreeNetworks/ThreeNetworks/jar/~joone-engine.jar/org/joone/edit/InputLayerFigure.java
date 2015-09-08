// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InputLayerFigure.java

package org.joone.edit;

import CH.ifa.draw.figures.TextFigure;
import CH.ifa.draw.framework.ConnectionFigure;
import CH.ifa.draw.standard.ConnectionHandle;
import CH.ifa.draw.standard.RelativeLocator;
import java.awt.Color;
import java.awt.Font;
import java.util.Vector;
import org.joone.engine.InputPatternListener;
import org.joone.io.StreamInputSynapse;
import org.joone.util.ConverterPlugIn;

// Referenced classes of package org.joone.edit:
//			LayerFigure, InputLayerConnection, SpecialConnectionHandle, InputPluginConnection, 
//			Wrapper, DesiredLayerConnection, ValidationLayerConnection, GenericFigure, 
//			UpdatableTextFigure

public class InputLayerFigure extends LayerFigure
{

	protected InputPatternListener myInputLayer;
	private static final long serialVersionUID = 0xa39e2b23ed7b2e82L;

	public InputLayerFigure()
	{
	}

	protected void initContent()
	{
		fPostConn = new Vector();
		fPreConn = new Vector();
		Font fb = new Font("Helvetica", 1, 12);
		myInputLayer = (InputPatternListener)createLayer();
		TextFigure name = new UpdatableTextFigure() {

			final InputLayerFigure this$0;

			public void setText(String newText)
			{
				super.setText(newText);
				getInputLayer().setName(newText);
			}

			public void update()
			{
				if (myInputLayer.isEnabled())
					setAttribute("TextColor", Color.blue);
				else
					setAttribute("TextColor", Color.gray);
				setText(getInputLayer().getName());
			}

			
			{
				this$0 = InputLayerFigure.this;
				super();
			}
		};
		name.setFont(fb);
		name.setText((new StringBuilder("InputLayer ")).append(++numLayers).toString());
		name.setAttribute("TextColor", Color.blue);
		add(name);
	}

	public void addPostConn(LayerFigure figure)
	{
		if (!fPostConn.contains(figure))
			fPostConn.addElement(figure);
	}

	public boolean addPreConn(LayerFigure layerFigure, InputPatternListener synapse)
	{
		if (!fPreConn.contains(layerFigure))
		{
			fPreConn.addElement(layerFigure);
			return true;
		} else
		{
			return false;
		}
	}

	public void addPreConn(LayerFigure layerFigure, ConverterPlugIn plugin)
	{
		getInputLayer().addPlugIn(plugin);
		if (!fPreConn.contains(layerFigure))
			fPreConn.addElement(layerFigure);
	}

	public void removePreConn(LayerFigure figure, ConverterPlugIn plugin)
	{
		fPreConn.removeElement(figure);
		getInputLayer().addPlugIn(null);
	}

	protected Vector addHandles(Vector handles)
	{
		handles.addElement(new ConnectionHandle(this, RelativeLocator.east(), new InputLayerConnection()));
		handles.addElement(new SpecialConnectionHandle(this, RelativeLocator.south(), new InputPluginConnection(), Color.magenta));
		return handles;
	}

	public StreamInputSynapse getInputLayer()
	{
		return (StreamInputSynapse)myInputLayer;
	}

	public boolean canConnect()
	{
		return true;
	}

	public Wrapper getWrapper()
	{
		return new Wrapper(this, getInputLayer(), getInputLayer().getName());
	}

	public boolean canConnect(GenericFigure start, ConnectionFigure conn)
	{
		StreamInputSynapse sis = (StreamInputSynapse)myInputLayer;
		boolean retValue = false;
		if ((conn instanceof DesiredLayerConnection) && !sis.isInputFull())
			retValue = true;
		if ((conn instanceof ValidationLayerConnection) && !sis.isInputFull())
			retValue = true;
		return retValue;
	}
}
