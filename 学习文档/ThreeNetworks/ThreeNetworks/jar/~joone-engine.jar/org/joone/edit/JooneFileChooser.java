// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JooneFileChooser.java

package org.joone.edit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.Vector;
import javax.swing.*;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

public class JooneFileChooser extends JFileChooser
{
	private final class HistoryAndPreviewPanel extends JPanel
	{

		private static final long serialVersionUID = 0x70938cc6e73c0f4cL;
		final JooneFileChooser this$0;


		public HistoryAndPreviewPanel()
		{
			this$0 = JooneFileChooser.this;
			super();
			setPreferredSize(new Dimension(250, 250));
			setBorder(BorderFactory.createEtchedBorder());
			setLayout(new BorderLayout());
			m_comboBox = new JComboBox(m_dirList);
			m_comboBox.addActionListener(new ActionListener() {

				final HistoryAndPreviewPanel this$1;

				public void actionPerformed(ActionEvent e)
				{
					String dir = (String)m_comboBox.getSelectedItem();
					setCurrentDirectory(new File(dir));
					JLabel label = new JLabel(dir);
					label.setFont(m_comboBox.getFont());
					if (label.getPreferredSize().width > m_comboBox.getSize().width)
						m_comboBox.setToolTipText(dir);
					else
						m_comboBox.setToolTipText(null);
				}

				
				{
					this$1 = HistoryAndPreviewPanel.this;
					super();
				}
			});
			add(m_comboBox, "North");
			m_previewer = new TextPreviewer();
			add(m_previewer, "Center");
		}
	}

	private class TextPreviewer extends JComponent
	{

		private JTextArea m_textArea;
		private JScrollPane m_scroller;
		private char m_buf[];
		private static final long serialVersionUID = 0xe76b0b3acf7f2e54L;
		final JooneFileChooser this$0;

		public void showFileContents(File p_file)
		{
			m_textArea.setText(readFile(p_file));
			m_textArea.setCaretPosition(0);
		}

		public void clear()
		{
			m_textArea.setText("");
		}

		private String readFile(File p_file)
		{
			String str = null;
			FileReader reader = null;
			try
			{
				reader = new FileReader(p_file);
			}
			catch (FileNotFoundException fnfe)
			{
				JooneFileChooser.log.warn((new StringBuilder("File '")).append(p_file).append("' not found.").toString(), fnfe);
				return str;
			}
			try
			{
				int nch = reader.read(m_buf, 0, m_buf.length);
				if (nch != -1)
					str = new String(m_buf, 0, nch);
				reader.close();
			}
			catch (IOException ioe)
			{
				JooneFileChooser.log.warn((new StringBuilder("File '")).append(m_dir).append("' input/output error.").toString(), ioe);
			}
			return str;
		}

		public TextPreviewer()
		{
			this$0 = JooneFileChooser.this;
			super();
			m_textArea = new JTextArea();
			m_scroller = new JScrollPane(m_textArea);
			m_buf = new char[500];
			m_textArea.setEditable(false);
			setLayout(new BorderLayout());
			add(m_scroller, "Center");
		}
	}


	private static final ILogger log = LoggerFactory.getLogger(org/joone/edit/JooneFileChooser);
	private String m_dir;
	private FileInputStream m_fis;
	private ObjectInputStream m_ois;
	private Vector m_dirList;
	private static final String APPLICATION_NAME = "Joone Edit";
	private HistoryAndPreviewPanel m_historyAndPreviewPanel;
	private JComboBox m_comboBox;
	private TextPreviewer m_previewer;
	private static final long serialVersionUID = 0x75cc4134950b0067L;

