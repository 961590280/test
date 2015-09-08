// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ControlPanel.java

package org.joone.edit;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import org.joone.engine.*;
import org.joone.net.NetChecker;
import org.joone.net.NeuralNet;

// Referenced classes of package org.joone.edit:
//			PropertySheet, EditorParameters

public class ControlPanel extends JPanel
	implements NeuralNetListener
{

	private Monitor monitor;
	private NeuralNet nNet;
	private PropertySheet parent;
	private NetChecker netChecker;
	private boolean printCycle;
	private JButton continueButton;
	private JLabel cicleLabel;
	private JButton stopButton;
	private JPanel jPanel2;
	private JLabel cicleValue;
	private JButton runButton;
	private JLabel rmsLabel;
	private JPanel jPanel1;
	private JLabel rmsValue;
	private static final long serialVersionUID = 0x122f14cfe2bf5751L;
	private EditorParameters parameters;

	public ControlPanel(NeuralNet nn, PropertySheet ps)
	{
		printCycle = false;
		setMonitor(nn.getMonitor());
		parent = ps;
		nNet = nn;
		netChecker = new NetChecker(nNet);
		initComponents();
	}

	public void setMonitor(Monitor m)
	{
		monitor = m;
		monitor.addNeuralNetListener(this);
	}

	private void initComponents()
	{
		jPanel1 = new JPanel();
		runButton = new JButton();
		continueButton = new JButton();
		stopButton = new JButton();
		jPanel2 = new JPanel();
		cicleLabel = new JLabel();
		cicleValue = new JLabel();
		rmsLabel = new JLabel();
		rmsValue = new JLabel();
		setLayout(new GridLayout(2, 1, 0, 10));
		setBorder(new TitledBorder("Controls"));
		jPanel1.setLayout(new GridLayout(1, 3));
		runButton.setForeground(Color.green);
		runButton.setText("Run");
		runButton.addActionListener(new ActionListener() {

			final ControlPanel this$0;

			public void actionPerformed(ActionEvent evt)
			{
				runButtonActionPerformed(evt);
			}

			
			{
				this$0 = ControlPanel.this;
				super();
			}
		});
		jPanel1.add(runButton);
		continueButton.setForeground(new Color(0, 51, 255));
		continueButton.setText("Continue");
		continueButton.setEnabled(false);
		continueButton.addActionListener(new ActionListener() {

			final ControlPanel this$0;

			public void actionPerformed(ActionEvent evt)
			{
				continueButtonActionPerformed(evt);
			}

			
			{
				this$0 = ControlPanel.this;
				super();
			}
		});
		jPanel1.add(continueButton);
		stopButton.setForeground(Color.red);
		stopButton.setText("Pause");
		stopButton.setEnabled(false);
		stopButton.addActionListener(new ActionListener() {

			final ControlPanel this$0;

			public void actionPerformed(ActionEvent evt)
			{
				stopButtonActionPerformed(evt);
			}

			
			{
				this$0 = ControlPanel.this;
				super();
			}
		});
		jPanel1.add(stopButton);
		add(jPanel1);
		jPanel2.setLayout(new GridLayout(2, 2, 0, 8));
		cicleLabel.setHorizontalAlignment(4);
		cicleLabel.setText("Epochs :  ");
		cicleLabel.setHorizontalTextPosition(4);
		jPanel2.add(cicleLabel);
		cicleValue.setHorizontalAlignment(2);
		cicleValue.setText("0");
		jPanel2.add(cicleValue);
		rmsLabel.setHorizontalAlignment(4);
		rmsLabel.setText("RMSE :  ");
		jPanel2.add(rmsLabel);
		rmsValue.setHorizontalAlignment(2);
		rmsValue.setText("0.0");
		jPanel2.add(rmsValue);
		add(jPanel2);
	}

	private void continueButtonActionPerformed(ActionEvent evt)
	{
		if (netChecker.hasErrors())
		{
			displayNogo("Network could not be run.", "See 'To Do List' to check all the errors.");
		} else
		{
			setLearners();
			nNet.restore();
			setState(false);
		}
	}

	private void stopButtonActionPerformed(ActionEvent evt)
	{
		nNet.stop();
	}

	private void runButtonActionPerformed(ActionEvent evt)
	{
		if (netChecker.hasErrors())
		{
			displayNogo("Network could not be run.", "See 'To Do List' to check all the errors.");
		} else
		{
			setLearners();
			nNet.go();
		}
	}

	public void netStopped(NeuralNetEvent e)
	{
		setState(true);
		printCycle = true;
		parent.update();
	}

	public void cicleTerminated(NeuralNetEvent e)
	{
		int currentCycle = (monitor.getTotCicles() - monitor.getCurrentCicle()) + 1;
		int cl = currentCycle / parameters.getRefreshingRate();
		if (cl * parameters.getRefreshingRate() == currentCycle || monitor.getCurrentCicle() == 1)
		{
			cicleValue.setText(Integer.toString(currentCycle));
			printCycle = true;
		}
	}

	public void setParameters(EditorParameters parameters)
	{
		this.parameters = parameters;
	}

	private void setState(boolean state)
	{
		runButton.setEnabled(state);
		continueButton.setEnabled(state);
		stopButton.setEnabled(!state);
		parent.setControlsEnabled(state);
	}

	public void netStarted(NeuralNetEvent e)
	{
		setState(false);
	}

	public void errorChanged(NeuralNetEvent e)
	{
		if (printCycle || monitor.getTotCicles() < parameters.getRefreshingRate())
		{
			rmsValue.setText(Double.toString(monitor.getGlobalError()));
			printCycle = false;
		}
	}

	private void displayNogo(String message1, String message2)
	{
		final JDialog dialog = new JDialog(parent, "Critical Network Error", true);
		dialog.getContentPane().setLayout(new BorderLayout(10, 10));
		Button ok = new Button("  OK  ");
		ok.addActionListener(new ActionListener() {

			final ControlPanel this$0;
			private final JDialog val$dialog;

			public void actionPerformed(ActionEvent e)
			{
				dialog.dispose();
			}

			
			{
				this$0 = ControlPanel.this;
				dialog = jdialog;
				super();
			}
		});
		Panel p = new Panel();
		p.setLayout(new FlowLayout(1));
		p.add(ok);
		dialog.getContentPane().add(p, "South");
		JLabel label = new JLabel(message1);
		label.setHorizontalAlignment(0);
		dialog.getContentPane().add(label, "North");
		label = new JLabel(message2);
		label.setHorizontalAlignment(0);
		dialog.getContentPane().add(label, "Center");
		dialog.pack();
		Point center = new Point(parent.getX() + parent.getWidth() / 2, parent.getY() + parent.getHeight() / 2);
		dialog.setLocation((int)(center.getX() - (double)(dialog.getWidth() / 2)), (int)(center.getY() - (double)(dialog.getHeight() / 2)));
		dialog.setVisible(true);
	}

	public void netStoppedError(NeuralNetEvent e, String error)
	{
		setState(true);
		printCycle = true;
		parent.update();
		displayNogo("Runtime Error!", error);
	}

	public void setLearners()
	{
		monitor.addLearner(0, "org.joone.engine.BasicLearner");
		monitor.addLearner(1, "org.joone.engine.BatchLearner");
		monitor.addLearner(2, "org.joone.engine.RpropLearner");
	}



}
