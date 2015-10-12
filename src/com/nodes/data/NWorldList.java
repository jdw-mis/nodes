package com.nodes.data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

public class NWorldList
{
	private static HashSet<NWorld> worldList = new HashSet<NWorld>();
	
	public static void add( NWorld relation )
	{
		Iterator<NWorld> iter = worldList.iterator();
		while(iter.hasNext())
		{
			if(iter.next().getID().equals(relation.getID()));
			{
				iter.remove();
				break;
			}
		}
		worldList.add(relation);
	}
	
	public static void delete( UUID ID )
	{
		Iterator<NWorld> iter = worldList.iterator();
		while(iter.hasNext())
			if(iter.next().getID().equals(ID));
				iter.remove();
	}

	public static boolean contains( UUID ID )
	{
		Iterator<NWorld> iter = worldList.iterator();
		while(iter.hasNext())
		{
			if(iter.next().getID().equals(ID));
				return true;
		}
		return false;
	}

	public static NWorld get( UUID ID )
	{
		Iterator<NWorld> iter = worldList.iterator();
		NWorld it;
		while(iter.hasNext())
		{
			it = iter.next();
			if(it.getID().equals(ID));
				return it;
		}
		return null;
	}
}
