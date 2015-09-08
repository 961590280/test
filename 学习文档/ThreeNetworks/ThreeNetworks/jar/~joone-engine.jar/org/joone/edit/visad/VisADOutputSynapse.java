// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   VisADOutputSynapse.java

package org.joone.edit.visad;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.rmi.RemoteException;
import java.util.*;
import javax.swing.*;
import org.joone.edit.*;
import org.joone.engine.*;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.util.NotSerialize;
import visad.*;
import visad.java2d.DisplayImplJ2D;
import visad.util.GMCWidget;
import visad.util.RangeWidget;

public class VisADOutputSynapse
	implements NotSerialize, Serializable, NeuralNetListener, ChartInterface
{

	private static final long serialVersionUID = 0x681c38213677ed91L;
	private static final ILogger log = LoggerFactory.getLogger(org/joone/edit/visad/VisADOutputSynapse);
	private boolean show;
	private Monitor monitor;
	private String name;
	private String title;
	private boolean resizable;
	private int maxXaxis;
	private double maxYaxis;
	private int serie;
	private boolean outputFull;
	private transient JFrame iFrame;
	private boolean enabled;
	private transient ChartingHandle def_handle;
	private transient JLabel status_label;
	private transient JPanel pane;
	private transient JPanel ControlPane;
	private transient Hashtable ChartBuffers;
	private transient Hashtable DataRefs;
	private transient Hashtable DataColors;
	private transient Hashtable flat_fields;
	private transient RealType time;
	private transient RealType height;
	private transient FunctionType func_time_height;
	private transient Set time_set;
	private transient FlatField vals_ff;
	private transient DataReferenceImpl data_ref;
	private transient DisplayImplJ2D display;
	private transient GMCWidget control_widg;
	private transient ScalarMap timeMap;
	private transient ScalarMap heightMap;
	private transient RangeWidget timeWidget;
	private transient RangeWidget heightWidget;
	private transient boolean visadinit;
	private transient int current_sample_index;

	private void initVisAd(int max_samples, String msg)
	{
		try
		{
			if (getMonitor() != null)
				getMonitor().addNeuralNetListener(this);
			status_label.setText(msg);
			time = RealType.getRealType("Sample");
			height = RealType.getRealType("Value");
			func_time_height = new FunctionType(time, height);
			time_set = new Integer1DSet(time, max_samples);
			display = new DisplayImplJ2D("display1");
			display.setAutoAspect(true);
			GraphicsModeControl dispGMC = display.getGraphicsModeControl();
			control_widg = new GMCWidget(dispGMC);
			dispGMC.setScaleEnable(true);
			timeMap = new ScalarMap(time, Display.XAxis);
			heightMap = new ScalarMap(height, Display.YAxis);
			display.addMap(timeMap);
			display.addMap(heightMap);
			timeMap.setRange(0.0D, maxXaxis);
			heightMap.setRange(0.0D, maxYaxis);
			timeWidget = new RangeWidget(timeMap);
			heightWidget = new RangeWidget(heightMap);
			if (iFrame != null)
			{
				iFrame.getContentPane().removeAll();
				pane = new JPanel();
				ControlPane = new JPanel();
				pane.setLayout(new BoxLayout(pane, 1));
				pane.setAlignmentY(0.0F);
				pane.setAlignmentX(0.0F);
				pane.add(display.getComponent());
				((JPanel)display.getComponent()).setMinimumSize(new Dimension(500, 250));
				((JPanel)display.getComponent()).setMaximumSize(new Dimension(1000, 500));
				iFrame.getContentPane().setLayout(new BorderLayout());
				iFrame.getContentPane().add(pane, "Center");
				iFrame.getContentPane().add(timeWidget, "North");
				ControlPane.setLayout(new GridLayout(2, 1));
				ControlPane.add(heightWidget);
				ControlPane.add(control_widg);
				iFrame.getContentPane().add(ControlPane, "South");
				iFrame.setSize(740, 450);
			}
		}
		catch (VisADException ex)
		{
			log.error(ex.toString());
			if (getMonitor() != null)
				new NetErrorManager(getMonitor(), (new StringBuilder("VisADException while attempting to initialise VisAd. Message is : ")).append(ex.getMessage()).toString());
		}
		catch (RemoteException ex)
		{
			if (getMonitor() != null)
				new NetErrorManager(getMonitor(), (new StringBuilder("Remote exception while attempting to initialise VisAd. Message is : ")).append(ex.getMessage()).toString());
		}
		catch (Exception ex)
		{
			log.error(ex.toString());
			if (getMonitor() != null)
				new NetErrorManager(getMonitor(), (new StringBuilder("Exception while attempting to initialise VisAd. Message is : ")).append(ex.getMessage()).toString());
		}
	}

	private void PlotVisAd()
	{
		for (Enumeration myEnum = ChartBuffers.elements(); myEnum.hasMoreElements();)
			try
			{
				SharedBuffer buffer = (SharedBuffer)myEnum.nextElement();
				ChartingHandle handle = buffer.getHandle();
				double arr[][] = buffer.get();
				int max = (int)timeMap.getRange()[1];
				if (max > arr.length)
					max = arr.length;
				if (max > 0)
				{
					double copy[][] = new double[1][(int)timeMap.getRange()[1]];
					for (int i = 0; i < max; i++)
						copy[0][i] = arr[i][0];

					((FlatField)flat_fields.get(handle)).setSamples(copy);
					((DataReferenceImpl)DataRefs.get(handle)).setData((FlatField)flat_fields.get(handle));
					display.addReference((DataReferenceImpl)DataRefs.get(handle), (ConstantMap[])DataColors.get(handle));
				} else
				{
					log.error("No data to plot!");
					if (getMonitor() != null)
						new NetErrorManager(getMonitor(), "Warning while attempting to plot VisAd chart. No data to plot!");
				}
			}
			catch (VisADException ex)
			{
				log.error(ex.toString());
				if (getMonitor() != null)
					new NetErrorManager(getMonitor(), (new StringBuilder("VisADException while attempting to plot VisAd chart. Message is : ")).append(ex.getMessage()).toString());
			}
			catch (RemoteException ex)
			{
				if (getMonitor() != null)
					new NetErrorManager(getMonitor(), (new StringBuilder("Remote Exception while attempting to plot VisAd chart. Message is : ")).append(ex.getMessage()).toString());
			}
			catch (Exception ex)
			{
				log.error(ex.toString());
				if (getMonitor() != null)
					new NetErrorManager(getMonitor(), (new StringBuilder("Exception while attempting to plot VisAd chart. Message is : ")).append(ex.getMessage()).toString());
			}

	}

	public VisADOutputSynapse()
	{
		name = "";
		title = "VisAD Chart";
		resizable = true;
		maxXaxis = 1000;
		maxYaxis = 1.0D;
		serie = 1;
		enabled = true;
		def_handle = new ChartingHandle();
		status_label = new JLabel("Waiting for network to run!");
		ControlPane = new JPanel();
		ChartBuffers = new Hashtable();
		DataRefs = new Hashtable();
		DataColors = new Hashtable();
		flat_fields = new Hashtable();
		visadinit = false;
		current_sample_index = 0;
		initComponents();
		def_handle.setSerie(getSerie());
		def_handle.setBlueColor(230);
		def_handle.setGreenColor(0);
		def_handle.setRedColor(0);
		def_handle.setName((new StringBuilder("Serie ")).append(getSerie()).toString());
	}

	private void initComponents()
	{
		iFrame = new JFrame();
		iFrame.setTitle(getTitle());
		iFrame.setResizable(isResizable());
		iFrame.addWindowListener(new WindowAdapter() {

			final VisADOutputSynapse this$0;

			public void windowClosing(WindowEvent evt)
			{
				exitForm(evt);
			}

			
			{
				this$0 = VisADOutputSynapse.this;
				super();
			}
		});
		iFrame.pack();
		ChartBuffers = new Hashtable();
		DataRefs = new Hashtable();
		DataColors = new Hashtable();
		flat_fields = new Hashtable();
		ControlPane = new JPanel();
		def_handle = new ChartingHandle();
		status_label = new JLabel("Waiting for network to run!");
		initVisAd(getMaxXaxis(), "Waiting...");
		visadinit = true;
	}

	private void exitForm(WindowEvent evt)
	{
		setShow(false);
	}

	public void setMonitor(Monitor newMonitor)
	{
		monitor = newMonitor;
		if (monitor != null)
			monitor.addNeuralNetListener(this);
	}

	public void fwdPut(Pattern pattern, ChartingHandle handle)
	{
		SharedBuffer sb = null;
		if (isEnabled() && pattern.getCount() > -1)
		{
			if (ChartBuffers.size() > 0)
				sb = (SharedBuffer)ChartBuffers.get(handle);
			else
				sb = null;
			if (sb == null)
				try
				{
					sb = new SharedBuffer();
					sb.setHandle(handle);
					ChartBuffers.put(handle, sb);
					time_set = new Integer1DSet(time, (int)timeMap.getRange()[1]);
					flat_fields.put(handle, new FlatField(func_time_height, time_set));
					ConstantMap lineCMap[] = {
						new ConstantMap((float)handle.getRedColor() / 255F, Display.Red), new ConstantMap((float)handle.getGreenColor() / 255F, Display.Green), new ConstantMap((float)handle.getBlueColor() / 255F, Display.Blue), new ConstantMap(1.0D, Display.LineWidth)
					};
					DataColors.put(handle, lineCMap);
					DataRefs.put(handle, new DataReferenceImpl(handle.getName()));
				}
				catch (VisADException ex)
				{
					log.error(ex.toString());
					if (getMonitor() != null)
						new NetErrorManager(getMonitor(), (new StringBuilder("VisADException while attempting to define VisAd compononent in fwdPut(handle,pattern) method. Message is : ")).append(ex.getMessage()).toString());
				}
				catch (Exception ex)
				{
					log.error(ex.toString());
					if (getMonitor() != null)
						new NetErrorManager(getMonitor(), (new StringBuilder("Exception while attempting to define VisAd component in fwdPut(handle,pattern) method. Message is : ")).append(ex.getMessage()).toString());
				}
				catch (OutOfMemoryError err)
				{
					log.error(err.toString());
					if (getMonitor() != null)
						new NetErrorManager(getMonitor(), (new StringBuilder("Out of memory error while attempting to define VisAd component in fwdPut(handle,pattern) method. Message is : ")).append(err.getMessage()).toString());
				}
			sb.put(pattern.getArray()[handle.getSerie() - 1], pattern.getCount());
		}
	}

	public void fwdPut(Pattern pattern)
	{
		if (isEnabled() && pattern.getCount() > -1)
		{
			SharedBuffer sb = (SharedBuffer)ChartBuffers.get(def_handle);
			if (sb == null)
				try
				{
					sb = new SharedBuffer();
					sb.setHandle(def_handle);
					ChartBuffers.put(def_handle, sb);
					time_set = new Integer1DSet(time, (int)timeMap.getRange()[1]);
					flat_fields.put(def_handle, new FlatField(func_time_height, time_set));
					ConstantMap lineCMap[] = {
						new ConstantMap((float)def_handle.getRedColor() / 255F, Display.Red), new ConstantMap((float)def_handle.getGreenColor() / 255F, Display.Green), new ConstantMap((float)def_handle.getBlueColor() / 255F, Display.Blue), new ConstantMap(1.0D, Display.LineWidth)
					};
					DataColors.put(def_handle, lineCMap);
					DataRefs.put(def_handle, new DataReferenceImpl(def_handle.getName()));
				}
				catch (VisADException ex)
				{
					log.error(ex.toString());
					if (getMonitor() != null)
						new NetErrorManager(getMonitor(), (new StringBuilder("VisADException while attempting to define VisAd compononent in fwdPut(pattern) method. Message is : ")).append(ex.getMessage()).toString());
				}
				catch (Exception ex)
				{
					log.error(ex.toString());
					if (getMonitor() != null)
						new NetErrorManager(getMonitor(), (new StringBuilder("Exception while attempting to define VisAd component in fwdPut(pattern) method. Message is : ")).append(ex.getMessage()).toString());
				}
				catch (OutOfMemoryError err)
				{
					log.error(err.toString());
					if (getMonitor() != null)
						new NetErrorManager(getMonitor(), (new StringBuilder("Out of memory error while attempting to define VisAd component in fwdPut(pattern) method. Message is : ")).append(err.getMessage()).toString());
				}
			sb.put(pattern.getArray()[getSerie() - 1], pattern.getCount());
		}
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
			iFrame.setVisible(true);
		else
			iFrame.setVisible(false);
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
		def_handle.setSerie(getSerie());
		def_handle.setBlueColor(230);
		def_handle.setGreenColor(0);
		def_handle.setRedColor(0);
		def_handle.setName((new StringBuilder("Serie ")).append(getSerie()).toString());
		setShow(false);
	}

	private void writeObject(ObjectOutputStream out)
		throws IOException
	{
		if (timeMap != null)
			maxXaxis = (int)timeMap.getRange()[1];
		if (heightMap != null)
			maxYaxis = heightMap.getRange()[1];
		out.defaultWriteObject();
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

	public void cicleTerminated(NeuralNetEvent neuralnetevent)
	{
	}

	public void errorChanged(NeuralNetEvent neuralnetevent)
	{
	}

	public void netStarted(NeuralNetEvent e)
	{
		ChartBuffers.clear();
		DataRefs.clear();
		DataColors.clear();
		flat_fields.clear();
		if (!visadinit)
		{
			initVisAd(getMaxXaxis(), "Collecting data ....");
			visadinit = true;
		} else
		{
			try
			{
				display.removeAllReferences();
			}
			catch (VisADException ve)
			{
				log.error(ve.toString());
				if (getMonitor() != null)
					new NetErrorManager(getMonitor(), (new StringBuilder("VisADException while attempting to remove Display references in netStarted event. Message is : ")).append(ve.getMessage()).toString());
			}
			catch (RemoteException re)
			{
				log.error(re.toString());
				if (getMonitor() != null)
					new NetErrorManager(getMonitor(), (new StringBuilder("RemoteException while attempting to remove Display references in netStarted event. Message is : ")).append(re.getMessage()).toString());
			}
		}
		status_label.setText("Collecting data ....");
	}

	public void netStopped(NeuralNetEvent e)
	{
		try
		{
			status_label.setText("Data collected and displayed.");
			PlotVisAd();
		}
		catch (Exception ex)
		{
			log.error(ex.toString());
		}
	}

	public void netStoppedError(NeuralNetEvent neuralnetevent, String s)
	{
	}

	public void removeHandle(ChartingHandle chartinghandle)
	{
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public boolean isOutputFull()
	{
		return outputFull;
	}

	public void setOutputFull(boolean newoutputFull)
	{
		outputFull = newoutputFull;
	}

	public void init()
	{
	}


}
