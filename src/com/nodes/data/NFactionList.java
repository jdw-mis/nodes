package com.nodes.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

public class NFactionList
{
	public static NFactionList i = new NFactionList();

	private HashMap<UUID,NFaction> factionMap;
	private HashMap<String,UUID> factionNameMap;
	private HashSet<UUID> modifySet;
	
	private NFactionList()
	{
		factionMap = new HashMap<UUID,NFaction>();
		factionNameMap = new HashMap<String,UUID>();
		modifySet = new HashSet<UUID>();
	}
	
	public void add( NFaction faction )
	{
		if(faction != null)
		{
			factionMap.put(faction.ID,faction);
			factionNameMap.put(faction.name.toLowerCase(),faction.ID);
			modifySet.add(faction.ID);
		}
	}

	public void remove( UUID ID )
	{
		NFaction faction = factionMap.get(ID);
		if(faction != null)
			factionNameMap.get(faction.name);
		factionMap.remove(ID);
		modifySet.add(ID);
	}

	public boolean contains( UUID ID )
	{
		return factionMap.containsKey(ID);
	}

	public NFaction get( UUID ID )
	{
		return factionMap.get(ID);
	}

	public void remove( String name )
	{
		remove(factionNameMap.get(name.toLowerCase()));
	}

	public boolean contains( String name )
	{
		return factionNameMap.containsKey(name.toLowerCase());
	}

	public NFaction get( String name )
	{
		return get(factionNameMap.get(name.toLowerCase()));
	}

	public Iterator<UUID> saveIter()
	{
		return modifySet.iterator();
	}
	public void saveClear()
	{
		modifySet.clear();
	}

	public Collection<NFaction> factionSet()
	{
		return factionMap.values();
	}

	public void flush()
	{
		for(UUID FID : factionMap.keySet())
			remove(FID);
	}
	
	public void boilAll()
	{
		for(NFaction faction : factionMap.values())
		{
			faction.boilNodes();
		}
	}
}
