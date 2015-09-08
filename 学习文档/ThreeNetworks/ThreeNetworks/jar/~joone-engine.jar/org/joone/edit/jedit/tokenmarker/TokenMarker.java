// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TokenMarker.java

package org.joone.edit.jedit.tokenmarker;

import javax.swing.text.Segment;

// Referenced classes of package org.joone.edit.jedit.tokenmarker:
//			Token

public abstract class TokenMarker
{
	public class LineInfo
	{

		public byte token;
		public Object obj;
		final TokenMarker this$0;

		public LineInfo()
		{
			this$0 = TokenMarker.this;
			super();
		}

		public LineInfo(byte token, Object obj)
		{
			this$0 = TokenMarker.this;
			super();
			this.token = token;
			this.obj = obj;
		}
	}


	protected Token firstToken;
	protected Token lastToken;
	protected LineInfo lineInfo[];
	protected int length;
	protected int lastLine;
	protected boolean nextLineRequested;

	public Token markTokens(Segment line, int lineIndex)
	{
		if (lineIndex >= length)
			throw new IllegalArgumentException((new StringBuilder("Tokenizing invalid line: ")).append(lineIndex).toString());
		lastToken = null;
		LineInfo info = lineInfo[lineIndex];
		LineInfo prev;
		if (lineIndex == 0)
			prev = null;
		else
			prev = lineInfo[lineIndex - 1];
		byte oldToken = info.token;
		byte token = markTokensImpl(prev != null ? prev.token : 0, line, lineIndex);
		info.token = token;
		if (lastLine != lineIndex || !nextLineRequested)
			nextLineRequested = oldToken != token;
		lastLine = lineIndex;
		addToken(0, (byte)127);
		return firstToken;
	}

	protected abstract byte markTokensImpl(byte byte0, Segment segment, int i);

	public boolean supportsMultilineTokens()
	{
		return true;
	}

	public void insertLines(int index, int lines)
	{
		if (lines <= 0)
			return;
		length += lines;
		ensureCapacity(length);
		int len = index + lines;
		System.arraycopy(lineInfo, index, lineInfo, len, lineInfo.length - len);
		for (int i = (index + lines) - 1; i >= index; i--)
			lineInfo[i] = new LineInfo();

	}

	public void deleteLines(int index, int lines)
	{
		if (lines <= 0)
		{
			return;
		} else
		{
			int len = index + lines;
			length -= lines;
			System.arraycopy(lineInfo, len, lineInfo, index, lineInfo.length - len);
			return;
		}
	}

	public int getLineCount()
	{
		return length;
	}

	public boolean isNextLineRequested()
	{
		return nextLineRequested;
	}

	protected TokenMarker()
	{
		lastLine = -1;
	}

	protected void ensureCapacity(int index)
	{
		if (lineInfo == null)
			lineInfo = new LineInfo[index + 1];
		else
		if (lineInfo.length <= index)
		{
			LineInfo lineInfoN[] = new LineInfo[(index + 1) * 2];
			System.arraycopy(lineInfo, 0, lineInfoN, 0, lineInfo.length);
			lineInfo = lineInfoN;
		}
	}

	protected void addToken(int length, byte id)
	{
		if (id >= 100 && id <= 126)
			throw new InternalError((new StringBuilder("Invalid id: ")).append(id).toString());
		if (length == 0 && id != 127)
			return;
		if (firstToken == null)
		{
			firstToken = new Token(length, id);
			lastToken = firstToken;
		} else
		if (lastToken == null)
		{
			lastToken = firstToken;
			firstToken.length = length;
			firstToken.id = id;
		} else
		if (lastToken.next == null)
		{
			lastToken.next = new Token(length, id);
			lastToken = lastToken.next;
		} else
		{
			lastToken = lastToken.next;
			lastToken.length = length;
			lastToken.id = id;
		}
	}
}
