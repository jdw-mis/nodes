package com.nodes.event;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
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
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
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
	public NEvent()
	{
		ALLOWSPAWN = new HashSet<SpawnReason>();
		ALLOWSPAWN.add(SpawnReason.BREEDING);
		ALLOWSPAWN.add(SpawnReason.BUILD_IRONGOLEM);
		ALLOWSPAWN.add(SpawnReason.BUILD_SNOWMAN);
		ALLOWSPAWN.add(SpawnReason.CURED);
		ALLOWSPAWN.add(SpawnReason.CUSTOM);
		ALLOWSPAWN.add(SpawnReason.DISPENSE_EGG);
		ALLOWSPAWN.add(SpawnReason.EGG);
		ALLOWSPAWN.add(SpawnReason.SPAWNER_EGG);
		ALLOWSPAWN.add(SpawnReason.INFECTION);
		ALLOWSPAWN.add(SpawnReason.JOCKEY);
		ALLOWSPAWN.add(SpawnReason.LIGHTNING);
		ALLOWSPAWN.add(SpawnReason.MOUNT);
		ALLOWSPAWN.add(SpawnReason.OCELOT_BABY);
		ALLOWSPAWN.add(SpawnReason.SLIME_SPLIT);
		ALLOWSPAWN.add(SpawnReason.SPAWNER);
		ALLOWINV = new HashSet<InventoryType>();
		ALLOWINV.add(InventoryType.PLAYER);
		ALLOWINV.add(InventoryType.CRAFTING);
		ALLOWINV.add(InventoryType.CREATIVE);
		ALLOWINV.add(InventoryType.WORKBENCH);
	}

	private Set<SpawnReason>ALLOWSPAWN;
	private Set<InventoryType>ALLOWINV;

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		String[] output = player.canWalk(event.getTo().getChunk());
		if(output[0] != null)
			event.getPlayer().sendMessage(output[0]);
		if(!Boolean.parseBoolean(output[1]))
			event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event)
	{
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		String[] output = player.canWalk(event.getTo().getChunk());
		if(output[0] != null)
			event.getPlayer().sendMessage(output[0]);
		if(!Boolean.parseBoolean(output[1]))
			event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerPortalEvent(PlayerPortalEvent event)
	{
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		String[] output = player.canWalk(event.getTo().getChunk());
		if(output[0] != null)
			event.getPlayer().sendMessage(output[0]);
		if(!Boolean.parseBoolean(output[1]))
			event.setCancelled(true);
	}



	@EventHandler
	public void onHangingBreak(HangingBreakEvent event)
	{
		Block block = event.getEntity().getLocation().getBlock();
		Material material = block.getType();
		NNode node = NNodeList.i.get(block.getChunk());
		if(node == null)
		{
			if(NConfig.i.RestrictOutsideAreas)
				event.setCancelled(true);
			return;
		}
		boolean embedded = node.isEmbedded();
		boolean canBreak = true;
		if(event.getCause() == RemoveCause.EXPLOSION)
			if(embedded)
				canBreak = NConfig.i.EmbeddedNodeCreeperProtection || NConfig.i.EmbeddedNodeExplosionProtection;
			else
				canBreak = NConfig.i.ExposedNodeCreeperProtection || NConfig.i.ExposedNodeExplosionProtection;
		else if((event instanceof HangingBreakByEntityEvent))
		{
			HangingBreakByEntityEvent eEvent = (HangingBreakByEntityEvent)event;
			if(eEvent.getRemover() instanceof Player)
			{
				NPlayer player = NPlayerList.i.get(eEvent.getRemover().getUniqueId());
				canBreak = player.canBreak(node,material);
			}
		}

		if(!canBreak)
			event.setCancelled(true);
		else if(canBreak && NConfig.i.BlockNaturalBlockItemDrop && NConfig.i.TypeNaturalBlocks.contains(material))
		{
			event.setCancelled(true);
			breakBlock(block);
		}
	}
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event)
	{
		Block block = event.getBlock();
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		NNode node = NNodeList.i.get(block.getChunk());
		if(node == null)
		{
			if(NConfig.i.RestrictOutsideAreas)
				event.setCancelled(true);
			return;
		}
		Material material = block.getType();
		boolean canBreak = player.canBreak(node,material);
		if(!canBreak)
			event.setCancelled(true);
		else if(canBreak && NConfig.i.BlockNaturalBlockItemDrop && NConfig.i.TypeNaturalBlocks.contains(material))
		{
			event.setCancelled(true);
			breakBlock(block);
		}
	}
	@EventHandler
	public void onBlockDamage(BlockDamageEvent event)
	{
		if(!event.getInstaBreak())
			return;
		Block block = event.getBlock();
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		NNode node = NNodeList.i.get(block.getChunk());
		if(node == null)
		{
			if(NConfig.i.RestrictOutsideAreas)
				event.setCancelled(true);
			return;
		}
		Material material = block.getType();
		boolean canBreak = player.canBreak(node,material);
		if(!canBreak)
			event.setCancelled(true);
		else if(canBreak && NConfig.i.BlockNaturalBlockItemDrop && NConfig.i.TypeNaturalBlocks.contains(material))
		{
			event.setCancelled(true);
			breakBlock(block);
		}
	}

	@SuppressWarnings("deprecation") //GOOD JOB DEPRECIATING METHODS BEFORE REPLACING THEM, 10/10
	private void breakBlock(Block block)
	{
		block.setType(Material.AIR);
		block.getWorld().playEffect(block.getLocation(),Effect.STEP_SOUND,block.getTypeId());
		block.getWorld().playEffect(block.getLocation(),Effect.TILE_BREAK,block.getTypeId(),block.getData());
	}

	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event)
	{
		Block block = event.getBlock();
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		NNode node = NNodeList.i.get(block.getChunk());
		if(node == null)
		{
			if(NConfig.i.RestrictOutsideAreas)
				event.setCancelled(true);
			return;
		}
		Material material = block.getType();
		boolean canPlace = player.canPlace(node,material);

		if(!canPlace)
			event.setCancelled(true);
	}

	@EventHandler
	public void onHangingPlace(HangingPlaceEvent event)
	{
		Block block = event.getBlock();
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		NNode node = NNodeList.i.get(block.getChunk());
		if(node == null)
		{
			if(NConfig.i.RestrictOutsideAreas)
				event.setCancelled(true);
			return;
		}
		Material material = block.getType();
		boolean canPlace = player.canPlace(node,material);

		if(!canPlace)
			event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event)
	{
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_AIR))
			return;

		Material material = event.getClickedBlock().getType();
		NNode node = NNodeList.i.get(event.getClickedBlock().getChunk());
		if(node == null)
		{
			if(NConfig.i.RestrictOutsideAreas)
				event.setCancelled(true);
			return;
		}
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		boolean canInteract = true;

		canInteract = player.canInteract(node, material);

		if(!canInteract)
			event.setCancelled(true);
	}

	@EventHandler
	public void onInventoryOpenEvent(InventoryOpenEvent event)
	{
		if(ALLOWINV.contains(event.getInventory().getType()))
			return;

		Chunk chunk = null;
		if(event.getInventory().getHolder() instanceof BlockState)
			chunk = ((BlockState)event.getInventory().getHolder()).getChunk();
		else if(event.getInventory().getHolder() instanceof Vehicle)
			chunk = ((Minecart)event.getInventory().getHolder()).getLocation().getChunk();
		NNode node = NNodeList.i.get(chunk);
		NFaction blockOwner = node.getFaction();
		if(blockOwner == null)
			return;
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());

		if((blockOwner.ID.equals(player.faction)) && player.getFaction() != null && !player.getRank().chest)
			event.setCancelled(true);
		else
		{
			NRelation relate = blockOwner.getRelation(player.faction);
			if(relate != null && !relate.openInv)
				event.setCancelled(true);
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
	public void onEntityDamage(EntityDamageEvent event){}
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event){}
	@EventHandler
	public void onEntityChangeBlock(EntityChangeBlockEvent event){}
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event){}
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event){}
	@EventHandler
	public void onEntityCombustByEntity(EntityCombustByEntityEvent event){}*/
	/*@EventHandler
	public void ( event){}*/
	

	@EventHandler
	public void onPlayerBucketFill(PlayerBucketFillEvent event)
	{
		Block block = event.getBlockClicked();
		NNode node = NNodeList.i.get(block.getChunk());
		if(node == null)
			return;
		NFaction faction = node.getFaction();
		if(faction == null)
			return;
		Material material = block.getType();
		NPlayer player = NPlayerList.i.get(event.getPlayer().getUniqueId());
		boolean embedded = node.isEmbedded();
		boolean canFill = true;
		
		if(material.equals(Material.WATER) || material.equals(Material.STATIONARY_WATER))
		{
			
		}
		else if(material.equals(Material.LAVA) || material.equals(Material.STATIONARY_LAVA))
		{
			
		}
	}
	
	@EventHandler
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event)
	{
		
	}


	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event)
	{
		if(ALLOWSPAWN.contains(event.getSpawnReason()))
			return;
		NNode node = NNodeList.i.get(event.getLocation().getChunk());
		if(node == null)
			return;
		NFaction faction = node.getFaction();
		if(faction == null)
			return;
		LivingEntity entity = event.getEntity();
		boolean embedded = node.isEmbedded();
		boolean canSpawn = true;

		if(entity instanceof Monster || (entity instanceof Wolf && ((Wolf)entity).isAngry() ))
		{
			if(entity instanceof Wither)
			{
				if(embedded)
					canSpawn = !NConfig.i.EmbeddedNodeWitherProtection;
				else
					canSpawn = !NConfig.i.ExposedNodeWitherProtection;
			}
			else
			{
				if(embedded && NConfig.i.EmbeddedNodeMonsterSpawn);
				else if(!embedded && NConfig.i.ExposedNodeMonsterSpawn);
				else
					canSpawn = false;
			}
		}
		else if(entity instanceof Animals)
		{
			if(embedded)
				canSpawn = NConfig.i.EmbeddedNodePassiveSpawn;
			else
				canSpawn = NConfig.i.ExposedNodePassiveSpawn;
		}

		if(!canSpawn)
			event.setCancelled(true);
	}

	@EventHandler
	public void onBlockBurn(BlockBurnEvent event)
	{
		Block block = event.getBlock();
		if(NConfig.i.BlockNaturalBlockItemDrop && NConfig.i.TypeNaturalBlocks.contains(block.getType()))
		{
			event.setCancelled(true);
			breakBlock(block);
		}
	}

	@EventHandler
	public void onBlockSpread(BlockSpreadEvent event)
	{
		NChunk chunk = NChunkList.i.get(event.getBlock().getChunk());
		NNode sourceNode = NNodeList.i.get(event.getSource().getChunk());
		Material material = event.getNewState().getType();
		boolean canSpread = true;
		if(chunk == null)
			return;
		NNode chunkNode = chunk.getNode();
		boolean embeddedChunk = chunkNode.isEmbedded();
		NFaction chunkOwner = chunkNode.getFaction();
		if(chunkOwner == null)
			return;
		if(sourceNode == null)
		{
			if(material == Material.WATER || material == Material.STATIONARY_WATER)
			{
				canSpread = embeddedChunk ? !NConfig.i.EmbeddedNodeWaterProtection : !NConfig.i.ExposedNodeWaterProtection;
			}
			else if(material == Material.LAVA || material == Material.STATIONARY_LAVA)
			{
				canSpread = embeddedChunk ? !NConfig.i.EmbeddedNodeLavaProtection : !NConfig.i.ExposedNodeLavaProtection;
			}
			else if(material == Material.FIRE)
			{
				canSpread = embeddedChunk ? !NConfig.i.EmbeddedNodeFireProtection : !NConfig.i.ExposedNodeFireProtection;
			}
			if(!canSpread)
				event.setCancelled(true);
			return;
		}
		if(chunk.node.equals(sourceNode.ID))
			return;
		boolean embeddedSource = sourceNode.isEmbedded();
		boolean sameFaction = chunkNode.faction.equals(sourceNode.faction);
		if(sameFaction && embeddedChunk == embeddedSource)
			return;

		if(sameFaction)
		{
			if(material == Material.WATER || material == Material.STATIONARY_WATER)
			{
				if(embeddedChunk == embeddedSource);
				else if(NConfig.i.EmbeddedNodeWaterProtection == NConfig.i.ExposedNodeWaterProtection);
				else
					canSpread = embeddedChunk ? !NConfig.i.EmbeddedNodeWaterProtection : !NConfig.i.ExposedNodeWaterProtection;
			}
			else if(material == Material.LAVA || material == Material.STATIONARY_LAVA)
			{
				if(embeddedChunk == embeddedSource);
				else if(NConfig.i.EmbeddedNodeLavaProtection == NConfig.i.ExposedNodeLavaProtection);
				else
					canSpread = embeddedChunk ? !NConfig.i.EmbeddedNodeLavaProtection : !NConfig.i.ExposedNodeLavaProtection;
			}
			else if(material == Material.FIRE)
			{
				if(embeddedChunk == embeddedSource);
				else if(NConfig.i.EmbeddedNodeFireProtection == NConfig.i.ExposedNodeFireProtection);
				else
					canSpread = embeddedChunk ? !NConfig.i.EmbeddedNodeFireProtection : !NConfig.i.ExposedNodeFireProtection;
			}
		}
		else
		{
			NRelation relate = chunkOwner.getRelation(sourceNode.faction);
			if(material == Material.WATER || material == Material.STATIONARY_WATER)
			{
				if(relate == null || !relate.water);
					canSpread = embeddedChunk ? !NConfig.i.EmbeddedNodeWaterProtection : !NConfig.i.ExposedNodeWaterProtection;
			}
			else if(material == Material.LAVA || material == Material.STATIONARY_LAVA)
			{
				if(relate == null || !relate.lava);
					canSpread = embeddedChunk ? !NConfig.i.EmbeddedNodeLavaProtection : !NConfig.i.ExposedNodeLavaProtection;
			}
			else if(material == Material.FIRE)
			{
				if(relate == null || !relate.fire);
					canSpread = embeddedChunk ? !NConfig.i.EmbeddedNodeFireProtection : !NConfig.i.ExposedNodeFireProtection;
			}
		}

		if(!canSpread)
			event.setCancelled(true);
	}

	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent event)
	{
		NNode node = NNodeList.i.get(event.getBlock().getChunk());
		if(node == null)
		{
			if(NConfig.i.RestrictOutsideAreas)
				event.setCancelled(true);
			return;
		}
		NFaction blockOwner = node.getFaction();
		NPlayer	player = null;
		Player pl = event.getPlayer();
		if(pl != null)
			player = NPlayerList.i.get(pl.getUniqueId());
		boolean embedded = node.isEmbedded();
		boolean canBurn = true;

		if(blockOwner == null);
		else if(player != null)
		{
			NRelation relate = blockOwner.getRelation(player.faction);
			if(embedded && NConfig.i.EmbeddedNodeBlockPlaceProtection)
			{
				if(blockOwner.ID.equals(player.faction) && player.getRank().blockEdit);
				else if(relate != null && relate.fire);
				else
					canBurn = false;
			}
			else if(!embedded && NConfig.i.ExposedNodeBlockPlaceProtection)
			{
				if(blockOwner.ID.equals(player.faction) && player.getRank().blockEdit);
				else if(relate != null && relate.fire);
				else
					canBurn = false;
			}
		}
		else
		{
			if(embedded && NConfig.i.EmbeddedNodeFireProtection)
				canBurn = false;
			else if(!embedded && NConfig.i.ExposedNodeFireProtection)
				canBurn = false;
		}

		if(!canBurn)
			event.setCancelled(true);
	}
}