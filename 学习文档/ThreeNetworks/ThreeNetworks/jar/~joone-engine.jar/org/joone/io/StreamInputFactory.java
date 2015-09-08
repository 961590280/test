// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StreamInputFactory.java

package org.joone.io;

import java.io.File;
import java.io.IOException;

// Referenced classes of package org.joone.io:
//			URLInputSynapse, FileInputSynapse, StreamInputSynapse

public class StreamInputFactory
{

	public StreamInputFactory()
	{
	}

	public StreamInputSynapse getInput(String streamName)
		throws IOException
	{
		if (streamName.startsWith("http://") || streamName.startsWith("ftp://"))
		{
			URLInputSynapse uis = new URLInputSynapse();
			uis.setURL(streamName);
			return uis;
		} else
		{
			FileInputSynapse fis = new FileInputSynapse();
			fis.setInputFile(new File(streamName));
			return fis;
		}
	}
}
