// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ImageInputSynapse.java

package org.joone.io;

import java.awt.Image;
import java.io.File;
import java.io.FilenameFilter;
import java.util.TreeSet;
import java.util.Vector;
import org.joone.exception.JooneRuntimeException;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NetCheck;

// Referenced classes of package org.joone.io:
//			StreamInputSynapse, ImageInputTokenizer

public class ImageInputSynapse extends StreamInputSynapse
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/io/ImageInputSynapse);
	static final long serialVersionUID = 0xec9f643ce62cbb26L;
	private File imageDirectory;
	private String theFileFilter;
	private Vector FileNameList;
	private Image MultiImages[];
	private int DesiredWidth;
	private int DesiredHeight;
	private boolean ColourMode;

	public ImageInputSynapse()
	{
		imageDirectory = new File(System.getProperty("user.dir"));
		theFileFilter = new String(".*[jJ][pP][gG]");
		FileNameList = new Vector();
		MultiImages = null;
		DesiredWidth = 0;
		DesiredHeight = 0;
		ColourMode = true;
		calculateNewACS();
	}

	public void setFileFilter(String newFileFilter)
	{
		theFileFilter = newFileFilter;
	}

	public String getFileFilter()
	{
		return theFileFilter;
	}

	public void setImageInput(Image theImages[])
	{
		MultiImages = theImages;
	}

	protected void initInputStream()
		throws JooneRuntimeException
	{
		ImageInputTokenizer toks = null;
		try
		{
			if (MultiImages != null && MultiImages.length > 0)
			{
				toks = new ImageInputTokenizer(getDesiredWidth(), getDesiredHeight(), MultiImages, ColourMode);
				super.setTokens(toks);
			} else
			{
				FileNameList = new Vector();
				String thelist[] = imageDirectory.list(new FilenameFilter() {

					final ImageInputSynapse this$0;

					public boolean accept(File dir, String name)
					{
						return name.matches(theFileFilter);
					}

			
			{
				this$0 = ImageInputSynapse.this;
				super();
			}
				});
				for (int i = 0; i < thelist.length; i++)
				{
					java.net.URL theurl = (new File((new StringBuilder(String.valueOf(imageDirectory.getPath()))).append(System.getProperty("file.separator")).append(thelist[i]).toString())).toURL();
					FileNameList.add(theurl);
				}

				toks = new ImageInputTokenizer(getDesiredWidth(), getDesiredHeight(), FileNameList, ColourMode);
				super.setTokens(toks);
			}
		}
		catch (Exception ex)
		{
			log.error((new StringBuilder("Error initialising the input stream : ")).append(ex.toString()).toString());
		}
	}

	public int getDesiredWidth()
	{
		return DesiredWidth;
	}

	public void setDesiredWidth(int DesiredWidth)
	{
		this.DesiredWidth = DesiredWidth;
		calculateNewACS();
	}

	public int getDesiredHeight()
	{
		return DesiredHeight;
	}

	public void setDesiredHeight(int DesiredHeight)
	{
		this.DesiredHeight = DesiredHeight;
		calculateNewACS();
	}

	public File getImageDirectory()
	{
		return imageDirectory;
	}

	public void setImageDirectory(File imgDir)
	{
		File tmpDir = imgDir;
		if (tmpDir != null && !tmpDir.isDirectory())
		{
			String name = tmpDir.getName();
			int pos = tmpDir.getPath().indexOf(name);
			if (pos > -1)
			{
				String dir = tmpDir.getPath().substring(0, pos);
				tmpDir = new File(dir);
			}
		}
		imageDirectory = tmpDir;
	}

	public boolean getColourMode()
	{
		return ColourMode;
	}

	public void setColourMode(boolean ColourMode)
	{
		this.ColourMode = ColourMode;
		calculateNewACS();
	}

	public TreeSet check()
	{
		TreeSet checks = super.check();
		if (theFileFilter.equals(""))
			checks.add(new NetCheck(0, "No File Filter set e.g. '.*[jJ][pP][gG]'.", this));
		if (imageDirectory == null)
			checks.add(new NetCheck(0, "No image input directory set.", this));
		if (DesiredHeight == 0)
			checks.add(new NetCheck(0, "Height of image not set.", this));
		if (DesiredWidth == 0)
			checks.add(new NetCheck(0, "Width of image not set.", this));
		return checks;
	}

	private void calculateNewACS()
	{
		int tokensCount = DesiredWidth * DesiredHeight;
		if (ColourMode)
			tokensCount *= 3;
		setAdvancedColumnSelector((new StringBuilder("1-")).append(tokensCount).toString());
	}


}
