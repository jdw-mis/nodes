package com.nodes.data;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public class NFaction
{
	public UUID ID;
	public boolean peaceful;
	public boolean warzone;
	public boolean safezone;
	public boolean open;
	public double money;
	public long lastOnline;
	public Location home;
	public String name;
	public String description;
	public HashSet<UUID> invites;
	public HashMap<UUID,UUID> relations;	//first is target faction, second is relation UUID
	public HashMap<UUID,UUID> players;	//first is playerID, second is rank
	public UUID capitalNode;
	public HashMap<UUID,Integer> nodes;		//first is nodeID, second is "embeddedness"
	public HashMap<UUID,NRank> customRanks;	//they can define their own rank names and such
	public HashMap<String,UUID> customRankNameMap;
	public LinkedList<UUID> customRankOrder;


	public NFaction( String faction )
	{
		name = faction;
		ID = UUID.randomUUID();
		lastOnline = System.currentTimeMillis();
		invites = new HashSet<UUID>();
		relations = new HashMap<UUID,UUID>();
		players = new HashMap<UUID,UUID>();
		nodes = new HashMap<UUID,Integer>();
		customRanks = new HashMap<UUID,NRank>();
		customRankOrder = new LinkedList<UUID>();
		peaceful = false;
		warzone = false;
		safezone = false;
		money = 0.0;
		//TODO default shit
	}

	//Get Block
	public NRank	getRank(UUID i)			{ return customRanks.get(players.get(i)); }
	public UUID		getRankID(int i)		{ return customRankOrder.get(i); }
	public int		getRankIndex(UUID i)	{ return customRankOrder.indexOf(players.get(i)); }
	public NRelation getRelation(UUID i)	{ return NRelationList.get(relations.get(i)); }
	public int		getNodeEmbed(UUID i)	{ return nodes.get(i); }
	public UUID		getHigherRank(UUID i)	{ UUID ID = null; try{ID = customRankOrder.get(customRankOrder.indexOf(players.get(i))+1);}catch(IndexOutOfBoundsException e){} return ID; } //hue hue hue
	public UUID		getLowerRank(UUID i)	{ UUID ID = null; try{ID = customRankOrder.get(customRankOrder.indexOf(players.get(i))-1);}catch(IndexOutOfBoundsException e){} return ID; }

	//Check Block
	public boolean	isInvited(UUID i)	{ return invites.contains(i); }
	public boolean	isLastPlayer()		{ return players.size() == 1; }
	public boolean	hasPlayer(UUID i)	{ return players.containsKey(i); }
	public boolean	hasPlayer(NPlayer i){ return players.containsKey(i.ID); }
	public boolean	hasNode(UUID i)		{ return nodes.containsKey(i); }
	public boolean	hasNode(NNode i)	{ return nodes.containsKey(i.ID); }
	public boolean	hasRank(String i)	{ return customRankNameMap.containsKey(i.toLowerCase()); }

	//Set Block
	public void		addInvite(UUID i)			{ invites.add(i); }
	public void		addPlayer(UUID i)			{ players.put(i, customRankOrder.getFirst()); }
	public void		addPlayer(UUID i, UUID j)	{ players.put(i, j); }
	public void		addRelation(NRelation i)	{ relations.put(i.juniorID,i.ID); }
	public void		addNode(UUID node)			{ nodes.put(node,0); }

	//Delete Block
	public void		deleteInvite(UUID i)			{ invites.remove(i); }
	public void		deletePlayer(UUID player)		{ players.remove(player); }
	public void		deleteRelation(UUID faction)	{ relations.remove(faction); }
	public void		deleteNode(UUID node)			{ nodes.remove(node); }

	public Iterator<UUID>				getPlayerIter()			{ return players.keySet().iterator(); }
	public Iterator<UUID> 				getRelationIter() 		{ return relations.values().iterator(); }
	public Iterator<UUID> 				getRelateFactionIter() 	{ return relations.keySet().iterator(); }
	public Iterator<UUID> 				getNodeIter()			{ return nodes.keySet().iterator(); }
	public Set<Entry<UUID, UUID>>		getPlayerEntrySet()		{ return players.entrySet(); }
	
	public ChatColor getRelationColor( UUID faction )
	{
		NRelation relate = getRelation(faction);
		if(relate == null);
		else if(relate.ally)
			return NConfig.AlliedColor;
		else if(relate.enemy)
			return NConfig.EnemyColor;
		else if(relate.neutral)
			return NConfig.NeutralColor;
		return NConfig.UnrelateColor;
	}

	public void boilNodes()
	{
		boolean exposed,rise,continueBoil = true;
		int change,ascend;
		Integer temp;
	
		while(continueBoil)
		{
			continueBoil = false;
			for(UUID boiler : nodes.keySet())
			{
				exposed = false;
				rise = true;
				ascend = 0;
				change = 0;
				for(UUID tempNode : NNodeList.get(boiler).borderNode.keySet())
				{
					temp = nodes.get(tempNode);
					if(temp == null)
					{
						exposed = true;
						break;
					}
					else if((NConfig.CapitalNodeAlwaysEmbedded && NNodeList.get(boiler).capital)||
							(NConfig.CapitalSurroundingNodesAlwaysEmbedded && NNodeList.get(tempNode).capital))
					{
						if(nodes.get(boiler) < NConfig.EmbeddedNodeDefine)
							change = NConfig.EmbeddedNodeDefine;
						else
							change = nodes.get(boiler);
						exposed = true;
						break;
					}
					else
					{
						temp -= nodes.get(boiler);
						if(!(temp == 0 || temp == 1))
						{
							rise = false;
							if( temp < -1 && ascend > temp)
								ascend = temp;
						}
					}
				}
				if(exposed)
					change -= nodes.get(boiler);
				else if(rise || ascend != 0)
					change = ascend+1;
				if(change != 0)
				{
					nodes.put(boiler, nodes.get(boiler)+change);
					continueBoil = true;
				}
			}
		}
	}
}