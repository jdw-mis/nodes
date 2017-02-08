package com.nodes.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;

/*
 * This class is largely superfluous, written before I knew bukkits built in config system.
 * might keep the variables as I'm not sure how fast the access time on bukkits config is
 */
public class NConfig
{
	public static NConfig i = new NConfig();

	public long firstActiveMillis;
	public boolean RestrictOutsideAreas;
	public int EmbeddedNodeDefine;
	public boolean EmbeddedNodeBlockPlaceProtection;
	public boolean EmbeddedNodeBlockBreakProtection;
	public boolean EmbeddedNodeBlockInteractProtection;
	public boolean EmbeddedNodeLavaProtection;
	public boolean EmbeddedNodeWaterProtection;
	public boolean EmbeddedNodeFireProtection;
	public boolean EmbeddedNodeExplosionProtection;
	public boolean EmbeddedNodeCreeperProtection;
	public boolean EmbeddedNodeWitherProtection;
	public boolean EmbeddedNodeMonsterSpawn;
	public boolean EmbeddedNodePassiveSpawn;
	public boolean EmbeddedNodeWalkingPrevention;
	public boolean EmbeddedNodeWoodInteractable;
	public boolean EmbeddedNodeStoneInteractable;
	public boolean CapitalNodeAlwaysEmbedded;
	public boolean CapitalSurroundingNodesAlwaysEmbedded;
	public HashSet<Material> TypeEmbeddedPlaceableOverride;
	public HashSet<Material> TypeEmbeddedBreakableOverride;
	public HashSet<Material> TypeEmbeddedInteractableOverride;

	public boolean ExposedNodeBlockPlaceProtection;
	public boolean ExposedNodeBlockBreakProtection;
	public boolean ExposedNodeBlockInteractProtection;
	public boolean ExposedNodeLavaProtection;
	public boolean ExposedNodeWaterProtection;
	public boolean ExposedNodeFireProtection;
	public boolean ExposedNodeExplosionProtection;
	public boolean ExposedNodeCreeperProtection;
	public boolean ExposedNodeWitherProtection;
	public boolean ExposedNodeMonsterSpawn;
	public boolean ExposedNodePassiveSpawn;
	public boolean ExposedNodeWalkingPrevention;
	public boolean ExposedNodeWoodInteractable;
	public boolean ExposedNodeStoneInteractable;
	public boolean AllySurroundingNodesAlwaysExposed;
	public boolean FillerSurroundingNodesAlwaysExposed;
	public HashSet<Material> TypeExposedPlaceableOverride;
	public HashSet<Material> TypeExposedBreakableOverride;
	public HashSet<Material> TypeExposedInteractableOverride;

	public HashSet<Material> TypeWoodInteractables;
	public HashSet<Material> TypeStoneInteractables;

	public boolean BlockNaturalBlockItemDrop;
	public HashSet<Material> TypeNaturalBlocks;
	public HashSet<Material> TypeInteractables;

	public ChatColor AlliedColor;
	public ChatColor NeutralColor;
	public ChatColor EnemyColor;
	public ChatColor UnrelateColor;
	public ChatColor SelfColor;
	public ChatColor NullColor;

	public int HomeTeleportDelay;
	public boolean HomeEmbeddedOnly;
	public boolean HomeFromEmbedded;
	public boolean HomeFromExposed;
	public boolean HomeFromUndef;
	public boolean HomeFromAlly;
	public boolean HomeFromNeutral;
	public boolean HomeFromEnemy;

	public boolean OfflineResourceDumps;
	public int OfflineResourceDumpMax;
	public boolean AdditiveCapitalNodes;
	public NResource CapitalResource;

	public int AutoSavePulse;

	public boolean ConnectedNodeClaimOnly;
	public int NodeCapturePulse;
	public int NodeCaptureCountdownMax;
	public int NodeCaptureYRestrict;
	//public NodeCaptureFormulaOwner;
	//public NodeCaptureFormulaAlly;
	//public NodeCaptureFormulaEnemy;

	public HashSet<NRank> StandardRanks;
	public LinkedList<UUID> StandardRankOrder;
	public HashMap<String,NRelation> StandardRelations;

	public int MapZoomShowCoreUntil;
	public int MapZoomShowPlayerUntil;

