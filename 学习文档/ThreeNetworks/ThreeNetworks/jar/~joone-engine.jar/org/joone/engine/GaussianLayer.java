// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GaussianLayer.java

package org.joone.engine;

import java.util.*;
import org.joone.exception.JooneRuntimeException;
import org.joone.inspection.implementations.BiasInspection;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NetCheck;

// Referenced classes of package org.joone.engine:
//			SimpleLayer, NeuralNetListener, Monitor, SpatialMap, 
//			GaussianSpatialMap, NeuralNetEvent

public class GaussianLayer extends SimpleLayer
	implements NeuralNetListener
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/engine/GaussianLayer);
	private static final long serialVersionUID = 0xf2ee92c1974bff1aL;
	private int LayerWidth;
	private int LayerHeight;
	private int LayerDepth;
	private SpatialMap space_map;
	private double timeConstant;
	private int orderingPhase;
	private double initialGaussianSize;

	public GaussianLayer()
	{
		LayerWidth = 1;
		LayerHeight = 1;
		LayerDepth = 1;
		timeConstant = 200D;
		orderingPhase = 1000;
		initialGaussianSize = 10D;
	}

	public GaussianLayer(String ElemName)
	{
		super(ElemName);
		LayerWidth = 1;
		LayerHeight = 1;
		LayerDepth = 1;
		timeConstant = 200D;
		orderingPhase = 1000;
		initialGaussianSize = 10D;
	}

	public void backward(double ad[])
		throws JooneRuntimeException
	{
	}

	public void forward(double pattern[])
		throws JooneRuntimeException
	{
		try
		{
			getSpace_map().ApplyNeighborhoodFunction(pattern, outs, getMonitor().isLearning());
		}
		catch (Exception aioobe)
		{
			String msg;
			log.error(msg = (new StringBuilder("Exception thrown while processing the pattern ")).append(pattern.toString()).append(" Exception thrown is ").append(aioobe.getClass().getName()).append(". Message is ").append(aioobe.getMessage()).toString());
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
			getSpace_map().setMapDepth(layerDepth);
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
			getSpace_map().setMapHeight(LayerHeight);
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
			getSpace_map().setMapWidth(LayerWidth);
		}
	}

	public int getLargestDimension()
	{
		int max = 1;
		if (getLayerWidth() > max)
			max = getLayerWidth();
		if (getLayerHeight() > max)
			max = getLayerHeight();
		if (getLayerDepth() > max)
			max = getLayerDepth();
		return max;
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
		if (getOrderingPhase() > getMonitor().getTotCicles())
			checks.add(new NetCheck(1, "Ordering phase should be lesser than or equal to the number of epochs", this));
		return checks;
	}

	public void start()
	{
		if (getMonitor() != null)
			getMonitor().addNeuralNetListener(this, false);
		super.start();
	}

	public void netStarted(NeuralNetEvent e)
	{
		getSpace_map().init(getMonitor().getTotCicles());
		space_map.setInitialGaussianSize(getLargestDimension());
	}

	public void cicleTerminated(NeuralNetEvent e)
	{
		if (getMonitor().isLearning())
			getSpace_map().updateCurrentGaussianSize(getMonitor().getTotCicles() - getMonitor().getCurrentCicle());
	}

	public int getOrderingPhase()
	{
		return orderingPhase;
	}

	public void setOrderingPhase(int orderingPhase)
	{
		this.orderingPhase = orderingPhase;
		getSpace_map().setOrderingPhase(orderingPhase);
	}

	public double getTimeConstant()
	{
		return timeConstant;
	}

	public void setTimeConstant(double timeConstant)
	{
		this.timeConstant = timeConstant;
		getSpace_map().setTimeConstant(timeConstant);
	}

	protected SpatialMap getSpace_map()
	{
		if (space_map == null)
		{
			space_map = new GaussianSpatialMap();
			space_map.setMapDepth(getLayerDepth());
			space_map.setMapHeight(getLayerHeight());
			space_map.setMapWidth(getLayerWidth());
			space_map.setInitialGaussianSize(getInitialGaussianSize());
			space_map.setOrderingPhase(getOrderingPhase());
			space_map.setTimeConstant(getTimeConstant());
		}
		return space_map;
	}

	public double getInitialGaussianSize()
	{
		return initialGaussianSize;
	}

	public void setInitialGaussianSize(double initialGaussianSize)
	{
		this.initialGaussianSize = initialGaussianSize;
		getSpace_map().setInitialGaussianSize(initialGaussianSize);
	}

	public Collection Inspections()
	{
		Collection col = new ArrayList();
		col.add(new BiasInspection(null));
		return col;
	}

	public void netStoppedError(NeuralNetEvent neuralnetevent, String s)
	{
	}

	public void errorChanged(NeuralNetEvent neuralnetevent)
	{
	}

	public void netStopped(NeuralNetEvent neuralnetevent)
	{
	}

}
