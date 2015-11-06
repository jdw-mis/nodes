package com.nodes.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

import org.bukkit.Location;
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
	private HashMap<UUID,UUID> players;	//first is playerID, second is rank
	private HashMap<UUID,UUID> nodes;		//first is nodeID
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
		customRanks = new HashMap<UUID,NRank>();
		customRankOrder = new LinkedList<UUID>();
		peaceful = false;
		warzone = false;
		safezone = false;
		money = 0.0;
		//TODO default shit
		players.put(player, customRankOrder.getFirst());
	}
	
	public NFaction(JSONObject json)
	{
		ID = UUID.fromString(json.getString("ID"));
		name = json.getString("name");
		description = json.getString("description");
		peaceful = json.getBoolean("peaceful");
		warzone = json.getBoolean("warzone");
		safezone = json.getBoolean("safezone");
		open = json.getBoolean("open");
		money = json.getDouble("money");
		lastOnline = json.getLong("lastOnline");
		//TODO: See how Location looks in file
	}
	
	//Get Block
    public NRank	getRank(UUID i)			{ return customRanks.get(players.get(i)); }
    public int		getRankIndex(UUID i)	{ return customRankOrder.indexOf(players.get(i)); }
    public UUID		getHigherRank(UUID i)	{ return customRankOrder.get(customRankOrder.indexOf(players.get(i))+1); }
    public UUID		getLowerRank(UUID i)	{ return customRankOrder.get(customRankOrder.indexOf(players.get(i))-1); }
    public UUID	    getRelation(UUID i)		{ return relations.get(i); }
    
    //Check Block
    public boolean	isInvited(UUID i)	{ return invites.contains(i); }
    public boolean	isPlayer(UUID i)	{ return players.containsKey(i); }
    
    //Set Block
    public void		addInvite(UUID i)			{ invites.add(i); }
    public void		addPlayer(UUID i)			{ players.put(i, customRankOrder.getFirst()); }
    public void		addPlayer(UUID i, UUID j)	{ players.put(i, j); }
    public void		addRelation(NRelation i)	{ relations.put(i.juniorID,i.ID); }
    
    //Delete Block
    public void		deleteInvite(UUID i)			{ invites.remove(i); }
    public void		deletePlayer(UUID player)		{ players.remove(player); }
    public void		deleteRelation(UUID faction)	{ relations.remove(faction); }
    
	public JSONObject toJson()
	{
		JSONObject json = new JSONObject();
		json.put("ID",ID);
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
		json.put("players", players);
		json.put("customRankOrder", customRankOrder);
		
		JSONObject temp = new JSONObject();
		Iterator<UUID> iterRank = customRankOrder.iterator();
		UUID rankID;
		while(iterRank.hasNext())
		{
			rankID = iterRank.next();
			temp.put(Integer.toString(customRankOrder.indexOf(rankID)),customRanks.get(rankID).toJson());
		}
		json.put("customRanks", temp);
		return json;
	}
}
