package com.nodes.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	private HashMap<UUID,Integer> nodes;		//first is nodeID, second is "embeddedness"
	public HashMap<UUID,NRank> customRanks;	//they can define their own rank names and such
	public HashMap<String,UUID> customRankNameMap;
	public LinkedList<UUID> customRankOrder;
	public transient boolean relList;


	public NFaction( String faction )
	{
		ID = UUID.randomUUID();
		peaceful = false;
		warzone = false;
		safezone = false;
		open = false;
		money = 0.0;
		lastOnline = System.currentTimeMillis();
		home = null;
		name = faction;
		description = "";
		invites = new HashSet<UUID>();
		relations = new HashMap<UUID,UUID>();
		players = new HashMap<UUID,UUID>();
		capitalNode = null;
		nodes = new HashMap<UUID,Integer>();
		customRanks = new HashMap<UUID,NRank>();
		customRankOrder = new LinkedList<UUID>(NConfig.i.StandardRankOrder);
		customRankNameMap = new HashMap<String,UUID>();
		for(NRank rank : NConfig.i.StandardRanks)
		{
			customRanks.put(rank.ID,rank);
			customRankNameMap.put(rank.rankName,rank.ID);
		}
		relList = false;
	}

	//Get Block
	public NNode	getCapital()			{ return NNodeList.i.get(capitalNode); }
	public NRank	getRank(UUID i)			{ return customRanks.get(players.get(i)); }
	public UUID		getRankID(int i)		{ return customRankOrder.get(i); }
	public int		getRankIndex(UUID i)	{ return customRankOrder.indexOf(players.get(i)); }
	public NRelation getRelation(UUID i)	{ return NRelationList.i.get(relations.get(i)); }
	public NRelation getRelationAbsolute(UUID i){ return NRelationList.i.getAbsolute(relations.get(i)); }
	public int		getNodeEmbed(UUID i)	{ return nodes.get(i); }
	public int		getNodesSize()			{ return nodes.size(); }
	public Set<UUID>nodeSet()				{ return nodes.keySet(); }
	public Set<UUID>relateFactionSet()		{ return relations.keySet(); }
	public UUID		getHigherRank(UUID i)	{ UUID ID = null; try{ID = customRankOrder.get(customRankOrder.indexOf(players.get(i))+1);}catch(IndexOutOfBoundsException e){} return ID; } //hue hue hue
	public UUID		getLowerRank(UUID i)	{ UUID ID = null; try{ID = customRankOrder.get(customRankOrder.indexOf(players.get(i))-1);}catch(IndexOutOfBoundsException e){} return ID; }
	public UUID		getHighestRank()		{ return customRankOrder.getLast(); }

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
	public void		addNode(UUID node)			{ if(nodes.isEmpty())capitalNode = node; nodes.put(node,0); }

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
		if(ID.equals(faction))
			return NConfig.i.AlliedColor;
		else if(relate == null);
		else if(relate.ally)
			return NConfig.i.AlliedColor;
		else if(relate.enemy)
			return NConfig.i.EnemyColor;
		else if(relate.neutral)
			return NConfig.i.NeutralColor;
		return NConfig.i.UnrelateColor;
	}

	public void delete()
	{
		NNode node;
		for(UUID NID : nodes.keySet())
		{
			node = NNodeList.i.get(NID);
			if(node != null)
			{
				node.faction = null;
				NNodeList.i.add(node);
			}
		}
		NPlayer player;
		for(UUID PID : players.keySet())
		{
			player = NPlayerList.i.get(PID);
			if(player != null)
			{
				player.faction = null;
				NPlayerList.i.add(player);
			}
		}
		NFaction faction;
		for(UUID FID : relations.keySet())
		{
			faction = NFactionList.i.get(FID);
			if(faction != null)
			{
				faction.relations.remove(ID);
				NFactionList.i.add(faction);
			}
		}
		NRelation relate;
		for(UUID RID : relations.values())
		{
			relate = NRelationList.i.get(RID);
			if(relate != null)
			{
				relate.clearPending();
				NRelationList.i.remove(RID);
			}
		}
		NFactionList.i.remove(ID);
	}

	public List<UUID> playersOnline()
	{
		List<UUID> sorted = new ArrayList<UUID>();
		for(UUID PID : players.keySet())
			if(Bukkit.getOfflinePlayer(PID).isOnline())
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
		List<UUID> sortRel = new ArrayList<UUID>();
		for(UUID RID : relations.values())
			if(NRelationList.i.get(RID) != null)
				sortRel.add(RID);
		return rel2fac(sortRel);
	}

	public List<UUID> allySort()
	{
		NRelation relate;
		List<UUID> sortRel = new ArrayList<UUID>();
		for(UUID RID : relations.values())
		{
			relate = NRelationList.i.get(RID);
			if(relate != null && relate.ally)
				sortRel.add(RID);
		}
		return rel2fac(sortRel);
	}

	public List<UUID> neutralSort()
	{
		NRelation relate;
		List<UUID> sortRel = new ArrayList<UUID>();
		for(UUID RID : relations.values())
		{
			relate = NRelationList.i.get(RID);
			if(relate != null && relate.neutral)
				sortRel.add(RID);
		}
		return rel2fac(sortRel);
	}

	public List<UUID> enemySort()
	{
		NRelation relate;
		List<UUID> sortRel = new ArrayList<UUID>();
		for(UUID RID : relations.values())
		{
			relate = NRelationList.i.get(RID);
			if(relate != null && relate.enemy)
				sortRel.add(RID);
		}
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

	public List<UUID> nodeSort()
	{
		NNode node;
		List<UUID> sortNode = new ArrayList<UUID>();
		for(UUID NID : nodes.keySet())
		{
			node = NNodeList.i.get(NID);
			if(node != null)
				sortNode.add(node.ID);
		}
		Collections.sort(sortNode,NNode.nodeNameComp);
		return sortNode;
	}

	public void boilNodes()
	{
		boolean exposed,rise,continueBoil = true;
		int change,ascend;
		Integer temp;
		NNode boilerN;
		NNode tempN;

		while(continueBoil)
		{
			continueBoil = false;
			for(UUID boiler : nodes.keySet())
			{
				boilerN = NNodeList.i.get(boiler);
				if(boilerN == null)
					continue;
				exposed = false;
				rise = true;
				ascend = 0;
				change = 0;
				for(UUID tempNode : boilerN.borderNode)
				{
					tempN = NNodeList.i.get(tempNode);
					temp = nodes.get(tempNode);
					if(tempN == null)
						continue;
					else if(tempN.filler)
						continue;
					else if(temp == null)
					{
						if(NConfig.i.AllySurroundingNodesAlwaysExposed && NConfig.i.FillerSurroundingNodesAlwaysExposed)
						{
							exposed = true;
							break;
						}
						NNode node = NNodeList.i.get(tempNode);
						if(node == null)
						{
							exposed = true;
							break;
						}
						if(node.filler && !NConfig.i.FillerSurroundingNodesAlwaysExposed)
							continue;
						if(NConfig.i.AllySurroundingNodesAlwaysExposed)
						{
							exposed = true;
							break;
						}
						NFaction borderer = node.getFaction();
						if(borderer == null)
						{
							exposed = true;
							break;
						}
						NRelation relate = getRelation(borderer.ID);
						if(relate == null || !relate.ally)
						{
							exposed = true;
							break;
						}
						continue;
					}
					else if((NConfig.i.CapitalNodeAlwaysEmbedded && boilerN.capital)||
							(NConfig.i.CapitalSurroundingNodesAlwaysEmbedded && tempN.capital))
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

	public static Comparator<UUID> factNameComp = new Comparator<UUID>()
	{
		public int compare(UUID o1, UUID o2)
		{
			return NFactionList.i.get(o1).name.compareToIgnoreCase(NFactionList.i.get(o2).name);
		}
	};

	public static Comparator<UUID> factNodeNameComp = new Comparator<UUID>()
	{
		public int compare(UUID o1, UUID o2)
		{
			NFaction first = NFactionList.i.get(o1), second = NFactionList.i.get(o2);
			if(first.nodes.size() == second.nodes.size())
				return first.name.compareToIgnoreCase(second.name);
			else
				return first.nodes.size() < second.nodes.size() ? -1 : 1;
		}
	};

	public static Comparator<UUID> factCountNameComp = new Comparator<UUID>()
	{
		public int compare(UUID o1, UUID o2)
		{
			NFaction first = NFactionList.i.get(o1), second = NFactionList.i.get(o2);
			if(first.players.size() == second.players.size())
				return first.name.compareToIgnoreCase(second.name);
			else
				return first.players.size() < second.players.size() ? -1 : 1;
		}
	};

	public static Comparator<UUID> factCountOnlineNameComp = new Comparator<UUID>()
	{
		public int compare(UUID o1, UUID o2)
		{
			NFaction first = NFactionList.i.get(o1), second = NFactionList.i.get(o2);
			List<UUID> sorted = new ArrayList<UUID>();
			for(UUID PID : first.players.keySet())
				if(Bukkit.getOfflinePlayer(PID).isOnline())
					sorted.add(PID);
			int firstSize = sorted.size();
			sorted.clear();
			for(UUID PID : second.players.keySet())
				if(Bukkit.getOfflinePlayer(PID).isOnline())
					sorted.add(PID);
			int secondSize = sorted.size();
			if(firstSize == secondSize)
				return first.name.compareToIgnoreCase(second.name);
			else
				return firstSize < secondSize ? -1 : 1;
		}
	};
}