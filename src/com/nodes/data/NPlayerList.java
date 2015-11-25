package com.nodes.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

public class NPlayerList
{
	private static HashMap<UUID,NPlayer> playerMap = new HashMap<UUID,NPlayer>();
	private static HashMap<String,UUID> playerNameMap = new HashMap<String,UUID>();
	private static HashSet<UUID> modifySet = new HashSet<UUID>();
	
	public static void add( NPlayer player )
	{
		playerMap.put(player.ID,player);
		playerNameMap.put(player.name,player.ID);
		modifySet.add(player.ID);
	}

	public static boolean contains( UUID ID )
	{
		return playerMap.containsKey(ID);
	}

	public static NPlayer get( UUID ID )
	{
		return playerMap.get(ID);
	}
	
	public static boolean contains( String name )
	{
		return playerNameMap.containsKey(name);
	}
	
	public static NPlayer get( String name )
	{
		return get(playerNameMap.get(name));
	}
	
	public static Iterator<UUID> saveIter()
	{
		return modifySet.iterator();
	}
	public static void saveClear()
	{
		modifySet.clear();
	}
	
	public static Iterator<NPlayer> saveAllIter()
	{
		return playerMap.values().iterator();
	}
	public static void flush()
	{
		playerMap.clear();
		playerNameMap.clear();
		modifySet.clear();
	}
}
