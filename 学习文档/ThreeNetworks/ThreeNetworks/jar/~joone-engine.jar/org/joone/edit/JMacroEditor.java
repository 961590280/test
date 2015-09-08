// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JMacroEditor.java

package org.joone.edit;

import CH.ifa.draw.framework.HJDError;
import CH.ifa.draw.util.Iconkit;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.*;
import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import org.joone.edit.jedit.JEditTextArea;
import org.joone.edit.jedit.tokenmarker.JavaTokenMarker;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NeuralNet;
import org.joone.script.MacroInterface;
import org.joone.script.MacroManager;
import org.joone.util.*;

// Referenced classes of package org.joone.edit:
//			JooneFileChooser

public class JMacroEditor extends JFrame
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/edit/JMacroEditor);
	private JEditTextArea ta;
	private NeuralNet neuralNet;
	private char m_buf[];
	private String m_dir;
	private String FileExtension[] = {
		"bsh", "groovy"
	};
	private String FileDescription;
	private String fileName;
	private String actualMacro;
	private DefaultListModel list;
	private JMenuItem addMenuItem;
	private JRadioButtonMenuItem beanShellMenuItem;
	private ButtonGroup buttonGroup1;
	private JMenuItem copyMenuItem;
	private JMenuItem cutMenuItem;
	private JMenu editMenu;
	private JCheckBoxMenuItem enableMacroMenuItem;
	private JMenuItem exitMenuItem;
	private JMenu fileMenu;
	private JRadioButtonMenuItem groovyMenuItem;
	private JMenuItem importMenuItem;
	private JList jList1;
	private JMenu jMenuLanguage;
	private JSeparator jSeparator1;
	private JSeparator jSeparator2;
	private JSeparator jSeparator3;
	private JSeparator jSeparator4;
	private JSeparator jSeparator5;
	private JSplitPane jSplitPane1;
	private JMenu macroMenu;
	private JMenuBar menuBar;
	private JMenuItem pasteMenuItem;
	private JMenuItem removeMenuItem;
	private JMenuItem renameMenuItem;
	private JMenuItem runMenuItem;
	private JMenuItem saveAsMenuItem;
	private JMenuItem selectAllMenuItem;
	private JMenuItem setRateMenuItem;

	public JMacroEditor(NeuralNet nnet)
	{
		FileDescription = "BeanShell scripts (.bsh) | Groovy scripts (.groovy)";
		fileName = null;
		actualMacro = null;
		initComponents();
		setNeuralNet(nnet);
		ta = new JEditTextArea();
		ta.addFocusListener(new FocusAdapter() {

			final JMacroEditor this$0;

			public void focusLost(FocusEvent evt)
			{
				taFocusLost(evt);
			}

			
			{
				this$0 = JMacroEditor.this;
				super();
			}
		});
		ta.setTokenMarker(new JavaTokenMarker());
		jSplitPane1.setRightComponent(ta);
		Iconkit kit = Iconkit.instance();
		if (kit == null)
			throw new HJDError("Iconkit instance isn't set");
		Image img = kit.loadImageResource("/org/joone/images/JooneIcon.gif");
		setIconImage(img);
		fillList();
		enableMacroMenuItem.setSelected(getNeuralNet().isScriptingEnabled());
		if (getNeuralNet().getMacroPlugin() instanceof MacroPlugin)
			beanShellMenuItem.setSelected(true);
		else
			groovyMenuItem.setSelected(true);
		pack();
	}

	private void initComponents()
	{
		buttonGroup1 = new ButtonGroup();
		jSplitPane1 = new JSplitPane();
		jList1 = new JList();
		menuBar = new JMenuBar();
		fileMenu = new JMenu();
		importMenuItem = new JMenuItem();
		saveAsMenuItem = new JMenuItem();
		jSeparator1 = new JSeparator();
		exitMenuItem = new JMenuItem();
		editMenu = new JMenu();
		cutMenuItem = new JMenuItem();
		copyMenuItem = new JMenuItem();
		pasteMenuItem = new JMenuItem();
		jSeparator4 = new JSeparator();
		selectAllMenuItem = new JMenuItem();
		macroMenu = new JMenu();
		enableMacroMenuItem = new JCheckBoxMenuItem();
		jSeparator3 = new JSeparator();
		addMenuItem = new JMenuItem();
		removeMenuItem = new JMenuItem();
		renameMenuItem = new JMenuItem();
		jSeparator2 = new JSeparator();
		runMenuItem = new JMenuItem();
		setRateMenuItem = new JMenuItem();
		jSeparator5 = new JSeparator();
		jMenuLanguage = new JMenu();
		beanShellMenuItem = new JRadioButtonMenuItem();
		groovyMenuItem = new JRadioButtonMenuItem();
		setTitle("JavaScript Macro Editor");
		addWindowListener(new WindowAdapter() {

			final JMacroEditor this$0;

			public void windowClosing(WindowEvent evt)
			{
				exitForm(evt);
			}

			
			{
				this$0 = JMacroEditor.this;
				super();
			}
		});
		jList1.addListSelectionListener(new ListSelectionListener() {

			final JMacroEditor this$0;

			public void valueChanged(ListSelectionEvent evt)
			{
				jList1ValueChanged(evt);
			}

			
			{
				this$0 = JMacroEditor.this;
				super();
			}
		});
		jSplitPane1.setLeftComponent(jList1);
		getContentPane().add(jSplitPane1, "Center");
		fileMenu.setText("File");
		importMenuItem.setText("Import Macro...");
		importMenuItem.addActionListener(new ActionListener() {

			final JMacroEditor this$0;

			public void actionPerformed(ActionEvent evt)
			{
				importMenuItemActionPerformed(evt);
			}

			
			{
				this$0 = JMacroEditor.this;
				super();
			}
		});
		fileMenu.add(importMenuItem);
		saveAsMenuItem.setText("Save Macro As ...");
		saveAsMenuItem.addActionListener(new ActionListener() {

			final JMacroEditor this$0;

			public void actionPerformed(ActionEvent evt)
			{
				saveAsMenuItemActionPerformed(evt);
			}

			
			{
				this$0 = JMacroEditor.this;
				super();
			}
		});
		fileMenu.add(saveAsMenuItem);
		fileMenu.add(jSeparator1);
		exitMenuItem.setMnemonic('x');
		exitMenuItem.setText("Close");
		exitMenuItem.addActionListener(new ActionListener() {

			final JMacroEditor this$0;

			public void actionPerformed(ActionEvent evt)
			{
				exitMenuItemActionPerformed(evt);
			}

			
			{
				this$0 = JMacroEditor.this;
				super();
			}
		});
		fileMenu.add(exitMenuItem);
		menuBar.add(fileMenu);
		editMenu.setText("Edit");
		cutMenuItem.setText("Cut");
		cutMenuItem.addActionListener(new ActionListener() {

			final JMacroEditor this$0;

			public void actionPerformed(ActionEvent evt)
			{
				cutMenuItemActionPerformed(evt);
			}

			
			{
				this$0 = JMacroEditor.this;
				super();
			}
		});
		editMenu.add(cutMenuItem);
		copyMenuItem.setText("Copy");
		copyMenuItem.addActionListener(new ActionListener() {

			final JMacroEditor this$0;

			public void actionPerformed(ActionEvent evt)
			{
				copyMenuItemActionPerformed(evt);
			}

			
			{
				this$0 = JMacroEditor.this;
				super();
			}
		});
		editMenu.add(copyMenuItem);
		pasteMenuItem.setText("Paste");
		pasteMenuItem.addActionListener(new ActionListener() {

			final JMacroEditor this$0;

			public void actionPerformed(ActionEvent evt)
			{
				pasteMenuItemActionPerformed(evt);
			}

			
			{
				this$0 = JMacroEditor.this;
				super();
			}
		});
		editMenu.add(pasteMenuItem);
		editMenu.add(jSeparator4);
		selectAllMenuItem.setText("Select All");
		selectAllMenuItem.addActionListener(new ActionListener() {

			final JMacroEditor this$0;

			public void actionPerformed(ActionEvent evt)
			{
				selectAllMenuItemActionPerformed(evt);
			}

			
			{
				this$0 = JMacroEditor.this;
				super();
			}
		});
		editMenu.add(selectAllMenuItem);
		menuBar.add(editMenu);
		macroMenu.setText("Macro");
		macroMenu.addActionListener(new ActionListener() {

			final JMacroEditor this$0;

			public void actionPerformed(ActionEvent evt)
			{
				macroMenuActionPerformed(evt);
			}

			
			{
				this$0 = JMacroEditor.this;
				super();
			}
		});
		enableMacroMenuItem.setText("Enable Scripting");
		enableMacroMenuItem.addActionListener(new ActionListener() {

			final JMacroEditor this$0;

			public void actionPerformed(ActionEvent evt)
			{
				enableMacroMenuItemActionPerformed(evt);
			}

			
			{
				this$0 = JMacroEditor.this;
				super();
			}
		});
		macroMenu.add(enableMacroMenuItem);
		macroMenu.add(jSeparator3);
		addMenuItem.setText("Add...");
		addMenuItem.addActionListener(new ActionListener() {

			final JMacroEditor this$0;

			public void actionPerformed(ActionEvent evt)
			{
				addMenuItemActionPerformed(evt);
			}

			
			{
				this$0 = JMacroEditor.this;
				super();
			}
		});
		macroMenu.add(addMenuItem);
		removeMenuItem.setText("Remove");
		removeMenuItem.addActionListener(new ActionListener() {

			final JMacroEditor this$0;

			public void actionPerformed(ActionEvent evt)
			{
				removeMenuItemActionPerformed(evt);
			}

			
			{
				this$0 = JMacroEditor.this;
				super();
			}
		});
		macroMenu.add(removeMenuItem);
		renameMenuItem.setText("Rename");
		renameMenuItem.addActionListener(new ActionListener() {

			final JMacroEditor this$0;

			public void actionPerformed(ActionEvent evt)
			{
				renameMenuItemActionPerformed(evt);
			}

			
			{
				this$0 = JMacroEditor.this;
				super();
			}
		});
		macroMenu.add(renameMenuItem);
		macroMenu.add(jSeparator2);
		runMenuItem.setText("Run");
		runMenuItem.addActionListener(new ActionListener() {

			final JMacroEditor this$0;

			public void actionPerformed(ActionEvent evt)
			{
				runMenuItemActionPerformed(evt);
			}

			
			{
				this$0 = JMacroEditor.this;
				super();
			}
		});
		macroMenu.add(runMenuItem);
		setRateMenuItem.setText("set Rate...");
		setRateMenuItem.addActionListener(new ActionListener() {

			final JMacroEditor this$0;

			public void actionPerformed(ActionEvent evt)
			{
				setRateMenuItemActionPerformed(evt);
			}

			
			{
				this$0 = JMacroEditor.this;
				super();
			}
		});
		macroMenu.add(setRateMenuItem);
		macroMenu.add(jSeparator5);
		jMenuLanguage.setText("Language");
		buttonGroup1.add(beanShellMenuItem);
		beanShellMenuItem.setSelected(true);
		beanShellMenuItem.setText("BeanShell");
		beanShellMenuItem.addActionListener(new ActionListener() {

			final JMacroEditor this$0;

			public void actionPerformed(ActionEvent evt)
			{
				beanShellMenuItemActionPerformed(evt);
			}

			
			{
				this$0 = JMacroEditor.this;
				super();
			}
		});
		jMenuLanguage.add(beanShellMenuItem);
		buttonGroup1.add(groovyMenuItem);
		groovyMenuItem.setText("Groovy");
		groovyMenuItem.addActionListener(new ActionListener() {

			final JMacroEditor this$0;

			public void actionPerformed(ActionEvent evt)
			{
				groovyMenuItemActionPerformed(evt);
			}

			
			{
				this$0 = JMacroEditor.this;
				super();
			}
		});
		jMenuLanguage.add(groovyMenuItem);
		macroMenu.add(jMenuLanguage);
		menuBar.add(macroMenu);
		setJMenuBar(menuBar);
		pack();
	}

	private void groovyMenuItemActionPerformed(ActionEvent evt)
	{
		GroovyMacroPlugin newMacroPlugin = new GroovyMacroPlugin();
		setNewMacroPlugin(newMacroPlugin);
	}

	private void beanShellMenuItemActionPerformed(ActionEvent evt)
	{
		MacroPlugin newMacroPlugin = new MacroPlugin();
		setNewMacroPlugin(newMacroPlugin);
	}

	private void setNewMacroPlugin(MacroInterface newMacroPlugin)
	{
		MacroInterface oldMacroPlugin = getNeuralNet().getMacroPlugin();
		newMacroPlugin.setMacroManager(oldMacroPlugin.getMacroManager());
		getNeuralNet().setMacroPlugin(newMacroPlugin);
	}

	private void taFocusLost(FocusEvent evt)
	{
		saveMacro();
	}

	private void setRateMenuItemActionPerformed(ActionEvent evt)
	{
		int oldRate = ((MonitorPlugin)getNeuralNet().getMacroPlugin()).getRate();
		String s_newRate = showInputDialog("Set Rate", "Insert the new rate's value:", Integer.toString(oldRate));
		if (s_newRate != null)
		{
			int newRate;
			try
			{
				newRate = Integer.parseInt(s_newRate);
			}
			catch (NumberFormatException nfe)
			{
				newRate = oldRate;
			}
			if (newRate < 0)
				newRate *= -1;
			((MonitorPlugin)getNeuralNet().getMacroPlugin()).setRate(newRate);
		}
	}

	private void renameMenuItemActionPerformed(ActionEvent evt)
	{
		String newName = getNewName("Rename a macro", "Insert the new name of the Macro:", actualMacro);
		if (newName != null)
		{
			getNeuralNet().getMacroPlugin().getMacroManager().renameMacro(actualMacro, newName);
			actualMacro = newName;
			list.setElementAt(newName, jList1.getSelectedIndex());
		}
	}

	private void beanShellRadioButtonMenuItemActionPerformed(ActionEvent actionevent)
	{
	}

	private void groovyRadioButtonMenuItemActionPerformed(ActionEvent actionevent)
	{
	}

	private void removeMenuItemActionPerformed(ActionEvent evt)
	{
		int n = JOptionPane.showConfirmDialog(this, (new StringBuilder("OK to remove '")).append(actualMacro).append("' ?").toString(), "Macro Editor", 0);
		if (n == 0)
		{
			getNeuralNet().getMacroPlugin().getMacroManager().removeMacro(actualMacro);
			String removed = actualMacro;
			actualMacro = null;
			list.removeElement(removed);
		}
	}

	private void addMenuItemActionPerformed(ActionEvent evt)
	{
		String newName = getNewName("Add a macro", "Insert the name of the new Macro:", "newMacro");
		if (newName != null)
		{
			getNeuralNet().getMacroPlugin().getMacroManager().addMacro(newName, "");
			list.addElement(newName);
			jList1.setSelectedIndex(list.size() - 1);
		}
	}

	private String getNewName(String title, String message, String initialValue)
	{
		boolean ok = false;
		String errorMsg = "ERROR: The name already exists.\n";
		String newName = showInputDialog(title, message, initialValue);
		if (newName != null)
			while (!ok) 
				if (getNeuralNet().getMacroPlugin().getMacroManager().getMacro(newName) != null)
				{
					newName = showInputDialog(title, (new StringBuilder(String.valueOf(errorMsg))).append(message).toString(), newName);
					if (newName == null)
						ok = true;
				} else
				{
					ok = true;
				}
		return newName;
	}

	private void jList1ValueChanged(ListSelectionEvent evt)
	{
		saveMacro();
		int index = jList1.getSelectedIndex();
		if (index > -1)
		{
			String name = (String)jList1.getModel().getElementAt(index);
			String macroText = getNeuralNet().getMacroPlugin().getMacroManager().getMacro(name);
			if (macroText.trim().equals(""))
				macroText = (new StringBuilder("/* ")).append(name).append(" */\n").toString();
			ta.setText(macroText);
			actualMacro = name;
			enableMenuItems(actualMacro);
		} else
		{
			jList1.setSelectedIndex(0);
		}
	}

	private void enableMenuItems(String name)
	{
		boolean system = getNeuralNet().getMacroPlugin().getMacroManager().isEventMacro(name);
		if (system)
		{
			removeMenuItem.setEnabled(false);
			renameMenuItem.setEnabled(false);
		} else
		{
			removeMenuItem.setEnabled(true);
			renameMenuItem.setEnabled(true);
		}
	}

	private void enableMacroMenuItemActionPerformed(ActionEvent evt)
	{
		neuralNet.setScriptingEnabled(enableMacroMenuItem.isSelected());
	}

	private void selectAllMenuItemActionPerformed(ActionEvent evt)
	{
		ta.selectAll();
	}

	private void saveAsMenuItemActionPerformed(ActionEvent evt)
	{
		saveAs();
	}

	private void importMenuItemActionPerformed(ActionEvent evt)
	{
		JFileChooser jfc = new JooneFileChooser();
		jfc.setDialogTitle("Macro Editor - Import File...");
		FileFilter bsh = new FileFilter() {

			final JMacroEditor this$0;

			public boolean accept(File checkFile)
			{
				if (checkFile.isDirectory())
					return true;
				boolean isFileExtensionMatch = false;
				String extension[] = getFileExtension();
				for (int i = 0; i < extension.length; i++)
				{
					if (!checkFile.getName().endsWith((new StringBuilder(".")).append(extension[i]).toString()))
						continue;
					isFileExtensionMatch = true;
					break;
				}

				return isFileExtensionMatch;
			}

			public String getDescription()
			{
				return getFileDescription();
			}

			
			{
				this$0 = JMacroEditor.this;
				super();
			}
		};
		jfc.addChoosableFileFilter(bsh);
		if (jfc.showOpenDialog(this) == 0)
		{
			setFileName(jfc.getSelectedFile().getAbsolutePath());
			File ff = new File(fileName);
			String txt = readFile(ff);
			if (txt != null)
				ta.setText(txt);
		}
	}

	private void pasteMenuItemActionPerformed(ActionEvent evt)
	{
		ta.paste();
	}

	private void copyMenuItemActionPerformed(ActionEvent evt)
	{
		ta.copy();
	}

	private void cutMenuItemActionPerformed(ActionEvent evt)
	{
		ta.cut();
	}

	private void runMenuItemActionPerformed(ActionEvent evt)
	{
		getNeuralNet().getMacroPlugin().runMacro(ta.getText());
	}

	private void macroMenuActionPerformed(ActionEvent actionevent)
	{
	}

	private void exitMenuItemActionPerformed(ActionEvent evt)
	{
		saveMacro();
		setVisible(false);
	}

	private void exitForm(WindowEvent evt)
	{
		saveMacro();
	}

	public static void main(String args[])
	{
		NeuralNet nn = new NeuralNet();
		nn.setMacroPlugin(new MacroPlugin());
		JMacroEditor jte = new JMacroEditor(nn);
		jte.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent evt)
			{
				System.exit(0);
			}

		});
		jte.show();
	}

	public void setText(String newText)
	{
		ta.setText(newText);
	}

	public String getText()
	{
		return ta.getText();
	}

	public NeuralNet getNeuralNet()
	{
		return neuralNet;
	}

	public void setNeuralNet(NeuralNet neuralNet)
	{
		this.neuralNet = neuralNet;
	}

	private String readFile(File p_file)
	{
		String str = null;
		FileReader reader = null;
		try
		{
			reader = new FileReader(p_file);
			int tch = (new Long(p_file.length())).intValue();
			m_buf = new char[tch];
			int nch = reader.read(m_buf);
			if (nch != -1)
				str = new String(m_buf, 0, nch);
			reader.close();
		}
		catch (FileNotFoundException fnfe)
		{
			log.warn((new StringBuilder("File '")).append(p_file).append("' not found. MEssage is : ").append(fnfe.getMessage()).toString(), fnfe);
			return str;
		}
		catch (IOException ioe)
		{
			log.error((new StringBuilder("File '")).append(m_dir).append("' input/output error. Message is : ").append(ioe.getMessage()).toString(), ioe);
		}
		return str;
	}

	public String getFileDescription()
	{
		return FileDescription;
	}

	public void setFileDescription(String FileDescription)
	{
		this.FileDescription = FileDescription;
	}

	public String[] getFileExtension()
	{
		return (String[])FileExtension.clone();
	}

	public void setFileExtension(String FileExtension[])
	{
		this.FileExtension = (String[])FileExtension.clone();
	}

	private void saveAs()
	{
		JFileChooser jfc;
		if (fileName == null)
			jfc = new JFileChooser();
		else
			jfc = new JFileChooser(new File(fileName));
		jfc.setDialogTitle("Macro Editor - Save as...");
		if (jfc.showSaveDialog(this) == 0)
		{
			setFileName(jfc.getSelectedFile().getAbsolutePath());
			save();
		}
	}

	private void save()
	{
		try
		{
			FileWriter fw = new FileWriter(new File(fileName));
			fw.write(ta.getText());
			fw.flush();
			fw.close();
		}
		catch (IOException ioe)
		{
			log.warn((new StringBuilder("IOException thrown. Message is +")).append(ioe.getMessage()).toString(), ioe);
		}
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
		if (fileName != null)
			setTitle(fileName);
		else
			setTitle("New File");
	}

	private void fillList()
	{
		Hashtable macros = getNeuralNet().getMacroPlugin().getMacroManager().getMacros();
		list = new DefaultListModel();
		String key;
		for (Enumeration keys = macros.keys(); keys.hasMoreElements(); list.addElement(key))
			key = (String)keys.nextElement();

		jList1.setModel(list);
		jList1.setSelectedIndex(0);
	}

	private String showInputDialog(String title, String message, String initialValue)
	{
		JOptionPane pane = new JOptionPane(message, 3);
		pane.setWantsInput(true);
		pane.setInitialSelectionValue(initialValue);
		pane.setOptionType(2);
		JDialog dialog = pane.createDialog(this, title);
		dialog.show();
		int selectedButton = ((Integer)pane.getValue()).intValue();
		if (selectedButton == 0)
		{
			Object selectedValue = pane.getInputValue();
			if (selectedValue != null && (selectedValue instanceof String))
				return (String)selectedValue;
		}
		return null;
	}

	private void saveMacro()
	{
		if (actualMacro != null)
		{
			String oldText = getNeuralNet().getMacroPlugin().getMacroManager().getMacro(actualMacro);
			if (!oldText.equals(ta.getText()))
				getNeuralNet().getMacroPlugin().getMacroManager().addMacro(actualMacro, ta.getText());
		}
	}




















}
