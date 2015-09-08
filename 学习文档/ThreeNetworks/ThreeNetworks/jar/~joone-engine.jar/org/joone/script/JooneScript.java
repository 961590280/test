// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JooneScript.java

package org.joone.script;

import bsh.EvalError;
import bsh.Interpreter;
import java.io.*;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

public class JooneScript
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/script/JooneScript);
	private Interpreter jShell;

	public JooneScript()
	{
	}

	private Interpreter getShell()
	{
		if (jShell == null)
		{
			jShell = new Interpreter();
			try
			{
				jShell.eval("import org.joone.engine.*;");
				jShell.eval("import org.joone.engine.learning.*;");
				jShell.eval("import org.joone.edit.*;");
				jShell.eval("import org.joone.util.*;");
				jShell.eval("import org.joone.net.*;");
				jShell.eval("import org.joone.io.*;");
				jShell.eval("import org.joone.script.*;");
			}
			catch (EvalError err)
			{
				log.error((new StringBuilder("EvalError thrown. Message is ")).append(err.getMessage()).toString(), err);
				return null;
			}
		}
		return jShell;
	}

	public static void main(String args[])
	{
		if (args.length == 0)
		{
			System.out.println("Usage: java org.joone.script.JooneScript <script_file>");
		} else
		{
			JooneScript jScript = new JooneScript();
			jScript.source(args[0]);
		}
	}

	public void source(String fileName)
	{
		try
		{
			getShell().source(fileName);
		}
		catch (FileNotFoundException fnfe)
		{
			log.error((new StringBuilder("FileNotFoundException thrown. Message is : ")).append(fnfe.getMessage()).toString(), fnfe);
		}
		catch (IOException ioe)
		{
			log.error((new StringBuilder("IOException thrown. Message is : ")).append(ioe.getMessage()).toString(), ioe);
		}
		catch (EvalError err)
		{
			log.warn((new StringBuilder("EvalError thrown. Message is : ")).append(err.getMessage()).toString(), err);
			System.out.println("Invalid BeanShell code!");
		}
	}

	public void eval(String script)
	{
		try
		{
			getShell().eval(script);
		}
		catch (EvalError err)
		{
			log.warn((new StringBuilder("Error while evaluating. Message is : ")).append(err.getMessage()).toString(), err);
			System.out.println("Invalid BeanShell code!");
			err.printStackTrace();
		}
	}

	public void set(String name, Object jObject)
	{
		try
		{
			getShell().set(name, jObject);
		}
		catch (EvalError err)
		{
			err.printStackTrace();
		}
	}

}
