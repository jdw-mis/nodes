package com.nodes.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

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
	private HashSet<UUID> invites;
	private HashMap<UUID,UUID> relations;	//first is target faction, second is relation UUID
	private HashMap<UUID,UUID> players;	//first is playerID, second is rank
	private HashMap<UUID,Integer> nodes;		//first is nodeID, second is "embeddedness"
	private HashMap<UUID,NRank> customRanks;	//they can define their own rank names and such
	private LinkedList<UUID> customRankOrder;
	
	
	public NFaction( String faction, UUID player )
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
		players.put(player, customRankOrder.getFirst());
	}
	
	//Get Block
    public NRank	getRank(UUID i)			{ return customRanks.get(players.get(i)); }
    public UUID		getRankID(int i)		{ return customRankOrder.get(i); }
    public int		getRankIndex(UUID i)	{ return customRankOrder.indexOf(players.get(i)); }
    public UUID	    getRelation(UUID i)		{ return relations.get(i); }
    public int		getNodeEmbed(UUID i)	{ return nodes.get(i); }
    public UUID		getHigherRank(UUID i)	{ UUID ID = null; try{ID = customRankOrder.get(customRankOrder.indexOf(players.get(i))+1);}catch(IndexOutOfBoundsException e){} return ID; } //hue hue hue
    public UUID		getLowerRank(UUID i)	{ UUID ID = null; try{ID = customRankOrder.get(customRankOrder.indexOf(players.get(i))-1);}catch(IndexOutOfBoundsException e){} return ID; }
    
    //Check Block
    public boolean	isInvited(UUID i)	{ return invites.contains(i); }
    public boolean	isPlayer(UUID i)	{ return players.containsKey(i); }
    public boolean	isLastPlayer()		{ return players.size() == 1; }
    
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
    
    public Iterator<UUID>				getPlayerIter()			{ return players.values().iterator(); }
    public Iterator<UUID> 				getRelationIter() 		{ return relations.values().iterator(); }
    public Iterator<UUID> 				getRelateFactionIter() 	{ return relations.keySet().iterator(); }
    public Iterator<UUID> 				getNodeIter()			{ return nodes.keySet().iterator(); }
    public Set<Entry<UUID, UUID>>		getPlayerEntrySet()		{ return players.entrySet(); }
    
    public void boilNodes()
    {
    	UUID boiler;
    	Integer temp;
    	
    	Iterator<UUID> iter = nodes.keySet().iterator();
    	Iterator<UUID> iterInner;
    	
    	boolean continueBoil = true;
    	int change;
    	
    	while(continueBoil)
    	{
    		continueBoil = false;
        	iter = nodes.keySet().iterator();
        	while(iter.hasNext())
        	{
        		boiler = iter.next();
        		iterInner = NNodeList.get(boiler).borderIter();
        		change = 0;
        		while(iterInner.hasNext())
        		{
        			temp = nodes.get(iterInner.next());
        			if( temp == null )
        			{
        				change += nodes.get(boiler);
        				break;
        			}
        			else
        			{
        				temp = nodes.get(boiler) - temp;
        				if( temp > 1 )
        				{
        					change = temp-1;
        					break;
        				}
        				else if( temp < -1 )
        				{
        					change = temp+1;
        					break;
        				}
        			}
        		}
        		if(change != 0)
        		{
        			nodes.put(boiler, nodes.get(boiler)-change);
        			continueBoil = true;
        		}
        	}
    	}
    }
    
}