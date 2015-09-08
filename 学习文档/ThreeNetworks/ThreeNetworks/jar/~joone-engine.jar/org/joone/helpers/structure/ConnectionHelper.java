// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConnectionHelper.java

package org.joone.helpers.structure;

import java.util.Vector;
import org.joone.engine.*;
import org.joone.engine.learning.ComparingElement;
import org.joone.io.*;
import org.joone.util.*;

public class ConnectionHelper
{

	public ConnectionHelper()
	{
	}

	public static boolean canConnect(Object source, Object target)
	{
		boolean retValue = false;
		if (source == target)
			return false;
		if (target instanceof InputConnector)
		{
			if (source instanceof LearningSwitch)
			{
				if (((LearningSwitch)source).getValidationSet() == null && !((StreamInputSynapse)target).isInputFull())
					retValue = true;
				return retValue;
			}
			if (!((InputConnector)target).isOutputFull() && (source instanceof StreamInputSynapse))
				retValue = true;
			return retValue;
		}
		if (target instanceof LearningSwitch)
		{
			if (((LearningSwitch)target).getTrainingSet() == null && (source instanceof StreamInputSynapse) && !((StreamInputSynapse)source).isInputFull())
				retValue = true;
			return retValue;
		}
		if (target instanceof InputSwitchSynapse)
		{
			if ((source instanceof StreamInputSynapse) && !((StreamInputSynapse)source).isInputFull())
				retValue = true;
			return retValue;
		}
		if (target instanceof Layer)
		{
			if (source instanceof Layer)
				retValue = true;
			if ((source instanceof InputPatternListener) && !(source instanceof StreamOutputSynapse) && !((InputPatternListener)source).isInputFull())
				retValue = true;
			return retValue;
		}
		if (target instanceof StreamInputSynapse)
		{
			if ((source instanceof LearningSwitch) && ((LearningSwitch)source).getValidationSet() == null && !((StreamInputSynapse)target).isInputFull())
				retValue = true;
			if ((source instanceof ConverterPlugIn) && !((ConverterPlugIn)source).isConnected())
				retValue = true;
			return retValue;
		}
		if (target instanceof StreamOutputSynapse)
		{
			StreamOutputSynapse sos = (StreamOutputSynapse)target;
			if (!sos.isOutputFull())
			{
				if (source instanceof Layer)
					retValue = true;
				if (source instanceof ComparingElement)
					retValue = true;
				if ((source instanceof OutputConverterPlugIn) && !((OutputConverterPlugIn)source).isConnected())
					retValue = true;
				if (source instanceof OutputSwitchSynapse)
					retValue = true;
			}
			return retValue;
		}
		if (target instanceof ComparingElement)
		{
			if ((source instanceof Layer) && !((ComparingElement)target).isOutputFull())
				retValue = true;
			if ((source instanceof StreamInputSynapse) && ((ComparingElement)target).getDesired() == null && !((StreamInputSynapse)source).isInputFull())
				retValue = true;
			return retValue;
		}
		if (target instanceof AbstractConverterPlugIn)
		{
			if ((source instanceof ConverterPlugIn) && !((ConverterPlugIn)source).isConnected())
				retValue = true;
			return retValue;
		}
		if (target instanceof OutputSwitchSynapse)
		{
			OutputSwitchSynapse oss = (OutputSwitchSynapse)target;
			if (!oss.isOutputFull())
			{
				if (source instanceof Layer)
					retValue = true;
				if (source instanceof ComparingElement)
					retValue = true;
				if (source instanceof OutputSwitchSynapse)
					retValue = true;
			}
		}
		return retValue;
	}

