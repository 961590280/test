// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NeuralNetDrawing.java

package org.joone.edit;

import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.standard.StandardDrawing;
import org.joone.engine.Layer;
import org.joone.engine.learning.ComparingElement;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.net.NeuralNet;
import org.joone.util.MonitorPlugin;

// Referenced classes of package org.joone.edit:
//			TeacherLayerFigure, LayerFigure, MonitorPluginFigure

public class NeuralNetDrawing extends StandardDrawing
{

	private NeuralNet layers;
	private static final long serialVersionUID = 0x3c65728acbcf7e2eL;

	public NeuralNetDrawing()
	{
		layers = new NeuralNet();
	}

	public Figure remove(Figure figure)
	{
		Figure retValue = super.remove(figure);
		if (figure instanceof TeacherLayerFigure)
		{
			if (((TeacherLayerFigure)figure).getOutputLayer() instanceof TeachingSynapse)
				layers.setTeacher(null);
		} else
		if (figure instanceof LayerFigure)
		{
			LayerFigure lFigure = (LayerFigure)figure;
			Layer layer = (Layer)lFigure.getLayer();
			if (layer != null)
				layers.removeLayer(layer);
		} else
		if (figure instanceof MonitorPluginFigure)
		{
			MonitorPluginFigure mpFigure = (MonitorPluginFigure)figure;
			MonitorPlugin layer = (MonitorPlugin)mpFigure.getLayer();
			if (layer != null)
				layers.removeNeuralNetListener(layer);
		}
		return retValue;
	}

	public Figure add(Figure figure)
	{
		Figure retValue = super.add(figure);
		if (figure instanceof TeacherLayerFigure)
		{
			TeacherLayerFigure tlFigure = (TeacherLayerFigure)figure;
			ComparingElement ts = (ComparingElement)tlFigure.getOutputLayer();
			if (ts != null && (ts instanceof TeachingSynapse))
				layers.setTeacher(ts);
		} else
		if (figure instanceof LayerFigure)
		{
			LayerFigure lFigure = (LayerFigure)figure;
			Layer layer = (Layer)lFigure.getLayer();
			if (layer != null)
				layers.addLayer(layer);
		} else
		if (figure instanceof MonitorPluginFigure)
		{
			MonitorPluginFigure mpFigure = (MonitorPluginFigure)figure;
			MonitorPlugin layer = (MonitorPlugin)mpFigure.getLayer();
			if (layer != null)
				layers.addNeuralNetListener(layer);
		}
		return retValue;
	}

	public void replace(Figure figure, Figure figure1)
	{
		super.replace(figure, figure1);
	}

	public NeuralNet getNeuralNet()
	{
		return layers;
	}

	public void setNeuralNet(NeuralNet newLayers)
	{
		layers = newLayers;
	}
}
