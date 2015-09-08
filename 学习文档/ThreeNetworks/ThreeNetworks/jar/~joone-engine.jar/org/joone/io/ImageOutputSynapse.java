// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ImageOutputSynapse.java

package org.joone.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.TreeSet;
import javax.imageio.ImageIO;
import org.joone.engine.Pattern;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NetCheck;

// Referenced classes of package org.joone.io:
//			StreamOutputSynapse

public class ImageOutputSynapse extends StreamOutputSynapse
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/io/ImageOutputSynapse);
	static final long serialVersionUID = 0x11df04124402399eL;
	public final int JPG = 1;
	public final int GIF = 2;
	public final int PNG = 3;
	private String OutputDirectory;
	private int ImageFileType;
	private int Width;
	private int Height;
	private boolean ColourMode;

	public ImageOutputSynapse()
	{
		OutputDirectory = System.getProperty("user.dir");
		ImageFileType = 1;
		Width = 10;
		Height = 10;
		ColourMode = true;
	}

	public void write(Pattern pattern)
	{
		if (!pattern.isMarkedAsStoppingPattern())
		{
			String formatName;
			switch (getImageFileType())
			{
			case 1: // '\001'
				formatName = "jpg";
				break;

			case 2: // '\002'
				formatName = "gif";
				break;

			case 3: // '\003'
				formatName = "png";
				break;

			default:
				formatName = "jpg";
				break;
			}
			try
			{
				String outDir = getOutputDirectory();
				if (!outDir.endsWith(System.getProperty("file.separator")))
					outDir = (new StringBuilder(String.valueOf(outDir))).append(System.getProperty("file.separator")).toString();
				File theOutFile = new File((new StringBuilder(String.valueOf(outDir))).append("Image").append(pattern.getCount()).append(".").append(formatName).toString());
				BufferedImage outImage = patternToImage(pattern);
				if (outImage != null)
					ImageIO.write(outImage, formatName, theOutFile);
			}
			catch (Exception ex)
			{
				log.error((new StringBuilder("Caught exception when trying to write Image : ")).append(ex.toString()).toString());
			}
		}
	}

	private BufferedImage patternToImage(Pattern pat)
	{
		BufferedImage theImage = new BufferedImage(getWidth(), getHeight(), 1);
		double array[] = pat.getArray();
		int Red = 0;
		int Green = 0;
		int Blue = 0;
		int rgb = 0;
		int x = 0;
		int y = 0;
		int j = 0;
		if (ColourMode)
		{
			if (array.length == getWidth() * getHeight() * 3)
				for (j = 0; j < array.length; j += 3)
				{
					Red = (int)(array[j] * 255D);
					Green = (int)(array[j + 1] * 255D);
					Blue = (int)(array[j + 2] * 255D);
					rgb = 0 + (Red << 16) + (Green << 8) + Blue;
					theImage.setRGB(x, y, rgb);
					if (++x >= getWidth())
					{
						x = 0;
						y++;
					}
				}

			else
				log.error((new StringBuilder("Pattern Contains ")).append(pat.getCount()).append(" RGB Values - Image Contains ").append(getWidth() * getHeight()).append(" RGB Values , Size Mismatch.").toString());
		} else
		if (array.length == getWidth() * getHeight())
			for (j = 0; j < array.length; j++)
			{
				rgb = (int)(array[j] * 255D);
				rgb = 0 + (rgb << 16) + (rgb << 8) + rgb;
				theImage.setRGB(x, y, rgb);
				if (++x >= getWidth())
				{
					x = 0;
					y++;
				}
			}

		else
			log.error((new StringBuilder("Pattern Contains ")).append(pat.getCount()).append(" RGB Values - Image Contains ").append(getWidth() * getHeight()).append(" RGB Values , Size Mismatch.").toString());
		return theImage;
	}

	public String getOutputDirectory()
	{
		return OutputDirectory;
	}

	public void setOutputDirectory(String OutputDirectory)
	{
		this.OutputDirectory = OutputDirectory;
	}

	public int getImageFileType()
	{
		return ImageFileType;
	}

	public void setImageFileType(int ImageFileType)
	{
		this.ImageFileType = ImageFileType;
		if (this.ImageFileType < 1)
			this.ImageFileType = 1;
		if (this.ImageFileType > 3)
			this.ImageFileType = 3;
	}

	public int getWidth()
	{
		return Width;
	}

	public void setWidth(int Width)
	{
		this.Width = Width;
	}

	public int getHeight()
	{
		return Height;
	}

	public void setHeight(int Height)
	{
		this.Height = Height;
	}

	public boolean getColourMode()
	{
		return ColourMode;
	}

	public void setColourMode(boolean ColourMode)
	{
		this.ColourMode = ColourMode;
	}

	public TreeSet check()
	{
		TreeSet checks = super.check();
		if (ColourMode)
		{
			if (Width * Height * 3 != getInputDimension())
				checks.add(new NetCheck(0, (new StringBuilder("Image Width[")).append(getWidth()).append("]*Height[").append(getHeight()).append("]*3 not equal to number of inputs from connected layer/s [").append(getInputDimension()).append("].").toString(), this));
		} else
		if (Width * Height != getInputDimension())
			checks.add(new NetCheck(0, (new StringBuilder("Image Width[")).append(getWidth()).append("]*Height[").append(getHeight()).append("] not equal to number of inputs from connected layer/s [").append(getInputDimension()).append("].").toString(), this));
		if (OutputDirectory.equals(""))
			checks.add(new NetCheck(0, "No image output directory set.", this));
		return checks;
	}

}
