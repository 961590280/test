// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StreamOutputSynapse.java

package org.joone.io;

import java.util.TreeSet;
import java.util.Vector;
import org.joone.engine.*;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.util.*;

public abstract class StreamOutputSynapse extends Synapse
	implements PlugInListener
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/io/StreamOutputSynapse);
	private char separator;
	private static final long serialVersionUID = 0x65ed8f8f56fde7a1L;
	protected transient Fifo fifo;
	private boolean buffered;
	private transient Vector buffered_patterns;
	protected OutputConverterPlugIn nextPlugIn;

	public StreamOutputSynapse()
	{
		separator = ';';
		buffered = true;
		nextPlugIn = null;
	}

	protected void backward(double ad[])
	{
	}

	protected void forward(double pattern[])
	{
		outs = pattern;
	}

	public synchronized void fwdPut(Pattern pattern)
	{
		if (isEnabled())
			try
			{
				if (isBuffered())
				{
					if (!pattern.isMarkedAsStoppingPattern())
					{
						m_pattern = pattern;
						inps = pattern.getArray();
						forward(inps);
						m_pattern.setArray(outs);
						getFifo().push(m_pattern.clone());
						items = fifo.size();
					} else
					{
						getFifo().push(pattern.clone());
						items = fifo.size();
						int num_patterns = fifo.size();
						buffered_patterns = new Vector();
						for (int i = 0; i < num_patterns; i++)
							buffered_patterns.addElement(((Pattern)fifo.pop()).clone());

						if (nextPlugIn != null)
						{
							nextPlugIn.setInputVector(buffered_patterns);
							nextPlugIn.convertPatterns();
						}
						write(buffered_patterns);
					}
				} else
				{
					if (pattern.getCount() > -1)
					{
						inps = pattern.getArray();
						forward(inps);
						items = 1;
						if (nextPlugIn != null)
						{
							nextPlugIn.setPattern(pattern);
							nextPlugIn.convertPattern();
						}
					}
					write(pattern);
				}
			}
			catch (NumberFormatException nfe)
			{
				String error = (new StringBuilder("NumberFormatException in ")).append(getName()).append(". Message is : ").toString();
				log.warn((new StringBuilder(String.valueOf(error))).append(nfe.getMessage()).toString());
				if (getMonitor() != null)
					new NetErrorManager(getMonitor(), (new StringBuilder(String.valueOf(error))).append(nfe.getMessage()).toString());
			}
		notifyAll();
	}

	private void write(Vector patterns)
	{
		if (patterns != null)
		{
			for (int i = 0; i < patterns.size(); i++)
				write((Pattern)patterns.elementAt(i));

		}
	}

	public abstract void write(Pattern pattern);

	public char getSeparator()
	{
		return separator;
	}

	public synchronized Pattern revGet()
	{
		return null;
	}

	protected void setArrays(int i, int j)
	{
	}

	protected void setDimensions(int i, int j)
	{
	}

	public void setSeparator(char newSeparator)
	{
		separator = newSeparator;
	}

	public void setBuffered(boolean buf)
	{
		buffered = buf;
	}

	public boolean isBuffered()
	{
		return buffered;
	}

	public TreeSet check()
	{
		TreeSet checks = super.check();
		if (nextPlugIn != null)
			nextPlugIn.check(checks);
		return checks;
	}

	protected Fifo getFifo()
	{
		if (fifo == null)
			fifo = new Fifo();
		return fifo;
	}

	public OutputConverterPlugIn getPlugIn()
	{
		return nextPlugIn;
	}

	/**
	 * @deprecated Method setPlugIn is deprecated
	 */

	public boolean setPlugIn(OutputConverterPlugIn newPlugIn)
	{
		if (newPlugIn == nextPlugIn)
			return false;
		if (newPlugIn == null)
		{
			nextPlugIn.setConnected(false);
		} else
		{
			if (newPlugIn.isConnected())
				return false;
			newPlugIn.setConnected(true);
			newPlugIn.addPlugInListener(this);
			buffered = true;
		}
		nextPlugIn = newPlugIn;
		getFifo().removeAllElements();
		return true;
	}

	public boolean addPlugIn(OutputConverterPlugIn aNewPlugIn)
	{
		if (nextPlugIn == aNewPlugIn)
			return false;
		if (aNewPlugIn == null)
		{
			if (nextPlugIn != null)
				nextPlugIn.setConnected(false);
			nextPlugIn = null;
			getFifo().removeAllElements();
			return true;
		}
		if (aNewPlugIn.isConnected())
			return false;
		if (nextPlugIn == null)
		{
			aNewPlugIn.setConnected(true);
			aNewPlugIn.addPlugInListener(this);
			setBuffered(true);
			nextPlugIn = aNewPlugIn;
			getFifo().removeAllElements();
			return true;
		} else
		{
			return nextPlugIn.addPlugIn(aNewPlugIn);
		}
	}

	public void removeAllPlugIns()
	{
		if (nextPlugIn != null)
		{
			nextPlugIn.setConnected(false);
			nextPlugIn.removeAllPlugIns();
			nextPlugIn = null;
		}
	}

	public void dataChanged(PlugInEvent data)
	{
		getFifo().removeAllElements();
	}

}
