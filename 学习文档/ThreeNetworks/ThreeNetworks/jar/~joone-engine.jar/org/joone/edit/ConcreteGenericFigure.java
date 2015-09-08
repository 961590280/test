// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConcreteGenericFigure.java

package org.joone.edit;

import CH.ifa.draw.figures.TextFigure;
import CH.ifa.draw.framework.*;
import CH.ifa.draw.standard.*;
import CH.ifa.draw.util.StorableInput;
import CH.ifa.draw.util.StorableOutput;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.joone.edit.inspection.InspectionFrame;
import org.joone.inspection.Inspectable;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.edit:
//			GenericFigure, UpdatableFigure, Wrapper, PropertySheet

public abstract class ConcreteGenericFigure extends CompositeFigure
	implements GenericFigure, UpdatableFigure
{

	public static final ILogger log = LoggerFactory.getLogger(org/joone/edit/ConcreteGenericFigure);
	protected static final int BORDER = 3;
	protected Rectangle fDisplayBox;
	protected Hashtable params;
	protected static int numLayers = 0;
	private static final long serialVersionUID = 0xac056993f16a6383L;

	public ConcreteGenericFigure()
	{
	}

	protected void basicMoveBy(int x, int y)
	{
		fDisplayBox.translate(x, y);
		super.basicMoveBy(x, y);
	}

	public Rectangle displayBox()
	{
		return new Rectangle(fDisplayBox.x, fDisplayBox.y, fDisplayBox.width, fDisplayBox.height);
	}

	public void basicDisplayBox(Point origin, Point corner)
	{
		fDisplayBox = new Rectangle(origin);
		fDisplayBox.add(corner);
		layout();
	}

	private void drawBorder(Graphics g)
	{
		super.draw(g);
		Rectangle r = displayBox();
		Figure f = figureAt(0);
		Rectangle rf = f.displayBox();
		g.setColor(Color.gray);
		g.drawLine(r.x, r.y + rf.height + 2, r.x + r.width, r.y + rf.height + 2);
		g.setColor(Color.white);
		g.drawLine(r.x, r.y + rf.height + 3, r.x + r.width, r.y + rf.height + 3);
		g.setColor(Color.white);
		g.drawLine(r.x, r.y, r.x, r.y + r.height);
		g.drawLine(r.x, r.y, r.x + r.width, r.y);
		g.setColor(Color.gray);
		g.drawLine(r.x + r.width, r.y, r.x + r.width, r.y + r.height);
		g.drawLine(r.x, r.y + r.height, r.x + r.width, r.y + r.height);
	}

	public void draw(Graphics g)
	{
		drawBorder(g);
		super.draw(g);
	}

	public Vector handles()
	{
		Vector handles = new Vector();
		handles.addElement(new NullHandle(this, RelativeLocator.northWest()));
		handles.addElement(new NullHandle(this, RelativeLocator.northEast()));
		handles.addElement(new NullHandle(this, RelativeLocator.southWest()));
		handles.addElement(new NullHandle(this, RelativeLocator.southEast()));
		return addHandles(handles);
	}

	protected abstract Vector addHandles(Vector vector);

	public void initialize()
	{
		fDisplayBox = new Rectangle(0, 0, 0, 0);
		Font fb = new Font("Helvetica", 1, 12);
		TextFigure type = new TextFigure();
		type.setFont(fb);
		type.setText((String)getParam("type"));
		type.setAttribute("TextColor", Color.white);
		type.setReadOnly(true);
		add(type);
		initContent();
	}

	protected abstract void initContent();

	private void layout()
	{
		Point partOrigin = new Point(fDisplayBox.x, fDisplayBox.y);
		partOrigin.translate(3, 3);
		Dimension extent = new Dimension(0, 0);
		for (FigureEnumeration k = figures(); k.hasMoreElements();)
		{
			Figure f = k.nextFigure();
			Dimension partExtent = f.size();
			Point corner = new Point(partOrigin.x + partExtent.width, partOrigin.y + partExtent.height);
			f.basicDisplayBox(partOrigin, corner);
			extent.width = Math.max(extent.width, partExtent.width);
			extent.height += partExtent.height;
			partOrigin.y += partExtent.height;
		}

		fDisplayBox.width = extent.width + 6;
		fDisplayBox.height = extent.height + 6;
	}

	private boolean needsLayout()
	{
		Dimension extent = new Dimension(0, 0);
		for (FigureEnumeration k = figures(); k.hasMoreElements();)
		{
			Figure f = k.nextFigure();
			extent.width = Math.max(extent.width, f.size().width);
		}

		int newExtent = extent.width + 6;
		return newExtent != fDisplayBox.width;
	}

	public void update(FigureChangeEvent e)
	{
		if (needsLayout())
		{
			layout();
			changed();
		}
	}

	public void update()
	{
		for (FigureEnumeration k = figures(); k.hasMoreElements();)
		{
			Figure f = k.nextFigure();
			if (f instanceof UpdatableFigure)
			{
				UpdatableFigure uf = (UpdatableFigure)f;
				uf.update();
			}
		}

		layout();
		changed();
	}

	public void figureChanged(FigureChangeEvent e)
	{
		update(e);
	}

	public void figureRemoved(FigureChangeEvent e)
	{
		update(e);
	}

	public void notifyPostConn()
	{
	}

	public void write(StorableOutput dw)
	{
		super.write(dw);
		dw.writeInt(fDisplayBox.x);
		dw.writeInt(fDisplayBox.y);
		dw.writeInt(fDisplayBox.width);
		dw.writeInt(fDisplayBox.height);
	}

	public void read(StorableInput dr)
		throws IOException
	{
		super.read(dr);
		fDisplayBox = new Rectangle(dr.readInt(), dr.readInt(), dr.readInt(), dr.readInt());
		layout();
	}

	public Insets connectionInsets()
	{
		Rectangle r = fDisplayBox;
		int cx = r.width / 2;
		int cy = r.height / 2;
		return new Insets(cy, cx, cy, cx);
	}

	protected Object createLayer()
	{
		Object ly;
		Class cLayer = Class.forName((String)getParam("class"));
		ly = cLayer.newInstance();
		return ly;
		ClassNotFoundException cnfe;
		cnfe;
		String msg = (new StringBuilder("ClassNotFoundException  was thrown. Message is : ")).append(cnfe.getMessage()).toString();
		log.warn(msg, cnfe);
		break MISSING_BLOCK_LABEL_123;
		InstantiationException ie;
		ie;
		String msg = (new StringBuilder("InstantiationException was thrown. Message is : ")).append(ie.getMessage()).toString();
		log.warn(msg, ie);
		break MISSING_BLOCK_LABEL_123;
		IllegalAccessException iae;
		iae;
		String msg = (new StringBuilder("IllegalAccessException was thrown. Message is : ")).append(iae.getMessage()).toString();
		log.warn(msg, iae);
		return null;
	}

	public Object getParam(Object key)
	{
		return params.get(key);
	}

	public void setParam(Object key, Object newParam)
	{
		params.put(key, newParam);
	}

	public void setParams(Hashtable newParams)
	{
		params = newParams;
	}

	public abstract Wrapper getWrapper();

	public boolean canConnect(GenericFigure start, ConnectionFigure conn)
	{
		return false;
	}

	public abstract Object getLayer();

	public JPopupMenu getPopupMenu(final PropertySheet ps, final DrawingView view)
	{
		JPopupMenu jpm = new JPopupMenu();
		JMenuItem jmi = new JMenuItem("Properties");
		jmi.addActionListener(new ActionListener() {

			final ConcreteGenericFigure this$0;
			private final PropertySheet val$ps;

			public void actionPerformed(ActionEvent e)
			{
				if (ps != null)
				{
					Wrapper wrap = getWrapper();
					ps.setTarget(wrap);
					ps.pack();
					if (ps.getSize().width < 250)
						ps.setSize(new Dimension(250, ps.getSize().height));
					ps.show();
				}
			}

			
			{
				this$0 = ConcreteGenericFigure.this;
				ps = propertysheet;
				super();
			}
		});
		jpm.add(jmi);
		jmi = new JMenuItem("Delete");
		jmi.addActionListener(new ActionListener() {

			final ConcreteGenericFigure this$0;
			private final DrawingView val$view;

			public void actionPerformed(ActionEvent e)
			{
				(new DeleteCommand("Delete", view)).execute();
			}

			
			{
				this$0 = ConcreteGenericFigure.this;
				view = drawingview;
				super();
			}
		});
		jpm.add(jmi);
		if (getWrapper().getBean() instanceof Inspectable)
		{
			jmi = new JMenuItem("Inspect");
			jmi.addActionListener(new ActionListener() {

				final ConcreteGenericFigure this$0;

				public void actionPerformed(ActionEvent e)
				{
					Inspectable inspectable = (Inspectable)getWrapper().getBean();
					InspectionFrame iFrame = new InspectionFrame(inspectable);
					iFrame.show();
				}

			
			{
				this$0 = ConcreteGenericFigure.this;
				super();
			}
			});
			jpm.add(jmi);
		}
		return jpm;
	}

}
