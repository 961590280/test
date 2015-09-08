// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NeuralNetFactory.java

package org.joone.samples.engine.validation;

import org.joone.engine.Layer;
import org.joone.engine.Synapse;
import org.joone.io.MemoryInputSynapse;
import org.joone.io.StreamInputSynapse;
import org.joone.util.LearningSwitch;

public class NeuralNetFactory
{

	public NeuralNetFactory()
	{
	}

	public static void connect(Layer ly1, Synapse syn, Layer ly2)
	{
		ly1.addOutputSynapse(syn);
		ly2.addInputSynapse(syn);
	}

	public static LearningSwitch createSwitch(String name, StreamInputSynapse IT, StreamInputSynapse IV)
	{
		LearningSwitch lsw = new LearningSwitch();
		lsw.setName(name);
		lsw.addTrainingSet(IT);
		lsw.addValidationSet(IV);
		return lsw;
	}

	public static MemoryInputSynapse createInput(String name, double inData[][], int firstRow, int firstCol, int lastCol)
	{
		MemoryInputSynapse input = new MemoryInputSynapse();
		input.setName(name);
		input.setInputArray(inData);
		input.setFirstRow(firstRow);
		if (firstCol != lastCol)
			input.setAdvancedColumnSelector((new StringBuilder(String.valueOf(firstCol))).append("-").append(lastCol).toString());
		else
			input.setAdvancedColumnSelector(Integer.toString(firstCol));
		return input;
	}
}
