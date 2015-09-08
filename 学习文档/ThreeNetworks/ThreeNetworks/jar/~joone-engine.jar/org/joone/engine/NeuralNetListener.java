// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NeuralNetListener.java

package org.joone.engine;

import java.util.EventListener;

// Referenced classes of package org.joone.engine:
//			NeuralNetEvent

public interface NeuralNetListener
	extends EventListener
{

	public abstract void netStarted(NeuralNetEvent neuralnetevent);

	public abstract void cicleTerminated(NeuralNetEvent neuralnetevent);

	public abstract void netStopped(NeuralNetEvent neuralnetevent);

	public abstract void errorChanged(NeuralNetEvent neuralnetevent);

	public abstract void netStoppedError(NeuralNetEvent neuralnetevent, String s);
}
