// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InputSynapse.java

package org.joone.io;

import java.io.IOException;
import org.joone.util.PlugInListener;

public interface InputSynapse
	extends PlugInListener
{

	public abstract char getDecimalPoint();

	public abstract int getFirstRow();

	public abstract int getLastRow();

	public abstract void gotoFirstLine()
		throws IOException;

	public abstract void gotoLine(int i)
		throws IOException;

	public abstract boolean isBuffered();

	public abstract boolean isEOF();

	public abstract void readAll();

	public abstract void setBuffered(boolean flag);

	public abstract void setDecimalPoint(char c);

	public abstract void setFirstRow(int i);

	public abstract void setLastRow(int i);

	public abstract void setFirstCol(int i)
		throws IllegalArgumentException;

	public abstract void setLastCol(int i)
		throws IllegalArgumentException;

	public abstract int getFirstCol();

	public abstract int getLastCol();

	public abstract void resetInput();

	public abstract boolean isStepCounter();

	public abstract void setStepCounter(boolean flag);
}
