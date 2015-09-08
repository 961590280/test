// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConverterPlugIn.java

package org.joone.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.util:
//			AbstractConverterPlugIn

public abstract class ConverterPlugIn extends AbstractConverterPlugIn
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/util/ConverterPlugIn);
	private static final long serialVersionUID = 0x1792536133cae936L;
	private boolean applyEveryCycle;

	public ConverterPlugIn()
	{
	}

	public ConverterPlugIn(String anAdvancedSerieSelector)
	{
		super(anAdvancedSerieSelector);
	}

	public boolean newCycle()
	{
		boolean retValue = false;
		if (isApplyEveryCycle())
			retValue = apply();
		if (getNextPlugIn() != null)
		{
			ConverterPlugIn myPlugIn = (ConverterPlugIn)getNextPlugIn();
			myPlugIn.setInputVector(getInputVector());
			retValue = myPlugIn.newCycle() | retValue;
		}
		return retValue;
	}

	public boolean isApplyEveryCycle()
	{
		return applyEveryCycle;
	}

	public void setApplyEveryCycle(boolean anApplyEveryCycle)
	{
		applyEveryCycle = anApplyEveryCycle;
	}

	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		if (getAdvancedSerieSelector() == null)
		{
			log.warn("AdvancedSerieSelector not set in readed plugin. Setting it to '1'");
			setAdvancedSerieSelector("1");
		}
		if (getName() == null)
		{
			long val = 1000L + Math.round(Math.random() * 999D);
			String name = (new StringBuilder("InputPlugin autogenName=")).append(val).toString();
			log.warn((new StringBuilder("Name not set in readed plugin. Setting it to '")).append(name).append("'").toString());
			setName(name);
		}
	}

}
