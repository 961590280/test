// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OutputPluginLayerFigure.java

package org.joone.edit;

import CH.ifa.draw.figures.TextFigure;
import CH.ifa.draw.framework.ConnectionFigure;
import CH.ifa.draw.standard.RelativeLocator;
import java.awt.Color;
import java.awt.Font;
import java.util.Vector;
import org.joone.util.OutputConverterPlugIn;

// Referenced classes of package org.joone.edit:
//			LayerFigure, SpecialConnectionHandle, OutputPluginConnection, Wrapper, 
//			GenericFigure, UpdatableTextFigure

public class OutputPluginLayerFigure extends LayerFigure
{

	static final long serialVersionUID = 0xcee3457288cd16dL;
	protected OutputConverterPlugIn myPlugin;

	public OutputPluginLayerFigure()
	{
	}

	protected void initContent()
	{
		fPostConn = new Vector();
		fPreConn = new Vector();
		Font fb = new Font("Helvetica", 1, 12);
		myPlugin = (OutputConverterPlugIn)createLayer();
		TextFigure name = new UpdatableTextFigure() {

			final OutputPluginLayerFigure this$0;

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
				this$0 = OutputPluginLayerFigure.this;
				super();
			}
		};
		name.setFont(fb);
		name.setText((new StringBuilder("OutputPlugin ")).append(++numLayers).toString());
		name.setAttribute("TextColor", Color.blue);
		add(name);
	}

	public void addPostConn(LayerFigure figure)
	{
		if (!fPostConn.contains(figure))
			fPostConn.addElement(figure);
	}

	public void addPreConn(LayerFigure layerFigure, OutputConverterPlugIn plugin)
	{
		getPluginLayer().setNextPlugIn(plugin);
		if (!fPreConn.contains(layerFigure))
			fPreConn.addElement(layerFigure);
	}

	public void removePreConn(LayerFigure figure, OutputConverterPlugIn plugin)
	{
		fPreConn.removeElement(figure);
		getPluginLayer().addPlugIn(null);
	}

	protected Vector addHandles(Vector handles)
	{
		handles.addElement(new SpecialConnectionHandle(this, RelativeLocator.south(), new OutputPluginConnection(), Color.magenta));
		return handles;
	}

	public OutputConverterPlugIn getPluginLayer()
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
		if (!(conn instanceof OutputPluginConnection))
			retValue = false;
		if (getPluginLayer().isConnected())
			retValue = false;
		return retValue;
	}
}
