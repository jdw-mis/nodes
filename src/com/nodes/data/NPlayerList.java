package com.nodes.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class NPlayerList
{
	private static HashMap<UUID,NPlayer> playerList = new HashMap<UUID,NPlayer>();
	private static HashSet<UUID> modifyList = new HashSet<UUID>();
	
	public static void add( NPlayer player )
	{
		playerList.put(player.getID(),player);
		modifyList.add(player.getID());
	}
	
	public static void delete( UUID ID )
	{
		playerList.remove(ID);
	}

	public static boolean contains( UUID ID )
	{
		return playerList.containsKey(ID);
	}

	public static NPlayer get( UUID ID )
	{
		return playerList.get(ID);
	}
	
	public static void delete( String name )
	{
		Iterator<Map.Entry<UUID,NPlayer>> iter = playerList.entrySet().iterator();
		while(iter.hasNext())
			if(iter.next().getValue().getName().equalsIgnoreCase(name));
				iter.remove();
	}
	
	public static boolean contains( String name )
	{
		Iterator<Map.Entry<UUID,NPlayer>> iter = playerList.entrySet().iterator();
		while(iter.hasNext())
		{
			if(iter.next().getValue().getName().equalsIgnoreCase(name));
				return true;
		}
		return false;
	}
	
	public static NPlayer get( String name )
	{
		Iterator<Map.Entry<UUID,NPlayer>> iter = playerList.entrySet().iterator();
		NPlayer it;
		while(iter.hasNext())
		{
			it = iter.next().getValue();
			if(it.getName().equalsIgnoreCase(name));
				return it;
		}
		return null;
	}
	
	public static Iterator<UUID> saveIter( UUID ID )
	{
		return modifyList.iterator();
	}
	
	public static void saveClear()
	{
		modifyList.clear();
	}
}
