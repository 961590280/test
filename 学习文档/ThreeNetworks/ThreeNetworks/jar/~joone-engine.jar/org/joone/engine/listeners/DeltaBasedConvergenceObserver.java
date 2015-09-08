// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DeltaBasedConvergenceObserver.java

package org.joone.engine.listeners;

import java.util.Vector;
import org.joone.engine.*;
import org.joone.net.NeuralNet;

// Referenced classes of package org.joone.engine.listeners:
//			ConvergenceObserver

public class DeltaBasedConvergenceObserver extends ConvergenceObserver
{

	private double size;
	private int cycles;
	private int cycleCounter;
	private NeuralNet net;

	public DeltaBasedConvergenceObserver()
	{
		size = 0.0D;
		cycles = 5;
		cycleCounter = 0;
	}

	public void setSize(double aSize)
	{
		size = aSize;
	}

	public double getSize()
	{
		return size;
	}

	public void setCycles(int aCylces)
	{
		cycles = aCylces;
	}

	public int getCycles()
	{
		return cycles;
	}

	public void setNeuralNet(NeuralNet aNet)
	{
		net = aNet;
	}

	public NeuralNet getNeuralNet()
	{
		return net;
	}

	protected void manageStop(Monitor monitor)
	{
	}

	protected void manageCycle(Monitor monitor)
	{
	}

	protected void manageStart(Monitor monitor)
	{
	}

	protected void manageError(Monitor mon)
	{
		if (cycles <= 0)
			return;
		for (int i = 0; i < net.getLayers().size(); i++)
		{
			Layer myLayer = (Layer)net.getLayers().get(i);
			Matrix myBiases = myLayer.getBias();
			for (int b = 0; b < myBiases.getM_rows(); b++)
				if (myBiases != null && !isConvergence(myBiases))
				{
					cycleCounter = 0;
					disableCurrentConvergence = false;
					return;
				}

			for (int s = 0; s < myLayer.getAllOutputs().size(); s++)
				if (myLayer.getAllOutputs().get(s) instanceof Synapse)
				{
					Matrix myWeights = ((Synapse)myLayer.getAllOutputs().get(s)).getWeights();
					if (myWeights != null && !isConvergence(myWeights))
					{
						cycleCounter = 0;
						disableCurrentConvergence = false;
						return;
					}
				}

		}

		cycleCounter++;
		if (cycleCounter == cycles)
		{
			if (!disableCurrentConvergence)
				fireNetConverged(mon);
			cycleCounter = 0;
		}
	}

	protected boolean isConvergence(Matrix aMatrix)
	{
		for (int r = 0; r < aMatrix.getM_rows(); r++)
		{
			for (int c = 0; c < aMatrix.getM_cols(); c++)
				if (Math.abs(aMatrix.delta[r][c]) > size)
					return false;

		}

		return true;
	}

	protected void manageStopError(Monitor monitor, String s)
	{
	}
}
