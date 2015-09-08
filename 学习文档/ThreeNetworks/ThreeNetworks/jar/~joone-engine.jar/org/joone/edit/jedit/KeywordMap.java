// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   KeywordMap.java

package org.joone.edit.jedit;

import javax.swing.text.Segment;

// Referenced classes of package org.joone.edit.jedit:
//			SyntaxUtilities

public class KeywordMap
{
	class Keyword
	{

		public char keyword[];
		public byte id;
		public Keyword next;
		final KeywordMap this$0;

		public Keyword(char keyword[], byte id, Keyword next)
		{
			this$0 = KeywordMap.this;
			super();
			this.keyword = keyword;
			this.id = id;
			this.next = next;
		}
	}


	protected int mapLength;
	private Keyword map[];
	private boolean ignoreCase;

	public KeywordMap(boolean ignoreCase)
	{
		this(ignoreCase, 52);
		this.ignoreCase = ignoreCase;
	}

	public KeywordMap(boolean ignoreCase, int mapLength)
	{
		this.mapLength = mapLength;
		this.ignoreCase = ignoreCase;
		map = new Keyword[mapLength];
	}

	public byte lookup(Segment text, int offset, int length)
	{
		if (length == 0)
			return 0;
		for (Keyword k = map[getSegmentMapKey(text, offset, length)]; k != null;)
			if (length != k.keyword.length)
			{
				k = k.next;
			} else
			{
				if (SyntaxUtilities.regionMatches(ignoreCase, text, offset, k.keyword))
					return k.id;
				k = k.next;
			}

		return 0;
	}

	public void add(String keyword, byte id)
	{
		int key = getStringMapKey(keyword);
		map[key] = new Keyword(keyword.toCharArray(), id, map[key]);
	}

	public boolean getIgnoreCase()
	{
		return ignoreCase;
	}

	public void setIgnoreCase(boolean ignoreCase)
	{
		this.ignoreCase = ignoreCase;
	}

	protected int getStringMapKey(String s)
	{
		return (Character.toUpperCase(s.charAt(0)) + Character.toUpperCase(s.charAt(s.length() - 1))) % mapLength;
	}

	protected int getSegmentMapKey(Segment s, int off, int len)
	{
		return (Character.toUpperCase(s.array[off]) + Character.toUpperCase(s.array[(off + len) - 1])) % mapLength;
	}
}
