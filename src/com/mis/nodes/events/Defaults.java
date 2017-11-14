package com.mis.nodes.events;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.mis.nodes.Nodes;
import com.mis.nodes.data.NPlayer;
import com.mis.nodes.data.Storage;

public class Defaults implements Listener {
	
	public Defaults(Nodes gay) {
		gay.getServer().getPluginManager().registerEvents(this, gay);
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent login){
		Player player = login.getPlayer();
		UUID playerId = player.getUniqueId();
		NPlayer nPlayer = (NPlayer) Storage.Players.getOrDefault(playerId, new NPlayer(playerId));
		if(!Storage.Players.containsKey(playerId)) Storage.Players.putIfAbsent(playerId,nPlayer);
	}
}
