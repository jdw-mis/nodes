package com.mis.nodes.data;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;

public class NFaction
{
	private final UUID id;
	private HashMap<NPlayer,NRank> members;
	private HashMap<NFaction,NRelation> relations;
	private HashMap<NNode,Integer> territory;		//integer is depth
	private NNode capital;

	NFaction(UUID i)
	{
		id = i;
	}

	public static enum NRelation {
		ALLY (ChatColor.GREEN, 8),
		TRUCE (ChatColor.BLUE, 4),
		NEUTRAL (ChatColor.GRAY, 2),
		ENEMY (ChatColor.RED, 1);
		
		public ChatColor color;
		public int flag;
		NRelation(ChatColor i, int j){color = i; flag = j;}
	}

	public static enum NRank {
		LEADER ('~', 8),
		MOD ('*', 4),
		MEMBER ('+', 2),
		RECRUIT ('-', 1);
		
		public char chatchar;
		public int flag;
		NRank(char i, int j){chatchar = i; flag = j;}
	}

	@Override
	public int hashCode()
	{
		return id.hashCode();
	}
	@Override
	public boolean equals(Object obj)
	{
		return id.hashCode() == obj.hashCode();
	}
}