	public NConfig()
	{
		MapZoomShowCoreUntil = 4;
		MapZoomShowPlayerUntil = 5;

		RestrictOutsideAreas = true;
		EmbeddedNodeDefine = 2;
		EmbeddedNodeBlockPlaceProtection = true;
		EmbeddedNodeBlockBreakProtection = true;
		EmbeddedNodeBlockInteractProtection = true;
		EmbeddedNodeWaterProtection = true;
		EmbeddedNodeLavaProtection = true;
		EmbeddedNodeFireProtection = true;
		EmbeddedNodeExplosionProtection = true;
		EmbeddedNodeCreeperProtection = true;
		EmbeddedNodeWitherProtection = true;
		EmbeddedNodeMonsterSpawn = false;
		EmbeddedNodePassiveSpawn = true;
		EmbeddedNodeWalkingPrevention = false;
		EmbeddedNodeWoodInteractable = false;
		EmbeddedNodeStoneInteractable = false;
		CapitalNodeAlwaysEmbedded = true;
		CapitalSurroundingNodesAlwaysEmbedded = false;

		ExposedNodeBlockPlaceProtection = true;
		ExposedNodeBlockBreakProtection = true;
		ExposedNodeBlockInteractProtection = true;
		ExposedNodeWaterProtection = true;
		ExposedNodeLavaProtection = true;
		ExposedNodeFireProtection = false;
		ExposedNodeExplosionProtection = false;
		ExposedNodeCreeperProtection = false;
		ExposedNodeWitherProtection = false;
		ExposedNodeMonsterSpawn = false;
		ExposedNodePassiveSpawn = false;
		ExposedNodeWalkingPrevention = false;
		ExposedNodeWoodInteractable = true;
		ExposedNodeStoneInteractable = false;
		AllySurroundingNodesAlwaysExposed = false;
		FillerSurroundingNodesAlwaysExposed = false;

		AlliedColor = ChatColor.GREEN;
		NeutralColor = ChatColor.AQUA;
		EnemyColor = ChatColor.RED;
		UnrelateColor = ChatColor.GRAY;
		SelfColor = ChatColor.AQUA;
		NullColor = ChatColor.DARK_GRAY;

		HomeTeleportDelay = 10;
		HomeEmbeddedOnly = true;
		HomeFromEmbedded = true;
		HomeFromExposed = true;
		HomeFromUndef = true;
		HomeFromAlly = true;
		HomeFromNeutral = false;
		HomeFromEnemy = false;

		OfflineResourceDumps = false;
		OfflineResourceDumpMax = 0;
		AdditiveCapitalNodes = true;

		AutoSavePulse = 15;

		ConnectedNodeClaimOnly = false;
		NodeCapturePulse = 10;
		NodeCaptureCountdownMax = 3;
		NodeCaptureYRestrict = 20;

		CapitalResource = new NResource();
		StandardRanks = new HashSet<NRank>();
		StandardRankOrder = new LinkedList<UUID>();
		StandardRelations = new HashMap<String,NRelation>();
		TypeWoodInteractables = new HashSet<Material>();
		TypeStoneInteractables = new HashSet<Material>();

		TypeExposedPlaceableOverride = new HashSet<Material>();
		TypeExposedBreakableOverride = new HashSet<Material>();
		TypeExposedInteractableOverride = new HashSet<Material>();
		TypeEmbeddedPlaceableOverride = new HashSet<Material>();
		TypeEmbeddedBreakableOverride = new HashSet<Material>();
		TypeEmbeddedInteractableOverride = new HashSet<Material>();
		BlockNaturalBlockItemDrop = true;
		TypeNaturalBlocks = new HashSet<Material>();
		TypeNaturalBlocks.add(Material.STONE);
		TypeNaturalBlocks.add(Material.DIRT);
		TypeNaturalBlocks.add(Material.GRAVEL);
		TypeNaturalBlocks.add(Material.SAND);
		TypeNaturalBlocks.add(Material.LOG);
		TypeNaturalBlocks.add(Material.LOG_2);
		TypeNaturalBlocks.add(Material.COAL_ORE);
		TypeNaturalBlocks.add(Material.DIAMOND_ORE);
		TypeNaturalBlocks.add(Material.EMERALD_ORE);
		TypeNaturalBlocks.add(Material.GLOWING_REDSTONE_ORE);
		TypeNaturalBlocks.add(Material.GOLD_ORE);
		TypeNaturalBlocks.add(Material.IRON_ORE);
		TypeNaturalBlocks.add(Material.LAPIS_ORE);
		TypeNaturalBlocks.add(Material.QUARTZ_ORE);
		TypeNaturalBlocks.add(Material.REDSTONE_ORE);
		TypeNaturalBlocks.add(Material.GRASS);
		TypeNaturalBlocks.add(Material.CLAY);
		TypeNaturalBlocks.add(Material.OBSIDIAN);
		TypeNaturalBlocks.add(Material.LEAVES);
		TypeNaturalBlocks.add(Material.LEAVES_2);

		TypeWoodInteractables.add(Material.WOOD_BUTTON);
		TypeWoodInteractables.add(Material.WOOD_PLATE);
		TypeWoodInteractables.add(Material.TRAP_DOOR);
		TypeWoodInteractables.add(Material.WOODEN_DOOR);
		TypeWoodInteractables.add(Material.BIRCH_DOOR);
		TypeWoodInteractables.add(Material.SPRUCE_DOOR);
		TypeWoodInteractables.add(Material.JUNGLE_DOOR);
		TypeWoodInteractables.add(Material.ACACIA_DOOR);
		TypeWoodInteractables.add(Material.DARK_OAK_DOOR);
		TypeWoodInteractables.add(Material.FENCE_GATE);
		TypeWoodInteractables.add(Material.BIRCH_FENCE_GATE);
		TypeWoodInteractables.add(Material.SPRUCE_FENCE_GATE);
		TypeWoodInteractables.add(Material.JUNGLE_FENCE_GATE);
		TypeWoodInteractables.add(Material.ACACIA_FENCE_GATE);
		TypeWoodInteractables.add(Material.DARK_OAK_FENCE_GATE);

		TypeStoneInteractables.add(Material.LEVER);
		TypeStoneInteractables.add(Material.STONE_BUTTON);
		TypeStoneInteractables.add(Material.STONE_PLATE);
		TypeStoneInteractables.add(Material.REDSTONE_COMPARATOR_OFF);
		TypeStoneInteractables.add(Material.REDSTONE_COMPARATOR_ON);
		TypeStoneInteractables.add(Material.DIODE_BLOCK_ON);
		TypeStoneInteractables.add(Material.DIODE_BLOCK_OFF);

		TypeInteractables = new HashSet<Material>();
		TypeInteractables.add(Material.WOOD_BUTTON);
		TypeInteractables.add(Material.WOOD_PLATE);
		TypeInteractables.add(Material.TRAP_DOOR);
		TypeInteractables.add(Material.WOODEN_DOOR);
		TypeInteractables.add(Material.FENCE_GATE);
		TypeInteractables.add(Material.BIRCH_DOOR);
		TypeInteractables.add(Material.BIRCH_FENCE_GATE);
		TypeInteractables.add(Material.SPRUCE_DOOR);
		TypeInteractables.add(Material.SPRUCE_FENCE_GATE);
		TypeInteractables.add(Material.JUNGLE_DOOR);
		TypeInteractables.add(Material.JUNGLE_FENCE_GATE);
		TypeInteractables.add(Material.ACACIA_DOOR);
		TypeInteractables.add(Material.ACACIA_FENCE_GATE);
		TypeInteractables.add(Material.DARK_OAK_DOOR);
		TypeInteractables.add(Material.DARK_OAK_FENCE_GATE);
		TypeInteractables.add(Material.LEVER);
		TypeInteractables.add(Material.STONE_BUTTON);
		TypeInteractables.add(Material.STONE_PLATE);
		TypeInteractables.add(Material.ANVIL);
		TypeInteractables.add(Material.ENCHANTMENT_TABLE);
		TypeInteractables.add(Material.REDSTONE_COMPARATOR_OFF);
		TypeInteractables.add(Material.REDSTONE_COMPARATOR_ON);
		TypeInteractables.add(Material.DIODE_BLOCK_ON);
		TypeInteractables.add(Material.DIODE_BLOCK_OFF);
		TypeInteractables.add(Material.DISPENSER);
		TypeInteractables.add(Material.DROPPER);
		TypeInteractables.add(Material.CHEST);
		TypeInteractables.add(Material.BEACON);
		TypeInteractables.add(Material.ARMOR_STAND);
		TypeInteractables.add(Material.ITEM_FRAME);
		TypeInteractables.add(Material.ENDER_PORTAL_FRAME);
		TypeInteractables.add(Material.BED_BLOCK);

		NRank tempRank = new NRank();
		tempRank.ID = UUID.fromString("d106d1bc-547a-45fa-90ad-1156b7ef8005");
		tempRank.rankName = "Player";
		tempRank.rankDesc = "Default Player Rank";
		tempRank.blockEdit = true;
		tempRank.walkCore = true;
		tempRank.walkEmbedded = true;
		tempRank.walkExposed = true;
		tempRank.chest = true;
		tempRank.invite = false;
		tempRank.kick = false;
		tempRank.kickSameRank = false;
		tempRank.open = false;
		tempRank.close = false;
		tempRank.home = true;
		tempRank.setHome = false;
		tempRank.promote = false;
		tempRank.promoteSameRank = false;
		tempRank.demote = false;
		tempRank.demoteSameRank = false;
		tempRank.name = false;
		tempRank.desc = false;
		tempRank.relate = false;
		tempRank.delete = false;
		StandardRanks.add(tempRank);
		StandardRankOrder.add(tempRank.ID);

		tempRank = new NRank();
		tempRank.ID = UUID.fromString("012b5fea-57fe-4b75-b30e-38c19c80aa38");
		tempRank.rankName = "Moderator";
		tempRank.rankDesc = "Default Moderator Rank";
		tempRank.blockEdit = true;
		tempRank.walkCore = true;
		tempRank.walkEmbedded = true;
		tempRank.walkExposed = true;
		tempRank.chest = true;
		tempRank.invite = true;
		tempRank.kick = true;
		tempRank.kickSameRank = false;
		tempRank.open = true;
		tempRank.close = true;
		tempRank.home = true;
		tempRank.setHome = true;
		tempRank.promote = false;
		tempRank.promoteSameRank = false;
		tempRank.demote = false;
		tempRank.demoteSameRank = false;
		tempRank.name = false;
		tempRank.desc = true;
		tempRank.relate = true;
		tempRank.delete = false;
		StandardRanks.add(tempRank);
		StandardRankOrder.add(tempRank.ID);

		tempRank = new NRank();
		tempRank.ID = UUID.fromString("0ebf5225-f3d2-4fdb-a82d-dd2f83173972");
		tempRank.rankName = "Leader";
		tempRank.rankDesc = "Default Leader Rank";
		tempRank.blockEdit = true;
		tempRank.walkCore = true;
		tempRank.walkEmbedded = true;
		tempRank.walkExposed = true;
		tempRank.chest = true;
		tempRank.invite = true;
		tempRank.kick = true;
		tempRank.kickSameRank = true;
		tempRank.open = true;
		tempRank.close = true;
		tempRank.home = true;
		tempRank.setHome = true;
		tempRank.promote = true;
		tempRank.promoteSameRank = true;
		tempRank.demote = true;
		tempRank.demoteSameRank = true;
		tempRank.name = true;
		tempRank.desc = true;
		tempRank.relate = true;
		tempRank.delete = true;
		StandardRanks.add(tempRank);
		StandardRankOrder.add(tempRank.ID);

		NRelation tempRelation = new NRelation();
		tempRelation.walkEmbedded = true;
		tempRelation.walkExposed = true;
		tempRelation.walkCore = true;
		tempRelation.blockBreak = false;
		tempRelation.blockPlace = false;
		tempRelation.blockInteract = false;
		tempRelation.attack = false;
		tempRelation.openInv = true;
		tempRelation.useWood = true;
		tempRelation.useStone = true;
		tempRelation.water = true;
		tempRelation.lava = true;
		tempRelation.cartPlace = true;
		tempRelation.tnt = false;
		tempRelation.fire = false;
		tempRelation.home = true;
		tempRelation.enemy = false;
		tempRelation.ally = true;
		tempRelation.neutral = false;
		tempRelation.undef = false;
		StandardRelations.put("ally", tempRelation);

		tempRelation = new NRelation();
		tempRelation.walkEmbedded = false;
		tempRelation.walkExposed = true;
		tempRelation.walkCore = false;
		tempRelation.blockBreak = false;
		tempRelation.blockPlace = false;
		tempRelation.blockInteract = false;
		tempRelation.attack = false;
		tempRelation.openInv = false;
		tempRelation.useWood = true;
		tempRelation.useStone = false;
		tempRelation.water = true;
		tempRelation.lava = false;
		tempRelation.cartPlace = false;
		tempRelation.tnt = false;
		tempRelation.fire = false;
		tempRelation.home = false;
		tempRelation.enemy = false;
		tempRelation.ally = false;
		tempRelation.neutral = true;
		tempRelation.undef = false;
		StandardRelations.put("neutral", tempRelation);

		tempRelation = new NRelation();
		tempRelation.walkEmbedded = true;
		tempRelation.walkExposed = true;
		tempRelation.walkCore = true;
		tempRelation.blockBreak = false;
		tempRelation.blockPlace = false;
		tempRelation.blockInteract = false;
		tempRelation.attack = true;
		tempRelation.openInv = true;
		tempRelation.useWood = true;
		tempRelation.useStone = false;
		tempRelation.water = true;
		tempRelation.lava = true;
		tempRelation.cartPlace = true;
		tempRelation.tnt = false;
		tempRelation.fire = true;
		tempRelation.home = false;
		tempRelation.enemy = true;
		tempRelation.ally = false;
		tempRelation.neutral = false;
		tempRelation.undef = false;
		StandardRelations.put("enemy", tempRelation);
	}
}
