// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AboutFrame.java

package org.joone.edit;

import CH.ifa.draw.framework.HJDError;
import CH.ifa.draw.util.Iconkit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NeuralNet;

// Referenced classes of package org.joone.edit:
//			JoonEdit

public class AboutFrame extends JDialog
{
	class TextBlock extends Canvas
	{

		private static final int TEXT_PADDING = 5;
		private String lines[];
		final AboutFrame this$0;

		public void paint(Graphics g)
		{
			FontMetrics fm = getFontMetrics(getFont());
			int lineAscent = fm.getAscent();
			int lineHeight = fm.getHeight();
			for (int i = 0; i < lines.length; i++)
				g.drawString(lines[i], 5, i * lineHeight + lineAscent + 5);

		}

		public Dimension getPreferredSize()
		{
			FontMetrics fm = getFontMetrics(getFont());
			int lineHeight = fm.getHeight();
			int width = 0;
			for (int i = 0; i < lines.length; i++)
				if (fm.stringWidth(lines[i]) > width)
					width = fm.stringWidth(lines[i]);

			return new Dimension(width + 10, lines.length * lineHeight + 10);
		}

		public TextBlock(String linesArg[], String hallOfFameArg[], String extPack[])
		{
			this$0 = AboutFrame.this;
			super();
			String neuralNetVersion = "unknown";
			Integer neuralNetNumericVersion = null;
			try
			{
				Object o = new NeuralNet();
				Method m = o.getClass().getMethod("getVersion", new Class[0]);
				if (m != null)
					neuralNetVersion = (String)m.invoke(o, new Object[0]);
				m = o.getClass().getMethod("getNumericVersion", new Class[0]);
				if (m != null)
					neuralNetNumericVersion = (Integer)m.invoke(o, new Object[0]);
			}
			catch (IllegalAccessException iae)
			{
				AboutFrame.log.warn((new StringBuilder("IllegalAccessException getting NeuralNetwork version. Message is ")).append(iae.getMessage()).toString(), iae);
			}
			catch (InvocationTargetException ite)
			{
				AboutFrame.log.warn((new StringBuilder("InvocationTargetException getting NeuralNetwork version. Message is : ")).append(ite.getMessage()).toString(), ite);
			}
			catch (NoSuchMethodException nsme)
			{
				AboutFrame.log.warn((new StringBuilder("NoSuchMethodException getting NeuralNetwork version. Do not panic. Message is : ")).append(nsme.getMessage()).toString(), nsme);
			}
			Collection col = new ArrayList();
			for (int i = 0; i < linesArg.length; i++)
				col.add(linesArg[i]);

			col.add("");
			col.add((new StringBuilder("Editor version: ")).append(JoonEdit.getVersion()).toString());
			col.add((new StringBuilder("Engine version: ")).append(neuralNetVersion).toString());
			if (neuralNetNumericVersion == null || neuralNetNumericVersion.intValue() < JoonEdit.getNumericRecommendedEngineVersion())
				col.add((new StringBuilder("Engine version ")).append(JoonEdit.getRecommendedEngineVersion()).append(" or above recommended").toString());
			col.add("");
			for (int i = 0; i < hallOfFameArg.length; i++)
				col.add(hallOfFameArg[i]);

			col.add("");
			for (int i = 0; i < extPack.length; i++)
				col.add(extPack[i]);

			lines = new String[col.size()];
			Iterator iter = col.iterator();
			int i = 0;
			while (iter.hasNext()) 
				lines[i++] = (String)iter.next();
		}
	}


	private static final ILogger log = LoggerFactory.getLogger(org/joone/edit/AboutFrame);
	private static final String ABOUT_TEXT[] = {
		"Joone - Java Object Oriented Neural Engine", "http://www.joone.org", "pmarrone@users.sourceforge.net"
	};
	private static final String HALL_OF_FAME[] = {
		"Joone was developed by Paolo Marrone,", "with the valuable collaboration of:", "   Gavin Alford, Mark Allen, Jan Boonen, Yan Cheng Cheok,", "   Ka-Hing Cheung, Andrea Corti, Huascar Fiorletta, Firestrand,", "   Pascal Deschenes, Jan Erik Garshol, Harry Glasgow,", "   Jack Hawkins, Nathan Hindley, Olivier Hussenet,", "   Boris Jansen, Shen Linlin, Casey Marshall, Julien Norman,", "   Christian Ribeaud, Anat Rozenzon, Trevis Silvers,", "   Paul Sinclair, Thomas Lionel Smets, Jerry R.Vos"
	};
	private static final String EXTERNAL_PACK[] = {
		"Joone uses the following external packages:", "   JHotDraw - http://sourceforge.net/projects/jhotdraw", "   BeanShell - http://www.beanshell.org", "   Groovy - http://groovy.codehaus.org", "   jEdit-Syntax - http://sourceforge.net/projects/jedit-syntax", "   Log4j -  http://jakarta.apache.org/log4j", "   HSSF-POI - http://jakarta.apache.org/poi", "   L2FProd - http://common.l2fprod.com", "   NachoCalendar - http://nachocalendar.sourceforge.net", "   XStream - http://xstream.codehaus.org", 
		"   VisAD - http://www.ssec.wisc.edu/~billh/visad.html"
	};

	AboutFrame(Frame owner)
	{
		setModal(true);
		JRootPane rp = new JRootPane();
		setRootPane(rp);
		rp.setLayout(new BorderLayout());
		Iconkit kit = Iconkit.instance();
		if (kit == null)
			throw new HJDError("Iconkit instance isn't set");
		final Image img = kit.loadImageResource("/org/joone/images/jooneShadowSmall.gif");
		if (img != null)
		{
			Panel q = new Panel() {

				final AboutFrame this$0;
				private final Image val$img;

				public void paint(Graphics g)
				{
					g.drawImage(img, 0, 0, this);
				}

				public Dimension getPreferredSize()
				{
					return new Dimension(84, 87);
				}

			
			{
				this$0 = AboutFrame.this;
				img = image;
				super();
			}
			};
			rp.add(q, "West");
		}
		Button b = new Button("   OK   ");
		b.addActionListener(new ActionListener() {

			final AboutFrame this$0;

			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}

			
			{
				this$0 = AboutFrame.this;
				super();
			}
		});
		Panel p = new Panel();
		p.add(b);
		rp.add(p, "South");
		TextBlock tb = new TextBlock(ABOUT_TEXT, HALL_OF_FAME, EXTERNAL_PACK);
		rp.add(tb, "Center");
		setTitle("About Joone");
		setBackground(Color.white);
		setResizable(false);
		pack();
	}

	public void place(int x, int y)
	{
		setLocation(x - getWidth() / 2, y - getHeight() / 2);
	}


}
