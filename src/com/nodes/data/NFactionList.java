package com.nodes.data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

public class NFactionList
{
	private static HashSet<NFaction> factionList = new HashSet<NFaction>();
	
	public static void add( NFaction faction )
	{
		Iterator<NFaction> iter = factionList.iterator();
		while(iter.hasNext())
		{
			if(iter.next().getID().equals(faction.getID()));
			{
				iter.remove();
				break;
			}
		}
		factionList.add(faction);
	}
	
	public static void delete( String name )
	{
		Iterator<NFaction> iter = factionList.iterator();
		while(iter.hasNext())
			if(iter.next().getName().equalsIgnoreCase(name));
				iter.remove();
	}
	public static void delete( UUID ID )
	{
		Iterator<NFaction> iter = factionList.iterator();
		while(iter.hasNext())
			if(iter.next().getID().equals(ID));
				iter.remove();
	}
	
	public static boolean contains( String name )
	{
		Iterator<NFaction> iter = factionList.iterator();
		while(iter.hasNext())
		{
			if(iter.next().getName().equalsIgnoreCase(name));
				return true;
		}
		return false;
	}
	public static boolean contains( UUID ID )
	{
		Iterator<NFaction> iter = factionList.iterator();
		while(iter.hasNext())
		{
			if(iter.next().getID().equals(ID));
				return true;
		}
		return false;
	}
	
	public static NFaction get( String name )
	{
		Iterator<NFaction> iter = factionList.iterator();
		NFaction it;
		while(iter.hasNext())
		{
			it = iter.next();
			if(it.getName().equalsIgnoreCase(name));
				return it;
		}
		return null;
	}
	public static NFaction get( UUID ID )
	{
		Iterator<NFaction> iter = factionList.iterator();
		NFaction it;
		while(iter.hasNext())
		{
			it = iter.next();
			if(it.getID().equals(ID));
				return it;
		}
		return null;
	}
}
