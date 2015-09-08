// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NeuralNetRunner.java

package org.joone.util;

import java.io.*;
import org.joone.engine.*;
import org.joone.net.NeuralNet;
import org.joone.net.NeuralNetLoader;

public class NeuralNetRunner
	implements NeuralNetListener
{

	public NeuralNet nnet;
	public int result;
	private String snetFileName;
	private String snetOutputFileName;
	private long lPrintCicle;

	public NeuralNetRunner()
	{
		result = 0;
		lPrintCicle = 1000L;
	}

	public static void main(String args[])
	{
		NeuralNetRunner nnRunner = new NeuralNetRunner();
		if (args.length < 1)
		{
			System.out.println("Usage: java NeuralNetRunner -snet <snetFile> [-printcicle <integer>] -snetout <snetOutputFile");
			System.out.println("where <snetFile> is the Serialized Output from Joone Edit");
			System.out.println("where <integer> is the Multiple of cicles output should be printed to standard output");
			System.out.println("where <snetOutputFile> is filename for NeuralNetRunner to save the NeurlalNet as it is processing.");
			System.exit(1);
		} else
		{
			for (int n = 0; n < args.length; n++)
				if (args[n].equals("-snet"))
					nnRunner.snetFileName = args[++n];
				else
				if (args[n].equals("-printcicle"))
					nnRunner.lPrintCicle = Long.parseLong(args[++n]);
				else
				if (args[n].equals("-snetout"))
					nnRunner.snetOutputFileName = args[++n];
				else
					throw new IllegalArgumentException("Unknown argument.");

		}
		if (nnRunner.snetFileName == null)
		{
			System.out.println("ERROR: A snet input parameter is required to run");
			System.exit(1);
		} else
		if (nnRunner.snetFileName.equals(nnRunner.snetOutputFileName))
		{
			System.out.println("ERROR: The output snet should not be the same as the input snet .");
			System.exit(1);
		}
		NeuralNetLoader nnl = new NeuralNetLoader(nnRunner.snetFileName);
		nnRunner.setNnet(nnl.getNeuralNet());
		nnRunner.execute();
	}

	public void execute()
	{
		if (nnet != null)
		{
			nnet.getMonitor().addNeuralNetListener(this);
			nnet.start();
			nnet.getMonitor().Go();
			synchronized (this)
			{
				try
				{
					while (result == 0) 
						wait();
				}
				catch (InterruptedException ie)
				{
					ie.printStackTrace();
				}
			}
		} else
		{
			throw new RuntimeException("Can't work: the neural net is null");
		}
	}

	public void cicleTerminated(NeuralNetEvent e)
	{
		Monitor mon = (Monitor)e.getSource();
		long c = mon.getCurrentCicle();
		long cl = c / lPrintCicle;
		if (cl * lPrintCicle == c)
		{
			System.out.println((new StringBuilder(String.valueOf(c))).append(" cycles remaining - Error = ").append(mon.getGlobalError()).toString());
			writeNnet();
		}
	}

	public void netStopped(NeuralNetEvent e)
	{
		synchronized (this)
		{
			result = 1;
			notifyAll();
		}
	}

	public void writeNnet()
	{
		if (snetOutputFileName != null)
			try
			{
				FileOutputStream stream = new FileOutputStream(snetOutputFileName);
				ObjectOutput output = new ObjectOutputStream(stream);
				output.writeObject(nnet);
				output.close();
			}
			catch (IOException ioe)
			{
				System.err.println((new StringBuilder("Error writing nnet: ")).append(ioe).toString());
			}
	}

	public NeuralNet getNnet()
	{
		return nnet;
	}

	public void setNnet(NeuralNet nnet)
	{
		this.nnet = nnet;
	}

	public void resetNnet()
	{
		nnet.resetInput();
	}

	public void netStarted(NeuralNetEvent e)
	{
		System.out.println("Running...");
	}

	public void errorChanged(NeuralNetEvent neuralnetevent)
	{
	}

	public void netStoppedError(NeuralNetEvent neuralnetevent, String s)
	{
	}
}
