// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BiasInspection.java

package org.joone.inspection.implementations;

import org.joone.engine.Matrix;
import org.joone.inspection.Inspection;

public class BiasInspection
	implements Inspection
{

	private Matrix bias;

	public BiasInspection(Matrix biasArg)
	{
		bias = biasArg;
	}

	public Object[][] getComponent()
	{
		if (bias == null)
			return null;
		double values[][] = bias.getValue();
		if (values.length > 0 && values[0].length > 0)
		{
			Object bigValues[][] = new Object[values.length][values[0].length];
			for (int i = 0; i < values[0].length; i++)
			{
				for (int j = 0; j < values.length; j++)
					bigValues[j][i] = new Double(values[j][i]);

			}

			return bigValues;
		} else
		{
			return null;
		}
	}

	public Object[] getNames()
	{
		if (bias == null)
			return null;
		double values[][] = bias.getValue();
		if (values.length > 0 && values[0].length > 0)
		{
			Object names[] = new String[values[0].length];
			for (int i = 0; i < values[0].length; i++)
				names[i] = (new StringBuilder("Column ")).append(i).toString();

			return names;
		} else
		{
			return null;
		}
	}

	public String getTitle()
	{
		return "Bias";
	}

	public boolean rowNumbers()
	{
		return false;
	}

	public void setComponent(Object newValues[][])
	{
		double values[][] = bias.getValue();
		for (int x = 0; x < values.length && x < newValues.length; x++)
		{
			for (int y = 0; y < values[0].length && y < newValues[0].length; y++)
				values[x][y] = ((Double)newValues[x][y]).doubleValue();

		}

	}
}
