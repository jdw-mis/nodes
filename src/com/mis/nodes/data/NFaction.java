package com.mis.nodes.data;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;

public class NFaction extends NData 
{
	public final UUID id;
	private HashMap<NPlayer,NRank> members;
	private HashMap<NFaction,NRelation> relations;
	private HashMap<NNode,Integer> territory;		//integer is depth
	private NNode capital;
	public boolean isOpen;
	public String name;

	NFaction()
	{
		this.id = UUID.randomUUID();
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
	
}



