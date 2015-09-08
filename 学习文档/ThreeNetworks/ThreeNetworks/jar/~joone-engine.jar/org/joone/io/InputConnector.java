// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InputConnector.java

package org.joone.io;

import java.util.TreeSet;
import java.util.Vector;
import org.joone.engine.Monitor;
import org.joone.engine.Pattern;
import org.joone.exception.JooneRuntimeException;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NetCheck;
import org.joone.util.PlugInEvent;
import org.joone.util.PlugInListener;

// Referenced classes of package org.joone.io:
//			StreamInputSynapse

public class InputConnector extends StreamInputSynapse
	implements PlugInListener
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/io/InputConnector);
	private static final long serialVersionUID = 0xd1fa4205c10c5e21L;
	private StreamInputSynapse inputSynapse;

	public InputConnector()
	{
		setBuffered(false);
	}

	protected void initInputStream()
		throws JooneRuntimeException
	{
		currentRow = getFirstRow();
		EOF = false;
	}

	public boolean setInputSynapse(StreamInputSynapse input)
	{
		if (input != null)
		{
			input.setMonitor(getMonitor());
			input.setBuffered(true);
			input.setStepCounter(false);
			input.setInputFull(true);
			input.addPlugInListener(this);
			setOutputFull(true);
		} else
		{
			setOutputFull(false);
			if (getInputSynapse() != null)
			{
				getInputSynapse().removePlugInListener(this);
				getInputSynapse().setInputFull(false);
			}
		}
		inputSynapse = input;
		return true;
	}

	protected Pattern getStream()
	{
		if (getInputSynapse() == null)
			return null;
		Pattern patt = getInputSynapse().fwdGet(this);
		if (patt == null)
			return null;
		if (cols == null)
			setColList();
		if (cols == null)
			return null;
		inps = new double[cols.length];
		for (int x = 0; x < cols.length; x++)
			inps[x] = patt.getArray()[cols[x] - 1];

		currentRow++;
		if (currentRow - getFirstRow() > getMonitor().getNumOfPatterns() - 1)
			EOF = true;
		if (getLastRow() > 0 && getCurrentRow() > getLastRow())
			EOF = true;
		forward(inps);
		Pattern newPattern = new Pattern(outs);
		newPattern.setCount(getCurrentRow() - getFirstRow());
		return newPattern;
	}

	public void setMonitor(Monitor newMonitor)
	{
		super.setMonitor(newMonitor);
		if (getInputSynapse() != null)
			getInputSynapse().setMonitor(newMonitor);
	}

	public void dataChanged(PlugInEvent anEvent)
	{
		if (isBuffered())
			getInputVector().removeAllElements();
	}

	public TreeSet check()
	{
		TreeSet checks = super.check();
		if (getInputSynapse() == null)
			checks.add(new NetCheck(0, "Input Synapse not set", this));
		else
		if (!getInputSynapse().isBuffered())
			checks.add(new NetCheck(0, "The Input Synapse must be buffered", this));
		return checks;
	}

	public void resetInput()
	{
		if (getInputSynapse() != null)
			getInputSynapse().resetInput();
		restart();
	}

	protected void setCurrentRow(int aRow)
	{
		currentRow = aRow;
	}

	public StreamInputSynapse getInputSynapse()
	{
		return inputSynapse;
	}

}
