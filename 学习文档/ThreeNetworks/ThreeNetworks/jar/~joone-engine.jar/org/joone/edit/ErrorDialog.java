// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ErrorDialog.java

package org.joone.edit;

import java.awt.Frame;

// Referenced classes of package org.joone.edit:
//			MessageDialog

public class ErrorDialog extends MessageDialog
{

	private static final long serialVersionUID = 0xc9823c12089855fdL;

	public ErrorDialog(Frame frame, String message)
	{
		super(frame, "Error", message);
	}
}
