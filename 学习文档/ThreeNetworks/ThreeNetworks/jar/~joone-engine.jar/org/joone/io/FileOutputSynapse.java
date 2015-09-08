// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileOutputSynapse.java

package org.joone.io;

import java.io.*;
import java.util.TreeSet;
import org.joone.engine.NetErrorManager;
import org.joone.engine.Pattern;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NetCheck;

// Referenced classes of package org.joone.io:
//			StreamOutputSynapse

public class FileOutputSynapse extends StreamOutputSynapse
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/io/FileOutputSynapse);
	private String FileName;
	private boolean append;
	protected transient PrintWriter printer;
	private static final long serialVersionUID = 0x2c55c0a0999ee5aeL;

	public FileOutputSynapse()
	{
		FileName = "";
		append = false;
		printer = null;
	}

	public synchronized void write(Pattern pattern)
	{
		if (printer == null || pattern.getCount() == 1)
			setFileName(FileName);
		if (pattern.isMarkedAsStoppingPattern())
		{
			flush();
		} else
		{
			double array[] = pattern.getArray();
			for (int i = 0; i < array.length; i++)
			{
				printer.print(array[i]);
				if (i < array.length - 1)
					printer.print(getSeparator());
			}

			printer.println();
		}
	}

	public String getFileName()
	{
		return FileName;
	}

	public void setFileName(String fn)
	{
		FileName = fn;
		try
		{
			if (printer != null)
				printer.close();
			printer = new PrintWriter(new FileOutputStream(fn, isAppend()), true);
		}
		catch (IOException ioe)
		{
			String error = (new StringBuilder("IOException in ")).append(getName()).append(". Message is : ").toString();
			log.error((new StringBuilder(String.valueOf(error))).append(ioe.getMessage()).toString());
			if (getMonitor() != null)
				new NetErrorManager(getMonitor(), (new StringBuilder(String.valueOf(error))).append(ioe.getMessage()).toString());
		}
	}

	public void flush()
	{
		printer.flush();
		printer.close();
		printer = null;
	}

	public TreeSet check()
	{
		TreeSet checks = super.check();
		if (FileName == null || FileName.trim().equals(""))
			checks.add(new NetCheck(0, "File Name not set.", this));
		return checks;
	}

	public boolean isAppend()
	{
		return append;
	}

	public void setAppend(boolean append)
	{
		this.append = append;
	}

}
