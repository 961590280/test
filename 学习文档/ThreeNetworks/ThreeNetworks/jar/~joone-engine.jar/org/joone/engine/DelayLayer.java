// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DelayLayer.java

package org.joone.engine;

import java.util.*;
import org.joone.inspection.implementations.BiasInspection;
import org.joone.net.NetCheck;

// Referenced classes of package org.joone.engine:
//			MemoryLayer, Monitor

public class DelayLayer extends MemoryLayer
{

	private static final long serialVersionUID = 0x157aa85a9e14751dL;

	public DelayLayer()
	{
	}

	public DelayLayer(String ElemName)
	{
		super(ElemName);
	}

	protected void backward(double pattern[])
	{
		int length = getRows();
		for (int x = 0; x < length; x++)
		{
			int ncell = x;
			for (int y = 0; y < getTaps(); y++)
			{
				backmemory[ncell] = backmemory[ncell + length];
				backmemory[ncell] += pattern[ncell];
				ncell += length;
			}

			backmemory[ncell] = pattern[ncell];
			gradientOuts[x] = backmemory[x];
		}

	}

	protected void forward(double pattern[])
	{
		int length = getRows();
		for (int x = 0; x < length; x++)
		{
			int ncell = x + getTaps() * length;
			for (int y = getTaps(); y > 0; y--)
			{
				memory[ncell] = memory[ncell - length];
				outs[ncell] = memory[ncell];
				ncell -= length;
			}

			memory[x] = pattern[x];
			outs[x] = memory[x];
		}

	}

	public TreeSet check()
	{
		TreeSet checks = super.check();
		if (getTaps() == 0)
			checks.add(new NetCheck(0, "The Taps parameter cannot be equal to zero.", this));
		if (monitor != null && monitor.getPreLearning() != getTaps() + 1)
			checks.add(new NetCheck(1, "The correct value for the Monitor PreLearning parameter is Taps + 1", this));
		return checks;
	}

	public Collection Inspections()
	{
		Collection col = new ArrayList();
		col.add(new BiasInspection(null));
		return col;
	}
}
