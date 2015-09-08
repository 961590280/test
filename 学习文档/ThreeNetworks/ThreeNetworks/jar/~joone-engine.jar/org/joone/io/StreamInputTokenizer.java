// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StreamInputTokenizer.java

package org.joone.io;

import java.io.*;
import java.util.StringTokenizer;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.io:
//			PatternTokenizer

public class StreamInputTokenizer
	implements PatternTokenizer
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/io/StreamInputTokenizer);
	private static final int MAX_BUFFER_SIZE = 0x100000;
	private LineNumberReader stream;
	private StringTokenizer tokenizer;
	private int numTokens;
	private char m_decimalPoint;
	private String m_delim;
	private double tokensArray[];
	private int maxBufSize;

	public StreamInputTokenizer(Reader in)
		throws IOException
	{
		this(in, 0x100000);
	}

	public StreamInputTokenizer(Reader in, int maxBufSize)
		throws IOException
	{
		tokenizer = null;
		numTokens = 0;
		m_decimalPoint = '.';
		m_delim = "; \t\n\r\f";
		this.maxBufSize = maxBufSize;
		stream = new LineNumberReader(in, maxBufSize);
		stream.mark(maxBufSize);
	}

	public int getLineno()
	{
		return stream.getLineNumber();
	}

	public int getNumTokens()
		throws IOException
	{
		return numTokens;
	}

	public double getTokenAt(int posiz)
		throws IOException
	{
		if (tokensArray == null && !nextLine())
		{
			log.warn("Warning during reading input stream. Requested line not found. Returning 0.");
			return 0.0D;
		}
		if (tokensArray.length <= posiz)
		{
			int lineno = stream.getLineNumber();
			log.warn((new StringBuilder("Warning during reading input stream. Requested token not found in line ")).append(lineno).append(". Returning 0.").toString());
			return 0.0D;
		} else
		{
			return tokensArray[posiz];
		}
	}

	public double[] getTokensArray()
	{
		if (tokensArray.length == 0)
			return tokensArray;
		else
			return (double[])tokensArray.clone();
	}

	public void mark()
		throws IOException
	{
		stream.mark(maxBufSize);
	}

	public boolean nextLine()
		throws IOException
	{
		String line = stream.readLine();
		if (line == null)
			return false;
		tokenizer = new StringTokenizer(line, m_delim, false);
		numTokens = tokenizer.countTokens();
		if (tokensArray == null || tokensArray.length != numTokens)
			tokensArray = new double[numTokens];
		for (int i = 0; i < numTokens; i++)
			tokensArray[i] = nextToken(m_delim);

		return true;
	}

	private double nextToken()
		throws IOException
	{
		return nextToken(null);
	}

	private double nextToken(String delim)
		throws IOException
	{
		String nt = null;
		if (tokenizer == null)
			nextLine();
		if (delim != null)
			nt = tokenizer.nextToken(delim);
		else
			nt = tokenizer.nextToken();
		if (m_decimalPoint != '.')
			nt = nt.replace(m_decimalPoint, '.');
		double parsedVal;
		try
		{
			parsedVal = Double.valueOf(nt).floatValue();
		}
		catch (NumberFormatException nfe)
		{
			log.warn((new StringBuilder("Warning: Not numeric value at row ")).append(getLineno()).append(": <").append(nt).append(">").toString());
			parsedVal = 0.0D;
		}
		return parsedVal;
	}

	public void resetInput()
		throws IOException
	{
		stream.reset();
		tokenizer = null;
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
