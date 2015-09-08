// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Connection.java

package org.joone.helpers.structure;

import org.joone.engine.Synapse;

public class Connection
{

	Synapse synapse;
	int input;
	int output;
	int inpIndex;
	int outIndex;

	public Connection()
	{
	}

	public int getInput()
	{
		return input;
	}

	public void setInput(int input)
	{
		this.input = input;
	}

	public int getOutput()
	{
		return output;
	}

	public void setOutput(int output)
	{
		this.output = output;
	}

	public int getInpIndex()
	{
		return inpIndex;
	}

	public void setInpIndex(int inpIndex)
	{
		this.inpIndex = inpIndex;
	}

	public int getOutIndex()
	{
		return outIndex;
	}

	public void setOutIndex(int outIndex)
	{
		this.outIndex = outIndex;
	}

	public Synapse getSynapse()
	{
		return synapse;
	}

	public void setSynapse(Synapse synapse)
	{
		this.synapse = synapse;
	}
}
