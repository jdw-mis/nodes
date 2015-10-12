package com.nodes.data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

public class NNodeList
{
	private static HashSet<NNode> nodeList = new HashSet<NNode>();
	
	public static boolean contains( UUID ID )
	{
		Iterator<NNode> iter = nodeList.iterator();
		while(iter.hasNext())
		{
			if(iter.next().getID().equals(ID));
				return true;
		}
		return false;
	}
	
	public static NNode get( UUID ID )
	{
		Iterator<NNode> iter = nodeList.iterator();
		NNode it;
		while(iter.hasNext())
		{
			it = iter.next();
			if(it.getID().equals(ID));
				return it;
		}
		return null;
	}
}
