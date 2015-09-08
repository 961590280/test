// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RbfGaussianLayer.java

package org.joone.engine;

import java.util.ArrayList;
import java.util.Collection;
import org.joone.exception.JooneRuntimeException;
import org.joone.inspection.implementations.BiasInspection;
import org.joone.io.StreamInputSynapse;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.util.AbstractConverterPlugIn;
import org.joone.util.RbfRandomCenterSelector;

// Referenced classes of package org.joone.engine:
//			RbfLayer, RbfGaussianParameters

public class RbfGaussianLayer extends RbfLayer
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/engine/RbfGaussianLayer);
	private RbfGaussianParameters theGaussianParameters[];
	private boolean theUseRandomSelector;
	private RbfRandomCenterSelector theRandomSelector;

	public RbfGaussianLayer()
	{
		theUseRandomSelector = true;
	}

	protected void backward(double pattern[])
		throws JooneRuntimeException
	{
		for (int i = 0; i < gradientInps.length; i++)
			gradientOuts[i] = gradientInps[i];

	}

	protected void forward(double pattern[])
		throws JooneRuntimeException
	{
		if (theUseRandomSelector && theGaussianParameters == null)
			setGaussianParameters(theRandomSelector.getGaussianParameters());
		int i = 0;
		try
		{
			for (i = 0; i < getRows(); i++)
			{
				double mySquaredEuclDist = 0.0D;
				for (int j = 0; j < pattern.length; j++)
				{
					double myTemp = pattern[j] - theGaussianParameters[i].getMean()[j];
					mySquaredEuclDist += myTemp * myTemp;
				}

				outs[i] = Math.exp(mySquaredEuclDist / (-2D * theGaussianParameters[i].getStdDeviation() * theGaussianParameters[i].getStdDeviation()));
			}

		}
		catch (Exception aioobe)
		{
			aioobe.printStackTrace();
			String msg;
			log.error(msg = (new StringBuilder("Exception thrown while processing the element ")).append(i).append(" of the array. Value is : ").append(pattern[i]).append(" Exception thrown is ").append(aioobe.getClass().getName()).append(". Message is ").append(aioobe.getMessage()).toString());
			throw new JooneRuntimeException(msg, aioobe);
		}
	}

	protected void setDimensions()
	{
		super.setDimensions();
		gradientOuts = new double[gradientInps.length];
	}

	public RbfGaussianParameters[] getGaussianParameters()
	{
		return theGaussianParameters;
	}

	public void setGaussianParameters(RbfGaussianParameters aGaussianParameters[])
	{
		if (aGaussianParameters.length != getRows())
		{
			setRows(aGaussianParameters.length);
			log.warn("Setting new RBF Gaussian parameters -> # neurons changed.");
		}
		theGaussianParameters = aGaussianParameters;
	}

	public void useRandomCenter(StreamInputSynapse aStreamInput)
	{
		theUseRandomSelector = true;
		theRandomSelector = new RbfRandomCenterSelector(this);
		if (aStreamInput.getPlugIn() == null)
		{
			aStreamInput.setPlugIn(theRandomSelector);
		} else
		{
			AbstractConverterPlugIn myPlugin = aStreamInput.getPlugIn();
			myPlugin.addPlugIn(theRandomSelector);
		}
	}

	public Collection Inspections()
	{
		Collection col = new ArrayList();
		col.add(new BiasInspection(null));
		return col;
	}

}
