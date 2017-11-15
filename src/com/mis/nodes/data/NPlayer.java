package com.mis.nodes.data;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.mis.nodes.data.NFaction.NRank;

public class NPlayer extends NData
{
	private static final long serialVersionUID = 6762424847314022509L;

	// player uuid same as mineman UUID
	public NFaction faction;

	public NPlayer( UUID playerId )
	{
		this.id = playerId;
	}

	@Override
	public String toString()
	{
		return "[Player] " + id.toString();
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