	public JooneFileChooser(String baseDir)
	{
		super(baseDir);
		m_dir = (new StringBuilder(String.valueOf(System.getProperty("user.home")))).append(System.getProperty("file.separator")).append("Joone Edit").append("_DIRECTORY_HISTORY.cfg").toString();
		if ((new File(m_dir)).exists())
		{
			try
			{
				m_fis = new FileInputStream(m_dir);
			}
			catch (FileNotFoundException fnfe)
			{
				log.warn((new StringBuilder("File '")).append(m_dir).append("' not found. Message is : ").append(fnfe.getMessage()).toString());
			}
			try
			{
				m_ois = new ObjectInputStream(m_fis);
			}
			catch (StreamCorruptedException sce)
			{
				(new File(m_dir)).delete();
				m_dirList = new Vector();
			}
			catch (IOException ioe)
			{
				log.warn((new StringBuilder("File '")).append(m_dir).append("' input/output error. Message is : ").append(ioe.getMessage()).toString());
				m_dirList = new Vector();
			}
			try
			{
				if (m_ois != null)
				{
					m_dirList = (Vector)m_ois.readObject();
					if (m_dirList == null)
						m_dirList = new Vector();
				} else
				{
					m_dirList = new Vector();
				}
			}
			catch (OptionalDataException ode)
			{
				log.warn((new StringBuilder("File '")).append(m_dir).append("' does not contain object.").toString());
				m_dirList = new Vector();
			}
			catch (IOException ioe)
			{
				log.warn((new StringBuilder("File '")).append(m_dir).append("' input/output error. Message is : ").append(ioe.getMessage()).toString());
				m_dirList = new Vector();
			}
			catch (ClassNotFoundException cnfe)
			{
				log.warn((new StringBuilder("ClassNotFoundException thrown. Message is : ")).append(cnfe.getMessage()).toString());
				m_dirList = new Vector();
			}
			try
			{
				if (m_ois != null)
					m_ois.close();
				m_fis.close();
			}
			catch (IOException ioe)
			{
				log.warn((new StringBuilder("File '")).append(m_dir).append("' input/output error. Message is : ").append(ioe.getMessage()).toString());
			}
		} else
		{
			m_dirList = new Vector();
		}
		setMultiSelectionEnabled(false);
		m_historyAndPreviewPanel = new HistoryAndPreviewPanel();
		setAccessory(m_historyAndPreviewPanel);
		addPropertyChangeListener(new PropertyChangeListener() {

			final JooneFileChooser this$0;

			public void propertyChange(PropertyChangeEvent e)
			{
				if (e.getPropertyName().equals("directoryChanged"))
					m_previewer.clear();
				if (e.getPropertyName().equals("SelectedFileChangedProperty"))
				{
					File f = (File)e.getNewValue();
					if (f != null && f.isFile())
					{
						m_previewer.showFileContents(f);
						addDirectory(f);
					}
				}
			}

			
			{
				this$0 = JooneFileChooser.this;
				super();
			}
		});
	}

	public JooneFileChooser()
	{
		this(".");
	}

	public void saveDirectoryEntries()
	{
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try
		{
			fos = new FileOutputStream(m_dir);
		}
		catch (FileNotFoundException fnfe)
		{
			log.warn((new StringBuilder("File '")).append(m_dir).append("' not found.").toString(), fnfe);
		}
		try
		{
			oos = new ObjectOutputStream(fos);
			oos.writeObject(m_dirList);
			oos.flush();
			oos.close();
			if (fos != null)
				fos.close();
		}
		catch (IOException ioe)
		{
			log.warn((new StringBuilder("File '")).append(m_dir).append("' input/output error. Message is : ").append(ioe.getMessage()).toString(), ioe);
		}
	}

	private void addDirectory(File p_file)
	{
		if (p_file == null || p_file.getName().equals(""))
		{
			return;
		} else
		{
			String absolutePath = p_file.getAbsolutePath();
			int posDirSep = absolutePath.lastIndexOf(System.getProperty("file.separator"));
			String d = absolutePath.substring(0, posDirSep);
			m_dirList.removeElement(d);
			m_dirList.add(0, d);
			updateJComboBox();
			return;
		}
	}

	private void updateJComboBox()
	{
		m_comboBox.revalidate();
		m_comboBox.setSelectedIndex(0);
	}

	public static void main(String args[])
	{
		final JFrame f = new JFrame("Joone FileChooser Demo");
		final JooneFileChooser fc = new JooneFileChooser();
		f.addWindowListener(new WindowAdapter() {

			private final JooneFileChooser val$fc;
			private final JFrame val$f;

			public void windowClosing(WindowEvent e)
			{
				fc.saveDirectoryEntries();
				f.setVisible(false);
				f.dispose();
				System.exit(0);
			}

			
			{
				fc = joonefilechooser;
				f = jframe;
				super();
			}
		});
		JMenuBar mb = new JMenuBar();
		f.setJMenuBar(mb);
		JMenu m = new JMenu("File");
		m.setMnemonic('F');
		mb.add(m);
		JMenuItem o = new JMenuItem("Open...");
		o.addActionListener(new ActionListener() {

			private final JooneFileChooser val$fc;
			private final JFrame val$f;

			public void actionPerformed(ActionEvent e)
			{
				fc.setCurrentDirectory(new File("."));
				String filename = e.toString();
				int val = fc.showOpenDialog(f);
				if (val == 0)
				{
					File file = fc.getSelectedFile();
					if (file != null && !file.getName().equals(""))
						filename = file.getName();
				}
			}

			
			{
				fc = joonefilechooser;
				f = jframe;
				super();
			}
		});
		m.add(o);
		f.pack();
		f.setSize(310, 130);
		f.setVisible(true);
	}









}
