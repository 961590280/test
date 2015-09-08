// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NeuralNetLoader.java

package org.joone.net;

import java.io.*;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;

// Referenced classes of package org.joone.net:
//			NeuralNet

public class NeuralNetLoader
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/net/NeuralNetLoader);
	NeuralNet nnet;

	public NeuralNetLoader(String netName)
	{
		try
		{
			nnet = readNeuralNet(netName);
		}
		catch (Exception e)
		{
			log.error((new StringBuilder("Cannot create the NeuralNet with the following name : \"")).append(netName).append("\"").toString(), e);
		}
	}

	public NeuralNet getNeuralNet()
	{
		return nnet;
	}

	private NeuralNet readNeuralNet(String NeuralNet)
		throws IOException, ClassNotFoundException
	{
		if (NeuralNet == null)
			return null;
		if (NeuralNet.equals(""))
		{
			return null;
		} else
		{
			File NNFile = new File(NeuralNet);
			FileInputStream fin = new FileInputStream(NNFile);
			ObjectInputStream oin = new ObjectInputStream(fin);
			NeuralNet newNeuralNet = (NeuralNet)oin.readObject();
			oin.close();
			fin.close();
			return newNeuralNet;
		}
	}

}
