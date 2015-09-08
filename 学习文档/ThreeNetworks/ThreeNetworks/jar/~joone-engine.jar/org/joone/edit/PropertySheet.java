// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertySheet.java

package org.joone.edit;

import CH.ifa.draw.framework.HJDError;
import CH.ifa.draw.util.Iconkit;
import java.awt.*;
import javax.swing.JComponent;
import javax.swing.JFrame;
import org.joone.net.NeuralNet;

// Referenced classes of package org.joone.edit:
//			Wrapper, PropertyPanel

public class PropertySheet extends JFrame
{

	private PropertyPanel panel;
	protected Wrapper target;
	protected NeuralNet nNet;
	private static final long serialVersionUID = 0xcf25543ca28e70aaL;
	private boolean enabled;

	PropertySheet(int x, int y)
	{
		this(null, x, y, null);
	}

	PropertySheet(Wrapper wr, int x, int y)
	{
		this(wr, x, y, null);
	}

	PropertySheet(Wrapper wr, int x, int y, NeuralNet nn)
	{
		super("Properties - <initializing...>");
		target = wr;
		nNet = nn;
		if (target != null)
			setTarget(wr);
		initialize(x, y);
	}

	protected void initialize(int x, int y)
	{
		getContentPane().setLayout(new BorderLayout());
		setBackground(Color.gray);
		setLocation(x, y);
		getContentPane().add(getContents(), "Center");
		setTitle("Properties window");
		Iconkit kit = Iconkit.instance();
		if (kit == null)
		{
			throw new HJDError("Iconkit instance isn't set");
		} else
		{
			java.awt.Image img = kit.loadImageResource("/org/joone/images/JooneIcon.gif");
			setIconImage(img);
			pack();
			return;
		}
	}

	protected JComponent getContents()
	{
		return getPanel();
	}

	public void setTarget(Wrapper targ)
	{
		target = targ;
		String displayName = target.getBeanName();
		getPanel().setTarget(target);
		setTitle((new StringBuilder("Properties - ")).append(displayName).toString());
	}

	protected PropertyPanel getPanel()
	{
		if (panel == null)
			panel = new PropertyPanel();
		return panel;
	}

	public boolean isControlsEnabled()
	{
		return enabled;
	}

	public void setControlsEnabled(boolean enabled)
	{
		this.enabled = enabled;
		getPanel().setEnabled(enabled);
	}

	public void update()
	{
		getPanel().update();
	}
}
