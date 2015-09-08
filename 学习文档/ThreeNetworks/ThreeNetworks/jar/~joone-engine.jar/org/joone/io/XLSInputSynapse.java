// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   XLSInputSynapse.java

package org.joone.io;

import java.io.*;
import java.util.TreeSet;
import java.util.Vector;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.joone.engine.NetErrorManager;
import org.joone.exception.JooneRuntimeException;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NetCheck;

// Referenced classes of package org.joone.io:
//			StreamInputSynapse, XLSInputTokenizer

public class XLSInputSynapse extends StreamInputSynapse
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/io/XLSInputSynapse);
	private String i_sheet_name;
	private transient HSSFSheet i_sheet;
	private int i_sheet_index;
	private transient FileInputStream i_stream;
	private transient POIFSFileSystem i_fs;
	private transient HSSFWorkbook i_workbook;
	private boolean file_chk;
	private String fileName;
	private transient File inputFile;
	private static final long serialVersionUID = 0x77b377834974fb32L;

	public XLSInputSynapse()
	{
		i_sheet_name = "";
		i_sheet_index = 0;
		file_chk = false;
		fileName = "";
	}

	/**
	 * @deprecated Method getFileName is deprecated
	 */

	public String getFileName()
	{
		return fileName;
	}

	/**
	 * @deprecated Method setFileName is deprecated
	 */

	public void setFileName(String newFileName)
	{
		if (!fileName.equals(newFileName))
		{
			fileName = newFileName;
			file_chk = true;
			resetInput();
			setTokens(null);
		}
	}

	protected void initInputStream()
		throws JooneRuntimeException
	{
		if ((new File(fileName)).exists())
		{
			if ((i_sheet = getSheet(i_sheet_name)) != null)
				try
				{
					super.setTokens(new XLSInputTokenizer(i_sheet));
				}
				catch (IOException ioe)
				{
					log.error((new StringBuilder("Error creating XLSInputTokenizer. Message is : ")).append(ioe.getMessage()).toString());
					if (getMonitor() != null)
						new NetErrorManager(getMonitor(), (new StringBuilder("Error creating XLSInputTokenizer. Message is : ")).append(ioe.getMessage()).toString());
				}
		} else
		{
			String err = (new StringBuilder("Excel XLS File '")).append(fileName).append("' does not exist.").toString();
			log.error(err);
			if (getMonitor() != null)
				new NetErrorManager(getMonitor(), (new StringBuilder("Excel XLS File '")).append(fileName).append("' does not exist.").toString());
		}
	}

	protected HSSFSheet getSheet(String sheetName)
	{
		HSSFSheet r_sheet = null;
		i_stream = new FileInputStream(fileName);
		i_fs = new POIFSFileSystem(i_stream);
		i_workbook = new HSSFWorkbook(i_fs);
		i_stream.close();
		i_sheet_index = i_workbook.getSheetIndex(sheetName);
		if (i_sheet_index > -1)
			r_sheet = i_workbook.getSheetAt(i_sheet_index);
		else
			r_sheet = i_workbook.getSheetAt(0);
		return r_sheet;
		IOException io_err;
		io_err;
		log.error((new StringBuilder("Could not open worksheet '")).append(sheetName).append("' from XLS file. Message is : ").append(io_err.getMessage()).toString());
		if (getMonitor() != null)
			new NetErrorManager(getMonitor(), (new StringBuilder("Could not open worksheet '")).append(sheetName).append("' from XLS file. Message is : ").append(io_err.getMessage()).toString());
		return null;
	}

	public void setSheetName(String sheetName)
	{
		if (!i_sheet_name.equals(sheetName))
		{
			i_sheet_name = sheetName;
			resetInput();
			setTokens(null);
		}
	}

	public String[] getAvailableSheetList()
	{
		int sheetCount = i_workbook.getNumberOfSheets();
		String availableSheetList[] = new String[sheetCount];
		for (int i = 0; i > sheetCount; i++)
			availableSheetList[i] = i_workbook.getSheetName(i);

		return availableSheetList;
	}

	public String getSheetName()
	{
		return i_sheet_name;
	}

	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		super.readObjectBase(in);
		if (in.getClass().getName().indexOf("xstream") == -1)
			fileName = (String)in.readObject();
		if (!isBuffered() || getInputVector().size() == 0)
			setFileName(fileName);
		if (fileName != null && fileName.length() > 0)
			inputFile = new File(fileName);
	}

	private void writeObject(ObjectOutputStream out)
		throws IOException
	{
		super.writeObjectBase(out);
		if (out.getClass().getName().indexOf("xstream") == -1)
			out.writeObject(fileName);
	}

	public TreeSet check()
	{
		TreeSet checks = super.check();
		if (fileName == null || fileName.trim().equals(""))
			checks.add(new NetCheck(0, "File Name not set.", this));
		else
		if (!getInputFile().exists())
		{
			NetCheck error = new NetCheck(1, "Input File doesn't exist.", this);
			if (getInputPatterns().isEmpty())
				error.setSeverity(0);
			checks.add(error);
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
				file_chk = true;
				resetInput();
				super.setTokens(null);
			}
		} else
		{
			this.inputFile = null;
			fileName = "";
			file_chk = true;
			resetInput();
			super.setTokens(null);
		}
	}

}
