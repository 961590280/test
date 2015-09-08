// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OutputConverterPlugIn.java

package org.joone.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import org.joone.engine.Pattern;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.util:
//			AbstractConverterPlugIn, OutputPluginListener

public abstract class OutputConverterPlugIn extends AbstractConverterPlugIn
{

	private transient Pattern conv_pattern;
	private static final long serialVersionUID = 0x1792536133cae936L;
	private static final ILogger log = LoggerFactory.getLogger(org/joone/util/OutputConverterPlugIn);

	public OutputConverterPlugIn()
	{
	}

	public OutputConverterPlugIn(String anAdvancedSerieSelector)
	{
		super(anAdvancedSerieSelector);
	}

	protected abstract void convert_pattern(int i);

	public void convertPattern()
		throws NumberFormatException
	{
		if (getAdvancedSerieSelector() != null && !getAdvancedSerieSelector().equals(""))
			try
			{
				int mySerieSelected[] = getSerieSelected();
				for (int i = 0; i < mySerieSelected.length; i++)
					convert_pattern(mySerieSelected[i] - 1);

			}
			catch (NumberFormatException nfe)
			{
				throw new NumberFormatException(nfe.getMessage());
			}
		else
			log.warn((new StringBuilder(String.valueOf(getName()))).append(" : Advanced Serie Selector not populated therefore converting no data.").toString());
		if (getNextPlugIn() != null)
		{
			OutputConverterPlugIn myPlugIn = (OutputConverterPlugIn)getNextPlugIn();
			myPlugIn.setPattern(getPattern());
			myPlugIn.convertPattern();
		}
	}

	/**
	 * @deprecated Method convertAllPatterns is deprecated
	 */

	public void convertAllPatterns()
		throws NumberFormatException
	{
		convertPatterns();
	}

	protected Pattern getPattern()
	{
		return conv_pattern;
	}

	public void setPattern(Pattern newPattern)
	{
		conv_pattern = newPattern;
	}

	/**
	 * @deprecated Method addOutputPluginListener is deprecated
	 */

	public synchronized void addOutputPluginListener(OutputPluginListener aListener)
	{
		addPlugInListener(aListener);
	}

	/**
	 * @deprecated Method removeOutputPluginListener is deprecated
	 */

	public synchronized void removeOutputPluginListener(OutputPluginListener aListener)
	{
		removePlugInListener(aListener);
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
			String name = (new StringBuilder("OutputPlugin autogenName=")).append(val).toString();
			log.warn((new StringBuilder("Name not set in readed plugin. Setting it to '")).append(name).append("'").toString());
			setName(name);
		}
	}

}
