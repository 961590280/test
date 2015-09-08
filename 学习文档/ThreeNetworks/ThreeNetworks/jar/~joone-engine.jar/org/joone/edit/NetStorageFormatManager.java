// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NetStorageFormatManager.java

package org.joone.edit;

import java.util.Iterator;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

// Referenced classes of package org.joone.edit:
//			NetStorageFormat

public class NetStorageFormatManager
{

	private Vector myStorageFormats;
	private NetStorageFormat myDefaultStorageFormat;

	public NetStorageFormatManager()
	{
		myStorageFormats = new Vector();
	}

	public void addStorageFormat(NetStorageFormat newStorageFormat)
	{
		myStorageFormats.add(newStorageFormat);
	}

	public void removeStorageFormat(NetStorageFormat oldStorageFormat)
	{
		myStorageFormats.remove(oldStorageFormat);
	}

	public boolean containsStorageFormat(NetStorageFormat checkStorageFormat)
	{
		return myStorageFormats.contains(checkStorageFormat);
	}

	public void setDefaultStorageFormat(NetStorageFormat newDefaultStorageFormat)
	{
		myDefaultStorageFormat = newDefaultStorageFormat;
	}

	public NetStorageFormat getDefaultStorageFormat()
	{
		return myDefaultStorageFormat;
	}

	public void registerFileFilters(JFileChooser fileChooser)
	{
		for (Iterator formatsIterator = myStorageFormats.iterator(); formatsIterator.hasNext(); fileChooser.addChoosableFileFilter(((NetStorageFormat)formatsIterator.next()).getFileFilter()));
		if (getDefaultStorageFormat() != null)
			fileChooser.setFileFilter(getDefaultStorageFormat().getFileFilter());
	}

	public NetStorageFormat findStorageFormat(FileFilter findFileFilter)
	{
		Iterator formatsIterator = myStorageFormats.iterator();
		NetStorageFormat currentStorageFormat = null;
		while (formatsIterator.hasNext()) 
		{
			currentStorageFormat = (NetStorageFormat)formatsIterator.next();
			if (currentStorageFormat.getFileFilter().equals(findFileFilter))
				return currentStorageFormat;
		}
		return null;
	}
}
