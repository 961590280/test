// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MemoryInputTokenizer.java

package org.joone.io;

import java.io.IOException;

// Referenced classes of package org.joone.io:
//			PatternTokenizer

public class MemoryInputTokenizer
	implements PatternTokenizer
{

	private double inputArray[][];
	private int lineNo;
	private int mark;
	private char decimalPoint;

	public MemoryInputTokenizer()
	{
		lineNo = 0;
		mark = 0;
	}

	public MemoryInputTokenizer(double array[][])
	{
		lineNo = 0;
		mark = 0;
		inputArray = array;
	}

	public void resetInput()
		throws IOException
	{
		lineNo = mark;
	}

	public int getLineno()
	{
		return lineNo;
	}

	public char getDecimalPoint()
	{
		return decimalPoint;
	}

	public boolean nextLine()
		throws IOException
	{
		lineNo++;
		return lineNo <= inputArray.length;
	}

	public int getNumTokens()
		throws IOException
	{
		double line[] = getTokensArray();
		return line.length;
	}

	public void mark()
		throws IOException
	{
		mark = lineNo;
	}

	public double getTokenAt(int posiz)
		throws IOException
	{
		double line[] = getTokensArray();
		return line[posiz];
	}

	public double[] getTokensArray()
	{
		return inputArray[lineNo - 1];
	}

	public void setDecimalPoint(char decimalPoint)
	{
		this.decimalPoint = decimalPoint;
	}
}
