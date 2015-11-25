package com.nodes.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.UUID;

import org.bukkit.Material;

public class NConfig
{
	public NConfig(){defaultConfig();}

	public static int EmbeddedNodeDefine;
	public static boolean EmbeddedNodeFireProtection;
	public static boolean EmbeddedNodeExplosionProtection;
	public static boolean EmbeddedNodeCreeperProtection;
	public static boolean EmbeddedNodeWitherProtection;
	public static boolean EmbeddedNodeMonsterSpawn;
	public static boolean EmbeddedNodePassiveSpawn;
	public static boolean EmbeddedNodeWalkingPrevention;
	public static boolean EmbeddedNodeWoodInteractable;
	public static boolean EmbeddedNodeStoneInteractable;
	public static HashSet<Material> TypeEmbeddedPlaceables;
	public static boolean CoreNodeAlwaysEmbedded;
	public static boolean CoreSurroundingNodesAlwaysEmbedded;
	
	public static boolean ExposedNodeFireProtection;
	public static boolean ExposedNodeExplosionProtection;
	public static boolean ExposedNodeCreeperProtection;
	public static boolean ExposedNodeWitherProtection;
	public static boolean ExposedNodeMonsterSpawn;
	public static boolean ExposedNodePassiveSpawn;
	public static boolean ExposedNodeWalkingPrevention;
	public static boolean ExposedNodeWoodInteractable;
	public static boolean ExposedNodeStoneInteractable;
	public static HashSet<Material> TypeExposedPlaceables;
	
	public static HashSet<Material> TypeWoodInteractables;
	public static HashSet<Material> TypeStoneInteractables;
	public static HashSet<Material> TypeNaturalBlocks;
	
	public static boolean ConnectedNodeClaimOnly;
	public static int NodeCapturePulse;
	public static int NodeCaptureCountdownMax;
	public static int NodeCaptureYRestrict;
	//public static NodeCaptureFormulaOwner;
	//public static NodeCaptureFormulaAlly;
	//public static NodeCaptureFormulaEnemy;
	
	public static HashMap<String,NRank> StandardRanks;
	public static LinkedList<String> StandardRankOrder;
	public static HashMap<String,NRelation> StandardRelations;
	
	public static void defaultConfig()
	{
		StandardRanks = new HashMap<String,NRank>();
		StandardRankOrder = new LinkedList<String>();
		StandardRelations = new HashMap<String,NRelation>();
		TypeWoodInteractables = new HashSet<Material>();
		TypeStoneInteractables = new HashSet<Material>();
		
		
		ConnectedNodeClaimOnly = true;
		NodeCapturePulse = 3;
		NodeCaptureCountdownMax = 3;
		NodeCaptureYRestrict = 20;
		
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
		tempRank.walkInner = true;
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
		tempRelation.moveInner = true;
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
