package com.mis.nodes.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mis.nodes.Nodes;

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

public class NCMD implements CommandExecutor
{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args )
	{
		if(!sender.hasPermission("nodes.no") && sender instanceof Player)
			sender.sendMessage(org.bukkit.Color.RED+"You do not have permission to use Nodes.");
		Player player = (Player) sender;
		String action = args[0].toLowerCase();
		switch (action)
		{
		case "create": Factions.create(sender, player, args[1]); break;
		}
		return false;
	}

}
