// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SyntaxStyle.java

package org.joone.edit.jedit;

import java.awt.*;
import javax.swing.text.StyleContext;

public class SyntaxStyle
{

	private Color color;
	private boolean italic;
	private boolean bold;
	private Font lastFont;
	private Font lastStyledFont;
	private FontMetrics fontMetrics;

	public SyntaxStyle(Color color, boolean italic, boolean bold)
	{
		this.color = color;
		this.italic = italic;
		this.bold = bold;
	}

	public Color getColor()
	{
		return color;
	}

	public boolean isPlain()
	{
		return !bold && !italic;
	}

	public boolean isItalic()
	{
		return italic;
	}

	public boolean isBold()
	{
		return bold;
	}

	public Font getStyledFont(Font font)
	{
		if (font == null)
			throw new NullPointerException("font param must not be null");
		if (font.equals(lastFont))
		{
			return lastStyledFont;
		} else
		{
			lastFont = font;
			lastStyledFont = new Font(font.getFamily(), (bold ? 1 : 0) | (italic ? 2 : 0), font.getSize());
			return lastStyledFont;
		}
	}

	public FontMetrics getFontMetrics(Font font)
	{
		if (font == null)
			throw new NullPointerException("font param must not be null");
		if (font.equals(lastFont) && fontMetrics != null)
		{
			return fontMetrics;
		} else
		{
			lastFont = font;
			lastStyledFont = new Font(font.getFamily(), (bold ? 1 : 0) | (italic ? 2 : 0), font.getSize());
			fontMetrics = StyleContext.getDefaultStyleContext().getFontMetrics(lastStyledFont);
			return fontMetrics;
		}
	}

	public void setGraphicsFlags(Graphics gfx, Font font)
	{
		Font _font = getStyledFont(font);
		gfx.setFont(_font);
		gfx.setColor(color);
	}

	public String toString()
	{
		return (new StringBuilder(String.valueOf(getClass().getName()))).append("[color=").append(color).append(italic ? ",italic" : "").append(bold ? ",bold" : "").append("]").toString();
	}
}
