// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MacroInterface.java

package org.joone.script;

import org.joone.engine.NeuralNetListener;

// Referenced classes of package org.joone.script:
//			MacroManager

public interface MacroInterface
	extends NeuralNetListener
{

	public abstract void set(String s, Object obj);

	public abstract void runMacro(String s);

	public abstract MacroManager getMacroManager();

	public abstract void setMacroManager(MacroManager macromanager);
}
