// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CenterOnZeroPlugIn.java

package org.joone.util;

import java.util.Vector;
import org.joone.engine.Pattern;

// Referenced classes of package org.joone.util:
//			ConverterPlugIn

public class CenterOnZeroPlugIn extends ConverterPlugIn
{

	private static final long serialVersionUID = 0x77189a286356a3ddL;

	public CenterOnZeroPlugIn()
	{
	}

	protected boolean convert(int serie)
	{
		boolean retValue = false;
		int s = getInputVector().size();
		double d = 0.0D;
		for (int i = 0; i < s; i++)
			d += getValuePoint(i, serie);

		d /= s;
		for (int i = 0; i < s; i++)
		{
			double v = getValuePoint(i, serie);
			v -= d;
			Pattern currPE = (Pattern)getInputVector().elementAt(i);
			currPE.setValue(serie, v);
			retValue = true;
		}

		return retValue;
	}
}
