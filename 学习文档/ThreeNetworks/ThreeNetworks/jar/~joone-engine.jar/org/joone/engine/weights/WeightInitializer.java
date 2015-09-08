// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WeightInitializer.java

package org.joone.engine.weights;

import java.io.Serializable;
import org.joone.engine.Matrix;

public interface WeightInitializer
	extends Serializable
{

	public abstract void initialize(Matrix matrix);
}
