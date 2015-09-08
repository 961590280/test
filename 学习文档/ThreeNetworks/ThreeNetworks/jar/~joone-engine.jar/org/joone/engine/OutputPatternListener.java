// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OutputPatternListener.java

package org.joone.engine;


// Referenced classes of package org.joone.engine:
//			NeuralElement, Pattern

public interface OutputPatternListener
	extends NeuralElement
{

	public abstract void fwdPut(Pattern pattern);

	public abstract boolean isOutputFull();

	public abstract void setOutputFull(boolean flag);

	public abstract int getInputDimension();

	public abstract Pattern revGet();

	public abstract void setInputDimension(int i);
}
