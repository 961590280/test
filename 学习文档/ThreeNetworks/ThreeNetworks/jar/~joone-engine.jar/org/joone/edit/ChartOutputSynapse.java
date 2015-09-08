// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ChartOutputSynapse.java

package org.joone.edit;

import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JFrame;
import org.joone.engine.NetErrorManager;
import org.joone.engine.Pattern;

// Referenced classes of package org.joone.edit:
//			AbstractChartSynapse, DrawingRegion, ChartingHandle, SharedBuffer

public class ChartOutputSynapse extends AbstractChartSynapse
{

	private static final long serialVersionUID = 0x681a166ed39a6d91L;
	private transient DrawingRegion myDrawingRegion;
	private transient SharedBuffer grafBuffer;

	public ChartOutputSynapse()
	{
	}

	private void initDrawingRegion(int mainTrainingCycles)
	{
		iFrame.getContentPane().removeAll();
		myDrawingRegion = new DrawingRegion(new Dimension(550, 350), getMaxYaxis(), mainTrainingCycles);
		iFrame.getContentPane().add(myDrawingRegion, "Center");
		iFrame.setSize(560, 360);
		iFrame.pack();
	}

	public void setMaxYaxis(double maxYaxis)
	{
		super.setMaxYaxis(maxYaxis);
		if (myDrawingRegion != null)
			myDrawingRegion.setMaxYvalue(maxYaxis);
	}

	public void setMaxXaxis(int maxXaxis)
	{
		super.setMaxXaxis(maxXaxis);
		if (myDrawingRegion != null)
			myDrawingRegion.setMaxXvalue(maxXaxis);
	}

	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		if (getTitle() == null)
		{
			setTitle("Chart");
			setName("Chart");
			setMaxXaxis(10000);
			setMaxYaxis(1.0D);
			setResizable(true);
			setSerie(1);
			super.initComponents();
		}
		myDrawingRegion = null;
	}

	public void fwdPut(Pattern pattern, ChartingHandle handle)
	{
		if (isEnabled())
		{
			SharedBuffer buffer = getDrawingRegion().getBuffer(handle);
			fwdPut(pattern, handle.getSerie(), buffer);
		}
	}

	public void fwdPut(Pattern pattern)
	{
		if (isEnabled())
		{
			if (getSerie() > pattern.getArray().length)
			{
				new NetErrorManager(getMonitor(), (new StringBuilder("ChartOutputSynapse: '")).append(getName()).append("' - Serie greater than the pattern's length").toString());
				return;
			}
			grafBuffer = getDrawingRegion().getBuffer();
			fwdPut(pattern, getSerie(), grafBuffer);
		}
	}

	private void fwdPut(Pattern pattern, int numSerie, SharedBuffer buffer)
	{
		if (pattern.getCount() > -1)
		{
			double array[] = pattern.getArray();
			double cycle = pattern.getCount();
			buffer.put(array[numSerie - 1], cycle);
		}
	}

	private DrawingRegion getDrawingRegion()
	{
		if (myDrawingRegion == null)
			initDrawingRegion(maxXaxis);
		return myDrawingRegion;
	}

	public void removeHandle(ChartingHandle handle)
	{
		if (myDrawingRegion != null)
			myDrawingRegion.removeHandle(handle);
	}
}
