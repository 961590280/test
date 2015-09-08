// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TextAreaDefaults.java

package org.joone.edit.jedit;

import java.awt.Color;
import javax.swing.JPopupMenu;

// Referenced classes of package org.joone.edit.jedit:
//			DefaultInputHandler, InputHandler, SyntaxDocument, SyntaxUtilities, 
//			SyntaxStyle

public class TextAreaDefaults
{

	private static TextAreaDefaults DEFAULTS;
	public InputHandler inputHandler;
	public SyntaxDocument document;
	public boolean editable;
	public boolean caretVisible;
	public boolean caretBlinks;
	public boolean blockCaret;
	public int electricScroll;
	public int cols;
	public int rows;
	public SyntaxStyle styles[];
	public Color caretColor;
	public Color selectionColor;
	public Color lineHighlightColor;
	public boolean lineHighlight;
	public Color bracketHighlightColor;
	public boolean bracketHighlight;
	public Color eolMarkerColor;
	public boolean eolMarkers;
	public boolean paintInvalid;
	public JPopupMenu popup;

	public TextAreaDefaults()
	{
	}

	public static TextAreaDefaults getDefaults()
	{
		if (DEFAULTS == null)
		{
			DEFAULTS = new TextAreaDefaults();
			DEFAULTS.inputHandler = new DefaultInputHandler();
			DEFAULTS.inputHandler.addDefaultKeyBindings();
			DEFAULTS.document = new SyntaxDocument();
			DEFAULTS.editable = true;
			DEFAULTS.blockCaret = false;
			DEFAULTS.caretVisible = true;
			DEFAULTS.caretBlinks = true;
			DEFAULTS.electricScroll = 3;
			DEFAULTS.cols = 80;
			DEFAULTS.rows = 25;
			DEFAULTS.styles = SyntaxUtilities.getDefaultSyntaxStyles();
			DEFAULTS.caretColor = Color.black;
			DEFAULTS.selectionColor = new Color(0xccccff);
			DEFAULTS.lineHighlightColor = new Color(0xe0e0e0);
			DEFAULTS.lineHighlight = true;
			DEFAULTS.bracketHighlightColor = Color.black;
			DEFAULTS.bracketHighlight = true;
			DEFAULTS.eolMarkerColor = new Color(39321);
			DEFAULTS.eolMarkers = false;
			DEFAULTS.paintInvalid = false;
		}
		return DEFAULTS;
	}
}
