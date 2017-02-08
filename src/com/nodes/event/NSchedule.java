package com.nodes.event;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import com.nodes.nodes;
import com.nodes.data.NConfig;
import com.nodes.data.NDataIO;
import com.nodes.data.NFaction;
import com.nodes.data.NFactionList;
import com.nodes.data.NNode;
import com.nodes.data.NNodeList;
import com.nodes.data.NPlayer;
import com.nodes.data.NPlayerList;
import com.nodes.data.NRelation;

public class NSchedule
{
	static BukkitScheduler schedule = Bukkit.getServer().getScheduler();
	public static void scheduleTasks()
	{
		final Runnable autosave = new Runnable()
		{ public void run() { NDataIO.save(false); } };
		final Runnable capture = new Runnable()
		{ public void run() { captureNode(); } };
		schedule.runTaskTimerAsynchronously(nodes.plugin, capture, 10, NConfig.i.NodeCapturePulse*20);
		schedule.runTaskTimerAsynchronously(nodes.plugin, autosave, NConfig.i.AutoSavePulse*20*60, NConfig.i.AutoSavePulse*20*60);
	}

	private static HashSet<UUID> playerSet = new HashSet<UUID>();
	private static CountDownLatch latch;

	/*
	 * runs async, mostly
	 */
	public static void captureNode()
	{
		NNode border;
		NPlayer player;
		NFaction faction;
		NFaction factionCap;
		NRelation relate;
		UUID capperID;
		Integer capperMem;
		String output;
		String pRel;
		LinkedList<NNode> nodeSet = new LinkedList<NNode>();
		for(UUID NID : NNodeList.i.activeSet())
			nodeSet.add(NNodeList.i.get(NID));
		LinkedList<UUID> factID = new LinkedList<UUID>();
		HashMap<UUID,Integer> factionMap = new HashMap<UUID,Integer>();
		DecimalFormat df = new DecimalFormat("0.#");
		Runnable getChickens;

		for(NNode node : nodeSet)
		{
			if(node == null)
				continue;
			latch = new CountDownLatch(1); //bukkit doesn't let you read this in async
			getChickens = new Runnable(){ public void run() { getChickens(node); } };
			schedule.runTask(nodes.plugin, getChickens);
			try { latch.await(); } catch (InterruptedException e) { e.printStackTrace(); }
			faction = NFactionList.i.get(node.faction);
			factionMap.clear();
			output = "";
			
			/*
			 * NODE CAPTURE SECTION
			 */
			if(node.capPercent >= 100) //node is to be captured
			{
				//Counts number of players per faction
				for(UUID PID : playerSet)
				{
					capperID = NPlayerList.i.get(PID).faction;
					if(capperID != null && (faction == null /* unowned */ || (faction.getRelation(capperID) != null && faction.getRelation(capperID).enemy)))
					{
						capperMem = factionMap.get(capperID);
						if(capperMem == null)
							factionMap.put(capperID,1);
						else
							factionMap.put(capperID,capperMem++);
					}
				}
				//no transfer if no one is in it somehow
				if(!factionMap.isEmpty())
				{
					factID.clear();

					if(NConfig.i.ConnectedNodeClaimOnly) //only adds those who have connected claims to factID
					{
						for(UUID NID : node.borderNode)
						{
							border = NNodeList.i.get(NID);
							if(!(border == null || border.faction == null || border.filler || border.coreChunk == null) && factionMap.containsKey(border.faction))
								factID.add(border.faction);
						}
						//for factions with no nodes at all
						for(UUID FID : factionMap.keySet())
						{
							factionCap = NFactionList.i.get(FID);
							if(factionCap != null && factionCap.getNodesSize() <= 0)
								factID.add(FID);
						}
					}
					else //adds everyone
						factID = new LinkedList<UUID>(factionMap.keySet());
					
					//gets faction with most pop in the node, else null
					if(!factID.isEmpty())
					{
						capperID = factID.getFirst();
						for(UUID FID : factionMap.keySet())
							if(factionMap.get(capperID) < factionMap.get(FID))
								capperID = FID;
					}
					else
						capperID = null;
					
					//removes from owner faction if exist
					if(faction != null)
					{
						faction.deleteNode(node.ID);
						faction.boilNodes();
						NFactionList.i.add(faction);
					}
					
					if(capperID != null) //adds to faction that captured it
					{
						factionCap = NFactionList.i.get(capperID);
						factionCap.addNode(node.ID);
						factionCap.boilNodes();
						NFactionList.i.add(factionCap);
						node.faction = factionCap.ID;
						for(UUID PID : factionCap.playersOnline())
							playerSet.add(PID);
						if(faction == null)
							output = node.name+" has been captured by "+factionCap.name+"!";
						else
							output = node.name+" has been captured from "+faction.name+" by "+factionCap.name+"!";
					}
					else if(faction != null) //returns faction to null owner
						output = node.name+" has been returned to Wild.";
					else
						output = node.name+" cannot be captured, no connected claims!";
				}
				node.capPercent = 99.9;
				node.coreCountdown = 4;
			}
			
			
			else if(node.capPercent <= 0.01 && node.coreCountdown <= 0) //node to be removed from the active list
			{
				node.coreActive = false;
				node.capPercent = 0;
				node.coreCountdown = 1;
				NNodeList.i.removeActive(node.ID);
				output = node.name + " has been secured.";
			}
			
			
			else if(node.capPercent >= -0.01) //node actively being captured
			{
				for(UUID PID : playerSet)
				{
					capperID = NPlayerList.i.get(PID).faction;
					if(capperID != null)
					{
						capperMem = factionMap.get(capperID);
						if(capperMem == null)
							factionMap.put(capperID,1);
						else
							factionMap.put(capperID,capperMem++);
					}
				}
				if(!factionMap.isEmpty())
				{
					for(UUID FID : factionMap.keySet())
					{
						if(faction != null)
						{
							if(FID.equals(faction.ID))
							{
								node.capPercent -= factionMap.get(FID)*10; //TODO: capture percent algorithm
							}
							relate = faction.getRelation(FID);
							if(relate != null)
								if(relate.enemy)
								{
									node.capPercent += factionMap.get(FID)*10;
									node.coreCountdown = 3;
								}
								else if(relate.ally)
									node.capPercent -= factionMap.get(FID)*10;
						}
						else
						{
							node.capPercent += factionMap.get(FID)*10;
							node.coreCountdown = 3;
						}
					}
				}
				if(node.coreCountdown <= 0)
					node.capPercent -= 10;
				if(node.capPercent < 0)
					node.capPercent = -0.01;
				output = NConfig.i.EnemyColor + node.name + " is now "+df.format(node.capPercent)+"% captured.";
			}
			//sends messages out
			if(faction != null)
			{
				for(UUID PID : faction.playersOnline())
					playerSet.add(PID);
				for(UUID PID : playerSet)
				{
					player = NPlayerList.i.get(PID);
					if(player != null)
						player.getPlayer().sendMessage(faction.getRelationColor(player.faction)+output);
				}
			}
			else
			{
				pRel = NConfig.i.UnrelateColor.toString();
				for(UUID PID : playerSet)
				{
					player = NPlayerList.i.get(PID);
					if(player != null)
						player.getPlayer().sendMessage(pRel+output);
				}
			}
			node.coreCountdown--;
			nodes.plugin.getLogger().info("CountDown: "+node.coreCountdown);
			NNodeList.i.add(node);
		}
	}

	/*
	 * because bukkit doesn't let you even Read information if you're working async
	 */
	private static void getChickens(NNode node)
	{
		playerSet.clear();
		for(Entity entity:node.coreChunk.getChunk().getEntities())
			if(entity instanceof Player)
				playerSet.add(entity.getUniqueId());
		latch.countDown();
	}
}
