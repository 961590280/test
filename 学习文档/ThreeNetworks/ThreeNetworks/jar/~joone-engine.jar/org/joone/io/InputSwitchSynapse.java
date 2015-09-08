// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InputSwitchSynapse.java

package org.joone.io;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import org.joone.engine.Monitor;
import org.joone.engine.Pattern;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.util.ConverterPlugIn;
import org.joone.util.PlugInEvent;

// Referenced classes of package org.joone.io:
//			StreamInputSynapse, InputConnector

public class InputSwitchSynapse extends StreamInputSynapse
	implements Serializable
{

	protected Vector inputs;
	private StreamInputSynapse activeSynapse;
	private StreamInputSynapse defaultSynapse;
	private String name;
	private Monitor mon;
	private int outputDimension;
	private static final long serialVersionUID = 0x7d425aa3faf96cb0L;
	private static final ILogger log = LoggerFactory.getLogger(org/joone/io/InputSwitchSynapse);

	public InputSwitchSynapse()
	{
		initSwitch();
		mon = null;
		outputDimension = 0;
	}

	private void initSwitch()
	{
		inputs = new Vector();
		activeSynapse = defaultSynapse = null;
	}

	public void init()
	{
		super.init();
		for (int i = 0; i < inputs.size(); i++)
		{
			StreamInputSynapse inp = (StreamInputSynapse)inputs.elementAt(i);
			inp.init();
		}

	}

	public void resetSwitch()
	{
		setActiveSynapse(getDefaultSynapse());
	}

	public void reset()
	{
		for (int i = 0; i < inputs.size(); i++)
		{
			StreamInputSynapse inp = (StreamInputSynapse)inputs.elementAt(i);
			inp.reset();
		}

	}

	public boolean removeInputSynapse(String inputName)
	{
		StreamInputSynapse sis = getInputSynapse(inputName);
		if (sis == null)
		{
			String nm = inputName != null ? inputName : "";
			log.warn((new StringBuilder("Synapse [")).append(nm).append("] not registered in switch. Ignoring removal request.").toString());
			return false;
		}
		inputs.removeElement(sis);
		sis.setInputFull(false);
		if (inputs.isEmpty())
		{
			setActiveInput("");
			setDefaultInput("");
		} else
		{
			if (getActiveInput().equalsIgnoreCase(inputName))
				setActiveSynapse((StreamInputSynapse)inputs.elementAt(0));
			if (getDefaultInput().equalsIgnoreCase(inputName))
				setDefaultSynapse((StreamInputSynapse)inputs.elementAt(0));
		}
		return true;
	}

	protected StreamInputSynapse getInputSynapse(String inputName)
	{
		for (int i = 0; i < inputs.size(); i++)
		{
			StreamInputSynapse inp = (StreamInputSynapse)inputs.elementAt(i);
			if (inp.getName().equalsIgnoreCase(inputName))
				return inp;
		}

		return null;
	}

	public boolean addInputSynapse(StreamInputSynapse newInput)
	{
		if (newInput == null)
		{
			log.warn("Null input synapse provided. Ignoring.");
			return false;
		}
		if (inputs.contains(newInput))
		{
			log.warn("Synapse already registered in switch. Ignoring.");
			return false;
		}
		if (newInput.isInputFull())
		{
			log.warn("Provided synapse is already connected. Ignoring.");
			return false;
		}
		inputs.addElement(newInput);
		newInput.setOutputDimension(outputDimension);
		newInput.setMonitor(mon);
		newInput.setStepCounter(super.isStepCounter());
		newInput.setInputFull(true);
		if (inputs.size() == 1)
		{
			setDefaultInput(newInput.getName());
			setActiveInput(newInput.getName());
		}
		return true;
	}

	public String getActiveInput()
	{
		if (activeSynapse == null)
			return "";
		else
			return activeSynapse.getName();
	}

	public void setActiveInput(String newActiveInput)
	{
		StreamInputSynapse inp = getInputSynapse(newActiveInput);
		if (inp == null)
		{
			String nm = newActiveInput != null ? newActiveInput : "";
			log.warn((new StringBuilder("Synapse [")).append(nm).append("] not registered in switch. Setting active synapse to empty value.").toString());
		}
		activeSynapse = inp;
	}

	public String getDefaultInput()
	{
		if (defaultSynapse == null)
			return "";
		else
			return defaultSynapse.getName();
	}

	public void setDefaultInput(String newDefaultInput)
	{
		StreamInputSynapse inp = getInputSynapse(newDefaultInput);
		if (inp == null)
		{
			String nm = newDefaultInput != null ? newDefaultInput : "";
			log.warn((new StringBuilder("Synapse [")).append(nm).append("] not registered in switch. Setting default synapse to empty value.").toString());
		}
		defaultSynapse = inp;
	}

	protected StreamInputSynapse getActiveSynapse()
	{
		return activeSynapse;
	}

	protected void setActiveSynapse(StreamInputSynapse activeSynapse)
	{
		this.activeSynapse = activeSynapse;
	}

	protected StreamInputSynapse getDefaultSynapse()
	{
		return defaultSynapse;
	}

	protected void setDefaultSynapse(StreamInputSynapse defaultSynapse)
	{
		this.defaultSynapse = defaultSynapse;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setOutputDimension(int newOutputDimension)
	{
		outputDimension = newOutputDimension;
		for (int i = 0; i < inputs.size(); i++)
		{
			StreamInputSynapse inp = (StreamInputSynapse)inputs.elementAt(i);
			inp.setOutputDimension(newOutputDimension);
		}

	}

	public void revPut(Pattern pattern)
	{
		if (activeSynapse == null)
			log.warn("Active synapse not set");
		else
			activeSynapse.revPut(pattern);
	}

	public Pattern fwdGet()
	{
		if (activeSynapse == null)
		{
			log.warn("Active synapse not set");
			return null;
		} else
		{
			return activeSynapse.fwdGet();
		}
	}

	public Pattern fwdGet(InputConnector conn)
	{
		if (activeSynapse == null)
		{
			log.warn("Active synapse not set");
			return null;
		} else
		{
			return activeSynapse.fwdGet(conn);
		}
	}

	public int getOutputDimension()
	{
		return outputDimension;
	}

	public Monitor getMonitor()
	{
		return mon;
	}

	public void setMonitor(Monitor newMonitor)
	{
		mon = newMonitor;
		for (int i = 0; i < inputs.size(); i++)
		{
			StreamInputSynapse inp = (StreamInputSynapse)inputs.elementAt(i);
			inp.setMonitor(newMonitor);
		}

	}

	protected void backward(double ad[])
	{
	}

	protected void forward(double ad[])
	{
	}

	public Vector getAllInputs()
	{
		return inputs;
	}

	public void setAllInputs(Vector inps)
	{
		inputs = inps;
		if (inputs != null)
		{
			for (int i = 0; i < inputs.size(); i++)
			{
				StreamInputSynapse inp = (StreamInputSynapse)inputs.elementAt(i);
				inp.setInputFull(true);
			}

		}
	}

	public void resetInput()
	{
		for (int i = 0; i < inputs.size(); i++)
		{
			StreamInputSynapse inp = (StreamInputSynapse)inputs.elementAt(i);
			inp.resetInput();
		}

		reset();
	}

	protected void initInputStream()
	{
	}

	public void setStepCounter(boolean newStepCounter)
	{
		super.setStepCounter(newStepCounter);
		for (int i = 0; i < inputs.size(); i++)
		{
			StreamInputSynapse inp = (StreamInputSynapse)inputs.elementAt(i);
			inp.setStepCounter(newStepCounter);
		}

	}

	public void gotoLine(int numLine)
		throws IOException
	{
		if (activeSynapse != null)
			activeSynapse.gotoLine(numLine);
	}

	public void dataChanged(PlugInEvent pluginevent)
	{
	}

	public void setDecimalPoint(char dp)
	{
		if (activeSynapse != null)
			activeSynapse.setDecimalPoint(dp);
	}

	public boolean isEOF()
	{
		if (activeSynapse != null)
			return activeSynapse.isEOF();
		else
			return false;
	}

	public void readAll()
	{
		if (activeSynapse != null)
			activeSynapse.readAll();
	}

	public void setBuffered(boolean newBuffered)
	{
		if (activeSynapse != null)
			activeSynapse.setBuffered(newBuffered);
	}

	public boolean isBuffered()
	{
		if (activeSynapse != null)
			return activeSynapse.isBuffered();
		else
			return false;
	}

	public ConverterPlugIn getPlugIn()
	{
		if (activeSynapse != null)
			return activeSynapse.getPlugIn();
		else
			return null;
	}

	public boolean isStepCounter()
	{
		if (activeSynapse != null)
			return activeSynapse.isStepCounter();
		else
			return false;
	}

	public void gotoFirstLine()
		throws IOException
	{
		if (activeSynapse != null)
			activeSynapse.gotoFirstLine();
	}

	public void removeAllInputs()
	{
		for (int i = 0; i < inputs.size(); i++)
		{
			StreamInputSynapse inp = (StreamInputSynapse)inputs.elementAt(i);
			inp.setInputFull(false);
		}

		initSwitch();
	}

	public TreeSet check()
	{
		TreeSet checks = new TreeSet();
		for (int i = 0; i < inputs.size(); i++)
		{
			StreamInputSynapse inp = (StreamInputSynapse)inputs.elementAt(i);
			checks.addAll(inp.check());
		}

		return checks;
	}

	public int getFirstRow()
	{
		if (activeSynapse != null)
			return activeSynapse.getFirstRow();
		else
			return 0;
	}

	public int getLastRow()
	{
		if (activeSynapse != null)
			return activeSynapse.getLastRow();
		else
			return 0;
	}

	public Collection getInspections()
	{
		if (activeSynapse != null)
			return activeSynapse.Inspections();
		else
			return null;
	}

	public int numColumns()
	{
		if (activeSynapse != null)
			return activeSynapse.numColumns();
		else
			return 0;
	}

	public String getAdvancedColumnSelector()
	{
		if (activeSynapse != null)
			return activeSynapse.getAdvancedColumnSelector();
		else
			return "";
	}

}
