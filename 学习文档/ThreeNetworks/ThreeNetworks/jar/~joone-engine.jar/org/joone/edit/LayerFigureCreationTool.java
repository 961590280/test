// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LayerFigureCreationTool.java

package org.joone.edit;

import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.standard.CreationTool;
import java.util.Hashtable;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.edit:
//			ConcreteGenericFigure, NeuralNetDrawing

public class LayerFigureCreationTool extends CreationTool
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/edit/LayerFigureCreationTool);
	protected Hashtable params;
	String objType;
	DrawingView view;

	public LayerFigureCreationTool(DrawingView view, String type)
	{
		super(view);
		params = new Hashtable();
		objType = type;
		this.view = view;
	}

	protected Figure createFigure()
	{
		ConcreteGenericFigure lf;
		Class cLayer = Class.forName(objType);
		lf = (ConcreteGenericFigure)cLayer.newInstance();
		lf.setParams(params);
		NeuralNetDrawing nnd = (NeuralNetDrawing)view.drawing();
		lf.setParam("NeuralNet", nnd.getNeuralNet());
		lf.initialize();
		return lf;
		ClassNotFoundException cnfe;
		cnfe;
		log.warn((new StringBuilder("ClassNotFoundException thrown. Message is : ")).append(cnfe.getMessage()).toString(), cnfe);
		break MISSING_BLOCK_LABEL_146;
		InstantiationException ie;
		ie;
		log.warn((new StringBuilder("InstantiationException thrown. Message is : ")).append(ie.getMessage()).toString(), ie);
		break MISSING_BLOCK_LABEL_146;
		IllegalAccessException iae;
		iae;
		log.warn((new StringBuilder("IllegalAccessException thrown. Message is : ")).append(iae.getMessage()).toString(), iae);
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

}
