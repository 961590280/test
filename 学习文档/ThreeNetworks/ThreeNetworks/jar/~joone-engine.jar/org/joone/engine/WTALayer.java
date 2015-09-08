// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WTALayer.java

package org.joone.engine;

import java.util.*;
import org.joone.exception.JooneRuntimeException;
import org.joone.inspection.implementations.BiasInspection;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NetCheck;

// Referenced classes of package org.joone.engine:
//			SimpleLayer

public class WTALayer extends SimpleLayer
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/engine/WTALayer);
	private static final long serialVersionUID = 0xf2ee92c1974bff1aL;
	private int LayerWidth;
	private int LayerHeight;
	private int LayerDepth;

	public WTALayer()
	{
		LayerWidth = 1;
		LayerHeight = 1;
		LayerDepth = 1;
	}

	public WTALayer(String ElemName)
	{
		super(ElemName);
		LayerWidth = 1;
		LayerHeight = 1;
		LayerDepth = 1;
	}

	public void backward(double ad[])
		throws JooneRuntimeException
	{
	}

	public void forward(double pattern[])
		throws JooneRuntimeException
	{
		int x = 0;
		int winner = 0;
		double min_dist = 1.7976931348623157E+308D;
		int n = getRows();
		try
		{
			for (x = 0; x < n; x++)
			{
				double in = pattern[x];
				if (in < min_dist)
				{
					min_dist = in;
					winner = x;
				}
			}

			for (x = 0; x < n; x++)
				if (x == winner)
					outs[x] = 1.0D;
				else
					outs[x] = 0.0D;

		}
		catch (Exception aioobe)
		{
			String msg = (new StringBuilder("Exception thrown while processing the element ")).append(x).append(" of the array. Value is : ").append(pattern[x]).append(" Exception thrown is ").append(aioobe.getClass().getName()).append(". Message is ").append(aioobe.getMessage()).toString();
			log.error(msg);
			throw new JooneRuntimeException(msg, aioobe);
		}
	}

	public int getLayerDepth()
	{
		return LayerDepth;
	}

	public void setLayerDepth(int layerDepth)
	{
		if (layerDepth != getLayerDepth())
		{
			LayerDepth = layerDepth;
			setRows(getLayerWidth() * getLayerHeight() * getLayerDepth());
			setDimensions();
			setConnDimensions();
		}
	}

	public int getLayerHeight()
	{
		return LayerHeight;
	}

	public void setLayerHeight(int LayerHeight)
	{
		if (LayerHeight != getLayerHeight())
		{
			this.LayerHeight = LayerHeight;
			setRows(getLayerWidth() * getLayerHeight() * getLayerDepth());
			setDimensions();
			setConnDimensions();
		}
	}

	public int getLayerWidth()
	{
		return LayerWidth;
	}

	public void setLayerWidth(int LayerWidth)
	{
		if (LayerWidth != getLayerWidth())
		{
			this.LayerWidth = LayerWidth;
			setRows(getLayerWidth() * getLayerHeight() * getLayerDepth());
			setDimensions();
			setConnDimensions();
		}
	}

	public TreeSet check()
	{
		TreeSet checks = super.check();
		if (getLayerWidth() < 1)
			checks.add(new NetCheck(0, "Layer width should be greater than or equal to 1.", this));
		if (getLayerHeight() < 1)
			checks.add(new NetCheck(0, "Layer height should be greater than or equal to 1.", this));
		if (getLayerDepth() < 1)
			checks.add(new NetCheck(0, "Layer depth should be greater than or equal to 1.", this));
		return checks;
	}

	public Collection Inspections()
	{
		Collection col = new ArrayList();
		col.add(new BiasInspection(null));
		return col;
	}

}
