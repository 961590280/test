// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SnapshotFormatEditor.java

package org.joone.util;

import java.beans.PropertyEditorSupport;

public class SnapshotFormatEditor extends PropertyEditorSupport
{

	public final String formats[] = {
		"joone"
	};

	public SnapshotFormatEditor()
	{
	}

	public String[] getTags()
	{
		return formats;
	}
}
