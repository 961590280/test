// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StandardNetStorageFormat.java

package org.joone.edit;

import java.io.*;
import javax.swing.filechooser.FileFilter;
import org.joone.net.NeuralNet;

// Referenced classes of package org.joone.edit:
//			NetStorageFormat

public class StandardNetStorageFormat
	implements NetStorageFormat
{

	private FileFilter myFileFilter;
	private String myFileExtension;
	private String myFileDescription;

	public StandardNetStorageFormat()
	{
		setFileExtension(createFileExtension());
		setFileDescription(createFileDescription());
		setFileFilter(createFileFilter());
	}

	protected String createFileExtension()
	{
		return myFileExtension = "snet";
	}

	public void setFileExtension(String newFileExtension)
	{
		myFileExtension = newFileExtension;
	}

	public String getFileExtension()
	{
		return myFileExtension;
	}

	public String createFileDescription()
	{
		return (new StringBuilder("Serialized NeuralNet (")).append(getFileExtension()).append(")").toString();
	}

	public void setFileDescription(String newFileDescription)
	{
		myFileDescription = newFileDescription;
	}

	public String getFileDescription()
	{
		return myFileDescription;
	}

	protected FileFilter createFileFilter()
	{
		return new FileFilter() {

			final StandardNetStorageFormat this$0;

			public boolean accept(File checkFile)
			{
				if (checkFile.isDirectory())
					return true;
				else
					return checkFile.getName().endsWith((new StringBuilder(".")).append(myFileExtension).toString());
			}

			public String getDescription()
			{
				return getFileDescription();
			}

			
			{
				this$0 = StandardNetStorageFormat.this;
				super();
			}
		};
	}

	public void setFileFilter(FileFilter newFileFilter)
	{
		myFileFilter = newFileFilter;
	}

	public FileFilter getFileFilter()
	{
		return myFileFilter;
	}

	public String store(String fileName, NeuralNet saveNeuralNet)
		throws IOException
	{
		FileOutputStream stream = new FileOutputStream(adjustFileName(fileName));
		ObjectOutput output = new ObjectOutputStream(stream);
		output.writeObject(saveNeuralNet);
		output.close();
		return adjustFileName(fileName);
	}

	public NeuralNet restore(String fileName)
		throws IOException
	{
		ObjectInput input;
		FileInputStream stream = new FileInputStream(fileName);
		input = new ObjectInputStream(stream);
		return (NeuralNet)input.readObject();
		ClassNotFoundException exception;
		exception;
		throw new IOException((new StringBuilder("Could not restore NeuralNet '")).append(fileName).append("': class not found!").toString());
	}

	public boolean equals(Object compareObject)
	{
		if (compareObject instanceof StandardNetStorageFormat)
			return getFileExtension().equals(((StandardNetStorageFormat)compareObject).getFileExtension());
		else
			return false;
	}

	public int hashCode()
	{
		return getFileExtension().hashCode();
	}

	protected String adjustFileName(String testFileName)
	{
		if (!hasCorrectFileExtension(testFileName))
			return (new StringBuilder(String.valueOf(testFileName))).append(".").append(getFileExtension()).toString();
		else
			return testFileName;
	}

	protected boolean hasCorrectFileExtension(String testFileName)
	{
		return testFileName.endsWith((new StringBuilder(".")).append(getFileExtension()).toString());
	}

}
