package com.nodes.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class NConfig
{
	public static NConfig i = new NConfig();
	
	public long firstActiveMillis;

	public int EmbeddedNodeDefine;
	public boolean EmbeddedNodeFireProtection;
	public boolean EmbeddedNodeExplosionProtection;
	public boolean EmbeddedNodeCreeperProtection;
	public boolean EmbeddedNodeWitherProtection;
	public boolean EmbeddedNodeMonsterSpawn;
	public boolean EmbeddedNodePassiveSpawn;
	public boolean EmbeddedNodeWalkingPrevention;
	public boolean EmbeddedNodeWoodInteractable;
	public boolean EmbeddedNodeStoneInteractable;
	public HashSet<Material> TypeEmbeddedPlaceables;
	public boolean CapitalNodeAlwaysEmbedded;
	public boolean CapitalSurroundingNodesAlwaysEmbedded;

	public boolean ExposedNodeFireProtection;
	public boolean ExposedNodeExplosionProtection;
	public boolean ExposedNodeCreeperProtection;
	public boolean ExposedNodeWitherProtection;
	public boolean ExposedNodeMonsterSpawn;
	public boolean ExposedNodePassiveSpawn;
	public boolean ExposedNodeWalkingPrevention;
	public boolean ExposedNodeWoodInteractable;
	public boolean ExposedNodeStoneInteractable;
	public HashSet<Material> TypeExposedPlaceables;

	public HashSet<Material> TypeWoodInteractables;
	public HashSet<Material> TypeStoneInteractables;
	public HashSet<Material> TypeNaturalBlocks;

	public ChatColor AlliedColor;
	public ChatColor NeutralColor;
	public ChatColor EnemyColor;
	public ChatColor UnrelateColor;

	public int HomeTeleportDelay;
	public boolean HomeEmbeddedOnly;
	public boolean HomeFromEmbedded;
	public boolean HomeFromExposed;
	public boolean HomeFromWild;
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

	public HashMap<String,NRank> StandardRanks;
	public LinkedList<String> StandardRankOrder;
	public HashMap<String,NRelation> StandardRelations;

	public NConfig()
	{
		EmbeddedNodeDefine = 2;
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

		ExposedNodeFireProtection = false;
		ExposedNodeExplosionProtection = false;
		ExposedNodeCreeperProtection = false;
		ExposedNodeWitherProtection = false;
		ExposedNodeMonsterSpawn = false;
		ExposedNodePassiveSpawn = false;
		ExposedNodeWalkingPrevention = false;
		ExposedNodeWoodInteractable = true;
		ExposedNodeStoneInteractable = false;

		AlliedColor = ChatColor.GREEN;
		NeutralColor = ChatColor.AQUA;
		EnemyColor = ChatColor.RED;
		UnrelateColor = ChatColor.GRAY;

		HomeTeleportDelay = 10;
		HomeEmbeddedOnly = true;
		HomeFromEmbedded = true;
		HomeFromExposed = true;
		HomeFromWild = true;
		HomeFromAlly = true;
		HomeFromNeutral = false;
		HomeFromEnemy = false;

		OfflineResourceDumps = false;
		OfflineResourceDumpMax = 0;
		AdditiveCapitalNodes = true;

		AutoSavePulse = 15;
		
		ConnectedNodeClaimOnly = true;
		NodeCapturePulse = 3;
		NodeCaptureCountdownMax = 3;
		NodeCaptureYRestrict = 20;

		CapitalResource = new NResource();
		StandardRanks = new HashMap<String,NRank>();
		StandardRankOrder = new LinkedList<String>();
		StandardRelations = new HashMap<String,NRelation>();
		TypeWoodInteractables = new HashSet<Material>();
		TypeStoneInteractables = new HashSet<Material>();

		TypeExposedPlaceables = new HashSet<Material>();
		TypeEmbeddedPlaceables = new HashSet<Material>();
		TypeNaturalBlocks = new HashSet<Material>();

		TypeWoodInteractables.add(Material.WOOD_BUTTON);
		TypeWoodInteractables.add(Material.WOOD_PLATE);
		TypeWoodInteractables.add(Material.TRAP_DOOR);
		TypeWoodInteractables.add(Material.WOODEN_DOOR);
		TypeWoodInteractables.add(Material.FENCE_GATE);
		TypeWoodInteractables.add(Material.BIRCH_DOOR);
		TypeWoodInteractables.add(Material.BIRCH_FENCE_GATE);
		TypeWoodInteractables.add(Material.SPRUCE_DOOR);
		TypeWoodInteractables.add(Material.SPRUCE_FENCE_GATE);
		TypeWoodInteractables.add(Material.JUNGLE_DOOR);
		TypeWoodInteractables.add(Material.JUNGLE_FENCE_GATE);
		TypeWoodInteractables.add(Material.ACACIA_DOOR);
		TypeWoodInteractables.add(Material.ACACIA_FENCE_GATE);
		TypeWoodInteractables.add(Material.DARK_OAK_DOOR);
		TypeWoodInteractables.add(Material.DARK_OAK_FENCE_GATE);

		TypeStoneInteractables.add(Material.LEVER);
		TypeStoneInteractables.add(Material.STONE_BUTTON);
		TypeStoneInteractables.add(Material.STONE_PLATE);


		NRank tempRank = new NRank();
		tempRank.ID = UUID.fromString("d106d1bc-547a-45fa-90ad-1156b7ef8005");
		tempRank.rankName = "Player";
		tempRank.rankDesc = "Default Player Rank";
		tempRank.edit = true;
		tempRank.walkCore = true;
		tempRank.walkEmbedded = true;
		tempRank.chest = true;
		StandardRanks.put(tempRank.rankName,tempRank);
		StandardRankOrder.add(tempRank.rankName);

		tempRank.ID = UUID.fromString("012b5fea-57fe-4b75-b30e-38c19c80aa38");
		tempRank.rankName = "Moderator";
		tempRank.rankDesc = "Default Moderator Rank";
		tempRank.invite = true;
		tempRank.kick = true;
		tempRank.open = true;
		tempRank.close = true;
		tempRank.desc = true;
		tempRank.relate = true;
		StandardRanks.put(tempRank.rankName,tempRank);
		StandardRankOrder.add(tempRank.rankName);

		tempRank.ID = UUID.fromString("0ebf5225-f3d2-4fdb-a82d-dd2f83173972");
		tempRank.rankName = "Leader";
		tempRank.rankDesc = "Default Leader Rank";
		tempRank.kickSameRank = true;
		tempRank.promote = true;
		tempRank.promoteSameRank = true;
		tempRank.demote = true;
		tempRank.demoteSameRank = true;
		StandardRanks.put(tempRank.rankName,tempRank);
		StandardRankOrder.add(tempRank.rankName);

		NRelation tempRelation = new NRelation();
		tempRelation.neutral = true;
		tempRelation.moveEmbedded = true;
		tempRelation.useWood = true;
		tempRelation.water = true;
		tempRelation.attack = true;
		StandardRelations.put("Neutral", tempRelation);

		tempRelation.neutral = false;
		tempRelation.attack = false;
		tempRelation.ally = true;
		tempRelation.moveCore = true;
		tempRelation.blockBreak = true;
		tempRelation.blockPlace = true;
		tempRelation.openInv = true;
		tempRelation.useStone = true;
		tempRelation.cartPlace = true;
		StandardRelations.put("Ally", tempRelation);


		tempRelation.ally = false;
		tempRelation.blockBreak = false;
		tempRelation.blockPlace = false;
		tempRelation.useStone = false;
		tempRelation.enemy = true;
		tempRelation.attack = true;
		tempRelation.fire = true;
		tempRelation.lava = true;
		StandardRelations.put("Enemy", tempRelation);
	}
}
