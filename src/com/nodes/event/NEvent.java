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
import com.nodes.data.NConfig;
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
		if(chunk != null )
		{
			NNode node = NNodeList.get(chunk.node);
			if(player.currentNode.equals(chunk.node) == false)
			{
				NRelation relation = NRelationList.get(NFactionList.get(player.faction).getRelation(node.faction));
				
				if(node.faction == null || !NConfig.InnerNodeWalkingPrevention || NFactionList.get(node.faction).getNodeEmbed(node.ID) < NConfig.InnerNodeDefine || (node.faction.equals(player.faction) && NFactionList.get(player.faction).getRank(player.faction).walkInner) || (relation != null && relation.moveInner))
					player.currentNode = chunk.node;
				else
					event.setCancelled(true);
				NPlayerList.add(player);
			}
			if(node.coreChunk.equals(chunk.CID))
			{
				if(node.faction.equals(player.faction))
					if(player.faction != null && !NFactionList.get(player.faction).getRank(player.faction).walkCore)
					{
						event.setCancelled(true);
						return;
					}
				else if(!node.coreActive)
				{
					node.coreActive = true;
					NNodeList.add(node);
				}
				//enter core text
			}
			//TODO: send message
		}
	}
	
	public void onPlayerTeleport(PlayerTeleportEvent event)
	{
		NChunk chunk = NChunkList.get(event.getTo().getChunk());
		NPlayer player = NPlayerList.get(event.getPlayer().getUniqueId());
		if(chunk != null && player.currentNode.equals(chunk.node) == false)
		{
			player.currentNode = chunk.node;
		}
		else
		{
			NChunkID ID = new NChunkID(event.getTo().getChunk());
			while( chunk == null )
			{
				ID.x++;
				chunk = NChunkList.get(ID);
			}
			if(player.currentNode.equals(chunk.node) == false)
			{
				player.currentNode = chunk.node;
			}
		}
	}
	
	public void onPlayerPortalEvent(PlayerPortalEvent event)
	{
		NChunk chunk = NChunkList.get(event.getTo().getChunk());
		NPlayer player = NPlayerList.get(event.getPlayer().getUniqueId());
		if(chunk != null && player.currentNode.equals(chunk.node) == false)
		{
			player.currentNode = chunk.node;
		}
		else
		{
			NChunkID ID = new NChunkID(event.getTo().getChunk());
			while( chunk == null )
			{
				ID.x++;
				chunk = NChunkList.get(ID);
			}
			if(player.currentNode.equals(chunk.node) == false)
			{
				player.currentNode = chunk.node;
			}
		}
	}

	public void onBlockBreakEvent(BlockBreakEvent event)
	{
		NChunk chunk = NChunkList.get(event.getBlock().getChunk());
		NPlayer player = NPlayerList.get(event.getPlayer().getUniqueId());
		NFaction blockOwner;
		if(chunk != null && !player.currentNode.equals(chunk.node))
			blockOwner = NFactionList.get(NNodeList.get(chunk.node).faction);
		else
			blockOwner = NFactionList.get(NNodeList.get(player.currentNode).faction);
		if((blockOwner.ID.equals(player.faction) && !blockOwner.getRank(player.ID).edit) || !NRelationList.get(blockOwner.getRelation(player.faction)).blockBreak)
			event.setCancelled(true);
		//TODO default placeables?
	}

	public void onBlockPlaceEvent(BlockPlaceEvent event)
	{
		NChunk chunk = NChunkList.get(event.getBlock().getChunk());
		NPlayer player = NPlayerList.get(event.getPlayer().getUniqueId());
		NFaction blockOwner;
		if(chunk != null && !player.currentNode.equals(chunk.node))
			blockOwner = NFactionList.get(NNodeList.get(chunk.node).faction);
		else
			blockOwner = NFactionList.get(NNodeList.get(player.currentNode).faction);
		if((blockOwner.ID.equals(player.faction) && !blockOwner.getRank(player.ID).edit) || !NRelationList.get(blockOwner.getRelation(player.faction)).blockPlace)
			event.setCancelled(true);
		//TODO default placeables?
	}

	public void onPlayerInteractEvent(PlayerInteractEvent event)
	{
		NChunk chunk = NChunkList.get(event.getClickedBlock().getChunk());
		NPlayer player = NPlayerList.get(event.getPlayer().getUniqueId());
		NFaction blockOwner;
		Material mat = event.getMaterial();
		if(chunk != null && !player.currentNode.equals(chunk.node))
			blockOwner = NFactionList.get(NNodeList.get(chunk.node).faction);
		else
			blockOwner = NFactionList.get(NNodeList.get(player.currentNode).faction);
		if((blockOwner.ID.equals(player.faction)) && !blockOwner.getRank(player.ID).edit)
			event.setCancelled(true);
		else
		{
			NRelation relate = NRelationList.get(blockOwner.getRelation(player.faction));
			if(relate.blockPlace || relate.blockBreak || (relate.useWood && NConfig.TypeWoodInteractables.contains(mat)) || (relate.useStone && NConfig.TypeStoneInteractables.contains(mat)));
			else
				event.setCancelled(true);
		}
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
			if(chunk != null && !player.currentNode.equals(chunk.node))
				blockOwner = NFactionList.get(NNodeList.get(chunk.node).faction);
			else
				blockOwner = NFactionList.get(NNodeList.get(player.currentNode).faction);
			if((blockOwner.ID.equals(player.faction)) && !blockOwner.getRank(player.ID).chest)
				event.setCancelled(true);
			else
			{
				NRelation relate = NRelationList.get(blockOwner.getRelation(player.faction));
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
    	NPlayer player = NPlayerList.get(event.getPlayer().getUniqueId());
    	if(player.faction != null)
    	{
    		NFaction faction = NFactionList.get(player.faction);
    		if(faction.home != null)
    		{
    			event.setRespawnLocation(faction.home);
    		}
    	}
	}

	public void onPlayerDeathEvent(PlayerDeathEvent event)
	{
    	NPlayer player = NPlayerList.get(event.getEntity().getUniqueId());
    	player.deaths++;
    	NPlayerList.add(player);
    	//TODO Chat stuff?
	}
	
	
    public void onAttack(EntityDamageByEntityEvent event){}
    public void onEntityExplode(EntityExplodeEvent event){}
	public void somethingDied(EntityDeathEvent  event){}
	/**
	 * when a player dies
	 * */
	public void playerDied(PlayerDeathEvent event){}
}
