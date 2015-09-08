// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InputsInspection.java

package org.joone.inspection.implementations;

import java.util.Vector;
import org.joone.engine.Pattern;
import org.joone.inspection.Inspection;

public class InputsInspection
	implements Inspection
{

	private Vector inputs;

	public InputsInspection(Vector inputsArg)
	{
		inputs = null;
		inputs = inputsArg;
	}

	public Object[][] getComponent()
	{
		if (inputs != null && inputs.size() > 0)
		{
			Object bigValues[][] = (Object[][])null;
			for (int i = 0; i < inputs.size(); i++)
			{
				Pattern pattern = (Pattern)inputs.elementAt(i);
				double array[] = pattern.getArray();
				if (bigValues == null)
					bigValues = new Object[inputs.size()][array.length + 1];
				for (int j = 1; j < array.length + 1; j++)
				{
					Double d = new Double(array[j - 1]);
					bigValues[i][j] = d;
				}

			}

			for (int i = 0; i < inputs.size(); i++)
				bigValues[i][0] = new Integer(i + 1);

			return bigValues;
		} else
		{
			return null;
		}
	}

	public String getTitle()
	{
		return "Inputs";
	}

	public Object[] getNames()
	{
		if (inputs != null && inputs.size() > 0)
		{
			Object names[] = (Object[])null;
			for (int i = 0; i < inputs.size(); i++)
			{
				Pattern pattern = (Pattern)inputs.elementAt(i);
				double array[] = pattern.getArray();
				if (names == null)
					names = new String[array.length + 1];
				names[0] = "Row Number";
				for (int j = 1; j < array.length + 1; j++)
					names[j] = (new StringBuilder("Column ")).append(j).toString();

			}

			return names;
		} else
		{
			return null;
		}
	}

	public boolean rowNumbers()
	{
		return true;
	}

	public void setComponent(Object newValues[][])
	{
		for (int x = 0; x < inputs.size() && x < newValues.length; x++)
		{
			double values[] = ((Pattern)inputs.elementAt(x)).getArray();
			int n = ((Pattern)inputs.elementAt(x)).getCount();
			for (int y = 0; y < values.length && y < newValues[0].length - 1; y++)
				values[y] = ((Double)newValues[x][y + 1]).doubleValue();

			Pattern patt = new Pattern(values);
			patt.setCount(n);
			inputs.setElementAt(patt, x);
		}

	}
}
