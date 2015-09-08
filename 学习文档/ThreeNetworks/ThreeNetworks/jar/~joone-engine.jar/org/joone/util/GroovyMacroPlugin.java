// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GroovyMacroPlugin.java

package org.joone.util;

import org.joone.engine.Monitor;
import org.joone.script.*;

// Referenced classes of package org.joone.util:
//			MonitorPlugin

public class GroovyMacroPlugin extends MonitorPlugin
	implements MacroInterface
{

	static final long serialVersionUID = 0xc6c54b251c8d7133L;
	private transient JooneGroovyScript jScript;
	private MacroManager macroManager;

	public GroovyMacroPlugin()
	{
	}

	public void set(String name, Object jObject)
	{
		getGroovy().set(name, jObject);
	}

	private JooneGroovyScript getGroovy()
	{
		if (jScript == null)
			jScript = new JooneGroovyScript();
		return jScript;
	}

	protected void manageStart(Monitor mon)
	{
		getGroovy().set("jMon", mon);
		String macro = getMacroManager().getMacro("netStarted");
		runScript(macro, false);
	}

	protected void manageCycle(Monitor mon)
	{
		getGroovy().set("jMon", mon);
		String macro = getMacroManager().getMacro("cycleTerminated");
		runScript(macro, false);
	}

	protected void manageStop(Monitor mon)
	{
		getGroovy().set("jMon", mon);
		String macro = getMacroManager().getMacro("netStopped");
		runScript(macro, false);
	}

	protected void manageError(Monitor mon)
	{
		getGroovy().set("jMon", mon);
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
