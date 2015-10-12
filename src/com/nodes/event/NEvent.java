package com.nodes.event;

import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.nodes.data.NChunk;
import com.nodes.data.NChunkList;
import com.nodes.data.NPlayer;
import com.nodes.data.NPlayerList;


public class NEvent implements Listener
{
	public void onPlayerMove(PlayerMoveEvent event)
	{
		Location ploc = event.getTo().getBlock().getLocation();
		NPlayer player = NPlayerList.get(event.getPlayer().getUniqueId());
		NChunk chunk = NChunkList.get(ploc.getChunk());
		if(chunk != null && player.getCurrentNode().equals(chunk.getNode()) == false)
		{
			player.setCurrentNode(chunk.getNode());
			//TODO: send message
		}
		else
		{
			
		}
	}
	//TODO: Teleport
	//TODO: World change
	//TODO: Block Break
	//TODO: Block Place
	//TODO: Respawn
	//TODO: Kills
	//TODO: Death
	//TODO: Core of Node
}
