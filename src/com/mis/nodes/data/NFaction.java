package com.mis.nodes.data;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;

public class NFaction extends NData
{
	private static final long serialVersionUID = -5540280074957403989L;
	
	public boolean                          isOpen;
	public HashMap<NPlayer, NRank>			members;
	public HashMap<NFaction, NRelation>	    relations;
	public HashMap<NNode, Integer>			territory;	// integer is depth
	public NNode							capital;
	public String							name;

	public NFaction(String fname)
	{
		this.members = new HashMap<NPlayer, NRank>();
		this.relations = new HashMap<NFaction, NRelation>();
		this.territory = new HashMap<NNode, Integer>();
		this.id = UUID.randomUUID();
		this.isOpen = false;
		this.name = fname;
	}
	
	public void create(NPlayer leader) {
		Storage.Factions.putIfAbsent(this.id, this);
		this.members.put(leader, NRank.LEADER);
		leader.faction = this;
	}
	
	public void destroy() {
		Storage.Factions.remove(this.id);
		for(NPlayer member : this.members.keySet()) {
			member.faction = null;
		}
	}
	
	public void remove(NPlayer player) {
		
		
	}

	public static enum NRelation
	{
		ALLY( ChatColor.GREEN, 8 ), TRUCE( ChatColor.BLUE, 4 ), NEUTRAL( ChatColor.GRAY, 2 ), ENEMY( ChatColor.RED, 1 );

		public ChatColor	color;
		public int			flag;

		NRelation( ChatColor i, int j )
		{
			color = i;
			flag = j;
		}
	}

	public static enum NRank
	{
		LEADER( '~', 8 ), MOD( '*', 4 ), MEMBER( '+', 2 ), RECRUIT( '-', 1 );
		public char	chatchar;
		public int	flag;

		NRank( char i, int j )
		{
			chatchar = i;
			flag = j;
		}
	}
	
}
