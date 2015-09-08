// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GaussianSpatialMap.java

package org.joone.engine;


// Referenced classes of package org.joone.engine:
//			SpatialMap

public class GaussianSpatialMap extends SpatialMap
{

	private static final long serialVersionUID = 0xb296ae5dd87c2d1dL;

	public GaussianSpatialMap()
	{
	}

	public void ApplyNeighborhoodFunction(double distances[], double n_outs[], boolean isLearning)
	{
		double dFalloff = 0.0D;
		double nbhRadius = 1.0D;
		double nbhRadiusSq = 1.0D;
		double dist_to_node = 0.0D;
		int current_output = 0;
		extractWinner(distances);
		int winx = getWinnerX();
		int winy = getWinnerY();
		int winz = getWinnerZ();
		nbhRadius = getCurrentGaussianSize();
		nbhRadiusSq = nbhRadius * nbhRadius;
		for (int z = 0; z < getMapDepth(); z++)
		{
			for (int y = 0; y < getMapHeight(); y++)
			{
				for (int x = 0; x < getMapWidth(); x++)
				{
					dist_to_node = distanceBetween(winx, winy, winz, x, y, z);
					dFalloff = getCircle2DDistanceFalloff(dist_to_node, nbhRadiusSq);
					current_output = x + y * getMapWidth() + z * (getMapWidth() * getMapHeight());
					n_outs[current_output] = dFalloff;
				}

			}

		}

	}

	private double getCircle2DDistanceFalloff(double distSq, double radiusSq)
	{
		return Math.exp(-distSq / (2D * radiusSq));
	}
}
