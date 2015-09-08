// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InputHandler.java

package org.joone.edit.jedit;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.*;
import javax.swing.JPopupMenu;
import javax.swing.text.BadLocationException;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.edit.jedit:
//			JEditTextArea, SyntaxDocument, TextUtilities

public abstract class InputHandler extends KeyAdapter
{
	public static interface MacroRecorder
	{

		public abstract void actionPerformed(ActionListener actionlistener, String s);
	}

	public static interface NonRecordable
	{
	}

	public static interface NonRepeatable
	{
	}

	public static interface Wrapper
	{
	}

	public static class backspace
		implements ActionListener
	{

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			if (!textArea.isEditable())
			{
				textArea.getToolkit().beep();
				return;
			}
			if (textArea.getSelectionStart() != textArea.getSelectionEnd())
			{
				textArea.setSelectedText("");
			} else
			{
				int caret = textArea.getCaretPosition();
				if (caret == 0)
				{
					textArea.getToolkit().beep();
					return;
				}
				try
				{
					textArea.getDocument().remove(caret - 1, 1);
				}
				catch (BadLocationException bl)
				{
					InputHandler.log.warn((new StringBuilder("BadLocationException thrown. Message is ")).append(bl.getMessage()).toString());
				}
			}
		}

		public backspace()
		{
		}
	}

	public static class backspace_word
		implements ActionListener
	{

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			int start = textArea.getSelectionStart();
			if (start != textArea.getSelectionEnd())
				textArea.setSelectedText("");
			int line = textArea.getCaretLine();
			int lineStart = textArea.getLineStartOffset(line);
			int caret = start - lineStart;
			String lineText = textArea.getLineText(textArea.getCaretLine());
			if (caret == 0)
			{
				if (lineStart == 0)
				{
					textArea.getToolkit().beep();
					return;
				}
				caret--;
			} else
			{
				String noWordSep = (String)textArea.getDocument().getProperty("noWordSep");
				caret = TextUtilities.findWordStart(lineText, caret, noWordSep);
			}
			try
			{
				textArea.getDocument().remove(caret + lineStart, start - (caret + lineStart));
			}
			catch (BadLocationException bl)
			{
				InputHandler.log.warn((new StringBuilder("BadLocationException thrown. Message is : ")).append(bl).toString());
			}
		}

		public backspace_word()
		{
		}
	}

	public static class clip_copy
		implements ActionListener
	{

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			textArea.copy();
		}

		public clip_copy()
		{
		}
	}

	public static class clip_cut
		implements ActionListener
	{

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			textArea.cut();
		}

		public clip_cut()
		{
		}
	}

	public static class clip_paste
		implements ActionListener
	{

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			textArea.paste();
		}

		public clip_paste()
		{
		}
	}

	public static class delete
		implements ActionListener
	{

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			if (!textArea.isEditable())
			{
				textArea.getToolkit().beep();
				return;
			}
			if (textArea.getSelectionStart() != textArea.getSelectionEnd())
			{
				textArea.setSelectedText("");
			} else
			{
				int caret = textArea.getCaretPosition();
				if (caret == textArea.getDocumentLength())
				{
					textArea.getToolkit().beep();
					return;
				}
				try
				{
					textArea.getDocument().remove(caret, 1);
				}
				catch (BadLocationException bl)
				{
					InputHandler.log.warn((new StringBuilder("BadLocationException thrown. Message is : ")).append(bl).toString());
				}
			}
		}

		public delete()
		{
		}
	}

	public static class delete_word
		implements ActionListener
	{

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			int start = textArea.getSelectionStart();
			if (start != textArea.getSelectionEnd())
				textArea.setSelectedText("");
			int line = textArea.getCaretLine();
			int lineStart = textArea.getLineStartOffset(line);
			int caret = start - lineStart;
			String lineText = textArea.getLineText(textArea.getCaretLine());
			if (caret == lineText.length())
			{
				if (lineStart + caret == textArea.getDocumentLength())
				{
					textArea.getToolkit().beep();
					return;
				}
				caret++;
			} else
			{
				String noWordSep = (String)textArea.getDocument().getProperty("noWordSep");
				caret = TextUtilities.findWordEnd(lineText, caret, noWordSep);
			}
			try
			{
				textArea.getDocument().remove(start, (caret + lineStart) - start);
			}
			catch (BadLocationException bl)
			{
				InputHandler.log.warn((new StringBuilder("BadLocationException thrown. Message is : ")).append(bl).toString());
			}
		}

		public delete_word()
		{
		}
	}

	public static class document_end
		implements ActionListener
	{

		private boolean select;

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			if (select)
				textArea.select(textArea.getMarkPosition(), textArea.getDocumentLength());
			else
				textArea.setCaretPosition(textArea.getDocumentLength());
		}

		public document_end(boolean select)
		{
			this.select = select;
		}
	}

	public static class document_home
		implements ActionListener
	{

		private boolean select;

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			if (select)
				textArea.select(textArea.getMarkPosition(), 0);
			else
				textArea.setCaretPosition(0);
		}

		public document_home(boolean select)
		{
			this.select = select;
		}
	}

	public static class end
		implements ActionListener
	{

		private boolean select;

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			int caret = textArea.getCaretPosition();
			int lastOfLine = textArea.getLineEndOffset(textArea.getCaretLine()) - 1;
			int lastVisibleLine = textArea.getFirstLine() + textArea.getVisibleLines();
			if (lastVisibleLine >= textArea.getLineCount())
				lastVisibleLine = Math.min(textArea.getLineCount() - 1, lastVisibleLine);
			else
				lastVisibleLine -= textArea.getElectricScroll() + 1;
			int lastVisible = textArea.getLineEndOffset(lastVisibleLine) - 1;
			int lastDocument = textArea.getDocumentLength();
			if (caret == lastDocument)
			{
				textArea.getToolkit().beep();
				return;
			}
			if (!Boolean.TRUE.equals(textArea.getClientProperty("InputHandler.homeEnd")))
				caret = lastOfLine;
			else
			if (caret == lastVisible)
				caret = lastDocument;
			else
			if (caret == lastOfLine)
				caret = lastVisible;
			else
				caret = lastOfLine;
			if (select)
				textArea.select(textArea.getMarkPosition(), caret);
			else
				textArea.setCaretPosition(caret);
		}

		public end(boolean select)
		{
			this.select = select;
		}
	}

	public static class home
		implements ActionListener
	{

		private boolean select;

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			int caret = textArea.getCaretPosition();
			int firstLine = textArea.getFirstLine();
			int firstOfLine = textArea.getLineStartOffset(textArea.getCaretLine());
			int firstVisibleLine = firstLine != 0 ? firstLine + textArea.getElectricScroll() : 0;
			int firstVisible = textArea.getLineStartOffset(firstVisibleLine);
			if (caret == 0)
			{
				textArea.getToolkit().beep();
				return;
			}
			if (!Boolean.TRUE.equals(textArea.getClientProperty("InputHandler.homeEnd")))
				caret = firstOfLine;
			else
			if (caret == firstVisible)
				caret = 0;
			else
			if (caret == firstOfLine)
				caret = firstVisible;
			else
				caret = firstOfLine;
			if (select)
				textArea.select(textArea.getMarkPosition(), caret);
			else
				textArea.setCaretPosition(caret);
		}

		public home(boolean select)
		{
			this.select = select;
		}
	}

	public static class insert_break
		implements ActionListener
	{

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			if (!textArea.isEditable())
			{
				textArea.getToolkit().beep();
				return;
			} else
			{
				textArea.setSelectedText("\n");
				return;
			}
		}

		public insert_break()
		{
		}
	}

	public static class insert_char
		implements ActionListener, NonRepeatable
	{

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			String str = evt.getActionCommand();
			int repeatCount = textArea.getInputHandler().getRepeatCount();
			if (textArea.isEditable())
			{
				StringBuffer buf = new StringBuffer();
				for (int i = 0; i < repeatCount; i++)
					buf.append(str);

				textArea.overwriteSetSelectedText(buf.toString());
			} else
			{
				textArea.getToolkit().beep();
			}
		}

		public insert_char()
		{
		}
	}

	public static class insert_tab
		implements ActionListener
	{

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			if (!textArea.isEditable())
			{
				textArea.getToolkit().beep();
				return;
			} else
			{
				textArea.overwriteSetSelectedText("\t");
				return;
			}
		}

		public insert_tab()
		{
		}
	}

	public static class next_char
		implements ActionListener
	{

		private boolean select;

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			int caret = textArea.getCaretPosition();
			if (caret == textArea.getDocumentLength())
			{
				textArea.getToolkit().beep();
				return;
			}
			if (select)
				textArea.select(textArea.getMarkPosition(), caret + 1);
			else
				textArea.setCaretPosition(caret + 1);
		}

		public next_char(boolean select)
		{
			this.select = select;
		}
	}

	public static class next_line
		implements ActionListener
	{

		private boolean select;

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			int caret = textArea.getCaretPosition();
			int line = textArea.getCaretLine();
			if (line == textArea.getLineCount() - 1)
			{
				textArea.getToolkit().beep();
				return;
			}
			int magic = textArea.getMagicCaretPosition();
			if (magic == -1)
				magic = textArea.offsetToX(line, caret - textArea.getLineStartOffset(line));
			caret = textArea.getLineStartOffset(line + 1) + textArea.xToOffset(line + 1, magic);
			if (select)
				textArea.select(textArea.getMarkPosition(), caret);
			else
				textArea.setCaretPosition(caret);
			textArea.setMagicCaretPosition(magic);
		}

		public next_line(boolean select)
		{
			this.select = select;
		}
	}

	public static class next_page
		implements ActionListener
	{

		private boolean select;

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			int lineCount = textArea.getLineCount();
			int firstLine = textArea.getFirstLine();
			int visibleLines = textArea.getVisibleLines();
			int line = textArea.getCaretLine();
			firstLine += visibleLines;
			if (firstLine + visibleLines >= lineCount - 1)
				firstLine = lineCount - visibleLines;
			textArea.setFirstLine(firstLine);
			int caret = textArea.getLineStartOffset(Math.min(textArea.getLineCount() - 1, line + visibleLines));
			if (select)
				textArea.select(textArea.getMarkPosition(), caret);
			else
				textArea.setCaretPosition(caret);
		}

		public next_page(boolean select)
		{
			this.select = select;
		}
	}

	public static class next_word
		implements ActionListener
	{

		private boolean select;

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			int caret = textArea.getCaretPosition();
			int line = textArea.getCaretLine();
			int lineStart = textArea.getLineStartOffset(line);
			caret -= lineStart;
			String lineText = textArea.getLineText(textArea.getCaretLine());
			if (caret == lineText.length())
			{
				if (lineStart + caret == textArea.getDocumentLength())
				{
					textArea.getToolkit().beep();
					return;
				}
				caret++;
			} else
			{
				String noWordSep = (String)textArea.getDocument().getProperty("noWordSep");
				caret = TextUtilities.findWordEnd(lineText, caret, noWordSep);
			}
			if (select)
				textArea.select(textArea.getMarkPosition(), lineStart + caret);
			else
				textArea.setCaretPosition(lineStart + caret);
		}

		public next_word(boolean select)
		{
			this.select = select;
		}
	}

	public static class overwrite
		implements ActionListener
	{

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			textArea.setOverwriteEnabled(!textArea.isOverwriteEnabled());
		}

		public overwrite()
		{
		}
	}

	public static class prev_char
		implements ActionListener
	{

		private boolean select;

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			int caret = textArea.getCaretPosition();
			if (caret == 0)
			{
				textArea.getToolkit().beep();
				return;
			}
			if (select)
				textArea.select(textArea.getMarkPosition(), caret - 1);
			else
				textArea.setCaretPosition(caret - 1);
		}

		public prev_char(boolean select)
		{
			this.select = select;
		}
	}

	public static class prev_line
		implements ActionListener
	{

		private boolean select;

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			int caret = textArea.getCaretPosition();
			int line = textArea.getCaretLine();
			if (line == 0)
			{
				textArea.getToolkit().beep();
				return;
			}
			int magic = textArea.getMagicCaretPosition();
			if (magic == -1)
				magic = textArea.offsetToX(line, caret - textArea.getLineStartOffset(line));
			caret = textArea.getLineStartOffset(line - 1) + textArea.xToOffset(line - 1, magic);
			if (select)
				textArea.select(textArea.getMarkPosition(), caret);
			else
				textArea.setCaretPosition(caret);
			textArea.setMagicCaretPosition(magic);
		}

		public prev_line(boolean select)
		{
			this.select = select;
		}
	}

	public static class prev_page
		implements ActionListener
	{

		private boolean select;

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			int firstLine = textArea.getFirstLine();
			int visibleLines = textArea.getVisibleLines();
			int line = textArea.getCaretLine();
			if (firstLine < visibleLines)
				firstLine = visibleLines;
			textArea.setFirstLine(firstLine - visibleLines);
			int caret = textArea.getLineStartOffset(Math.max(0, line - visibleLines));
			if (select)
				textArea.select(textArea.getMarkPosition(), caret);
			else
				textArea.setCaretPosition(caret);
		}

		public prev_page(boolean select)
		{
			this.select = select;
		}
	}

	public static class prev_word
		implements ActionListener
	{

		private boolean select;

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			int caret = textArea.getCaretPosition();
			int line = textArea.getCaretLine();
			int lineStart = textArea.getLineStartOffset(line);
			caret -= lineStart;
			String lineText = textArea.getLineText(textArea.getCaretLine());
			if (caret == 0)
			{
				if (lineStart == 0)
				{
					textArea.getToolkit().beep();
					return;
				}
				caret--;
			} else
			{
				String noWordSep = (String)textArea.getDocument().getProperty("noWordSep");
				caret = TextUtilities.findWordStart(lineText, caret, noWordSep);
			}
			if (select)
				textArea.select(textArea.getMarkPosition(), lineStart + caret);
			else
				textArea.setCaretPosition(lineStart + caret);
		}

		public prev_word(boolean select)
		{
			this.select = select;
		}
	}

	public static class repeat
		implements ActionListener, NonRecordable
	{

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			textArea.getInputHandler().setRepeatEnabled(true);
			String actionCommand = evt.getActionCommand();
			if (actionCommand != null)
				textArea.getInputHandler().setRepeatCount(Integer.parseInt(actionCommand));
		}

		public repeat()
		{
		}
	}

	public static class select_all
		implements ActionListener
	{

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			textArea.selectAll();
		}

		public select_all()
		{
		}
	}

	public static class toggle_rect
		implements ActionListener
	{

		public void actionPerformed(ActionEvent evt)
		{
			JEditTextArea textArea = InputHandler.getTextArea(evt);
			textArea.setSelectionRectangular(!textArea.isSelectionRectangular());
		}

		public toggle_rect()
		{
		}
	}


	public static final ILogger log = LoggerFactory.getLogger(org/joone/edit/jedit/InputHandler);
	public static final String SMART_HOME_END_PROPERTY = "InputHandler.homeEnd";
	public static final ActionListener BACKSPACE;
	public static final ActionListener BACKSPACE_WORD;
	public static final ActionListener DELETE;
	public static final ActionListener DELETE_WORD;
	public static final ActionListener END;
	public static final ActionListener DOCUMENT_END;
	public static final ActionListener SELECT_ALL;
	public static final ActionListener SELECT_END;
	public static final ActionListener SELECT_DOC_END;
	public static final ActionListener INSERT_BREAK;
	public static final ActionListener INSERT_TAB;
	public static final ActionListener HOME;
	public static final ActionListener DOCUMENT_HOME;
	public static final ActionListener SELECT_HOME;
	public static final ActionListener SELECT_DOC_HOME;
	public static final ActionListener NEXT_CHAR;
	public static final ActionListener NEXT_LINE;
	public static final ActionListener NEXT_PAGE;
	public static final ActionListener NEXT_WORD;
	public static final ActionListener SELECT_NEXT_CHAR;
	public static final ActionListener SELECT_NEXT_LINE;
	public static final ActionListener SELECT_NEXT_PAGE;
	public static final ActionListener SELECT_NEXT_WORD;
	public static final ActionListener OVERWRITE;
	public static final ActionListener PREV_CHAR;
	public static final ActionListener PREV_LINE;
	public static final ActionListener PREV_PAGE;
	public static final ActionListener PREV_WORD;
	public static final ActionListener SELECT_PREV_CHAR;
	public static final ActionListener SELECT_PREV_LINE;
	public static final ActionListener SELECT_PREV_PAGE;
	public static final ActionListener SELECT_PREV_WORD;
	public static final ActionListener REPEAT;
	public static final ActionListener TOGGLE_RECT;
	public static final ActionListener CLIP_COPY;
	public static final ActionListener CLIP_PASTE;
	public static final ActionListener CLIP_CUT;
	public static final ActionListener INSERT_CHAR;
	private static Hashtable actions;
	protected ActionListener grabAction;
	protected boolean repeat;
	protected int repeatCount;
	protected MacroRecorder recorder;

	public InputHandler()
	{
	}

	public static ActionListener getAction(String name)
	{
		return (ActionListener)actions.get(name);
	}

	public static String getActionName(ActionListener listener)
	{
		for (Enumeration myEnum = getActions(); myEnum.hasMoreElements();)
		{
			String name = (String)myEnum.nextElement();
			ActionListener _listener = getAction(name);
			if (_listener == listener)
				return name;
		}

		return null;
	}

	public static Enumeration getActions()
	{
		return actions.keys();
	}

	public abstract void addDefaultKeyBindings();

	public abstract void addKeyBinding(String s, ActionListener actionlistener);

	public abstract void removeKeyBinding(String s);

	public abstract void removeAllKeyBindings();

	public void grabNextKeyStroke(ActionListener listener)
	{
		grabAction = listener;
	}

	public boolean isRepeatEnabled()
	{
		return repeat;
	}

	public void setRepeatEnabled(boolean repeat)
	{
		this.repeat = repeat;
	}

	public int getRepeatCount()
	{
		return repeat ? Math.max(1, repeatCount) : 1;
	}

	public void setRepeatCount(int repeatCount)
	{
		this.repeatCount = repeatCount;
	}

	public MacroRecorder getMacroRecorder()
	{
		return recorder;
	}

	public void setMacroRecorder(MacroRecorder recorder)
	{
		this.recorder = recorder;
	}

	public abstract InputHandler copy();

	public void executeAction(ActionListener listener, Object source, String actionCommand)
	{
		ActionEvent evt = new ActionEvent(source, 1001, actionCommand);
		if (listener instanceof Wrapper)
		{
			listener.actionPerformed(evt);
			return;
		}
		boolean _repeat = repeat;
		int _repeatCount = getRepeatCount();
		if (listener instanceof NonRepeatable)
		{
			listener.actionPerformed(evt);
		} else
		{
			for (int i = 0; i < Math.max(1, repeatCount); i++)
				listener.actionPerformed(evt);

		}
		if (grabAction == null)
		{
			if (recorder != null && !(listener instanceof NonRecordable))
			{
				if (_repeatCount != 1)
					recorder.actionPerformed(REPEAT, String.valueOf(_repeatCount));
				recorder.actionPerformed(listener, actionCommand);
			}
			if (_repeat)
			{
				repeat = false;
				repeatCount = 0;
			}
		}
	}

	public static JEditTextArea getTextArea(EventObject evt)
	{
		if (evt != null)
		{
			Object o = evt.getSource();
			if (o instanceof Component)
			{
				Component c = (Component)o;
				do
				{
					if (c instanceof JEditTextArea)
						return (JEditTextArea)c;
					if (c == null)
						break;
					if (c instanceof JPopupMenu)
						c = ((JPopupMenu)c).getInvoker();
					else
						c = c.getParent();
				} while (true);
			}
		}
		log.debug("BUG: getTextArea() returning null");
		log.debug("Report this to Slava Pestov <sp@gjt.org>");
		return null;
	}

	protected void handleGrabAction(KeyEvent evt)
	{
		ActionListener _grabAction = grabAction;
		grabAction = null;
		executeAction(_grabAction, evt.getSource(), String.valueOf(evt.getKeyChar()));
	}

	static 
	{
		BACKSPACE = new backspace();
		BACKSPACE_WORD = new backspace_word();
		DELETE = new delete();
		DELETE_WORD = new delete_word();
		END = new end(false);
		DOCUMENT_END = new document_end(false);
		SELECT_ALL = new select_all();
		SELECT_END = new end(true);
		SELECT_DOC_END = new document_end(true);
		INSERT_BREAK = new insert_break();
		INSERT_TAB = new insert_tab();
		HOME = new home(false);
		DOCUMENT_HOME = new document_home(false);
		SELECT_HOME = new home(true);
		SELECT_DOC_HOME = new document_home(true);
		NEXT_CHAR = new next_char(false);
		NEXT_LINE = new next_line(false);
		NEXT_PAGE = new next_page(false);
		NEXT_WORD = new next_word(false);
		SELECT_NEXT_CHAR = new next_char(true);
		SELECT_NEXT_LINE = new next_line(true);
		SELECT_NEXT_PAGE = new next_page(true);
		SELECT_NEXT_WORD = new next_word(true);
		OVERWRITE = new overwrite();
		PREV_CHAR = new prev_char(false);
		PREV_LINE = new prev_line(false);
		PREV_PAGE = new prev_page(false);
		PREV_WORD = new prev_word(false);
		SELECT_PREV_CHAR = new prev_char(true);
		SELECT_PREV_LINE = new prev_line(true);
		SELECT_PREV_PAGE = new prev_page(true);
		SELECT_PREV_WORD = new prev_word(true);
		REPEAT = new repeat();
		TOGGLE_RECT = new toggle_rect();
		CLIP_COPY = new clip_copy();
		CLIP_PASTE = new clip_paste();
		CLIP_CUT = new clip_cut();
		INSERT_CHAR = new insert_char();
		actions = new Hashtable();
		actions.put("backspace", BACKSPACE);
		actions.put("backspace-word", BACKSPACE_WORD);
		actions.put("delete", DELETE);
		actions.put("delete-word", DELETE_WORD);
		actions.put("end", END);
		actions.put("select-all", SELECT_ALL);
		actions.put("select-end", SELECT_END);
		actions.put("document-end", DOCUMENT_END);
		actions.put("select-doc-end", SELECT_DOC_END);
		actions.put("insert-break", INSERT_BREAK);
		actions.put("insert-tab", INSERT_TAB);
		actions.put("home", HOME);
		actions.put("select-home", SELECT_HOME);
		actions.put("document-home", DOCUMENT_HOME);
		actions.put("select-doc-home", SELECT_DOC_HOME);
		actions.put("next-char", NEXT_CHAR);
		actions.put("next-line", NEXT_LINE);
		actions.put("next-page", NEXT_PAGE);
		actions.put("next-word", NEXT_WORD);
		actions.put("select-next-char", SELECT_NEXT_CHAR);
		actions.put("select-next-line", SELECT_NEXT_LINE);
		actions.put("select-next-page", SELECT_NEXT_PAGE);
		actions.put("select-next-word", SELECT_NEXT_WORD);
		actions.put("overwrite", OVERWRITE);
		actions.put("prev-char", PREV_CHAR);
		actions.put("prev-line", PREV_LINE);
		actions.put("prev-page", PREV_PAGE);
		actions.put("prev-word", PREV_WORD);
		actions.put("select-prev-char", SELECT_PREV_CHAR);
		actions.put("select-prev-line", SELECT_PREV_LINE);
		actions.put("select-prev-page", SELECT_PREV_PAGE);
		actions.put("select-prev-word", SELECT_PREV_WORD);
		actions.put("repeat", REPEAT);
		actions.put("toggle-rect", TOGGLE_RECT);
		actions.put("insert-char", INSERT_CHAR);
		actions.put("clipboard-copy", CLIP_COPY);
		actions.put("clipboard-paste", CLIP_PASTE);
		actions.put("clipboard-cut", CLIP_CUT);
	}
}
