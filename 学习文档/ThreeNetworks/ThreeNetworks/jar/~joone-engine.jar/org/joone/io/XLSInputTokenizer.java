// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   XLSInputTokenizer.java

package org.joone.io;

import java.io.IOException;
import java.util.*;
import org.apache.poi.hssf.usermodel.*;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.io:
//			PatternTokenizer

public class XLSInputTokenizer
	implements PatternTokenizer
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/io/XLSInputTokenizer);
	private int numTokens;
	private char m_decimalPoint;
	private double tokensArray[];
	private int row_no;
	private int mark_row;
	private final HSSFSheet sheet;

	public XLSInputTokenizer(HSSFSheet i_sheet)
		throws IOException
	{
		numTokens = 0;
		m_decimalPoint = '.';
		row_no = 0;
		mark_row = 0;
		sheet = i_sheet;
	}

	public int getLineno()
	{
		return row_no;
	}

	public int getNumTokens()
		throws IOException
	{
		return numTokens;
	}

	public double getTokenAt(int p0)
		throws IOException
	{
		if (tokensArray == null && !nextLine())
			return 0.0D;
		if (tokensArray.length <= p0)
			return 0.0D;
		else
			return tokensArray[p0];
	}

	public double[] getTokensArray()
	{
		return tokensArray;
	}

	public void mark()
		throws IOException
	{
		mark_row = row_no;
	}

	public boolean nextLine()
		throws IOException
	{
		int lastRowNum = sheet.getLastRowNum();
		if (row_no <= lastRowNum)
		{
			HSSFRow row = sheet.getRow(row_no);
			if (row == null)
			{
				numTokens = 0;
				tokensArray = new double[numTokens];
			} else
			{
				int maxCol = 0;
				Hashtable cellist = new Hashtable();
				double v;
				int i;
				for (Iterator iter = row.cellIterator(); iter.hasNext(); cellist.put(new Integer(i), new Double(v)))
				{
					v = 0.0D;
					HSSFCell cell = (HSSFCell)iter.next();
					i = cell.getCellNum();
					if (i > maxCol)
						maxCol = i;
					switch (cell.getCellType())
					{
					case 1: // '\001'
						String cellValue = cell.getStringCellValue();
						try
						{
							v = Double.valueOf(cellValue).doubleValue();
						}
						catch (NumberFormatException nfe)
						{
							log.warn((new StringBuilder("Warning: Not numeric cell at (")).append(row_no).append(",").append(i).append("): <").append(cellValue).append("> - Skipping").toString());
							v = 0.0D;
						}
						break;

					case 0: // '\0'
						v = cell.getNumericCellValue();
						break;
					}
				}

				tokensArray = new double[maxCol + 1];
				for (Enumeration keys = cellist.keys(); keys.hasMoreElements();)
				{
					Integer Icol = (Integer)keys.nextElement();
					Double Dval = (Double)cellist.get(Icol);
					tokensArray[Icol.intValue()] = Dval.doubleValue();
				}

			}
			row_no++;
			return true;
		} else
		{
			return false;
		}
	}

	public void resetInput()
		throws IOException
	{
		row_no = mark_row;
	}

	public void setDecimalPoint(char dp)
	{
		m_decimalPoint = dp;
	}

	public char getDecimalPoint()
	{
		return m_decimalPoint;
	}

}
