// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MacroManager.java

package org.joone.script;

import java.io.Serializable;
import java.util.Hashtable;

// Referenced classes of package org.joone.script:
//			JooneMacro

public class MacroManager
	implements Serializable
{

	static final long serialVersionUID = 0x27a03e42e1a924fbL;
	private Hashtable macros;

	public MacroManager()
	{
		macros = new Hashtable();
		macros = initMacro(macros);
	}

	protected Hashtable initMacro(Hashtable mm)
	{
		JooneMacro macro = new JooneMacro();
		macro.setName("cycleTerminated");
		macro.setText("");
		macro.setEventMacro(true);
		mm.put(macro.getName(), macro);
		macro = new JooneMacro();
		macro.setName("errorChanged");
		macro.setText("");
		macro.setEventMacro(true);
		mm.put(macro.getName(), macro);
		macro = new JooneMacro();
		macro.setName("netStarted");
		macro.setText("");
		macro.setEventMacro(true);
		mm.put(macro.getName(), macro);
		macro = new JooneMacro();
		macro.setName("netStopped");
		macro.setText("");
		macro.setEventMacro(true);
		mm.put(macro.getName(), macro);
		return mm;
	}

	public synchronized void addMacro(String name, String text)
	{
		boolean oldEvent = false;
		JooneMacro macro;
		if (macros.containsKey(name))
		{
			macro = (JooneMacro)macros.get(name);
			oldEvent = macro.isEventMacro();
		} else
		{
			macro = new JooneMacro();
		}
		macro.setName(name);
		macro.setText(text);
		macro.setEventMacro(oldEvent);
		if (!macros.containsKey(name))
			macros.put(name, macro);
	}

	public String getMacro(String name)
	{
		JooneMacro macro = (JooneMacro)macros.get(name);
		if (macro != null)
			return macro.getText();
		else
			return null;
	}

	public boolean isEventMacro(String name)
	{
		JooneMacro macro = (JooneMacro)macros.get(name);
		if (macro != null)
			return macro.isEventMacro();
		else
			return false;
	}

	public boolean removeMacro(String name)
	{
		JooneMacro macro = (JooneMacro)macros.get(name);
		if (macro != null)
		{
			if (macro.isEventMacro())
			{
				return false;
			} else
			{
				macros.remove(name);
				return true;
			}
		} else
		{
			return false;
		}
	}

	public boolean renameMacro(String oldName, String newName)
	{
		JooneMacro macro = (JooneMacro)macros.get(oldName);
		if (macro != null)
		{
			if (macro.isEventMacro())
			{
				return false;
			} else
			{
				macros.remove(oldName);
				addMacro(newName, macro.getText());
				return true;
			}
		} else
		{
			return false;
		}
	}

	public Hashtable getMacros()
	{
		return (Hashtable)macros.clone();
	}
}
