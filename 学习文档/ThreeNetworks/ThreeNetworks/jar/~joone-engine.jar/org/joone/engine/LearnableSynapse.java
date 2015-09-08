// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LearnableSynapse.java

package org.joone.engine;


// Referenced classes of package org.joone.engine:
//			Learnable, Matrix

public interface LearnableSynapse
	extends Learnable
{

	public abstract int getInputDimension();

	public abstract int getOutputDimension();

	public abstract Matrix getWeights();

	public abstract void setWeights(Matrix matrix);

	public abstract double getLearningRate();

	public abstract double getMomentum();
}
