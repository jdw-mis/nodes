package com.nodes.data;

import java.util.UUID;

import org.bukkit.entity.Player;

public class NPlayer
{
	public UUID ID;
	public String name;
	public String title;
	public UUID faction;
	public UUID currentNode;
	public double money;
	public int kills;
	public int deaths;
	public long lastOnline;
	public long timeOnline;
	public boolean autoclaim;
	public boolean unautoclaim;
	public UUID chatChannel;
	
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
}
