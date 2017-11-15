package com.mis.nodes.data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.mis.nodes.data.NFaction.NRank;

public class NPlayer extends NData
{
	private static final long serialVersionUID = 6762424847314022509L;

	public transient Set<NFaction>	invites;
	public NFaction					faction;

	public NPlayer( UUID playerId )
	{
		this.invites = new HashSet<NFaction>();
		this.id = playerId;
	}

	public static NPlayer getPlayer( Player player )
	{
		return (NPlayer) Storage.Players.get( player.getUniqueId() );
	}

	public NRank getRank()
	{
		return faction.members.get( this );
	}
}
