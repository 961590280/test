// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LayerFigure.java

package org.joone.edit;

import CH.ifa.draw.figures.NumberTextFigure;
import CH.ifa.draw.figures.TextFigure;
import CH.ifa.draw.framework.ConnectionFigure;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.standard.ConnectionHandle;
import CH.ifa.draw.standard.RelativeLocator;
import CH.ifa.draw.util.*;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import org.joone.engine.*;

// Referenced classes of package org.joone.edit:
//			ConcreteGenericFigure, Wrapper, LayerConnection, GenericFigure, 
//			UpdatableTextFigure, UpdatableNumberTextFigure

public class LayerFigure extends ConcreteGenericFigure
{

	protected Vector fPostConn;
	protected Vector fPreConn;
	protected Layer myLayer;
	private static final long serialVersionUID = 0xf0117bf4b72c0bd7L;

	public LayerFigure()
	{
	}

	protected void initContent()
	{
		fPostConn = new Vector();
		fPreConn = new Vector();
		Font f = new Font("Helvetica", 0, 12);
		Font fb = new Font("Helvetica", 1, 12);
		myLayer = (Layer)createLayer();
		TextFigure name = new UpdatableTextFigure() {

			final LayerFigure this$0;

			public void setText(String newText)
			{
				super.setText(newText);
				Layer ly = (Layer)getLayer();
				ly.setLayerName(newText);
			}

			public void update()
			{
				Layer ly = (Layer)getLayer();
				setText(ly.getLayerName());
			}

			
			{
				this$0 = LayerFigure.this;
				super();
			}
		};
		name.setFont(fb);
		name.setText((new StringBuilder("Layer ")).append(++numLayers).toString());
		name.setAttribute("TextColor", Color.blue);
		add(name);
		NumberTextFigure inputDim = new UpdatableNumberTextFigure() {

			final LayerFigure this$0;

			public void setText(String newText)
			{
				super.setText(newText);
				Layer ly = (Layer)getLayer();
				ly.setRows(Integer.parseInt(newText));
			}

			public void update()
			{
				Layer ly = (Layer)getLayer();
				setValue(ly.getRows());
			}

			
			{
				this$0 = LayerFigure.this;
				super();
			}
		};
		inputDim.setValue(1);
		inputDim.setFont(f);
		add(inputDim);
	}

	public boolean hasCycle(Figure start)
	{
		if (start == this)
			return true;
		for (Enumeration i = fPreConn.elements(); i.hasMoreElements();)
			if (((LayerFigure)i.nextElement()).hasCycle(start))
				return true;

		return false;
	}

	public void write(StorableOutput dw)
	{
		super.write(dw);
		writeConn(dw, fPreConn);
		writeConn(dw, fPostConn);
	}

	public void writeConn(StorableOutput dw, Vector v)
	{
		dw.writeInt(v.size());
		for (Enumeration i = v.elements(); i.hasMoreElements(); dw.writeStorable((Storable)i.nextElement()));
	}

	public void read(StorableInput dr)
		throws IOException
	{
		super.read(dr);
		fPreConn = readConn(dr);
		fPostConn = readConn(dr);
	}

	public Vector readConn(StorableInput dr)
		throws IOException
	{
		int size = dr.readInt();
		Vector v = new Vector(size);
		for (int i = 0; i < size; i++)
			v.addElement(dr.readStorable());

		return v;
	}

	public void removePostConn(LayerFigure figure)
	{
		fPostConn.removeElement(figure);
	}

	public void removePreConn(LayerFigure figure)
	{
		fPreConn.removeElement(figure);
	}

	public void removePostConn(LayerFigure figure, OutputPatternListener conn)
	{
		removePostConn(figure);
		Layer ly = (Layer)getLayer();
		if (ly != null)
			ly.removeOutputSynapse(conn);
	}

	public void removePreConn(LayerFigure figure, InputPatternListener conn)
	{
		removePreConn(figure);
		Layer ly = (Layer)getLayer();
		if (ly != null)
			ly.removeInputSynapse(conn);
	}

	public void addPostConn(LayerFigure figure, OutputPatternListener conn)
	{
		if (!fPostConn.contains(figure))
		{
			fPostConn.addElement(figure);
			Layer ly = (Layer)getLayer();
			if (ly != null)
				ly.addOutputSynapse(conn);
		}
	}

	public boolean addPreConn(LayerFigure figure, InputPatternListener conn)
	{
		if (!fPreConn.contains(figure))
		{
			fPreConn.addElement(figure);
			Layer ly = (Layer)getLayer();
			if (ly != null)
				ly.addInputSynapse(conn);
			return true;
		} else
		{
			return false;
		}
	}

	public void setLayer(Object newMyLayer)
	{
		myLayer = (Layer)newMyLayer;
	}

	public boolean canConnect(GenericFigure start, ConnectionFigure conn)
	{
		return true;
	}

	public static void setNumLayers(int newValue)
	{
		numLayers = newValue;
	}

	public Wrapper getWrapper()
	{
		Layer ly = (Layer)getLayer();
		return new Wrapper(this, ly, ly.getLayerName());
	}

	public Object getLayer()
	{
		return myLayer;
	}

	protected Vector addHandles(Vector handles)
	{
		LayerConnection lc = new LayerConnection();
		lc.setParam("class", "org.joone.engine.FullSynapse");
		lc.setParam("label", "F");
		handles.addElement(new ConnectionHandle(this, RelativeLocator.east(), lc));
		return handles;
	}
}
