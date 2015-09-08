// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   URLInputSynapse.java

package org.joone.io;

import java.io.*;
import java.net.URL;
import java.util.Vector;
import org.joone.engine.NetErrorManager;
import org.joone.exception.JooneRuntimeException;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.io:
//			StreamInputSynapse, StreamInputTokenizer

public class URLInputSynapse extends StreamInputSynapse
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/io/URLInputSynapse);
	private String URL;
	private URL cURL;
	private static final long serialVersionUID = 0xe606caff410545b0L;

	public URLInputSynapse()
	{
		URL = "http://";
	}

	public String getURL()
	{
		return URL;
	}

	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		super.readObjectBase(in);
		if (in.getClass().getName().indexOf("xstream") == -1)
			URL = (String)in.readObject();
		if (!isBuffered() || getInputVector().size() == 0)
			setURL(URL);
	}

	public void setURL(String newURL)
	{
		if (!URL.equals(newURL))
		{
			resetInput();
			setTokens(null);
		}
	}

	private void writeObject(ObjectOutputStream out)
		throws IOException
	{
		super.writeObjectBase(out);
		if (out.getClass().getName().indexOf("xstream") == -1)
			out.writeObject(URL);
	}

	protected void initInputStream()
		throws JooneRuntimeException
	{
		if (URL != null && !"".equals(URL.trim()))
			try
			{
				cURL = new URL(URL);
				InputStream is = cURL.openStream();
				StreamInputTokenizer sit;
				if (getMaxBufSize() > 0)
					sit = new StreamInputTokenizer(new InputStreamReader(is), getMaxBufSize());
				else
					sit = new StreamInputTokenizer(new InputStreamReader(is));
				super.setTokens(sit);
			}
			catch (IOException ioe)
			{
				log.warn((new StringBuilder("Could not extract data from the URL '")).append(URL).append("' Message is : ").append(ioe.getMessage()).toString());
				if (getMonitor() != null)
					new NetErrorManager(getMonitor(), (new StringBuilder("Could not extract data from the URL '")).append(URL).append("' Message is : ").append(ioe.getMessage()).toString());
			}
		else
			log.warn("URLInputSynapse: provided URL contains no data");
	}

}
