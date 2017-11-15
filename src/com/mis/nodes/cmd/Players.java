package com.mis.nodes.cmd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.mis.nodes.data.NPlayer;
import com.mis.nodes.data.Storage;
import com.mis.nodes.data.NFaction.NRank;

public class Players {
	
	public static String kickPlayer(NPlayer player, String target_name) {
		//Requirements: Player Has Faction, Player Atleast Mod
		if(player.faction == null || 
		player.faction.members.get(player).flag < NRank.MOD.flag) 
			return ChatColor.YELLOW + "Who you think you are trying keep a brother down?";
		//I bet the retard mistyped
	  	Player target = Bukkit.getPlayer(target_name);
	  	if(target == null) return ChatColor.RED+"Could not find the sex criminal in question";
	  	//Kick the fag
		player.faction.remove((NPlayer) Storage.Players.get(target.getUniqueId())); 
		target.sendMessage(ChatColor.RED+"You've been kicked. Stay out where you belong.");
		return ChatColor.GREEN+"I bet you've made this crack den a much nicer place.";
	}

}
