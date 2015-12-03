package com.nodes.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;

public class NPlayerList
{
	private static HashMap<UUID,NPlayer> playerMap = new HashMap<UUID,NPlayer>();
	private static HashSet<UUID> modifySet = new HashSet<UUID>();

	public static void add( NPlayer player )
	{
		playerMap.put(player.ID,player);
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
		return contains(Bukkit.getPlayer(name).getUniqueId());
	}

	public static NPlayer get( String name )
	{
		return get(Bukkit.getPlayer(name).getUniqueId());
	}

	public static Iterator<UUID> saveIter()
	{
		return modifySet.iterator();
	}
	public static void saveClear()
	{
		modifySet.clear();
	}

	public static Collection<NPlayer> playerSet()
	{
		return playerMap.values();
	}

	public static void flush()
	{
		playerMap.clear();
		modifySet.clear();
	}
}
