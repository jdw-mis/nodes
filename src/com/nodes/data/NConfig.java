package com.nodes.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

public class NConfig
{
	public NConfig(){defaultConfig();}
	
	public static boolean ConnectedNodeClaimOnly;
	public static int NodeCapturePulse;
	public static int NodeCaptureCountdownMax;
	public static int NodeCaptureYRestrict;
	public static HashMap<String,NRank> StandardRanks;
	public static LinkedList<String> StandardRankOrder;
	public static HashMap<String,NRelation> StandardRelations;
	
	public static void defaultConfig()
	{
		ConnectedNodeClaimOnly = true;
		NodeCapturePulse = 3;
		NodeCaptureCountdownMax = 3;
		NodeCaptureYRestrict = 20;
		
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
		tempRelation.move = true;
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
