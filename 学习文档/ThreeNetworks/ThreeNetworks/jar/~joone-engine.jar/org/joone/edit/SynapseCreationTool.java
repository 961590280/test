// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SynapseCreationTool.java

package org.joone.edit;

import CH.ifa.draw.framework.ConnectionFigure;
import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.standard.ConnectionTool;
import java.util.Hashtable;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.edit:
//			LayerConnection, NeuralNetDrawing

public class SynapseCreationTool extends ConnectionTool
{

	public static final ILogger log = LoggerFactory.getLogger(org/joone/edit/SynapseCreationTool);
	protected Hashtable params;
	String objType;
	DrawingView view;

	public SynapseCreationTool(DrawingView view, String type)
	{
		super(view, null);
		params = new Hashtable();
		objType = type;
		this.view = view;
	}

	protected ConnectionFigure createConnection()
	{
		LayerConnection lc;
		Class cLayer = Class.forName(objType);
		lc = (LayerConnection)cLayer.newInstance();
		lc.setParams(params);
		NeuralNetDrawing nnd = (NeuralNetDrawing)view.drawing();
		lc.setParam("NeuralNet", nnd.getNeuralNet());
		return lc;
		ClassNotFoundException cnfe;
		cnfe;
		log.warn((new StringBuilder("ClassNotFoundException exception thrown while ConnectionFigure. Message is : ")).append(cnfe.getMessage()).toString(), cnfe);
		break MISSING_BLOCK_LABEL_142;
		InstantiationException ie;
		ie;
		log.warn((new StringBuilder("InstantiationException exception thrown while ConnectionFigure. Message is : ")).append(ie.getMessage()).toString(), ie);
		break MISSING_BLOCK_LABEL_142;
		IllegalAccessException iae;
		iae;
		log.warn((new StringBuilder("IllegalAccessException exception thrown while ConnectionFigure. Message is : ")).append(iae.getMessage()).toString(), iae);
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
