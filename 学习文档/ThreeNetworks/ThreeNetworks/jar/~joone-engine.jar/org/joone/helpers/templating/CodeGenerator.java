// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CodeGenerator.java

package org.joone.helpers.templating;

import java.beans.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.*;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.*;
import org.joone.helpers.structure.NeuralNetMatrix;
import org.joone.net.NeuralNet;
import org.joone.net.NeuralNetLoader;

public class CodeGenerator
{

	private VelocityContext context;
	private Hashtable objMap;

	public CodeGenerator()
		throws Exception
	{
		context = null;
		context = init();
	}

	public CodeGenerator(Properties props)
		throws Exception
	{
		context = null;
		context = init(props);
	}

	public static void main(String args[])
		throws Exception
	{
		CodeGenerator me = new CodeGenerator();
		NeuralNetLoader loader = new NeuralNetLoader("xor.snet");
		NeuralNet nnet = loader.getNeuralNet();
		String code = me.getCode(nnet, "codeTemplate.vm", "org.joone.test.templating", "TestClass");
		System.out.println(code);
	}

	protected VelocityContext init()
		throws Exception
	{
		Properties props = new Properties();
		props.setProperty("resource.loader", "file, class");
		props.setProperty("file.resource.loader.description", "Velocity File Resource Loader");
		props.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		props.setProperty("file.resource.loader.path", ".");
		props.setProperty("file.resource.loader.cache", "false");
		props.setProperty("file.resource.loader.modificationCheckInterval", "0");
		props.setProperty("class.resource.loader.description", "Velocity Classpath Resource Loader");
		props.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		return init(props);
	}

	protected VelocityContext init(Properties props)
		throws Exception
	{
		Velocity.init(props);
		objMap = new Hashtable();
		return new VelocityContext();
	}

	public String getCode(NeuralNet nnet, String templateName, String packageName, String className)
	{
		StringWriter sw = new StringWriter();
		try
		{
			NeuralNetMatrix nMatrix = new NeuralNetMatrix(nnet.cloneNet());
			context.put("netDescriptor", nMatrix);
			context.put("package", packageName);
			context.put("class", className);
			context.put("helper", this);
			Template template = null;
			try
			{
				template = Velocity.getTemplate(templateName);
				template.merge(context, sw);
			}
			catch (ResourceNotFoundException rnfe)
			{
				String message = "couldn't find the template";
				throw new Exception(message, rnfe);
			}
			catch (ParseErrorException pee)
			{
				String message = "syntax error : problem parsing the template";
				throw new Exception(message, pee);
			}
			catch (MethodInvocationException mie)
			{
				String message = "Exception threw in the template code";
				throw new Exception(message, mie);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		return sw.toString().trim();
	}

	public ArrayList getFilteredProperties(Object theObject)
	{
		ArrayList names = new ArrayList();
		try
		{
			BeanInfo beanInfo = Introspector.getBeanInfo(theObject.getClass());
			PropertyDescriptor props[] = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < props.length; i++)
			{
				PropertyDescriptor prop = props[i];
				Method getter = prop.getReadMethod();
				Method setter = prop.getWriteMethod();
				if (!prop.isHidden() && !prop.isExpert() && setter != null && getter != null)
				{
					String type = prop.getPropertyType().getName();
					Object value = getter.invoke(theObject, null);
					String sValue = null;
					if (value != null)
					{
						if (type.equals("java.lang.String"))
							sValue = (new StringBuilder("\"")).append((String)value).append("\"").toString();
						if (type.equals("java.io.File"))
							sValue = (new StringBuilder("new java.io.File(\"")).append(((File)value).getAbsolutePath()).append("\")").toString();
						if (type.equals("java.util.Date"))
							sValue = (new StringBuilder("new java.util.Date(\"")).append(DateFormat.getDateInstance().format((Date)value)).append("\")").toString();
						if (type.equals("int"))
							sValue = ((Integer)value).toString();
						if (type.equals("double"))
							sValue = ((Double)value).toString();
						if (type.equals("boolean"))
							sValue = ((Boolean)value).toString();
					} else
					{
						sValue = "null";
					}
					sValue = (new StringBuilder(String.valueOf(setter.getName()))).append("(").append(sValue).append(")").toString();
					names.add(sValue);
				}
			}

		}
		catch (IntrospectionException ex)
		{
			names.add("IntrospectionException_for_the_object");
			ex.printStackTrace();
		}
		catch (IllegalArgumentException ex)
		{
			names.add("IllegalArgumentException_for_the_object");
			ex.printStackTrace();
		}
		catch (InvocationTargetException ex)
		{
			names.add("InvocationTargetException_for_the_object");
			ex.printStackTrace();
		}
		catch (IllegalAccessException ex)
		{
			names.add("IllegalAccessException_for_the_object");
			ex.printStackTrace();
		}
		return names;
	}

	public void setObj(Object key, Object value)
	{
		if (!objMap.containsKey(key))
			objMap.put(key, value);
	}

	public Object getObj(Object key)
	{
		return objMap.get(key);
	}

	public boolean inherits(Object obj, String supClass)
	{
		Class cl = null;
		cl = Class.forName(supClass);
		return cl.isAssignableFrom(obj.getClass());
		ClassNotFoundException ex;
		ex;
		ex.printStackTrace();
		return false;
	}
}
