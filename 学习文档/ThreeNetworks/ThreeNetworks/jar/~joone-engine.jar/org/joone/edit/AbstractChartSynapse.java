// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AbstractChartSynapse.java

package org.joone.edit;

import CH.ifa.draw.framework.HJDError;
import CH.ifa.draw.util.Iconkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.TreeSet;
import javax.swing.JFrame;
import org.joone.engine.Monitor;
import org.joone.engine.Pattern;
import org.joone.util.NotSerialize;

// Referenced classes of package org.joone.edit:
//			ChartInterface, ChartingHandle

public abstract class AbstractChartSynapse
	implements ChartInterface, NotSerialize, Serializable
{

	private static final long serialVersionUID = 0x25d6851905722ed4L;
	protected boolean show;
	protected Monitor monitor;
	protected String name;
	protected String title;
	protected boolean resizable;
	protected int maxXaxis;
	protected double maxYaxis;
	protected int serie;
	protected boolean outputFull;
	protected transient JFrame iFrame;
	private boolean enabled;

	public AbstractChartSynapse()
	{
		name = "";
		title = "";
		resizable = true;
		maxXaxis = 10000;
		maxYaxis = 1.0D;
		serie = 1;
		enabled = true;
		initComponents();
	}

	protected JFrame getFrame()
	{
		if (iFrame == null)
			iFrame = new JFrame();
		return iFrame;
	}

	protected void initComponents()
	{
		getFrame().setTitle(getTitle());
		iFrame.setResizable(isResizable());
		iFrame.addWindowListener(new WindowAdapter() {

			final AbstractChartSynapse this$0;

			public void windowClosing(WindowEvent evt)
			{
				exitForm(evt);
			}

			
			{
				this$0 = AbstractChartSynapse.this;
				super();
			}
		});
		Iconkit kit = Iconkit.instance();
		if (kit == null)
		{
			throw new HJDError("Iconkit instance isn't set");
		} else
		{
			java.awt.Image img = kit.loadImageResource("/org/joone/images/jooneIcon.gif");
			iFrame.setIconImage(img);
			iFrame.pack();
			return;
		}
	}

	private void exitForm(WindowEvent evt)
	{
		setShow(false);
	}

	public void setMonitor(Monitor newMonitor)
	{
		monitor = newMonitor;
	}

	public Pattern revGet()
	{
		return null;
	}

	public void setInputDimension(int i)
	{
	}

	public Monitor getMonitor()
	{
		return monitor;
	}

	public int getInputDimension()
	{
		return 0;
	}

	public boolean isShow()
	{
		return show;
	}

	public void setShow(boolean show)
	{
		this.show = show;
		if (show)
			getFrame().setVisible(true);
		else
			getFrame().setVisible(false);
	}

	public double getMaxYaxis()
	{
		return maxYaxis;
	}

	public void setMaxYaxis(double maxYaxis)
	{
		this.maxYaxis = maxYaxis;
	}

	public int getMaxXaxis()
	{
		return maxXaxis;
	}

	public void setMaxXaxis(int maxXaxis)
	{
		this.maxXaxis = maxXaxis;
	}

	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		initComponents();
		setShow(false);
	}

	public int getSerie()
	{
		if (serie < 1)
			serie = 1;
		return serie;
	}

	public void setSerie(int newSerie)
	{
		if (newSerie < 1)
			serie = 1;
		else
			serie = newSerie;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
		if (iFrame != null)
			iFrame.setTitle(title);
	}

	public boolean isResizable()
	{
		return resizable;
	}

	public void setResizable(boolean resizable)
	{
		this.resizable = resizable;
		if (iFrame != null)
			iFrame.setResizable(resizable);
	}

	public TreeSet check()
	{
		TreeSet checks = new TreeSet();
		return checks;
	}

	public boolean isOutputFull()
	{
		return outputFull;
	}

	public void setOutputFull(boolean outputFull)
	{
		this.outputFull = outputFull;
	}

	public abstract void fwdPut(Pattern pattern);

	public abstract void fwdPut(Pattern pattern, ChartingHandle chartinghandle);

	public abstract void removeHandle(ChartingHandle chartinghandle);

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public void init()
	{
	}

}
