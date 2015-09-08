// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NeuralValidationListener.java

package org.joone.net;

import java.util.EventListener;

// Referenced classes of package org.joone.net:
//			NeuralValidationEvent

public interface NeuralValidationListener
	extends EventListener
{

	public abstract void netValidated(NeuralValidationEvent neuralvalidationevent);
}
