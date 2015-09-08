// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ChartInterface.java

package org.joone.edit;

import org.joone.engine.OutputPatternListener;
import org.joone.engine.Pattern;

// Referenced classes of package org.joone.edit:
//			ChartingHandle

public interface ChartInterface
	extends OutputPatternListener
{

	public abstract void fwdPut(Pattern pattern, ChartingHandle chartinghandle);

	public abstract void removeHandle(ChartingHandle chartinghandle);

	public abstract int getMaxXaxis();

	public abstract void setMaxXaxis(int i);

	public abstract double getMaxYaxis();

	public abstract void setMaxYaxis(double d);
}
