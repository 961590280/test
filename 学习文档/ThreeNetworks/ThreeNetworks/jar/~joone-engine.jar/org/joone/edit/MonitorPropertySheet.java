// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MonitorPropertySheet.java

package org.joone.edit;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.joone.net.NeuralNet;

// Referenced classes of package org.joone.edit:
//			PropertySheet, ControlPanel, Wrapper, EditorParameters

public class MonitorPropertySheet extends PropertySheet
{

	private ControlPanel cp;
	private static final long serialVersionUID = 0x758bf60f7e5ed31L;

	public MonitorPropertySheet(Wrapper wr, NeuralNet nn)
	{
		super(wr, 300, 100, nn);
	}

	protected JComponent getContents()
	{
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		JComponent propPanel = super.getPanel();
		cp = new ControlPanel(nNet, this);
		jp.add(cp, "North");
		jp.add(propPanel, "Center");
		return jp;
	}

	public void setParameters(EditorParameters parameters)
	{
		if (cp != null)
			cp.setParameters(parameters);
	}
}
