// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HTMLTokenMarker.java

package org.joone.edit.jedit.tokenmarker;

import javax.swing.text.Segment;
import org.joone.edit.jedit.KeywordMap;
import org.joone.edit.jedit.SyntaxUtilities;

// Referenced classes of package org.joone.edit.jedit.tokenmarker:
//			TokenMarker, JavaScriptTokenMarker

public class HTMLTokenMarker extends TokenMarker
{

	public static final byte JAVASCRIPT = 100;
	private KeywordMap keywords;
	private boolean js;
	private int lastOffset;
	private int lastKeyword;

	public HTMLTokenMarker()
	{
		this(true);
	}

	public HTMLTokenMarker(boolean js)
	{
		this.js = js;
		keywords = JavaScriptTokenMarker.getKeywords();
	}

	public byte markTokensImpl(byte token, Segment line, int lineIndex)
	{
		char array[] = line.array;
		int offset = line.offset;
		lastOffset = offset;
		lastKeyword = offset;
		int length = line.count + offset;
		boolean backslash = false;
label0:
		for (int i = offset; i < length; i++)
		{
			int i1 = i + 1;
			char c = array[i];
			if (c == '\\')
			{
				backslash = !backslash;
				continue;
			}
label1:
			switch (token)
			{
			case 0: // '\0'
				backslash = false;
				switch (c)
				{
				default:
					break;

				case 60: // '<'
					addToken(i - lastOffset, token);
					lastOffset = lastKeyword = i;
					if (SyntaxUtilities.regionMatches(false, line, i1, "!--"))
					{
						i += 3;
						token = 1;
						break label1;
					}
					if (js && SyntaxUtilities.regionMatches(true, line, i1, "script>"))
					{
						addToken(8, (byte)6);
						lastOffset = lastKeyword = i += 8;
						token = 100;
					} else
					{
						token = 6;
					}
					break;

				case 38: // '&'
					addToken(i - lastOffset, token);
					lastOffset = lastKeyword = i;
					token = 7;
					break;
				}
				break;

			case 6: // '\006'
				backslash = false;
				if (c == '>')
				{
					addToken(i1 - lastOffset, token);
					lastOffset = lastKeyword = i1;
					token = 0;
				}
				break;

			case 7: // '\007'
				backslash = false;
				if (c == ';')
				{
					addToken(i1 - lastOffset, token);
					lastOffset = lastKeyword = i1;
					token = 0;
				}
				break;

			case 1: // '\001'
				backslash = false;
				if (SyntaxUtilities.regionMatches(false, line, i, "-->"))
				{
					addToken((i + 3) - lastOffset, token);
					lastOffset = lastKeyword = i + 3;
					token = 0;
				}
				break;

			case 100: // 'd'
				switch (c)
				{
				case 60: // '<'
					backslash = false;
					doKeyword(line, i, c);
					if (SyntaxUtilities.regionMatches(true, line, i1, "/script>"))
					{
						addToken(i - lastOffset, (byte)0);
						addToken(9, (byte)6);
						lastOffset = lastKeyword = i += 9;
						token = 0;
					}
					break label1;

				case 34: // '"'
					if (backslash)
					{
						backslash = false;
					} else
					{
						doKeyword(line, i, c);
						addToken(i - lastOffset, (byte)0);
						lastOffset = lastKeyword = i;
						token = 3;
					}
					break label1;

				case 39: // '\''
					if (backslash)
					{
						backslash = false;
					} else
					{
						doKeyword(line, i, c);
						addToken(i - lastOffset, (byte)0);
						lastOffset = lastKeyword = i;
						token = 4;
					}
					break label1;

				case 47: // '/'
					backslash = false;
					doKeyword(line, i, c);
					if (length - i <= 1)
						break label1;
					addToken(i - lastOffset, (byte)0);
					lastOffset = lastKeyword = i;
					if (array[i1] == '/')
					{
						addToken(length - i, (byte)2);
						lastOffset = lastKeyword = length;
					} else
					{
						if (array[i1] == '*')
							token = 2;
						break label1;
					}
					break;

				default:
					backslash = false;
					if (!Character.isLetterOrDigit(c) && c != '_')
						doKeyword(line, i, c);
					break label1;
				}
				break label0;

			case 3: // '\003'
				if (backslash)
				{
					backslash = false;
					break;
				}
				if (c == '"')
				{
					addToken(i1 - lastOffset, (byte)3);
					lastOffset = lastKeyword = i1;
					token = 100;
				}
				break;

			case 4: // '\004'
				if (backslash)
				{
					backslash = false;
					break;
				}
				if (c == '\'')
				{
					addToken(i1 - lastOffset, (byte)3);
					lastOffset = lastKeyword = i1;
					token = 100;
				}
				break;

			case 2: // '\002'
				backslash = false;
				if (c == '*' && length - i > 1 && array[i1] == '/')
				{
					addToken((i += 2) - lastOffset, (byte)2);
					lastOffset = lastKeyword = i;
					token = 100;
				}
				break;

			default:
				throw new InternalError((new StringBuilder("Invalid state: ")).append(token).toString());
			}
		}

		switch (token)
		{
		case 3: // '\003'
		case 4: // '\004'
			addToken(length - lastOffset, (byte)10);
			token = 100;
			break;

		case 7: // '\007'
			addToken(length - lastOffset, (byte)10);
			token = 0;
			break;

		case 100: // 'd'
			doKeyword(line, length, '\0');
			addToken(length - lastOffset, (byte)0);
			break;

		default:
			addToken(length - lastOffset, token);
			break;
		}
		return token;
	}

	private boolean doKeyword(Segment line, int i, char c)
	{
		int i1 = i + 1;
		int len = i - lastKeyword;
		byte id = keywords.lookup(line, lastKeyword, len);
		if (id != 0)
		{
			if (lastKeyword != lastOffset)
				addToken(lastKeyword - lastOffset, (byte)0);
			addToken(len, id);
			lastOffset = i;
		}
		lastKeyword = i1;
		return false;
	}
}
