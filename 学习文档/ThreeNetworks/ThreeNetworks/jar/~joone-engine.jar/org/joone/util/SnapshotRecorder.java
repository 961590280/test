// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SnapshotRecorder.java

package org.joone.util;

import java.io.*;
import org.joone.engine.Layer;
import org.joone.engine.Monitor;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NeuralNet;

// Referenced classes of package org.joone.util:
//			SnapshotPlugin

public class SnapshotRecorder extends SnapshotPlugin
	implements Serializable
{

	private static final long serialVersionUID = 0x8edec10a4383027eL;
	private static final ILogger log = LoggerFactory.getLogger(org/joone/util/SnapshotRecorder);
	public static final String JOONE_FORMAT = "joone";
	public static final String VISAD_FORMAT = "visad";
	private transient OutputStream os;
	private transient ObjectOutputStream oos;
	private String filename;
	private String format;

	public SnapshotRecorder()
	{
		os = null;
		oos = null;
		filename = "";
		format = "joone";
	}

	protected void doStart()
	{
		try
		{
			os = new FileOutputStream(filename);
			oos = new ObjectOutputStream(os);
		}
		catch (IOException e)
		{
			log.warn((new StringBuilder("IOException while opening OutputStream. Message is : ")).append(e.getMessage()).toString(), e);
		}
	}

	protected void doSnapshot()
	{
		if (oos != null)
			if ("joone".equals(format))
				jooneSnapshot(getNeuralNet());
			else
				jooneSnapshot(getNeuralNet());
	}

	private void jooneSnapshot(NeuralNet net)
	{
		try
		{
			NeuralNet clone = net.cloneNet();
			Layer layer = clone.getInputLayer();
			if (layer != null)
				layer.removeAllInputs();
			layer = clone.getOutputLayer();
			if (layer != null)
				layer.removeAllOutputs();
			oos.writeObject(clone);
		}
		catch (IOException e)
		{
			log.warn((new StringBuilder("IOException while writing to OutputStream. Message is : ")).append(e.getMessage()).toString(), e);
		}
	}

	protected void doStop()
	{
		try
		{
			if (oos != null)
			{
				oos.flush();
				os.close();
			}
		}
		catch (IOException e)
		{
			log.warn((new StringBuilder("IOException while closing OutputStream. Message is : ")).append(e.getMessage()).toString(), e);
		}
	}

	public void setFilename(String name)
	{
		filename = name;
	}

	public String getFilename()
	{
		return filename;
	}

	public String getFormat()
	{
		return format;
	}

	public void setFormat(String format)
	{
		this.format = format;
	}

	protected void manageStopError(Monitor monitor, String s)
	{
	}

}
