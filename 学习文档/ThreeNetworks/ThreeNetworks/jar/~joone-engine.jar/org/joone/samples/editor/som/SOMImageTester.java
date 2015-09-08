// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SOMImageTester.java

package org.joone.samples.editor.som;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.TitledBorder;

// Referenced classes of package org.joone.samples.editor.som:
//			ImagePainter, ImageDrawer

public class SOMImageTester extends JFrame
{

	private int DrawSizeX;
	private int DrawSizeY;
	private int ScaleSizeX;
	private int ScaleSizeY;
	private Vector imageHolder;
	private Image downsamplePreviewImage;
	private BufferedImage downSample;
	private BufferedImage drawImage;
	private Vector idHolder;
	private int currentImage;
	private boolean alone;
	protected int downSampleLeft;
	protected int downSampleRight;
	protected int downSampleTop;
	protected int downSampleBottom;
	protected double ratioX;
	protected double ratioY;
	protected int pixelMap[];
	private JButton ClearImageButton;
	private JPanel ControlPanel;
	private JButton DownSampleButton;
	private JPanel DownsamplePanel;
	private JButton HelpButton;
	private JTextField IDInputTextField;
	private JPanel ImageHolderPanel;
	private JLabel ImageIDLabel;
	private JLabel ImageNoLabel;
	private JScrollBar ImageScrollBar;
	private JPanel InfoPanel;
	private JButton NewImageButton;
	private JPanel PainterPanel;
	private JButton QuitButton;
	private JButton SaveImagesButton;

	public SOMImageTester()
	{
		this(false);
	}

	public SOMImageTester(boolean main)
	{
		DrawSizeX = 81;
		DrawSizeY = 81;
		ScaleSizeX = 9;
		ScaleSizeY = 9;
		imageHolder = new Vector();
		downsamplePreviewImage = null;
		downSample = new BufferedImage(getScaleSizeX(), getScaleSizeY(), 1);
		drawImage = new BufferedImage(getDrawSizeX(), getDrawSizeY(), 1);
		idHolder = new Vector();
		currentImage = 0;
		alone = main;
		initComponents();
		setup();
		setSize(300, 350);
		setResizable(false);
	}

