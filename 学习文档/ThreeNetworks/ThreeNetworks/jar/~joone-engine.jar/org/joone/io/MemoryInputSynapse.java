// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MemoryInputSynapse.java

package org.joone.io;

import org.joone.exception.JooneRuntimeException;

// Referenced classes of package org.joone.io:
//			StreamInputSynapse, MemoryInputTokenizer

public class MemoryInputSynapse extends StreamInputSynapse
{

	static final long serialVersionUID = 0x1cacae0c1574edbaL;
	private double inputArray[][];

	public MemoryInputSynapse()
	{
	}

	protected void initInputStream()
		throws JooneRuntimeException
	{
		super.setTokens(new MemoryInputTokenizer(inputArray));
	}

	public void setInputArray(double inputArray[][])
	{
		this.inputArray = inputArray;
		initInputStream();
	}
}
