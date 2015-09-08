// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NeuralNet.java

package org.joone.net;

import java.io.*;
import java.util.*;
import org.joone.engine.*;
import org.joone.engine.learning.AbstractTeacherSynapse;
import org.joone.engine.learning.ComparingElement;
import org.joone.exception.JooneRuntimeException;
import org.joone.helpers.structure.ConnectionHelper;
import org.joone.helpers.structure.NeuralNetMatrix;
import org.joone.io.StreamInputSynapse;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.script.MacroInterface;

// Referenced classes of package org.joone.net:
//			NeuralNetAttributes, NetCheck

public class NeuralNet
	implements NeuralLayer, NeuralNetListener, Serializable
{

	private static final int MAJOR_RELEASE = 2;
	private static final int MINOR_RELEASE = 0;
	private static final int BUILD = 0;
	private static final String SUFFIX = "RC2";
	private static final ILogger log = LoggerFactory.getLogger(org/joone/net/NeuralNet);
	private Vector layers;
	private String NetName;
	private Monitor mon;
	private Layer inputLayer;
	private Layer outputLayer;
	private ComparingElement teacher;
	private static final long serialVersionUID = 0x73e5273ccac3809aL;
	public static final int INPUT_LAYER = 0;
	public static final int HIDDEN_LAYER = 1;
	public static final int OUTPUT_LAYER = 2;
	protected Vector listeners;
	private MacroInterface macroPlugin;
	private boolean scriptingEnabled;
	private NeuralNetAttributes descriptor;
	private Hashtable params;
	private Layer orderedLayers[];
	private transient Layer intOrderedLayers[];
	private static final String OUTPUT_LAYER_FOR_NEURAL_NETWORK_NOT_FOUND = "Output layer for neural network not found.";
	private static final String INPUT_LAYER_FOR_NEURAL_NETWORK_NOT_FOUND = "Input layer for neural network not found.";
	private transient Thread singleThread;
	private boolean stopFastRun;

	public NeuralNet()
	{
		scriptingEnabled = false;
		descriptor = null;
		orderedLayers = null;
		intOrderedLayers = null;
		singleThread = null;
		layers = new Vector();
		mon = new Monitor();
	}

	public void start()
	{
		terminate(false);
		boolean readyToStart = readyToStart();
		if (!readyToStart)
		{
			String msg = "NeuralNet: The neural net is already running or start was interrupted.";
			log.warn(msg);
			throw new JooneRuntimeException(msg);
		}
		getMonitor().addNeuralNetListener(this, false);
		try
		{
			for (int i = 0; i < layers.size(); i++)
			{
				Layer ly = (Layer)layers.elementAt(i);
				ly.start();
			}

		}
		catch (RuntimeException rte)
		{
			stop();
			String msg = (new StringBuilder("RuntimeException was thrown while starting the neural network. Message is:")).append(rte.getMessage()).toString();
			log.error(msg, rte);
			throw new JooneRuntimeException(msg, rte);
		}
	}

	private boolean readyToStart()
	{
		for (int i = 0; i < 100; i++)
		{
			if (!isRunning())
				return true;
			try
			{
				Thread.sleep(10L);
			}
			catch (InterruptedException e)
			{
				log.warn("Interruption during starting of neural net. Aborting.");
				return false;
			}
		}

		return false;
	}

	public void join()
	{
		if (getMonitor().isSingleThreadMode())
		{
			if (getSingleThread() != null)
				try
				{
					getSingleThread().join();
				}
				catch (InterruptedException interruptedexception) { }
		} else
		{
			for (int i = 0; i < layers.size(); i++)
			{
				Layer ly = (Layer)layers.elementAt(i);
				ly.join();
			}

			if (teacher != null)
				teacher.getTheLinearLayer().join();
		}
	}

	public void stop()
	{
		if (getMonitor().isSingleThreadMode())
			stopFastRun();
		else
			getMonitor().Stop();
	}

	public void terminate(boolean notify)
	{
		if (!isRunning())
		{
			log.warn("Termination requested but net appears not to be running.");
			return;
		}
		for (int i = 0; i < layers.size(); i++)
		{
			Layer ly = (Layer)layers.elementAt(i);
			ly.stop();
		}

		if (teacher != null)
		{
			teacher.getTheLinearLayer().stop();
			if (teacher instanceof AbstractTeacherSynapse)
				((AbstractTeacherSynapse)teacher).netStoppedError();
		}
		Monitor monitor = getMonitor();
		if (monitor != null && notify)
			(new NetStoppedEventNotifier(monitor)).start();
	}

	public void terminate()
	{
		terminate(true);
	}

	protected int getNumOfstepCounters()
	{
		int count = 0;
		for (int i = 0; i < layers.size(); i++)
		{
			Layer ly = (Layer)layers.elementAt(i);
			if (ly.hasStepCounter())
				count++;
		}

		if (teacher != null)
		{
			StreamInputSynapse sis = teacher.getDesired();
			if (sis != null && sis.isStepCounter())
				count++;
		}
		return count;
	}

	public Layer getInputLayer()
	{
		if (inputLayer != null)
		{
			return inputLayer;
		} else
		{
			setInputLayer(findInputLayer());
			return inputLayer;
		}
	}

	public Layer findInputLayer()
	{
		Vector layersVect = getLayersWarningly();
		if (layersVect == null)
			return null;
		for (int i = 0; i < layersVect.size(); i++)
		{
			Layer ly = (Layer)layersVect.elementAt(i);
			if (ly.isInputLayer())
				return ly;
		}

		return null;
	}

	private Vector getLayersWarningly()
	{
		if (layers == null)
		{
			log.warn("Network has no layers");
			return null;
		} else
		{
			return layers;
		}
	}

	public Layer getOutputLayer()
	{
		if (outputLayer != null)
		{
			return outputLayer;
		} else
		{
			setOutputLayer(findOutputLayer());
			return outputLayer;
		}
	}

	public Layer findOutputLayer()
	{
		Vector layersVect = getLayersWarningly();
		if (layersVect == null)
			return null;
		for (int i = 0; i < layersVect.size(); i++)
		{
			Layer ly = (Layer)layersVect.elementAt(i);
			if (ly.isOutputLayer())
				return ly;
		}

		return null;
	}

	public int getRows()
	{
		Layer ly = getInputLayer();
		if (ly != null)
			return ly.getRows();
		else
			return 0;
	}

	public void setRows(int p1)
	{
		Layer ly = getInputLayer();
		if (ly == null)
		{
			log.warn("Input layer for neural network not found. setRows ignored.");
			return;
		} else
		{
			ly.setRows(p1);
			return;
		}
	}

	public void addNoise(double p1)
	{
		for (int i = 0; i < layers.size(); i++)
		{
			Layer ly = (Layer)layers.elementAt(i);
			ly.addNoise(p1);
		}

	}

	public void randomize(double amplitude)
	{
		for (int i = 0; i < layers.size(); i++)
		{
			Layer ly = (Layer)layers.elementAt(i);
			ly.randomize(amplitude);
		}

	}

	public Matrix getBias()
	{
		Layer ly = getInputLayer();
		if (ly == null)
		{
			log.warn("Input layer for neural network not found.");
			return null;
		} else
		{
			return ly.getBias();
		}
	}

	public Vector getAllOutputs()
	{
		Layer ly = getOutputLayer();
		if (ly == null)
			return null;
		else
			return ly.getAllOutputs();
	}

	public String getLayerName()
	{
		return NetName;
	}

	public void removeOutputSynapse(OutputPatternListener p1)
	{
		Layer ly = getOutputLayer();
		if (ly != null)
			ly.removeOutputSynapse(p1);
		if (p1 instanceof ComparingElement)
			setTeacher(null);
	}

	public void setAllInputs(Vector p1)
	{
		Layer ly = getInputLayer();
		if (ly == null)
		{
			log.warn("Input layer for neural network not found.");
			return;
		} else
		{
			ly.setAllInputs(p1);
			return;
		}
	}

	public void removeAllOutputs()
	{
		Layer ly = getOutputLayer();
		if (ly != null)
			ly.removeAllOutputs();
		setTeacher(null);
	}

	public Vector getAllInputs()
	{
		Layer ly = getInputLayer();
		if (ly == null)
			return null;
		else
			return ly.getAllInputs();
	}

	public boolean addOutputSynapse(OutputPatternListener p1)
	{
		Layer ly = getOutputLayer();
		if (ly == null)
			return false;
		else
			return ly.addOutputSynapse(p1);
	}

	public void setBias(Matrix p1)
	{
		Layer ly = getInputLayer();
		if (ly == null)
		{
			log.warn("Input layer for neural network not found.");
			return;
		} else
		{
			ly.setBias(p1);
			return;
		}
	}

	public void removeInputSynapse(InputPatternListener p1)
	{
		Layer ly = getInputLayer();
		if (ly == null)
		{
			log.warn("Input layer for neural network not found.");
			return;
		} else
		{
			ly.removeInputSynapse(p1);
			return;
		}
	}

	public void setLayerName(String p1)
	{
		NetName = p1;
	}

	public boolean addInputSynapse(InputPatternListener p1)
	{
		Layer ly = getInputLayer();
		if (ly == null)
		{
			log.warn("Input layer for neural network not found.");
			return false;
		} else
		{
			return ly.addInputSynapse(p1);
		}
	}

	public void setAllOutputs(Vector p1)
	{
		Layer ly = getOutputLayer();
		if (ly == null)
		{
			log.warn("Output layer for neural network not found.");
			return;
		} else
		{
			ly.setAllOutputs(p1);
			return;
		}
	}

	public void setMonitor(Monitor p1)
	{
		boolean isScriptingEnabled = isScriptingEnabled();
		mon = p1;
		for (int i = 0; i < layers.size(); i++)
		{
			Layer ly = (Layer)layers.elementAt(i);
			ly.setMonitor(mon);
		}

		setScriptingEnabled(isScriptingEnabled);
		if (getTeacher() != null)
			getTeacher().setMonitor(p1);
	}

	public Monitor getMonitor()
	{
		return mon;
	}

	public void removeAllInputs()
	{
		Layer ly = getInputLayer();
		if (ly == null)
		{
			log.warn("Input layer for neural network not found.");
			return;
		} else
		{
			ly.removeAllInputs();
			return;
		}
	}

	public NeuralLayer copyInto(NeuralLayer p1)
	{
		throw new UnsupportedOperationException();
	}

	public void addLayer(Layer layer)
	{
		addLayer(layer, 1);
	}

	public void addLayer(Layer layer, int tier)
	{
		if (!layers.contains(layer))
		{
			layer.setMonitor(mon);
			layers.addElement(layer);
		}
		if (tier == 0)
			setInputLayer(layer);
		if (tier == 2)
			setOutputLayer(layer);
	}

	public void removeLayer(Layer layer)
	{
		if (!layers.contains(layer))
		{
			log.warn("Neural net does not contain layer requested to be removed.");
			return;
		}
		layers.removeElement(layer);
		NeuralNetMatrix matrix = new NeuralNetMatrix(this);
		Synapse conn[][] = matrix.getConnectionMatrix();
		removeSynapses(matrix.getLayerInd(layer), conn);
		if (layer == inputLayer)
			setInputLayer(null);
		if (layer == outputLayer)
			setOutputLayer(null);
	}

	private void removeSynapses(int ind, Synapse conn[][])
	{
		if (ind < 0)
		{
			log.warn("Index out of range in removeSynapses");
			return;
		}
		Object removedLayer = layers.get(ind);
		for (int i = 0; i < conn.length; i++)
			if (conn[i][ind] != null)
				ConnectionHelper.disconnect(layers.get(i), removedLayer);

		for (int i = 0; i < conn[0].length; i++)
			if (conn[ind][i] != null)
				ConnectionHelper.disconnect(removedLayer, layers.get(i));

	}

	public void resetInput()
	{
		for (int i = 0; i < layers.size(); i++)
		{
			Layer ly = (Layer)layers.elementAt(i);
			Vector inputs = ly.getAllInputs();
			if (inputs != null)
			{
				for (int x = 0; x < inputs.size(); x++)
				{
					InputPatternListener syn = (InputPatternListener)inputs.elementAt(x);
					if (syn instanceof StreamInputSynapse)
						((StreamInputSynapse)syn).resetInput();
				}

			}
		}

		if (getTeacher() != null)
			getTeacher().resetInput();
	}

	public void addNeuralNetListener(NeuralNetListener listener)
	{
		if (getListeners().contains(listener))
		{
			log.warn("Provided listener already registered in network. Ignoring.");
			return;
		}
		listeners.addElement(listener);
		if (getMonitor() != null)
			getMonitor().addNeuralNetListener(listener);
	}

	public Vector getListeners()
	{
		if (listeners == null)
			listeners = new Vector();
		return listeners;
	}

	public void removeNeuralNetListener(NeuralNetListener listener)
	{
		Vector nnListeners = getListeners();
		if (!nnListeners.contains(listener))
		{
			log.warn("Provided listener not registered in network. Ignoring.");
			return;
		}
		nnListeners.removeElement(listener);
		Monitor monitor = getMonitor();
		if (monitor != null)
			monitor.removeNeuralNetListener(listener);
	}

	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		Vector lst = getListeners();
		Monitor monitor = getMonitor();
		if (monitor != null)
		{
			for (int i = 0; i < lst.size(); i++)
				monitor.addNeuralNetListener((NeuralNetListener)lst.elementAt(i));

		}
		setMacroPlugin(macroPlugin);
	}

	public static String getVersion()
	{
		return "2.0.0RC2";
	}

	public static Integer getNumericVersion()
	{
		return new Integer(0x1e8480);
	}

	public Layer getLayer(String layerName)
	{
		for (int i = 0; i < layers.size(); i++)
		{
			Layer ly = (Layer)layers.elementAt(i);
			if (ly.getLayerName().compareToIgnoreCase(layerName) == 0)
				return ly;
		}

		return null;
	}

	public Vector getLayers()
	{
		return layers;
	}

	public void setLayers(Vector newlayers)
	{
		layers = newlayers;
	}

	public void setLayersList(List list)
	{
		setLayers(new Vector(list));
	}

	public void setTeacher(ComparingElement ts)
	{
		teacher = ts;
	}

	public ComparingElement getTeacher()
	{
		return teacher;
	}

	public void setListeners(Vector listeners)
	{
		throw new UnsupportedOperationException();
	}

	public void setInputLayer(Layer newLayer)
	{
		inputLayer = newLayer;
	}

	public void setOutputLayer(Layer newLayer)
	{
		outputLayer = newLayer;
	}

	public NeuralNetAttributes getDescriptor()
	{
		if (descriptor == null)
		{
			descriptor = new NeuralNetAttributes();
			descriptor.setNeuralNetName(getLayerName());
		}
		return descriptor;
	}

	public void setDescriptor(NeuralNetAttributes newdescriptor)
	{
		descriptor = newdescriptor;
	}

	public boolean isRunning()
	{
		if (getMonitor().isSingleThreadMode())
		{
			if (getSingleThread() != null && getSingleThread().isAlive())
				return true;
		} else
		{
			for (int i = 0; i < layers.size(); i++)
			{
				Layer ly = (Layer)layers.elementAt(i);
				if (ly.isRunning())
					return true;
			}

			if (teacher != null && teacher.getTheLinearLayer().isRunning())
				return true;
		}
		return false;
	}

	public NeuralNet cloneNet()
	{
		NeuralNet newnet = null;
		try
		{
			ByteArrayOutputStream f = new ByteArrayOutputStream();
			ObjectOutput s = new ObjectOutputStream(f);
			s.writeObject(this);
			s.flush();
			ByteArrayInputStream fi = new ByteArrayInputStream(f.toByteArray());
			ObjectInput oi = new ObjectInputStream(fi);
			newnet = (NeuralNet)oi.readObject();
		}
		catch (Exception ioe)
		{
			log.warn((new StringBuilder("IOException while cloning the Net. Message is : ")).append(ioe.getMessage()).toString(), ioe);
		}
		return newnet;
	}

	public void removeAllListeners()
	{
		listeners = null;
		Monitor monitor = getMonitor();
		if (monitor != null)
			monitor.removeAllListeners();
	}

	public void setScriptingEnabled(boolean enabled)
	{
		scriptingEnabled = enabled;
		if (enabled)
		{
			NeuralNetListener listener = getMacroPlugin();
			if (listener == null)
				log.info("MacroPlugin not set: Impossible to enable the scripting");
			else
				addNeuralNetListener(getMacroPlugin());
		} else
		if (macroPlugin != null)
			removeNeuralNetListener(macroPlugin);
	}

	public boolean isScriptingEnabled()
	{
		return scriptingEnabled;
	}

	public MacroInterface getMacroPlugin()
	{
		return macroPlugin;
	}

	public void setMacroPlugin(MacroInterface macroPlugin)
	{
		if (macroPlugin != null)
		{
			removeNeuralNetListener(this.macroPlugin);
			if (scriptingEnabled)
				addNeuralNetListener(macroPlugin);
		}
		this.macroPlugin = macroPlugin;
		if (macroPlugin != null)
		{
			macroPlugin.set("jNet", this);
			macroPlugin.set("jMon", getMonitor());
		}
	}

	public Object getParam(String key)
	{
		if (params == null)
			return null;
		else
			return params.get(key);
	}

	public void setParam(String key, Object obj)
	{
		if (params == null)
			params = new Hashtable();
		if (params.containsKey(key))
			params.remove(key);
		if (obj != null)
			params.put(key, obj);
	}

	public String[] getKeys()
	{
		if (params == null)
			return null;
		String keys[] = new String[params.keySet().size()];
		Enumeration myEnum = params.keys();
		for (int i = 0; myEnum.hasMoreElements(); i++)
			keys[i] = (String)myEnum.nextElement();

		return keys;
	}

	public TreeSet check()
	{
		TreeSet checks = new TreeSet();
		if (layers == null || layers.isEmpty())
		{
			checks.add(new NetCheck(0, "The Neural Network doesn't contain any layer", mon));
			return checks;
		}
		if (getNumOfstepCounters() > 1)
			checks.add(new NetCheck(0, "More than one InputSynapse having stepCounter set to true is present", mon));
		for (int i = 0; i < layers.size(); i++)
		{
			Layer layer = (Layer)layers.elementAt(i);
			checks.addAll(layer.check());
		}

		if (mon.getParent() == null)
			if (teacher != null)
			{
				checks.addAll(teacher.check());
				if (mon != null && mon.isLearning() && !mon.isSupervised())
					checks.add(new NetCheck(1, "Teacher is present: the supervised property should be set to true", mon));
			} else
			if (mon != null && mon.isLearning() && mon.isSupervised())
				checks.add(new NetCheck(0, "Teacher not present: set to false the supervised property", mon));
		if (mon != null)
			checks.addAll(mon.check());
		return checks;
	}

	public void netStarted(NeuralNetEvent neuralnetevent)
	{
	}

	public void cicleTerminated(NeuralNetEvent neuralnetevent)
	{
	}

	public void netStopped(NeuralNetEvent neuralnetevent)
	{
	}

	public void errorChanged(NeuralNetEvent neuralnetevent)
	{
	}

	public void netStoppedError(NeuralNetEvent e, String error)
	{
		terminate(false);
	}

	public void setOrderedLayers(Layer orderedLayers[])
	{
		this.orderedLayers = orderedLayers;
	}

	public Layer[] getOrderedLayers()
	{
		return orderedLayers;
	}

	public Layer[] calculateOrderedLayers()
	{
		Layer oLayers[] = getOrderedLayers();
		if (oLayers == null)
		{
			if (intOrderedLayers == null)
			{
				NeuralNetMatrix matrix = new NeuralNetMatrix(this);
				intOrderedLayers = matrix.getOrderedLayers();
			}
		} else
		{
			intOrderedLayers = oLayers;
		}
		return intOrderedLayers;
	}

	public void go(boolean singleThreadMode, boolean sync)
	{
		getMonitor().setSingleThreadMode(singleThreadMode);
		go(sync);
	}

	public void go(boolean sync)
	{
		if (getMonitor().isSingleThreadMode())
		{
			Runnable runner = new Runnable() {

				final NeuralNet this$0;

				public void run()
				{
					fastRun();
				}

			
			{
				this$0 = NeuralNet.this;
				super();
			}
			};
			setSingleThread(new Thread(runner));
			getSingleThread().start();
		} else
		{
			start();
			getMonitor().Go();
		}
		if (sync)
			join();
	}

	public void go()
	{
		go(false);
	}

	public void restore(boolean sync)
	{
		if (getMonitor().isSingleThreadMode())
		{
			Runnable runner = new Runnable() {

				final NeuralNet this$0;

				public void run()
				{
					fastContinue();
				}

			
			{
				this$0 = NeuralNet.this;
				super();
			}
			};
			setSingleThread(new Thread(runner));
			getSingleThread().start();
		} else
		{
			start();
			getMonitor().runAgain();
		}
		if (sync)
			join();
	}

	public void restore()
	{
		restore(false);
	}

	protected void fastRun()
	{
		fastRun(getMonitor().getTotCicles(), false);
	}

	protected void fastContinue()
	{
		fastRun(getMonitor().getCurrentCicle(), true);
	}

	protected void fastRun(int firstEpoch, boolean continuation)
	{
		Monitor monitor = getMonitor();
		monitor.setSingleThreadMode(true);
		int epochs = firstEpoch;
		int patterns = monitor.getNumOfPatterns();
		Layer ordLayers[] = calculateOrderedLayers();
		if (!continuation)
		{
			int layersCount = ordLayers.length;
			for (int ly = 0; ly < layersCount; ly++)
				ordLayers[ly].init();

		}
		stopFastRun = false;
		monitor.fireNetStarted();
		for (int epoch = epochs; epoch > 0; epoch--)
		{
			monitor.setCurrentCicle(epoch);
			for (int p = 0; p < patterns; p++)
			{
				stepForward(null);
				if (monitor.isLearningCicle(p + 1))
					stepBackward(null);
			}

			monitor.fireCicleTerminated();
			if (stopFastRun)
				break;
		}

		Pattern stop = Pattern.makeStopPattern(ordLayers[0].getRows());
		stepForward(stop);
		monitor.fireNetStopped();
	}

	public void singleStepForward(Pattern pattern)
	{
		getMonitor().setSingleThreadMode(true);
		Layer ordLayers[] = calculateOrderedLayers();
		int layersCount = ordLayers.length;
		for (int ly = 0; ly < layersCount; ly++)
			ordLayers[ly].init();

		stepForward(pattern);
	}

	public void singleStepBackward(Pattern error)
	{
		getMonitor().setSingleThreadMode(true);
		stepBackward(error);
	}

	protected void stepForward(Pattern pattern)
	{
		Layer ordLayers[] = calculateOrderedLayers();
		int layersCount = ordLayers.length;
		ordLayers[0].fwdRun(pattern);
		for (int ly = 1; ly < layersCount; ly++)
			ordLayers[ly].fwdRun(null);

	}

	protected void stepBackward(Pattern error)
	{
		Layer ordLayers[] = calculateOrderedLayers();
		int layersCount = ordLayers.length;
		for (int ly = layersCount; ly > 0; ly--)
			ordLayers[ly - 1].revRun(error);

	}

	protected void stopFastRun()
	{
		stopFastRun = true;
	}

	protected Thread getSingleThread()
	{
		return singleThread;
	}

	protected void setSingleThread(Thread singleThread)
	{
		this.singleThread = singleThread;
	}

}
