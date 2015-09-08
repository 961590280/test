// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JooneFileChooserEditor.java

package org.joone.edit;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyEditorSupport;
import java.io.File;

// Referenced classes of package org.joone.edit:
//			JooneFileChooser, CaseAwareTextDisplay

public class JooneFileChooserEditor extends PropertyEditorSupport
	implements ActionListener
{

	private JooneFileChooser fileChooser;
	private CaseAwareTextDisplay fileChooserPanel;

	public JooneFileChooserEditor()
	{
		fileChooser = new JooneFileChooser();
		fileChooser.addActionListener(this);
		fileChooserPanel = new CaseAwareTextDisplay();
		fileChooserPanel.setText("wordA wordB");
	}

	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		if (command.equals("ApproveSelection"))
			setValue(getValue());
	}

	public boolean isPaintable()
	{
		return true;
	}

	public void paintValue(Graphics g, Rectangle box)
	{
		g.clipRect(box.x, box.y, box.width, box.height);
		g.translate(box.x, box.y);
		g.setColor(Color.white);
		g.fillRect(0, 0, box.width, box.height);
		g.setColor(Color.black);
		fileChooserPanel.paint(g);
	}

	public void setValue(Object value)
	{
		File file = new File((String)value);
		fileChooser.setSelectedFile(file);
		fileChooserPanel.setText(file.getName());
		firePropertyChange();
	}

	public Object getValue()
	{
		return fileChooser.getSelectedFile().getAbsolutePath();
	}

	public Component getCustomEditor()
	{
		return fileChooser;
	}

	public boolean supportsCustomEditor()
	{
		return true;
	}
}
