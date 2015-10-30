package com.nodes.event;

import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Minecart;
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
import com.nodes.data.NChunkID;
import com.nodes.data.NChunkList;
import com.nodes.data.NFaction;
import com.nodes.data.NFactionList;
import com.nodes.data.NNode;
import com.nodes.data.NNodeList;
import com.nodes.data.NPlayer;
import com.nodes.data.NPlayerList;
import com.nodes.data.NRelation;
import com.nodes.data.NRelationList;


public class NEvent implements Listener
{
	public void onPlayerMove(PlayerMoveEvent event)
	{
		NChunk chunk = NChunkList.get(event.getTo().getChunk());
		NPlayer player = NPlayerList.get(event.getPlayer().getUniqueId());
		if(chunk != null && player.getCurrentNode().equals(chunk.getNode()) == false)
		{
			NNode node = NNodeList.get(chunk.getNode());
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
			NChunkID ID = new NChunkID(event.getTo().getChunk());
			while( chunk == null )
			{
				ID.x++;
				chunk = NChunkList.get(ID);
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
			NChunkID ID = new NChunkID(event.getTo().getChunk());
			while( chunk == null )
			{
				ID.x++;
				chunk = NChunkList.get(ID);
			}
			if(player.getCurrentNode().equals(chunk.getNode()) == false)
			{
				player.setCurrentNode(chunk.getNode());
			}
		}
	}

	public void onBlockBreakEvent(BlockBreakEvent event)
	{
		NChunk chunk = NChunkList.get(event.getBlock().getChunk());
		NPlayer player = NPlayerList.get(event.getPlayer().getUniqueId());
		NFaction blockOwner;
		if(chunk != null && !player.getCurrentNode().equals(chunk.getNode()))
			blockOwner = NFactionList.get(NNodeList.get(chunk.getNode()).getOwner());
		else
			blockOwner = NFactionList.get(NNodeList.get(player.getCurrentNode()).getOwner());
		if((blockOwner.getID().equals(player.getFaction()) && !blockOwner.getRank(player.getID()).edit) || !NRelationList.get(blockOwner.getRelation(player.getFaction())).blockBreak)
			event.setCancelled(true);
		//TODO default placeables?
	}

	public void onBlockPlaceEvent(BlockPlaceEvent event)
	{
		NChunk chunk = NChunkList.get(event.getBlock().getChunk());
		NPlayer player = NPlayerList.get(event.getPlayer().getUniqueId());
		NFaction blockOwner;
		if(chunk != null && !player.getCurrentNode().equals(chunk.getNode()))
			blockOwner = NFactionList.get(NNodeList.get(chunk.getNode()).getOwner());
		else
			blockOwner = NFactionList.get(NNodeList.get(player.getCurrentNode()).getOwner());
		if((blockOwner.getID().equals(player.getFaction()) && !blockOwner.getRank(player.getID()).edit) || !NRelationList.get(blockOwner.getRelation(player.getFaction())).blockPlace)
			event.setCancelled(true);
		//TODO default placeables?
	}

	public void onPlayerInteractEvent(PlayerInteractEvent event)
	{
		NChunk chunk = NChunkList.get(event.getClickedBlock().getChunk());
		NPlayer player = NPlayerList.get(event.getPlayer().getUniqueId());
		NFaction blockOwner;
		Material mat = event.getMaterial();
		if(chunk != null && !player.getCurrentNode().equals(chunk.getNode()))
			blockOwner = NFactionList.get(NNodeList.get(chunk.getNode()).getOwner());
		else
			blockOwner = NFactionList.get(NNodeList.get(player.getCurrentNode()).getOwner());
		if((blockOwner.getID().equals(player.getFaction())) && !blockOwner.getRank(player.getID()).edit)
			event.setCancelled(true);
		else
		{
			NRelation relate = NRelationList.get(blockOwner.getRelation(player.getFaction()));
			//TEMP
			HashSet<Material> wood = new HashSet<Material>();
			wood.add(Material.WOOD_BUTTON);
			wood.add(Material.WOOD_PLATE);
			wood.add(Material.TRAP_DOOR);
			wood.add(Material.WOODEN_DOOR);
			wood.add(Material.FENCE_GATE);
			wood.add(Material.BIRCH_DOOR);
			wood.add(Material.BIRCH_FENCE_GATE);
			wood.add(Material.SPRUCE_DOOR);
			wood.add(Material.SPRUCE_FENCE_GATE);
			wood.add(Material.JUNGLE_DOOR);
			wood.add(Material.JUNGLE_FENCE_GATE);
			wood.add(Material.ACACIA_DOOR);
			wood.add(Material.ACACIA_FENCE_GATE);
			wood.add(Material.DARK_OAK_DOOR);
			wood.add(Material.DARK_OAK_FENCE_GATE);
			HashSet<Material> stone = new HashSet<Material>();
			stone.add(Material.LEVER);
			stone.add(Material.STONE_BUTTON);
			stone.add(Material.STONE_PLATE);
			//TEMP
			if(relate.blockPlace || relate.blockBreak || (relate.useWood && wood.contains(mat)) || (relate.useStone && stone.contains(mat)));
			else
				event.setCancelled(true);
		}
		//TODO default interactables
	}
	
	public void onInventoryOpenEvent(InventoryOpenEvent event)
	{
		if(!event.getInventory().getType().equals(InventoryType.PLAYER) && !event.getInventory().getType().equals(InventoryType.WORKBENCH))
		{
			NChunk chunk = null;
			if(event.getInventory().getHolder() instanceof BlockState)
				chunk = NChunkList.get(((BlockState)event.getInventory().getHolder()).getChunk());
			else if(event.getInventory().getHolder() instanceof Minecart)
				chunk = NChunkList.get(((Minecart)event.getInventory().getHolder()).getLocation().getChunk());
			NPlayer player = NPlayerList.get(event.getPlayer().getUniqueId());
			NFaction blockOwner;
			if(chunk != null && !player.getCurrentNode().equals(chunk.getNode()))
				blockOwner = NFactionList.get(NNodeList.get(chunk.getNode()).getOwner());
			else
				blockOwner = NFactionList.get(NNodeList.get(player.getCurrentNode()).getOwner());
			if((blockOwner.getID().equals(player.getFaction())) && !blockOwner.getRank(player.getID()).chest)
				event.setCancelled(true);
			else
			{
				NRelation relate = NRelationList.get(blockOwner.getRelation(player.getFaction()));
				if(!relate.openInv)
					event.setCancelled(true);
			}
		}
	}

    public void onPlayerJoinEvent(PlayerJoinEvent event)
    {
    	NPlayer player = NPlayerList.get(event.getPlayer().getUniqueId());
    	if(player == null)
    	{
    		//OH SHIT NEW PLAYA
    		player = new NPlayer(event.getPlayer());
    	}
    	else
    	{
        	player.lastOnline = System.currentTimeMillis();
    	}
    	//TODO: set currentNode to spawn
    }
    
    public void onPlayerLeaveEvent(PlayerQuitEvent event)
    {
    	NPlayer player = NPlayerList.get(event.getPlayer().getUniqueId());
    	player.timeOnline += System.currentTimeMillis()-player.lastOnline;
    	player.lastOnline = System.currentTimeMillis();
    }
    
    public void onPlayerKickEvent(PlayerKickEvent event)
    {
    	NPlayer player = NPlayerList.get(event.getPlayer().getUniqueId());
    	player.timeOnline += System.currentTimeMillis()-player.lastOnline;
    	player.lastOnline = System.currentTimeMillis();
    }
    
	public void onPlayerRespawnEvent(PlayerRespawnEvent event)
	{
		
	}

	public void onPlayerDeathEvent(PlayerDeathEvent event)
	{
    	NPlayer player = NPlayerList.get(event.getEntity().getUniqueId());
	}
	
	
    public void onAttack(EntityDamageByEntityEvent event){}
    public void onEntityExplode(EntityExplodeEvent event){}
	public void somethingDied(EntityDeathEvent  event){}
	/**
	 * when a player dies
	 * */
	public void playerDied(PlayerDeathEvent event){}
}
