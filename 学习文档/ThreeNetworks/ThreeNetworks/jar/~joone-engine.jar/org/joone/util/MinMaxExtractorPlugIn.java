// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MinMaxExtractorPlugIn.java

package org.joone.util;

import java.util.Vector;
import org.joone.engine.Pattern;

// Referenced classes of package org.joone.util:
//			ConverterPlugIn

public class MinMaxExtractorPlugIn extends ConverterPlugIn
{
	class PointValue
	{

		double value;
		int point;
		final MinMaxExtractorPlugIn this$0;

		boolean equal(PointValue compare)
		{
			if (compare == null)
				return false;
			return compare.point == point;
		}

		PointValue()
		{
			this$0 = MinMaxExtractorPlugIn.this;
			super();
		}

		PointValue(PointValue c)
		{
			this$0 = MinMaxExtractorPlugIn.this;
			super();
			value = c.value;
			point = c.point;
		}

		PointValue(int p, double v)
		{
			this$0 = MinMaxExtractorPlugIn.this;
			super();
			value = v;
			point = p;
		}
	}


	private final double MIN_VALUE = 0.050000000000000003D;
	private final double MAX_VALUE = 0.94999999999999996D;
	private final double MID_VALUE = 0.49999999999999994D;
	private double minPerc;
	private static final long serialVersionUID = 0x5edcb0813d7bbe8cL;

	public MinMaxExtractorPlugIn()
	{
		minPerc = 0.01D;
	}

	protected boolean convert(int serie)
	{
		boolean retValue = false;
		int pf = 0;
		PointValue lastInserted = null;
		Vector changePoints = new Vector();
		PointValue pMax = new PointValue(0, getValuePoint(0, serie));
		PointValue pMin = new PointValue(0, getValuePoint(0, serie));
		PointValue pCur = new PointValue();
		int s = getInputVector().size();
		boolean up;
		if (s > 1)
		{
			if (getValuePoint(1, serie) > pMax.value)
				up = true;
			else
				up = false;
		} else
		{
			return false;
		}
		while (pf < s - 1) 
		{
			if (up)
			{
				pCur.point = pf;
				pCur.value = getValuePoint(pf, serie);
				pf = findMax(pCur, serie);
				if (!pMin.equal(lastInserted) && minPerc(getValuePoint(pf, serie), pMin.value))
				{
					PointValue lastInserting = new PointValue(pMin.point, 0.050000000000000003D);
					changePoints.addElement(lastInserting);
					writeMidPoints(lastInserted, lastInserting, serie);
					retValue = true;
					lastInserted = new PointValue(lastInserting);
					pMax.point = pf;
					pMax.value = getValuePoint(pf, serie);
				}
				if (pMax.value < getValuePoint(pf, serie))
				{
					pMax.point = pf;
					pMax.value = getValuePoint(pf, serie);
				}
			}
			up = false;
			pCur.point = pf;
			pCur.value = getValuePoint(pf, serie);
			pf = findMin(pCur, serie);
			if (!pMax.equal(lastInserted) && minPerc(getValuePoint(pf, serie), pMax.value))
			{
				PointValue lastInserting = new PointValue(pMax.point, 0.94999999999999996D);
				changePoints.addElement(lastInserting);
				writeMidPoints(lastInserted, lastInserting, serie);
				retValue = true;
				lastInserted = new PointValue(lastInserting);
				pMin.point = pf;
				pMin.value = getValuePoint(pf, serie);
			}
			if (pMin.value > getValuePoint(pf, serie))
			{
				pMin.point = pf;
				pMin.value = getValuePoint(pf, serie);
			}
			up = true;
		}
		if (lastInserted.point < s - 1)
		{
			writeMidPoints(lastInserted, new PointValue(s - 1, 0.49999999999999994D), serie);
			Pattern currPE = (Pattern)getInputVector().elementAt(s - 1);
			currPE.setValue(serie, 0.49999999999999994D);
			retValue = true;
		} else
		{
			Pattern currPE = (Pattern)getInputVector().elementAt(s - 1);
			currPE.setValue(serie, lastInserted.value);
			retValue = true;
		}
		return retValue;
	}

	private int findMax(PointValue pInit, int serie)
	{
		int i = pInit.point + 1;
		int s = getInputVector().size();
		double lastValue = pInit.value;
		for (; i < s; i++)
		{
			if (getValuePoint(i, serie) < lastValue)
				break;
			lastValue = getValuePoint(i, serie);
		}

		return --i;
	}

	private int findMin(PointValue pInit, int serie)
	{
		int i = pInit.point + 1;
		int s = getInputVector().size();
		double lastValue = pInit.value;
		for (; i < s; i++)
		{
			if (getValuePoint(i, serie) > lastValue)
				break;
			lastValue = getValuePoint(i, serie);
		}

		return --i;
	}

	public double getMinChangePercentage()
	{
		return minPerc;
	}

	private boolean minPerc(double last, double first)
	{
		double perc = (last - first) / first;
		if (perc < 0.0D)
			perc *= -1D;
		return perc >= minPerc;
	}

	public void setMinChangePercentage(double newMinPerc)
	{
		if (minPerc != newMinPerc)
		{
			minPerc = newMinPerc;
			super.fireDataChanged();
		}
	}

	private void writeMidPoints(PointValue pointA, PointValue pointB, int serie)
	{
		int i;
		double vi;
		if (pointA == null)
		{
			i = 0;
			vi = 0.49999999999999994D;
		} else
		{
			i = pointA.point;
			vi = pointA.value;
		}
		double rmin;
		double rmax;
		if (vi < pointB.value)
		{
			rmin = vi;
			rmax = pointB.value;
		} else
		{
			rmax = vi;
			rmin = pointB.value;
		}
		double vmin;
		double vmax;
		if (getValuePoint(i, serie) < getValuePoint(pointB.point, serie))
		{
			vmin = getValuePoint(i, serie);
			vmax = getValuePoint(pointB.point, serie);
		} else
		{
			vmax = getValuePoint(i, serie);
			vmin = getValuePoint(pointB.point, serie);
		}
		double rdif = rmax - rmin;
		double vdif = vmax - vmin;
		for (; i < pointB.point; i++)
		{
			vi = (getValuePoint(i, serie) - vmin) / vdif;
			vi = vi * rdif + rmin;
			Pattern currPE = (Pattern)getInputVector().elementAt(i);
			currPE.setValue(serie, vi);
		}

	}
}
