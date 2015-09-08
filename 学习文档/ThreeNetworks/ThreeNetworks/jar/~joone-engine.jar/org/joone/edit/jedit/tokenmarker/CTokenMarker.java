// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CTokenMarker.java

package org.joone.edit.jedit.tokenmarker;

import javax.swing.text.Segment;
import org.joone.edit.jedit.KeywordMap;

// Referenced classes of package org.joone.edit.jedit.tokenmarker:
//			TokenMarker

public class CTokenMarker extends TokenMarker
{

	private static KeywordMap cKeywords;
	private boolean cpp;
	private KeywordMap keywords;
	private int lastOffset;
	private int lastKeyword;

	public CTokenMarker()
	{
		this(true, getKeywords());
	}

	public CTokenMarker(boolean cpp, KeywordMap keywords)
	{
		this.cpp = cpp;
		this.keywords = keywords;
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
				switch (c)
				{
				case 35: // '#'
					if (backslash)
					{
						backslash = false;
						break label1;
					}
					if (!cpp || doKeyword(line, i, c))
						break label1;
					addToken(i - lastOffset, token);
					addToken(length - i, (byte)7);
					lastOffset = lastKeyword = length;
					break;

				case 34: // '"'
					doKeyword(line, i, c);
					if (backslash)
					{
						backslash = false;
					} else
					{
						addToken(i - lastOffset, token);
						token = 3;
						lastOffset = lastKeyword = i;
					}
					break label1;

				case 39: // '\''
					doKeyword(line, i, c);
					if (backslash)
					{
						backslash = false;
					} else
					{
						addToken(i - lastOffset, token);
						token = 4;
						lastOffset = lastKeyword = i;
					}
					break label1;

				case 58: // ':'
					if (lastKeyword == offset)
					{
						if (!doKeyword(line, i, c))
						{
							backslash = false;
							addToken(i1 - lastOffset, (byte)5);
							lastOffset = lastKeyword = i1;
						}
					} else
					if (!doKeyword(line, i, c));
					break label1;

				case 47: // '/'
					backslash = false;
					doKeyword(line, i, c);
					if (length - i <= 1)
						break label1;
					switch (array[i1])
					{
					default:
						break label1;

					case 42: // '*'
						addToken(i - lastOffset, token);
						lastOffset = lastKeyword = i;
						if (length - i > 2 && array[i + 2] == '*')
							token = 2;
						else
							token = 1;
						break label1;

					case 47: // '/'
						addToken(i - lastOffset, token);
						addToken(length - i, (byte)1);
						lastOffset = lastKeyword = length;
						break;
					}
					break;

				default:
					backslash = false;
					if (!Character.isLetterOrDigit(c) && c != '_')
						doKeyword(line, i, c);
					break label1;
				}
				break label0;

			case 1: // '\001'
			case 2: // '\002'
				backslash = false;
				if (c == '*' && length - i > 1 && array[i1] == '/')
				{
					i++;
					addToken((i + 1) - lastOffset, token);
					token = 0;
					lastOffset = lastKeyword = i + 1;
				}
				break;

			case 3: // '\003'
				if (backslash)
				{
					backslash = false;
					break;
				}
				if (c == '"')
				{
					addToken(i1 - lastOffset, token);
					token = 0;
					lastOffset = lastKeyword = i1;
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
					token = 0;
					lastOffset = lastKeyword = i1;
				}
				break;

			default:
				throw new InternalError((new StringBuilder("Invalid state: ")).append(token).toString());
			}
		}

		if (token == 0)
			doKeyword(line, length, '\0');
		switch (token)
		{
		case 3: // '\003'
		case 4: // '\004'
			addToken(length - lastOffset, (byte)10);
			token = 0;
			break;

		case 7: // '\007'
			addToken(length - lastOffset, token);
			if (!backslash)
				token = 0;
			// fall through

		case 5: // '\005'
		case 6: // '\006'
		default:
			addToken(length - lastOffset, token);
			break;
		}
		return token;
	}

	public static KeywordMap getKeywords()
	{
		if (cKeywords == null)
		{
			cKeywords = new KeywordMap(false);
			cKeywords.add("char", (byte)8);
			cKeywords.add("double", (byte)8);
			cKeywords.add("enum", (byte)8);
			cKeywords.add("float", (byte)8);
			cKeywords.add("int", (byte)8);
			cKeywords.add("long", (byte)8);
			cKeywords.add("short", (byte)8);
			cKeywords.add("signed", (byte)8);
			cKeywords.add("struct", (byte)8);
			cKeywords.add("typedef", (byte)8);
			cKeywords.add("union", (byte)8);
			cKeywords.add("unsigned", (byte)8);
			cKeywords.add("void", (byte)8);
			cKeywords.add("auto", (byte)6);
			cKeywords.add("const", (byte)6);
			cKeywords.add("extern", (byte)6);
			cKeywords.add("register", (byte)6);
			cKeywords.add("static", (byte)6);
			cKeywords.add("volatile", (byte)6);
			cKeywords.add("break", (byte)6);
			cKeywords.add("case", (byte)6);
			cKeywords.add("continue", (byte)6);
			cKeywords.add("default", (byte)6);
			cKeywords.add("do", (byte)6);
			cKeywords.add("else", (byte)6);
			cKeywords.add("for", (byte)6);
			cKeywords.add("goto", (byte)6);
			cKeywords.add("if", (byte)6);
			cKeywords.add("return", (byte)6);
			cKeywords.add("sizeof", (byte)6);
			cKeywords.add("switch", (byte)6);
			cKeywords.add("while", (byte)6);
			cKeywords.add("asm", (byte)7);
			cKeywords.add("asmlinkage", (byte)7);
			cKeywords.add("far", (byte)7);
			cKeywords.add("huge", (byte)7);
			cKeywords.add("inline", (byte)7);
			cKeywords.add("near", (byte)7);
			cKeywords.add("pascal", (byte)7);
			cKeywords.add("true", (byte)4);
			cKeywords.add("false", (byte)4);
			cKeywords.add("NULL", (byte)4);
		}
		return cKeywords;
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