	private void initComponents()
	{
		ImageHolderPanel = new JPanel();
		PainterPanel = new ImagePainter();
		DownsamplePanel = new ImageDrawer();
		InfoPanel = new JPanel();
		ImageIDLabel = new JLabel();
		IDInputTextField = new JTextField();
		ImageNoLabel = new JLabel();
		DownSampleButton = new JButton();
		ImageScrollBar = new JScrollBar();
		ControlPanel = new JPanel();
		HelpButton = new JButton();
		NewImageButton = new JButton();
		ClearImageButton = new JButton();
		SaveImagesButton = new JButton();
		QuitButton = new JButton();
		addWindowListener(new WindowAdapter() {

			final SOMImageTester this$0;

			public void windowClosing(WindowEvent evt)
			{
				exitForm(evt);
			}

			
			{
				this$0 = SOMImageTester.this;
				super();
			}
		});
		ImageHolderPanel.setLayout(new GridLayout(1, 2));
		PainterPanel.setToolTipText("You can draw on this image.");
		ImageHolderPanel.add(PainterPanel);
		DownsamplePanel.setToolTipText("This contains the down sampled image.");
		ImageHolderPanel.add(DownsamplePanel);
		getContentPane().add(ImageHolderPanel, "Center");
		InfoPanel.setLayout(new GridLayout(2, 2));
		ImageIDLabel.setText("Image ID");
		InfoPanel.add(ImageIDLabel);
		IDInputTextField.setText("1");
		InfoPanel.add(IDInputTextField);
		ImageNoLabel.setFont(new Font("Dialog", 1, 14));
		ImageNoLabel.setText("Image 1 of 1");
		ImageNoLabel.setToolTipText("The current image number.");
		InfoPanel.add(ImageNoLabel);
		DownSampleButton.setText("Down Sample");
		DownSampleButton.addActionListener(new ActionListener() {

			final SOMImageTester this$0;

			public void actionPerformed(ActionEvent evt)
			{
				DownSampleButtonActionPerformed(evt);
			}

			
			{
				this$0 = SOMImageTester.this;
				super();
			}
		});
		InfoPanel.add(DownSampleButton);
		getContentPane().add(InfoPanel, "North");
		ImageScrollBar.setMaximum(1);
		ImageScrollBar.setMinimum(1);
		ImageScrollBar.setToolTipText("Use scroll bar to scroll through images.");
		ImageScrollBar.addAdjustmentListener(new AdjustmentListener() {

			final SOMImageTester this$0;

			public void adjustmentValueChanged(AdjustmentEvent evt)
			{
				OnScrolled(evt);
			}

			
			{
				this$0 = SOMImageTester.this;
				super();
			}
		});
		getContentPane().add(ImageScrollBar, "East");
		ControlPanel.setLayout(new GridLayout(5, 2));
		ControlPanel.setBorder(new TitledBorder("Controls"));
		HelpButton.setText("Help");
		HelpButton.setToolTipText("Help on this application.");
		HelpButton.addActionListener(new ActionListener() {

			final SOMImageTester this$0;

			public void actionPerformed(ActionEvent evt)
			{
				HelpButtonActionPerformed(evt);
			}

			
			{
				this$0 = SOMImageTester.this;
				super();
			}
		});
		ControlPanel.add(HelpButton);
		NewImageButton.setText("New Image");
		NewImageButton.setToolTipText("Create a new image.");
		NewImageButton.addActionListener(new ActionListener() {

			final SOMImageTester this$0;

			public void actionPerformed(ActionEvent evt)
			{
				NewImageButtonActionPerformed(evt);
			}

			
			{
				this$0 = SOMImageTester.this;
				super();
			}
		});
		ControlPanel.add(NewImageButton);
		ClearImageButton.setText("Clear Image");
		ClearImageButton.setToolTipText("Clear the drawing from this image.");
		ClearImageButton.addActionListener(new ActionListener() {

			final SOMImageTester this$0;

			public void actionPerformed(ActionEvent evt)
			{
				ClearImageButtonActionPerformed(evt);
			}

			
			{
				this$0 = SOMImageTester.this;
				super();
			}
		});
		ControlPanel.add(ClearImageButton);
		SaveImagesButton.setText("Save Images");
		SaveImagesButton.setToolTipText("Save the images out to Joone format.");
		SaveImagesButton.addActionListener(new ActionListener() {

			final SOMImageTester this$0;

			public void actionPerformed(ActionEvent evt)
			{
				SaveImagesButtonActionPerformed(evt);
			}

			
			{
				this$0 = SOMImageTester.this;
				super();
			}
		});
		ControlPanel.add(SaveImagesButton);
		QuitButton.setText("Quit");
		QuitButton.setToolTipText("Quit this application.");
		QuitButton.addActionListener(new ActionListener() {

			final SOMImageTester this$0;

			public void actionPerformed(ActionEvent evt)
			{
				QuitButtonActionPerformed(evt);
			}

			
			{
				this$0 = SOMImageTester.this;
				super();
			}
		});
		ControlPanel.add(QuitButton);
		getContentPane().add(ControlPanel, "South");
		pack();
	}

	private void HelpButtonActionPerformed(ActionEvent evt)
	{
		String help1 = new String("This application allows the user to draw characters or images for recognition by a Joone neural network.");
		String help2 = new String("It is intended to test a SOM or Kohonen Network by providing an image recognition example.");
		String help3 = new String("The drawing image grid is 81 X 81 but the images saved in the file are 9x9 down sampled images.");
		String help4 = new String("The saved file has 81 inputs and an id.  The id can be used to identify the character.");
		String help5 = new String("Read the Editor's help pages to learn how to use this example.");
		JOptionPane.showMessageDialog(this, (new StringBuilder(String.valueOf(help1))).append("\n").append(help2).append("\n").append(help3).append("\n").append(help4).append("\n").append(help5).toString());
	}

	private void QuitButtonActionPerformed(ActionEvent evt)
	{
		exitTester();
	}

	private void SaveImagesButtonActionPerformed(ActionEvent evt)
	{
		SaveImagesOut();
	}

	private void NewImageButtonActionPerformed(ActionEvent evt)
	{
		NewImage();
	}

	private void ClearImageButtonActionPerformed(ActionEvent evt)
	{
		clearCurrentImage();
	}

