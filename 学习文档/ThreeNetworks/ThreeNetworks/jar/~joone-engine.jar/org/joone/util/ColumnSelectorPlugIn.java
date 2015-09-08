// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ColumnSelectorPlugIn.java

package org.joone.util;

import java.util.Vector;
import org.joone.engine.Pattern;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.util:
//			ConverterPlugIn

public class ColumnSelectorPlugIn extends ConverterPlugIn
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/util/ColumnSelectorPlugIn);

	public ColumnSelectorPlugIn()
	{
	}

	public ColumnSelectorPlugIn(String anAdvancedSerieSelector)
	{
		super(anAdvancedSerieSelector);
	}

	protected boolean convert(int serie)
	{
		return false;
	}

	protected boolean applyOnRows()
	{
		boolean retValue = false;
		if (getAdvancedSerieSelector() != null && !getAdvancedSerieSelector().equals(""))
		{
			int mySerieSelected[] = getSerieSelected();
			for (int i = 0; i < getInputVector().size(); i++)
			{
				double mySelected[] = new double[mySerieSelected.length];
				Pattern myPattern = (Pattern)getInputVector().get(i);
				for (int j = 0; j < mySerieSelected.length; j++)
					if (mySerieSelected[j] - 1 < myPattern.getArray().length)
						mySelected[j] = myPattern.getArray()[mySerieSelected[j] - 1];
					else
						log.warn((new StringBuilder(String.valueOf(getName()))).append(" : Advanced Serie Selector contains too many serie. ").append("Check the number of columns in the appropriate input synapse or previous plug in.").toString());

				myPattern.setArray(mySelected);
				retValue = true;
			}

		}
		return retValue;
	}

}
