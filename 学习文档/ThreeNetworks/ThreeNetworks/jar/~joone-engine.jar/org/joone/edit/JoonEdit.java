// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JoonEdit.java

package org.joone.edit;

import CH.ifa.draw.application.DrawApplication;
import CH.ifa.draw.contrib.*;
import CH.ifa.draw.figures.*;
import CH.ifa.draw.framework.*;
import CH.ifa.draw.standard.*;
import CH.ifa.draw.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterJob;
import java.io.*;
import java.net.*;
import java.util.Calendar;
import java.util.Vector;
import javax.help.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import org.joone.engine.Monitor;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NeuralNet;
import org.joone.samples.editor.som.SOMImageTester;
import org.joone.util.MacroPlugin;

// Referenced classes of package org.joone.edit:
//			ToolsSAXParser, ToolElement, LayerFigureCreationTool, SynapseCreationTool, 
//			DelegationSelectionTool, PropertySheet, TodoFrame, Wrapper, 
//			MonitorPropertySheet, JooneCommandMenu, AboutFrame, NetStorageFormatManager, 
//			NetStorageFormat, NeuralNetDrawing, JooneFileChooser, StandardNetStorageFormat, 
//			XMLNetStorageFormat, JooneStandardDrawingView, LayerFigure, JMacroEditor, 
//			EditorParameters, JooneCutCommand, JooneCopyCommand, JooneDuplicateCommand, 
//			IniFile

public class JoonEdit extends DrawApplication
{
	private class httpAuthenticateProxy extends Authenticator
	{

		private String userid;
		private String passw;
		final JoonEdit this$0;

		protected PasswordAuthentication getPasswordAuthentication()
		{
			return new PasswordAuthentication(userid, passw.toCharArray());
		}

		public httpAuthenticateProxy(String userid, String passw)
		{
			this$0 = JoonEdit.this;
			super();
			this.userid = userid;
			this.passw = passw;
		}
	}


	private static final ILogger log = LoggerFactory.getLogger(org/joone/edit/JoonEdit);
	private static final int MAJOR_RELEASE = 2;
	private static final int MINOR_RELEASE = 0;
	private static final int BUILD = 0;
	private static final String SUFFIX = "RC1";
	public static final String INI_FILE_NAME = "joonedit.config";
	private static final int RECO_ENG_MAJOR_RELEASE = 1;
	private static final int RECO_ENG_MINOR_RELEASE = 2;
	private static final int RECO_ENG_BUILD = 5;
	public static final String DIAGRAM_IMAGES = "/org/joone/images/";
	public static final String MENU_IMAGES = "/org/joone/images/menu/";
	private PropertySheet psp;
	private InputStream XMLParamsFile;
	private JooneFileChooser m_openDialog;
	private NetStorageFormatManager fNetStorageFormatManager;
	private NetStorageFormatManager xNetStorageFormatManager;
	private ToolsSAXParser tParser;
	private EditorParameters parameters;
	private MonitorPropertySheet ps;
	private static final long serialVersionUID = 0xc0738c556c3cd87fL;
	private AboutFrame af;
	private TodoFrame tf;
	private StorageFormat latestStorageFormat;
	private JMacroEditor macroEditor;
	private IniFile iniFile;
	private int width;
	private int height;

	public JoonEdit(String params)
	{
		super("JoonEdit - Joone Neural Net Editor");
		m_openDialog = null;
		af = null;
		tf = null;
		iniFile = null;
		width = 800;
		height = 600;
		try
		{
			initJoonEdit(new FileInputStream(params));
		}
		catch (FileNotFoundException fnfe)
		{
			log.fatal((new StringBuilder("FileNotFoundException thrown with params ")).append(params).append(". Message is ").append(fnfe.getMessage()).toString(), fnfe);
			System.exit(0);
		}
	}

	public JoonEdit()
	{
		super("JoonEdit - Joone Neural Net Editor");
		m_openDialog = null;
		af = null;
		tf = null;
		iniFile = null;
		width = 800;
		height = 600;
		InputStream is = getClass().getResourceAsStream("/org/joone/data/layers.xml");
		initJoonEdit(is);
	}

	private void initJoonEdit(InputStream isParams)
	{
		XMLParamsFile = isParams;
		fNetStorageFormatManager = createNetStorageFormatManager();
		xNetStorageFormatManager = createXMLStorageFormatManager();
		setDefaultCloseOperation(0);
		readIniFile();
	}

