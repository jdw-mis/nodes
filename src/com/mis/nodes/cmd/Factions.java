package com.mis.nodes.cmd;

import com.mis.nodes.data.NData;
import com.mis.nodes.data.NFaction;
import com.mis.nodes.data.NFaction.NRank;
import com.mis.nodes.data.NPlayer;
import com.mis.nodes.data.Storage;

import org.bukkit.ChatColor;

public class Factions extends Players
{

	public static String createFaction( NPlayer player, String fname )
	{
		// Requirements: Player without Faction, Faction Does Not Exist
		if ( player.faction != null )
			return ChatColor.RED + "Ayy hol up, u already in a faction nigga.";
		if ( factionExist( fname ) )
			return ChatColor.RED + "This faction already exists you fucking mong.";
		// Otherwise
		new NFaction( fname ).create( player );
		;
		return ChatColor.GREEN + "Okay, here's your circlejerk, retard.";
	}

	public static String deleteFaction( NPlayer player )
	{
		// Requirements: Player Has Faction, Player Heads Faction
		if ( player.faction == null || player.faction.members.get( player ).flag < NRank.LEADER.flag )
			return ChatColor.YELLOW + "You don't head shit negro.";
		// Otherwise
		player.faction.destroy();
		return ChatColor.AQUA + "This is what happens when you let in gypsies.";
	}

	public static String leaveFaction( NPlayer player )
	{
		// Requirements: Player Has Faction, Player Does Not Head Faction
		if ( player.faction == null || player.faction.members.get( player ) == NRank.LEADER )
			return ChatColor.RED
					+ "Please set a new leader before leaving. Or use the disband command to disband the faction.";
		// Otherwise
		player.faction.remove( player );
		return ChatColor.AQUA + "I'm *sure* that the grass is greener on the other side.";
	}

	public static String setPolicy( NPlayer player, boolean iBlameSoros )
	{
		if ( player.faction == null || player.faction.members.get( player ).flag < NRank.LEADER.flag )
			return ChatColor.RED + "You do not have the ability to regulate those borders.";
		player.faction.isOpen = iBlameSoros;
		if ( iBlameSoros )
			return ChatColor.AQUA + "I hope you chose the right immigration policy.";
		return ChatColor.GREEN + "I hope you enjoy that wall, Mr. Drumpf.";
	}

	// Here Be Utils
	public static boolean factionExist( String fname )
	{
		fname = fname.toLowerCase();
		for ( NData d : Storage.Factions.values() )
		{
			String hname = ((NFaction) d).name;
			if ( hname == fname )
				return true;
		}
		return false;
	}

}
