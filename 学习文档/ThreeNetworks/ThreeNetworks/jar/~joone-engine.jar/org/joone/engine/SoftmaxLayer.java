// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SoftmaxLayer.java

package org.joone.engine;


// Referenced classes of package org.joone.engine:
//			LinearLayer

public class SoftmaxLayer extends LinearLayer
{

	private static final long serialVersionUID = 0x1f211ffd9ee950fbL;

	public SoftmaxLayer()
	{
	}

	public void forward(double pattern[])
	{
		int n = getRows();
		double sum = 0.0D;
		for (int x = 0; x < n; x++)
		{
			outs[x] = Math.exp(getBeta() * pattern[x]);
			sum += outs[x];
		}

		for (int x = 0; x < n; x++)
			outs[x] = outs[x] / sum;

	}
}
