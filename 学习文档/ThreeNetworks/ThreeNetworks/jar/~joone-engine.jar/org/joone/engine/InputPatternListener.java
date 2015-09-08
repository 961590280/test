// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InputPatternListener.java

package org.joone.engine;


// Referenced classes of package org.joone.engine:
//			NeuralElement, Pattern

public interface InputPatternListener
	extends NeuralElement
{

	public abstract Pattern fwdGet();

	public abstract boolean isInputFull();

	public abstract void setInputFull(boolean flag);

	public abstract int getOutputDimension();

	public abstract void revPut(Pattern pattern);

	public abstract void setOutputDimension(int i);

	public abstract void reset();
}
