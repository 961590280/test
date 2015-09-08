// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DeltaNormPlugIn.java

package org.joone.util;

import java.util.Vector;
import org.joone.engine.Pattern;

// Referenced classes of package org.joone.util:
//			ConverterPlugIn

public class DeltaNormPlugIn extends ConverterPlugIn
{

	private static final long serialVersionUID = 0x1792536133cababaL;
	private transient double probVolWin[];

	public DeltaNormPlugIn()
	{
	}

	protected boolean convert(int serie)
	{
		boolean retValue = false;
		int currInput = getSerieIndexNumber(serie);
		if (getProbVolWin()[currInput] == 0.0D)
			getProbVolWin()[currInput] = calculatePVW(currInput + 1, serie);
		int init = getSerieSelected().length;
		for (int i = getInputVector().size() - 1; i >= init; i--)
		{
			double vi = getDelta(i, currInput + 1, serie, false);
			vi /= getProbVolWin()[currInput];
			Pattern currPE = (Pattern)getInputVector().elementAt(i);
			currPE.setValue(serie, vi);
			retValue = true;
		}

		return retValue;
	}

	protected double calculatePVW(int delay, int serie)
	{
		int init = getSerieSelected().length;
		double sum = 0.0D;
		for (int i = init; i < getInputVector().size(); i++)
			sum += getDelta(i, delay, serie, true);

		int numPoints = getInputVector().size() - init;
		sum /= numPoints;
		return sum * 2D;
	}

	protected double getDelta(int index, int delay, int serie, boolean abs)
	{
		double Ci = getValuePoint(index, serie);
		double fd = funcDelta(index, delay, serie);
		if (Ci == 0.0D)
			if (!abs)
				return -fd / Math.abs(fd);
			else
				return fd / fd;
		if (!abs)
			return (Ci - fd) / Ci;
		else
			return Math.abs(Ci - fd) / Ci;
	}

	protected double funcDelta(int index, int delay, int serie)
	{
		return getValuePoint(index - delay, serie);
	}

	private double[] getProbVolWin()
	{
		if (probVolWin == null || probVolWin.length != getSerieSelected().length)
			probVolWin = new double[getSerieSelected().length];
		return probVolWin;
	}
}
