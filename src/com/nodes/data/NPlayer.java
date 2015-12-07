package com.nodes.data;

import java.util.Comparator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
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
	public transient boolean inCore;
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
		inCore = false;
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
		return faction.getRank(ID);
	}

	public int getRankIndex()
	{
		NFaction faction = getFaction();
		if(faction == null)
			return -1;
		return getFaction().getRankIndex(getRank().ID);
	}
	
	public Player getPlayer()
	{
		return Bukkit.getPlayer(ID);
	}
	
	public String[] canWalk( Chunk chunk )
	{
		NNode node = NNodeList.i.get(chunk);
		NChunkID CID = new NChunkID(chunk);
		NFaction playFact = getFaction();
		NFaction nodeFact = node.getFaction();
		boolean embedded = node.isEmbedded();
		NRelation relation = null;
		String pRel = NConfig.i.UnrelateColor.toString();
		NRank pRank = getRank();
		if(nodeFact != null)
		{
			relation = nodeFact.getRelation(faction);
			pRel = nodeFact.getRelationColor(faction).toString();
		}
		Boolean canWalk = true;
		String[] output = {null,canWalk.toString()};
		
		if(node.ID.equals(currentNode))
		{
			if(inCore && !CID.equals(node.coreChunk)) //leave core message
			{
				inCore = false;
				output[0] = pRel+"You have left "+node.name+"'s Core.";
			}
			else if(!inCore && CID.equals(node.coreChunk)) //enter core message
			{
				output[0] = pRel+"Welcome to "+node.name+"'s Core";
				if(relation != null && !node.coreActive && relation.enemy)
				{
					inCore = true;
					node.coreActive = true;
					NNodeList.i.add(node);
				}
				else if(nodeFact == null || nodeFact.equals(playFact) || relation != null)
				{
					if(nodeFact == null || pRank.walkCore || relation.walkCore)
						inCore = true;
					else
						canWalk = false;
				}
			}
		}
		else if(CID.equals(node.coreChunk))
		{
			output[0] = pRel+"Welcome to "+node.name+"'s Core, owned by ";
			if(nodeFact == null) //yes
			{
				output[0] += "no-one.";
				inCore = true;
				node.coreActive = true;
				NNodeList.i.add(node);
			}
			else
			{
				output[0] += nodeFact.name+".";
				if(embedded && NConfig.i.EmbeddedNodeWalkingPrevention)
				{
					if(nodeFact.equals(playFact))
					{
						if(pRank.walkEmbedded && pRank.walkCore)
							inCore = true;
						else
							canWalk = false;
					}
					else if(relation != null)
					{
						if(relation.walkEmbedded && relation.walkCore)
							inCore = true;
						else
							canWalk = false;
					}
				}
				else if(!embedded && NConfig.i.ExposedNodeWalkingPrevention)
				{
					if(nodeFact.equals(playFact))
					{
						if(pRank.walkExposed && pRank.walkCore)
							inCore = true;
						else
							canWalk = false;
					}
					else if(relation != null)
					{
						if(relation.walkExposed && relation.walkCore)
							inCore = true;
						else
							canWalk = false;
					}
				}
				else
					inCore = true;
				if(relation != null && relation.enemy && canWalk)
				{
					node.coreActive = true;
					NNodeList.i.add(node);
				}
				if(canWalk == false)
					inCore = false;
			}
		}
		else
		{
			inCore = false;
			output[0] = pRel+"Welcome to "+node.name+", owned by ";
			if(nodeFact == null) //yes
				output[0] += "no-one.";
			else
			{
				output[0] += nodeFact.name+".";
				if(embedded && NConfig.i.EmbeddedNodeWalkingPrevention)
				{
					if(nodeFact.equals(playFact))
						canWalk = pRank.walkEmbedded;
					else if(relation != null)
						canWalk = relation.walkEmbedded;
				}
				else if(!embedded && NConfig.i.ExposedNodeWalkingPrevention)
				{
					if(nodeFact.equals(playFact))
						canWalk = pRank.walkExposed;
					else if(relation != null)
						canWalk = relation.walkExposed;
				}
			}
		}
		if(!canWalk)
			output[0] = "§cCannot Walk Here!";
		output[1] = canWalk.toString();
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
