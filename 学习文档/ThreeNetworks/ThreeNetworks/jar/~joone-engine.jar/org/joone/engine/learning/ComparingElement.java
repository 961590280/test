// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ComparingElement.java

package org.joone.engine.learning;

import java.io.Serializable;
import org.joone.engine.LinearLayer;
import org.joone.engine.OutputPatternListener;
import org.joone.io.StreamInputSynapse;

public interface ComparingElement
	extends OutputPatternListener, Serializable
{

	public abstract StreamInputSynapse getDesired();

	public abstract boolean setDesired(StreamInputSynapse streaminputsynapse);

	public abstract boolean addResultSynapse(OutputPatternListener outputpatternlistener);

	public abstract void removeResultSynapse(OutputPatternListener outputpatternlistener);

	public abstract LinearLayer getTheLinearLayer();

	public abstract void resetInput();
}
