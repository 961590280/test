// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Matrix.java

package org.joone.engine;

import java.io.*;
import org.joone.engine.weights.RandomWeightInitializer;
import org.joone.engine.weights.WeightInitializer;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

public class Matrix
	implements Serializable, Cloneable
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/engine/Matrix);
	public static final double DEFAULT_INITIAL = 0.20000000000000001D;
	private static final long serialVersionUID = 0xecab30096ff46b72L;
	public double value[][];
	public double delta[][];
	public boolean enabled[][];
	public boolean fixed[][];
	protected int m_rows;
	protected int m_cols;
	protected WeightInitializer weightInitializer;

	public Matrix()
	{
	}

	public Matrix(int aRows, int aColumns)
	{
		this(aRows, aColumns, 0.20000000000000001D);
	}

	public Matrix(int aRows, int aColumns, double anInitial)
	{
		value = new double[aRows][aColumns];
		delta = new double[aRows][aColumns];
		enabled = new boolean[aRows][aColumns];
		fixed = new boolean[aRows][aColumns];
		m_rows = aRows;
		m_cols = aColumns;
		enableAll();
		unfixAll();
		if (anInitial == 0.0D)
		{
			setWeightInitializer(new RandomWeightInitializer(0.0D), false);
			clear();
		} else
		{
			setWeightInitializer(new RandomWeightInitializer(anInitial));
		}
	}

	public void initialize()
	{
		getWeightInitializer().initialize(this);
	}

	public void randomizeConditionally(double amplitude)
	{
		WeightInitializer weightInit = getWeightInitializer();
		if (weightInit instanceof RandomWeightInitializer)
		{
			setWeightInitializer(new RandomWeightInitializer(amplitude), true);
			setWeightInitializer(weightInit, false);
		} else
		{
			initialize();
		}
	}

	public void setWeightInitializer(WeightInitializer aWeightInitializer)
	{
		setWeightInitializer(aWeightInitializer, true);
	}

	public void setWeightInitializer(WeightInitializer aWeightInitializer, boolean anInitialize)
	{
		weightInitializer = aWeightInitializer;
		if (anInitialize)
			getWeightInitializer().initialize(this);
	}

	public WeightInitializer getWeightInitializer()
	{
		if (weightInitializer == null)
			weightInitializer = new RandomWeightInitializer(0.20000000000000001D);
		return weightInitializer;
	}

	public Object clone()
	{
		Matrix o = null;
		try
		{
			o = (Matrix)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			log.error("Matrix can't clone", e);
		}
		o.value = (double[][])o.value.clone();
		o.delta = (double[][])o.delta.clone();
		o.enabled = (boolean[][])o.enabled.clone();
		o.fixed = (boolean[][])o.fixed.clone();
		for (int x = 0; x < m_rows; x++)
		{
			o.value[x] = (double[])o.value[x].clone();
			o.delta[x] = (double[])o.delta[x].clone();
			o.enabled[x] = (boolean[])o.enabled[x].clone();
			o.fixed[x] = (boolean[])o.fixed[x].clone();
		}

		return o;
	}

	public void addNoise(double amplitude)
	{
		for (int x = 0; x < m_rows; x++)
		{
			for (int y = 0; y < m_cols; y++)
				if (enabled[x][y] && !fixed[x][y])
					value[x][y] += -amplitude + Math.random() * (2D * amplitude);

		}

	}

	public void removeRow(int aRow)
	{
		double myValue[][] = new double[m_rows - 1][];
		double myDelta[][] = new double[m_rows - 1][];
		boolean myEnabled[][] = new boolean[m_rows - 1][];
		boolean myFixed[][] = new boolean[m_rows - 1][];
		for (int x = 0; x < m_rows; x++)
			if (x < aRow)
			{
				myValue[x] = (double[])value[x].clone();
				myDelta[x] = (double[])delta[x].clone();
				myEnabled[x] = (boolean[])enabled[x].clone();
				myFixed[x] = (boolean[])fixed[x].clone();
			} else
			if (x > aRow)
			{
				myValue[x - 1] = (double[])value[x].clone();
				myDelta[x - 1] = (double[])delta[x].clone();
				myEnabled[x - 1] = (boolean[])enabled[x].clone();
				myFixed[x - 1] = (boolean[])fixed[x].clone();
			}

		value = myValue;
		delta = myDelta;
		enabled = myEnabled;
		fixed = myFixed;
		m_rows--;
	}

	public void removeColumn(int aColumn)
	{
		double myValue[][] = new double[m_rows][m_cols - 1];
		double myDelta[][] = new double[m_rows][m_cols - 1];
		boolean myEnabled[][] = new boolean[m_rows][m_cols - 1];
		boolean myFixed[][] = new boolean[m_rows][m_cols - 1];
		for (int x = 0; x < m_rows; x++)
		{
			for (int y = 0; y < m_cols; y++)
				if (y < aColumn)
				{
					myValue[x][y] = value[x][y];
					myDelta[x][y] = delta[x][y];
					myEnabled[x][y] = enabled[x][y];
					myFixed[x][y] = fixed[x][y];
				} else
				if (y > aColumn)
				{
					myValue[x][y - 1] = value[x][y];
					myDelta[x][y - 1] = delta[x][y];
					myEnabled[x][y - 1] = enabled[x][y];
					myFixed[x][y - 1] = fixed[x][y];
				}

		}

		value = myValue;
		delta = myDelta;
		enabled = myEnabled;
		fixed = myFixed;
		m_cols--;
	}

	public void clear()
	{
		for (int x = 0; x < m_rows; x++)
		{
			for (int y = 0; y < m_cols; y++)
				if (enabled[x][y] || !fixed[x][y])
				{
					value[x][y] = 0.0D;
					delta[x][y] = 0.0D;
				}

		}

	}

	public void clearDelta()
	{
		for (int x = 0; x < m_rows; x++)
		{
			for (int y = 0; y < m_cols; y++)
				delta[x][y] = 0.0D;

		}

	}

	public void enableAll()
	{
		for (int x = 0; x < m_rows; x++)
		{
			for (int y = 0; y < m_cols; y++)
				enabled[x][y] = true;

		}

	}

	public void disableAll()
	{
		for (int x = 0; x < m_rows; x++)
		{
			for (int y = 0; y < m_cols; y++)
				enabled[x][y] = false;

		}

	}

	public void fixAll()
	{
		for (int x = 0; x < m_rows; x++)
		{
			for (int y = 0; y < m_cols; y++)
				fixed[x][y] = true;

		}

	}

	public void unfixAll()
	{
		for (int x = 0; x < m_rows; x++)
		{
			for (int y = 0; y < m_cols; y++)
				fixed[x][y] = false;

		}

	}

	public int getM_rows()
	{
		return m_rows;
	}

	public void setM_rows(int newm_rows)
	{
		m_rows = newm_rows;
	}

	public int getM_cols()
	{
		return m_cols;
	}

	public void setM_cols(int newm_cols)
	{
		m_cols = newm_cols;
	}

	public double[][] getDelta()
	{
		return delta;
	}

	public void setDelta(double newdelta[][])
	{
		delta = newdelta;
	}

	public double[][] getValue()
	{
		return value;
	}

	public void setValue(double newvalue[][])
	{
		value = newvalue;
	}

	public boolean[][] getFixed()
	{
		return fixed;
	}

	public void setFixed(boolean newfixed[][])
	{
		fixed = newfixed;
	}

	public boolean[][] getEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean newenabled[][])
	{
		enabled = newenabled;
	}

	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		if (enabled == null)
		{
			enabled = new boolean[m_rows][m_cols];
			enableAll();
		}
		if (fixed == null)
		{
			fixed = new boolean[m_rows][m_cols];
			unfixAll();
		}
	}

}
