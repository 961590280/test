// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CoordGenerator.java

package org.joone.samples.editor.som;

import java.awt.Graphics;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;
import javax.swing.*;

public class CoordGenerator extends JFrame
{

	private Vector coords;
	private boolean alone;
	String fileName;
	private JMenuItem aboutMenuItem;
	private JMenuItem exitMenuItem;
	private JMenu fileMenu;
	private JMenu helpMenu;
	private JSeparator jSeparator1;
	private JMenuBar menuBar;
	private JMenuItem newMenuItem;
	private JMenuItem saveMenuItem;

	public CoordGenerator()
	{
		this(false);
	}

	public CoordGenerator(boolean main)
	{
		fileName = "/tmp/coords.txt";
		alone = main;
		initComponents();
		coords = new Vector();
		addMouseListener(new MouseAdapter() {

			final CoordGenerator this$0;

			public void mousePressed(MouseEvent e)
			{
				System.out.println((new StringBuilder("x=")).append(e.getX()).append(" y=").append(e.getY()).toString());
				int cc[] = new int[2];
				cc[0] = e.getX();
				cc[1] = e.getY();
				coords.addElement(cc);
				repaint();
			}

			
			{
				this$0 = CoordGenerator.this;
				super();
			}
		});
		setSize(640, 480);
	}

	private void initComponents()
	{
		menuBar = new JMenuBar();
		fileMenu = new JMenu();
		newMenuItem = new JMenuItem();
		saveMenuItem = new JMenuItem();
		jSeparator1 = new JSeparator();
		exitMenuItem = new JMenuItem();
		helpMenu = new JMenu();
		aboutMenuItem = new JMenuItem();
		addWindowListener(new WindowAdapter() {

			final CoordGenerator this$0;

			public void windowClosing(WindowEvent evt)
			{
				exitForm(evt);
			}

			
			{
				this$0 = CoordGenerator.this;
				super();
			}
		});
		fileMenu.setText("File");
		fileMenu.addActionListener(new ActionListener() {

			final CoordGenerator this$0;

			public void actionPerformed(ActionEvent evt)
			{
				fileMenuActionPerformed(evt);
			}

			
			{
				this$0 = CoordGenerator.this;
				super();
			}
		});
		newMenuItem.setText("New");
		newMenuItem.addActionListener(new ActionListener() {

			final CoordGenerator this$0;

			public void actionPerformed(ActionEvent evt)
			{
				newMenuItemActionPerformed(evt);
			}

			
			{
				this$0 = CoordGenerator.this;
				super();
			}
		});
		fileMenu.add(newMenuItem);
		saveMenuItem.setText("Save");
		saveMenuItem.addActionListener(new ActionListener() {

			final CoordGenerator this$0;

			public void actionPerformed(ActionEvent evt)
			{
				saveMenuItemActionPerformed(evt);
			}

			
			{
				this$0 = CoordGenerator.this;
				super();
			}
		});
		fileMenu.add(saveMenuItem);
		fileMenu.add(jSeparator1);
		exitMenuItem.setText("Exit");
		exitMenuItem.addActionListener(new ActionListener() {

			final CoordGenerator this$0;

			public void actionPerformed(ActionEvent evt)
			{
				exitMenuItemActionPerformed(evt);
			}

			
			{
				this$0 = CoordGenerator.this;
				super();
			}
		});
		fileMenu.add(exitMenuItem);
		menuBar.add(fileMenu);
		helpMenu.setText("Help");
		aboutMenuItem.setText("About");
		aboutMenuItem.addActionListener(new ActionListener() {

			final CoordGenerator this$0;

			public void actionPerformed(ActionEvent evt)
			{
				aboutMenuItemActionPerformed(evt);
			}

			
			{
				this$0 = CoordGenerator.this;
				super();
			}
		});
		helpMenu.add(aboutMenuItem);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);
		pack();
	}

	private void aboutMenuItemActionPerformed(ActionEvent evt)
	{
		String help1 = new String("This application allows the user to plot a figure for recognition by a Joone neural network.");
		String help2 = new String("It is intended to test a SOM or Kohonen Network by providing an image recognition example.");
		String help3 = new String("Draw a whatever figure clicking on the drawing area, and then save the figure into a file.");
		String help4 = new String("The saved file must be used as input of the figureRecognition.ser neural network.");
		JOptionPane.showMessageDialog(this, (new StringBuilder(String.valueOf(help1))).append("\n").append(help2).append("\n").append(help3).append("\n").append(help4).toString());
	}

	private void saveMenuItemActionPerformed(ActionEvent evt)
	{
		try
		{
			FileWriter fw = new FileWriter(fileName);
			for (int x = 0; x < coords.size(); x++)
			{
				int cc[] = (int[])coords.elementAt(x);
				fw.write((new StringBuilder(String.valueOf(Integer.toString(cc[0])))).append(";").toString());
				fw.write((new StringBuilder(String.valueOf(Integer.toString(cc[1])))).append("\n").toString());
			}

			fw.close();
			System.out.println((new StringBuilder("Written ")).append(coords.size()).append(" coords").toString());
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}

	private void newMenuItemActionPerformed(ActionEvent evt)
	{
		coords = new Vector();
		repaint();
	}

	private void fileMenuActionPerformed(ActionEvent actionevent)
	{
	}

	private void exitMenuItemActionPerformed(ActionEvent evt)
	{
		exitTester();
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

	public static void main(String args[])
	{
		(new CoordGenerator(true)).show();
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		for (int x = 0; x < coords.size(); x++)
		{
			int cc[] = (int[])coords.elementAt(x);
			g.fillOval(cc[0], cc[1], 3, 3);
		}

	}







}
