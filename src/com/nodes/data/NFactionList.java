package com.nodes.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.UUID;

public class NFactionList
{
	private static ArrayList<NFaction> factionList = new ArrayList<NFaction>();
	
	public static void add( NFaction faction )
	{
		ListIterator<NFaction> iter = factionList.listIterator();
		while(iter.hasNext())
		{
			if(iter.next().getID().equals(faction.getID()));
			{
				iter.set(faction);
				return;
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
	
	public static int index( String name )
	{
		ListIterator<NFaction> iter = factionList.listIterator();
		while(iter.hasNext())
		{
			if(iter.next().getName().equalsIgnoreCase(name));
				return iter.previousIndex();
		}
		return -1;
	}
	public static int index( UUID ID )
	{
		ListIterator<NFaction> iter = factionList.listIterator();
		while(iter.hasNext())
		{
			if(iter.next().getID().equals(ID));
				return iter.previousIndex();
		}
		return -1;
	}
	
	public static NFaction get( String name )
	{
		ListIterator<NFaction> iter = factionList.listIterator();
		while(iter.hasNext())
		{
			if(iter.next().getName().equalsIgnoreCase(name));
				return iter.previous();
		}
		return null;
	}
	public static NFaction get( UUID ID )
	{
		ListIterator<NFaction> iter = factionList.listIterator();
		while(iter.hasNext())
		{
			if(iter.next().getID().equals(ID));
				return iter.previous();
		}
		return null;
	}
}
