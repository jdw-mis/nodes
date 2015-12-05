package com.nodes.event;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

import com.nodes.data.NChunk;
import com.nodes.data.NChunkList;
import com.nodes.data.NConfig;
import com.nodes.data.NFaction;
import com.nodes.data.NFactionList;
import com.nodes.data.NPlayer;
import com.nodes.data.NPlayerList;
import com.nodes.data.NRelation;


public class NEvent implements Listener
{
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		NChunk chunk = NChunkList.i.get(event.getTo().getChunk());
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		String[] output = new String[2];
		boolean cancel = false;

		output = player.canWalk(chunk);
		
		cancel = !Boolean.parseBoolean(output[1]);
		event.getPlayer().sendMessage(output[0]);
		
		event.setCancelled(cancel);
	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event)
	{
		NChunk chunk = NChunkList.i.get(event.getTo().getChunk());
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		String[] output = new String[2];
		boolean cancel = false;

		output = player.canWalk(chunk);
		
		cancel = !Boolean.parseBoolean(output[1]);
		event.getPlayer().sendMessage(output[0]);
		
		event.setCancelled(cancel);
	}

	@EventHandler
	public void onPlayerPortalEvent(PlayerPortalEvent event)
	{
		NChunk chunk = NChunkList.i.get(event.getTo().getChunk());
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		String[] output = new String[2];
		boolean cancel = false;

		output = player.canWalk(chunk);
		
		cancel = !Boolean.parseBoolean(output[1]);
		event.getPlayer().sendMessage(output[0]);
		
		event.setCancelled(cancel);
	}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event)
	{
		NChunk chunk = NChunkList.i.get(event.getBlock().getChunk());
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		NFaction blockOwner;
		if(chunk != null && !player.currentNode.equals(chunk.node))
			blockOwner = chunk.getNode().getFaction();
		else
			blockOwner = player.getNode().getFaction();
		if(blockOwner != null && ((blockOwner.ID.equals(player.faction) && !player.getRank().edit) || !blockOwner.getRelation(player.faction).blockBreak))
			event.setCancelled(true);
		//TODO default placeables?
	}

	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event)
	{
		NChunk chunk = NChunkList.i.get(event.getBlock().getChunk());
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		NFaction blockOwner;
		if(chunk != null && !player.currentNode.equals(chunk.node))
			blockOwner = chunk.getNode().getFaction();
		else
			blockOwner = player.getNode().getFaction();
		if(blockOwner != null && ((blockOwner.ID.equals(player.faction) && !player.getRank().edit) || !blockOwner.getRelation(player.faction).blockPlace))
			event.setCancelled(true);
		//TODO default placeables?
	}

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event)
	{
		NChunk chunk = NChunkList.i.get(event.getClickedBlock().getChunk());
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		NFaction blockOwner;
		Material mat = event.getMaterial();
		if(chunk != null && !player.currentNode.equals(chunk.node))
			blockOwner = chunk.getNode().getFaction();
		else
			blockOwner = player.getNode().getFaction();
		if((blockOwner.ID.equals(player.faction)) && !player.getRank().edit)
			event.setCancelled(true);
		else
		{
			NRelation relate = blockOwner.getRelation(player.faction);
			if(relate.blockPlace || relate.blockBreak || (relate.useWood && NConfig.i.TypeWoodInteractables.contains(mat)) || (relate.useStone && NConfig.i.TypeStoneInteractables.contains(mat)));
			else
				event.setCancelled(true);
		}
	}

	@EventHandler
	public void onInventoryOpenEvent(InventoryOpenEvent event)
	{
		if(!event.getInventory().getType().equals(InventoryType.PLAYER) && !event.getInventory().getType().equals(InventoryType.WORKBENCH))
		{
			NChunk chunk = null;
			if(event.getInventory().getHolder() instanceof BlockState)
				chunk = NChunkList.i.get(((BlockState)event.getInventory().getHolder()).getChunk());
			else if(event.getInventory().getHolder() instanceof Minecart)
				chunk = NChunkList.i.get(((Minecart)event.getInventory().getHolder()).getLocation().getChunk());
			NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
			NFaction blockOwner;
			if(chunk != null && !player.currentNode.equals(chunk.node))
				blockOwner = chunk.getNode().getFaction();
			else
				blockOwner = player.getNode().getFaction();
			if((blockOwner.ID.equals(player.faction)) && !player.getRank().chest)
				event.setCancelled(true);
			else
			{
				NRelation relate = blockOwner.getRelation(player.faction);
				if(!relate.openInv)
					event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event)
	{
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		if(player == null)
			player = new NPlayer(event.getPlayer());
		else
			player.lastOnline = System.currentTimeMillis();
	}

	@EventHandler
	public void onPlayerLeaveEvent(PlayerQuitEvent event)
	{
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		player.timeOnline += System.currentTimeMillis()-player.lastOnline;
		player.lastOnline = System.currentTimeMillis();
	}

	@EventHandler
	public void onPlayerKickEvent(PlayerKickEvent event)
	{
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		player.timeOnline += System.currentTimeMillis()-player.lastOnline;
		player.lastOnline = System.currentTimeMillis();
	}

	@EventHandler
	public void onPlayerRespawnEvent(PlayerRespawnEvent event)
	{
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		if(player.faction != null)
		{
			NFaction faction = NFactionList.i.get(player.faction);
			if(faction.home != null)
			{
				event.setRespawnLocation(faction.home);
			}
		}
	}

	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event)
	{
		NPlayer player = NPlayerList.i.get(event.getEntity().getUniqueId());
		player.deaths++;
		NPlayerList.i.add(player);
		//TODO Chat stuff?
	}


	@EventHandler
	public void onAttack(EntityDamageByEntityEvent event){}
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event){}
	@EventHandler
	public void somethingDied(EntityDeathEvent event){}
}
