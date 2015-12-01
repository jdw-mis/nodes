package com.nodes.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

public class NFactionList
{
	private static HashMap<UUID,NFaction> factionMap = new HashMap<UUID,NFaction>();
	private static HashMap<String,UUID> factionNameMap = new HashMap<String,UUID>();
	private static HashSet<UUID> modifySet = new HashSet<UUID>();

	public static void add( NFaction faction )
	{
		factionMap.put(faction.ID,faction);
		factionNameMap.put(faction.name.toLowerCase(),faction.ID);
		modifySet.add(faction.ID);
	}

	public static void delete( UUID ID )
	{
		factionNameMap.remove(factionMap.get(ID).name);
		factionMap.remove(ID);
		modifySet.remove(ID);
	}

	public static boolean contains( UUID ID )
	{
		return factionMap.containsKey(ID);
	}

	public static NFaction get( UUID ID )
	{
		return factionMap.get(ID);
	}

	public static void delete( String name )
	{
		delete(factionNameMap.get(name.toLowerCase()));
	}

	public static boolean contains( String name )
	{
		return factionNameMap.containsKey(name.toLowerCase());
	}

	public static NFaction get( String name )
	{
		return get(factionNameMap.get(name.toLowerCase()));
	}

	public static Iterator<UUID> saveIter()
	{
		return modifySet.iterator();
	}
	public static void saveClear()
	{
		modifySet.clear();
	}

	public static Iterator<NFaction> saveAllIter()
	{
		return factionMap.values().iterator();
	}
	public static void flush()
	{
		factionMap.clear();
		factionNameMap.clear();
		modifySet.clear();
	}
	public static void boilAll()
	{
		for(NFaction faction : factionMap.values())
		{
			faction.boilNodes();
		}
	}
}
