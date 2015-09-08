// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NeuralElement.java

package org.joone.engine;

import java.util.TreeSet;

// Referenced classes of package org.joone.engine:
//			Monitor

public interface NeuralElement
{

	public abstract boolean isEnabled();

	public abstract void setEnabled(boolean flag);

	public abstract void setMonitor(Monitor monitor);

	public abstract Monitor getMonitor();

	public abstract String getName();

	public abstract void setName(String s);

	public abstract void init();

	public abstract TreeSet check();
}
