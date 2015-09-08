// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NeuralNetFactory.java

package org.joone.helpers.structure;

import java.io.*;
import java.util.StringTokenizer;
import org.joone.engine.Layer;
import org.joone.engine.Monitor;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.helpers.factory.JooneTools;
import org.joone.io.*;
import org.joone.net.NeuralNet;
import org.joone.util.NormalizerPlugIn;

public class NeuralNetFactory
{

	public static final int CLASSIFICATION = 1;
	public static final int ONEOFC_CLASSIF = 2;
	public static final int APPROXIMIMATION = 3;
	public static final int PREDICTION = 4;
	public static final int CLUSTERING = 5;
	private int type;
	private String totInputCols;
	private String inputFileName;
	private String inputCols;
	private boolean skipFirstInputRow;
	private String desiredFileName;
	private String desiredCols;
	private boolean skipFirstDesiredRow;
	private int taps;
	private int predictLength;
	private int mapWidth;
	private int mapHeight;
	private int numPlugin;

	public NeuralNetFactory()
	{
		numPlugin = 0;
	}

	public NeuralNet getNeuralNetwork()
	{
		return getNeuralNetwork(true);
	}

	public NeuralNet getNeuralNetwork(boolean createIO)
	{
		NeuralNet nnet = null;
		switch (getType())
		{
		case 1: // '\001'
		case 2: // '\002'
		case 3: // '\003'
			nnet = createFFNN();
			break;

		case 4: // '\004'
			nnet = createTimeSeries();
			break;

		case 5: // '\005'
			nnet = createKohonen();
			break;
		}
		if (createIO)
			attach_IO(nnet);
		return nnet;
	}

	public NeuralNet createFFNN()
	{
		int inputRows = getNumOfColumns(inputCols);
		int outputRows = getNumOfColumns(desiredCols);
		int nodes[] = {
			inputRows, inputRows, outputRows
		};
		int outputType = 1;
		switch (getType())
		{
		case 1: // '\001'
			outputType = 2;
			break;

		case 2: // '\002'
			outputType = 3;
			break;

		case 3: // '\003'
			outputType = 1;
			break;
		}
		NeuralNet nnet = JooneTools.create_standard(nodes, outputType);
		Monitor mon = nnet.getMonitor();
		mon.setTotCicles(5000);
		mon.setTrainingPatterns(getNumOfRows(inputFileName, skipFirstInputRow));
		mon.setLearning(true);
		return nnet;
	}

	public NeuralNet createKohonen()
	{
		int inputRows = getNumOfColumns(inputCols);
		int outputRows = 10;
		int nodes[] = {
			inputRows, getMapWidth(), getMapHeight()
		};
		int outputType = 4;
		NeuralNet nnet = JooneTools.create_unsupervised(nodes, outputType);
		Monitor mon = nnet.getMonitor();
		mon.setTotCicles(5000);
		mon.setTrainingPatterns(getNumOfRows(inputFileName, skipFirstInputRow));
		mon.setLearning(true);
		return nnet;
	}

	public NeuralNet createTimeSeries()
	{
		int inputRows = getNumOfColumns(inputCols);
		int outputRows = getNumOfColumns(desiredCols);
		int nodes[] = {
			inputRows, getTaps(), outputRows
		};
		int outputType = 1;
		NeuralNet nnet = JooneTools.create_timeDelay(nodes, getTaps() - 1, outputType);
		Monitor mon = nnet.getMonitor();
		mon.setTotCicles(5000);
		mon.setTrainingPatterns(getNumOfRows(inputFileName, skipFirstInputRow) - getPredictionLength());
		mon.setLearning(true);
		mon.setPreLearning(getTaps());
		return nnet;
	}

	protected void attach_IO(NeuralNet nnet)
	{
		Object inputs[] = create_IO();
		InputConnector inputConn = (InputConnector)inputs[0];
		nnet.getInputLayer().addInputSynapse(inputConn);
		if (inputs[1] != null)
		{
			InputConnector targetConn = (InputConnector)inputs[1];
			TeachingSynapse teacher = createTeacher(targetConn);
			nnet.getOutputLayer().addOutputSynapse(teacher);
			nnet.setTeacher(teacher);
		}
	}

	public Object[] create_IO()
	{
		Object inputs[] = new Object[2];
		StreamInputSynapse inputData = createInputSynapse(true);
		InputConnector inputConn = createInputConnector(getInputCols(), 1, 0);
		inputConn.setInputSynapse(inputData);
		inputs[0] = inputConn;
		if (getType() != 5)
		{
			StreamInputSynapse targetData = null;
			InputConnector targetConn = createInputConnector(getDesiredCols(), 1, 0);
			if (!getInputFileName().equalsIgnoreCase(getDesiredFileName()))
			{
				targetData = createTargetSynapse(true);
				targetConn.setInputSynapse(targetData);
			} else
			{
				if (getPredictionLength() > 0)
					targetConn.setFirstRow(inputConn.getFirstRow() + getPredictionLength());
				targetConn.setInputSynapse(inputData);
			}
			inputs[1] = targetConn;
		}
		return inputs;
	}

	public TeachingSynapse createTeacher(StreamInputSynapse desired)
	{
		TeachingSynapse teacher = new TeachingSynapse();
		teacher.setName("Teacher");
		teacher.setDesired(desired);
		return teacher;
	}

