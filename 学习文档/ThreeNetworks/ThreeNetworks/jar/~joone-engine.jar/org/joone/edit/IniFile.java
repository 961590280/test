// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IniFile.java

package org.joone.edit;

import java.io.*;
import java.util.*;

public class IniFile
{

	String fileName;
	Collection cache;

	public IniFile(String fileNameArg)
		throws IOException
	{
		cache = new ArrayList();
		fileName = fileNameArg;
		File file = new File(fileName);
		if (!file.exists())
		{
			PrintWriter pw = new PrintWriter(new FileWriter(file));
			pw.flush();
			pw.close();
		}
	}

	public void setParameter(String section, String item, String value)
		throws IOException, IllegalArgumentException
	{
		if (section == null || section.trim().equals(""))
			throw new IllegalArgumentException("Section is null or blank.");
		if (item == null || item.trim().equals(""))
			throw new IllegalArgumentException("Item is null or blank.");
		if (value == null)
			throw new IllegalArgumentException("Value is null.");
		cache = new ArrayList();
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		boolean foundSection = false;
		String line;
		while ((line = br.readLine()) != null) 
		{
			cache.add(line);
			if (line.toUpperCase().equals((new StringBuilder("[")).append(section.toUpperCase()).append("]").toString()))
			{
				foundSection = true;
				break;
			}
		}
		if (!foundSection)
		{
			cache.add((new StringBuilder("[")).append(section.toUpperCase()).append("]").toString());
			foundSection = true;
		}
		boolean foundItem = false;
		while ((line = br.readLine()) != null) 
			if (line.startsWith((new StringBuilder(String.valueOf(item.toLowerCase()))).append("=").toString()))
			{
				cache.add((new StringBuilder(String.valueOf(item.toLowerCase()))).append("=").append(value.toLowerCase()).toString());
				foundItem = true;
			} else
			{
				if (line.startsWith("[") && !foundItem)
				{
					cache.add((new StringBuilder(String.valueOf(item.toLowerCase()))).append("=").append(value.toLowerCase()).toString());
					foundSection = false;
					foundItem = true;
				}
				cache.add(line);
			}
		if (!foundItem && foundSection)
			cache.add((new StringBuilder(String.valueOf(item.toLowerCase()))).append("=").append(value.toLowerCase()).toString());
		br.close();
		PrintWriter pw = new PrintWriter(new FileWriter(fileName));
		for (Iterator iter = cache.iterator(); iter.hasNext(); pw.println((String)iter.next()));
		pw.flush();
		pw.close();
	}

	public String getParameter(String section, String item)
		throws IOException, IllegalArgumentException
	{
		if (!cache.isEmpty())
		{
			String result = null;
			boolean foundSection = false;
			Iterator iter = cache.iterator();
			while (iter.hasNext()) 
			{
				String line = (String)iter.next();
				if (line.toUpperCase().equals((new StringBuilder("[")).append(section.toUpperCase()).append("]").toString()))
				{
					foundSection = true;
					continue;
				}
				if (foundSection && line.startsWith((new StringBuilder(String.valueOf(item))).append("=").toString()))
					result = line.substring(1 + line.indexOf("="));
				if (foundSection && line.startsWith("["))
					break;
			}
			if (result != null)
				return result;
		} else
		{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			boolean foundSection = false;
			String result = null;
			String line;
			while ((line = br.readLine()) != null) 
			{
				cache.add(line);
				if (line.toUpperCase().equals((new StringBuilder("[")).append(section.toUpperCase()).append("]").toString()))
				{
					foundSection = true;
				} else
				{
					if (foundSection && line.startsWith((new StringBuilder(String.valueOf(item))).append("=").toString()))
						result = line.substring(1 + line.indexOf("="));
					if (foundSection && line.startsWith("["))
						foundSection = false;
				}
			}
			if (result != null)
				return result;
		}
		throw new IllegalArgumentException((new StringBuilder("Section {")).append(section).append("} item {").append(item).append("} not found.").toString());
	}

	public String getParameter(String section, String item, String deflt)
		throws IOException
	{
		return getParameter(section, item);
		IllegalArgumentException e;
		e;
		return deflt;
	}

	public static void main(String args[])
	{
		try
		{
			IniFile i = new IniFile("q.ini");
			i.setParameter("section 1", "item 1", "value 1");
			i.setParameter("sec 2", "item 2", "val 2");
			i.setParameter("section 1", "item 3", "val 3");
			System.out.println(i.getParameter("section 1", "item 3", "55"));
			System.out.println(i.getParameter("sec 2", "item 2"));
			System.out.println(i.getParameter("section 1", "item 4", "55"));
			System.out.println(i.getParameter("section 2", "item 2"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
