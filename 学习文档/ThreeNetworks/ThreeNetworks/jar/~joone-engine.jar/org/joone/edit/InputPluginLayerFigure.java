// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InputPluginLayerFigure.java

package org.joone.edit;

import CH.ifa.draw.figures.TextFigure;
import CH.ifa.draw.framework.ConnectionFigure;
import CH.ifa.draw.standard.RelativeLocator;
import java.awt.Color;
import java.awt.Font;
import java.util.Vector;
import org.joone.engine.InputPatternListener;
import org.joone.util.AbstractConverterPlugIn;
import org.joone.util.ConverterPlugIn;

// Referenced classes of package org.joone.edit:
//			LayerFigure, SpecialConnectionHandle, InputPluginConnection, Wrapper, 
//			GenericFigure, UpdatableTextFigure

public class InputPluginLayerFigure extends LayerFigure
{

	protected ConverterPlugIn myPlugin;
	private static final long serialVersionUID = 0x34ef2358a07b718bL;

	public InputPluginLayerFigure()
	{
	}

	protected void initContent()
	{
		fPostConn = new Vector();
		fPreConn = new Vector();
		Font fb = new Font("Helvetica", 1, 12);
		myPlugin = (ConverterPlugIn)createLayer();
		TextFigure name = new UpdatableTextFigure() {

			final InputPluginLayerFigure this$0;

			public void setText(String newText)
			{
				super.setText(newText);
				getPluginLayer().setName(newText);
			}

			public void update()
			{
				setText(getPluginLayer().getName());
			}

			
			{
				this$0 = InputPluginLayerFigure.this;
				super();
			}
		};
		name.setFont(fb);
		name.setText((new StringBuilder("InputPlugin ")).append(++numLayers).toString());
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
		return false;
	}

	public void addPreConn(LayerFigure layerFigure, AbstractConverterPlugIn plugin)
	{
		getPluginLayer().addPlugIn(plugin);
		if (!fPreConn.contains(layerFigure))
			fPreConn.addElement(layerFigure);
	}

	public void removePreConn(LayerFigure figure, AbstractConverterPlugIn plugin)
	{
		fPreConn.removeElement(figure);
		getPluginLayer().addPlugIn(null);
	}

	protected Vector addHandles(Vector handles)
	{
		handles.addElement(new SpecialConnectionHandle(this, RelativeLocator.south(), new InputPluginConnection(), Color.magenta));
		return handles;
	}

	public ConverterPlugIn getPluginLayer()
	{
		return myPlugin;
	}

	public boolean canConnect()
	{
		return true;
	}

	public Wrapper getWrapper()
	{
		return new Wrapper(this, getPluginLayer(), getPluginLayer().getName());
	}

	public boolean canConnect(GenericFigure start, ConnectionFigure conn)
	{
		boolean retValue = super.canConnect(start, conn);
		if (!(conn instanceof InputPluginConnection))
			retValue = false;
		if (getPluginLayer().isConnected())
			retValue = false;
		return retValue;
	}
}
