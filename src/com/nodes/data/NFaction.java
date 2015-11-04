package com.nodes.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Location;
import org.json.JSONArray;
import org.json.JSONObject;

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
	private HashMap<UUID,NRank> players;	//first is playerID, second is rank
	private HashMap<UUID,UUID> nodes;		//first is nodeID
	private LinkedList<NRank> customRanks;	//they can define their own rank names and such
	
	
	public NFaction( String faction, UUID player )
	{
		name = faction;
		ID = UUID.randomUUID();
		lastOnline = System.currentTimeMillis();
		invites = new HashSet<UUID>();
		relations = new HashMap<UUID,UUID>();
		players = new HashMap<UUID,NRank>();
		customRanks = new LinkedList<NRank>();
		peaceful = false;
		warzone = false;
		safezone = false;
		money = 0.0;
		//TODO default shit
		players.put(player, customRanks.getFirst());
	}
	
	
	//Get Block
    public NRank	getRank(UUID i)			{ return players.get(i); }
    public int		getRankIndex(UUID i)	{ return customRanks.indexOf(players.get(i)); }
    public NRank	getHigherRank(UUID i)	{ return customRanks.get(customRanks.indexOf(players.get(i))-1); }
    public NRank	getLowerRank(UUID i)	{ return customRanks.get(customRanks.indexOf(players.get(i))+1); }
    public UUID	    getRelation(UUID i)		{ return relations.get(i); }
    
    //Check Block
    public boolean	isInvited(UUID i)	{ return invites.contains(i); }
    public boolean	isPlayer(UUID i)	{ return players.containsKey(i); }
    
    //Set Block
    public void		addInvite(UUID i)			{ invites.add(i); }
    public void		addPlayer(UUID i, NRank j)	{ players.put(i, j); }
    public void		addPlayer(UUID i)			{ players.put(i, customRanks.getLast()); }
    public void		addRelation(NRelation i)	{ relations.put(i.juniorID,i.ID); }
    
    //Delete Block
    public void		deleteInvite(UUID i)			{ invites.remove(i); }
    public void		deletePlayer(UUID player)		{ players.remove(player); }
    public void		deleteRelation(UUID faction)	{ relations.remove(faction); }
    
	public JSONObject toJson()
	{
		JSONObject json = new JSONObject();
		json.put("ID",ID.toString());
		json.put("name",name);
		json.put("description",description);
		json.put("peaceful",peaceful);
		json.put("warzone",warzone);
		json.put("safezone",safezone);
		json.put("open",open);
		json.put("money",money);
		json.put("lastOnline",lastOnline);
		json.put("home", home);
		json.put("invites", invites);
		json.put("relations", relations);
		json.put("relations", nodes);
		
		JSONObject temp = new JSONObject();
		Iterator<NRank> iterRank = customRanks.iterator();
		while(iterRank.hasNext())
		{
			NRank rank = iterRank.next();
			temp.put(rank.rankName,rank.toJson());
		}
		json.put("customRanks", temp);

		temp = new JSONObject();
		JSONObject tempIn = new JSONObject();
		Iterator<Entry<UUID, NRank>> iterPlayer = players.entrySet().iterator();
		while(iterPlayer.hasNext())
		{
			//TODO
		}
		return json;
	}
}
