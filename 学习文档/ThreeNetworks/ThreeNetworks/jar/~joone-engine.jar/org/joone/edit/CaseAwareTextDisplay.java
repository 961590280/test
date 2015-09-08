// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CaseAwareTextDisplay.java

package org.joone.edit;

import java.awt.*;
import java.beans.*;
import java.io.Serializable;
import javax.swing.text.StyleContext;

public class CaseAwareTextDisplay extends Canvas
	implements Serializable
{

	public static final int AS_IS = 0;
	public static final int UPPERCASE = 1;
	public static final int LOWERCASE = 2;
	public static final int FIRST_IN_CAPS = 3;
	public static final String TEXT = "text";
	public static final String TOP_MARGIN = "topMargin";
	public static final String LEFT_MARGIN = "leftMargin";
	public static final String FOREGROUND = "foreground";
	public static final String BACKGROUND = "background";
	public static final String TEXT_CASE = "textCase";
	public static final String FONT = "font";
	static final long serialVersionUID = 0xaac5f7c2df8fd8c0L;
	protected String text;
	protected int topMargin;
	protected int leftMargin;
	protected int textCase;
	protected PropertyChangeSupport propertyListenerSupport;
	protected VetoableChangeSupport vetoListenerSupport;

	public CaseAwareTextDisplay()
	{
		text = "default text";
		topMargin = 4;
		leftMargin = 4;
		textCase = 0;
		setSize(180, 30);
		propertyListenerSupport = new PropertyChangeSupport(this);
		vetoListenerSupport = new VetoableChangeSupport(this);
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		String oldValue = this.text;
		this.text = text;
		firePropertyChange("text", oldValue, this.text);
	}

	public int getTopMargin()
	{
		return topMargin;
	}

	public void setTopMargin(int pixels)
	{
		int oldValue = topMargin;
		topMargin = pixels;
		firePropertyChange("topMargin", new Integer(oldValue), new Integer(topMargin));
	}

	public int getLeftMargin()
	{
		return leftMargin;
	}

	public void setLeftMargin(int pixels)
	{
		int oldValue = leftMargin;
		leftMargin = pixels;
		firePropertyChange("leftMargin", new Integer(oldValue), new Integer(leftMargin));
	}

	public void setForeground(Color newColor)
	{
		Color oldValue = getForeground();
		try
		{
			fireVetoableChange("foreground", oldValue, newColor);
			super.setForeground(newColor);
			firePropertyChange("foreground", oldValue, newColor);
			repaint();
		}
		catch (PropertyVetoException propertyvetoexception) { }
	}

	public void setBackground(Color newColor)
	{
		Color oldValue = getBackground();
		try
		{
			fireVetoableChange("background", oldValue, newColor);
			super.setBackground(newColor);
			firePropertyChange("background", oldValue, newColor);
			repaint();
		}
		catch (PropertyVetoException propertyvetoexception) { }
	}

	public int getTextCase()
	{
		return textCase;
	}

	public void setTextCase(int textCase)
	{
		int oldValue = this.textCase;
		this.textCase = textCase;
		firePropertyChange("textCase", new Integer(oldValue), new Integer(this.textCase));
		repaint();
	}

	public void paint(Graphics g)
	{
		String convertedText = getText();
		if (convertedText == null)
			convertedText = " ";
		switch (textCase)
		{
		case 0: // '\0'
		default:
			break;

		case 1: // '\001'
			convertedText = convertedText.toUpperCase();
			break;

		case 2: // '\002'
			convertedText = convertedText.toLowerCase();
			break;

		case 3: // '\003'
			convertedText = convertedText.toLowerCase();
			char temp[] = convertedText.toCharArray();
			char previous = ' ';
			for (int i = 0; i < temp.length; i++)
			{
				if (previous == ' ')
					temp[i] = Character.toUpperCase(temp[i]);
				previous = temp[i];
			}

			convertedText = new String(temp);
			break;
		}
		Rectangle r = getBounds();
		Font font = getFont();
		if (font == null)
			font = new Font("Dialog", 0, 12);
		FontMetrics fm = StyleContext.getDefaultStyleContext().getFontMetrics(font);
		int x = leftMargin;
		int y = topMargin + fm.getAscent();
		Color color = getBackground() != null ? getBackground() : Color.white;
		g.setColor(color);
		g.fillRect(0, 0, r.width, r.height);
		color = getForeground() != null ? getForeground() : Color.black;
		g.setColor(color);
		g.setFont(getFont());
		g.drawString(convertedText, x, y);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		propertyListenerSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
		propertyListenerSupport.removePropertyChangeListener(listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue)
	{
		propertyListenerSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	public void addVetoableChangeListener(VetoableChangeListener vetoListener)
	{
		vetoListenerSupport.addVetoableChangeListener(vetoListener);
	}

	public void removeVetoableChangeListener(VetoableChangeListener vetoListener)
	{
		vetoListenerSupport.removeVetoableChangeListener(vetoListener);
	}

	protected void fireVetoableChange(String propertyName, Object oldValue, Object newValue)
		throws PropertyVetoException
	{
		vetoListenerSupport.fireVetoableChange(propertyName, oldValue, newValue);
	}
}
