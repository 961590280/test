// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Layer.java

package org.joone.engine;

import java.io.*;
import java.util.*;
import org.joone.engine.learning.TeacherSynapse;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.exception.JooneRuntimeException;
import org.joone.inspection.Inspectable;
import org.joone.inspection.implementations.BiasInspection;
import org.joone.io.*;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NetCheck;
import org.joone.util.NotSerialize;

// Referenced classes of package org.joone.engine:
//			NeuralLayer, LearnableLayer, Matrix, InputPatternListener, 
//			Synapse, Pattern, Monitor, OutputPatternListener, 
//			NeuralNetListener, NetErrorManager, NeuralElement, OutputSwitchSynapse, 
//			Learner

public abstract class Layer
	implements NeuralLayer, Runnable, Serializable, Inspectable, LearnableLayer
{

	public static final int STOP_FLAG = -1;
	private static final long serialVersionUID = 0xea2d0844feedcd05L;
	private String LayerName;
	private int rows;
	protected Matrix bias;
	protected Monitor monitor;
	protected int m_batch;
	protected boolean learning;
	protected boolean learnable;
	protected Vector inputPatternListeners;
	protected Vector outputPatternListeners;
	private transient Thread myThread;
	private volatile transient Object myThreadMonitor;
	protected transient double outs[];
	protected transient double inps[];
	protected transient double gradientInps[];
	protected transient double gradientOuts[];
	protected transient int step;
	protected volatile transient boolean running;
	protected transient Learner myLearner;
	private static final ILogger log = LoggerFactory.getLogger(org/joone/engine/Layer);

	public Layer()
	{
		rows = 0;
		learnable = false;
		inputPatternListeners = null;
		outputPatternListeners = null;
		myThread = null;
		myThreadMonitor = new Object();
		step = 0;
		running = false;
		myLearner = null;
	}

	public Layer(String ElemName)
	{
		rows = 0;
		learnable = false;
		inputPatternListeners = null;
		outputPatternListeners = null;
		myThread = null;
		myThreadMonitor = new Object();
		step = 0;
		running = false;
		myLearner = null;
		LayerName = ElemName;
	}

	public void addNoise(double amplitude)
	{
		bias.addNoise(amplitude);
		if (inputPatternListeners == null)
			return;
		int currentSize = inputPatternListeners.size();
		for (int index = 0; index < currentSize; index++)
		{
			InputPatternListener elem = (InputPatternListener)inputPatternListeners.elementAt(index);
			if (elem != null && (elem instanceof Synapse))
				((Synapse)elem).addNoise(amplitude);
		}

	}

	public void randomize(double amplitude)
	{
		randomizeBias(amplitude);
		randomizeWeights(amplitude);
	}

	public void randomizeWeights(double amplitude)
	{
		if (inputPatternListeners == null)
			return;
		int currentSize = inputPatternListeners.size();
		for (int index = 0; index < currentSize; index++)
		{
			InputPatternListener elem = (InputPatternListener)inputPatternListeners.elementAt(index);
			if (elem != null && (elem instanceof Synapse))
				((Synapse)elem).randomize(amplitude);
		}

	}

	public void randomizeBias(double amplitude)
	{
		bias.randomizeConditionally(amplitude);
	}

	protected abstract void backward(double ad[])
		throws JooneRuntimeException;

	public NeuralLayer copyInto(NeuralLayer newLayer)
	{
		newLayer.setMonitor(getMonitor());
		newLayer.setRows(getRows());
		newLayer.setBias(getBias());
		newLayer.setLayerName(getLayerName());
		newLayer.setAllInputs((Vector)getAllInputs().clone());
		newLayer.setAllOutputs((Vector)getAllOutputs().clone());
		removeAllInputs();
		removeAllOutputs();
		return newLayer;
	}

	protected void fireFwdGet()
	{
		int currentSize = inputPatternListeners.size();
		step = 0;
		for (int index = 0; index < currentSize && running; index++)
		{
			InputPatternListener tempListener = (InputPatternListener)inputPatternListeners.elementAt(index);
			if (tempListener != null)
			{
				Pattern tPatt = tempListener.fwdGet();
				if (tPatt != null)
				{
					double patt[] = tPatt.getArray();
					if (patt.length != inps.length)
						adjustSizeToFwdPattern(patt);
					sumInput(patt);
					if (step != -1 && (step < tPatt.getCount() || tPatt.isMarkedAsStoppingPattern()))
						step = tPatt.getCount();
				}
			}
		}

	}

	protected void fireFwdPut(Pattern pattern)
	{
		if (outputPatternListeners == null)
			return;
		int currentSize = outputPatternListeners.size();
		boolean isLearningCycle = getMonitor().isLearningCicle(pattern.getCount());
		for (int index = 0; index < currentSize && running; index++)
		{
			OutputPatternListener tempListener = (OutputPatternListener)outputPatternListeners.elementAt(index);
			if (tempListener != null)
			{
				boolean loop = false;
				if (tempListener instanceof Synapse)
					loop = ((Synapse)tempListener).isLoopBack();
				if (currentSize == 1 && isLearningCycle && !loop)
					tempListener.fwdPut(pattern);
				else
					tempListener.fwdPut((Pattern)pattern.clone());
			}
		}

	}

	protected void fireRevGet()
	{
		if (outputPatternListeners == null)
			return;
		int currentSize = outputPatternListeners.size();
		for (int index = 0; index < currentSize && running; index++)
		{
			OutputPatternListener tempListener = (OutputPatternListener)outputPatternListeners.elementAt(index);
			if (tempListener != null)
			{
				Pattern tPatt = tempListener.revGet();
				if (tPatt != null)
				{
					double patt[] = tPatt.getArray();
					if (patt.length != gradientInps.length)
						adjustSizeToRevPattern(patt);
					sumBackInput(patt);
				}
			}
		}

	}

	protected void fireRevPut(Pattern pattern)
	{
		if (inputPatternListeners == null)
			return;
		int currentSize = inputPatternListeners.size();
		InputPatternListener tempListener = null;
		for (int index = 0; index < currentSize && running; index++)
		{
			tempListener = (InputPatternListener)inputPatternListeners.elementAt(index);
			if (tempListener != null)
			{
				boolean loop = false;
				if (tempListener instanceof Synapse)
					loop = ((Synapse)tempListener).isLoopBack();
				if (currentSize == 1 && !loop)
					tempListener.revPut(pattern);
				else
					tempListener.revPut((Pattern)pattern.clone());
			}
		}

	}

	protected void adjustSizeToFwdPattern(double aPattern[])
	{
		int myOldSize = getRows();
		setRows(aPattern.length);
		log.warn((new StringBuilder("Pattern size mismatches #neurons. #neurons in layer '")).append(getLayerName()).append("' adjusted [fwd pass, ").append(myOldSize).append(" -> ").append(getRows()).append("].").toString());
	}

	protected void adjustSizeToRevPattern(double aPattern[])
	{
		int myOldSize = getRows();
		setRows(aPattern.length);
		log.warn((new StringBuilder("Pattern size mismatches #neurons. #neurons in layer '")).append(getLayerName()).append("' adjusted [rev pass, ").append(myOldSize).append(" -> ").append(getRows()).append("].").toString());
	}

	protected abstract void forward(double ad[])
		throws JooneRuntimeException;

	public Vector getAllInputs()
	{
		return inputPatternListeners;
	}

	public Vector getAllOutputs()
	{
		return outputPatternListeners;
	}

	public Matrix getBias()
	{
		return bias;
	}

	public int getDimension()
	{
		return getRows();
	}

	public String getLayerName()
	{
		return LayerName;
	}

	public Monitor getMonitor()
	{
		return monitor;
	}

	public int getRows()
	{
		return rows;
	}

	public void removeAllInputs()
	{
		if (inputPatternListeners != null)
		{
			Vector tempVect = (Vector)inputPatternListeners.clone();
			for (int i = 0; i < tempVect.size(); i++)
				removeInputSynapse((InputPatternListener)tempVect.elementAt(i));

			inputPatternListeners = null;
		}
	}

	public void removeAllOutputs()
	{
		if (outputPatternListeners != null)
		{
			Vector tempVect = (Vector)outputPatternListeners.clone();
			for (int i = 0; i < tempVect.size(); i++)
				removeOutputSynapse((OutputPatternListener)tempVect.elementAt(i));

			outputPatternListeners = null;
		}
	}

	public void removeInputSynapse(InputPatternListener newListener)
	{
		if (newListener == null)
			return;
		if (inputPatternListeners != null)
		{
			inputPatternListeners.removeElement(newListener);
			newListener.setInputFull(false);
			if (newListener instanceof NeuralNetListener)
				removeListener((NeuralNetListener)newListener);
			if (inputPatternListeners.size() == 0)
				inputPatternListeners = null;
		}
	}

	public void removeOutputSynapse(OutputPatternListener newListener)
	{
		if (newListener == null)
			return;
		if (outputPatternListeners != null)
		{
			outputPatternListeners.removeElement(newListener);
			newListener.setOutputFull(false);
			if (newListener instanceof NeuralNetListener)
				removeListener((NeuralNetListener)newListener);
			if (outputPatternListeners.size() == 0)
				outputPatternListeners = null;
		}
	}

	protected void removeListener(NeuralNetListener listener)
	{
		if (getMonitor() != null)
			getMonitor().removeNeuralNetListener(listener);
	}

	public double[] getLastOutputs()
	{
		return (double[])outs.clone();
	}

	public void run()
		throws JooneRuntimeException
	{
		Pattern patt = new Pattern();
		while (running) 
		{
			inps = new double[getRows()];
			try
			{
				fireFwdGet();
				if (running)
				{
					forward(inps);
					patt.setArray(outs);
					patt.setCount(step);
					fireFwdPut(patt);
				}
				if (step != -1)
				{
					if (monitor != null)
						learning = monitor.isLearningCicle(step);
					else
						learning = false;
				} else
				{
					running = false;
				}
			}
			catch (JooneRuntimeException jre)
			{
				String msg = (new StringBuilder("JooneException thrown in run() method.")).append(jre.getMessage()).toString();
				log.error(msg);
				running = false;
				new NetErrorManager(getMonitor(), msg);
			}
			if (learning && running)
			{
				gradientInps = new double[getDimension()];
				try
				{
					fireRevGet();
					backward(gradientInps);
					patt.setArray(gradientOuts);
					patt.setOutArray(outs);
					patt.setCount(step);
					fireRevPut(patt);
				}
				catch (JooneRuntimeException jre)
				{
					String msg = (new StringBuilder("In run() JooneException thrown.")).append(jre.getMessage()).toString();
					log.error(msg);
					running = false;
					new NetErrorManager(getMonitor(), msg);
				}
			}
		}
		resetInputListeners();
		synchronized (getThreadMonitor())
		{
			myThread = null;
		}
	}

	public synchronized void setAllInputs(Vector newInputPatternListeners)
	{
		inputPatternListeners = newInputPatternListeners;
		if (inputPatternListeners != null)
		{
			for (int i = 0; i < inputPatternListeners.size(); i++)
				setInputDimension((InputPatternListener)inputPatternListeners.elementAt(i));

		}
		notifyAll();
	}

	public void setInputSynapses(ArrayList newInputPatternListeners)
	{
		setAllInputs(new Vector(newInputPatternListeners));
	}

	public void setAllOutputs(Vector newOutputPatternListeners)
	{
		outputPatternListeners = newOutputPatternListeners;
		if (outputPatternListeners != null)
		{
			for (int i = 0; i < outputPatternListeners.size(); i++)
				setOutputDimension((OutputPatternListener)outputPatternListeners.elementAt(i));

		}
	}

	public void setOutputSynapses(ArrayList newOutputPatternListeners)
	{
		setAllOutputs(new Vector(newOutputPatternListeners));
	}

	public void setBias(Matrix newBias)
	{
		bias = newBias;
	}

	protected abstract void setDimensions();

	protected void setInputDimension(InputPatternListener syn)
	{
		if (syn.getOutputDimension() != getRows())
			syn.setOutputDimension(getRows());
	}

	public synchronized boolean addInputSynapse(InputPatternListener newListener)
	{
		if (newListener == null)
			return false;
		if (inputPatternListeners == null)
			inputPatternListeners = new Vector();
		boolean retValue = false;
		if (!inputPatternListeners.contains(newListener) && !newListener.isInputFull())
		{
			inputPatternListeners.addElement(newListener);
			if (newListener.getMonitor() == null)
				newListener.setMonitor(getMonitor());
			newListener.setInputFull(true);
			setInputDimension(newListener);
			retValue = true;
		}
		notifyAll();
		return retValue;
	}

	public void setLayerName(String newLayerName)
	{
		LayerName = newLayerName;
	}

	public void setMonitor(Monitor mon)
	{
		monitor = mon;
		setVectMonitor(inputPatternListeners, mon);
		setVectMonitor(outputPatternListeners, mon);
	}

	private void setVectMonitor(Vector vect, Monitor mon)
	{
		if (vect == null)
		{
			log.warn((new StringBuilder("Null vector provided in setVectMonitor at ")).append(getLayerName()).toString());
			return;
		}
		int currentSize = vect.size();
		for (int index = 0; index < currentSize; index++)
		{
			Object tempListener = vect.elementAt(index);
			if (tempListener != null)
				((NeuralElement)tempListener).setMonitor(mon);
		}

	}

	protected void setOutputDimension(OutputPatternListener syn)
	{
		if (syn.getInputDimension() != getRows())
			syn.setInputDimension(getRows());
	}

	public boolean addOutputSynapse(OutputPatternListener newListener)
	{
		if (newListener == null)
			return false;
		if (outputPatternListeners == null)
			outputPatternListeners = new Vector();
		boolean retValue = false;
		if (!outputPatternListeners.contains(newListener) && !newListener.isOutputFull())
		{
			outputPatternListeners.addElement(newListener);
			newListener.setMonitor(getMonitor());
			newListener.setOutputFull(true);
			setOutputDimension(newListener);
			retValue = true;
		}
		return retValue;
	}

	public void setRows(int newRows)
	{
		if (rows != newRows)
		{
			rows = newRows;
			setDimensions();
			setConnDimensions();
			bias = new Matrix(getRows(), 1);
		}
	}

	public void start()
	{
label0:
		{
			String msg;
			synchronized (getThreadMonitor())
			{
				if (myThread == null)
					break MISSING_BLOCK_LABEL_68;
				if (!myThread.isAlive())
					break label0;
				msg = (new StringBuilder("Ignoring start request in layer '")).append(getLayerName()).append("': it appears to be running").toString();
				log.warn(msg);
			}
			return;
		}
		myThread = null;
		if (myThread == null)
			if (inputPatternListeners != null)
			{
				if (checkInputEnabled())
				{
					running = true;
					if (getLayerName() != null)
						myThread = new Thread(this, getLayerName());
					else
						myThread = new Thread(this);
					init();
					myThread.start();
				} else
				{
					msg = (new StringBuilder("Can't start: '")).append(getLayerName()).append("' has not input synapses connected and/or enabled").toString();
					log.error(msg);
					throw new JooneRuntimeException(msg);
				}
			} else
			{
				msg = (new StringBuilder("Can't start: '")).append(getLayerName()).append("' has not input synapses connected").toString();
				log.error(msg);
				throw new JooneRuntimeException(msg);
			}
		obj;
		JVM INSTR monitorexit ;
	}

	public void init()
	{
		initLearner();
		if (getBias() != null)
			getBias().clearDelta();
		if (outputPatternListeners != null)
		{
			Vector tempVect = (Vector)outputPatternListeners.clone();
			for (int i = 0; i < tempVect.size(); i++)
			{
				Object tmpVectElement = tempVect.elementAt(i);
				if (tmpVectElement instanceof NeuralElement)
					((NeuralElement)tmpVectElement).init();
			}

		}
	}

	protected boolean checkInputEnabled()
	{
		for (int i = 0; i < inputPatternListeners.size(); i++)
		{
			InputPatternListener iPatt = (InputPatternListener)inputPatternListeners.elementAt(i);
			if (iPatt.isEnabled())
				return true;
		}

		return false;
	}

	public void stop()
	{
		synchronized (getThreadMonitor())
		{
			if (myThread != null)
			{
				running = false;
				myThread.interrupt();
			}
		}
	}

	protected void resetInputListeners()
	{
		int currentSize = inputPatternListeners.size();
		for (int index = 0; index < currentSize; index++)
		{
			InputPatternListener tempListener = (InputPatternListener)inputPatternListeners.elementAt(index);
			if (tempListener != null)
				tempListener.reset();
		}

	}

	protected void sumBackInput(double pattern[])
	{
		int x = 0;
		try
		{
			while (x < gradientInps.length) 
			{
				gradientInps[x] += pattern[x];
				x++;
			}
		}
		catch (IndexOutOfBoundsException iobe)
		{
			log.warn((new StringBuilder(String.valueOf(getLayerName()))).append(" gradInps.size:").append(gradientInps.length).append(" pattern.size:").append(pattern.length).append(" x:").append(x).toString());
		}
	}

	protected void sumInput(double pattern[])
	{
		for (int x = 0; x < inps.length; x++)
			inps[x] += pattern[x];

	}

	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		if (in.getClass().getName().indexOf("xstream") != -1)
		{
			in.defaultReadObject();
		} else
		{
			LayerName = (String)in.readObject();
			rows = in.readInt();
			bias = (Matrix)in.readObject();
			monitor = (Monitor)in.readObject();
			m_batch = in.readInt();
			learning = in.readBoolean();
			inputPatternListeners = readVector(in);
			outputPatternListeners = readVector(in);
		}
		setDimensions();
	}

	private void writeObject(ObjectOutputStream out)
		throws IOException
	{
		if (out.getClass().getName().indexOf("xstream") != -1)
		{
			out.defaultWriteObject();
		} else
		{
			out.writeObject(LayerName);
			out.writeInt(rows);
			out.writeObject(bias);
			out.writeObject(monitor);
			out.writeInt(m_batch);
			out.writeBoolean(learning);
			writeVector(out, inputPatternListeners);
			writeVector(out, outputPatternListeners);
		}
	}

	private void writeVector(ObjectOutputStream out, Vector vect)
		throws IOException
	{
		if (vect != null)
		{
			boolean exporting = false;
			if (monitor != null && monitor.isExporting())
				exporting = true;
			for (int i = 0; i < vect.size(); i++)
			{
				Object obj = vect.elementAt(i);
				if (!(obj instanceof NotSerialize) || !exporting)
					out.writeObject(obj);
			}

		}
		out.writeObject(null);
	}

	private Vector readVector(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		Vector vect = new Vector();
		for (Object obj = in.readObject(); obj != null; obj = in.readObject())
			vect.addElement(obj);

		return vect;
	}

	protected void setConnDimensions()
	{
		if (inputPatternListeners != null)
		{
			int currentSize = inputPatternListeners.size();
			for (int index = 0; index < currentSize; index++)
			{
				InputPatternListener tempListener = (InputPatternListener)inputPatternListeners.elementAt(index);
				if (tempListener != null)
					setInputDimension(tempListener);
			}

		}
		if (outputPatternListeners != null)
		{
			int currentSize = outputPatternListeners.size();
			for (int index = 0; index < currentSize; index++)
			{
				OutputPatternListener tempListener = (OutputPatternListener)outputPatternListeners.elementAt(index);
				if (tempListener != null)
					setOutputDimension(tempListener);
			}

		}
	}

	public boolean isRunning()
	{
		Object obj = getThreadMonitor();
		JVM INSTR monitorenter ;
		if (myThread != null && myThread.isAlive())
			return true;
		obj;
		JVM INSTR monitorexit ;
		return false;
		obj;
		JVM INSTR monitorexit ;
		throw ;
	}

	public TreeSet check()
	{
		TreeSet checks = new TreeSet();
		if (inputPatternListeners == null || inputPatternListeners.isEmpty())
			checks.add(new NetCheck(0, "Layer has no input synapses attached.", this));
		if (inputPatternListeners != null)
		{
			for (int i = 0; i < inputPatternListeners.size(); i++)
			{
				InputPatternListener listener = (InputPatternListener)inputPatternListeners.elementAt(i);
				checks.addAll(listener.check());
				if (listener instanceof StreamInputSynapse)
				{
					StreamInputSynapse sis = (StreamInputSynapse)listener;
					int cols = sis.numColumns();
					if (cols != rows)
						checks.add(new NetCheck(0, "Rows parameter does not match the number of columns for the attached input stream .", this));
				}
			}

		}
		if (outputPatternListeners != null)
		{
			for (int i = 0; i < outputPatternListeners.size(); i++)
			{
				OutputPatternListener listener = (OutputPatternListener)outputPatternListeners.elementAt(i);
				checks.addAll(listener.check());
			}

		}
		return checks;
	}

	public String toString()
	{
		return getLayerName();
	}

	public void finalize()
		throws Throwable
	{
		super.finalize();
		LayerName = null;
		bias = null;
		monitor = null;
		if (inputPatternListeners != null)
		{
			inputPatternListeners.clear();
			inputPatternListeners = null;
		}
		if (outputPatternListeners != null)
		{
			outputPatternListeners.clear();
			outputPatternListeners = null;
		}
	}

	public Collection Inspections()
	{
		Collection col = new ArrayList();
		col.add(new BiasInspection(bias));
		return col;
	}

	public String InspectableTitle()
	{
		return getLayerName();
	}

	public boolean hasStepCounter()
	{
		Vector inputs = getAllInputs();
		if (inputs == null)
			return false;
		for (int x = 0; x < inputs.size(); x++)
			if (inputs.elementAt(x) instanceof InputSynapse)
			{
				InputSynapse inp = (InputSynapse)inputs.elementAt(x);
				if (inp.isStepCounter())
					return true;
			}

		return false;
	}

	public boolean isInputLayer()
	{
		Vector inputListeners = getAllInputs();
		return checkInputs(inputListeners);
	}

	protected boolean checkInputs(Vector inputListeners)
	{
		if (inputListeners == null || inputListeners.isEmpty())
			return true;
		for (int x = 0; x < inputListeners.size(); x++)
			if (inputListeners.elementAt(x) instanceof StreamInputSynapse)
				return true;

		return false;
	}

	public boolean isOutputLayer()
	{
		Vector outputVectors = getAllOutputs();
		return checkOutputs(outputVectors);
	}

	protected boolean checkOutputs(Vector outputListeners)
	{
		boolean lastListener = false;
		if (outputListeners == null || outputListeners.isEmpty())
			return true;
		for (int x = 0; x < outputListeners.size(); x++)
		{
			Object lElem = outputListeners.elementAt(x);
			if ((lElem instanceof StreamOutputSynapse) || (lElem instanceof TeachingSynapse) || (lElem instanceof TeacherSynapse))
				lastListener = true;
			else
			if (lElem instanceof OutputSwitchSynapse)
			{
				OutputSwitchSynapse os = (OutputSwitchSynapse)lElem;
				if (checkOutputs(os.getAllOutputs()))
					lastListener = true;
				else
					return false;
			} else
			if (lElem instanceof Synapse)
			{
				Synapse syn = (Synapse)lElem;
				if (syn.isLoopBack())
					lastListener = true;
				else
					return false;
			}
		}

		return lastListener;
	}

	public Learner getLearner()
	{
		if (!learnable)
			return null;
		else
			return getMonitor().getLearner();
	}

	public void initLearner()
	{
		myLearner = getLearner();
		if (myLearner != null)
			myLearner.registerLearnable(this);
	}

	protected Object getThreadMonitor()
	{
		if (myThreadMonitor == null)
			synchronized (this)
			{
				if (myThreadMonitor == null)
					myThreadMonitor = new Object();
			}
		return myThreadMonitor;
	}

	public void join()
	{
		try
		{
			if (myThread != null)
				myThread.join();
		}
		catch (InterruptedException interruptedexception) { }
		catch (NullPointerException nullpointerexception) { }
	}

	public void fwdRun(Pattern pattIn)
	{
		running = true;
		if (pattIn == null)
		{
			inps = new double[getRows()];
			fireFwdGet();
		} else
		{
			inps = pattIn.getArray();
		}
		if (running)
		{
			Pattern patt = new Pattern();
			if (pattIn != null && pattIn.isMarkedAsStoppingPattern())
			{
				patt.setArray(outs);
				patt.markAsStoppingPattern();
			} else
			{
				forward(inps);
				patt.setArray(outs);
				patt.setCount(step);
			}
			fireFwdPut(patt);
		}
		running = false;
	}

	public void revRun(Pattern pattIn)
	{
		running = true;
		if (pattIn == null)
		{
			gradientInps = new double[getDimension()];
			fireRevGet();
		} else
		{
			gradientInps = pattIn.getArray();
		}
		if (running)
		{
			Pattern patt = new Pattern();
			backward(gradientInps);
			patt.setArray(gradientOuts);
			patt.setOutArray(outs);
			patt.setCount(step);
			fireRevPut(patt);
		}
		running = false;
	}

}