	public StreamInputSynapse createInputSynapse(boolean normalized)
	{
		String cols = getTotInputCols();
		if (!getInputFileName().equalsIgnoreCase(getDesiredFileName()))
			cols = getInputCols();
		StreamInputSynapse inputData = createInput(inputFileName, cols, skipFirstInputRow ? 2 : 1, 0, normalized);
		return inputData;
	}

	public StreamInputSynapse createTargetSynapse(boolean normalized)
	{
		StreamInputSynapse inputData = createInput(desiredFileName, getDesiredCols(), skipFirstDesiredRow ? 2 : 1, 0, normalized);
		return inputData;
	}

	public InputConnector createInputConnector(String inputCols, int firstRow, int lastRow)
	{
		numPlugin++;
		InputConnector input = new InputConnector();
		input.setName((new StringBuilder("View ")).append(numPlugin).toString());
		input.setAdvancedColumnSelector(inputCols);
		input.setFirstRow(firstRow);
		input.setLastRow(lastRow);
		return input;
	}

	public StreamInputSynapse createInput(String fileName, String columns, int firstRow, int lastRow, boolean normalized)
	{
		numPlugin++;
		FileInputSynapse in = new FileInputSynapse();
		in.setInputFile(new File(fileName));
		in.setAdvancedColumnSelector(columns);
		in.setFirstRow(firstRow);
		in.setLastRow(lastRow);
		in.setName((new StringBuilder("Input Data ")).append(numPlugin).toString());
		if (normalized)
		{
			NormalizerPlugIn norm = new NormalizerPlugIn();
			norm.setName((new StringBuilder("Input PlugIn ")).append(numPlugin).toString());
			int cols = getNumOfColumns(columns);
			norm.setAdvancedSerieSelector((new StringBuilder("1-")).append(cols).toString());
			in.addPlugIn(norm);
		}
		return in;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getInputFileName()
	{
		return inputFileName;
	}

	public void setInputFileName(String inputFileName)
	{
		this.inputFileName = inputFileName;
	}

	public String getInputCols()
	{
		return inputCols;
	}

	public void setInputCols(String inputCols)
	{
		this.inputCols = inputCols;
	}

	public boolean isSkipFirstInputRow()
	{
		return skipFirstInputRow;
	}

	public void setSkipFirstInputRow(boolean skipFirstInputRow)
	{
		this.skipFirstInputRow = skipFirstInputRow;
	}

	public String getDesiredFileName()
	{
		return desiredFileName;
	}

	public void setDesiredFileName(String desiredFileName)
	{
		this.desiredFileName = desiredFileName;
	}

	public String getDesiredCols()
	{
		return desiredCols;
	}

	public void setDesiredCols(String desiredCols)
	{
		this.desiredCols = desiredCols;
	}

	public boolean isSkipFirstDesiredRow()
	{
		return skipFirstDesiredRow;
	}

	public void setSkipFirstDesiredRow(boolean skipFirstDesiredRow)
	{
		this.skipFirstDesiredRow = skipFirstDesiredRow;
	}

	protected int getNumOfColumns(String columns)
	{
		int c = 0;
		StringTokenizer tokens = new StringTokenizer(columns, ",");
		int n = tokens.countTokens();
		for (int i = 0; i < n; i++)
		{
			String t = tokens.nextToken();
			if (t.indexOf('-') == -1)
			{
				c++;
			} else
			{
				StringTokenizer tt = new StringTokenizer(t, "-");
				int low = Integer.valueOf(tt.nextToken()).intValue();
				int hig = Integer.valueOf(tt.nextToken()).intValue();
				c += (hig - low) + 1;
			}
		}

		return c;
	}

	protected int getNumOfRows(String fileName, boolean skipFirstLine)
	{
		int c;
		BufferedReader file;
		c = 0;
		file = null;
		try
		{
			file = new BufferedReader(new FileReader(fileName));
			if (skipFirstLine)
				file.readLine();
			while (file.readLine() != null) 
				c++;
			break MISSING_BLOCK_LABEL_94;
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		if (file != null)
			try
			{
				file.close();
			}
			catch (IOException ioexception) { }
		break MISSING_BLOCK_LABEL_109;
		Exception exception;
		exception;
		if (file != null)
			try
			{
				file.close();
			}
			catch (IOException ioexception1) { }
		throw exception;
		if (file != null)
			try
			{
				file.close();
			}
			catch (IOException ioexception2) { }
		return c;
	}

	public int getTaps()
	{
		return taps;
	}

	public void setTaps(int taps)
	{
		this.taps = taps;
	}

	public int getPredictionLength()
	{
		return predictLength;
	}

	public void setPredictionLength(int predictLength)
	{
		this.predictLength = predictLength;
	}

	public int getMapWidth()
	{
		return mapWidth;
	}

	public void setMapWidth(int mapWidth)
	{
		this.mapWidth = mapWidth;
	}

	public int getMapHeight()
	{
		return mapHeight;
	}

	public void setMapHeight(int mapHeight)
	{
		this.mapHeight = mapHeight;
	}

	public String getTotInputCols()
	{
		return totInputCols;
	}

	public void setTotInputCols(String totInputCols)
	{
		this.totInputCols = totInputCols;
	}
}
