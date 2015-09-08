// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LearningSwitchLayerFigure.java

package org.joone.edit;

import CH.ifa.draw.figures.TextFigure;
import CH.ifa.draw.standard.ConnectionHandle;
import CH.ifa.draw.standard.RelativeLocator;
import java.awt.Color;
import java.awt.Font;
import java.util.Vector;
import org.joone.engine.InputPatternListener;
import org.joone.io.StreamInputSynapse;
import org.joone.util.LearningSwitch;

// Referenced classes of package org.joone.edit:
//			InputLayerFigure, InputLayerConnection, SpecialConnectionHandle, ValidationLayerConnection, 
//			LayerFigure, UpdatableTextFigure

public class LearningSwitchLayerFigure extends InputLayerFigure
{

	private static final long serialVersionUID = 0x9e044ca47a5ff99dL;

	public LearningSwitchLayerFigure()
	{
	}

	protected void initContent()
	{
		fPostConn = new Vector();
		fPreConn = new Vector();
		Font fb = new Font("Helvetica", 1, 12);
		myInputLayer = (StreamInputSynapse)createLayer();
		TextFigure name = new UpdatableTextFigure() {

			final LearningSwitchLayerFigure this$0;

			public void setText(String newText)
			{
				super.setText(newText);
				getInputLayer().setName(newText);
			}

			public void update()
			{
				setText(getInputLayer().getName());
			}

			
			{
				this$0 = LearningSwitchLayerFigure.this;
				super();
			}
		};
		name.setFont(fb);
		name.setText((new StringBuilder("LearningSwitch ")).append(++numLayers).toString());
		name.setAttribute("TextColor", Color.blue);
		add(name);
	}

	protected Vector addHandles(Vector handles)
	{
		handles.addElement(new ConnectionHandle(this, RelativeLocator.east(), new InputLayerConnection()));
		handles.addElement(new SpecialConnectionHandle(this, RelativeLocator.north(), new ValidationLayerConnection(), Color.red));
		return handles;
	}

	public boolean addPreConn(LayerFigure layerFigure, InputPatternListener synapse)
	{
		boolean ret = super.addPreConn(layerFigure, synapse);
		LearningSwitch ls = (LearningSwitch)getInputLayer();
		ls.addTrainingSet((StreamInputSynapse)synapse);
		return ret;
	}

	public void addPreValConn(LayerFigure layerFigure, InputPatternListener synapse)
	{
		super.addPreConn(layerFigure, synapse);
		LearningSwitch ls = (LearningSwitch)getInputLayer();
		ls.addValidationSet((StreamInputSynapse)synapse);
	}

	public void removePreConn(LayerFigure figure, InputPatternListener conn)
	{
		removePreConn(figure);
		LearningSwitch ls = (LearningSwitch)getInputLayer();
		ls.removeTrainingSet();
	}

	public void removePreValConn(LayerFigure figure, InputPatternListener conn)
	{
		removePreConn(figure);
		LearningSwitch ls = (LearningSwitch)getInputLayer();
		ls.removeValidationSet();
	}
}
