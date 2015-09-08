// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NeuralLayer.java

package org.joone.engine;

import java.util.TreeSet;
import java.util.Vector;

// Referenced classes of package org.joone.engine:
//			Matrix, InputPatternListener, OutputPatternListener, Monitor

public interface NeuralLayer
{

	public abstract void addNoise(double d);

	public abstract NeuralLayer copyInto(NeuralLayer neurallayer);

	public abstract Vector getAllInputs();

	public abstract Vector getAllOutputs();

	public abstract Matrix getBias();

	public abstract String getLayerName();

	public abstract int getRows();

	public abstract void removeAllInputs();

	public abstract void removeAllOutputs();

	public abstract void removeInputSynapse(InputPatternListener inputpatternlistener);

	public abstract void removeOutputSynapse(OutputPatternListener outputpatternlistener);

	public abstract void setAllInputs(Vector vector);

	public abstract void setAllOutputs(Vector vector);

	public abstract void setBias(Matrix matrix);

	public abstract boolean addInputSynapse(InputPatternListener inputpatternlistener);

	public abstract void setLayerName(String s);

	public abstract boolean addOutputSynapse(OutputPatternListener outputpatternlistener);

	public abstract void setRows(int i);

	public abstract void start();

	public abstract void setMonitor(Monitor monitor);

	public abstract Monitor getMonitor();

	public abstract boolean isRunning();

	public abstract TreeSet check();
}
