// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultipleInputSynapse.java

package org.joone.io;

import java.io.IOException;
import java.util.Vector;
import org.joone.engine.Pattern;

// Referenced classes of package org.joone.io:
//			InputSwitchSynapse, StreamInputSynapse, InputConnector

public class MultipleInputSynapse extends InputSwitchSynapse
{

	private int currentInput;
	private int currentPatt;

	public MultipleInputSynapse()
	{
		currentInput = 0;
		currentPatt = 0;
	}

	public Pattern fwdGet()
	{
		super.setActiveSynapse((StreamInputSynapse)inputs.get(currentInput));
		Pattern patt = super.fwdGet();
		return elaboratePattern(patt);
	}

	public Pattern fwdGet(InputConnector conn)
	{
		StreamInputSynapse myInput = null;
		int myFirstRow = conn.getFirstRow();
		int myCurrentRow = conn.getCurrentRow();
		int myLastRow = conn.getLastRow();
		for (int i = 0; i < inputs.size(); i++)
		{
			myInput = (StreamInputSynapse)inputs.elementAt(i);
			int myNumberOfPatterns = getNumberOfPatterns(myInput);
			if (myFirstRow > myNumberOfPatterns)
			{
				myFirstRow -= myNumberOfPatterns;
				myCurrentRow -= myNumberOfPatterns;
				if (myLastRow > 0)
					myLastRow -= myNumberOfPatterns;
			} else
			if (myCurrentRow > myNumberOfPatterns)
			{
				myFirstRow = 1;
				myCurrentRow -= myNumberOfPatterns;
				if (myLastRow > 0)
					myLastRow -= myNumberOfPatterns;
			} else
			{
				if (myLastRow > myNumberOfPatterns)
					myLastRow = 0;
				InputConnector myInputConnector = new InputConnector();
				myInputConnector.setFirstRow(myFirstRow);
				myInputConnector.setCurrentRow(myCurrentRow);
				myInputConnector.setLastRow(myLastRow);
				setActiveSynapse(myInput);
				Pattern myPattern = super.fwdGet(myInputConnector);
				if (!myPattern.isMarkedAsStoppingPattern())
					myPattern.setCount(conn.getCurrentRow() - conn.getFirstRow());
				return myPattern;
			}
		}

		return null;
	}

	protected int getNumberOfPatterns(StreamInputSynapse anInputSynpase)
	{
		if (anInputSynpase.getLastRow() != 0)
			return (anInputSynpase.getLastRow() - anInputSynpase.getFirstRow()) + 1;
		if (anInputSynpase.getInputVector().size() != 0)
			return anInputSynpase.getInputVector().size();
		if (!anInputSynpase.isBuffered())
			anInputSynpase.setBuffered(true);
		anInputSynpase.readAll();
		return anInputSynpase.getInputVector().size();
	}

	private Pattern elaboratePattern(Pattern patt)
	{
		int count = patt.getCount();
		if (patt.isMarkedAsStoppingPattern())
		{
			currentInput = currentPatt = 0;
		} else
		{
			patt.setCount(++currentPatt);
			if (getActiveSynapse().isEOF())
			{
				currentInput++;
				if (currentInput == inputs.size())
					currentInput = currentPatt = 0;
			}
		}
		return patt;
	}

	public void reset()
	{
		super.reset();
		currentInput = currentPatt = 0;
	}

	public void setBuffered(boolean newBuffered)
	{
		for (int i = 0; i < inputs.size(); i++)
		{
			StreamInputSynapse myInput = (StreamInputSynapse)inputs.elementAt(i);
			myInput.setBuffered(newBuffered);
		}

	}

	public void gotoLine(int aNumLine)
		throws IOException
	{
		StreamInputSynapse myInput = null;
		for (int i = 0; i < inputs.size(); i++)
		{
			myInput = (StreamInputSynapse)inputs.elementAt(i);
			int myNumberOfPatterns = getNumberOfPatterns(myInput);
			if (aNumLine < (myNumberOfPatterns + myInput.getFirstRow()) - 1)
			{
				setActiveSynapse(myInput);
				myInput.gotoLine(aNumLine);
				return;
			}
			aNumLine -= myNumberOfPatterns;
		}

	}

	public void setFirstRow(int newFirstRow)
	{
		throw new UnsupportedOperationException("Control the first row through the underlying source or any connected input connectors");
	}

	public void setLastRow(int newLastRow)
	{
		throw new UnsupportedOperationException("Control the last row through the underlying source or any connected input connectors");
	}
}
