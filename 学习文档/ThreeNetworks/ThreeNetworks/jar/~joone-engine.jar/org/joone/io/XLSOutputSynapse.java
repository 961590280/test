// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   XLSOutputSynapse.java

package org.joone.io;

import java.io.*;
import java.util.TreeSet;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.joone.engine.NetErrorManager;
import org.joone.engine.Pattern;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NetCheck;

// Referenced classes of package org.joone.io:
//			StreamOutputSynapse

public class XLSOutputSynapse extends StreamOutputSynapse
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/io/XLSOutputSynapse);
	static final long serialVersionUID = 0x80c800179ac34fbaL;
	private String FileName;
	private transient FileOutputStream o_stream;
	private transient FileInputStream i_stream;
	private transient HSSFSheet o_sheet;
	private int o_sheet_index;
	private int row_no;
	private int startCol;
	private int startRow;
	private transient HSSFWorkbook workbook;
	private String o_sheet_name;

	public XLSOutputSynapse()
	{
		FileName = "";
		o_sheet_index = -1;
		row_no = 0;
		o_sheet_name = "j_output";
		startCol = 0;
		startRow = 0;
	}

	public synchronized void flush()
	{
		try
		{
			workbook.write(o_stream);
		}
		catch (IOException ioe)
		{
			logWarn(ioe);
		}
	}

	public synchronized void write(Pattern pattern)
	{
		if (workbook == null || pattern.getCount() == 1)
			try
			{
				initOutputStream();
			}
			catch (IOException ioe)
			{
				logWarn(ioe);
			}
		if (pattern.isMarkedAsStoppingPattern())
		{
			try
			{
				o_stream = new FileOutputStream(FileName);
				workbook.write(o_stream);
				o_stream.close();
				row_no = startRow;
				workbook = null;
			}
			catch (IOException ioe)
			{
				logWarn(ioe);
			}
		} else
		{
			double array[] = pattern.getArray();
			HSSFRow row;
			if (o_sheet.getRow(row_no) == null)
				row = o_sheet.createRow((short)row_no);
			else
				row = o_sheet.getRow(row_no);
			for (int i = 0; i < array.length; i++)
			{
				if (row.getCell((short)(i + startCol)) != null)
					row.removeCell(row.getCell((short)(i + startCol)));
				HSSFCell cell = row.createCell((short)(i + startCol), 0);
				cell.setCellValue(array[i]);
			}

			row_no++;
		}
	}

	public String getFileName()
	{
		return FileName;
	}

	public void setFileName(String fn)
	{
		FileName = fn;
	}

	private void initOutputStream()
		throws IOException
	{
		if ((new File(FileName)).exists())
		{
			i_stream = new FileInputStream(FileName);
			POIFSFileSystem i_fs = new POIFSFileSystem(i_stream);
			workbook = new HSSFWorkbook(i_fs);
			i_stream.close();
		} else
		{
			o_stream = new FileOutputStream(FileName);
			workbook = new HSSFWorkbook();
			workbook.write(o_stream);
			o_stream.close();
		}
		o_sheet_index = workbook.getSheetIndex(o_sheet_name);
		if (o_sheet_index != -1)
			o_sheet = workbook.getSheetAt(o_sheet_index);
		else
			o_sheet = workbook.createSheet(o_sheet_name);
		row_no = startRow;
	}

	public void setSheetName(String sheetName)
	{
		o_sheet_name = sheetName;
	}

	public synchronized String[] getAvailableSheetList()
	{
		int sheetCount = workbook.getNumberOfSheets();
		String availableSheetList[] = new String[sheetCount];
		for (int i = 0; i > sheetCount; i++)
			availableSheetList[i] = workbook.getSheetName(i);

		return availableSheetList;
	}

	public String getSheetName()
	{
		return o_sheet_name;
	}

	public TreeSet check()
	{
		TreeSet checks = super.check();
		if (FileName == null || FileName.trim().equals(""))
			checks.add(new NetCheck(0, "File Name not set.", this));
		return checks;
	}

	public int getStartRow(int startRow)
	{
		return startRow;
	}

	public int getStartCol(int startCol)
	{
		return startCol;
	}

	public void setStartRow(int startRow)
	{
		this.startRow = startRow;
	}

	public void setStartCol(int startCol)
	{
		this.startCol = startCol;
	}

	private void logWarn(IOException ioe)
	{
		String error = (new StringBuilder("IOException in ")).append(getName()).append(". Message is : ").toString();
		log.warn((new StringBuilder(String.valueOf(error))).append(ioe).toString());
		if (getMonitor() != null)
			new NetErrorManager(getMonitor(), (new StringBuilder(String.valueOf(error))).append(ioe.getMessage()).toString());
	}

}
