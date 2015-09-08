// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConvergenceListener.java

package org.joone.engine.listeners;

import java.util.EventListener;

// Referenced classes of package org.joone.engine.listeners:
//			ConvergenceEvent, ConvergenceObserver

public interface ConvergenceListener
	extends EventListener
{

	public abstract void netConverged(ConvergenceEvent convergenceevent, ConvergenceObserver convergenceobserver);
}
