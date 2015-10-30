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
	public double money;
	public int kills;
	public int deaths;
	public long lastOnline;
	public long timeOnline;
	public boolean autoclaim;
	public boolean unautoclaim;
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
    public String	getName()			{ return name; } 
    public String	getTitle()			{ return title; }
    public UUID		getID()				{ return ID; }
    public UUID		getCurrentNode()	{ return currentNode; }
    public UUID		getFaction()		{ return faction; }
    public UUID		getChatChannel()	{ return chatChannel; }
    
    //Set Block
	public void		setName( String i )			{ name = i; } 
	public void		setTitle( String i )		{ title = i; }
	public void		setCurrentNode( UUID i )	{ currentNode = i; }
	public void		setFaction( UUID i )		{ faction = i; }
	public void		setChatChannel( UUID i )	{ chatChannel = i; }
}
