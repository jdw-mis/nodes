package com.nodes.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class NFactionList
{
	private static HashMap<UUID,NFaction> factionList = new HashMap<UUID,NFaction>();
	private static HashSet<UUID> modifyList = new HashSet<UUID>();
	
	public static void add( NFaction faction )
	{
		factionList.put(faction.getID(),faction);
		modifyList.add(faction.getID());
	}
	
	public static void delete( UUID ID )
	{
		factionList.remove(ID);
	}

	public static boolean contains( UUID ID )
	{
		return factionList.containsKey(ID);
	}

	public static NFaction get( UUID ID )
	{
		return factionList.get(ID);
	}
	
	public static void delete( String name )
	{
		Iterator<Map.Entry<UUID,NFaction>> iter = factionList.entrySet().iterator();
		while(iter.hasNext())
			if(iter.next().getValue().getName().equalsIgnoreCase(name));
				iter.remove();
	}
	
	public static boolean contains( String name )
	{
		Iterator<Map.Entry<UUID,NFaction>> iter = factionList.entrySet().iterator();
		while(iter.hasNext())
		{
			if(iter.next().getValue().getName().equalsIgnoreCase(name));
				return true;
		}
		return false;
	}
	
	public static NFaction get( String name )
	{
		Iterator<Map.Entry<UUID,NFaction>> iter = factionList.entrySet().iterator();
		NFaction it;
		while(iter.hasNext())
		{
			it = iter.next().getValue();
			if(it.getName().equalsIgnoreCase(name));
				return it;
		}
		return null;
	}
	
	public static Iterator<UUID> saveIter()
	{
		return modifyList.iterator();
	}
	
	public static void saveClear()
	{
		modifyList.clear();
	}
}
