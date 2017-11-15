package com.mis.nodes.cmd;

import java.util.UUID;

import com.mis.nodes.data.NData;
import com.mis.nodes.data.NFaction;
import com.mis.nodes.data.NFaction.NRank;
import com.mis.nodes.data.NPlayer;
import com.mis.nodes.data.Storage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Factions{
	
  public static String createFaction(UUID playerId, String fname) {
	NPlayer player = (NPlayer) Storage.Players.get(playerId);
	//Requirements: Player without Faction, Faction Does Not Exist
	if(player.faction != null) 
		return ChatColor.RED + "Ayy hol up, u already in a faction nigga.";
	if(factionExist(fname)) return ChatColor.RED+
		"This faction already exists you fucking mong.";
	//Otherwise
	new NFaction(fname).create(player);;
	return ChatColor.GREEN+"Okay, here's your circlejerk, retard.";
}
  public static String deleteFaction(UUID playerId) {
	NPlayer player = (NPlayer) Storage.Players.get(playerId);
	//Requirements: Player Has Faction, Player Heads Faction
	if(player.faction == null || 
	player.faction.members.get(player).flag < NRank.LEADER.flag) 
		return ChatColor.YELLOW + "You don't head shit negro.";
	//Otherwise
	player.faction.destroy();
	return ChatColor.AQUA+"This is what happens when you let in gypsies.";
}
 
public static String leaveFaction(UUID playerId) {
	NPlayer player = (NPlayer) Storage.Players.get(playerId);
	//Requirements: Player Has Faction, Player Does Not Head Faction
	if(player.faction == null || 
	player.faction.members.get(player) == NRank.LEADER) 
		return ChatColor.RED + "Please set a new leader before leaving. Or use the disband command to disband the faction.";
	//Otherwise
	player.faction.remove(player);
	return ChatColor.AQUA+"I'm *sure* that the grass is greener on the other side.";
}

public static String kickPlayer(UUID playerId, String target_name) {
	NPlayer player = (NPlayer) Storage.Players.get(playerId);
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
  
//  public static String flipOpen(UUID playerId) {
//	  if(!inFaction(playerId) || ) 
//			return ChatColor.RED + "Can't let you do this goy boy.";
//  }
//  
  
  public static boolean factionExist(String fname) {
	  fname = fname.toLowerCase();
	  for(NData d : Storage.Factions.values()) {
			String hname = ((NFaction)d).name;
			if(hname==fname) return true;
		} return false;
  }

}
