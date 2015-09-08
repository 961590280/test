// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DefaultInputHandler.java

package org.joone.edit.jedit;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.StringTokenizer;
import javax.swing.KeyStroke;
import org.joone.log.ILogger;

// Referenced classes of package org.joone.edit.jedit:
//			InputHandler

public class DefaultInputHandler extends InputHandler
{

	private Hashtable bindings;
	private Hashtable currentBindings;

	public DefaultInputHandler()
	{
		bindings = currentBindings = new Hashtable();
	}

	public void addDefaultKeyBindings()
	{
		addKeyBinding("BACK_SPACE", BACKSPACE);
		addKeyBinding("C+BACK_SPACE", BACKSPACE_WORD);
		addKeyBinding("DELETE", DELETE);
		addKeyBinding("C+DELETE", DELETE_WORD);
		addKeyBinding("ENTER", INSERT_BREAK);
		addKeyBinding("TAB", INSERT_TAB);
		addKeyBinding("INSERT", OVERWRITE);
		addKeyBinding("C+\\", TOGGLE_RECT);
		addKeyBinding("HOME", HOME);
		addKeyBinding("END", END);
		addKeyBinding("C+A", SELECT_ALL);
		addKeyBinding("S+HOME", SELECT_HOME);
		addKeyBinding("S+END", SELECT_END);
		addKeyBinding("C+HOME", DOCUMENT_HOME);
		addKeyBinding("C+END", DOCUMENT_END);
		addKeyBinding("CS+HOME", SELECT_DOC_HOME);
		addKeyBinding("CS+END", SELECT_DOC_END);
		addKeyBinding("PAGE_UP", PREV_PAGE);
		addKeyBinding("PAGE_DOWN", NEXT_PAGE);
		addKeyBinding("S+PAGE_UP", SELECT_PREV_PAGE);
		addKeyBinding("S+PAGE_DOWN", SELECT_NEXT_PAGE);
		addKeyBinding("LEFT", PREV_CHAR);
		addKeyBinding("S+LEFT", SELECT_PREV_CHAR);
		addKeyBinding("C+LEFT", PREV_WORD);
		addKeyBinding("CS+LEFT", SELECT_PREV_WORD);
		addKeyBinding("RIGHT", NEXT_CHAR);
		addKeyBinding("S+RIGHT", SELECT_NEXT_CHAR);
		addKeyBinding("C+RIGHT", NEXT_WORD);
		addKeyBinding("CS+RIGHT", SELECT_NEXT_WORD);
		addKeyBinding("UP", PREV_LINE);
		addKeyBinding("S+UP", SELECT_PREV_LINE);
		addKeyBinding("DOWN", NEXT_LINE);
		addKeyBinding("S+DOWN", SELECT_NEXT_LINE);
		addKeyBinding("C+ENTER", REPEAT);
		addKeyBinding("C+C", CLIP_COPY);
		addKeyBinding("C+V", CLIP_PASTE);
		addKeyBinding("C+X", CLIP_CUT);
	}

	public void addKeyBinding(String keyBinding, ActionListener action)
	{
		Hashtable current = bindings;
		for (StringTokenizer st = new StringTokenizer(keyBinding); st.hasMoreTokens();)
		{
			KeyStroke keyStroke = parseKeyStroke(st.nextToken());
			if (keyStroke == null)
				return;
			if (st.hasMoreTokens())
			{
				Object o = current.get(keyStroke);
				if (o instanceof Hashtable)
				{
					current = (Hashtable)o;
				} else
				{
					o = new Hashtable();
					current.put(keyStroke, o);
					current = (Hashtable)o;
				}
			} else
			{
				current.put(keyStroke, action);
			}
		}

	}

	public void removeKeyBinding(String keyBinding)
	{
		throw new InternalError("Not yet implemented");
	}

	public void removeAllKeyBindings()
	{
		bindings.clear();
	}

	public InputHandler copy()
	{
		return new DefaultInputHandler(this);
	}

