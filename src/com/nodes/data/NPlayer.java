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
	public UUID currentNode;
	public double money;
	public int kills;
	public int deaths;
	public long lastOnline;
	public long timeOnline;
	public boolean autoclaim;
	public boolean unautoclaim;
	public UUID chatChannel;

	public NPlayer( Player player )
	{
		ID = player.getUniqueId();
		name = player.getName();
		lastOnline = System.currentTimeMillis();
		title = "";
		faction = null;
		currentNode = null;
		chatChannel = null;
		autoclaim = false;
		unautoclaim = false;
		money = 0;
		kills = 0;
		deaths = 0;
		timeOnline = 0;
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
