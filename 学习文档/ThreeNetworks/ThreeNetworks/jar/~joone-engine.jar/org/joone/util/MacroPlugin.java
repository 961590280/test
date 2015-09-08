// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MacroPlugin.java

package org.joone.util;

import org.joone.engine.Monitor;
import org.joone.script.*;

// Referenced classes of package org.joone.util:
//			MonitorPlugin

public class MacroPlugin extends MonitorPlugin
	implements MacroInterface
{

	static final long serialVersionUID = 0xbc7213163ba97133L;
	private transient JooneScript jScript;
	private MacroManager macroManager;

	public MacroPlugin()
	{
	}

	public void set(String name, Object jObject)
	{
		getBSH().set(name, jObject);
	}

	private JooneScript getBSH()
	{
		if (jScript == null)
			jScript = new JooneScript();
		return jScript;
	}

	protected void manageStart(Monitor mon)
	{
		getBSH().set("jMon", mon);
		String macro = getMacroManager().getMacro("netStarted");
		runScript(macro, false);
	}

	protected void manageCycle(Monitor mon)
	{
		getBSH().set("jMon", mon);
		String macro = getMacroManager().getMacro("cycleTerminated");
		runScript(macro, false);
	}

	protected void manageStop(Monitor mon)
	{
		getBSH().set("jMon", mon);
		String macro = getMacroManager().getMacro("netStopped");
		runScript(macro, false);
	}

	protected void manageError(Monitor mon)
	{
		getBSH().set("jMon", mon);
		String macro = getMacroManager().getMacro("errorChanged");
		runScript(macro, false);
	}

	public void runMacro(String text)
	{
		runScript(text, false);
	}

	private void runScript(String eventScript, boolean file)
	{
		if (file)
			jScript.source(eventScript);
		else
			jScript.eval(eventScript);
	}

	public MacroManager getMacroManager()
	{
		if (macroManager == null)
			macroManager = new MacroManager();
		return macroManager;
	}

	public void setMacroManager(MacroManager macroManager)
	{
		this.macroManager = macroManager;
	}

	protected void manageStopError(Monitor monitor, String s)
	{
	}
}
