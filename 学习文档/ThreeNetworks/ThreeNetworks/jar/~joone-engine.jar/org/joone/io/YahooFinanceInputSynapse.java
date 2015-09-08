// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   YahooFinanceInputSynapse.java

package org.joone.io;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;
import org.joone.engine.NetErrorManager;
import org.joone.exception.JooneRuntimeException;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NetCheck;

// Referenced classes of package org.joone.io:
//			StreamInputSynapse, StreamInputTokenizer

public class YahooFinanceInputSynapse extends StreamInputSynapse
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/io/YahooFinanceInputSynapse);
	String months[] = {
		"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", 
		"November", "December"
	};
	String frequency[] = {
		"Daily", "Weekly", "Monthly"
	};
	String freq_conv[] = {
		"d", "w", "m"
	};
	String Symbol;
	DateFormat date_formater;
	Calendar CalendarStart;
	Calendar CalendarEnd;
	String DateStart;
	String DateEnd;
	String Period;
	private transient Date startDate;
	private transient Date endDate;
	private Vector StockData[];
	private Vector StockDates;
	String ColumnNames[] = {
		"Date", "Open", "High", "Low", "Close", "Volume", "Adj. Close"
	};
	static final long serialVersionUID = 0x1210d032e51a3c51L;

	public YahooFinanceInputSynapse()
	{
		Symbol = "";
		date_formater = DateFormat.getDateInstance(2);
		CalendarStart = Calendar.getInstance();
		CalendarEnd = Calendar.getInstance();
		DateStart = new String(date_formater.format(CalendarStart.getTime()));
		DateEnd = new String(date_formater.format(CalendarEnd.getTime()));
		Period = new String("Daily");
		startDate = CalendarStart.getTime();
		endDate = CalendarEnd.getTime();
		StockData = new Vector[6];
		StockDates = new Vector();
	}

	public String getSymbol()
	{
		return Symbol;
	}

	/**
	 * @deprecated Method getDateStart is deprecated
	 */

	public String getDateStart()
	{
		return DateStart;
	}

	/**
	 * @deprecated Method getDateEnd is deprecated
	 */

	public String getDateEnd()
	{
		return DateEnd;
	}

	public String getPeriod()
	{
		return Period;
	}

	public Vector getStockDates()
	{
		return StockDates;
	}

	public Vector[] getStockData()
	{
		return StockData;
	}

	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		super.readObjectBase(in);
		if (in.getClass().getName().indexOf("xstream") == -1)
		{
			Symbol = (String)in.readObject();
			DateStart = (String)in.readObject();
			DateEnd = (String)in.readObject();
			Period = (String)in.readObject();
		}
		if (!isBuffered() || getInputVector().size() == 0)
			initInputStream();
		try
		{
			startDate = date_formater.parse(DateStart);
			endDate = date_formater.parse(DateEnd);
		}
		catch (ParseException ex)
		{
			log.error((new StringBuilder("Invalid Date: start:")).append(DateStart).append(" end:").append(DateEnd).toString());
		}
	}

	/**
	 * @deprecated Method setSymbol is deprecated
	 */

	public void setSymbol(String newSymbol)
	{
		if (!Symbol.equals(newSymbol))
		{
			Symbol = newSymbol;
			resetInput();
			setTokens(null);
		}
	}

	/**
	 * @deprecated Method setDateStart is deprecated
	 */

	public void setDateStart(String newDateStart)
	{
		if (!DateStart.equals(newDateStart))
		{
			DateStart = newDateStart;
			resetInput();
			setTokens(null);
		}
	}

	public void setDateEnd(String newDateEnd)
	{
		if (!DateEnd.equals(newDateEnd))
		{
			DateEnd = newDateEnd;
			resetInput();
			setTokens(null);
		}
	}

	public void setPeriod(String newPeriod)
	{
		if (!Period.equals(newPeriod))
		{
			Period = newPeriod;
			resetInput();
			setTokens(null);
		}
	}

	private void writeObject(ObjectOutputStream out)
		throws IOException
	{
		super.writeObjectBase(out);
		if (out.getClass().getName().indexOf("xstream") == -1)
		{
			out.writeObject(Symbol);
			out.writeObject(DateStart);
			out.writeObject(DateEnd);
			out.writeObject(Period);
		}
	}

	protected void initInputStream()
		throws JooneRuntimeException
	{
		String final_patterns = "";
		int start = 0;
		String myUrl = "";
		String cperiod = "d";
		for (int i = 0; i < frequency.length; i++)
			if (Period.equals(frequency[i]))
				cperiod = freq_conv[i];

		StockData = null;
		StockDates = null;
		if (Symbol != null && !Symbol.equals(""))
		{
			try
			{
				CalendarStart = Calendar.getInstance();
				CalendarEnd = Calendar.getInstance();
				CalendarStart.setTime(date_formater.parse(DateStart));
				CalendarEnd.setTime(date_formater.parse(DateEnd));
			}
			catch (ParseException ex)
			{
				log.error((new StringBuilder("YahooFinanceInputSynapse could not parse date string. Message is : = ")).append(ex.getMessage()).toString());
				CalendarStart = null;
				CalendarEnd = null;
				if (getMonitor() != null)
					new NetErrorManager(getMonitor(), (new StringBuilder("YahooFinanceInputSynapse could not parse date string. ")).append(ex.getMessage()).toString());
				return;
			}
			int addedData = 1;
			if (CalendarStart != null && CalendarEnd != null && Period != null)
			{
				log.info("Contacting Yahoo Finance Network.");
				try
				{
					while (addedData > 0 && addedData <= 200) 
					{
						myUrl = new String((new StringBuilder("http://table.finance.yahoo.com/table.csv?a=")).append(CalendarStart.get(2)).append("&b=").append(CalendarStart.get(5)).append("&c=").append(CalendarStart.get(1)).append("&d=").append(CalendarEnd.get(2)).append("&e=").append(CalendarEnd.get(5)).append("&f=").append(CalendarEnd.get(1)).append("&s=").append(Symbol).append("&g=").append(cperiod).append("&z=200&y=").append(start).append("&ignore=.csv").toString());
						addedData = addURLToMemory(new URL(myUrl));
						start += 200;
					}
				}
				catch (IOException ex)
				{
					log.error((new StringBuilder("Error obtaining data from YahooFInance. Error message is ")).append(ex.getMessage()).toString());
					if (getMonitor() != null)
						new NetErrorManager(getMonitor(), (new StringBuilder("Error obtaining data from YahooFInance.")).append(ex.getMessage()).toString());
					return;
				}
				log.info("Loaded Yahoo Fianance data ok.");
				for (int i = StockData[0].size() - 1; i >= 0; i--)
				{
					final_patterns = (new StringBuilder(String.valueOf(final_patterns))).append(((Double)StockData[0].elementAt(i)).toString()).toString();
					final_patterns = (new StringBuilder(String.valueOf(final_patterns))).append(";").append(((Double)StockData[1].elementAt(i)).toString()).toString();
					final_patterns = (new StringBuilder(String.valueOf(final_patterns))).append(";").append(((Double)StockData[2].elementAt(i)).toString()).toString();
					final_patterns = (new StringBuilder(String.valueOf(final_patterns))).append(";").append(((Double)StockData[3].elementAt(i)).toString()).toString();
					final_patterns = (new StringBuilder(String.valueOf(final_patterns))).append(";").append(((Double)StockData[4].elementAt(i)).toString()).toString();
					final_patterns = (new StringBuilder(String.valueOf(final_patterns))).append(";").append(((Double)StockData[5].elementAt(i)).toString()).toString();
					final_patterns = (new StringBuilder(String.valueOf(final_patterns))).append('\n').toString();
				}

				try
				{
					StreamInputTokenizer sit;
					if (getMaxBufSize() > 0)
						sit = new StreamInputTokenizer(new StringReader(final_patterns), getMaxBufSize());
					else
						sit = new StreamInputTokenizer(new StringReader(final_patterns));
					super.setTokens(sit);
				}
				catch (IOException ex)
				{
					log.error((new StringBuilder("IOException thrown while initializing the YahooFinanceInputStream. Message is : ")).append(ex.getMessage()).toString());
					if (getMonitor() != null)
						new NetErrorManager(getMonitor(), (new StringBuilder("Error while trying to parse Yahoo Finance data. Message is : ")).append(ex.getMessage()).toString());
				}
			}
		}
	}

	private int addURLToMemory(URL urlData)
		throws IOException
	{
		String urlLine = "";
		boolean ignoreHeader = true;
		boolean added_data = true;
		boolean doContinues = true;
		int initSize = 0;
		if (urlData != null)
		{
			log.debug((new StringBuilder(String.valueOf(urlData.getProtocol()))).append("://").append(urlData.getHost()).append(urlData.getFile()).toString());
			LineNumberReader urlReader = new LineNumberReader(new InputStreamReader(new BufferedInputStream(urlData.openStream())));
			urlLine = urlReader.readLine();
			if (StockDates == null)
				StockDates = new Vector();
			if (StockData == null || StockData.length < 6)
				StockData = new Vector[6];
			if (StockData[0] == null)
				StockData[0] = new Vector();
			if (StockData[1] == null)
				StockData[1] = new Vector();
			if (StockData[2] == null)
				StockData[2] = new Vector();
			if (StockData[3] == null)
				StockData[3] = new Vector();
			if (StockData[4] == null)
				StockData[4] = new Vector();
			if (StockData[5] == null)
				StockData[5] = new Vector();
			initSize = StockData[0].size();
			for (; urlLine != null && !urlLine.equals("") && doContinues; urlLine = urlReader.readLine())
				if (ignoreHeader && urlLine.indexOf(ColumnNames[0]) < 0)
					if (urlLine.indexOf(",") != -1)
					{
						StringTokenizer tokens = new StringTokenizer(urlLine, ",\n");
						if (tokens.countTokens() >= 7)
						{
							added_data = true;
							String _data = tokens.nextToken();
							StockDates.addElement(_data);
							StockData[0].add(Double.valueOf(tokens.nextToken()));
							StockData[1].add(Double.valueOf(tokens.nextToken()));
							StockData[2].add(Double.valueOf(tokens.nextToken()));
							StockData[3].add(Double.valueOf(tokens.nextToken()));
							StockData[4].add(Double.valueOf(tokens.nextToken()));
							StockData[5].add(Double.valueOf(tokens.nextToken()));
						}
					} else
					{
						doContinues = false;
					}

			urlReader.close();
		}
		int addedData = StockData[0].size() - initSize;
		return addedData;
	}

	public TreeSet check()
	{
		TreeSet checks = super.check();
		if (getDateStart() != null && !getDateStart().equals(""))
			try
			{
				date_formater.parse(DateStart);
			}
			catch (ParseException ex)
			{
				checks.add(new NetCheck(0, "Format for Start Date is invalid.", this));
			}
		else
			checks.add(new NetCheck(0, "Start Date should be populated.", this));
		if (getDateEnd() != null && !getDateEnd().equals(""))
			try
			{
				date_formater.parse(DateEnd);
			}
			catch (ParseException ex)
			{
				checks.add(new NetCheck(0, "Format for End Date is invalid.", this));
			}
		else
			checks.add(new NetCheck(0, "End Date should be populated.", this));
		if (getPeriod() != null)
		{
			if (!getPeriod().equals("Daily") && !getPeriod().equals("Weekly") && !getPeriod().equals("Monthly"))
				checks.add(new NetCheck(0, "Period should be one of 'Daily' , 'Weekly' , 'Monthly'.", this));
		} else
		{
			checks.add(new NetCheck(0, "Period should be one of 'Daily' , 'Weekly' , 'Monthly'.", this));
		}
		if (getSymbol() == null || getSymbol().equals(""))
			checks.add(new NetCheck(0, "Symbol should be populated.", this));
		return checks;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
		DateStart = date_formater.format(startDate);
		resetInput();
		setTokens(null);
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
		DateEnd = date_formater.format(endDate);
		resetInput();
		setTokens(null);
	}

}
