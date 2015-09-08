// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UnNormalizerOutputPlugIn.java

package org.joone.util;

import java.util.Vector;
import org.joone.engine.Pattern;

// Referenced classes of package org.joone.util:
//			OutputConverterPlugIn

public class UnNormalizerOutputPlugIn extends OutputConverterPlugIn
{

	private double min;
	private double max;
	private double datamax;
	private double datamin;
	private transient double datadiff;
	private transient double tmpmin;
	private transient double tmpmax;
	static final long serialVersionUID = 0x49dcd33e40b9b32cL;

	public UnNormalizerOutputPlugIn()
	{
		min = 0.0D;
		max = 1.0D;
		datamax = 0.0D;
		datamin = 0.0D;
		datadiff = 0.0D;
		tmpmin = 0.0D;
		tmpmax = 0.0D;
	}

	public UnNormalizerOutputPlugIn(String newAdvSerieSel, double newInDataMin, double newInDataMax, double newOutDataMin, 
			double newOutDataMax)
	{
		min = 0.0D;
		max = 1.0D;
		datamax = 0.0D;
		datamin = 0.0D;
		datadiff = 0.0D;
		tmpmin = 0.0D;
		tmpmax = 0.0D;
		setAdvancedSerieSelector(newAdvSerieSel);
		setInDataMin(newInDataMin);
		setInDataMax(newInDataMax);
		setOutDataMin(newOutDataMin);
		setOutDataMax(newOutDataMax);
	}

	public double getOutDataMax()
	{
		return max;
	}

	public double getOutDataMin()
	{
		return min;
	}

	public void setOutDataMax(double newMax)
	{
		if (max != newMax)
		{
			max = newMax;
			super.fireDataChanged();
		}
	}

	public void setOutDataMin(double newMin)
	{
		if (min != newMin)
		{
			min = newMin;
			super.fireDataChanged();
		}
	}

	public double getInDataMax()
	{
		return datamax;
	}

	public double getInDataMin()
	{
		return datamin;
	}

	public void setInDataMax(double newMax)
	{
		if (datamax != newMax)
		{
			datamax = newMax;
			super.fireDataChanged();
		}
	}

	public void setInDataMin(double newMin)
	{
		if (datamin != newMin)
		{
			datamin = newMin;
			super.fireDataChanged();
		}
	}

	protected boolean convert(int serie)
	{
		boolean retValue = false;
		Vector con_pats = getInputVector();
		double v = 0.0D;
		int i = 0;
		int datasize = 0;
		datasize = con_pats.size();
		if (con_pats != null && serie >= 0)
		{
			if (datamax == 0.0D && datamin == 0.0D)
			{
				setupMinMax(serie, con_pats);
			} else
			{
				tmpmin = datamin;
				tmpmax = datamax;
			}
			datadiff = tmpmax - tmpmin;
			for (i = 0; i < datasize; i++)
			{
				if (datadiff != 0.0D)
				{
					v = getValuePoint(i, serie);
					v = (v - tmpmin) / datadiff;
					v = v * (getOutDataMax() - getOutDataMin()) + getOutDataMin();
				} else
				{
					v = getOutDataMin();
				}
				((Pattern)con_pats.elementAt(i)).setValue(serie, v);
				retValue = true;
			}

		}
		return retValue;
	}

	protected void convert_pattern(int serie)
	{
		Pattern con_pat = getPattern();
		double v = 0.0D;
		if (con_pat != null && serie >= 0)
		{
			if (datamax != 0.0D || datamin != 0.0D)
			{
				datadiff = datamax - datamin;
				if (datadiff != 0.0D)
				{
					v = con_pat.getArray()[serie];
					v = (v - datamin) / datadiff;
					v = v * (getOutDataMax() - getOutDataMin()) + getOutDataMin();
				} else
				{
					v = getOutDataMin();
				}
				con_pat.setValue(serie, v);
			}
			return;
		} else
		{
			return;
		}
	}

	private void setupMinMax(int serie, Vector pats_to_convert)
	{
		int datasize = pats_to_convert.size();
		tmpmax = getValuePoint(0, serie);
		tmpmin = tmpmax;
		for (int i = 0; i < datasize; i++)
		{
			double v = getValuePoint(i, serie);
			if (v > tmpmax)
				tmpmax = v;
			else
			if (v < tmpmin)
				tmpmin = v;
		}

	}
}
