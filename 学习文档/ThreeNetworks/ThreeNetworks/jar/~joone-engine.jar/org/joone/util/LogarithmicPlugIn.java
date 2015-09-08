// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LogarithmicPlugIn.java

package org.joone.util;

import java.util.Vector;
import org.joone.engine.Pattern;

// Referenced classes of package org.joone.util:
//			ConverterPlugIn

public class LogarithmicPlugIn extends ConverterPlugIn
{

	private static final long serialVersionUID = 0x40b5bbaf431bac18L;

	public LogarithmicPlugIn()
	{
	}

	protected boolean convert(int serie)
	{
		int s = getInputVector().size();
		double v = 0.0D;
		double result = 0.0D;
		Pattern currPE = null;
		for (int i = 0; i < s; i++)
		{
			v = getValuePoint(i, serie);
			currPE = (Pattern)getInputVector().elementAt(i);
			if (v >= 0.0D)
				result = Math.log(1.0D + v);
			else
				result = -Math.log(1.0D - v);
			currPE.setValue(serie, result);
		}

		return true;
	}
}
