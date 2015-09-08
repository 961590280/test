// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OutputLayerFigure.java

package org.joone.edit;

import CH.ifa.draw.figures.TextFigure;
import CH.ifa.draw.standard.RelativeLocator;
import java.awt.Color;
import java.awt.Font;
import java.util.Vector;
import org.joone.engine.OutputPatternListener;
import org.joone.io.StreamOutputSynapse;
import org.joone.util.OutputConverterPlugIn;

// Referenced classes of package org.joone.edit:
//			LayerFigure, SpecialConnectionHandle, OutputPluginConnection, Wrapper, 
//			UpdatableTextFigure

public class OutputLayerFigure extends LayerFigure
{

	protected OutputPatternListener myOutputLayer;
	private static final long serialVersionUID = 0x1f501d770dc381a9L;

	public OutputLayerFigure()
	{
	}

	protected void initContent()
	{
		fPostConn = new Vector();
		fPreConn = new Vector();
		Font fb = new Font("Helvetica", 1, 12);
		myOutputLayer = (OutputPatternListener)createLayer();
		TextFigure name = new UpdatableTextFigure() {

			final OutputLayerFigure this$0;

			public void setText(String newText)
			{
				super.setText(newText);
				getOutputLayer().setName(newText);
			}

			public void update()
			{
				if (myOutputLayer.isEnabled())
					setAttribute("TextColor", Color.blue);
				else
					setAttribute("TextColor", Color.gray);
				setText(getOutputLayer().getName());
			}

			
			{
				this$0 = OutputLayerFigure.this;
				super();
			}
		};
		name.setFont(fb);
		name.setAttribute("TextColor", Color.blue);
		name.setText((new StringBuilder("OutputLayer ")).append(++numLayers).toString());
		add(name);
	}

	public void addPostConn(LayerFigure layerfigure, OutputPatternListener outputpatternlistener)
	{
	}

	public boolean addPreConn(LayerFigure figure)
	{
		if (!fPreConn.contains(figure))
		{
			fPreConn.addElement(figure);
			return true;
		} else
		{
			return false;
		}
	}

	public void removePreConn(LayerFigure figure)
	{
		super.removePreConn(figure);
	}

	public void addPreConn(LayerFigure layerFigure, OutputConverterPlugIn plugin)
	{
		OutputPatternListener opl = getOutputLayer();
		if (opl instanceof StreamOutputSynapse)
		{
			StreamOutputSynapse sos = (StreamOutputSynapse)opl;
			sos.addPlugIn(plugin);
			if (!fPreConn.contains(layerFigure))
				fPreConn.addElement(layerFigure);
		}
	}

	public void removePreConn(LayerFigure figure, OutputConverterPlugIn plugin)
	{
		fPreConn.removeElement(figure);
		OutputPatternListener opl = getOutputLayer();
		if (opl instanceof StreamOutputSynapse)
		{
			StreamOutputSynapse sos = (StreamOutputSynapse)opl;
			sos.addPlugIn(null);
		}
	}

	protected Vector addHandles(Vector handles)
	{
		handles.addElement(new SpecialConnectionHandle(this, RelativeLocator.south(), new OutputPluginConnection(), Color.magenta));
		return handles;
	}

	public OutputPatternListener getOutputLayer()
	{
		return myOutputLayer;
	}

	public Wrapper getWrapper()
	{
		return new Wrapper(this, getOutputLayer(), getOutputLayer().getName());
	}

	public boolean canConnect()
	{
		boolean retValue = super.canConnect() && !getOutputLayer().isOutputFull();
		return retValue;
	}
}
