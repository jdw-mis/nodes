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
		return NNodeList.i.get(currentNode);
	}

	public NFaction getFaction()
	{
		return NFactionList.i.get(faction);
	}

	public NRank getRank()
	{
		NFaction faction = getFaction();
		if(faction == null)
			return null;
		return getFaction().getRank(ID);
	}

	public int getRankIndex()
	{
		NFaction faction = getFaction();
		if(faction == null)
			return -1;
		return getFaction().getRankIndex(getRank().ID);
	}
	
	public String[] canWalk( NChunk chunk )
	{
		NNode node = NNodeList.i.get(chunk);
		NFaction fact = getFaction();
		boolean embedded = node.isEmbedded();
		NRelation relation = null;
		Boolean ret = true;
		String[] output = new String[2];
		if(fact != null)
			relation = fact.getRelation(node.faction);
		
		if(currentNode.equals(node.ID) || node.getFaction() == null);
		else if(node.faction.equals(fact) && fact != null)
			if(embedded)
				ret = getRank().walkEmbedded || !NConfig.i.EmbeddedNodeWalkingPrevention;
			else
				ret = getRank().walkExposed || !NConfig.i.ExposedNodeWalkingPrevention;
		else if(relation != null)
			if(embedded)
				ret = relation.moveEmbedded || !NConfig.i.EmbeddedNodeWalkingPrevention;
			else
				ret = relation.moveExposed || !NConfig.i.ExposedNodeWalkingPrevention;
		else if(embedded)
			ret = !NConfig.i.EmbeddedNodeWalkingPrevention;
		else
			ret = !NConfig.i.ExposedNodeWalkingPrevention;
		
		if(ret)
		{
			output[0] = "§6Welcome to "+node.name;
			if(node.coreChunk.equals(chunk.CID))
			{
				output[0] += "'s core";
				if(node.faction.equals(faction))
					ret = fact == null || getRank().walkCore;
				else if(!node.coreActive && relation != null && relation.enemy)
				{
					node.coreActive = true;
					NNodeList.i.add(node);
				}
			}
			output[0] += ", owned by ";
			if(fact == null)
				output[0] += NConfig.i.UnrelateColor+"no-one";
			else
				output[0] += fact.getRelationColor(node.faction)+fact.name;
		}
		if(!ret)
			output[0] = "§cNo Permissions to Walk Here!";
		output[1] = ret.toString();
		return output;
	}

	public static Comparator<UUID> playNameComp = new Comparator<UUID>()
	{
		public int compare(UUID o1, UUID o2)
		{
			return NPlayerList.i.get(o1).name.compareToIgnoreCase(NPlayerList.i.get(o2).name);
		}
	};

	public static Comparator<UUID> playNameRankComp = new Comparator<UUID>()
	{
		public int compare(UUID o1, UUID o2)
		{
			NPlayer first = NPlayerList.i.get(o1), second = NPlayerList.i.get(o2);
			if(first.getRankIndex() == second.getRankIndex())
				return first.name.compareToIgnoreCase(second.name);
			else
				return first.getRankIndex() < second.getRankIndex() ? -1 : 1;
		}
	};
}
