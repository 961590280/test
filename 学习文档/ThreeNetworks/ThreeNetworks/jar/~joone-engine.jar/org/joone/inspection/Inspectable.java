// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Inspectable.java

package org.joone.inspection;

import java.util.Collection;

public interface Inspectable
{

	public abstract Collection Inspections();

	public abstract String InspectableTitle();
}
