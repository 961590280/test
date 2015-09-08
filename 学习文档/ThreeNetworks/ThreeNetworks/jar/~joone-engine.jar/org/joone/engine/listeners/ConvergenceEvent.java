// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConvergenceEvent.java

package org.joone.engine.listeners;

import java.util.EventObject;
import org.joone.engine.Monitor;

public class ConvergenceEvent extends EventObject
{

	public ConvergenceEvent(Monitor aSource)
	{
		super(aSource);
	}
}
