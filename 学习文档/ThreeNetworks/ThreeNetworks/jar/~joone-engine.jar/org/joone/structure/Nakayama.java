// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Nakayama.java

package org.joone.structure;

import java.io.Serializable;
import java.util.*;
import org.joone.engine.*;
import org.joone.engine.listeners.*;
import org.joone.exception.JooneRuntimeException;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.*;

// Referenced classes of package org.joone.structure:
//			PatternForwardedSynapse

public class Nakayama
	implements NeuralNetListener, NeuralValidationListener, ConvergenceListener, Serializable
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/structure/Nakayama);
	private static final int NO_REMOVE = 0;
	private static final int INFO_REMOVE = 1;
	private static final int VARIANCE_REMOVE = 2;
	private static final int CORRELATION_POSSIBLE_REMOVE = 3;
	private static final int CORRELATION_REMOVE = 4;
	private static final int REMOVE_DONE = -1;
	private List layers;
	private boolean isRunning;
	private NeuralNet net;
	private NeuralNet clone;
	private double epsilon;
	private List listeners;
	private List outputsAfterPattern;
	private List information;
	private double infoMax;
	private List averageOutputs;
	private List variance;
	private double varianceMax;
	private List gamma;
	private boolean optimized;

	public Nakayama(NeuralNet aNet)
	{
		layers = new ArrayList();
		epsilon = 0.050000000000000003D;
		listeners = new ArrayList();
		optimized = false;
		net = aNet;
	}

	public void addLayer(Layer aLayer)
	{
		layers.add(aLayer);
	}

	public void addLayers(NeuralNet aNeuralNet)
	{
		for (int i = 0; i < aNeuralNet.getLayers().size(); i++)
		{
			Object myLayer = aNeuralNet.getLayers().get(i);
			if (myLayer != aNeuralNet.getInputLayer() && myLayer != aNeuralNet.getOutputLayer())
				layers.add(myLayer);
		}

	}

	public boolean optimize()
	{
		outputsAfterPattern = new ArrayList();
		information = new ArrayList();
		infoMax = 0.0D;
		averageOutputs = new ArrayList();
		variance = new ArrayList();
		varianceMax = 0.0D;
		gamma = new ArrayList();
		optimized = false;
		log.debug((new StringBuilder("Optimization request [cycle : ")).append(net.getMonitor().getCurrentCicle()).append("]").toString());
		isRunning = net.isRunning();
		if (isRunning)
		{
			log.debug("Stopping network...");
			removeAllListeners();
			net.addNeuralNetListener(this);
			net.getMonitor().Stop();
		} else
		{
			runValidation();
		}
		return optimized;
	}

	protected void runValidation()
	{
		net.getMonitor().setExporting(true);
		clone = net.cloneNet();
		net.getMonitor().setExporting(false);
		clone.removeAllListeners();
		clone.getOutputLayer().addOutputSynapse(new PatternForwardedSynapse(this));
		log.debug("Validating network...");
		NeuralNetValidator myValidator = new NeuralNetValidator(clone);
		myValidator.addValidationListener(this);
		myValidator.useTrainingData(true);
		myValidator.start();
	}

	protected void doOptimize()
	{
		log.debug("Optimizing...");
		evaluateNeurons();
		selectNeurons();
		log.debug("Optimization done.");
		cleanUp();
		for (int i = 0; i < net.getLayers().size(); i++)
		{
			Layer myLayer = (Layer)net.getLayers().get(i);
			log.debug((new StringBuilder("Layer [")).append(myLayer.getClass().getName()).append("] - neurons : ").append(myLayer.getRows()).toString());
		}

	}

	protected void cleanUp()
	{
		log.debug("Cleaning up...");
		outputsAfterPattern = null;
		information = null;
		averageOutputs = null;
		variance = null;
		gamma = null;
		clone = null;
		for (int i = 0; i < layers.size(); i++)
		{
			Layer myLayer = (Layer)layers.get(i);
			if (myLayer.getRows() == 0)
			{
				log.debug((new StringBuilder("Remove layer [")).append(myLayer.getClass().getName()).append("]").toString());
				net.removeLayer(myLayer);
				layers.remove(i);
				i--;
			}
		}

		net.removeNeuralNetListener(this);
		restoreAllListeners();
		log.debug("Clean ;)");
		if (isRunning)
		{
			log.debug("Restarting net...");
			net.start();
			net.getMonitor().runAgain();
		}
	}

	protected void selectNeurons()
	{
		log.debug("Selecting neurons...");
		List myStatuses = new ArrayList();
		List myMinCorrelationPointers = new ArrayList();
		for (int i = 0; i < layers.size(); i++)
		{
			Layer myLayer = (Layer)layers.get(i);
			int myStatus[] = new int[myLayer.getRows()];
			for (int n = 0; n < myLayer.getRows(); n++)
			{
				double myScaledInfo = ((double[])information.get(i))[n] / infoMax;
				double myScaledVariance = ((double[])variance.get(i))[n] / varianceMax;
				double myMinCorrelation[] = getMinCorrelation(i, n);
				if (myScaledInfo * myScaledVariance * myMinCorrelation[0] <= epsilon)
					if (myScaledInfo <= myScaledVariance && myScaledInfo <= myMinCorrelation[0])
						myStatus[n] = 1;
					else
					if (myScaledVariance < myScaledInfo && myScaledVariance <= myMinCorrelation[0])
					{
						myStatus[n] = 2;
					} else
					{
						myStatus[n] = 3;
						myMinCorrelationPointers.add(myMinCorrelation);
					}
			}

			myStatuses.add(myStatus);
		}

		List myCorrelations = new ArrayList();
		for (int i = 0; i < myMinCorrelationPointers.size(); i++)
		{
			double myMinCorrelation[] = (double[])myMinCorrelationPointers.get(i);
			if (myMinCorrelation[5] < 0.0D && ((int[])myStatuses.get((int)myMinCorrelation[1]))[(int)myMinCorrelation[2]] == 3)
			{
				int mySingleStatus = ((int[])myStatuses.get((int)myMinCorrelation[3]))[(int)myMinCorrelation[4]];
				if (mySingleStatus == 1 || mySingleStatus == 2 || mySingleStatus == 4)
					((int[])myStatuses.get((int)myMinCorrelation[1]))[(int)myMinCorrelation[2]] = 0;
				else
				if (((double[])variance.get((int)myMinCorrelation[1]))[(int)myMinCorrelation[2]] <= ((double[])variance.get((int)myMinCorrelation[3]))[(int)myMinCorrelation[4]])
				{
					((int[])myStatuses.get((int)myMinCorrelation[1]))[(int)myMinCorrelation[2]] = 4;
					myCorrelations.add(new int[] {
						(int)myMinCorrelation[1], (int)myMinCorrelation[2], (int)myMinCorrelation[3], (int)myMinCorrelation[4]
					});
				} else
				{
					((int[])myStatuses.get((int)myMinCorrelation[1]))[(int)myMinCorrelation[2]] = 0;
					((int[])myStatuses.get((int)myMinCorrelation[3]))[(int)myMinCorrelation[4]] = 4;
					myCorrelations.add(new int[] {
						(int)myMinCorrelation[3], (int)myMinCorrelation[4], (int)myMinCorrelation[1], (int)myMinCorrelation[2]
					});
				}
			} else
			if (myMinCorrelation[5] > 0.0D && ((int[])myStatuses.get((int)myMinCorrelation[3]))[(int)myMinCorrelation[4]] == 3)
			{
				int mySingleStatus = ((int[])myStatuses.get((int)myMinCorrelation[1]))[(int)myMinCorrelation[2]];
				if (mySingleStatus == 1 || mySingleStatus == 2 || mySingleStatus == 4)
					((int[])myStatuses.get((int)myMinCorrelation[3]))[(int)myMinCorrelation[4]] = 0;
				else
				if (((double[])variance.get((int)myMinCorrelation[1]))[(int)myMinCorrelation[2]] >= ((double[])variance.get((int)myMinCorrelation[3]))[(int)myMinCorrelation[4]])
				{
					((int[])myStatuses.get((int)myMinCorrelation[3]))[(int)myMinCorrelation[4]] = 4;
					myCorrelations.add(new int[] {
						(int)myMinCorrelation[3], (int)myMinCorrelation[4], (int)myMinCorrelation[1], (int)myMinCorrelation[2]
					});
				} else
				{
					((int[])myStatuses.get((int)myMinCorrelation[3]))[(int)myMinCorrelation[4]] = 0;
					((int[])myStatuses.get((int)myMinCorrelation[1]))[(int)myMinCorrelation[2]] = 4;
					myCorrelations.add(new int[] {
						(int)myMinCorrelation[1], (int)myMinCorrelation[2], (int)myMinCorrelation[3], (int)myMinCorrelation[4]
					});
				}
			}
		}

		for (int l = 0; l < myStatuses.size(); l++)
		{
			int myStatus[] = (int[])myStatuses.get(l);
			int myNeuron = 0;
			for (int n = 0; n < myStatus.length; n++)
				if (myStatus[n] == 1)
				{
					log.debug((new StringBuilder("Remove[info]: ")).append(l).append(" ").append(n).toString());
					removeNeuron(l, myNeuron);
					optimized = true;
					myStatus[n] = -1;
				} else
				if (myStatus[n] == 2)
				{
					log.debug((new StringBuilder("Remove[variance]: ")).append(l).append(" ").append(n).toString());
					weightsUpdateVariance(l, n, myNeuron);
					removeNeuron(l, myNeuron);
					optimized = true;
					myStatus[n] = -1;
				} else
				if (myStatus[n] == 4)
				{
					log.debug((new StringBuilder("Remove[correlation]: ")).append(l).append(" ").append(n).toString());
					weightsUpdateCorrelation(myStatuses, myCorrelations, l, n);
					removeNeuron(l, myNeuron);
					optimized = true;
					myStatus[n] = -1;
				} else
				if (myStatus[n] == 0)
					myNeuron++;

		}

		log.debug("Selection done.");
	}

	protected void weightsUpdateCorrelation(List aStatuses, List aCorrelations, int aLayer, int aNeuron)
	{
		int myCorrelatedNeuron[] = (int[])null;
		for (int myTemp[] = findCorrelation(aCorrelations, aLayer, aNeuron); myTemp != null; myTemp = findCorrelation(aCorrelations, myCorrelatedNeuron[0], myCorrelatedNeuron[1]))
			myCorrelatedNeuron = myTemp;

		int myAdjustedNeuron = findIndex(aStatuses, aLayer, aNeuron);
		int myAdjustedCorrelatedNeuron = findIndex(aStatuses, myCorrelatedNeuron[0], myCorrelatedNeuron[1]);
		double myAlpha = (double)(getGamma(aLayer, aNeuron, myCorrelatedNeuron[0], myCorrelatedNeuron[1]) < 0.0D ? -1 : 1) * Math.sqrt(((double[])variance.get(aLayer))[aNeuron] / ((double[])variance.get(aLayer))[myCorrelatedNeuron[1]]);
		Synapse mySynapseCorrelation = null;
		Layer myLayer = (Layer)layers.get(aLayer);
		Layer myLayerCorrelation = (Layer)layers.get(myCorrelatedNeuron[0]);
		if (myLayer.getAllOutputs().size() != myLayerCorrelation.getAllInputs().size())
			throw new JooneRuntimeException("Unable to optimize. #output layers for neuron and correlated neuron are not equal.");
		for (int i = 0; i < myLayer.getAllOutputs().size(); i++)
		{
			NeuralElement myElement = (NeuralElement)myLayer.getAllOutputs().get(i);
			if (!(myElement instanceof Synapse))
				throw new JooneRuntimeException("Unable to optimize. Output of layer is not a synapse.");
			Synapse mySynapse = (Synapse)myElement;
			Layer myOutputLayer = findOutputLayer(mySynapse);
			for (int j = 0; j < myLayerCorrelation.getAllOutputs().size() && mySynapseCorrelation == null; j++)
			{
				NeuralElement myElementCorrelation = (NeuralElement)myLayerCorrelation.getAllOutputs().get(j);
				if (myElementCorrelation instanceof Synapse)
				{
					mySynapseCorrelation = (Synapse)myElementCorrelation;
					if (findOutputLayer(mySynapseCorrelation) != myOutputLayer)
						mySynapseCorrelation = null;
				}
			}

			if (mySynapseCorrelation == null)
				throw new JooneRuntimeException("Unable to optimize. Unable to find same output layer for correlated layer.");
			Matrix myBiases = myOutputLayer.getBias();
			Matrix myWeights = mySynapse.getWeights();
			Matrix myWeightsCorrelation = mySynapseCorrelation.getWeights();
			for (int r = 0; r < myOutputLayer.getRows(); r++)
			{
				myBiases.value[r][0] += myWeights.value[myAdjustedNeuron][r] * (((double[])averageOutputs.get(aLayer))[aNeuron] - myAlpha * ((double[])averageOutputs.get(myCorrelatedNeuron[0]))[myCorrelatedNeuron[1]]);
				myBiases.delta[r][0] = 0.0D;
				myWeightsCorrelation.value[myAdjustedCorrelatedNeuron][r] += myWeights.value[myAdjustedNeuron][r];
				myWeightsCorrelation.delta[myAdjustedCorrelatedNeuron][r] = 0.0D;
			}

		}

	}

	protected double getGamma(int aLayer1, int aNeuron1, int aLayer2, int aNeuron2)
	{
		if (aLayer1 > aLayer2 || aLayer1 == aLayer2 && aNeuron1 > aNeuron2)
		{
			int mySwapLayer = aLayer1;
			int mySwapNeuron = aNeuron1;
			aLayer1 = aLayer2;
			aNeuron1 = aNeuron2;
			aLayer2 = mySwapLayer;
			aNeuron2 = mySwapNeuron;
		}
		return ((double[])((List[])gamma.get(aLayer1))[aNeuron1].get(aLayer2))[aNeuron2];
	}

	protected int findIndex(List aStatuses, int aLayer, int aNeuron)
	{
		int myStatusLayer[] = (int[])aStatuses.get(aLayer);
		int myNewIndex = aNeuron;
		for (int i = 0; i < aNeuron; i++)
			if (myStatusLayer[i] == -1)
				myNewIndex--;

		return myNewIndex;
	}

	protected int[] findCorrelation(List aCorrelations, int aLayer, int aNeuron)
	{
		for (int i = 0; i < aCorrelations.size(); i++)
		{
			int myCorrelation[] = (int[])aCorrelations.get(i);
			if (myCorrelation[0] == aLayer && myCorrelation[1] == aNeuron)
				return (new int[] {
					myCorrelation[2], myCorrelation[3]
				});
		}

		return null;
	}

	protected void weightsUpdateVariance(int aLayer, int aNeuronOriginal, int aNeuron)
	{
		Layer myLayer = (Layer)layers.get(aLayer);
		for (int i = 0; i < myLayer.getAllOutputs().size(); i++)
		{
			NeuralElement myElement = (NeuralElement)myLayer.getAllOutputs().get(i);
			if (!(myElement instanceof Synapse))
				throw new JooneRuntimeException("Unable to optimize. Output of layer is not a synapse.");
			Synapse mySynapse = (Synapse)myElement;
			Layer myOutputLayer = findOutputLayer(mySynapse);
			Matrix myBiases = myOutputLayer.getBias();
			Matrix myWeights = mySynapse.getWeights();
			double myAverageOutput = ((double[])averageOutputs.get(aLayer))[aNeuronOriginal];
			for (int r = 0; r < myOutputLayer.getRows(); r++)
			{
				myBiases.value[r][0] += myWeights.value[aNeuron][r] * myAverageOutput;
				myBiases.delta[r][0] = 0.0D;
			}

		}

	}

	protected void removeNeuron(int aLayer, int aNeuron)
	{
		Layer myLayer = (Layer)layers.get(aLayer);
		if (myLayer.getRows() > 1)
		{
			Matrix myWeights;
			for (int i = 0; i < myLayer.getAllInputs().size(); i++)
			{
				NeuralElement myElement = (NeuralElement)myLayer.getAllInputs().get(i);
				if (!(myElement instanceof Synapse))
					throw new JooneRuntimeException("Unable to optimize. Input of layer is not a synapse.");
				Synapse mySynapse = (Synapse)myElement;
				myWeights = mySynapse.getWeights();
				myWeights.removeColumn(aNeuron);
				mySynapse.setOutputDimension(mySynapse.getOutputDimension() - 1);
				mySynapse.setWeights(myWeights);
			}

			for (int i = 0; i < myLayer.getAllOutputs().size(); i++)
			{
				NeuralElement myElement = (NeuralElement)myLayer.getAllOutputs().get(i);
				if (!(myElement instanceof Synapse))
					throw new JooneRuntimeException("Unable to optimize. Output of layer is not a synapse.");
				Synapse mySynapse = (Synapse)myElement;
				myWeights = mySynapse.getWeights();
				myWeights.removeRow(aNeuron);
				mySynapse.setInputDimension(mySynapse.getInputDimension() - 1);
				mySynapse.setWeights(myWeights);
			}

			myWeights = myLayer.getBias();
			myWeights.removeRow(aNeuron);
			myLayer.setRows(myLayer.getRows() - 1);
			myLayer.setBias(myWeights);
		} else
		{
			for (int i = 0; i < myLayer.getAllInputs().size(); i++)
			{
				NeuralElement myElement = (NeuralElement)myLayer.getAllInputs().get(i);
				if (!(myElement instanceof Synapse))
					throw new JooneRuntimeException("Unable to optimize. Input of layer is not a synapse.");
				Synapse mySynapse = (Synapse)myElement;
				Layer myInputLayer = findInputLayer(mySynapse);
				myInputLayer.removeOutputSynapse(mySynapse);
			}

			for (int i = 0; i < myLayer.getAllOutputs().size(); i++)
			{
				NeuralElement myElement = (NeuralElement)myLayer.getAllOutputs().get(i);
				if (!(myElement instanceof Synapse))
					throw new JooneRuntimeException("Unable to optimize. Output of layer is not a synapse.");
				Synapse mySynapse = (Synapse)myElement;
				Layer myOutputLayer = findOutputLayer(mySynapse);
				myOutputLayer.removeInputSynapse(mySynapse);
			}

			Matrix myWeights = myLayer.getBias();
			myWeights.removeRow(aNeuron);
			myLayer.setRows(myLayer.getRows() - 1);
			myLayer.setBias(myWeights);
		}
	}

	protected Layer findInputLayer(Synapse aSynapse)
	{
		for (int i = 0; i < net.getLayers().size(); i++)
		{
			Layer myLayer = (Layer)net.getLayers().get(i);
			if (myLayer.getAllOutputs().contains(aSynapse))
				return myLayer;
		}

		return null;
	}

	protected Layer findOutputLayer(Synapse aSynapse)
	{
		for (int i = 0; i < net.getLayers().size(); i++)
		{
			Layer myLayer = (Layer)net.getLayers().get(i);
			if (myLayer.getAllInputs().contains(aSynapse))
				return myLayer;
		}

		return null;
	}

	protected void evaluateNeurons()
	{
		log.debug("Evaluation of neurons...");
		int myNrOfPatterns = net.getMonitor().getTrainingPatterns();
		for (int i = 0; i < layers.size(); i++)
		{
			Layer myLayer = (Layer)layers.get(i);
			double myInfo[] = new double[myLayer.getRows()];
			double myAvgOutputs[] = new double[myLayer.getRows()];
			for (int n = 0; n < myLayer.getRows(); n++)
			{
				double myTempSumWeights = getSumAbsoluteWeights(myLayer, n);
				double myTempSumOutputs[] = getSumOutputs(i, n);
				myInfo[n] = (myTempSumWeights * myTempSumOutputs[1]) / (double)myNrOfPatterns;
				if (myInfo[n] > infoMax)
					infoMax = myInfo[n];
				myAvgOutputs[n] = myTempSumOutputs[0] / (double)myNrOfPatterns;
			}

			information.add(myInfo);
			averageOutputs.add(myAvgOutputs);
		}

		List myDifferences = new ArrayList();
		for (int i = 0; i < layers.size(); i++)
		{
			Layer myLayer = (Layer)layers.get(i);
			double myVariance[] = new double[myLayer.getRows()];
			List myDifferencesForLayer = new ArrayList();
			for (int p = 0; p < outputsAfterPattern.size(); p++)
			{
				double myTempDifferences[] = new double[myLayer.getRows()];
				for (int n = 0; n < myLayer.getRows(); n++)
				{
					List myOutputs = (List)outputsAfterPattern.get(p);
					myTempDifferences[n] = ((double[])myOutputs.get(i))[n] - ((double[])averageOutputs.get(i))[n];
					myVariance[n] += myTempDifferences[n] * myTempDifferences[n];
				}

				myDifferencesForLayer.add(myTempDifferences);
			}

			for (int n = 0; n < myLayer.getRows(); n++)
				if (myVariance[n] > varianceMax)
					varianceMax = myVariance[n];

			myDifferences.add(myDifferencesForLayer);
			variance.add(myVariance);
		}

		for (int l1 = 0; l1 < layers.size(); l1++)
		{
			Layer myLayer1 = (Layer)layers.get(l1);
			List myNeurons1Pointer[] = new List[myLayer1.getRows()];
			gamma.add(myNeurons1Pointer);
			for (int n1 = 0; n1 < myLayer1.getRows(); n1++)
			{
				myNeurons1Pointer[n1] = new ArrayList();
				for (int l2 = 0; l2 < layers.size(); l2++)
				{
					Layer myLayer2 = (Layer)layers.get(l2);
					if (l2 < l1)
					{
						myNeurons1Pointer[n1].add(new double[0]);
					} else
					{
						double myNeurons2Pointer[] = new double[myLayer2.getRows()];
						myNeurons1Pointer[n1].add(myNeurons2Pointer);
						for (int n2 = l1 != l2 ? 0 : n1 + 1; n2 < myLayer2.getRows(); n2++)
						{
							double myA = 0.0D;
							double myB = 0.0D;
							for (int p = 0; p < myNrOfPatterns; p++)
							{
								List myTempDifferencesForLayer1 = (List)myDifferences.get(l1);
								List myTempDifferencesForLayer2 = (List)myDifferences.get(l2);
								myA += ((double[])myTempDifferencesForLayer1.get(p))[n1] * ((double[])myTempDifferencesForLayer2.get(p))[n2];
							}

							myB = ((double[])variance.get(l1))[n1] * ((double[])variance.get(l2))[n2];
							myNeurons2Pointer[n2] = myA / Math.sqrt(myB);
						}

					}
				}

			}

		}

		log.debug("Evaluation done.");
	}

	protected double[] getMinCorrelation(int aLayer, int aNeuron)
	{
		double myReturnValue[] = {
			2D, -1D, -1D, -1D, -1D, 0.0D
		};
		for (int l = 0; l <= aLayer; l++)
		{
			List myNeurons[] = (List[])gamma.get(l);
			for (int n = 0; n < (l != aLayer ? myNeurons.length : aNeuron); n++)
			{
				double myCorrelation = 1.0D - Math.abs(((double[])myNeurons[n].get(aLayer))[aNeuron]);
				if (myReturnValue[0] > myCorrelation)
				{
					myReturnValue[0] = myCorrelation;
					myReturnValue[1] = l;
					myReturnValue[2] = n;
					myReturnValue[3] = aLayer;
					myReturnValue[4] = aNeuron;
					myReturnValue[5] = 1.0D;
				}
			}

		}

		List myLayers = ((List[])gamma.get(aLayer))[aNeuron];
		for (int l = aLayer; l < myLayers.size(); l++)
		{
			double myNeurons2[] = (double[])myLayers.get(l);
			for (int n = l != aLayer ? 0 : aNeuron + 1; n < myNeurons2.length; n++)
			{
				double myCorrelation = 1.0D - Math.abs(myNeurons2[n]);
				if (myReturnValue[0] > myCorrelation)
				{
					myReturnValue[0] = myCorrelation;
					myReturnValue[1] = aLayer;
					myReturnValue[2] = aNeuron;
					myReturnValue[3] = l;
					myReturnValue[4] = n;
					myReturnValue[5] = -1D;
				}
			}

		}

		return myReturnValue;
	}

	protected double[] getSumOutputs(int aLayer, int aNeuron)
	{
		double mySum[] = new double[2];
		for (int i = 0; i < outputsAfterPattern.size(); i++)
		{
			List myOutputs = (List)outputsAfterPattern.get(i);
			double myOutput = ((double[])myOutputs.get(aLayer))[aNeuron];
			mySum[0] += myOutput;
			mySum[1] += Math.abs(myOutput);
		}

		return mySum;
	}

	protected double getSumAbsoluteWeights(Layer aLayer, int aNeuron)
	{
		double mySum = 0.0D;
		for (int i = 0; i < aLayer.getAllOutputs().size(); i++)
		{
			OutputPatternListener myListener = (OutputPatternListener)aLayer.getAllOutputs().get(i);
			if (!(myListener instanceof Synapse))
				throw new JooneRuntimeException("Unable to optimize. Output of layer is not a synapse.");
			Synapse mySynapse = (Synapse)myListener;
			for (int j = 0; j < mySynapse.getOutputDimension(); j++)
				mySum += Math.abs(mySynapse.getWeights().value[aNeuron][j]);

		}

		return mySum;
	}

	public void cicleTerminated(NeuralNetEvent neuralnetevent)
	{
	}

	public void errorChanged(NeuralNetEvent neuralnetevent)
	{
	}

	public void netStarted(NeuralNetEvent neuralnetevent)
	{
	}

	public void netStopped(NeuralNetEvent e)
	{
		log.debug("Network stopped.");
		runValidation();
	}

	public void netStoppedError(NeuralNetEvent neuralnetevent, String s)
	{
	}

	public void netValidated(NeuralValidationEvent event)
	{
		log.debug("Network validated.");
		doOptimize();
	}

	protected void removeAllListeners()
	{
		NeuralNetListener myListener;
		for (Vector myListeners = net.getListeners(); myListeners.size() > 0; net.removeNeuralNetListener(myListener))
		{
			myListener = (NeuralNetListener)myListeners.get(myListeners.size() - 1);
			listeners.add(myListener);
		}

	}

	protected void restoreAllListeners()
	{
		NeuralNetListener myListener;
		for (Iterator myIterator = listeners.iterator(); myIterator.hasNext(); net.addNeuralNetListener(myListener))
			myListener = (NeuralNetListener)myIterator.next();

		listeners = new Vector();
	}

	void patternFinished()
	{
		List myOutputs = new ArrayList();
		for (int i = 0; i < layers.size(); i++)
		{
			Layer myLayer = findClonedLayer((Layer)layers.get(i));
			myOutputs.add(myLayer.getLastOutputs());
		}

		outputsAfterPattern.add(myOutputs);
	}

	private Layer findClonedLayer(Layer aLayer)
	{
		for (int i = 0; i < net.getLayers().size(); i++)
			if (net.getLayers().get(i) == aLayer)
				return (Layer)clone.getLayers().get(i);

		return null;
	}

	public double getEpsilon()
	{
		return epsilon;
	}

	public void setEpsilon(double anEpsilon)
	{
		epsilon = anEpsilon;
	}

	public void netConverged(ConvergenceEvent anEvent, ConvergenceObserver anObserver)
	{
		if (!optimize())
			anObserver.disableCurrentConvergence();
	}

}
