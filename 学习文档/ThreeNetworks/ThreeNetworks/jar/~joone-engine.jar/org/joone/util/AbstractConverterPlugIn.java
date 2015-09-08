// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AbstractConverterPlugIn.java

package org.joone.util;

import java.io.*;
import java.util.TreeSet;
import java.util.Vector;
import org.joone.engine.Pattern;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NetCheck;

// Referenced classes of package org.joone.util:
//			PlugInListener, PlugInEvent, CSVParser

public abstract class AbstractConverterPlugIn
	implements Serializable, PlugInListener
{

	private static final long serialVersionUID = 0x4f152e2fd1594f0eL;
	private static final ILogger log = LoggerFactory.getLogger(org/joone/util/AbstractConverterPlugIn);
	private AbstractConverterPlugIn nextPlugIn;
	private String name;
	private boolean connected;
	protected Vector pluginListeners;
	private transient Vector InputVector;
	private String AdvancedSerieSelector;
	private transient int serieSelected[];

	public AbstractConverterPlugIn()
	{
		nextPlugIn = null;
		AdvancedSerieSelector = "";
	}

	public AbstractConverterPlugIn(String anAdvancedSerieSelector)
	{
		nextPlugIn = null;
		AdvancedSerieSelector = "";
		setAdvancedSerieSelector(anAdvancedSerieSelector);
	}

	public void convertPatterns()
	{
		apply();
		cascade();
	}

	protected boolean apply()
	{
		boolean retValue = false;
		Vector inputVect = getInputVector();
		if (inputVect != null && !inputVect.isEmpty())
			retValue = applyOnColumns() | applyOnRows();
		else
			log.warn((new StringBuilder(String.valueOf(getName()))).append(" : Plugin has no input data to convert.").toString());
		return retValue;
	}

	protected boolean applyOnColumns()
	{
		boolean retValue = false;
		Pattern currPE = (Pattern)getInputVector().elementAt(0);
		int aSize = currPE.getArray().length;
		if (getAdvancedSerieSelector() != null && !getAdvancedSerieSelector().equals(""))
		{
			int mySerieSelected[] = getSerieSelected();
			for (int i = 0; i < mySerieSelected.length; i++)
				if (mySerieSelected[i] - 1 < aSize)
					retValue = convert(mySerieSelected[i] - 1) | retValue;
				else
					log.warn((new StringBuilder(String.valueOf(getName()))).append(" : Advanced Serie Selector contains too many serie. Check the number of columns in the appropriate input synapse.").toString());

		}
		return retValue;
	}

	protected boolean applyOnRows()
	{
		return false;
	}

	protected void cascade()
	{
		if (getNextPlugIn() != null)
		{
			AbstractConverterPlugIn myPlugIn = getNextPlugIn();
			myPlugIn.setInputVector(getInputVector());
			myPlugIn.convertPatterns();
		}
	}

	protected abstract boolean convert(int i);

	protected double getValuePoint(int point, int serie)
	{
		Pattern currPE = (Pattern)getInputVector().elementAt(point);
		return currPE.getArray()[serie];
	}

	public String getName()
	{
		return name;
	}

	public void setName(String aName)
	{
		name = aName;
	}

	public boolean isConnected()
	{
		return connected;
	}

	public void setConnected(boolean aConnected)
	{
		connected = aConnected;
	}

	public synchronized void addPlugInListener(PlugInListener aListener)
	{
		if (!getPluginListeners().contains(aListener))
			getPluginListeners().add(aListener);
	}

	public synchronized void removePlugInListener(PlugInListener aListener)
	{
		if (getPluginListeners().contains(aListener))
			getPluginListeners().remove(aListener);
	}

	protected Vector getPluginListeners()
	{
		if (pluginListeners == null)
			pluginListeners = new Vector();
		return pluginListeners;
	}

	public void dataChanged(PlugInEvent anEvent)
	{
		fireDataChanged();
	}

	protected void fireDataChanged()
	{
		Object myList[];
		synchronized (this)
		{
			myList = getPluginListeners().toArray();
		}
		for (int i = 0; i < myList.length; i++)
		{
			PlugInListener myListener = (PlugInListener)myList[i];
			if (myListener != null)
				myListener.dataChanged(new PlugInEvent(this));
		}

	}

	public String getAdvancedSerieSelector()
	{
		return AdvancedSerieSelector;
	}

	public void setAdvancedSerieSelector(String aNewSerieSelector)
	{
		if (AdvancedSerieSelector == null || !AdvancedSerieSelector.equals(aNewSerieSelector))
		{
			AdvancedSerieSelector = aNewSerieSelector;
			serieSelected = null;
			fireDataChanged();
		}
	}

	protected int[] getSerieSelected()
	{
		if (serieSelected == null)
		{
			CSVParser myParser = new CSVParser(getAdvancedSerieSelector(), true);
			serieSelected = myParser.parseInt();
		}
		return serieSelected;
	}

	public boolean addPlugIn(AbstractConverterPlugIn aNewPlugIn)
	{
		if (nextPlugIn == aNewPlugIn)
		{
			log.warn("Plugin already connected to plugin stack. Ignoring request.");
			return false;
		}
		if (aNewPlugIn == null)
		{
			if (nextPlugIn != null)
				nextPlugIn.setConnected(false);
			nextPlugIn = null;
			fireDataChanged();
			return true;
		}
		if (aNewPlugIn.isConnected())
			return false;
		if (nextPlugIn == null)
		{
			aNewPlugIn.setConnected(true);
			aNewPlugIn.addPlugInListener(this);
			nextPlugIn = aNewPlugIn;
			fireDataChanged();
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
		} else
		{
			fireDataChanged();
		}
	}

	/**
	 * @deprecated Method setNextPlugin is deprecated
	 */

	public boolean setNextPlugin(AbstractConverterPlugIn aNewNextPlugIn)
	{
		if (aNewNextPlugIn == nextPlugIn)
			return false;
		if (aNewNextPlugIn == null)
		{
			nextPlugIn.setConnected(false);
		} else
		{
			if (aNewNextPlugIn.isConnected())
				return false;
			aNewNextPlugIn.setConnected(true);
			aNewNextPlugIn.addPlugInListener(this);
		}
		nextPlugIn = aNewNextPlugIn;
		fireDataChanged();
		return true;
	}

	public AbstractConverterPlugIn getNextPlugIn()
	{
		return nextPlugIn;
	}

	public void setNextPlugIn(AbstractConverterPlugIn newNextPlugIn)
	{
		addPlugIn(newNextPlugIn);
	}

	public void setInputVector(Vector newInputVector)
	{
		InputVector = newInputVector;
	}

	protected Vector getInputVector()
	{
		return InputVector;
	}

	public TreeSet check(TreeSet checks)
	{
		if (AdvancedSerieSelector == null || AdvancedSerieSelector.equals(""))
			checks.add(new NetCheck(0, "Advanced Serie Selector should be populated, e.g 1,2,4.", this));
		if (getNextPlugIn() != null)
			getNextPlugIn().check(checks);
		return checks;
	}

	protected int getSerieIndexNumber(int serie)
	{
		CSVParser Parse = new CSVParser(getAdvancedSerieSelector(), true);
		int checker[] = Parse.parseInt();
		for (int i = 0; i < checker.length; i++)
			if (checker[i] == serie + 1)
				return i;

		return -1;
	}

	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		if (getAdvancedSerieSelector() == null)
			setAdvancedSerieSelector("1");
	}

}
