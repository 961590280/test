// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JDBCOutputSynapse.java

package org.joone.io;

import java.sql.*;
import java.util.TreeSet;
import org.joone.engine.NetErrorManager;
import org.joone.engine.Pattern;
import org.joone.exception.JooneRuntimeException;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NetCheck;

// Referenced classes of package org.joone.io:
//			StreamOutputSynapse

public class JDBCOutputSynapse extends StreamOutputSynapse
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/io/JDBCOutputSynapse);
	private String driverName;
	private String dbURL;
	private String SQLAmendment;
	private transient Connection con;
	private transient Statement stmt;
	private static final long serialVersionUID = 0x1e35a98687512ff7L;

	public JDBCOutputSynapse()
	{
		driverName = "";
		dbURL = "";
		SQLAmendment = "";
		con = null;
		stmt = null;
	}

	public JDBCOutputSynapse(String newDrivername, String newdbURL, String newSQLAmendment, boolean buffered)
	{
		driverName = "";
		dbURL = "";
		SQLAmendment = "";
		con = null;
		stmt = null;
		setBuffered(buffered);
		driverName = newDrivername;
		dbURL = newdbURL;
		SQLAmendment = newSQLAmendment;
	}

	public String getdriverName()
	{
		return driverName;
	}

	public String getdbURL()
	{
		return dbURL;
	}

	public String getSQLAmendment()
	{
		return SQLAmendment;
	}

	public void setdriverName(String newDriverName)
	{
		driverName = newDriverName;
	}

	public void setdbURL(String newdbURL)
	{
		dbURL = newdbURL;
	}

	public void setSQLAmendment(String newSQLAmendment)
	{
		SQLAmendment = newSQLAmendment;
	}

	protected void initStream()
		throws JooneRuntimeException
	{
		if (driverName != null && !driverName.trim().equals(""))
		{
			if (dbURL != null && !dbURL.trim().equals(""))
			{
				if (SQLAmendment != null && !SQLAmendment.trim().equals(""))
				{
					try
					{
						Class.forName(driverName);
						con = DriverManager.getConnection(dbURL);
						stmt = con.createStatement();
					}
					catch (ClassNotFoundException ex)
					{
						log.error((new StringBuilder("Could not find Database Driver Class while initializing the JDBCInputStream. Message is : ")).append(ex.getMessage()).toString(), ex);
						if (getMonitor() != null)
							new NetErrorManager(getMonitor(), (new StringBuilder("Could not find Database Driver Class while initializing the JDBCInputStream. Message is : ")).append(ex.getMessage()).toString());
					}
					catch (SQLException sqlex)
					{
						log.error((new StringBuilder("SQLException thrown while initializing the JDBCInputStream. Message is : ")).append(sqlex.getMessage()).toString(), sqlex);
						if (getMonitor() != null)
							new NetErrorManager(getMonitor(), (new StringBuilder("SQLException thrown while initializing the JDBCInputStream. Message is : ")).append(sqlex.getMessage()).toString());
					}
				} else
				{
					String err = "The SQL Amendment has not been entered!";
					log.warn(err);
					if (getMonitor() != null)
						new NetErrorManager(getMonitor(), "The SQL Amendment has not been entered!");
				}
			} else
			{
				String err = "The Database URL has not been entered!";
				log.warn(err);
				if (getMonitor() != null)
					new NetErrorManager(getMonitor(), "The Database URL has not been entered!");
			}
		} else
		{
			String err = "The Driver Name has not been entered!";
			log.warn(err);
			if (getMonitor() != null)
				new NetErrorManager(getMonitor(), "The Driver Name has not been entered!");
		}
	}

	public TreeSet check()
	{
		TreeSet checks;
		checks = super.check();
		if (driverName == null || driverName.compareTo("") == 0)
		{
			checks.add(new NetCheck(0, "Database Driver Name needs to be entered.", this));
			break MISSING_BLOCK_LABEL_201;
		}
		Connection con;
		Class.forName(driverName);
		if (isUrlEmpty())
			break MISSING_BLOCK_LABEL_201;
		con = DriverManager.getConnection(dbURL);
		stmt = con.createStatement();
		break MISSING_BLOCK_LABEL_103;
		ClassNotFoundException ex;
		ex;
		if (stmt != null)
		{
			stmt.close();
			stmt = null;
		}
		throw ex;
		if (stmt != null)
		{
			stmt.close();
			stmt = null;
		}
		break MISSING_BLOCK_LABEL_138;
		Exception exception;
		exception;
		con.close();
		throw exception;
		con.close();
		break MISSING_BLOCK_LABEL_201;
		ex;
		checks.add(new NetCheck(0, "Could not find Database Driver Class. Check Database Driver is in the classpath and is readable.", this));
		break MISSING_BLOCK_LABEL_201;
		SQLException sqlex;
		sqlex;
		checks.add(new NetCheck(0, (new StringBuilder("The Database URL is incorrect. Connection error is : ")).append(sqlex.toString()).toString(), this));
		if (dbURL == null || dbURL.compareTo("") == 0)
			checks.add(new NetCheck(0, "Database URL needs to be entered.", this));
		if (SQLAmendment == null || SQLAmendment.compareTo("") == 0)
			checks.add(new NetCheck(0, "Database SQL Amendment needs to be entered.", this));
		return checks;
	}

	private boolean isUrlEmpty()
	{
		return dbURL == null || dbURL.equals("");
	}

	public void write(Pattern pattern)
		throws JooneRuntimeException
	{
		String Amendment = SQLAmendment;
		if (con == null || stmt == null || pattern.getCount() == 1)
			initStream();
		if (pattern.isMarkedAsStoppingPattern())
		{
			try
			{
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			}
			catch (SQLException ex)
			{
				stmt = null;
				con = null;
			}
			return;
		}
		try
		{
			if (Amendment != null && !Amendment.equals(""))
			{
				for (int i = 0; i < pattern.getArray().length; i++)
				{
					String searchString = (new StringBuilder("JOONE[")).append(i + 1).append("]").toString();
					String replacement = Double.toString(pattern.getArray()[i]);
					StringBuffer buf;
					int idx;
					for (; Amendment.indexOf(searchString) >= 0; Amendment = buf.replace(idx, idx + searchString.length(), replacement).toString())
					{
						buf = new StringBuffer(Amendment);
						idx = Amendment.indexOf(searchString);
					}

				}

				stmt.executeUpdate(Amendment);
			}
		}
		catch (SQLException ex)
		{
			String err = (new StringBuilder("An SQL error occurred while trying to execute [")).append(Amendment).append("], error is ").append(ex.toString()).toString();
			log.error(err);
			if (getMonitor() != null)
				new NetErrorManager(getMonitor(), err);
		}
	}

}
