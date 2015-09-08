// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   XOR_using_helpers.java

package org.joone.samples.engine.helpers;

import java.io.PrintStream;
import org.joone.engine.Monitor;
import org.joone.helpers.factory.JooneTools;
import org.joone.net.NeuralNet;

public class XOR_using_helpers
{

	private static double inputArray[][] = {
		{
			0.0D, 0.0D
		}, {
			0.0D, 1.0D
		}, {
			1.0D, 0.0D
		}, {
			1.0D, 1.0D
		}
	};
	private static double desiredArray[][] = {
		{
			0.0D
		}, {
			1.0D
		}, {
			1.0D
		}, {
			0.0D
		}
	};
	private static boolean singleThreadMode = true;

	public XOR_using_helpers()
	{
	}

	public static void main(String args[])
	{
		try
		{
			NeuralNet nnet = JooneTools.create_standard(new int[] {
				2, 2, 1
			}, 2);
			nnet.getMonitor().setSingleThreadMode(singleThreadMode);
			double rmse = JooneTools.train(nnet, inputArray, desiredArray, 5000, 0.01D, 200, System.out, false);
			try
			{
				Thread.sleep(50L);
			}
			catch (InterruptedException interruptedexception) { }
			System.out.println((new StringBuilder("Last RMSE = ")).append(rmse).toString());
			System.out.println("\nResults:");
			System.out.println("|Inp 1\t|Inp 2\t|Output");
			for (int i = 0; i < 4; i++)
			{
				double output[] = JooneTools.interrogate(nnet, inputArray[i]);
				System.out.print((new StringBuilder("| ")).append(inputArray[i][0]).append("\t| ").append(inputArray[i][1]).append("\t| ").toString());
				System.out.println(output[0]);
			}

			double testRMSE = JooneTools.test(nnet, inputArray, desiredArray);
			System.out.println((new StringBuilder("\nTest error = ")).append(testRMSE).toString());
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
	}

}
