// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StreamInputSynapse.java

package org.joone.io;

import java.io.*;
import java.util.*;
import org.joone.engine.*;
import org.joone.exception.JooneRuntimeException;
import org.joone.inspection.Inspectable;
import org.joone.inspection.implementations.InputsInspection;
import org.joone.log.ILogger;
import org.joone.log.LoggerFactory;
import org.joone.net.NetCheck;
import org.joone.util.*;

// Referenced classes of package org.joone.io:
//			InputSynapse, PatternTokenizer, InputConnector

public abstract class StreamInputSynapse extends Synapse
	implements InputSynapse, Inspectable
{

	private static final ILogger log = LoggerFactory.getLogger(org/joone/io/StreamInputSynapse);
	private int firstRow;
	private int lastRow;
	private int firstCol;
	private int lastCol;
	private String advColumnsSel;
	private boolean buffered;
	private char decimalPoint;
	private boolean StepCounter;
	protected transient int cols[];
	protected transient Vector InputVector;
	protected transient int currentRow;
	protected transient PatternTokenizer tokens;
	protected transient boolean EOF;
	private ConverterPlugIn plugIn;
	private int maxBufSize;
	private static final long serialVersionUID = 0xd1fa4205c36eb821L;
	private transient int startFrom;
	protected List plugInListeners;
	private int myFirstRow;
	private int myLastRow;
	private transient boolean skipNewCycle;

	public StreamInputSynapse()
	{
		firstRow = 1;
		lastRow = 0;
		firstCol = 0;
		lastCol = 0;
		advColumnsSel = "";
		buffered = true;
		decimalPoint = '.';
		StepCounter = true;
		currentRow = 0;
		EOF = false;
		maxBufSize = 0;
		startFrom = 0;
		plugInListeners = new ArrayList();
		myFirstRow = 1;
		myLastRow = 0;
		skipNewCycle = false;
	}

	protected void backward(double ad[])
	{
	}

	protected void forward(double pattern[])
	{
		outs = pattern;
	}

	public synchronized Pattern fwdGet()
	{
		if (!isEnabled())
			return null;
		Monitor synapseMonitor = getMonitor();
		if (EOF || outs == null)
			try
			{
				if (EOF && synapseMonitor != null && isStepCounter())
					synapseMonitor.resetCycle();
				gotoFirstLine();
				outs = new double[1];
			}
			catch (Exception ioe)
			{
				handleExceptionWithError(ioe, "Exception while executing the \"fwdGet\".");
				return zeroPattern();
			}
		if (currentRow - firstRow > synapseMonitor.getNumOfPatterns() - 1)
			try
			{
				gotoFirstLine();
			}
			catch (Exception ioe)
			{
				handleExceptionWithError(ioe, "Exception while attempting to access the first line.");
				return zeroPattern();
			}
		if (isStepCounter())
		{
			boolean cont = synapseMonitor.nextStep();
			if (!cont)
			{
				reset();
				return zeroPattern();
			}
		}
		if (isBuffered())
		{
			int actualRow = (currentRow - firstRow) + (startFrom != 0 ? startFrom - 1 : 0);
			if (getInputVector().isEmpty())
			{
				readAll();
			} else
			{
				ConverterPlugIn plgIn = getPlugIn();
				if (currentRow == firstRow && plgIn != null && !skipNewCycle)
				{
					plgIn.setInputVector(getInputVector());
					if (plgIn.newCycle())
						fireDataChanged();
				}
			}
			Vector inpVect = getInputVector();
			int inpVectSize = inpVect.size();
			if (actualRow < inpVectSize)
				m_pattern = (Pattern)inpVect.elementAt(actualRow);
			if (actualRow + 1 >= inpVectSize)
				EOF = true;
			currentRow++;
			setEofOnEofCondition();
		} else
		{
			try
			{
				m_pattern = getStream();
			}
			catch (Exception ioe)
			{
				handleExceptionWithError(ioe, "Exception");
				ioe.printStackTrace();
				return zeroPattern();
			}
		}
		m_pattern.setCount(currentRow - firstRow);
		return m_pattern;
	}

	public char getDecimalPoint()
	{
		return decimalPoint;
	}

	public int getFirstRow()
	{
		return firstRow;
	}

	protected Vector getInputVector()
	{
		if (InputVector == null)
			InputVector = new Vector();
		return InputVector;
	}

	private void setInputVector(Vector newInpVect)
	{
		InputVector = newInpVect;
	}

	public int getLastRow()
	{
		return lastRow;
	}

	protected Pattern getStream()
		throws IOException
	{
		EOF = !tokens.nextLine();
		if (EOF)
			return null;
		if (cols == null)
			setColList();
		if (cols == null)
			return null;
		inps = new double[cols.length];
		for (int x = 0; x < cols.length; x++)
			inps[x] = tokens.getTokenAt(cols[x] - 1);

		currentRow++;
		setEofOnEofCondition();
		forward(inps);
		m_pattern = new Pattern(outs);
		m_pattern.setCount(currentRow - firstRow);
		return m_pattern;
	}

	private void setEofOnEofCondition()
	{
		if (lastRow > 0 && currentRow > lastRow)
			EOF = true;
	}

	public void gotoFirstLine()
		throws IOException
	{
		gotoLine(firstRow);
	}

	public void gotoLine(int numLine)
		throws IOException
	{
		EOF = false;
		if (!isBuffered() || getInputVector().isEmpty())
		{
			PatternTokenizer tk = getTokens();
			if (tk != null)
			{
				if (isBuffered())
					tk.resetInput();
				else
					initInputStream();
				EOF = false;
				for (currentRow = 1; currentRow < numLine && !EOF;)
					if (tokens.nextLine())
						currentRow++;
					else
						EOF = true;

			}
		}
		currentRow = numLine;
		if (lastRow > 0 && currentRow >= lastRow)
			EOF = true;
		else
			EOF = false;
	}

	public boolean isBuffered()
	{
		return buffered;
	}

	public boolean isEOF()
	{
		return EOF;
	}

	public boolean isStepCounter()
	{
		if (getMonitor() != null && getMonitor().isSingleThreadMode())
			return false;
		else
			return StepCounter;
	}

	public int numColumns()
	{
		if (cols == null)
			setColList();
		if (cols == null)
			return 0;
		else
			return cols.length;
	}

	public void readAll()
	{
		getInputVector().removeAllElements();
		try
		{
			gotoFirstLine();
			for (Pattern ptn = getStream(); ptn != null; ptn = getStream())
			{
				InputVector.addElement(ptn);
				if (EOF)
					break;
			}

			ConverterPlugIn plgIn = getPlugIn();
			if (plgIn != null)
			{
				plgIn.setInputVector(InputVector);
				plgIn.convertPatterns();
			}
			gotoFirstLine();
		}
		catch (IOException ioe)
		{
			handleExceptionWithWarn(ioe, "IOException");
		}
		catch (NumberFormatException nfe)
		{
			handleExceptionWithWarn(nfe, "NumberFormatException");
		}
	}

	public synchronized void revPut(Pattern pattern)
	{
	}

	protected void setArrays(int i, int j)
	{
	}

	public void setBuffered(boolean aNewBuffered)
	{
		if (plugIn == null)
			buffered = aNewBuffered;
		else
		if (!aNewBuffered)
		{
			log.warn("Synapse is connected to plugin so that it has to be buffered. Ignoring setBuffered(false).");
			buffered = true;
		}
	}

	protected void setColList()
	{
		if (getAdvancedColumnSelector().trim().length() > 0)
		{
			CSVParser parser = new CSVParser(getAdvancedColumnSelector().trim());
			try
			{
				cols = parser.parseInt();
			}
			catch (NumberFormatException nfe)
			{
				new NetErrorManager(getMonitor(), nfe.getMessage());
			}
		} else
		{
			if (getFirstCol() == 0 || getLastCol() == 0)
				return;
			cols = new int[(getLastCol() - getFirstCol()) + 1];
			for (int i = getFirstCol(); i <= getLastCol(); i++)
				cols[i - getFirstCol()] = i;

		}
	}

	public void setDecimalPoint(char dp)
	{
		decimalPoint = dp;
		if (tokens != null)
			tokens.setDecimalPoint(dp);
	}

	protected void setDimensions(int i, int j)
	{
	}

	protected void setEOF(boolean newEOF)
	{
		EOF = newEOF;
	}

	public void setFirstRow(int newFirstRow)
	{
		myFirstRow = firstRow;
		if (firstRow != newFirstRow)
		{
			firstRow = newFirstRow;
			resetInput();
		}
	}

	public synchronized void resetInput()
	{
		restart();
		tokens = null;
		notifyAll();
	}

	protected void restart()
	{
		getInputVector().removeAllElements();
		EOF = false;
		cols = null;
	}

	public void setLastRow(int newLastRow)
	{
		myLastRow = lastRow;
		if (lastRow != newLastRow)
		{
			lastRow = newLastRow;
			resetInput();
		}
	}

	public boolean addPlugIn(ConverterPlugIn aNewPlugIn)
	{
		if (plugIn == aNewPlugIn)
		{
			log.warn("Plugin already connected to plugin stack. Ignoring request.");
			return false;
		}
		if (aNewPlugIn == null)
		{
			if (plugIn != null)
				plugIn.setConnected(false);
			plugIn = null;
			resetInput();
			return true;
		}
		if (aNewPlugIn.isConnected())
		{
			log.warn("Plugin already connected. Ignoring request.");
			return false;
		}
		if (plugIn == null)
		{
			aNewPlugIn.setConnected(true);
			aNewPlugIn.addPlugInListener(this);
			if (!isBuffered())
				log.warn("Use of plugins requires buffering. Enabling. ");
			setBuffered(true);
			plugIn = aNewPlugIn;
			resetInput();
			return true;
		} else
		{
			return plugIn.addPlugIn(aNewPlugIn);
		}
	}

	public void removeAllPlugIns()
	{
		if (plugIn != null)
		{
			plugIn.setConnected(false);
			plugIn.removeAllPlugIns();
			plugIn = null;
		}
	}

	/**
	 * @deprecated Method setPlugin is deprecated
	 */

	public boolean setPlugin(ConverterPlugIn newPlugIn)
	{
		if (newPlugIn == plugIn)
			return false;
		if (newPlugIn == null)
		{
			plugIn.setConnected(false);
		} else
		{
			if (newPlugIn.isConnected())
				return false;
			newPlugIn.setConnected(true);
			newPlugIn.addPlugInListener(this);
			buffered = true;
		}
		plugIn = newPlugIn;
		resetInput();
		return true;
	}

	public ConverterPlugIn getPlugIn()
	{
		return plugIn;
	}

	public void setPlugIn(ConverterPlugIn newPlugIn)
	{
		setPlugin(newPlugIn);
	}

	public void setStepCounter(boolean newStepCounter)
	{
		StepCounter = newStepCounter;
	}

	protected void writeObjectBase(ObjectOutputStream out)
		throws IOException
	{
		int s = 0;
		if (isBuffered())
		{
			s = getInputVector().size();
			if (s == 0 && tokens != null)
			{
				gotoFirstLine();
				readAll();
			}
		}
		if (out.getClass().getName().indexOf("xstream") != -1)
		{
			out.defaultWriteObject();
			if (isBuffered())
				out.writeObject(InputVector);
		} else
		{
			out.defaultWriteObject();
			if (isBuffered())
			{
				s = getInputVector().size();
				out.writeInt(s);
				for (int i = 0; i < s; i++)
					out.writeObject(InputVector.elementAt(i));

			}
		}
	}

	protected void readObjectBase(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		if (in.getClass().getName().indexOf("xstream") != -1)
		{
			if (isBuffered())
				setInputVector((Vector)in.readObject());
		} else
		if (isBuffered())
		{
			Vector inputVect = getInputVector();
			inputVect.removeAllElements();
			int s = in.readInt();
			for (int i = 0; i < s; i++)
			{
				Pattern ptn = (Pattern)in.readObject();
				inputVect.addElement(ptn);
			}

		}
		if (advColumnsSel == null)
			advColumnsSel = "";
		if (plugInListeners == null)
			plugInListeners = new ArrayList();
	}

	/**
	 * @deprecated Method getLastCol is deprecated
	 */

	public int getLastCol()
	{
		return lastCol;
	}

	/**
	 * @deprecated Method setLastCol is deprecated
	 */

	public void setLastCol(int lastCol)
		throws IllegalArgumentException
	{
		if (this.lastCol != lastCol)
		{
			this.lastCol = lastCol;
			cols = null;
		}
	}

	/**
	 * @deprecated Method getFirstCol is deprecated
	 */

	public int getFirstCol()
	{
		return firstCol;
	}

	/**
	 * @deprecated Method setFirstCol is deprecated
	 */

	public void setFirstCol(int firstCol)
		throws IllegalArgumentException
	{
		if (this.firstCol != firstCol)
		{
			this.firstCol = firstCol;
			cols = null;
		}
	}

	public String getAdvancedColumnSelector()
	{
		return advColumnsSel;
	}

	public void setAdvancedColumnSelector(String newAdvColSel)
	{
		if (advColumnsSel.compareTo(newAdvColSel) != 0)
		{
			advColumnsSel = newAdvColSel;
			resetInput();
		}
	}

	public void dataChanged(PlugInEvent data)
	{
		resetInput();
	}

	protected PatternTokenizer getTokens()
		throws JooneRuntimeException
	{
		if (tokens == null)
			initInputStream();
		return tokens;
	}

	protected void setTokens(PatternTokenizer tkn)
	{
		tokens = tkn;
		if (tokens != null)
			tokens.setDecimalPoint(getDecimalPoint());
		restart();
	}

	protected Pattern zeroPattern()
	{
		Pattern pat = Pattern.makeStopPattern(getOutputDimension());
		return pat;
	}

	protected abstract void initInputStream()
		throws JooneRuntimeException;

	public TreeSet check()
	{
		TreeSet checks = super.check();
		if (firstRow <= 0)
			checks.add(new NetCheck(0, "First Row parameter cannot be less than 1.", this));
		if (advColumnsSel == null || advColumnsSel.trim().length() == 0)
			checks.add(new NetCheck(0, "Columns selector not set.", this));
		if (isBuffered() && getInputVector().isEmpty())
			try
			{
				getTokens();
			}
			catch (JooneRuntimeException jre)
			{
				checks.add(new NetCheck(0, (new StringBuilder("Cannot initialize the input stream: ")).append(jre.getMessage()).toString(), this));
			}
		if (getPlugIn() != null)
		{
			if (!isBuffered())
				checks.add(new NetCheck(0, "Synapse has some plugins connected but it is not buffered", this));
			getPlugIn().check(checks);
		}
		return checks;
	}

	public Collection Inspections()
	{
		Collection col = new ArrayList();
		if (isBuffered())
		{
			if (getInputVector().isEmpty() && getTokens() != null)
				readAll();
			col.add(new InputsInspection(getInputVector()));
		} else
		{
			col.add(new InputsInspection(null));
		}
		return col;
	}

	public String InspectableTitle()
	{
		return getName();
	}

	public void reset()
	{
		super.reset();
		outs = null;
	}

	public int getMaxBufSize()
	{
		return maxBufSize;
	}

	public void setMaxBufSize(int maxBufSize)
	{
		this.maxBufSize = maxBufSize;
	}

	public Vector getInputPatterns()
	{
		return InputVector;
	}

	public void setInputPatterns(Vector inputPatterns)
	{
		InputVector = inputPatterns;
	}

	public synchronized Pattern fwdGet(InputConnector conn)
	{
		if (isBuffered() && getInputVector().size() == 0)
		{
			firstRow = myFirstRow;
			lastRow = myLastRow;
			startFrom = 0;
			skipNewCycle = false;
			readAll();
		}
		if (conn == null)
		{
			return null;
		} else
		{
			skipNewCycle = !conn.isStepCounter();
			firstRow = conn.getFirstRow();
			lastRow = conn.getLastRow();
			EOF = conn.isEOF();
			currentRow = conn.getCurrentRow();
			startFrom = firstRow;
			Pattern retValue = fwdGet();
			firstRow = myFirstRow;
			lastRow = myLastRow;
			startFrom = 0;
			return retValue;
		}
	}

	public int getCurrentRow()
	{
		return currentRow;
	}

	public void addPlugInListener(PlugInListener aListener)
	{
		if (!plugInListeners.contains(aListener))
			plugInListeners.add(aListener);
	}

	public void removePlugInListener(PlugInListener aListener)
	{
		plugInListeners.remove(aListener);
	}

	public List getAllPlugInListeners()
	{
		return plugInListeners;
	}

	protected void fireDataChanged()
	{
		Object myList[];
		synchronized (this)
		{
			myList = getAllPlugInListeners().toArray();
		}
		for (int i = 0; i < myList.length; i++)
			if (myList[i] != null)
				((PlugInListener)myList[i]).dataChanged(new PlugInEvent(this));

	}

	private void handleExceptionWithWarn(Exception ex, String errMsg)
	{
		handleExceptionWithWarn(ex, errMsg, log);
	}

	protected void handleExceptionWithWarn(Exception ex, String errMsg, ILogger logger)
	{
		String errorWithSynapseName = (new StringBuilder(String.valueOf(errMsg))).append(" in ").append(getSynapseNameSafely()).append(". Message is : ").append(ex.getMessage()).toString();
		logger.warn(errorWithSynapseName, ex);
		if (getMonitor() != null)
			new NetErrorManager(getMonitor(), errorWithSynapseName);
	}

	private void handleExceptionWithError(Exception ex, String errMsg)
	{
		handleExceptionWithError(ex, errMsg, log);
	}

	protected void handleExceptionWithError(Exception ex, String errMsg, ILogger log)
	{
		String errorWithSynapseName = (new StringBuilder(String.valueOf(errMsg))).append(" in ").append(getSynapseNameSafely()).append(". Message is : ").append(ex.getMessage()).toString();
		log.error(errorWithSynapseName, ex);
		if (getMonitor() != null)
			new NetErrorManager(getMonitor(), errorWithSynapseName);
	}

}
