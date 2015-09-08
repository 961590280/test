// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LayerConnection.java

package org.joone.edit;

import CH.ifa.draw.figures.*;
import CH.ifa.draw.framework.*;
import CH.ifa.draw.standard.DeleteCommand;
import CH.ifa.draw.standard.NullHandle;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.joone.edit.inspection.InspectionFrame;
import org.joone.engine.Synapse;
import org.joone.inspection.Inspectable;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.edit:
//			GenericFigure, UpdatableFigure, LayerFigure, OutputLayerFigure, 
//			ConcreteGenericFigure, Wrapper, PropertySheet

public class LayerConnection extends LineConnection
	implements GenericFigure, UpdatableFigure
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/edit/LayerConnection);
	private static final long serialVersionUID = 0xea49bf14204414eeL;
	private Hashtable params;
	protected Synapse mySynapse;

	public LayerConnection()
	{
		setEndDecoration(new ArrowTip());
		setStartDecoration(null);
		params = new Hashtable();
	}

	public void handleConnect(Figure start, Figure end)
	{
		LayerFigure source = (LayerFigure)start;
		LayerFigure target = (LayerFigure)end;
		if (end instanceof OutputLayerFigure)
		{
			OutputLayerFigure olf = (OutputLayerFigure)target;
			org.joone.engine.OutputPatternListener myListener = olf.getOutputLayer();
			olf.addPreConn(source);
			source.addPostConn(target, myListener);
			source.notifyPostConn();
		} else
		{
			mySynapse = (Synapse)createSynapse();
			boolean newConn = target.addPreConn(source, mySynapse);
			source.addPostConn(target, mySynapse);
			source.notifyPostConn();
			if (newConn && source.hasCycle(target))
			{
				setAttribute("FrameColor", Color.red);
				mySynapse.setLoopBack(true);
			}
		}
	}

	public void handleDisconnect(Figure start, Figure end)
	{
		LayerFigure source = (LayerFigure)start;
		LayerFigure target = (LayerFigure)end;
		if (target != null)
			target.removePreConn(source, getSynapse());
		if (source != null)
			if (target instanceof OutputLayerFigure)
				source.removePostConn(target, ((OutputLayerFigure)target).getOutputLayer());
			else
				source.removePostConn(target, getSynapse());
	}

	public boolean canConnect(Figure start, Figure end)
	{
		boolean retVal = (start instanceof ConcreteGenericFigure) && (end instanceof ConcreteGenericFigure);
		if (retVal)
		{
			ConcreteGenericFigure fEnd = (ConcreteGenericFigure)end;
			ConcreteGenericFigure fStart = (ConcreteGenericFigure)start;
			retVal = retVal && fEnd.canConnect(fStart, this);
		}
		return retVal;
	}

	public Vector handles()
	{
		Vector handles = super.handles();
		handles.setElementAt(new NullHandle(this, PolyLineFigure.locator(0)), 0);
		return handles;
	}

	protected Object createSynapse()
	{
		Object ly;
		String cl = (String)getParam("class");
		if (cl == null)
			cl = "org.joone.engine.FullSynapse";
		Class cLayer = Class.forName(cl);
		ly = cLayer.newInstance();
		return ly;
		ClassNotFoundException cnfe;
		cnfe;
		log.warn((new StringBuilder("ClassNotFoundException thrown. Message is : ")).append(cnfe.getMessage()).toString(), cnfe);
		break MISSING_BLOCK_LABEL_122;
		InstantiationException ie;
		ie;
		log.warn((new StringBuilder("InstantiationException thrown. Message is : ")).append(ie.getMessage()).toString(), ie);
		break MISSING_BLOCK_LABEL_122;
		IllegalAccessException iae;
		iae;
		log.warn((new StringBuilder("IllegalAccessException thrown. Message is : ")).append(iae.getMessage()).toString(), iae);
		return null;
	}

	public Synapse getSynapse()
	{
		return mySynapse;
	}

	public void setSynapse(Synapse newMySynapse)
	{
		mySynapse = newMySynapse;
	}

	public Object getParam(Object key)
	{
		if (params == null)
			return null;
		else
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

	public Wrapper getWrapper()
	{
		Synapse syn = getSynapse();
		if (syn != null)
			return new Wrapper(this, getSynapse(), getSynapse().getName());
		else
			return null;
	}

	public void update()
	{
	}

	public boolean canConnect(GenericFigure start, ConnectionFigure conn)
	{
		return true;
	}

	public void draw(Graphics g)
	{
		Object oldColor = getAttribute("FrameColor");
		if (mySynapse != null && mySynapse.isLoopBack())
			setAttribute("FrameColor", Color.red);
		else
			setAttribute("FrameColor", oldColor);
		super.draw(g);
		String lbl = (String)getParam("label");
		Synapse syn = getSynapse();
		if (lbl != null && syn != null)
		{
			int i = fPoints.size();
			Point p1 = (Point)fPoints.elementAt(0);
			Point p2 = (Point)fPoints.elementAt(i - 1);
			int xMin = p1.x;
			int xMax = p2.x;
			if (xMin < p2.x)
			{
				xMin = p2.x;
				xMax = p1.x;
			}
			int yMin = p1.y;
			int yMax = p2.y;
			if (yMin < p2.y)
			{
				yMin = p2.y;
				yMax = p1.y;
			}
			Point p = new Point();
			p.x = xMin + (xMax - xMin) / 2;
			p.y = yMin + (yMax - yMin) / 2;
			if (syn.isEnabled())
				g.setColor(new Color(255, 255, 255));
			else
				g.setColor(new Color(170, 170, 170));
			g.fillRect(p.x - 6, p.y - 7, 12, 14);
			g.setColor(new Color(0, 0, 200));
			g.drawRect(p.x - 6, p.y - 7, 12, 14);
			g.drawString(lbl, p.x - 4, p.y + 5);
			g.setColor(getFrameColor());
		}
	}

	public JPopupMenu getPopupMenu(final PropertySheet ps, final DrawingView view)
	{
		JPopupMenu jpm = new JPopupMenu();
		final Wrapper wrap = getWrapper();
		JMenuItem jmi;
		if (wrap != null)
		{
			jmi = new JMenuItem("Properties");
			jmi.addActionListener(new ActionListener() {

				final LayerConnection this$0;
				private final PropertySheet val$ps;
				private final Wrapper val$wrap;

				public void actionPerformed(ActionEvent e)
				{
					if (ps != null)
					{
						ps.setTarget(wrap);
						ps.pack();
						if (ps.getSize().width < 250)
							ps.setSize(new Dimension(250, ps.getSize().height));
						ps.show();
					}
				}

			
			{
				this$0 = LayerConnection.this;
				ps = propertysheet;
				wrap = wrapper;
				super();
			}
			});
			jpm.add(jmi);
		}
		jmi = new JMenuItem("Delete");
		jmi.addActionListener(new ActionListener() {

			final LayerConnection this$0;
			private final DrawingView val$view;

			public void actionPerformed(ActionEvent e)
			{
				(new DeleteCommand("Delete", view)).execute();
			}

			
			{
				this$0 = LayerConnection.this;
				view = drawingview;
				super();
			}
		});
		jpm.add(jmi);
		if (wrap != null && (getWrapper().getBean() instanceof Inspectable))
		{
			jmi = new JMenuItem("Inspect");
			jmi.addActionListener(new ActionListener() {

				final LayerConnection this$0;

				public void actionPerformed(ActionEvent e)
				{
					Inspectable inspectable = (Inspectable)getWrapper().getBean();
					InspectionFrame iFrame = new InspectionFrame(inspectable);
					iFrame.show();
				}

			
			{
				this$0 = LayerConnection.this;
				super();
			}
			});
			jpm.add(jmi);
		}
		return jpm;
	}

}
