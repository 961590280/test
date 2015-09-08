// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ScriptValidationSample.java

package org.joone.samples.engine.scripting;

import java.io.*;
import org.joone.engine.*;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.io.FileInputSynapse;
import org.joone.io.StreamInputSynapse;
import org.joone.net.NeuralNet;
import org.joone.script.MacroManager;
import org.joone.util.*;

public class ScriptValidationSample
{

	NeuralNet net;
	private static String filePath = "org/joone/samples/engine/scripting";

	public ScriptValidationSample()
	{
	}

	public static void main(String args[])
	{
		ScriptValidationSample sampleNet = new ScriptValidationSample();
		sampleNet.initialize(filePath);
		sampleNet.start();
	}

	private void initialize(String path)
	{
		LinearLayer ILayer = new LinearLayer();
		SigmoidLayer HLayer = new SigmoidLayer();
		SigmoidLayer OLayer = new SigmoidLayer();
		ILayer.setRows(13);
		HLayer.setRows(4);
		OLayer.setRows(1);
		FullSynapse synIH = new FullSynapse();
		FullSynapse synHO = new FullSynapse();
		connect(ILayer, synIH, HLayer);
		connect(HLayer, synHO, OLayer);
		FileInputSynapse ITdata = createInput((new StringBuilder(String.valueOf(path))).append("/wine.txt").toString(), 1, 2, 14);
		FileInputSynapse IVdata = createInput((new StringBuilder(String.valueOf(path))).append("/wine.txt").toString(), 131, 2, 14);
		FileInputSynapse DTdata = createInput((new StringBuilder(String.valueOf(path))).append("/wine.txt").toString(), 1, 1, 1);
		FileInputSynapse DVdata = createInput((new StringBuilder(String.valueOf(path))).append("/wine.txt").toString(), 131, 1, 1);
		LearningSwitch Ilsw = createSwitch(ITdata, IVdata);
		ILayer.addInputSynapse(Ilsw);
		LearningSwitch Dlsw = createSwitch(DTdata, DVdata);
		TeachingSynapse ts = new TeachingSynapse();
		ts.setDesired(Dlsw);
		OLayer.addOutputSynapse(ts);
		net = new NeuralNet();
		net.addLayer(ILayer, 0);
		net.addLayer(HLayer, 1);
		net.addLayer(OLayer, 2);
		net.setTeacher(ts);
		MacroPlugin mPlugin = new MacroPlugin();
		String validation = readFile(new File((new StringBuilder(String.valueOf(path))).append("/validation.bsh").toString()));
		mPlugin.getMacroManager().addMacro("cycleTerminated", validation);
		mPlugin.setRate(100);
		net.setMacroPlugin(mPlugin);
		net.setScriptingEnabled(true);
		Monitor mon = net.getMonitor();
		mon.setLearningRate(0.20000000000000001D);
		mon.setMomentum(0.29999999999999999D);
		mon.setTrainingPatterns(130);
		mon.setValidationPatterns(48);
		mon.setTotCicles(1000);
		mon.setLearning(true);
	}

	private FileInputSynapse createInput(String name, int firstRow, int firstCol, int lastCol)
	{
		FileInputSynapse input = new FileInputSynapse();
		input.setInputFile(new File(name));
		input.setFirstRow(firstRow);
		if (firstCol != lastCol)
			input.setAdvancedColumnSelector((new StringBuilder(String.valueOf(firstCol))).append("-").append(lastCol).toString());
		else
			input.setAdvancedColumnSelector(Integer.toString(firstCol));
		NormalizerPlugIn norm = new NormalizerPlugIn();
		if (firstCol != lastCol)
			norm.setAdvancedSerieSelector((new StringBuilder("1-")).append(Integer.toString((lastCol - firstCol) + 1)).toString());
		else
			norm.setAdvancedSerieSelector("1");
		input.addPlugIn(norm);
		return input;
	}

	private void connect(Layer ly1, Synapse syn, Layer ly2)
	{
		ly1.addOutputSynapse(syn);
		ly2.addInputSynapse(syn);
	}

	private LearningSwitch createSwitch(StreamInputSynapse IT, StreamInputSynapse IV)
	{
		LearningSwitch lsw = new LearningSwitch();
		lsw.addTrainingSet(IT);
		lsw.addValidationSet(IV);
		return lsw;
	}

	private String readFile(File p_file)
	{
		String str = null;
		FileReader reader = null;
		try
		{
			reader = new FileReader(p_file);
			int tch = (new Long(p_file.length())).intValue();
			char m_buf[] = new char[tch];
			int nch = reader.read(m_buf);
			if (nch != -1)
				str = new String(m_buf, 0, nch);
			reader.close();
		}
		catch (FileNotFoundException fnfe)
		{
			System.err.println(fnfe.getMessage());
			return str;
		}
		catch (IOException ioe)
		{
			System.err.println(ioe.getMessage());
		}
		return str;
	}

	private void start()
	{
		net.go();
	}

}
