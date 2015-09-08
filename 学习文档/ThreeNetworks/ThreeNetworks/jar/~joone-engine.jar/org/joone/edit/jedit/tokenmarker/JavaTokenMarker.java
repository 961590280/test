// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JavaTokenMarker.java

package org.joone.edit.jedit.tokenmarker;

import org.joone.edit.jedit.KeywordMap;

// Referenced classes of package org.joone.edit.jedit.tokenmarker:
//			CTokenMarker

public class JavaTokenMarker extends CTokenMarker
{

	private static KeywordMap javaKeywords;

	public JavaTokenMarker()
	{
		super(false, getKeywords());
	}

	public static KeywordMap getKeywords()
	{
		if (javaKeywords == null)
		{
			javaKeywords = new KeywordMap(false);
			javaKeywords.add("package", (byte)7);
			javaKeywords.add("import", (byte)7);
			javaKeywords.add("byte", (byte)8);
			javaKeywords.add("char", (byte)8);
			javaKeywords.add("short", (byte)8);
			javaKeywords.add("int", (byte)8);
			javaKeywords.add("long", (byte)8);
			javaKeywords.add("float", (byte)8);
			javaKeywords.add("double", (byte)8);
			javaKeywords.add("boolean", (byte)8);
			javaKeywords.add("void", (byte)8);
			javaKeywords.add("class", (byte)8);
			javaKeywords.add("interface", (byte)8);
			javaKeywords.add("abstract", (byte)6);
			javaKeywords.add("final", (byte)6);
			javaKeywords.add("private", (byte)6);
			javaKeywords.add("protected", (byte)6);
			javaKeywords.add("public", (byte)6);
			javaKeywords.add("static", (byte)6);
			javaKeywords.add("synchronized", (byte)6);
			javaKeywords.add("native", (byte)6);
			javaKeywords.add("volatile", (byte)6);
			javaKeywords.add("transient", (byte)6);
			javaKeywords.add("break", (byte)6);
			javaKeywords.add("case", (byte)6);
			javaKeywords.add("continue", (byte)6);
			javaKeywords.add("default", (byte)6);
			javaKeywords.add("do", (byte)6);
			javaKeywords.add("else", (byte)6);
			javaKeywords.add("for", (byte)6);
			javaKeywords.add("if", (byte)6);
			javaKeywords.add("instanceof", (byte)6);
			javaKeywords.add("new", (byte)6);
			javaKeywords.add("return", (byte)6);
			javaKeywords.add("switch", (byte)6);
			javaKeywords.add("while", (byte)6);
			javaKeywords.add("throw", (byte)6);
			javaKeywords.add("try", (byte)6);
			javaKeywords.add("catch", (byte)6);
			javaKeywords.add("extends", (byte)6);
			javaKeywords.add("finally", (byte)6);
			javaKeywords.add("implements", (byte)6);
			javaKeywords.add("throws", (byte)6);
			javaKeywords.add("this", (byte)4);
			javaKeywords.add("null", (byte)4);
			javaKeywords.add("super", (byte)4);
			javaKeywords.add("true", (byte)4);
			javaKeywords.add("false", (byte)4);
			javaKeywords.add("jNet", (byte)6);
			javaKeywords.add("jMon", (byte)6);
		}
		return javaKeywords;
	}
}