	public static boolean connect(Object source, Object media, Object target)
	{
		boolean retValue = false;
		if (target instanceof InputConnector)
		{
			if (source instanceof LearningSwitch)
				return ((LearningSwitch)source).addValidationSet((StreamInputSynapse)target);
			if (source instanceof StreamInputSynapse)
				retValue = ((InputConnector)target).setInputSynapse((StreamInputSynapse)source);
			return retValue;
		}
		if (target instanceof LearningSwitch)
		{
			if (source instanceof StreamInputSynapse)
				retValue = ((LearningSwitch)target).addTrainingSet((StreamInputSynapse)source);
			return retValue;
		}
		if (target instanceof InputSwitchSynapse)
		{
			if (source instanceof StreamInputSynapse)
				retValue = ((InputSwitchSynapse)target).addInputSynapse((StreamInputSynapse)source);
			return retValue;
		}
		if (target instanceof Layer)
		{
			retValue = connectToLayer(source, media, (Layer)target);
			return retValue;
		}
		if (target instanceof StreamInputSynapse)
		{
			if (source instanceof LearningSwitch)
				retValue = ((LearningSwitch)source).addValidationSet((StreamInputSynapse)target);
			if (source instanceof ConverterPlugIn)
				retValue = ((StreamInputSynapse)target).addPlugIn((ConverterPlugIn)source);
			return retValue;
		}
		if (target instanceof StreamOutputSynapse)
		{
			retValue = connectToStreamOutputSynapse(source, (StreamOutputSynapse)target);
			return retValue;
		}
		if (target instanceof ComparingElement)
		{
			retValue = connectToComparingElement(source, (ComparingElement)target);
			return retValue;
		}
		if (target instanceof AbstractConverterPlugIn)
		{
			if (source instanceof ConverterPlugIn)
				retValue = ((AbstractConverterPlugIn)target).addPlugIn((ConverterPlugIn)source);
			return retValue;
		}
		if (target instanceof OutputSwitchSynapse)
			retValue = connectToOutputSwitchSynapse(source, (OutputSwitchSynapse)target);
		return retValue;
	}

	private static boolean connectToLayer(Object source, Object media, Layer target)
	{
		boolean retValue = false;
		if ((source instanceof Layer) && media != null && (media instanceof Synapse) && ((Layer)source).addOutputSynapse((Synapse)media))
			retValue = target.addInputSynapse((Synapse)media);
		if (source instanceof InputPatternListener)
			retValue = target.addInputSynapse((InputPatternListener)source);
		return retValue;
	}

	private static boolean connectToStreamOutputSynapse(Object source, StreamOutputSynapse target)
	{
		boolean retValue = false;
		if (source instanceof Layer)
			retValue = ((Layer)source).addOutputSynapse(target);
		if (source instanceof ComparingElement)
			retValue = ((ComparingElement)source).addResultSynapse(target);
		if (source instanceof OutputConverterPlugIn)
			retValue = target.addPlugIn((OutputConverterPlugIn)source);
		if (source instanceof OutputSwitchSynapse)
			retValue = ((OutputSwitchSynapse)source).addOutputSynapse(target);
		return retValue;
	}

	private static boolean connectToComparingElement(Object source, ComparingElement target)
	{
		boolean retValue = false;
		if (source instanceof Layer)
			retValue = ((Layer)source).addOutputSynapse(target);
		if (source instanceof StreamInputSynapse)
			retValue = target.setDesired((StreamInputSynapse)source);
		return retValue;
	}

	private static boolean connectToOutputSwitchSynapse(Object source, OutputSwitchSynapse target)
	{
		boolean retValue = false;
		if (source instanceof Layer)
			retValue = ((Layer)source).addOutputSynapse(target);
		if (source instanceof ComparingElement)
			retValue = ((ComparingElement)source).addResultSynapse(target);
		if (source instanceof OutputSwitchSynapse)
			retValue = ((OutputSwitchSynapse)source).addOutputSynapse(target);
		return retValue;
	}

