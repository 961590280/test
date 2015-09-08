// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MonitorPluginFigure.java

package org.joone.edit;

import CH.ifa.draw.figures.TextFigure;
import java.awt.Color;
import java.awt.Font;
import java.util.Vector;
import org.joone.net.NeuralNet;
import org.joone.util.MonitorPlugin;

// Referenced classes of package org.joone.edit:
//			ConcreteGenericFigure, Wrapper, UpdatableTextFigure

public class MonitorPluginFigure extends ConcreteGenericFigure
{

	protected MonitorPlugin myMonitorPlugin;
	private static final long serialVersionUID = 0xa12d49e23fbebacL;

	public MonitorPluginFigure()
	{
	}

	public Object getLayer()
	{
		return myMonitorPlugin;
	}

	public Wrapper getWrapper()
	{
		MonitorPlugin ly = (MonitorPlugin)getLayer();
		return new Wrapper(this, ly, ly.getName());
	}

	protected void initContent()
	{
		myMonitorPlugin = (MonitorPlugin)createLayer();
		Font fb = new Font("Helvetica", 1, 12);
		TextFigure name = new UpdatableTextFigure() {

			final MonitorPluginFigure this$0;

			public void setText(String newText)
			{
				super.setText(newText);
				MonitorPlugin ly = (MonitorPlugin)getLayer();
				ly.setName(newText);
			}

			public void update()
			{
				MonitorPlugin ly = (MonitorPlugin)getLayer();
				setText(ly.getName());
			}

			
			{
				this$0 = MonitorPluginFigure.this;
				super();
			}
		};
		name.setFont(fb);
		name.setText((new StringBuilder("Monitor Plugin ")).append(++numLayers).toString());
		name.setAttribute("TextColor", Color.blue);
		add(name);
	}

	protected Vector addHandles(Vector handles)
	{
		return handles;
	}

	public void initialize()
	{
		super.initialize();
		((MonitorPlugin)getLayer()).setNeuralNet((NeuralNet)getParam("NeuralNet"));
	}
}
