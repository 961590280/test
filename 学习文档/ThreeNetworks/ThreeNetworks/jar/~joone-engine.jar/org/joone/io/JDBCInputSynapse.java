// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JDBCInputSynapse.java

package org.joone.io;

import java.io.*;
import java.sql.*;
import java.util.TreeSet;
import java.util.Vector;
import org.joone.engine.NetErrorManager;
import org.joone.exception.JooneRuntimeException;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NetCheck;

// Referenced classes of package org.joone.io:
//			StreamInputSynapse, StreamInputTokenizer

public class JDBCInputSynapse extends StreamInputSynapse
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/io/JDBCInputSynapse);
	private String driverName;
	private String dbURL;
	private String SQLQuery;
	private static final long serialVersionUID = 0xbf91f739c8b1af40L;

	public JDBCInputSynapse()
	{
		driverName = "";
		dbURL = "";
		SQLQuery = "";
	}

	public JDBCInputSynapse(String newDrivername, String newdbURL, String newSQLQuery, String newAdvColSel, int newfirstRow, int newlastRow, boolean buffered)
	{
		driverName = "";
		dbURL = "";
		SQLQuery = "";
		setBuffered(buffered);
		setFirstRow(newfirstRow);
		setLastRow(newlastRow);
		setAdvancedColumnSelector(newAdvColSel);
		driverName = newDrivername;
		dbURL = newdbURL;
		SQLQuery = newSQLQuery;
		resetInputAndTokens();
	}

	public String getdriverName()
	{
		return driverName;
	}

	public String getdbURL()
	{
		return dbURL;
	}

	public String getSQLQuery()
	{
		return SQLQuery;
	}

	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		super.readObjectBase(in);
		if (in.getClass().getName().indexOf("xstream") == -1)
		{
			driverName = (String)in.readObject();
			dbURL = (String)in.readObject();
			SQLQuery = (String)in.readObject();
		}
		if (!isBuffered() || getInputVector().size() == 0)
			setdriverName(driverName);
	}

	public void setdriverName(String newDriverName)
	{
		if (!newDriverName.equals(driverName))
		{
			driverName = newDriverName;
			resetInputAndTokens();
		}
	}

	private void resetInputAndTokens()
	{
		resetInput();
		setTokens(null);
	}

	public void setdbURL(String newdbURL)
	{
		if (!dbURL.equals(newdbURL))
		{
			dbURL = newdbURL;
			resetInputAndTokens();
		}
	}

	public void setSQLQuery(String newSQLQuery)
	{
		if (!SQLQuery.equals(newSQLQuery))
		{
			SQLQuery = newSQLQuery;
			resetInputAndTokens();
		}
	}

	private void writeObject(ObjectOutputStream out)
		throws IOException
	{
		super.writeObjectBase(out);
		if (out.getClass().getName().indexOf("xstream") == -1)
		{
			out.writeObject(driverName);
			out.writeObject(dbURL);
			out.writeObject(SQLQuery);
		}
	}

	protected void initInputStream()
		throws JooneRuntimeException
	{
		Statement stmt;
		ResultSet rs;
		Connection con;
		if (!checkIfPropertiesSet())
			return;
		try
		{
			Class.forName(driverName);
		}
		catch (ClassNotFoundException ex)
		{
			String errMsg = "Could not find Database Driver Class while initializing the JDBCInputStream";
			handleExceptionWithError(ex, errMsg, log);
			return;
		}
		stmt = null;
		rs = null;
		con = null;
		con = DriverManager.getConnection(dbURL);
		stmt = con.createStatement();
		rs = stmt.executeQuery(SQLQuery);
		tokenizeResultSet(rs);
		break MISSING_BLOCK_LABEL_88;
		SQLException sqlex;
		sqlex;
		if (rs != null)
			rs.close();
		throw sqlex;
		if (rs != null)
			rs.close();
		break MISSING_BLOCK_LABEL_116;
		Exception exception;
		exception;
		if (stmt != null)
			stmt.close();
		throw exception;
		if (stmt != null)
			stmt.close();
		break MISSING_BLOCK_LABEL_144;
		Exception exception1;
		exception1;
		if (con != null)
			con.close();
		throw exception1;
		if (con != null)
			con.close();
		break MISSING_BLOCK_LABEL_194;
		sqlex;
		String errMsg = "SQLException thrown while initializing the JDBCInputStream";
		handleExceptionWithError(sqlex, errMsg, log);
		break MISSING_BLOCK_LABEL_194;
		IOException ex;
		ex;
		String errMsg = "IOException thrown while initializing the JDBCInputStream";
		handleExceptionWithError(ex, errMsg, log);
	}

	private boolean checkIfPropertiesSet()
	{
		if (isStringEmpty(driverName))
		{
			handlePropertyNotSet("The Driver Name has not been entered or is empty");
			return false;
		}
		if (isStringEmpty(dbURL))
		{
			handlePropertyNotSet("The Database URL has not been entered or is empty");
			return false;
		}
		if (isStringEmpty(SQLQuery))
		{
			handlePropertyNotSet("The SQL Query has not been entered or is empty");
			return false;
		} else
		{
			return true;
		}
	}

	private void handlePropertyNotSet(String err)
	{
		String errorWithName = (new StringBuilder(String.valueOf(err))).append(" in ").append(getSynapseNameSafely()).append(".").toString();
		log.warn(errorWithName);
		if (getMonitor() != null)
			new NetErrorManager(getMonitor(), errorWithName);
	}

	private void tokenizeResultSet(ResultSet rs)
		throws SQLException, IOException
	{
		StringBuffer SQLNetInput = new StringBuffer();
		for (; rs.next(); SQLNetInput.append('\n'))
		{
			if (rs.getMetaData().getColumnCount() >= 1)
				SQLNetInput.append(rs.getDouble(1));
			for (int counter = 2; counter <= rs.getMetaData().getColumnCount(); counter++)
			{
				SQLNetInput.append(";");
				SQLNetInput.append(rs.getDouble(counter));
			}

		}

		StreamInputTokenizer sit;
		if (getMaxBufSize() > 0)
			sit = new StreamInputTokenizer(new StringReader(SQLNetInput.toString()), getMaxBufSize());
		else
			sit = new StreamInputTokenizer(new StringReader(SQLNetInput.toString()));
		super.setTokens(sit);
	}

	public TreeSet check()
	{
		TreeSet checks;
		Connection con;
		checks = super.check();
		con = null;
		if (isStringEmpty(driverName))
		{
			addNetCheckFatal(checks, "Database Driver Name needs to be entered.");
			break MISSING_BLOCK_LABEL_141;
		}
		Class.forName(driverName);
		if (isStringEmpty(dbURL))
			break MISSING_BLOCK_LABEL_141;
		con = DriverManager.getConnection(dbURL);
		break MISSING_BLOCK_LABEL_81;
		ClassNotFoundException ex;
		ex;
		if (con != null && !con.isClosed())
			con.close();
		throw ex;
		if (con != null && !con.isClosed())
			con.close();
		break MISSING_BLOCK_LABEL_141;
		ex;
		addNetCheckFatal(checks, "Could not find Database Driver Class. Check Database Driver is in the classpath and is readable.");
		break MISSING_BLOCK_LABEL_141;
		SQLException sqlex;
		sqlex;
		addNetCheckFatal(checks, (new StringBuilder("The Database URL is incorrect. Connection error is : ")).append(sqlex.toString()).toString());
		if (isStringEmpty(dbURL))
			addNetCheckFatal(checks, "Database URL needs to be entered.");
		if (isStringEmpty(SQLQuery))
			addNetCheckFatal(checks, "Database SQL Query needs to be entered.");
		return checks;
	}

	private boolean isStringEmpty(String s)
	{
		return s == null || s.trim().equals("");
	}

	private void addNetCheckFatal(TreeSet checks, String s)
	{
		checks.add(new NetCheck(0, s, this));
	}

}
