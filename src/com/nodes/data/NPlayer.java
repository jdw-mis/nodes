package com.nodes.data;

import java.util.UUID;

import org.bukkit.entity.Player;

public class NPlayer
{
	private String name;
	private String title;
	private final UUID ID;
	private UUID faction;
	private UUID currentNode;
	private double money;
	private int kills;
	private int deaths;
	private long lastOnline;
	private long timeOnline;
	private boolean autoclaim;
	private boolean unautoclaim;
	private UUID chatChannel;
	
	public NPlayer( Player player )
	{
		ID = player.getUniqueId();
		name = player.getName();
		lastOnline = System.currentTimeMillis();
		title = "";
		faction = null;
		currentNode = null;
		chatChannel = null;
		autoclaim = false;
		unautoclaim = false;
		money = 0;
		kills = 0;
		deaths = 0;
		timeOnline = 0;
	}
	
	//Get Block
    public boolean	getAutoClaim()		{ return autoclaim; }
    public boolean	getUnAutoClaim()	{ return unautoclaim; }
    public double	getMoney()			{ return money; }
    public long		getLastOnline()		{ return lastOnline; }
    public long		getTimeOnline()		{ return timeOnline; }
    public int		getKills()			{ return kills; }
    public int		getDeaths()			{ return deaths; }
    public String	getName()			{ return name; } 
    public String	getTitle()			{ return title; }
    public UUID		getID()				{ return ID; }
    public UUID		getCurrentNode()	{ return currentNode; }
    public UUID		getFaction()		{ return faction; }
    public UUID		getChatChannel()	{ return chatChannel; }
    
    //Set Block
    public void		setAutoClaim( boolean i )	{ autoclaim = i; }
	public void		setUnAutoClaim( boolean i )	{ unautoclaim = i; }
	public void		setMoney( double i )		{ money = i; }
	public void		setLastOnline( long i )		{ lastOnline = i; }
	public void		setTimeOnline( long i )		{ timeOnline = i; }
	public void		setKills( int i )			{ kills = i; }
	public void		setDeaths( int i )			{ deaths = i; }
	public void		setName( String i )			{ name = i; } 
	public void		setTitle( String i )		{ title = i; }
	public void		setCurrentNode( UUID i )	{ currentNode = i; }
	public void		setFaction( UUID i )		{ faction = i; }
	public void		setChatChannel( UUID i )	{ chatChannel = i; }
}
