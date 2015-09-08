// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ImagePainter.java

package org.joone.samples.editor.som;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;

// Referenced classes of package org.joone.samples.editor.som:
//			ImageDrawer

public class ImagePainter extends ImageDrawer
{

	private Color drawColor;
	private BufferedImage ImageToEdit;

	public ImagePainter()
	{
		drawColor = new Color(0);
		ImageToEdit = null;
		addMouseListener(new MouseAdapter() {

			final ImagePainter this$0;

			public void mousePressed(MouseEvent e)
			{
				paintPixel(e.getX(), e.getY());
			}

			
			{
				this$0 = ImagePainter.this;
				super();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {

			final ImagePainter this$0;

			public void mouseDragged(MouseEvent e)
			{
				paintPixel(e.getX(), e.getY());
			}

			
			{
				this$0 = ImagePainter.this;
				super();
			}
		});
	}

	public void setDrawColor(Color newColor)
	{
		drawColor = newColor;
	}

	public Color getDrawColor()
	{
		return drawColor;
	}

	public void paintPixel(int x, int y)
	{
		if (x < getImageToDraw().getWidth(this) && x >= 0 && y < getImageToDraw().getHeight(this) && y >= 0)
		{
			getImageToEdit().setRGB(x, y, getDrawColor().getRGB());
			repaint(0, 0, getImageToDraw().getWidth(this), getImageToDraw().getHeight(this));
		}
	}

	public BufferedImage getImageToEdit()
	{
		return ImageToEdit;
	}

	public void setImageToEdit(BufferedImage ImageToEdit)
	{
		this.ImageToEdit = ImageToEdit;
		setImageToDraw(ImageToEdit);
	}
}
