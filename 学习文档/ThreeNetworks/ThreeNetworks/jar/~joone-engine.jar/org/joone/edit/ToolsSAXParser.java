// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ToolsSAXParser.java

package org.joone.edit;

import java.io.InputStream;
import java.util.Vector;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

// Referenced classes of package org.joone.edit:
//			ToolElement

public class ToolsSAXParser extends DefaultHandler
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/edit/ToolsSAXParser);
	protected Vector elements;

	public ToolsSAXParser(InputStream xmlFile)
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try
		{
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(xmlFile, this);
		}
		catch (Throwable t)
		{
			log.error("Problem while parsing the XML document with the SAXParser", t);
		}
	}

	public void startElement(String name, String sName, String qName, Attributes attrs)
		throws SAXException
	{
		String eName = sName;
		if ("".equals(eName))
			eName = qName;
		ToolElement te = new ToolElement(eName);
		if (attrs != null)
		{
			for (int i = 0; i < attrs.getLength(); i++)
			{
				String aName = attrs.getLocalName(i);
				if ("".equals(aName))
					aName = attrs.getQName(i);
				te.setParam(aName, attrs.getValue(i));
			}

		}
		elements.addElement(te);
	}

	public Vector getElements()
	{
		return elements;
	}

	public void startDocument()
		throws SAXException
	{
		elements = new Vector();
	}

}
