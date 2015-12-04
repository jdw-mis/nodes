package com.nodes.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
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
	public transient boolean relList;


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
	public NRelation getRelation(UUID i)	{ return NRelationList.i.get(relations.get(i)); }
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
			return NConfig.i.AlliedColor;
		else if(relate.enemy)
			return NConfig.i.EnemyColor;
		else if(relate.neutral)
			return NConfig.i.NeutralColor;
		return NConfig.i.UnrelateColor;
	}
	
	public List<UUID> playersOnline()
	{
		List<UUID> sorted = new ArrayList<UUID>();
		for(UUID PID : players.keySet())
			if(Bukkit.getPlayer(PID).isOnline())
				sorted.add(PID);
		Collections.sort(sorted,NPlayer.playNameRankComp);
		return sorted;
	}
	
	public List<UUID> playersOffline()
	{
		List<UUID> sorted = new ArrayList<UUID>();
		for(UUID PID : players.keySet())
			if(!Bukkit.getPlayer(PID).isOnline())
				sorted.add(PID);
		Collections.sort(sorted,NPlayer.playNameRankComp);
		return sorted;
	}
	
	public List<UUID> allRelated()
	{
		Collection<UUID> relates = relations.values();
		List<UUID> sortRel;
		if (relates instanceof List)
			sortRel = (List<UUID>)relates;
		else
			sortRel = new ArrayList<UUID>(relates);
		return rel2fac(sortRel);
	}
	
	public List<UUID> allies()
	{
		List<UUID> sortRel = new ArrayList<UUID>();
		for(UUID RID : relations.values())
			if(NRelationList.i.get(RID).ally)
				sortRel.add(RID);
		return rel2fac(sortRel);
	}
	
	public List<UUID> neutral()
	{
		List<UUID> sortRel = new ArrayList<UUID>();
		for(UUID RID : relations.values())
			if(NRelationList.i.get(RID).neutral)
				sortRel.add(RID);
		return rel2fac(sortRel);
	}
	
	public List<UUID> enemies()
	{
		List<UUID> sortRel = new ArrayList<UUID>();
		for(UUID RID : relations.values())
			if(NRelationList.i.get(RID).enemy)
				sortRel.add(RID);
		return rel2fac(sortRel);
	}
	
	private List<UUID> rel2fac(List<UUID> sortRel)
	{
		List<UUID> sortFac = new ArrayList<UUID>();
		NRelation relate;
		relList = true;
		Collections.sort(sortRel,NRelation.relationTypeComp);
		relList = false;
		for(UUID RID : sortRel)
		{
			relate = NRelationList.i.get(RID);
			if(ID.equals(relate.seniorID))
				sortFac.add(relate.juniorID);
			else
				sortFac.add(relate.seniorID);
		}
		return sortFac;
	}
	
	public List<UUID> nodes()
	{
		List<UUID> sortNode = new ArrayList<UUID>(nodes.keySet());
		Collections.sort(sortNode,NNode.nodeNameComp);
		return sortNode;
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
				for(UUID tempNode : NNodeList.i.get(boiler).borderNode)
				{
					temp = nodes.get(tempNode);
					if(temp == null)
					{
						if(NNodeList.i.get(tempNode).filler)
							continue;
						exposed = true;
						break;
					}
					else if((NConfig.i.CapitalNodeAlwaysEmbedded && NNodeList.i.get(boiler).capital)||
							(NConfig.i.CapitalSurroundingNodesAlwaysEmbedded && NNodeList.i.get(tempNode).capital))
					{
						if(nodes.get(boiler) < NConfig.i.EmbeddedNodeDefine)
							change = NConfig.i.EmbeddedNodeDefine;
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