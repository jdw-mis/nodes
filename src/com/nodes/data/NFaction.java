package com.nodes.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
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
	private HashSet<UUID> invites;
	private HashMap<UUID,UUID> relations;	//first is target faction, second is relation UUID
	private HashMap<UUID,NRank> players;	//first is playerID, second is rank
	private HashMap<UUID,UUID> nodes;		//first is nodeID
	private LinkedList<NRank> customTemplates;	//they can define their own rank names and such
	
	
	public NFaction( String faction, UUID player )
	{
		name = faction;
		factionID = UUID.randomUUID();
		lastOnline = System.currentTimeMillis();
		invites = new HashSet<UUID>();
		relations = new HashMap<UUID,UUID>();
		players = new HashMap<UUID,NRank>();
		customTemplates = new LinkedList<NRank>();
		peaceful = false;
		warzone = false;
		safezone = false;
		money = 0.0;
		//TODO default shit
		players.put(player, customTemplates.getFirst());
	}
	
	
	//Get Block
    public UUID		getID()					{ return factionID; }
    public boolean	getPeaceful()			{ return peaceful; }
    public boolean	getWarzone()			{ return warzone; }
    public boolean	getSafezone()			{ return safezone; }
    public boolean	getOpen()				{ return open; }
    public double	getMoney()				{ return money; }
    public long		getLastOnline()			{ return lastOnline; }
    public String	getName()				{ return name; } 
    public String	getDescription()		{ return description; }
    public Location	getHome()				{ return home; }
    public NRank	getRank(UUID i)			{ return players.get(i); }
    public int		getRankIndex(UUID i)	{ return customTemplates.indexOf(players.get(i)); }
    public NRank	getHigherRank(UUID i)	{ return customTemplates.get(customTemplates.indexOf(players.get(i))-1); }
    public NRank	getLowerRank(UUID i)	{ return customTemplates.get(customTemplates.indexOf(players.get(i))+1); }
    public UUID	    getRelation(UUID i)		{ return relations.get(i); }
    
    //Check Block
    public boolean	isInvited(UUID i)	{ return invites.contains(i); }
    public boolean	isPlayer(UUID i)	{ return players.containsKey(i); }
    
    //Set Block
    public void		setPeaceful(boolean i)		{ peaceful = i; }
    public void		setWarzone(boolean i)		{ warzone = i; }
    public void		setSafezone(boolean i)		{ safezone = i; }
    public void		setOpen(boolean i)			{ open = i; }
    public void		setName(String i)			{ name = i; }
    public void		setDescription(String i)	{ description = i; }
    public void		setMoney(double i)			{ money = i; }
    public void		setMoney(int i)				{ money = i; }
    public void		addMoney(double i)			{ money += i; }
    public void		addMoney(int i)				{ money += i; }
    public void		addInvite(UUID i)			{ invites.add(i); }
    public void		addPlayer(UUID i, NRank j)	{ players.put(i, j); }
    public void		addPlayer(UUID i)			{ players.put(i, customTemplates.getLast()); }
    public void		addRelation(NRelation i)	{ relations.put(i.getJunior(),i.getID()); }
    
    //Delete Block
    public void		deleteInvite(UUID i)			{ invites.remove(i); }
    public void		deletePlayer(UUID player)		{ players.remove(player); }
    public void		deleteRelation(UUID faction)	{ relations.remove(faction); }
}
