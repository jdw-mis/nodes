package com.nodes.event;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.nodes.data.NChunk;
import com.nodes.data.NChunkList;
import com.nodes.data.NPlayer;
import com.nodes.data.NPlayerList;


public class NEvent implements Listener
{
	public void onPlayerMove(PlayerMoveEvent event)
	{
		NChunk chunk = NChunkList.get(event.getTo().getChunk());
		NPlayer player = NPlayerList.get(event.getPlayer().getUniqueId());
		if(chunk != null &&  player.getCurrentNode().equals(chunk.getNode()) == false)
		{
			player.setCurrentNode(chunk.getNode());
			//TODO: send message
		}
	}
	
	public void onPlayerTeleport(PlayerTeleportEvent event)
	{
		NChunk chunk = NChunkList.get(event.getTo().getChunk());
		NPlayer player = NPlayerList.get(event.getPlayer().getUniqueId());
		if(chunk != null && player.getCurrentNode().equals(chunk.getNode()) == false)
		{
			player.setCurrentNode(chunk.getNode());
		}
		else
		{
			int x = event.getTo().getChunk().getX();
			int z = event.getTo().getChunk().getZ();
			UUID world = event.getTo().getWorld().getUID();
			while( chunk == null )
			{
				chunk = NChunkList.get(x++,z,world);
			}
			if(player.getCurrentNode().equals(chunk.getNode()) == false)
			{
				player.setCurrentNode(chunk.getNode());
			}
		}
	}
	
	public void onPlayerPortalEvent(PlayerPortalEvent event)
	{
		NChunk chunk = NChunkList.get(event.getTo().getChunk());
		NPlayer player = NPlayerList.get(event.getPlayer().getUniqueId());
		if(chunk != null && player.getCurrentNode().equals(chunk.getNode()) == false)
		{
			player.setCurrentNode(chunk.getNode());
		}
		else
		{
			int x = event.getTo().getChunk().getX();
			int z = event.getTo().getChunk().getZ();
			UUID world = event.getTo().getWorld().getUID();
			while( chunk == null )
			{
				chunk = NChunkList.get(x++,z,world);
			}
			if(player.getCurrentNode().equals(chunk.getNode()) == false)
			{
				player.setCurrentNode(chunk.getNode());
			}
		}
	}
	
	
	
	//TODO: World change
	//TODO: Block Break
	//TODO: Block Place
	//TODO: Respawn
	//TODO: Kills
	//TODO: Death
}
