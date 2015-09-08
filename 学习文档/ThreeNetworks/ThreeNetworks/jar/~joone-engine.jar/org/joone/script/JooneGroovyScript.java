// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JooneGroovyScript.java

package org.joone.script;

import groovy.lang.GroovyShell;
import java.io.*;
import org.codehaus.groovy.control.CompilationFailedException;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

public class JooneGroovyScript
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/script/JooneGroovyScript);
	private static final String GROOVY_IMPORT_PREFIX = "import org.joone.engine.*\nimport org.joone.engine.learning.*\nimport org.joone.edit.*\nimport org.joone.util.*\nimport org.joone.net.*\nimport org.joone.io.*\nimport org.joone.script.*\n";
	private GroovyShell groovyShell;

	public JooneGroovyScript()
	{
	}

	private GroovyShell getShell()
	{
		if (groovyShell == null)
			groovyShell = new GroovyShell();
		return groovyShell;
	}

	public static void main(String args[])
	{
		if (args.length == 0)
		{
			System.out.println("Usage: java org.joone.script.JooneGroovyScript <script_file>");
		} else
		{
			JooneGroovyScript jScript = new JooneGroovyScript();
			jScript.source(args[0]);
		}
	}

	public void source(String fileName)
	{
		try
		{
			getShell().run(new File(fileName), new String[0]);
		}
		catch (CompilationFailedException cfe)
		{
			log.error((new StringBuilder("CompilationFailedException thrown. Message is : ")).append(cfe.getMessage()).toString(), cfe);
		}
		catch (IOException ioe)
		{
			log.error((new StringBuilder("IOException thrown. Message is : ")).append(ioe.getMessage()).toString(), ioe);
		}
	}

	public void eval(String script)
	{
		try
		{
			getShell().evaluate((new StringBuilder("import org.joone.engine.*\nimport org.joone.engine.learning.*\nimport org.joone.edit.*\nimport org.joone.util.*\nimport org.joone.net.*\nimport org.joone.io.*\nimport org.joone.script.*\n")).append(script).toString());
		}
		catch (CompilationFailedException cfe)
		{
			log.error((new StringBuilder("CompilationFailedException thrown. Message is : ")).append(cfe.getMessage()).toString(), cfe);
		}
		catch (IOException ioe)
		{
			log.error((new StringBuilder("IOException thrown. Message is : ")).append(ioe.getMessage()).toString(), ioe);
		}
	}

	public void set(String name, Object jObject)
	{
		getShell().setVariable(name, jObject);
	}

}
