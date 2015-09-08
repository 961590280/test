// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SyntaxDocument.java

package org.joone.edit.jedit;

import javax.swing.event.DocumentEvent;
import javax.swing.text.*;
import javax.swing.undo.UndoableEdit;
import org.joone.edit.jedit.tokenmarker.TokenMarker;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

public class SyntaxDocument extends PlainDocument
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/edit/jedit/SyntaxDocument);
	protected TokenMarker tokenMarker;

	public SyntaxDocument()
	{
	}

	public TokenMarker getTokenMarker()
	{
		return tokenMarker;
	}

	public void setTokenMarker(TokenMarker tm)
	{
		tokenMarker = tm;
		if (tm == null)
		{
			return;
		} else
		{
			tokenMarker.insertLines(0, getDefaultRootElement().getElementCount());
			tokenizeLines();
			return;
		}
	}

	public void tokenizeLines()
	{
		tokenizeLines(0, getDefaultRootElement().getElementCount());
	}

	public void tokenizeLines(int start, int len)
	{
		if (tokenMarker == null || !tokenMarker.supportsMultilineTokens())
			return;
		Segment lineSegment = new Segment();
		Element map = getDefaultRootElement();
		len += start;
		try
		{
			for (int i = start; i < len; i++)
			{
				Element lineElement = map.getElement(i);
				int lineStart = lineElement.getStartOffset();
				getText(lineStart, lineElement.getEndOffset() - lineStart - 1, lineSegment);
				tokenMarker.markTokens(lineSegment, i);
			}

		}
		catch (BadLocationException ble)
		{
			log.warn((new StringBuilder("BadLocationException was thrown. Message is : ")).append(ble.getMessage()).toString(), ble);
		}
	}

	public void beginCompoundEdit()
	{
	}

	public void endCompoundEdit()
	{
	}

	public void addUndoableEdit(UndoableEdit undoableedit)
	{
	}

	protected void fireInsertUpdate(DocumentEvent evt)
	{
		if (tokenMarker != null)
		{
			javax.swing.event.DocumentEvent.ElementChange ch = evt.getChange(getDefaultRootElement());
			if (ch != null)
				tokenMarker.insertLines(ch.getIndex() + 1, ch.getChildrenAdded().length - ch.getChildrenRemoved().length);
		}
		super.fireInsertUpdate(evt);
	}

	protected void fireRemoveUpdate(DocumentEvent evt)
	{
		if (tokenMarker != null)
		{
			javax.swing.event.DocumentEvent.ElementChange ch = evt.getChange(getDefaultRootElement());
			if (ch != null)
				tokenMarker.deleteLines(ch.getIndex() + 1, ch.getChildrenRemoved().length - ch.getChildrenAdded().length);
		}
		super.fireRemoveUpdate(evt);
	}

}
