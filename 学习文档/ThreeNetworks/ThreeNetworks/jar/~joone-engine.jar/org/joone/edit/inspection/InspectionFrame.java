// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InspectionFrame.java

package org.joone.edit.inspection;

import CH.ifa.draw.framework.HJDError;
import CH.ifa.draw.util.Iconkit;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import org.joone.inspection.Inspectable;
import org.joone.inspection.Inspection;

public class InspectionFrame extends JFrame
{

	Collection inspections;
	JTabbedPane center;
	JTable tables[];

	public InspectionFrame(Inspectable inspectableArg)
	{
		inspections = new ArrayList();
		Iconkit kit = Iconkit.instance();
		final Collection copycol = inspectableArg != null ? inspectableArg.Inspections() : null;
		if (kit == null)
			throw new HJDError("Iconkit instance isn't set");
		Image img = kit.loadImageResource("/org/joone/images/JooneIcon.gif");
		setIconImage(img);
		if (inspectableArg != null)
		{
			inspections = inspectableArg.Inspections();
			setTitle((new StringBuilder("Inspection - ")).append(inspectableArg.InspectableTitle()).toString());
		} else
		{
			setTitle("Inspection");
		}
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		JButton close = new JButton("Close");
		close.setToolTipText("Close the inspection frame");
		close.addActionListener(new ActionListener() {

			final InspectionFrame this$0;

			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}

			
			{
				this$0 = InspectionFrame.this;
				super();
			}
		});
		JButton copybutton = new JButton("Copy");
		copybutton.setToolTipText("Copy data as tab delimeted");
		copybutton.addActionListener(new ActionListener() {

			final InspectionFrame this$0;
			private final Collection val$copycol;

			public void actionPerformed(ActionEvent e)
			{
				Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
				String s = "";
				if (copycol != null)
				{
					for (Iterator iter = copycol.iterator(); iter.hasNext();)
					{
						Object o = iter.next();
						if (o instanceof Inspection)
						{
							Inspection inspection = (Inspection)o;
							Component comp = makeTable(inspection);
							try
							{
								if (comp.getClass().isInstance(new JTable()))
								{
									JTable tab = (JTable)comp;
									int f = inspection.rowNumbers() ? 1 : 0;
									for (int i = 0; i < tab.getRowCount(); i++)
									{
										for (int j = f; j < tab.getColumnCount(); j++)
										{
											if (j > f)
												s = (new StringBuilder(String.valueOf(s))).append("\t").toString();
											s = (new StringBuilder(String.valueOf(s))).append(tab.getModel().getValueAt(i, j).toString()).toString();
										}

										s = (new StringBuilder(String.valueOf(s))).append("\n").toString();
									}

									StringSelection contents = new StringSelection(s);
									cb.setContents(contents, null);
								}
							}
							catch (Throwable throwable) { }
						}
					}

				}
			}

			
			{
				this$0 = InspectionFrame.this;
				copycol = collection;
				super();
			}
		});
		JButton pastebutton = new JButton("Paste");
		pastebutton.setToolTipText("Paste data from tab delimeted source");
		pastebutton.addActionListener(new ActionListener() {

			final InspectionFrame this$0;

			public void actionPerformed(ActionEvent e)
			{
				Transferable contents;
				Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
				contents = cb.getContents(null);
				if (contents == null)
					break MISSING_BLOCK_LABEL_305;
				LineNumberReader lnr;
				int curr;
				Inspection inspection;
				Object newValues[][];
				String s = (String)contents.getTransferData(DataFlavor.stringFlavor);
				StringReader sr = new StringReader(s);
				lnr = new LineNumberReader(sr);
				curr = center.getSelectedIndex();
				Object arr[] = inspections.toArray();
				inspection = (Inspection)arr[curr];
				newValues = inspection.getComponent();
				if (newValues == null)
					return;
				try
				{
					String line = null;
					for (int iLine = 0; (line = lnr.readLine()) != null && iLine < newValues.length; iLine++)
					{
						StringTokenizer st = new StringTokenizer(line, " ;,\t\n\r\f");
						int iCol = 0;
						if (inspection.rowNumbers())
							iCol = 1;
						for (; st.hasMoreTokens() && iCol < newValues[0].length; iCol++)
						{
							String token = st.nextToken();
							try
							{
								Double val = new Double(token);
								newValues[iLine][iCol] = val;
							}
							catch (NumberFormatException numberformatexception) { }
						}

					}

					inspection.setComponent(newValues);
					center.removeTabAt(curr);
					Component pan = createPanel(makeTable(inspection));
					center.insertTab(inspection.getTitle(), null, pan, null, curr);
					center.setSelectedIndex(curr);
				}
				catch (Exception exc)
				{
					exc.printStackTrace();
				}
			}

			
			{
				this$0 = InspectionFrame.this;
				super();
			}
		});
		JPanel east = new JPanel();
		east.setLayout(new GridLayout(6, 1));
		east.add(close);
		east.add(copybutton);
		east.add(pastebutton);
		contentPane.add(east, "East");
		center = new JTabbedPane();
		if (inspectableArg == null)
		{
			addTab(center, "error", new JLabel("Inspectable object is null."));
		} else
		{
			Collection col = inspectableArg.Inspections();
			if (col == null)
			{
				addTab(center, "Error", new JLabel("Inspectable object Collection is null."));
			} else
			{
				for (Iterator iter = col.iterator(); iter.hasNext();)
				{
					Object o = iter.next();
					if (o instanceof Inspection)
					{
						Inspection inspection = (Inspection)o;
						String title = inspection.getTitle();
						addTab(center, title, makeTable(inspection));
					} else
					{
						addTab(center, "Error", new Label("Object is not an Inspection."));
					}
				}

			}
		}
		contentPane.add(center, "Center");
	}

	private void addTab(JTabbedPane jTabbedPane, String title, Component component)
	{
		jTabbedPane.addTab(title, createPanel(component));
	}

	private Component createPanel(Component component)
	{
		JPanel jPanel = new JPanel(new FlowLayout(0));
		jPanel.add(component);
		JScrollPane jScrollPane = new JScrollPane(jPanel, 20, 30);
		return jScrollPane;
	}

	public void show()
	{
		pack();
		if (getWidth() < 250)
			setSize(250, getHeight());
		if (getWidth() > 500)
			setSize(500, getHeight());
		if (getHeight() > 200)
			setSize(getWidth(), 200);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
		super.show();
	}

	private Component makeTable(Inspection inspection)
	{
		Object array[][] = inspection.getComponent();
		Object names[] = inspection.getNames();
		boolean rowsNumber = inspection.rowNumbers();
		if (array != null && names != null)
		{
			JTable jTable = new JTable(array, names);
			if (rowsNumber)
			{
				TableColumn col = jTable.getColumnModel().getColumn(0);
				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
				renderer.setToolTipText("Row Number");
				renderer.setBackground(new Color(178, 178, 255));
				col.setCellRenderer(renderer);
			}
			jTable.setAutoResizeMode(2);
			jTable.doLayout();
			jTable.setEnabled(false);
			return jTable;
		} else
		{
			return new JLabel("There are no values set for this item.");
		}
	}


}
