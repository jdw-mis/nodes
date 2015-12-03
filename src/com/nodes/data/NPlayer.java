package com.nodes.data;

import java.util.Comparator;
import java.util.UUID;

import org.bukkit.entity.Player;

public class NPlayer
{
	public UUID ID;
	public String name;
	public String title;
	public UUID faction;
	public int kills;
	public int deaths;
	public long lastOnline;
	public long timeOnline;
	public boolean autoclaim;
	public boolean unautoclaim;
	public transient UUID currentNode;
	public transient UUID chatChannel;

	public NPlayer( Player player )
	{
		ID = player.getUniqueId();
		name = player.getName();
		title = "";
		faction = null;
		kills = 0;
		deaths = 0;
		lastOnline = System.currentTimeMillis();
		timeOnline = 0;
		autoclaim = false;
		unautoclaim = false;
		currentNode = null;
		chatChannel = null;
	}

	public NNode getNode()
	{
		return NNodeList.get(currentNode);
	}

	public NFaction getFaction()
	{
		return NFactionList.get(faction);
	}

	public NRank getRank()
	{
		return getFaction().getRank(ID);
	}
	
	public int getRankIndex()
	{
		NFaction faction = getFaction();
		if(faction == null)
			return -1;
		return getFaction().getRankIndex(getRank().ID);
	}
	
	public static Comparator<UUID> playNameComp = new Comparator<UUID>()
	{
		public int compare(UUID o1, UUID o2)
		{
			return NPlayerList.get(o1).name.compareToIgnoreCase(NPlayerList.get(o2).name);
		}
	};
	
	public Comparator<UUID> playNameRankComp = new Comparator<UUID>()
	{
		public int compare(UUID o1, UUID o2)
		{
			NPlayer first = NPlayerList.get(o1), second = NPlayerList.get(o2);
			if(first.getRankIndex() == second.getRankIndex())
				return first.name.compareToIgnoreCase(second.name);
			else
				return first.getRankIndex() < second.getRankIndex() ? -1 : 1;
		}
	};
}
