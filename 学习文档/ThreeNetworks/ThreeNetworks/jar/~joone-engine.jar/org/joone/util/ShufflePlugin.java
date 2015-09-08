// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ShufflePlugin.java

package org.joone.util;

import java.util.Vector;
import org.joone.engine.Pattern;

// Referenced classes of package org.joone.util:
//			ConverterPlugIn

public class ShufflePlugin extends ConverterPlugIn
{

	static final long serialVersionUID = 0x62ca22433911f35aL;

	public ShufflePlugin()
	{
		setApplyEveryCycle(true);
		setAdvancedSerieSelector("1");
	}

	protected boolean convert(int serie)
	{
		return false;
	}

	protected boolean applyOnRows()
	{
		Vector inputVect = getInputVector();
		int s = inputVect.size();
		if (s <= 1)
			return false;
		for (int i = s - 1; i > 0; i--)
		{
			int n = Math.round((float)(Math.random() * (double)(i + 1) - 0.5D));
			if (n != i)
			{
				Pattern p1 = (Pattern)inputVect.elementAt(i);
				Pattern p2 = (Pattern)inputVect.elementAt(n);
				inputVect.set(n, p1);
				inputVect.set(i, p2);
			}
		}

		return true;
	}
}
