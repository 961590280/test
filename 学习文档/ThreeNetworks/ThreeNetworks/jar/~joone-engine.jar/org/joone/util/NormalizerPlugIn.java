// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NormalizerPlugIn.java

package org.joone.util;

import java.util.Vector;
import org.joone.engine.Pattern;

// Referenced classes of package org.joone.util:
//			ConverterPlugIn

public class NormalizerPlugIn extends ConverterPlugIn
{

	private double min;
	private double max;
	private double datamin;
	private double datamax;
	private static final long serialVersionUID = 0x40b5bbaf431bb38dL;

	public NormalizerPlugIn()
	{
		min = 0.0D;
		max = 1.0D;
		datamin = 0.0D;
		datamax = 0.0D;
	}

	protected boolean convert(int serie)
	{
		boolean retValue = false;
		int s = getInputVector().size();
		double vMax = getValuePoint(0, serie);
		double vMin = vMax;
		if (datamin == 0.0D && datamax == 0.0D)
		{
			for (int i = 0; i < s; i++)
			{
				double v = getValuePoint(i, serie);
				if (v > vMax)
					vMax = v;
				else
				if (v < vMin)
					vMin = v;
			}

		} else
		{
			vMax = datamax;
			vMin = datamin;
		}
		double d = vMax - vMin;
		for (int i = 0; i < s; i++)
		{
			double v;
			if (d != 0.0D)
			{
				v = getValuePoint(i, serie);
				v = (v - vMin) / d;
				v = v * (getMax() - getMin()) + getMin();
			} else
			{
				v = getMin();
			}
			Pattern currPE = (Pattern)getInputVector().elementAt(i);
			currPE.setValue(serie, v);
			retValue = true;
		}

		return retValue;
	}

	public double getMax()
	{
		return max;
	}

	public double getMin()
	{
		return min;
	}

	public void setMax(double newMax)
	{
		if (max != newMax)
		{
			max = newMax;
			super.fireDataChanged();
		}
	}

	public void setMin(double newMin)
	{
		if (min != newMin)
		{
			min = newMin;
			super.fireDataChanged();
		}
	}

	public double getDataMax()
	{
		return datamax;
	}

	public double getDataMin()
	{
		return datamin;
	}

	public void setDataMax(double newDataMax)
	{
		if (datamax != newDataMax)
		{
			datamax = newDataMax;
			super.fireDataChanged();
		}
	}

	public void setDataMin(double newDataMin)
	{
		if (datamin != newDataMin)
		{
			datamin = newDataMin;
			super.fireDataChanged();
		}
	}
}
