package com.nodes.event;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

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
import com.nodes.data.NPlayerList;
import com.nodes.data.NRelation;

public class NSchedule
{
	public static void scheduleTasks()
	{
		BukkitScheduler schedule = Bukkit.getServer().getScheduler();

		final Runnable capture = new Runnable()
		{ public void run() { captureNode(); } };
		
		final Runnable autosave = new Runnable()
		{ public void run() { NDataIO.save(); } };

		schedule.runTaskTimerAsynchronously(nodes.plugin, capture, 10, NConfig.i.NodeCapturePulse*20*60);
		schedule.runTaskTimerAsynchronously(nodes.plugin, autosave, NConfig.i.AutoSavePulse*20*60, NConfig.i.AutoSavePulse*20*60);
	}

	public static void captureNode()
	{
		NNode node;
		NFaction faction;
		NRelation relate;
		UUID capperID, tempID;
		Integer capperMem;
		HashMap<UUID,Integer> factionMap;
		LinkedList<UUID> playerList;
		Iterator<UUID> factIter;
		Iterator<UUID> iter = NNodeList.i.activeIter();

		while(iter.hasNext())
		{
			node = NNodeList.i.get(iter.next());
			faction = NFactionList.i.get(node.faction);
			factionMap = new HashMap<UUID,Integer>();
			playerList = new LinkedList<UUID>();

			if(node.capPercent >= 100)
			{
				for(Entity entity:node.coreChunk.getChunk().getEntities())
					if(entity instanceof Player)
					{
						playerList.add(entity.getUniqueId());
						capperID = NPlayerList.i.get(entity.getUniqueId()).faction;
						if(node.faction == null || (capperID != null && faction.getRelation(capperID).enemy))
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
					factIter = factionMap.keySet().iterator();
					capperID = iter.next();
					while(factIter.hasNext())
					{
						tempID = factIter.next();
						if(factionMap.get(capperID) < factionMap.get(tempID))
							capperID = tempID;
					}
					faction.deleteNode(node.ID);
					faction.boilNodes();
					NFactionList.i.add(faction);
					faction = NFactionList.i.get(capperID);
					faction.addNode(node.ID);
					faction.boilNodes();
					NFactionList.i.add(faction);
					node.faction = faction.ID;
				}
				node.capPercent = 99.9;
				node.coreCountdown = 4;
			}
			else if(node.capPercent >= 0)
			{
				for(Entity entity:node.coreChunk.getChunk().getEntities())
					if(entity instanceof Player)
					{
						capperID = NPlayerList.i.get(entity.getUniqueId()).faction;
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
					factIter = factionMap.keySet().iterator();
					while(factIter.hasNext())
					{
						capperID = factIter.next();
						if(capperID.equals(faction.ID))
							node.capPercent -= factionMap.get(capperID); //TODO: capture percent algorithm
						else
						{
							relate = faction.getRelation(capperID);
							if(relate != null)
								if(relate.enemy)
								{
									node.capPercent += factionMap.get(capperID);
									node.coreCountdown = 3;
								}
								else if(relate.ally)
									node.capPercent -= factionMap.get(capperID);
						}
					}
				}
				if(node.coreCountdown <= 0)
					node.capPercent -= 10;
			}
			else if(node.capPercent < 0 && node.coreCountdown <= 0)
			{
				node.coreActive = false;
				node.capPercent = 0;
				node.coreCountdown = 1;
				NNodeList.i.removeActive(node.ID);
			}
			node.coreCountdown--;
			NNodeList.i.add(node);
		}
	}
}
