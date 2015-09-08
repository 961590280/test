// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NeuralNetAttributes.java

package org.joone.net;

import java.io.Serializable;

public class NeuralNetAttributes
	implements Serializable
{

	private static final long serialVersionUID = 0xd4a94c3d506a3586L;
	private double validationError;
	private double trainingError;
	private String neuralNetName;
	private int lastEpoch;

	public NeuralNetAttributes()
	{
		validationError = -1D;
		trainingError = -1D;
		lastEpoch = 0;
	}

	public double getTrainingError()
	{
		return trainingError;
	}

	public void setTrainingError(double trainingError)
	{
		this.trainingError = trainingError;
	}

	public double getValidationError()
	{
		return validationError;
	}

	public void setValidationError(double validationError)
	{
		this.validationError = validationError;
	}

	public String getNeuralNetName()
	{
		return neuralNetName;
	}

	public void setNeuralNetName(String neuralNetName)
	{
		this.neuralNetName = neuralNetName;
	}

	public int getLastEpoch()
	{
		return lastEpoch;
	}

	public void setLastEpoch(int lastEpoch)
	{
		this.lastEpoch = lastEpoch;
	}
}
