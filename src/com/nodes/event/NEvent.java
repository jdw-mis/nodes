package com.nodes.event;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
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
import com.nodes.data.NNode;
import com.nodes.data.NNodeList;
import com.nodes.data.NPlayer;
import com.nodes.data.NPlayerList;
import com.nodes.data.NRelation;

public class NEvent implements Listener
{
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		String[] output = player.canWalk(event.getTo().getChunk());
		if(output[0] != null)
			event.getPlayer().sendMessage(output[0]);
		event.setCancelled(!Boolean.parseBoolean(output[1]));
	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event)
	{
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		String[] output = player.canWalk(event.getTo().getChunk());
		if(output[0] != null)
			event.getPlayer().sendMessage(output[0]);
		event.setCancelled(!Boolean.parseBoolean(output[1]));
	}

	@EventHandler
	public void onPlayerPortalEvent(PlayerPortalEvent event)
	{
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		String[] output = player.canWalk(event.getTo().getChunk());
		if(output[0] != null)
			event.getPlayer().sendMessage(output[0]);
		event.setCancelled(!Boolean.parseBoolean(output[1]));
	}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event)
	{
		NNode node = NNodeList.i.get(event.getBlock().getChunk());
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		Material material = event.getBlock().getType();
		NFaction blockOwner = node.getFaction();
		NRelation relate = null;
		if(blockOwner != null)
			relate = blockOwner.getRelation(player.faction);
		boolean embedded = node.isEmbedded();
		boolean canBreak = true;
	
		if(blockOwner == null);	  //Can they break it?
		else if(embedded && NConfig.i.EmbeddedNodeBlockBreakProtection)
		{
			if(NConfig.i.TypeEmbeddedPlaceableOverride.contains(material));
			else if(blockOwner.ID.equals(player.faction) && player.getRank().blockBreak);
			else if(relate != null && relate.blockBreak);
			else
				canBreak = false;
		}
		else if(!embedded && NConfig.i.ExposedNodeBlockBreakProtection)
		{
			if(NConfig.i.TypeExposedBreakableOverride.contains(material));
			else if(blockOwner.ID.equals(player.faction) && player.getRank().blockBreak);
			else if(relate != null && relate.blockBreak);
			else
				canBreak = false;
		}
		
		if(canBreak && NConfig.i.BlockNaturalBlockItemDrop && NConfig.i.TypeNaturalBlocks.contains(material))
			event.getBlock().getDrops().clear();
		event.setCancelled(!canBreak);
	}

	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event)
	{
		NNode node = NNodeList.i.get(event.getBlock().getChunk());
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		Material material = event.getBlock().getType();
		NFaction blockOwner = node.getFaction();
		NRelation relate = null;
		if(blockOwner != null)
			relate = blockOwner.getRelation(player.faction);
		boolean embedded = node.isEmbedded();
		boolean canPlace = true;
	
		if(blockOwner == null);	  //Can they Place it?
		else if(embedded && NConfig.i.EmbeddedNodeBlockPlaceProtection)
		{
			if(NConfig.i.TypeEmbeddedPlaceableOverride.contains(material));
			else if(blockOwner.ID.equals(player.faction) && player.getRank().blockPlace);
			else if(relate != null && relate.blockPlace);
			else
				canPlace = false;
		}
		else if(!embedded && NConfig.i.ExposedNodeBlockPlaceProtection)
		{
			if(NConfig.i.TypeExposedPlaceableOverride.contains(material));
			else if(blockOwner.ID.equals(player.faction) && player.getRank().blockPlace);
			else if(relate != null && relate.blockPlace);
			else
				canPlace = false;
		}
		
		event.setCancelled(!canPlace);
	}

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event)
	{
		Action action = event.getAction();
		if(!(action.equals(Action.PHYSICAL) || action.equals(Action.RIGHT_CLICK_BLOCK)))
			return;
		Material material = event.getMaterial();
		NNode node = NNodeList.i.get(event.getClickedBlock().getChunk());
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		NFaction blockOwner = node.getFaction();
		NRelation relate = null;
		if(blockOwner != null)
			relate = blockOwner.getRelation(player.faction);
		boolean embedded = node.isEmbedded();
		boolean canInteract = true;
	
		if(blockOwner == null);	  //Can they Interact with it?
		else if(embedded && NConfig.i.EmbeddedNodeBlockInteractProtection)
		{
			if(NConfig.i.TypeEmbeddedInteractableOverride.contains(material));
			else if(blockOwner.ID.equals(player.faction) && player.getRank().blockInteract);
			else if(relate != null && (relate.blockInteract ||
					(relate.useWood && NConfig.i.TypeWoodInteractables.contains(material)) ||
					(relate.useStone && NConfig.i.TypeWoodInteractables.contains(material))));
			else
				canInteract = false;
		}
		else if(!embedded && NConfig.i.ExposedNodeBlockInteractProtection)
		{
			if(NConfig.i.TypeExposedInteractableOverride.contains(material));
			else if(blockOwner.ID.equals(player.faction) && player.getRank().blockInteract);
			else if(relate != null && (relate.blockInteract ||
					(relate.useWood && NConfig.i.TypeWoodInteractables.contains(material)) ||
					(relate.useStone && NConfig.i.TypeWoodInteractables.contains(material))));
			else
				canInteract = false;
		}
		
		event.setCancelled(!canInteract);
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
			if(blockOwner == null)
				return;
			else if((blockOwner.ID.equals(player.faction)) && !player.getRank().chest)
				event.setCancelled(true);
			else
			{
				NRelation relate = blockOwner.getRelation(player.faction);
				if(!relate.openInv)
					event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoinEvent(PlayerJoinEvent event)
	{
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		if(player == null)
			player = new NPlayer(event.getPlayer());
		else
			player.lastOnline = System.currentTimeMillis();
		NPlayerList.i.add(player);
	}

	@EventHandler
	public void onPlayerLeaveEvent(PlayerQuitEvent event)
	{
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		if(player != null)
		{
			player.timeOnline += System.currentTimeMillis()-player.lastOnline;
			player.lastOnline = System.currentTimeMillis();
		}
	}

	@EventHandler
	public void onPlayerKickEvent(PlayerKickEvent event)
	{
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		if(player != null)
		{
			player.timeOnline += System.currentTimeMillis()-player.lastOnline;
			player.lastOnline = System.currentTimeMillis();
		}
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


	/*@EventHandler
	public void onEntityExplode(EntityExplodeEvent event){}
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){}
	@EventHandler
	public void onPistonExtend(BlockPistonExtendEvent event){}
	@EventHandler
	public void onPistonExtend(BlockPistonRetractEvent event){}
	@EventHandler
	public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event){}
	@EventHandler
	public void onBlockFromTo(BlockFromToEvent event){}
	@EventHandler
	public void onPotionSplash(PotionSplashEvent event){}
	@EventHandler
	public void  onEntityDamage(EntityDamageEvent event){}
	@EventHandler
	public void  onEntityDamageByEntity(EntityDamageByEntityEvent event){}
	@EventHandler
	public void  onEntityChangeBlock(EntityChangeBlockEvent event){}
	@EventHandler
	public void  onPlayerInteractEntity(PlayerInteractEntityEvent event){}
	@EventHandler
	public void  onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event){}
	@EventHandler
	public void  onPlayerBucketFill(PlayerBucketFillEvent event){}
	@EventHandler
	public void  onPlayerBucketEmpty(PlayerBucketEmptyEvent event){}
	@EventHandler
	public void  onHangingBreak(HangingBreakEvent event){}
	@EventHandler
	public void  onHangingPlace(HangingPlaceEvent event){}
	@EventHandler
	public void  onHangingBreakByEntity(HangingBreakByEntityEvent event){}
	@EventHandler
	public void  onCreatureSpawn(CreatureSpawnEvent event){}
	@EventHandler
	public void  onEntityCombustByEntity(EntityCombustByEntityEvent event){}
	@EventHandler
	public void  onBlockIgnite(BlockIgniteEvent event){}
	@EventHandler
	public void  onBlockSpread(BlockSpreadEvent event){}
	@EventHandler
	public void  onBlockDamage(BlockDamageEvent event){}
	@EventHandler
	public void  onBlockBurn(BlockBurnEvent event){}*/
	/*@EventHandler
	public void  ( event){}*/
}