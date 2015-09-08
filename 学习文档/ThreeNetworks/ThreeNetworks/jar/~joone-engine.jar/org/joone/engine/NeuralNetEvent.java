// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NeuralNetEvent.java

package org.joone.engine;

import java.util.EventObject;
import org.joone.net.NeuralNet;

// Referenced classes of package org.joone.engine:
//			Monitor

public class NeuralNetEvent extends EventObject
{

	private static final long serialVersionUID = 0xdff8573a35940927L;
	private NeuralNet nnet;

	public NeuralNetEvent(Monitor source)
	{
		super(source);
	}

	public NeuralNet getNeuralNet()
	{
		return nnet;
	}

	public void setNeuralNet(NeuralNet nnet)
	{
		this.nnet = nnet;
	}
}