	public static boolean disconnect(Object source, Object target)
	{
		boolean retValue = false;
		if (target instanceof InputConnector)
		{
			if (source instanceof StreamInputSynapse)
				retValue = ((InputConnector)target).setInputSynapse(null);
			return retValue;
		}
		if (target instanceof LearningSwitch)
		{
			if (source instanceof StreamInputSynapse)
			{
				if (((LearningSwitch)target).getTrainingSet() == source)
				{
					((LearningSwitch)target).removeTrainingSet();
					retValue = true;
				}
				if (((LearningSwitch)target).getValidationSet() == source)
				{
					((LearningSwitch)target).removeValidationSet();
					retValue = true;
				}
			}
			return retValue;
		}
		if (target instanceof InputSwitchSynapse)
		{
			if (source instanceof StreamInputSynapse)
				retValue = ((InputSwitchSynapse)target).removeInputSynapse(((StreamInputSynapse)source).getName());
			return retValue;
		}
		if (target instanceof Layer)
		{
			retValue = disconnectFromLayer(source, (Layer)target);
			return retValue;
		}
		if (target instanceof StreamInputSynapse)
		{
			if (source instanceof ConverterPlugIn)
				retValue = ((StreamInputSynapse)target).addPlugIn(null);
			return retValue;
		}
		if (target instanceof StreamOutputSynapse)
		{
			retValue = disconnectFromStreamOutputSynapse(source, (StreamOutputSynapse)target);
			return retValue;
		}
		if (target instanceof ComparingElement)
		{
			retValue = disconnectFromComparingElement(source, (ComparingElement)target);
			return retValue;
		}
		if (target instanceof AbstractConverterPlugIn)
		{
			if (source instanceof ConverterPlugIn)
				retValue = ((AbstractConverterPlugIn)target).addPlugIn(null);
			return retValue;
		}
		if (target instanceof OutputSwitchSynapse)
			retValue = disconnectFromOutputSwitchSynapse(source, (OutputSwitchSynapse)target);
		return retValue;
	}

	private static boolean disconnectFromLayer(Object source, Layer target)
	{
		boolean retValue = false;
		if (source instanceof Layer)
		{
			Object media = getConnection((Layer)source, target);
			if (media != null && (media instanceof Synapse))
			{
				((Layer)source).removeOutputSynapse((Synapse)media);
				target.removeInputSynapse((Synapse)media);
				retValue = true;
			}
		}
		if (source instanceof InputPatternListener)
		{
			target.removeInputSynapse((InputPatternListener)source);
			retValue = true;
		}
		return retValue;
	}

	private static boolean disconnectFromStreamOutputSynapse(Object source, StreamOutputSynapse target)
	{
		boolean retValue = false;
		if (source instanceof Layer)
		{
			((Layer)source).removeOutputSynapse(target);
			retValue = true;
		}
		if (source instanceof ComparingElement)
		{
			((ComparingElement)source).removeResultSynapse(target);
			retValue = true;
		}
		if (source instanceof OutputConverterPlugIn)
			retValue = target.addPlugIn(null);
		if (source instanceof OutputSwitchSynapse)
			retValue = ((OutputSwitchSynapse)source).removeOutputSynapse(target.getName());
		return retValue;
	}

	private static boolean disconnectFromComparingElement(Object source, ComparingElement target)
	{
		boolean retValue = false;
		if (source instanceof Layer)
		{
			((Layer)source).removeOutputSynapse(target);
			retValue = true;
		}
		if (source instanceof StreamInputSynapse)
			retValue = target.setDesired(null);
		return retValue;
	}

	private static boolean disconnectFromOutputSwitchSynapse(Object source, OutputSwitchSynapse target)
	{
		boolean retValue = false;
		if (source instanceof Layer)
		{
			((Layer)source).removeOutputSynapse(target);
			retValue = true;
		}
		if (source instanceof ComparingElement)
		{
			((ComparingElement)source).removeResultSynapse(target);
			retValue = true;
		}
		if (source instanceof OutputSwitchSynapse)
			retValue = ((OutputSwitchSynapse)source).removeOutputSynapse(target.getName());
		return retValue;
	}

	private static Object getConnection(Layer source, Layer target)
	{
		Object conn = null;
		Vector inps = target.getAllInputs();
		Vector outs = source.getAllOutputs();
		if (inps != null && inps.size() > 0 && outs != null && outs.size() > 0)
		{
			for (int i = 0; conn == null && i < inps.size(); i++)
			{
				Object cc = inps.elementAt(i);
				if (cc instanceof Synapse)
				{
					for (int u = 0; conn == null && u < outs.size(); u++)
						if (outs.elementAt(u) == cc)
							conn = cc;

				}
			}

		}
		return conn;
	}
}
