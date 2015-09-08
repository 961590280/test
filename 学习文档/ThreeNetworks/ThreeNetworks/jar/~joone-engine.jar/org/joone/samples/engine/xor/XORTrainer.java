// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   XORTrainer.java

package org.joone.samples.engine.xor;

import java.io.*;
import java.util.Iterator;
import java.util.TreeSet;
import org.joone.engine.*;
import org.joone.net.NetCheck;
import org.joone.net.NeuralNet;

public class XORTrainer
	implements NeuralNetListener
{

	private static String xorNet = "org/joone/samples/engine/xor/xor.snet";

	public XORTrainer()
	{
	}

	public static void main(String args[])
	{
		XORTrainer xor = new XORTrainer();
		xor.Go(xorNet);
	}

	private void Go(String fileName)
	{
		NeuralNet xor = restoreNeuralNet(fileName);
		if (xor != null)
		{
			xor.getMonitor().addNeuralNetListener(this);
			xor.getMonitor().setLearning(true);
			TreeSet tree = xor.check();
			if (tree.isEmpty())
			{
				xor.go(true);
				System.out.println((new StringBuilder("Network stopped. Last RMSE=")).append(xor.getMonitor().getGlobalError()).toString());
			} else
			{
				NetCheck nc;
				for (Iterator it = tree.iterator(); it.hasNext(); System.out.println(nc.toString()))
					nc = (NetCheck)it.next();

			}
		}
	}

	private NeuralNet restoreNeuralNet(String fileName)
	{
		NeuralNet nnet = null;
		try
		{
			FileInputStream stream = new FileInputStream(fileName);
			ObjectInput input = new ObjectInputStream(stream);
			nnet = (NeuralNet)input.readObject();
		}
		catch (Exception e)
		{
			System.out.println((new StringBuilder("Exception was thrown. Message is : ")).append(e.getMessage()).toString());
		}
		return nnet;
	}

	public void cicleTerminated(NeuralNetEvent neuralnetevent)
	{
	}

	public void errorChanged(NeuralNetEvent e)
	{
		Monitor mon = (Monitor)e.getSource();
		long c = mon.getCurrentCicle();
		if (c % 200L == 0L)
			System.out.println((new StringBuilder(String.valueOf(c))).append(" epochs remaining - RMSE = ").append(mon.getGlobalError()).toString());
	}

	public void netStarted(NeuralNetEvent e)
	{
		System.out.println("Started...");
	}

	public void netStopped(NeuralNetEvent e)
	{
		System.out.println("Stopped...");
	}

	public void netStoppedError(NeuralNetEvent e, String error)
	{
		System.out.println((new StringBuilder("Error: ")).append(error).toString());
		System.exit(1);
	}

}
