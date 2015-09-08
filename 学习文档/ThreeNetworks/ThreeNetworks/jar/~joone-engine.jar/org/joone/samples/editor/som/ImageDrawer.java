// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ImageDrawer.java

package org.joone.samples.editor.som;

import java.awt.*;
import javax.swing.JPanel;

public class ImageDrawer extends JPanel
{

	private Image img;

	public ImageDrawer()
	{
		img = null;
	}

	public void setImageToDraw(Image newimg)
	{
		img = newimg;
		if (img != null)
			repaint(0, 0, img.getWidth(this), img.getHeight(this));
	}

	public Image getImageToDraw()
	{
		return img;
	}

	public void paintComponent(Graphics g)
	{
		if (img != null)
			g.drawImage(img, 0, 0, img.getWidth(this), img.getHeight(this), Color.BLACK, this);
	}
}