	private void DownSampleButtonActionPerformed(ActionEvent evt)
	{
		downSample();
		repaint();
	}

	private void OnScrolled(AdjustmentEvent evt)
	{
		Integer id = null;
		if (evt.getAdjustmentType() == 5 && evt.getValue() <= imageHolder.size())
		{
			try
			{
				id = new Integer(IDInputTextField.getText());
			}
			catch (NumberFormatException ex)
			{
				ImageScrollBar.setValue(ImageScrollBar.getValue() - ImageScrollBar.getUnitIncrement());
				JOptionPane.showMessageDialog(this, "ID must be an integer value.");
				return;
			}
			idHolder.set(currentImage - 1, id);
			currentImage = evt.getValue();
			IDInputTextField.setText((new StringBuilder()).append(((Integer)idHolder.get(currentImage - 1)).intValue()).toString());
			drawImage = (BufferedImage)imageHolder.get(currentImage - 1);
			((ImagePainter)PainterPanel).setImageToEdit(drawImage);
			downSample();
			ImageNoLabel.setText((new StringBuilder("Image 1 of ")).append(currentImage).toString());
			repaint();
		}
	}

	private void exitForm(WindowEvent evt)
	{
		exitTester();
	}

	private void exitTester()
	{
		if (alone)
			System.exit(0);
		else
			dispose();
	}

	public void setup()
	{
		if (imageHolder != null)
		{
			drawImage.getGraphics().setColor(new Color(255, 255, 255));
			drawImage.getGraphics().fillRect(0, 0, drawImage.getWidth(), drawImage.getHeight());
			imageHolder.add(drawImage);
			((ImagePainter)PainterPanel).setImageToEdit(drawImage);
			if (downsamplePreviewImage != null)
			{
				downsamplePreviewImage.getGraphics().setColor(Color.WHITE);
				downsamplePreviewImage.getGraphics().fillRect(0, 0, downsamplePreviewImage.getWidth(this), downsamplePreviewImage.getHeight(this));
				((ImageDrawer)DownsamplePanel).setImageToDraw(downsamplePreviewImage);
			}
			idHolder.add(new Integer(1));
			currentImage = 1;
		}
		repaint();
	}

	public static void main(String args[])
	{
		(new SOMImageTester()).show();
	}

	public int getDrawSizeX()
	{
		return DrawSizeX;
	}

	public void setDrawSizeX(int DrawSizeX)
	{
		this.DrawSizeX = DrawSizeX;
	}

	public int getDrawSizeY()
	{
		return DrawSizeY;
	}

	public void setDrawSizeY(int DrawSizeY)
	{
		this.DrawSizeY = DrawSizeY;
	}

	public int getScaleSizeX()
	{
		return ScaleSizeX;
	}

	public void setScaleSizeX(int ScaleSizeX)
	{
		this.ScaleSizeX = ScaleSizeX;
	}

	public int getScaleSizeY()
	{
		return ScaleSizeY;
	}

	public void setScaleSizeY(int ScaleSizeY)
	{
		this.ScaleSizeY = ScaleSizeY;
	}

	public void clearCurrentImage()
	{
		drawImage.getGraphics().setColor(Color.WHITE);
		drawImage.getGraphics().fillRect(0, 0, drawImage.getWidth(), drawImage.getHeight());
		downSample();
		repaint();
	}

	public void NewImage()
	{
		if (imageHolder != null && idHolder != null)
		{
			downSample = new BufferedImage(getScaleSizeX(), getScaleSizeY(), 1);
			drawImage = new BufferedImage(getDrawSizeX(), getDrawSizeY(), 1);
			clearCurrentImage();
			Integer cur_id = (Integer)idHolder.get(currentImage - 1);
			currentImage++;
			imageHolder.add(drawImage);
			idHolder.add(cur_id);
			IDInputTextField.setText((new StringBuilder()).append(cur_id.intValue()).toString());
			ImageScrollBar.setMaximum(currentImage);
			ImageScrollBar.setValue(currentImage);
			((ImagePainter)PainterPanel).setImageToEdit(drawImage);
			((ImageDrawer)DownsamplePanel).setImageToDraw(downsamplePreviewImage);
			ImageNoLabel.setText((new StringBuilder("Image 1 of ")).append(currentImage).toString());
			repaint();
		}
	}

