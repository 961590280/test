// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileInputSynapse.java

package org.joone.io;

import java.io.*;
import java.util.TreeSet;
import java.util.Vector;
import org.joone.exception.JooneRuntimeException;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NetCheck;

// Referenced classes of package org.joone.io:
//			StreamInputSynapse, StreamInputTokenizer

public class FileInputSynapse extends StreamInputSynapse
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/io/FileInputSynapse);
	private static final long serialVersionUID = 0x677b4303ea47a370L;
	private String fileName;
	private transient File inputFile;

	public FileInputSynapse()
	{
		fileName = "";
	}

	/**
	 * @deprecated Method getFileName is deprecated
	 */

	public String getFileName()
	{
		return fileName;
	}

	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		super.readObjectBase(in);
		if (in.getClass().getName().indexOf("xstream") == -1)
			fileName = (String)in.readObject();
		if (fileName != null && fileName.trim().length() > 0)
			inputFile = new File(fileName);
	}

	/**
	 * @deprecated Method setFileName is deprecated
	 */

	public void setFileName(String newFileName)
	{
		if (!fileName.equals(newFileName))
		{
			fileName = newFileName;
			makeInit();
		}
	}

	private void writeObject(ObjectOutputStream out)
		throws IOException
	{
		super.writeObjectBase(out);
		if (out.getClass().getName().indexOf("xstream") == -1)
			out.writeObject(fileName);
	}

	protected void initInputStream()
		throws JooneRuntimeException
	{
		if (fileName != null && !fileName.trim().equals(""))
			try
			{
				inputFile = new File(fileName);
				FileInputStream fis = new FileInputStream(inputFile);
				int maxBufferSize = getMaxBufSize();
				StreamInputTokenizer sit;
				if (maxBufferSize > 0)
					sit = new StreamInputTokenizer(new InputStreamReader(fis), maxBufferSize);
				else
					sit = new StreamInputTokenizer(new InputStreamReader(fis));
				setTokens(sit);
			}
			catch (IOException ioe)
			{
				handleExceptionWithWarn(ioe, "IOException", log);
			}
	}

	public TreeSet check()
	{
		TreeSet checks = super.check();
		if (fileName == null || fileName.trim().equals(""))
		{
			checks.add(new NetCheck(0, "File Name not set.", this));
		} else
		{
			if (getInputFile() == null)
				makeInit();
			if (getInputFile() != null && !getInputFile().exists())
			{
				NetCheck error = new NetCheck(1, "Input File doesn't exist.", this);
				if (getInputPatterns().isEmpty())
					error.setSeverity(0);
				checks.add(error);
			}
		}
		return checks;
	}

	public File getInputFile()
	{
		return inputFile;
	}

	public void setInputFile(File inputFile)
	{
		if (inputFile != null)
		{
			if (!fileName.equals(inputFile.getAbsolutePath()))
			{
				this.inputFile = inputFile;
				fileName = inputFile.getAbsolutePath();
				makeInit();
			}
		} else
		{
			this.inputFile = null;
			fileName = "";
			makeInit();
		}
	}

	private void makeInit()
	{
		resetInput();
		setTokens(null);
	}

}
