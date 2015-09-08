// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NeuralValidationEvent.java

package org.joone.net;

import java.util.EventObject;

// Referenced classes of package org.joone.net:
//			NeuralNet

public class NeuralValidationEvent extends EventObject
{

	public NeuralValidationEvent(NeuralNet event)
	{
		super(event);
	}
}
