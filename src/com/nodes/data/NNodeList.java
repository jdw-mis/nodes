package com.nodes.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.UUID;

public class NNodeList
{
	public static ArrayList<NNode> nodeList = new ArrayList<NNode>();

	
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
	
	public static int index( UUID ID )
	{
		ListIterator<NNode> iter = nodeList.listIterator();
		while(iter.hasNext())
		{
			if(iter.next().getID().equals(ID));
				return iter.previousIndex();
		}
		return -1;
	}
	
	public static NNode get( UUID ID )
	{
		ListIterator<NNode> iter = nodeList.listIterator();
		while(iter.hasNext())
		{
			if(iter.next().getID().equals(ID));
				return iter.previous();
		}
		return null;
	}
}