	protected boolean hLineClear(int y)
	{
		int w = drawImage.getWidth(this);
		for (int i = 0; i < w; i++)
			if (pixelMap[y * w + i] != -1)
				return false;

		return true;
	}

	protected boolean vLineClear(int x)
	{
		int w = drawImage.getWidth(this);
		int h = drawImage.getHeight(this);
		for (int i = 0; i < h; i++)
			if (pixelMap[i * w + x] != -1)
				return false;

		return true;
	}

	protected void findBounds(int w, int h)
	{
		for (int y = 0; y < h; y++)
		{
			if (hLineClear(y))
				continue;
			downSampleTop = y;
			break;
		}

		for (int y = h - 1; y >= 0; y--)
		{
			if (hLineClear(y))
				continue;
			downSampleBottom = y;
			break;
		}

		for (int x = 0; x < w; x++)
		{
			if (vLineClear(x))
				continue;
			downSampleLeft = x;
			break;
		}

		for (int x = w - 1; x >= 0; x--)
		{
			if (vLineClear(x))
				continue;
			downSampleRight = x;
			break;
		}

	}

	protected boolean downSampleQuadrant(int x, int y)
	{
		int w = drawImage.getWidth(this);
		int startX = (int)((double)downSampleLeft + (double)x * ratioX);
		int startY = (int)((double)downSampleTop + (double)y * ratioY);
		int endX = (int)((double)startX + ratioX);
		int endY = (int)((double)startY + ratioY);
		for (int yy = startY; yy <= endY; yy++)
		{
			for (int xx = startX; xx <= endX; xx++)
			{
				int loc = xx + yy * w;
				if (pixelMap[loc] != -1)
					return true;
			}

		}

		return false;
	}

	public void downSample()
	{
		int w = drawImage.getWidth(this);
		int h = drawImage.getHeight(this);
		PixelGrabber grabber = new PixelGrabber(drawImage, 0, 0, w, h, true);
		try
		{
			grabber.grabPixels();
			pixelMap = (int[])grabber.getPixels();
			findBounds(w, h);
			ratioX = (double)(downSampleRight - downSampleLeft) / (double)downSample.getWidth();
			ratioY = (double)(downSampleBottom - downSampleTop) / (double)downSample.getHeight();
			for (int y = 0; y < downSample.getHeight(); y++)
			{
				for (int x = 0; x < downSample.getWidth(); x++)
					if (downSampleQuadrant(x, y))
						downSample.setRGB(x, y, Color.BLACK.getRGB());
					else
						downSample.setRGB(x, y, Color.WHITE.getRGB());

			}

			downsamplePreviewImage = downSample.getScaledInstance(getDrawSizeX(), getDrawSizeY(), 1);
			if (downsamplePreviewImage != null)
				((ImageDrawer)DownsamplePanel).setImageToDraw(downsamplePreviewImage);
		}
		catch (InterruptedException interruptedexception) { }
	}

	public void SaveImagesOut()
	{
		FileOutputStream joone_file = null;
		DataOutputStream joone_out = null;
		JFileChooser choose = new JFileChooser();
		int result = choose.showSaveDialog(this);
		try
		{
			if (result == 0)
			{
				joone_file = new FileOutputStream(choose.getSelectedFile());
				joone_out = new DataOutputStream(joone_file);
				for (int i = 0; i < imageHolder.size(); i++)
				{
					drawImage = (BufferedImage)imageHolder.get(i);
					downSample();
					for (int y = 0; y < downSample.getHeight(); y++)
					{
						for (int x = 0; x < downSample.getWidth(); x++)
							if (downSample.getRGB(x, y) == Color.BLACK.getRGB())
								joone_out.writeBytes("1.0;");
							else
								joone_out.writeBytes("0.0;");

					}

					joone_out.writeBytes((new StringBuilder(String.valueOf(((Integer)idHolder.get(i)).intValue()))).append("\n").toString());
				}

			}
		}
		catch (IOException ex)
		{
			JOptionPane.showInternalMessageDialog(this, (new StringBuilder("An error occurred while trying to write to the file. Error is ")).append(ex.toString()).toString());
		}
	}








}