	public static void center(Window frame)
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		frame.setLocation(screenSize.width / 2 - frameSize.width / 2, screenSize.height / 2 - frameSize.height / 2);
	}

	protected JComponent createContents(StandardDrawingView view)
	{
		JPanel jpan = new JPanel();
		jpan.setLayout(new BorderLayout());
		JScrollPane rsp = (JScrollPane)super.createContents(view);
		JPanel ptools = new JPanel(new BorderLayout());
		JToolBar tools[] = new JToolBar[2];
		for (int i = 0; i < tools.length; i++)
			tools[i] = createToolPalette();

		createMyTools(tools);
		ptools.add(tools[0], "North");
		ptools.add(tools[1], "South");
		jpan.add(ptools, "North");
		jpan.add(rsp, "Center");
		return jpan;
	}

	protected void createTools(JToolBar palette)
	{
		super.createTools(palette);
		palette.addSeparator();
		Tool tool = new TextTool(view(), new TextFigure());
		palette.add(createToolButton("/CH/ifa/draw/images/TEXT", "Label", tool));
		tool = new ConnectedTextTool(view(), new TextFigure());
		palette.add(createToolButton("/CH/ifa/draw/images/ATEXT", "Connected Text Tool", tool));
		palette.addSeparator();
		tool = new CreationTool(view(), new RectangleFigure());
		palette.add(createToolButton("/CH/ifa/draw/images/RECT", "Rectangle Tool", tool));
		tool = new CreationTool(view(), new RoundRectangleFigure());
		palette.add(createToolButton("/CH/ifa/draw/images/RRECT", "Round Rectangle Tool", tool));
		tool = new CreationTool(view(), new EllipseFigure());
		palette.add(createToolButton("/CH/ifa/draw/images/ELLIPSE", "Ellipse Tool", tool));
		tool = new CreationTool(view(), new TriangleFigure());
		palette.add(createToolButton("/CH/ifa/draw/images/TRIANGLE", "Triangle Tool", tool));
		tool = new CreationTool(view(), new DiamondFigure());
		palette.add(createToolButton("/CH/ifa/draw/images/DIAMOND", "Diamond Tool", tool));
		tool = new PolygonTool(view());
		palette.add(createToolButton("/CH/ifa/draw/images/POLYGON", "Polygon Tool", tool));
		tool = new CreationTool(view(), new LineFigure());
		palette.add(createToolButton("/CH/ifa/draw/images/LINE", "Line Tool", tool));
		tool = new BorderTool(view());
		palette.add(createToolButton("/CH/ifa/draw/images/BORDDEC", "Border Tool", tool));
		tool = new ScribbleTool(view());
		palette.add(createToolButton("/CH/ifa/draw/images/SCRIBBL", "Scribble Tool", tool));
	}

	protected void createMyTools(JToolBar palettes[])
	{
		tParser = new ToolsSAXParser(XMLParamsFile);
		Vector elements = tParser.getElements();
		JToolBar palette = palettes[0];
		for (int i = 0; i < elements.size(); i++)
		{
			ToolElement te = (ToolElement)elements.elementAt(i);
			if (te.getType().compareToIgnoreCase("break") == 0)
				palette = palettes[1];
			if (te.getType().compareToIgnoreCase("separator") == 0)
				palette.addSeparator();
			if (te.getType().compareToIgnoreCase("layer") == 0)
			{
				LayerFigureCreationTool LFCtool = new LayerFigureCreationTool(view(), "org.joone.edit.LayerFigure");
				LFCtool.setParams(te.getParams());
				palette.add(createToolButton((new StringBuilder("/org/joone/images/")).append((String)LFCtool.getParam("image")).toString(), (new StringBuilder("New ")).append((String)LFCtool.getParam("type")).append(" layer").toString(), LFCtool));
			}
			if (te.getType().compareToIgnoreCase("input_layer") == 0)
			{
				LayerFigureCreationTool LFCtool = new LayerFigureCreationTool(view(), "org.joone.edit.InputLayerFigure");
				LFCtool.setParams(te.getParams());
				palette.add(createToolButton((new StringBuilder("/org/joone/images/")).append((String)LFCtool.getParam("image")).toString(), (new StringBuilder("New ")).append((String)LFCtool.getParam("type")).append(" layer").toString(), LFCtool));
			}
			if (te.getType().compareToIgnoreCase("output_layer") == 0)
			{
				LayerFigureCreationTool LFCtool = new LayerFigureCreationTool(view(), "org.joone.edit.OutputLayerFigure");
				LFCtool.setParams(te.getParams());
				palette.add(createToolButton((new StringBuilder("/org/joone/images/")).append((String)LFCtool.getParam("image")).toString(), (new StringBuilder("New ")).append((String)LFCtool.getParam("type")).append(" layer").toString(), LFCtool));
			}
			if (te.getType().compareToIgnoreCase("teacher_layer") == 0)
			{
				LayerFigureCreationTool LFCtool = new LayerFigureCreationTool(view(), "org.joone.edit.TeacherLayerFigure");
				LFCtool.setParams(te.getParams());
				palette.add(createToolButton((new StringBuilder("/org/joone/images/")).append((String)LFCtool.getParam("image")).toString(), (new StringBuilder("New ")).append((String)LFCtool.getParam("type")).append(" layer").toString(), LFCtool));
			}
			if (te.getType().compareToIgnoreCase("input_plugin") == 0)
			{
				LayerFigureCreationTool LFCtool = new LayerFigureCreationTool(view(), "org.joone.edit.InputPluginLayerFigure");
				LFCtool.setParams(te.getParams());
				palette.add(createToolButton((new StringBuilder("/org/joone/images/")).append((String)LFCtool.getParam("image")).toString(), (new StringBuilder("New ")).append((String)LFCtool.getParam("type")).toString(), LFCtool));
			}
			if (te.getType().compareToIgnoreCase("monitor_plugin") == 0)
			{
				LayerFigureCreationTool LFCtool = new LayerFigureCreationTool(view(), "org.joone.edit.MonitorPluginFigure");
				LFCtool.setParams(te.getParams());
				palette.add(createToolButton((new StringBuilder("/org/joone/images/")).append((String)LFCtool.getParam("image")).toString(), (new StringBuilder("New ")).append((String)LFCtool.getParam("type")).toString(), LFCtool));
			}
			if (te.getType().compareToIgnoreCase("input_switch") == 0)
			{
				LayerFigureCreationTool LFCtool = new LayerFigureCreationTool(view(), "org.joone.edit.InputSwitchLayerFigure");
				LFCtool.setParams(te.getParams());
				palette.add(createToolButton((new StringBuilder("/org/joone/images/")).append((String)LFCtool.getParam("image")).toString(), (new StringBuilder("New ")).append((String)LFCtool.getParam("type")).toString(), LFCtool));
			}
			if (te.getType().compareToIgnoreCase("input_connector") == 0)
			{
				LayerFigureCreationTool LFCtool = new LayerFigureCreationTool(view(), "org.joone.edit.InputConnectorLayerFigure");
				LFCtool.setParams(te.getParams());
				palette.add(createToolButton((new StringBuilder("/org/joone/images/")).append((String)LFCtool.getParam("image")).toString(), (new StringBuilder("New ")).append((String)LFCtool.getParam("type")).toString(), LFCtool));
			}
			if (te.getType().compareToIgnoreCase("output_switch") == 0)
			{
				LayerFigureCreationTool LFCtool = new LayerFigureCreationTool(view(), "org.joone.edit.OutputSwitchLayerFigure");
				LFCtool.setParams(te.getParams());
				palette.add(createToolButton((new StringBuilder("/org/joone/images/")).append((String)LFCtool.getParam("image")).toString(), (new StringBuilder("New ")).append((String)LFCtool.getParam("type")).toString(), LFCtool));
			}
			if (te.getType().compareToIgnoreCase("script_plugin") == 0)
			{
				LayerFigureCreationTool LFCtool = new LayerFigureCreationTool(view(), "org.joone.edit.ScriptPluginFigure");
				LFCtool.setParams(te.getParams());
				palette.add(createToolButton((new StringBuilder("/org/joone/images/")).append((String)LFCtool.getParam("image")).toString(), (new StringBuilder("New ")).append((String)LFCtool.getParam("type")).toString(), LFCtool));
			}
			if (te.getType().compareToIgnoreCase("learning_switch") == 0)
			{
				LayerFigureCreationTool LFCtool = new LayerFigureCreationTool(view(), "org.joone.edit.LearningSwitchLayerFigure");
				LFCtool.setParams(te.getParams());
				palette.add(createToolButton((new StringBuilder("/org/joone/images/")).append((String)LFCtool.getParam("image")).toString(), (new StringBuilder("New ")).append((String)LFCtool.getParam("type")).toString(), LFCtool));
			}
			if (te.getType().compareToIgnoreCase("synapse") == 0)
			{
				SynapseCreationTool SCtool = new SynapseCreationTool(view(), "org.joone.edit.LayerConnection");
				SCtool.setParams(te.getParams());
				palette.add(createToolButton((new StringBuilder("/org/joone/images/")).append((String)SCtool.getParam("image")).toString(), (new StringBuilder("New ")).append((String)SCtool.getParam("type")).toString(), SCtool));
			}
			if (te.getType().compareToIgnoreCase("output_plugin") == 0)
			{
				LayerFigureCreationTool LFCtool = new LayerFigureCreationTool(view(), "org.joone.edit.OutputPluginLayerFigure");
				LFCtool.setParams(te.getParams());
				palette.add(createToolButton((new StringBuilder("/org/joone/images/")).append((String)LFCtool.getParam("image")).toString(), (new StringBuilder("New ")).append((String)LFCtool.getParam("type")).toString(), LFCtool));
			}
			if (te.getType().compareToIgnoreCase("chart_handle") == 0)
			{
				LayerFigureCreationTool LFCtool = new LayerFigureCreationTool(view(), "org.joone.edit.ChartHandleLayerFigure");
				LFCtool.setParams(te.getParams());
				palette.add(createToolButton((new StringBuilder("/org/joone/images/")).append((String)LFCtool.getParam("image")).toString(), (new StringBuilder("New ")).append((String)LFCtool.getParam("type")).toString(), LFCtool));
			}
		}

	}

	protected Tool createSelectionTool()
	{
		DelegationSelectionTool dst = new DelegationSelectionTool(view());
		psp = new PropertySheet(500, 100);
		dst.setPropertyPanel(psp);
		return dst;
	}

	protected void createMenus(JMenuBar mb)
	{
		mb.add(createFileMenu());
		mb.add(createEditMenu());
		mb.add(createAlignmentMenu());
		mb.add(createAttributesMenu());
		mb.add(createToolsMenu());
		mb.add(createWindowMenu());
		mb.add(createHelpMenu());
	}

	protected Dimension defaultSize()
	{
		return new Dimension(width, height);
	}

	protected JMenu createToolsMenu()
	{
		tf = new TodoFrame(this);
		JMenu menu = new JMenu("Tools");
		menu.setMnemonic(79);
		JMenuItem mi = new JMenuItem("Control Panel");
		mi.setMnemonic(67);
		mi.addActionListener(new ActionListener() {

			final JoonEdit this$0;

			public void actionPerformed(ActionEvent event)
			{
				NeuralNetDrawing nnd = (NeuralNetDrawing)drawing();
				NeuralNet nn = nnd.getNeuralNet();
				ps = getMonitorPropertySheet(nn);
				ps.setParameters(parameters);
				ps.update();
				ps.setSize(330, 350);
				ps.setVisible(true);
				((JooneStandardDrawingView)view()).setModified(true);
			}

			
			{
				this$0 = JoonEdit.this;
				super();
			}
		});
		menu.add(mi);
		mi = new JMenuItem("To Do List");
		mi.setMnemonic(84);
		mi.addActionListener(new ActionListener() {

			final JoonEdit this$0;

			public void actionPerformed(ActionEvent event)
			{
				tf.show();
			}

			
			{
				this$0 = JoonEdit.this;
				super();
			}
		});
		menu.add(mi);
		menu.addSeparator();
		mi = new JMenuItem("Add Noise");
		mi.setMnemonic(65);
		mi.addActionListener(new ActionListener() {

			final JoonEdit this$0;

			public void actionPerformed(ActionEvent event)
			{
				NeuralNetDrawing nnd = (NeuralNetDrawing)drawing();
				NeuralNet nn = nnd.getNeuralNet();
				nn.addNoise(0.20000000000000001D);
				((JooneStandardDrawingView)view()).setModified(true);
			}

			
			{
				this$0 = JoonEdit.this;
				super();
			}
		});
		menu.add(mi);
		mi = new JMenuItem("Randomize");
		mi.setMnemonic(82);
		mi.addActionListener(new ActionListener() {

			final JoonEdit this$0;

			public void actionPerformed(ActionEvent event)
			{
				NeuralNetDrawing nnd = (NeuralNetDrawing)drawing();
				NeuralNet nn = nnd.getNeuralNet();
				nn.randomize(0.29999999999999999D);
				((JooneStandardDrawingView)view()).setModified(true);
			}

			
			{
				this$0 = JoonEdit.this;
				super();
			}
		});
		menu.add(mi);
		menu.addSeparator();
		mi = new JMenuItem("Reset Input Streams");
		mi.setMnemonic(73);
		mi.addActionListener(new ActionListener() {

			final JoonEdit this$0;

			public void actionPerformed(ActionEvent event)
			{
				NeuralNetDrawing nnd = (NeuralNetDrawing)drawing();
				NeuralNet nn = nnd.getNeuralNet();
				nn.resetInput();
				((JooneStandardDrawingView)view()).setModified(true);
			}

			
			{
				this$0 = JoonEdit.this;
				super();
			}
		});
		menu.add(mi);
		menu.addSeparator();
		mi = new JMenuItem("Macro Editor");
		mi.setMnemonic(77);
		mi.addActionListener(new ActionListener() {

			final JoonEdit this$0;

			public void actionPerformed(ActionEvent event)
			{
				NeuralNetDrawing nnd = (NeuralNetDrawing)drawing();
				NeuralNet nn = nnd.getNeuralNet();
				if (macroEditor == null)
				{
					if (nn.getMacroPlugin() == null)
						nn.setMacroPlugin(new MacroPlugin());
					macroEditor = new JMacroEditor(nn);
				}
				macroEditor.setVisible(true);
				((JooneStandardDrawingView)view()).setModified(true);
			}

			
			{
				this$0 = JoonEdit.this;
				super();
			}
		});
		menu.add(mi);
		return menu;
	}

	protected MonitorPropertySheet getMonitorPropertySheet(NeuralNet nn)
	{
		if (ps == null)
		{
			Wrapper wr = new Wrapper(null, nn.getMonitor(), "Control Panel");
			ps = new MonitorPropertySheet(wr, nn);
		}
		return ps;
	}

	protected JMenu createAlignmentMenu()
	{
		JooneCommandMenu menu = new JooneCommandMenu("Align");
		menu.setMnemonic(65);
		menu.add(new ToggleGridCommand("Toggle Snap to Grid", view(), new Point(4, 4)), new MenuShortcut(116));
		menu.addSeparator();
		menu.add(new AlignCommand("Left", view(), 0), new MenuShortcut(108));
		menu.add(new AlignCommand("Center", view(), 1), new MenuShortcut(99));
		menu.add(new AlignCommand("Right", view(), 2), new MenuShortcut(114));
		menu.addSeparator();
		menu.add(new AlignCommand("Top", view(), 3), new MenuShortcut(111));
		menu.add(new AlignCommand("Middle", view(), 4), new MenuShortcut(109));
		menu.add(new AlignCommand("Bottom", view(), 5), new MenuShortcut(98));
		return menu;
	}

	protected JMenu createFileMenu()
	{
		JMenu menu = new JMenu("File");
		menu.setMnemonic(70);
		JMenuItem mi = new JMenuItem("New", (new MenuShortcut(110)).getKey());
		mi.setAccelerator(KeyStroke.getKeyStroke(78, 2));
		mi.addActionListener(new ActionListener() {

			final JoonEdit this$0;

			public void actionPerformed(ActionEvent event)
			{
				int n = askForSave("Save changes to Neural Net?");
				if (n == 1 || !((JooneStandardDrawingView)view()).isModified())
				{
					promptNew();
					return;
				} else
				{
					return;
				}
			}

			
			{
				this$0 = JoonEdit.this;
				super();
			}
		});
		menu.add(mi);
		mi = new JMenuItem("Open...", (new MenuShortcut(111)).getKey());
		mi.setAccelerator(KeyStroke.getKeyStroke(79, 2));
		mi.addActionListener(new ActionListener() {

			final JoonEdit this$0;

			public void actionPerformed(ActionEvent event)
			{
				int n = askForSave("Save changes to Neural Net?");
				if (n == 1 || !((JooneStandardDrawingView)view()).isModified())
					promptOpen();
			}

			
			{
				this$0 = JoonEdit.this;
				super();
			}
		});
		menu.add(mi);
		mi = new JMenuItem("Save", (new MenuShortcut(115)).getKey());
		mi.setAccelerator(KeyStroke.getKeyStroke(83, 2));
		mi.addActionListener(new ActionListener() {

			final JoonEdit this$0;

			public void actionPerformed(ActionEvent event)
			{
				if (latestStorageFormat != null && getDrawingTitle() != null && !getDrawingTitle().equals(""))
					saveDrawing(latestStorageFormat, getDrawingTitle());
				else
					promptSaveAs();
			}

			
			{
				this$0 = JoonEdit.this;
				super();
			}
		});
		menu.add(mi);
		mi = new JMenuItem("Save As...", (new MenuShortcut(97)).getKey());
		mi.addActionListener(new ActionListener() {

			final JoonEdit this$0;

			public void actionPerformed(ActionEvent event)
			{
				promptSaveAs();
			}

			
			{
				this$0 = JoonEdit.this;
				super();
			}
		});
		menu.add(mi);
		mi = new JMenuItem("Export NeuralNet...", (new MenuShortcut(101)).getKey());
		mi.addActionListener(new ActionListener() {

			final JoonEdit this$0;

			public void actionPerformed(ActionEvent event)
			{
				promptSaveNeuralNet();
			}

			
			{
				this$0 = JoonEdit.this;
				super();
			}
		});
		menu.add(mi);
		mi = new JMenuItem("Export as XML...", (new MenuShortcut(101)).getKey());
		mi.addActionListener(new ActionListener() {

			final JoonEdit this$0;

			public void actionPerformed(ActionEvent event)
			{
				promptSaveAsXML();
			}

			
			{
				this$0 = JoonEdit.this;
				super();
			}
		});
		menu.add(mi);
		menu.addSeparator();
		mi = new JMenuItem("Print...", (new MenuShortcut(112)).getKey());
		mi.setAccelerator(KeyStroke.getKeyStroke(80, 2));
		mi.addActionListener(new ActionListener() {

			final JoonEdit this$0;

			public void actionPerformed(ActionEvent event)
			{
				print();
			}

			
			{
				this$0 = JoonEdit.this;
				super();
			}
		});
		menu.add(mi);
		mi = new JMenuItem("Page Setup...", (new MenuShortcut(117)).getKey());
		mi.addActionListener(new ActionListener() {

			final JoonEdit this$0;

			public void actionPerformed(ActionEvent event)
			{
				pageSetup();
			}

			
			{
				this$0 = JoonEdit.this;
				super();
			}
		});
		menu.add(mi);
		menu.addSeparator();
		mi = new JMenuItem("Exit", (new MenuShortcut(120)).getKey());
		mi.addActionListener(new ActionListener() {

			final JoonEdit this$0;

			public void actionPerformed(ActionEvent event)
			{
				exit();
			}

			
			{
				this$0 = JoonEdit.this;
				super();
			}
		});
		menu.add(mi);
		return menu;
	}

	protected JMenu createHelpMenu()
	{
		CommandMenu menu = new CommandMenu("Help");
		menu.setMnemonic(72);
		af = new AboutFrame(this);
		try
		{
			JMenuItem helpContents = new JMenuItem("Help Contents");
			helpContents.setAccelerator(KeyStroke.getKeyStroke("F1"));
			helpContents.setMnemonic(72);
			HelpSet hs = new HelpSet(null, HelpSet.findHelpSet(null, "org/joone/edit/help_contents/joone.hs"));
			HelpBroker hb = hs.createHelpBroker();
			hb.setSize(new Dimension(800, 600));
			hb.setFont(new Font("Helvetica", 0, 10));
			helpContents.addActionListener(new javax.help.CSH.DisplayHelpFromSource(hb));
			hb.enableHelpKey(getRootPane(), "top", null);
			menu.add(helpContents);
		}
		catch (Exception e)
		{
			log.warn((new StringBuilder("Exception thrown while creating the MenuHelp. Message is : ")).append(e.getMessage()).toString(), e);
		}
		JMenu samples = createSamplesMenu();
		menu.add(samples);
		menu.addSeparator();
		JMenuItem mi = new JMenuItem("About Joone");
		mi.setMnemonic(65);
		mi.addActionListener(new ActionListener() {

			final JoonEdit this$0;

			public void actionPerformed(ActionEvent event)
			{
				Rectangle r = getBounds();
				af.place(r.x + r.width / 2, r.y + r.height / 2);
				af.setVisible(true);
			}

			
			{
				this$0 = JoonEdit.this;
				super();
			}
		});
		menu.add(mi);
		return menu;
	}

	private JMenu createSamplesMenu()
	{
		JMenu smp = new JMenu("Examples");
		JMenuItem smpi = new JMenuItem("SOM Image Tester");
		smpi.addActionListener(new ActionListener() {

			final JoonEdit this$0;

			public void actionPerformed(ActionEvent event)
			{
				(new SOMImageTester()).setVisible(true);
			}

			
			{
				this$0 = JoonEdit.this;
				super();
			}
		});
		smp.add(smpi);
		return smp;
	}

	public void promptSaveNeuralNet()
	{
		toolDone();
		JFileChooser saveDialog;
		if (getDrawingTitle() == null || getDrawingTitle().equals(""))
		{
			saveDialog = createSaveFileChooser();
		} else
		{
			saveDialog = new JFileChooser(getDrawingTitle());
			saveDialog.setDialogTitle("Save File...");
		}
		getNetStorageFormatManager().registerFileFilters(saveDialog);
		if (saveDialog.showSaveDialog(this) == 0)
		{
			NetStorageFormat foundFormat = getNetStorageFormatManager().findStorageFormat(saveDialog.getFileFilter());
			if (foundFormat != null)
			{
				saveNeuralNet(foundFormat, saveDialog.getSelectedFile().getAbsolutePath());
				latestStorageFormat = null;
			} else
			{
				showStatus((new StringBuilder("Not a valid file format: ")).append(saveDialog.getFileFilter().getDescription()).toString());
			}
		}
	}

	public void promptSaveAsXML()
	{
		toolDone();
		JFileChooser saveDialog;
		if (getDrawingTitle() == null || getDrawingTitle().equals(""))
		{
			saveDialog = createSaveFileChooser();
		} else
		{
			saveDialog = new JFileChooser(getDrawingTitle());
			saveDialog.setDialogTitle("Save File...");
		}
		getXMLStorageFormatManager().registerFileFilters(saveDialog);
		if (saveDialog.showSaveDialog(this) == 0)
		{
			NetStorageFormat foundFormat = getXMLStorageFormatManager().findStorageFormat(saveDialog.getFileFilter());
			if (foundFormat != null)
			{
				saveNeuralNet(foundFormat, saveDialog.getSelectedFile().getAbsolutePath());
				latestStorageFormat = null;
			} else
			{
				showStatus((new StringBuilder("Not a valid file format: ")).append(saveDialog.getFileFilter().getDescription()).toString());
			}
		}
	}

	protected void saveNeuralNet(NetStorageFormat storeFormat, String file)
	{
		try
		{
			NeuralNetDrawing nnd = (NeuralNetDrawing)drawing();
			NeuralNet nn = nnd.getNeuralNet();
			nn.getMonitor().setExporting(true);
			storeFormat.store(file, nn);
			nn.getMonitor().setExporting(false);
		}
		catch (IOException e)
		{
			showStatus(e.toString());
		}
	}

	protected JFileChooser createOpenFileChooser(String fn)
	{
		if (m_openDialog == null)
			m_openDialog = new JooneFileChooser(fn);
		return m_openDialog;
	}

	protected JFileChooser createOpenFileChooser()
	{
		if (m_openDialog == null)
			m_openDialog = new JooneFileChooser();
		return m_openDialog;
	}

	public StorageFormatManager createStorageFormatManager()
	{
		StorageFormatManager storageFormatManager = new StorageFormatManager();
		storageFormatManager.setDefaultStorageFormat(new SerializationStorageFormat());
		storageFormatManager.addStorageFormat(storageFormatManager.getDefaultStorageFormat());
		return storageFormatManager;
	}

	public NetStorageFormatManager createNetStorageFormatManager()
	{
		NetStorageFormatManager storageFormatManager = new NetStorageFormatManager();
		storageFormatManager.setDefaultStorageFormat(new StandardNetStorageFormat());
		storageFormatManager.addStorageFormat(storageFormatManager.getDefaultStorageFormat());
		return storageFormatManager;
	}

	public NetStorageFormatManager createXMLStorageFormatManager()
	{
		NetStorageFormatManager storageFormatManager = new NetStorageFormatManager();
		storageFormatManager.setDefaultStorageFormat(new XMLNetStorageFormat());
		storageFormatManager.addStorageFormat(storageFormatManager.getDefaultStorageFormat());
		return storageFormatManager;
	}

	private void setNetStorageFormatManager(NetStorageFormatManager storageFormatManager)
	{
		fNetStorageFormatManager = storageFormatManager;
	}

	public NetStorageFormatManager getNetStorageFormatManager()
	{
		return fNetStorageFormatManager;
	}

	private void setXMLStorageFormatManager(NetStorageFormatManager storageFormatManager)
	{
		xNetStorageFormatManager = storageFormatManager;
	}

	public NetStorageFormatManager getXMLStorageFormatManager()
	{
		return xNetStorageFormatManager;
	}

	protected Drawing createDrawing()
	{
		return new NeuralNetDrawing();
	}

	protected StandardDrawingView createDrawingView()
	{
		Dimension d = getDrawingViewSize();
		JooneStandardDrawingView newView = new JooneStandardDrawingView(this, d.width, d.height);
		newView.setBackground(Color.lightGray);
		return newView;
	}

	protected void loadDrawing(StorageFormat restoreFormat, String file)
	{
		try
		{
			Drawing restoredDrawing = restoreFormat.restore(file);
			if (restoredDrawing != null)
			{
				setDrawing(restoredDrawing);
				setDrawingTitle(file);
			} else
			{
				showStatus((new StringBuilder("Unknown file type: could not open file '")).append(file).append("'").toString());
			}
		}
		catch (IOException e)
		{
			showStatus((new StringBuilder("Error: ")).append(e).toString());
		}
	}

	public static void main(String args[])
	{
		if (args.length == 0)
		{
			JoonEdit window = new JoonEdit();
			window.open();
		} else
		{
			JoonEdit window = new JoonEdit(args[0]);
			window.open();
		}
	}

	public void promptNew()
	{
		super.promptNew();
		if (psp != null)
		{
			psp.setVisible(false);
			psp = null;
		}
		if (ps != null)
		{
			ps.setVisible(false);
			ps = null;
		}
		NeuralNetDrawing nnd = (NeuralNetDrawing)drawing();
		nnd.setNeuralNet(new NeuralNet());
		LayerFigure.setNumLayers(0);
		if (macroEditor != null)
		{
			macroEditor.setVisible(false);
			macroEditor = null;
		}
		((JooneStandardDrawingView)view()).setModified(false);
	}

	public void open()
	{
		ImageIcon img;
		JWindow frame;
		JLabel info;
		URL url = org/joone/edit/JoonEdit.getResource("/org/joone/images/splash.gif");
		img = new ImageIcon(url);
		frame = new JWindow();
		info = new JLabel("Initializing....", 0);
		info.setForeground(Color.black);
		try
		{
			JLabel pan = new JLabel(img) {

				final JoonEdit this$0;

				public void paint(Graphics g)
				{
					super.paint(g);
					Graphics2D g2 = (Graphics2D)g;
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2.setFont(new Font("Arial", 1, 10));
					g2.drawString("Neural Network Editor", 15, 145);
					g2.drawString((new StringBuilder("v ")).append(JoonEdit.getVersion()).toString(), 45, 158);
					g2.drawString((new StringBuilder("(c) ")).append(Calendar.getInstance().get(1)).append(" Paolo Marrone").toString(), 10, 171);
				}

			
			{
				this$0 = JoonEdit.this;
				super($anonymous0);
			}
			};
			frame.getContentPane().add(pan, "Center");
			pan.setLayout(new BorderLayout());
			pan.add(info, "South");
			pan.setBorder(BorderFactory.createRaisedBevelBorder());
			info.setPreferredSize(new Dimension(pan.getWidth(), 24));
			frame.pack();
			center(frame);
			frame.setVisible(true);
			info.setText("Starting...");
			super.open();
			readParameters();
			Iconkit kit = Iconkit.instance();
			if (kit == null)
				throw new HJDError("Iconkit instance isn't set");
			java.awt.Image icn = kit.loadImageResource("/org/joone/images/JooneIcon.gif");
			setIconImage(icn);
			((JooneStandardDrawingView)view()).setModified(false);
			break MISSING_BLOCK_LABEL_241;
		}
		catch (Exception e)
		{
			info.setText(e.getMessage());
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		frame.dispose();
		break MISSING_BLOCK_LABEL_245;
		Exception exception;
		exception;
		frame.dispose();
		throw exception;
		frame.dispose();
	}

	private void readParameters()
	{
		parameters = new EditorParameters();
		Vector elements = tParser.getElements();
		for (int i = 0; i < elements.size(); i++)
		{
			ToolElement te = (ToolElement)elements.elementAt(i);
			if (te.getType().compareToIgnoreCase("refreshing_rate") == 0)
				parameters.setRefreshingRate(Integer.parseInt((String)te.getParam("value")));
			if (te.getType().compareToIgnoreCase("http_proxy") == 0)
			{
				String hostname = (String)te.getParam("host");
				String port = (String)te.getParam("port");
				String userid = (String)te.getParam("userid");
				String passw = (String)te.getParam("passw");
				log.info((new StringBuilder("Using proxy: http://")).append(hostname).append(":").append(port).toString());
				System.setProperty("proxySet", "true");
				System.setProperty("http.proxyHost", hostname);
				System.setProperty("http.proxyPort", port);
				Authenticator.setDefault(new httpAuthenticateProxy(userid, passw));
			}
		}

	}

	public static String getVersion()
	{
		return "2.0.0RC1";
	}

	public static int getNumericVersion()
	{
		return 0x1e8480;
	}

	public static String getRecommendedEngineVersion()
	{
		return "1.2.5";
	}

	public static int getNumericRecommendedEngineVersion()
	{
		return 0xf4a15;
	}

	protected JMenu createAttributesMenu()
	{
		JMenu menu = new JMenu("Attributes");
		menu.setMnemonic(84);
		JMenuItem mi = createColorMenu("Fill Color", "FillColor");
		mi.setMnemonic(70);
		menu.add(mi);
		mi = createColorMenu("Pen Color", "FrameColor");
		mi.setMnemonic(80);
		menu.add(mi);
		mi = createArrowMenu();
		mi.setMnemonic(65);
		menu.add(mi);
		menu.addSeparator();
		mi = createFontMenu();
		mi.setMnemonic(79);
		menu.add(mi);
		mi = createFontSizeMenu();
		mi.setMnemonic(83);
		menu.add(mi);
		mi = createFontStyleMenu();
		mi.setMnemonic(78);
		menu.add(mi);
		mi = createColorMenu("Text Color", "TextColor");
		mi.setMnemonic(84);
		menu.add(mi);
		return menu;
	}

	public JMenu createWindowMenu()
	{
		JMenu menu = new JMenu("Window");
		menu.setMnemonic(87);
		menu.add(createLookAndFeel());
		return menu;
	}

	public JMenu createLookAndFeel()
	{
		javax.swing.UIManager.LookAndFeelInfo lafs[] = UIManager.getInstalledLookAndFeels();
		JMenu menu = new JMenu("Look and Feel");
		menu.setMnemonic(76);
		JMenuItem mi = null;
		for (int i = 0; i < lafs.length; i++)
		{
			mi = new JMenuItem(lafs[i].getName());
			final String lnfClassName = lafs[i].getClassName();
			mi.addActionListener(new ActionListener() {

				final JoonEdit this$0;
				private final String val$lnfClassName;

				public void actionPerformed(ActionEvent event)
				{
					newLookAndFeel(lnfClassName);
				}

			
			{
				this$0 = JoonEdit.this;
				lnfClassName = s;
				super();
			}
			});
			menu.add(mi);
		}

		return menu;
	}

	private void newLookAndFeel(String landf)
	{
		try
		{
			UIManager.setLookAndFeel(landf);
			SwingUtilities.updateComponentTreeUI(this);
		}
		catch (Exception e)
		{
			log.warn((new StringBuilder("Exception thrown while adding a lookAndFeel. Message is : ")).append(e.getMessage()).toString(), e);
		}
	}

	protected JMenu createEditMenu()
	{
		JooneCommandMenu menu = new JooneCommandMenu("Edit");
		menu.setMnemonic(69);
		menu.add(new JooneCutCommand("Cut", view()), new MenuShortcut(116), KeyStroke.getKeyStroke(88, 2));
		menu.add(new JooneCopyCommand("Copy", view()), new MenuShortcut(99), KeyStroke.getKeyStroke(67, 2));
		menu.add(new PasteCommand("Paste", view()), new MenuShortcut(112), KeyStroke.getKeyStroke(86, 2));
		menu.addSeparator();
		menu.add(new JooneDuplicateCommand("Duplicate", view()), new MenuShortcut(100));
		menu.add(new DeleteCommand("Delete", view()), new MenuShortcut(101), KeyStroke.getKeyStroke("DELETE"));
		menu.addSeparator();
		menu.add(new GroupCommand("Group", view()), new MenuShortcut(103));
		menu.add(new UngroupCommand("Ungroup", view()), new MenuShortcut(117));
		menu.addSeparator();
		menu.add(new SendToBackCommand("Send to Back", view()), new MenuShortcut(115));
		menu.add(new BringToFrontCommand("Bring to Front", view()), new MenuShortcut(98));
		return menu;
	}

	public void promptSaveAs()
	{
		toolDone();
		JFileChooser saveDialog;
		if (getDrawingTitle() == null || getDrawingTitle().equals(""))
		{
			saveDialog = createSaveFileChooser();
		} else
		{
			saveDialog = new JFileChooser(getDrawingTitle());
			saveDialog.setDialogTitle("Save File...");
		}
		getStorageFormatManager().registerFileFilters(saveDialog);
		if (saveDialog.showSaveDialog(this) == 0)
		{
			StorageFormat foundFormat = getStorageFormatManager().findStorageFormat(saveDialog.getFileFilter());
			if (foundFormat != null)
			{
				saveDrawing(foundFormat, saveDialog.getSelectedFile().getAbsolutePath());
				latestStorageFormat = foundFormat;
			} else
			{
				showStatus((new StringBuilder("Not a valid file format: ")).append(saveDialog.getFileFilter().getDescription()).toString());
			}
		}
	}

	public void promptOpen()
	{
		toolDone();
		if (getDrawingTitle() == null || getDrawingTitle().equals(""))
		{
			createOpenFileChooser();
		} else
		{
			createOpenFileChooser(getDrawingTitle());
			m_openDialog.setDialogTitle("Open File...");
		}
		getStorageFormatManager().registerFileFilters(m_openDialog);
		if (m_openDialog.showOpenDialog(this) == 0)
		{
			StorageFormat foundFormat = getStorageFormatManager().findStorageFormat(m_openDialog.getFileFilter());
			if (foundFormat != null)
			{
				promptNew();
				loadDrawing(foundFormat, m_openDialog.getSelectedFile().getAbsolutePath());
				latestStorageFormat = foundFormat;
			} else
			{
				showStatus((new StringBuilder("Not a valid file format: ")).append(m_openDialog.getFileFilter().getDescription()).toString());
			}
		}
	}

	protected void initDrawing()
	{
		super.initDrawing();
		latestStorageFormat = null;
	}

	public void exit()
	{
		int n = askForSave("Save changes to Neural Net?");
		if (n == 1 || !((JooneStandardDrawingView)view()).isModified())
		{
			writeIniFile();
			destroy();
			setVisible(false);
			dispose();
			log.info("Exit invoked successfully. Frame disposed. Bye");
			System.exit(0);
		}
	}

	protected void destroy()
	{
		if (m_openDialog != null)
			m_openDialog.saveDirectoryEntries();
	}

	protected void saveDrawing(StorageFormat storeFormat, String file)
	{
		try
		{
			setDrawingTitle(storeFormat.store(file, drawing()));
			((JooneStandardDrawingView)view()).setModified(false);
		}
		catch (IOException e)
		{
			showStatus(e.toString());
		}
	}

	protected int askForSave(String message)
	{
		int n = 2;
		if (((JooneStandardDrawingView)view()).isModified())
		{
			n = JOptionPane.showConfirmDialog(this, message, "JoonEdit", 1);
			if (n == 0)
				if (latestStorageFormat != null && getDrawingTitle() != null && !getDrawingTitle().equals(""))
					saveDrawing(latestStorageFormat, getDrawingTitle());
				else
					promptSaveAs();
		}
		return n;
	}

	private void readIniFile()
	{
		try
		{
			File jooneHome = new File((new StringBuilder(String.valueOf(System.getProperty("user.home")))).append(File.separator).append(".joone").toString());
			if (!jooneHome.exists())
				jooneHome.mkdir();
			iniFile = new IniFile((new StringBuilder()).append(jooneHome).append(File.separator).append("joonedit.config").toString());
			int state = Integer.parseInt(iniFile.getParameter("gui", "state", "0"));
			int x = Integer.parseInt(iniFile.getParameter("gui", "x", "0"));
			int y = Integer.parseInt(iniFile.getParameter("gui", "y", "0"));
			width = Integer.parseInt(iniFile.getParameter("gui", "width", "800"));
			height = Integer.parseInt(iniFile.getParameter("gui", "height", "600"));
			setLocation(x, y);
			if (state != -99)
				setState(state);
			String lafName = iniFile.getParameter("gui", "laf", null);
			if (lafName != null)
			{
				javax.swing.UIManager.LookAndFeelInfo lafs[] = UIManager.getInstalledLookAndFeels();
				for (int i = 0; i < lafs.length; i++)
					if (lafName.toLowerCase().equals(lafs[i].getClassName().toLowerCase()))
					{
						UIManager.setLookAndFeel(lafs[i].getClassName());
						SwingUtilities.updateComponentTreeUI(this);
					}

			}
		}
		catch (Exception e)
		{
			log.warn((new StringBuilder("Exception thrown reading writing config file. Message is : ")).append(e.getMessage()).toString(), e);
		}
	}

	private void writeIniFile()
	{
		try
		{
			if (getState() == 0)
			{
				iniFile.setParameter("gui", "x", String.valueOf(getX()));
				iniFile.setParameter("gui", "y", String.valueOf(getY()));
				iniFile.setParameter("gui", "width", String.valueOf(getWidth()));
				iniFile.setParameter("gui", "height", String.valueOf(getHeight()));
			}
			iniFile.setParameter("gui", "state", String.valueOf(getState()));
			iniFile.setParameter("gui", "laf", UIManager.getLookAndFeel().getClass().getName());
		}
		catch (Exception e)
		{
			log.warn((new StringBuilder("Exception thrown while writing config file. Message is : ")).append(e.getMessage()).toString(), e);
		}
	}

	private void pageSetup()
	{
		PrinterJob job = PrinterJob.getPrinterJob();
		job.pageDialog(job.defaultPage());
	}












}
