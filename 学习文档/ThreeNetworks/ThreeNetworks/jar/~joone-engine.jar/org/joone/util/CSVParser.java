// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CSVParser.java

package org.joone.util;

import java.util.StringTokenizer;
import java.util.Vector;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

public class CSVParser
{

	private String m_values;
	private boolean range_allowed;
	private static final ILogger log = LoggerFactory.getLogger(org/joone/util/CSVParser);
	private static final char RANGE_SEPARATOR = 45;

	public CSVParser(String values)
	{
		this(values, true);
	}

	public CSVParser(String values, boolean range)
	{
		range_allowed = true;
		m_values = values;
		range_allowed = range;
	}

	public int[] parseInt()
		throws NumberFormatException
	{
		int ivalues[] = (int[])null;
		Vector values = new Vector();
		for (StringTokenizer tokens = new StringTokenizer(m_values, ","); tokens.hasMoreTokens();)
		{
			String tk = tokens.nextToken().trim();
			int rpos = tk.indexOf('-');
			if (rpos <= 0)
				try
				{
					values.add(new Integer(Integer.parseInt(tk)));
				}
				catch (NumberFormatException nfe)
				{
					String error = (new StringBuilder("Error parsing '")).append(m_values).append("' : '").append(tk).append("' is not a valid integer value").toString();
					throw new NumberFormatException(error);
				}
			else
			if (range_allowed)
			{
				String tkl = tk.substring(0, rpos);
				String tkr = tk.substring(rpos + 1);
				try
				{
					int iv = Integer.parseInt(tkl);
					int fv = Integer.parseInt(tkr);
					if (iv > fv)
					{
						int ii = fv;
						fv = iv;
						iv = ii;
					}
					for (int i = iv; i <= fv; i++)
						values.add(new Integer(i));

				}
				catch (NumberFormatException nfe)
				{
					String error = (new StringBuilder("Error parsing '")).append(m_values).append("' : '").append(tk).append("' contains not valid integer values").toString();
					throw new NumberFormatException(error);
				}
			} else
			{
				String error = (new StringBuilder("Error parsing '")).append(m_values).append("' : range not allowed").toString();
				throw new NumberFormatException(error);
			}
		}

		ivalues = new int[values.size()];
		for (int v = 0; v < values.size(); v++)
			ivalues[v] = ((Integer)values.elementAt(v)).intValue();

		return ivalues;
	}

	public double[] parseDouble()
		throws NumberFormatException
	{
		Vector values = new Vector();
		for (StringTokenizer tokens = new StringTokenizer(m_values, ","); tokens.hasMoreTokens();)
		{
			String tk = tokens.nextToken().trim();
			int rpos = tk.indexOf('-');
			if (rpos <= 0)
			{
				try
				{
					values.add(new Double(Double.parseDouble(tk)));
				}
				catch (NumberFormatException nfe)
				{
					String error = (new StringBuilder("Error parsing '")).append(m_values).append("' : '").append(tk).append("' is not a valid numeric value").toString();
					throw new NumberFormatException(error);
				}
			} else
			{
				String error = (new StringBuilder("Error parsing '")).append(m_values).append("' : range not allowed for not integer values").toString();
				throw new NumberFormatException(error);
			}
		}

		double dvalues[] = new double[values.size()];
		for (int v = 0; v < values.size(); v++)
			dvalues[v] = ((Double)values.elementAt(v)).doubleValue();

		return dvalues;
	}

	public static void main(String args[])
	{
		CSVParser parser = new CSVParser("1.0,-3.6,1.4,15");
		double dd[] = parser.parseDouble();
		log.debug("Double values:");
		if (dd != null)
		{
			for (int i = 0; i < dd.length; i++)
				log.debug((new StringBuilder("array[")).append(i).append("] = ").append(dd[i]).toString());

		}
		parser = new CSVParser("1,-3,4-8,11");
		int ii[] = parser.parseInt();
		log.debug("Integer values:");
		if (ii != null)
		{
			for (int i = 0; i < ii.length; i++)
				log.debug((new StringBuilder("array[")).append(i).append("] = ").append(ii[i]).toString());

		}
	}

}
