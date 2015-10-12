package com.nodes.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;

public class NFaction
{
	private boolean peaceful;
	private boolean warzone;
	private boolean safezone;
	private boolean open;
	private double money;
	private long lastOnline;
	private UUID factionID;
	private Location home;
	private String name;
	private String description;
	private ArrayList<UUID> invites;
	private HashMap<UUID,UUID> players;		//first is playerID, second is rankID
	private HashMap<UUID,UUID> relations;	//first is factionID, second is relationID
	private HashMap<UUID,UUID> nodes;		//first is factionID, second is relationID
	
	
	public NFaction( String faction )
	{
		name = faction;
		//description = Config.configData.getString("default faction description");
		peaceful = false;
		warzone = false;
		safezone = false;
		//open = Boolean.parseBoolean(Config.configData.getString("factions open by default"));
		money = 0.0;
		lastOnline = System.currentTimeMillis();

		invites = new ArrayList<UUID>();
		relations = new HashMap<UUID,UUID>();
		players = new HashMap<UUID,UUID>();
		
		//TODO default rank shit
		//players.put(founder,);
		factionID = UUID.randomUUID();
	}
	
	
	//Get Block
    public UUID		getID()				{ return factionID; }
    public boolean	getPeaceful()		{ return peaceful; }
    public boolean	getWarzone()		{ return warzone; }
    public boolean	getSafezone()		{ return safezone; }
    public boolean	getOpen()			{ return open; }
    public double	getMoney()			{ return money; }
    public long		getLastOnline()		{ return lastOnline; }
    public String	getName()			{ return name; } 
    public String	getDescription()	{ return description; }
    public Location	getHome()			{ return home; }
    public UUID		getInvite(int i)	{ return invites.get(i); }
    public UUID		getRank(UUID i)		{ return players.get(i); }
    public UUID		getRelation(UUID i)	{ return relations.get(i); }
    
    //Check Block
    public boolean	isInvited(UUID i)	{ return invites.contains(i); }
    public boolean	isPlayer(UUID i)	{ return players.containsKey(i); }
    public boolean	isRelated(UUID i)	{ return relations.containsKey(i); }
    
    //Set Block
    public void		setPeaceful(boolean i)	{ peaceful = i; }
    public void		setWarzone(boolean i)	{ warzone = i; }
    public void		setSafezone(boolean i)	{ safezone = i; }
    public void		setOpen(boolean i)		{ open = i; }
    public void		setName(String i)		{ name = i; }
    public void		setDescription(String i){ description = i; }
    public void		setMoney(double i)		{ money = i; }
    public void		setMoney(int i)			{ money = i; }
    public void		addMoney(double i)		{ money += i; }
    public void		addMoney(int i)			{ money += i; }
    public void		addInvite(UUID i)		{ invites.add(i); }
    public void		addPlayer(UUID i)		{ /*players.put(TODO default rank, i);*/ }
    public void		addPlayer(UUID player, UUID rank)			{ players.put(player, rank); }
    public void		addRelation(UUID faction, UUID relation)	{ relations.put(faction, relation); }
    
    //Delete Block
    public void		deleteInvite(UUID i)			{ invites.remove(i); }
    public void		deletePlayer(UUID player)		{ players.remove(player); }
    public void		deleteRelation(UUID faction)	{ relations.remove(faction); }
}
