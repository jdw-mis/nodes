package com.nodes.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;

public class NPlayerList
{
	public static NPlayerList i = new NPlayerList();

	private HashMap<UUID,NPlayer> playerMap;
	private HashSet<UUID> modifySet;
	
	private NPlayerList()
	{
		playerMap = new HashMap<UUID,NPlayer>();
		modifySet = new HashSet<UUID>();
	}

	public void add( NPlayer player )
	{
		if(player != null)
		{
			playerMap.put(player.ID,player);
			modifySet.add(player.ID);
		}
	}
	
	public void remove( UUID ID )
	{
		playerMap.remove(ID);
		modifySet.add(ID);
	}

	public boolean contains( UUID ID )
	{
		return playerMap.containsKey(ID);
	}

	public NPlayer get( UUID ID )
	{
		return playerMap.get(ID);
	}

	public boolean contains( String name )
	{
		return contains(Bukkit.getPlayer(name).getUniqueId());
	}

	public NPlayer get( String name )
	{
		return get(Bukkit.getOfflinePlayer(name).getUniqueId());
	}

	public HashSet<UUID> modifySet()
	{
		return modifySet;
	}
	public void modifyClear()
	{
		modifySet.clear();
	}
	
	public Set<UUID> idSet()
	{
		return playerMap.keySet();
	}

	public Collection<NPlayer> playerSet()
	{
		return playerMap.values();
	}
	
	public List<UUID> players()
	{
		List<UUID> sortPlay = new ArrayList<UUID>(playerMap.keySet());
		Collections.sort(sortPlay, NPlayer.playNameComp);
		return sortPlay;
	}

	public void flush()
	{
		playerMap.clear();
		modifySet.clear();
	}
}
