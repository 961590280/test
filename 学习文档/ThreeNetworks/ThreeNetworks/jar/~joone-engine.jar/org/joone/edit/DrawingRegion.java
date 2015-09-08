// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DrawingRegion.java

package org.joone.edit;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Enumeration;
import java.util.Hashtable;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.edit:
//			SharedBuffer, ChartingHandle

public class DrawingRegion extends Canvas
	implements Runnable
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/edit/DrawingRegion);
	private static final int ERROR = 0;
	private static final int CYCLE = 1;
	private int X_ENHETER;
	private double X_INTERVALL;
	private int Y_ENHETER;
	private int MAX_X;
	private int MAX_Y;
	private double grafData[];
	private Hashtable grafDataMul;
	private SharedBuffer grafBuffer;
	private Hashtable buffers;
	private Thread myThread;
	private Dimension myDim;
	private int currentCycle;
	private boolean keepPlotting;
	private Color original;
	private Color background;
	private Color gray;
	private int max_x_value;
	private double max_y_value;
	private double stepY;
	private double stepX;
	private int X;
	private int Y;
	private BufferedImage bi;
	private Graphics big;
	private static final long serialVersionUID = 0xe8a946c99ba76917L;

	public DrawingRegion(Dimension dimension, double ySpredning, int xSpredning)
	{
		X_ENHETER = 0;
		X_INTERVALL = 0.0D;
		Y_ENHETER = 10;
		currentCycle = 0;
		keepPlotting = true;
		X = 45;
		Y = 25;
		if (dimension.width < X && dimension.height < Y)
			log.warn((new StringBuilder("Drawing area too small, use min ")).append(X).append(" * ").append(Y).append(" pixels.").toString());
		else
		if (xSpredning < 2)
			log.warn("X Spredning must be > 1");
		else
		if (ySpredning <= 0.0D)
		{
			log.warn("Y Spredning must be > 0.0");
		} else
		{
			myDim = dimension;
			max_x_value = xSpredning;
			max_y_value = ySpredning;
			setSize(myDim);
			getXEnhet(xSpredning);
			constructorInit();
		}
	}

	public DrawingRegion(Dimension dimension, int xSpredning)
	{
		this(dimension, 1.0D, xSpredning);
	}

	public DrawingRegion(int xSpredning)
	{
		this(new Dimension(550, 350), xSpredning);
	}

	private void constructorInit()
	{
		buffers = new Hashtable();
		grafData = new double[max_x_value + 1];
		grafDataMul = new Hashtable();
		original = new Color(0, 0, 0);
		background = new Color(200, 200, 200);
		gray = new Color(160, 160, 160);
		setBackground(background);
		setDimensions(myDim);
		start();
	}

	private void setDimensions(Dimension dimension)
	{
		myDim = dimension;
		double tempX = myDim.width - 2 * X;
		double tempY = myDim.height - 2 * Y;
		MAX_X = (new Double(tempX)).intValue();
		MAX_Y = (new Double(tempY)).intValue();
		stepX = (double)MAX_X / (double)max_x_value;
		stepY = (double)MAX_Y / max_y_value;
	}

	private void getXEnhet(int xSpredning)
	{
		X_INTERVALL = xSpredning / 5;
		X_ENHETER = (new Double((double)xSpredning / X_INTERVALL)).intValue();
	}

	public void stopPloting()
	{
		keepPlotting = false;
	}

	public SharedBuffer getBuffer()
	{
		if (grafBuffer == null)
			grafBuffer = new SharedBuffer();
		return grafBuffer;
	}

	public SharedBuffer getBuffer(ChartingHandle handle)
	{
		SharedBuffer sb = (SharedBuffer)buffers.get(handle);
		if (sb == null)
		{
			sb = new SharedBuffer();
			sb.setHandle(handle);
			buffers.put(handle, sb);
		}
		return sb;
	}

	public void paint(Graphics g)
	{
		update(g);
	}

	public void update(Graphics g)
	{
		Dimension dim = getSize();
		if (bi == null || bi.getWidth() != dim.width || bi.getHeight() != dim.height)
		{
			bi = new BufferedImage(dim.width, dim.height, 1);
			big = bi.getGraphics();
		}
		big.setColor(background);
		big.fillRect(0, 0, dim.width, dim.height);
		if (myDim.height != dim.height || myDim.width != dim.width)
			setDimensions(dim);
		initDrawingRegion(big);
		if (grafBuffer != null)
			plot(big, grafData, new Color(0, 0, 200));
		for (Enumeration k = buffers.keys(); k.hasMoreElements();)
		{
			ChartingHandle handle = (ChartingHandle)k.nextElement();
			double data[] = (double[])grafDataMul.get(handle);
			Color col = new Color(handle.getRedColor(), handle.getGreenColor(), handle.getBlueColor());
			if (data != null)
				plot(big, data, col);
		}

		big.setColor(original);
		int plassering = (new Double((double)myDim.width * 0.5D)).intValue();
		big.drawString((new StringBuilder("Current cycle is ")).append(currentCycle).toString(), plassering, 70);
		g.drawImage(bi, 0, 0, this);
	}

	private void initDrawingRegion(Graphics g)
	{
		g.setColor(original);
		g.drawRect(0, 0, myDim.width - 1, myDim.height - 1);
		g.drawString("Y", 20, 12);
		plotLine(g, 0, 0.0D, 0, max_y_value);
		g.drawString("X", myDim.width - 20, myDim.height - 20);
		plotLine(g, 0, 0.0D, max_x_value, 0.0D);
		g.drawString("0,0", 10, myDim.height - 10);
		int xx = (new Float(max_x_value / X_ENHETER)).intValue();
		int x = 0;
		String text = (new Integer((new Double(X_INTERVALL)).intValue())).toString();
		for (int i = 1; i < X_ENHETER + 1;)
		{
			x += xx;
			g.drawString(text, getAbsX(x) - 15, myDim.height - 2);
			plotLine(g, x, 5D / stepY, x, -5D / stepY);
			plotXGridLine(g, x);
			text = (new Integer((new Double(X_INTERVALL * (double)(++i))).intValue())).toString();
		}

		Y_ENHETER = 10;
		double yy = max_y_value / (double)Y_ENHETER;
		double y = 0.0D;
		int prc = getPrecision(max_y_value);
		int exp = (int)Math.pow(10D, prc);
		for (int i = 1; i < Y_ENHETER + 1; i++)
		{
			y += yy;
			double valY = (max_y_value / (double)Y_ENHETER) * (double)((Y_ENHETER + 1) - i);
			int truncY = (new Double(valY * (double)exp)).intValue();
			valY = (new Double(truncY)).doubleValue() / (double)exp;
			text = (new Double(valY)).toString();
			plotLine(g, (new Double(-5D / stepX)).intValue(), y, (new Double(5D / stepX)).intValue(), y);
			plotYGridLine(g, y);
			g.drawString(text, 2, getAbsY((max_y_value - y) + yy) + 2);
		}

	}

	private int getPrecision(double xMax)
	{
		int div = (new Double(10D / xMax)).intValue();
		if (div == 0)
			div = 1;
		double log10 = Math.log(div) / Math.log(10D);
		int dec = (new Double(log10)).intValue() + 2;
		return dec;
	}

	private void plotLine(Graphics g, int start_x, double start_y, int slutt_x, double slutt_y)
	{
		int x1 = getAbsX(start_x);
		int y1 = getAbsY(start_y);
		int x2 = getAbsX(slutt_x);
		int y2 = getAbsY(slutt_y);
		g.drawLine(x1, y1, x2, y2);
	}

	private int getAbsX(int x)
	{
		int x1 = (new Double((double)x * stepX)).intValue();
		return x1 + X;
	}

	private int getAbsY(double y)
	{
		int y1 = (new Double(y * stepY)).intValue();
		return myDim.height - Y - y1;
	}

	private void plotYGridLine(Graphics g, double y)
	{
		g.setColor(gray);
		plotLine(g, 0, y, max_x_value, y);
		g.setColor(original);
	}

	private void plotXGridLine(Graphics g, int x)
	{
		g.setColor(gray);
		plotLine(g, x, 0.0D, x, max_y_value);
		g.setColor(original);
	}

	private void plot(Graphics g, double grafData[], Color col)
	{
		boolean firstPoint = false;
		g.setColor(col);
		for (int i = 2; i <= currentCycle; i++)
			if (grafData[i - 1] != 0.0D || firstPoint)
			{
				plotLine(g, i - 1, grafData[i - 1], i, grafData[i]);
				firstPoint = true;
			}

	}

	public void start()
	{
		if (myThread == null)
		{
			myThread = new Thread(this, "DrawingRegion");
			try
			{
				myThread.start();
			}
			catch (Exception e)
			{
				String msg = (new StringBuilder("DrawingRegion:start() : ")).append(e.getMessage()).toString();
				log.fatal(msg, e);
				System.exit(1);
			}
		}
	}

	public void run()
	{
		int i = 0;
		while (keepPlotting) 
		{
			if (grafBuffer != null)
				i = getData(null);
			int imax = i;
			for (Enumeration k = buffers.keys(); k.hasMoreElements();)
			{
				ChartingHandle handle = (ChartingHandle)k.nextElement();
				i = getData(handle);
				if (i > imax)
					imax = i;
			}

			if (imax > 0)
			{
				currentCycle = imax;
				repaint();
			}
			try
			{
				Thread.sleep(500L);
			}
			catch (InterruptedException interruptedexception) { }
		}
		myThread = null;
	}

	private int getData(ChartingHandle handle)
	{
		double error[][];
		double data[];
		if (handle == null)
		{
			error = grafBuffer.get();
			data = grafData;
		} else
		{
			SharedBuffer buff = (SharedBuffer)buffers.get(handle);
			error = buff.getNoWait();
			data = (double[])grafDataMul.get(handle);
			if (data == null)
			{
				data = new double[max_x_value + 1];
				grafDataMul.put(handle, data);
			}
		}
		int cycle = 0;
		if (error != null)
		{
			for (int i = 0; i < error.length; i++)
			{
				int x = (new Double(error[i][1])).intValue();
				if (x == -1 || x >= data.length)
					break;
				data[x] = error[i][0];
				cycle = x;
			}

		}
		return cycle;
	}

	public int getMaxXvalue()
	{
		return max_x_value;
	}

	public void setMaxXvalue(int max_x_value)
	{
		this.max_x_value = max_x_value;
		copyBuffer(max_x_value);
		getXEnhet(max_x_value);
		setDimensions(myDim);
		repaint();
	}

	public double getMaxYvalue()
	{
		return max_y_value;
	}

	public void setMaxYvalue(double max_y_value)
	{
		this.max_y_value = max_y_value;
		setDimensions(myDim);
		repaint();
	}

	private void copyBuffer(int newSize)
	{
		double newBuff[] = new double[newSize + 1];
		int xMax = newSize;
		if (xMax > grafData.length)
			xMax = grafData.length;
		for (int i = 0; i < xMax; i++)
			newBuff[i] = grafData[i];

		if (currentCycle >= xMax)
			currentCycle = xMax - 1;
		grafData = newBuff;
	}

	protected void removeHandle(ChartingHandle handle)
	{
		if (buffers != null)
			buffers.remove(handle);
		if (grafDataMul != null)
			grafDataMul.remove(handle);
	}

}
