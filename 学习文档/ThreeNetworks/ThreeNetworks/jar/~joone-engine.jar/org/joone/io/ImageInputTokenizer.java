// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ImageInputTokenizer.java

package org.joone.io;

import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.io.IOException;
import java.net.URL;
import java.util.Vector;
import javax.imageio.ImageIO;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.io:
//			PatternTokenizer

public class ImageInputTokenizer
	implements PatternTokenizer
{

	private int RequiredWidth;
	private int RequiredHeight;
	private int TotalTokens;
	private double ImageTokens[];
	private Vector ImageFileList;
	private Image ArrayOfInputImages[];
	private int TotalInputImages;
	private boolean FileMode;
	private boolean ColourMode;
	private int CurrentImageNo;
	private int CurrentToken;
	private int MarkedImage;
	private int MarkedToken;
	private static final ILogger log = LoggerFactory.getLogger(org/joone/io/ImageInputTokenizer);

	public ImageInputTokenizer(int req_width, int req_height, Vector file_list, boolean colour)
		throws IOException
	{
		RequiredWidth = 0;
		RequiredHeight = 0;
		TotalTokens = 0;
		ImageTokens = null;
		ImageFileList = new Vector();
		ArrayOfInputImages = null;
		TotalInputImages = 0;
		FileMode = true;
		ColourMode = true;
		CurrentImageNo = 0;
		CurrentToken = 0;
		MarkedImage = 0;
		MarkedToken = 0;
		init(req_width, req_height, colour, true);
		ImageFileList = file_list;
		if (ImageFileList != null)
			TotalInputImages = ImageFileList.size();
		else
			TotalInputImages = -1;
	}

	public ImageInputTokenizer(int req_width, int req_height, Image the_images[], boolean colour)
		throws IOException
	{
		RequiredWidth = 0;
		RequiredHeight = 0;
		TotalTokens = 0;
		ImageTokens = null;
		ImageFileList = new Vector();
		ArrayOfInputImages = null;
		TotalInputImages = 0;
		FileMode = true;
		ColourMode = true;
		CurrentImageNo = 0;
		CurrentToken = 0;
		MarkedImage = 0;
		MarkedToken = 0;
		init(req_width, req_height, colour, false);
		ArrayOfInputImages = the_images;
		if (the_images != null)
			TotalInputImages = the_images.length;
		else
			TotalInputImages = -1;
	}

	private void init(int reqWidth, int reqHeight, boolean colour, boolean fileMode)
	{
		FileMode = fileMode;
		RequiredWidth = reqWidth;
		RequiredHeight = reqHeight;
		ColourMode = colour;
		if (ColourMode)
			TotalTokens = RequiredWidth * RequiredHeight * 3;
		else
			TotalTokens = RequiredWidth * RequiredHeight;
		ImageTokens = new double[TotalTokens];
	}

	public int getLineno()
	{
		return CurrentImageNo;
	}

	public int getNumTokens()
		throws IOException
	{
		return TotalTokens;
	}

	public double getTokenAt(int posiz)
		throws IOException
	{
		if (ImageTokens == null || posiz >= TotalTokens)
			return 0.0D;
		else
			return ImageTokens[posiz];
	}

	public double[] getTokensArray()
	{
		return (double[])ImageTokens.clone();
	}

	public void mark()
		throws IOException
	{
		MarkedImage = CurrentImageNo;
		MarkedToken = CurrentToken;
	}

	public boolean nextLine()
		throws IOException
	{
		boolean result = false;
		if (CurrentImageNo >= TotalInputImages || TotalInputImages <= 0)
		{
			return false;
		} else
		{
			result = processImage();
			CurrentImageNo++;
			return result;
		}
	}

	private boolean processImage()
	{
		boolean result = false;
		if (FileMode)
			try
			{
				URL theFile = (URL)ImageFileList.get(CurrentImageNo);
				Image tImg = ImageIO.read(theFile);
				result = processImage(tImg);
			}
			catch (Exception ex)
			{
				log.error((new StringBuilder("Error processing/loading image : ")).append(ex.toString()).toString());
			}
		else
		if (ArrayOfInputImages != null && ArrayOfInputImages.length >= 1)
			result = processImage(ArrayOfInputImages[CurrentImageNo]);
		return result;
	}

	private double nextToken()
		throws IOException
	{
		return nextToken(null);
	}

	private double nextToken(String delim)
		throws IOException
	{
		if (CurrentToken >= TotalTokens)
		{
			return 0.0D;
		} else
		{
			double value = ImageTokens[CurrentToken];
			CurrentToken++;
			return value;
		}
	}

	public void resetInput()
		throws IOException
	{
		if (MarkedImage > 0 || MarkedToken > 0)
		{
			CurrentImageNo = MarkedImage;
			CurrentToken = MarkedToken;
		} else
		{
			CurrentImageNo = 0;
		}
		processImage();
	}

	public void setDecimalPoint(char dp)
	{
		log.error((new StringBuilder("setDecimalPoint not supported in ")).append(getClass()).toString());
	}

	public char getDecimalPoint()
	{
		return '.';
	}

	private boolean processImage(Image theImage)
	{
label0:
		{
			double Red = 0.0D;
			double Green = 0.0D;
			double Blue = 0.0D;
			double Grey = 0.0D;
			try
			{
				int TempTokens[];
				if (ColourMode)
					TempTokens = new int[TotalTokens / 3];
				else
					TempTokens = new int[TotalTokens];
				Image TempImage = theImage.getScaledInstance(RequiredWidth, RequiredHeight, 16);
				PixelGrabber pixgrab = new PixelGrabber(TempImage, 0, 0, TempImage.getWidth(null), TempImage.getHeight(null), TempTokens, 0, TempImage.getWidth(null));
				if (pixgrab.grabPixels())
				{
					if (ColourMode)
					{
						for (int i = 0; i < TempTokens.length; i++)
						{
							Red = (double)((TempTokens[i] & 0xff0000) >> 16) / 255D;
							Green = (double)((TempTokens[i] & 0xff00) >> 8) / 255D;
							Blue = (double)(TempTokens[i] & 0xff) / 255D;
							ImageTokens[i * 3] = Red;
							ImageTokens[i * 3 + 1] = Green;
							ImageTokens[i * 3 + 2] = Blue;
						}

					} else
					{
						for (int i = 0; i < TempTokens.length; i++)
						{
							Red = (double)((TempTokens[i] & 0xff0000) >> 16) / 255D;
							Green = (double)((TempTokens[i] & 0xff00) >> 8) / 255D;
							Blue = (double)(TempTokens[i] & 0xff) / 255D;
							Grey = (Red + Blue + Green) / 3D;
							ImageTokens[i] = Grey;
						}

					}
					break label0;
				}
				log.error("Failed to grab image pixels due to error.");
			}
			catch (Exception ex)
			{
				log.error((new StringBuilder("Error processing image : ")).append(ex.toString()).toString());
				return false;
			}
			return false;
		}
		return true;
	}

}
