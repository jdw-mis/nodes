package com.mis.nodes.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mis.nodes.data.NPlayer;
import com.mis.nodes.data.Storage;

import org.bukkit.ChatColor;

/*
 * create faction
 * delete faction
 * invite to faction
 * join faction
 * leave faction
 * close faction
 * open faction
 * set faction home
 * set faction name
 * set faction description
 * 
 * 
 * ally faction
 * truce faction
 * neutral faction
 * enemy faction
 * 
 * promote rank
 * demote rank
 * 
 * list
 * info faction
 * info player
 * info node
 * map world
 * 
 * 
 * maybe best way to do this is to gather all variables needed
 * then pop off the command in a thread???
 * 
 */

public class Commands extends Factions implements CommandExecutor
{

	@Override
	public boolean onCommand( CommandSender sender, Command cmd, String label, String[] args )
	{
		// if ( !sender.hasPermission("nodes.no") && sender instanceof Player )
		// sender.sendMessage( Color.RED + "You do not have permission to use
		// Nodes." );
		if ( !(sender instanceof Player) || args.length < 1 )
			return quit( sender, ChatColor.RED + "Frick off" );
		// Actually Do Shit
		Player player = (Player) sender;
		String action = args[0].toLowerCase();
		NPlayer nPlayer = (NPlayer) Storage.Players.get( player.getUniqueId() );
		String result = ChatColor.LIGHT_PURPLE + "Shits fucked.";
		switch ( action )
		{
		case "create":
		case "new":
			result = createFaction( nPlayer, args[1] );
			break;
		case "delete":
		case "disband":
			result = deleteFaction( nPlayer );
			break;
		case "remove":
		case "kick":
			result = kickPlayer( nPlayer, args[1] );
			break;
		case "invite":
			result = invitePlayer( nPlayer, args[1] );
			break;
		case "leave":
			result = leaveFaction( nPlayer );
			break;
		case "close":
			result = setPolicy( nPlayer, false );
			break;
		case "open":
			result = setPolicy( nPlayer, true );
			break;
		default:
			break;
		}
		sender.sendMessage( result );
		return true;
	}

	public static boolean quit( CommandSender sender, String fuckit )
	{
		sender.sendMessage( fuckit );
		return false;
	}

}
