// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Inspection.java

package org.joone.inspection;


public interface Inspection
{

	public abstract Object[][] getComponent();

	public abstract String getTitle();

	public abstract boolean rowNumbers();

	public abstract Object[] getNames();

	public abstract void setComponent(Object aobj[][]);
}