	public void keyPressed(KeyEvent evt)
	{
		int keyCode = evt.getKeyCode();
		int modifiers = evt.getModifiers();
		if (keyCode == 17 || keyCode == 16 || keyCode == 18 || keyCode == 157)
			return;
		if ((modifiers & -2) != 0 || evt.isActionKey() || keyCode == 8 || keyCode == 127 || keyCode == 10 || keyCode == 9 || keyCode == 27)
		{
			if (grabAction != null)
			{
				handleGrabAction(evt);
				return;
			}
			KeyStroke keyStroke = KeyStroke.getKeyStroke(keyCode, modifiers);
			Object o = currentBindings.get(keyStroke);
			if (o == null)
			{
				if (currentBindings != bindings)
				{
					Toolkit.getDefaultToolkit().beep();
					repeatCount = 0;
					repeat = false;
					evt.consume();
				}
				currentBindings = bindings;
				return;
			}
			if (o instanceof ActionListener)
			{
				currentBindings = bindings;
				executeAction((ActionListener)o, evt.getSource(), null);
				evt.consume();
				return;
			}
			if (o instanceof Hashtable)
			{
				currentBindings = (Hashtable)o;
				evt.consume();
				return;
			}
		}
	}

	public void keyTyped(KeyEvent evt)
	{
		int modifiers = evt.getModifiers();
		char c = evt.getKeyChar();
		if (c != '\uFFFF' && (modifiers & 8) == 0 && c >= ' ' && c != '\177')
		{
			KeyStroke keyStroke = KeyStroke.getKeyStroke(Character.toUpperCase(c));
			Object o = currentBindings.get(keyStroke);
			if (o instanceof Hashtable)
			{
				currentBindings = (Hashtable)o;
				return;
			}
			if (o instanceof ActionListener)
			{
				currentBindings = bindings;
				executeAction((ActionListener)o, evt.getSource(), String.valueOf(c));
				return;
			}
			currentBindings = bindings;
			if (grabAction != null)
			{
				handleGrabAction(evt);
				return;
			}
			if (repeat && Character.isDigit(c))
			{
				repeatCount *= 10;
				repeatCount += c - 48;
				return;
			}
			executeAction(INSERT_CHAR, evt.getSource(), String.valueOf(evt.getKeyChar()));
			repeatCount = 0;
			repeat = false;
		}
	}

	public static KeyStroke parseKeyStroke(String keyStroke)
	{
		if (keyStroke == null)
			return null;
		int modifiers = 0;
		int index = keyStroke.indexOf('+');
		if (index != -1)
		{
			for (int i = 0; i < index; i++)
				switch (Character.toUpperCase(keyStroke.charAt(i)))
				{
				case 65: // 'A'
					modifiers |= 8;
					break;

				case 67: // 'C'
					modifiers |= 2;
					break;

				case 77: // 'M'
					modifiers |= 4;
					break;

				case 83: // 'S'
					modifiers |= 1;
					break;
				}

		}
		String key = keyStroke.substring(index + 1);
		int ch;
		if (key.length() == 1)
		{
			ch = Character.toUpperCase(key.charAt(0));
			if (modifiers == 0)
				return KeyStroke.getKeyStroke(ch);
			else
				return KeyStroke.getKeyStroke(ch, modifiers);
		}
		if (key.length() == 0)
		{
			log.error((new StringBuilder("Invalid key stroke: ")).append(keyStroke).toString());
			return null;
		}
		try
		{
			ch = java/awt/event/KeyEvent.getField("VK_".concat(key)).getInt(null);
		}
		catch (Exception e)
		{
			log.warn((new StringBuilder("Invalid key stroke: ")).append(keyStroke).toString());
			return null;
		}
		return KeyStroke.getKeyStroke(ch, modifiers);
	}

	private DefaultInputHandler(DefaultInputHandler copy)
	{
		bindings = currentBindings = copy.bindings;
	}
}
