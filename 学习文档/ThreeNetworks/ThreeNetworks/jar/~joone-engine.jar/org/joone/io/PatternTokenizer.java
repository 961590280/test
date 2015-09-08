// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PatternTokenizer.java

package org.joone.io;

import java.io.IOException;

public interface PatternTokenizer
{

	public abstract int getLineno();

	public abstract int getNumTokens()
		throws IOException;

	public abstract double getTokenAt(int i)
		throws IOException;

	public abstract double[] getTokensArray();

	public abstract void mark()
		throws IOException;

	public abstract boolean nextLine()
		throws IOException;

	public abstract void resetInput()
		throws IOException;

	public abstract char getDecimalPoint();

	public abstract void setDecimalPoint(char c);
}
