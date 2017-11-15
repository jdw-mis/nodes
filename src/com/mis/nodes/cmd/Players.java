package com.mis.nodes.cmd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.mis.nodes.data.NPlayer;
import com.mis.nodes.data.Storage;
import com.mis.nodes.data.NFaction.NRank;

public class Players {
	
	public static String invitePlayer(NPlayer player, String target_name) {
		//Requirements: Player Has Faction, Player Atleast Mod
		if(player.faction == null || 
		player.faction.members.get(player).flag < NRank.MOD.flag) 
			return ChatColor.YELLOW + "You can't issue an invite, get a mod to do it.";
		//I bet the retard mistyped
	  	Player target = Bukkit.getPlayer(target_name);
	  	if(target == null) return ChatColor.RED+"Could not find the hobo in question";
	  	//Invite the Homo
	  	NPlayer catgorl = (NPlayer) Storage.Players.get(target.getUniqueId());
		player.faction.invites.add(catgorl); catgorl.invites.add(player.faction);
		String notification = String.format(
				"%s/%s/ would like you to have gay sex with them. %s %s to poz yourself.",
			ChatColor.AQUA,player.faction.name,"Use /nodes join",player.faction.name);
		target.sendMessage(notification); // Monkeys will go ape shit about this
		return ChatColor.GREEN+"I'm sure he'll make this crack den a much nicer place.";
	}
	
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
