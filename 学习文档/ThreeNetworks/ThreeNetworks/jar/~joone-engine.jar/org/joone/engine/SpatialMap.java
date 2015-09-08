// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpatialMap.java

package org.joone.engine;

import java.io.Serializable;

public abstract class SpatialMap
	implements Serializable
{

	private double InitialGaussianSize;
	private double CurrentGaussianSize;
	private int map_width;
	private int map_height;
	private int map_depth;
	private int win_x;
	private int win_y;
	private int win_z;
	private int TotalEpochs;
	private int orderingPhase;
	double TimeConstant;

	public SpatialMap()
	{
		InitialGaussianSize = 1.0D;
		CurrentGaussianSize = 1.0D;
		map_width = 1;
		map_height = 1;
		map_depth = 1;
		win_x = 0;
		win_y = 0;
		win_z = 0;
		TotalEpochs = 1;
		TimeConstant = 1.0D;
	}

	public final void init(int total_epochs)
	{
		updateCurrentGaussianSize(1);
	}

	public final int getTotalEpochs()
	{
		return TotalEpochs;
	}

	public final void setInitialGaussianSize(double size)
	{
		setCurrentGaussianSize(size);
		InitialGaussianSize = size;
	}

	public final double getInitialGaussianSize()
	{
		return InitialGaussianSize;
	}

	public final void setCurrentGaussianSize(double size)
	{
		CurrentGaussianSize = size;
	}

	public final double getCurrentGaussianSize()
	{
		return CurrentGaussianSize;
	}

	public final void updateCurrentGaussianSize(int current_epoch)
	{
		if (current_epoch < getOrderingPhase())
			setCurrentGaussianSize(getInitialGaussianSize() * Math.exp(-((double)current_epoch / getTimeConstant())));
		else
			setCurrentGaussianSize(0.01D);
	}

	public abstract void ApplyNeighborhoodFunction(double ad[], double ad1[], boolean flag);

	protected final void extractWinner(double distances[])
	{
		double curDist = 0.0D;
		double bestDist = 1.7976931348623157E+308D;
		for (int z = 0; z < getMapDepth(); z++)
		{
			for (int y = 0; y < getMapHeight(); y++)
			{
				for (int x = 0; x < getMapWidth(); x++)
				{
					int current_output = x + y * getMapWidth() + z * (getMapWidth() * getMapHeight());
					curDist = distances[current_output];
					if (curDist < bestDist)
					{
						bestDist = curDist;
						win_x = x;
						win_y = y;
						win_z = z;
					}
				}

			}

		}

	}

	protected final int getWinnerX()
	{
		return win_x;
	}

	protected final int getWinnerY()
	{
		return win_y;
	}

	protected final int getWinnerZ()
	{
		return win_z;
	}

	public final void setMapDimensions(int x, int y, int z)
	{
		setMapWidth(x);
		setMapHeight(y);
		setMapDepth(z);
	}

	public final void setMapWidth(int w)
	{
		if (w > 0)
			map_width = w;
		else
			map_width = 1;
	}

	public final void setMapHeight(int h)
	{
		if (h > 0)
			map_height = h;
		else
			map_height = 1;
	}

	public final void setMapDepth(int d)
	{
		if (d > 0)
			map_depth = d;
		else
			map_depth = 1;
	}

	public final int getMapWidth()
	{
		return map_width;
	}

	public final int getMapHeight()
	{
		return map_height;
	}

	public final int getMapDepth()
	{
		return map_depth;
	}

	protected final double distanceBetween(int x1, int y1, int z1, int x2, int y2, int z2)
	{
		int xleg = 0;
		int yleg = 0;
		int zleg = 0;
		xleg = x1 - x2;
		xleg *= xleg;
		yleg = y1 - y2;
		yleg *= yleg;
		zleg = z1 - z2;
		zleg *= zleg;
		return (double)(xleg + yleg + zleg);
	}

	public int getOrderingPhase()
	{
		return orderingPhase;
	}

	public void setOrderingPhase(int orderingPhase)
	{
		this.orderingPhase = orderingPhase;
	}

	public double getTimeConstant()
	{
		return TimeConstant;
	}

	public void setTimeConstant(double TimeConstant)
	{
		this.TimeConstant = TimeConstant;
	}
}
