// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SimpleValidationSample.java

package org.joone.samples.engine.validation;

import java.io.File;
import java.io.PrintStream;
import org.joone.engine.*;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.io.FileInputSynapse;
import org.joone.io.StreamInputSynapse;
import org.joone.net.*;
import org.joone.util.LearningSwitch;
import org.joone.util.NormalizerPlugIn;

public class SimpleValidationSample
	implements NeuralNetListener, NeuralValidationListener
{

	NeuralNet net;
	long startms;
	private static String filePath = "org/joone/samples/engine/validation";

	public SimpleValidationSample()
	{
	}

	public static void main(String args[])
	{
		SimpleValidationSample sampleNet = new SimpleValidationSample();
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
		Monitor mon = net.getMonitor();
		mon.setLearningRate(0.40000000000000002D);
		mon.setMomentum(0.5D);
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
		norm.setMin(0.10000000000000001D);
		norm.setMax(0.90000000000000002D);
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

	private void start()
	{
		net.getMonitor().addNeuralNetListener(this);
		startms = System.currentTimeMillis();
		net.go();
	}

	public void netValidated(NeuralValidationEvent event)
	{
		NeuralNet NN = (NeuralNet)event.getSource();
		System.out.println((new StringBuilder("    Validation Error: ")).append(NN.getMonitor().getGlobalError()).toString());
	}

	public void cicleTerminated(NeuralNetEvent e)
	{
		int cycle = (net.getMonitor().getTotCicles() - net.getMonitor().getCurrentCicle()) + 1;
		if (cycle % 200 == 0)
		{
			System.out.println((new StringBuilder("Cycle #")).append(cycle).toString());
			System.out.println((new StringBuilder("    Training Error:   ")).append(net.getMonitor().getGlobalError()).toString());
			net.getMonitor().setExporting(true);
			NeuralNet newNet = net.cloneNet();
			net.getMonitor().setExporting(false);
			newNet.removeAllListeners();
			NeuralNetValidator nnv = new NeuralNetValidator(newNet);
			nnv.addValidationListener(this);
			nnv.start();
		}
	}

	public void errorChanged(NeuralNetEvent neuralnetevent)
	{
	}

	public void netStarted(NeuralNetEvent neuralnetevent)
	{
	}

	public void netStopped(NeuralNetEvent e)
	{
		System.out.println((new StringBuilder("Stopped after ")).append(System.currentTimeMillis() - startms).append(" ms").toString());
	}

	public void netStoppedError(NeuralNetEvent neuralnetevent, String s)
	{
	}

}
