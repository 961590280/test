// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MovingAveragePlugIn.java

package org.joone.util;

import java.util.Vector;
import org.joone.engine.Pattern;

// Referenced classes of package org.joone.util:
//			ConverterPlugIn, CSVParser

public class MovingAveragePlugIn extends ConverterPlugIn
{

	static final long serialVersionUID = 0xb12eb7f8e62dabfdL;
	private String AdvancedMovAvgSpec;

	public MovingAveragePlugIn()
	{
		AdvancedMovAvgSpec = "";
	}

	public MovingAveragePlugIn(String newAdvSerieSel, String newMovAvgSpec)
	{
		AdvancedMovAvgSpec = "";
		setAdvancedMovAvgSpec(newMovAvgSpec);
		setAdvancedSerieSelector(newAdvSerieSel);
	}

	protected boolean convert(int serie)
	{
		boolean retValue = false;
		int s = getInputVector().size();
		double CurrentMovingAvgerage = 0.0D;
		double Sum = 0.0D;
		int MovingAverageSpec = 0;
		int CurrentItem = 0;
		CSVParser MovParse = new CSVParser(AdvancedMovAvgSpec, false);
		int MovAvgArray[] = MovParse.parseInt();
		int index = getSerieIndexNumber(serie);
		if (index > -1 && index < MovAvgArray.length)
			MovingAverageSpec = MovAvgArray[index];
		if (MovingAverageSpec > 0 && getInputVector().size() > MovingAverageSpec)
		{
			Sum = 0.0D;
			CurrentMovingAvgerage = 0.0D;
			for (int i = getInputVector().size() - 1; i > -1; i--)
			{
				Pattern currPE = (Pattern)getInputVector().elementAt(i);
				if (i < MovingAverageSpec - 1)
				{
					CurrentMovingAvgerage = 0.0D;
				} else
				{
					Sum = 0.0D;
					for (int j = i; j > i - MovingAverageSpec; j--)
						Sum += getValuePoint(j, serie);

					CurrentMovingAvgerage = Sum / (double)MovingAverageSpec;
				}
				currPE.setValue(serie, CurrentMovingAvgerage);
				retValue = true;
			}

		}
		return retValue;
	}

	public String getAdvancedMovAvgSpec()
	{
		return AdvancedMovAvgSpec;
	}

	public void setAdvancedMovAvgSpec(String newAdvancedMovAvgSpec)
	{
		if (AdvancedMovAvgSpec.compareTo(newAdvancedMovAvgSpec) != 0)
		{
			AdvancedMovAvgSpec = newAdvancedMovAvgSpec;
			fireDataChanged();
		}
	}
}
