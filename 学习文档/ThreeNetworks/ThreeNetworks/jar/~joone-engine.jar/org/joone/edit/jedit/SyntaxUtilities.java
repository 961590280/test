// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SyntaxUtilities.java

package org.joone.edit.jedit;

import java.awt.*;
import javax.swing.text.*;
import org.joone.edit.jedit.tokenmarker.Token;

// Referenced classes of package org.joone.edit.jedit:
//			SyntaxStyle

public class SyntaxUtilities
{

	public static boolean regionMatches(boolean ignoreCase, Segment text, int offset, String match)
	{
		int length = offset + match.length();
		char textArray[] = text.array;
		if (length > text.offset + text.count)
			return false;
		int i = offset;
		for (int j = 0; i < length; j++)
		{
			char c1 = textArray[i];
			char c2 = match.charAt(j);
			if (ignoreCase)
			{
				c1 = Character.toUpperCase(c1);
				c2 = Character.toUpperCase(c2);
			}
			if (c1 != c2)
				return false;
			i++;
		}

		return true;
	}

	public static boolean regionMatches(boolean ignoreCase, Segment text, int offset, char match[])
	{
		int length = offset + match.length;
		char textArray[] = text.array;
		if (length > text.offset + text.count)
			return false;
		int i = offset;
		for (int j = 0; i < length; j++)
		{
			char c1 = textArray[i];
			char c2 = match[j];
			if (ignoreCase)
			{
				c1 = Character.toUpperCase(c1);
				c2 = Character.toUpperCase(c2);
			}
			if (c1 != c2)
				return false;
			i++;
		}

		return true;
	}

	public static SyntaxStyle[] getDefaultSyntaxStyles()
	{
		SyntaxStyle styles[] = new SyntaxStyle[11];
		styles[1] = new SyntaxStyle(Color.black, true, false);
		styles[2] = new SyntaxStyle(new Color(0x990033), true, false);
		styles[6] = new SyntaxStyle(Color.black, false, true);
		styles[7] = new SyntaxStyle(Color.magenta, false, false);
		styles[8] = new SyntaxStyle(new Color(38400), false, false);
		styles[3] = new SyntaxStyle(new Color(0x650099), false, false);
		styles[4] = new SyntaxStyle(new Color(0x650099), false, true);
		styles[5] = new SyntaxStyle(new Color(0x990033), false, true);
		styles[9] = new SyntaxStyle(Color.black, false, true);
		styles[10] = new SyntaxStyle(Color.red, false, true);
		return styles;
	}

	public static int paintSyntaxLine(Segment line, Token tokens, SyntaxStyle styles[], TabExpander expander, Graphics gfx, int x, int y)
	{
		Font defaultFont = gfx.getFont();
		Color defaultColor = gfx.getColor();
		int offset = 0;
		do
		{
			byte id = tokens.id;
			if (id != 127)
			{
				int length = tokens.length;
				if (id == 0)
				{
					if (!defaultColor.equals(gfx.getColor()))
						gfx.setColor(defaultColor);
					if (!defaultFont.equals(gfx.getFont()))
						gfx.setFont(defaultFont);
				} else
				{
					styles[id].setGraphicsFlags(gfx, defaultFont);
				}
				line.count = length;
				x = Utilities.drawTabbedText(line, x, y, gfx, expander, 0);
				line.offset += length;
				offset += length;
				tokens = tokens.next;
			} else
			{
				return x;
			}
		} while (true);
	}

	private SyntaxUtilities()
	{
	}
}
