// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EditorParameters.java

package org.joone.edit;


public class EditorParameters
{

	private int refreshingRate;

	public EditorParameters()
	{
		refreshingRate = 1;
	}

	public int getRefreshingRate()
	{
		return refreshingRate;
	}

	public void setRefreshingRate(int refreshingRate)
	{
		this.refreshingRate = refreshingRate;
	}
}
