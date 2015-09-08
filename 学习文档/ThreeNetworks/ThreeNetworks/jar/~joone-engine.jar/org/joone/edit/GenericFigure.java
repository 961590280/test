// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GenericFigure.java

package org.joone.edit;

import CH.ifa.draw.framework.ConnectionFigure;
import java.util.Hashtable;

// Referenced classes of package org.joone.edit:
//			Wrapper

public interface GenericFigure
{

	public abstract boolean canConnect(GenericFigure genericfigure, ConnectionFigure connectionfigure);

	public abstract Wrapper getWrapper();

	public abstract Object getParam(Object obj);

	public abstract void setParam(Object obj, Object obj1);

	public abstract void setParams(Hashtable hashtable);
}
