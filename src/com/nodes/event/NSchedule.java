package com.nodes.event;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import com.nodes.data.NChunk;
import com.nodes.data.NChunkList;
import com.nodes.data.NFaction;
import com.nodes.data.NFactionList;
import com.nodes.data.NNode;
import com.nodes.data.NNodeList;
import com.nodes.data.NPlayer;
import com.nodes.data.NPlayerList;
import com.nodes.data.NRelation;
import com.nodes.data.NRelationList;

public class NSchedule
{	
	public static void scheduleTasks()
	{
		BukkitScheduler schedule = Bukkit.getServer().getScheduler();

		/*final Runnable resourceSpawn = new Runnable()
		{
			
		}*/
	}
	
	public static void captureNode()
	{
		Iterator<UUID> iter = NNodeList.activeIter();
		NNode node;
		NFaction faction;
		NRelation relate;
		UUID capperID, tempID;
		Integer capperMem;
		HashMap<UUID,Integer> factionMap;
		Iterator<UUID> factIter;
		
		while(iter.hasNext())
		{
			node = NNodeList.get(iter.next());
			faction = NFactionList.get(node.faction);
			factionMap = new HashMap<UUID,Integer>();
			
			if(node.capPercent >= 100)
			{
				for(Entity entity:node.coreChunk.getChunk().getEntities())
				{
					if(entity instanceof Player)
					{
						capperID = NPlayerList.get(entity.getUniqueId()).faction;
						if(capperID != null && NRelationList.get(faction.getRelation(capperID)).enemy)
						{
							capperMem = factionMap.get(capperID);
							if(capperMem == null)
								factionMap.put(capperID,1);
							else
								factionMap.put(capperID,capperMem++);
						}
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
					NFactionList.add(faction);
					faction = NFactionList.get(capperID);
					faction.addNode(node.ID);
					NFactionList.add(faction);
					node.faction = faction.ID;
				}
				node.capPercent = 99.9;
				NNodeList.add(node);
			}
			else if(node.capPercent >= 0)
			{
				for(Entity entity:node.coreChunk.getChunk().getEntities())
				{
					if(entity instanceof Player)
					{
						capperID = NPlayerList.get(entity.getUniqueId()).faction;
						if(capperID != null)
						{
							if(capperID.equals(faction.ID))
							{
								node.capPercent--; //TODO: capture algorithm
							}
							else
							{
								relate = NRelationList.get(faction.getRelation(capperID));
								if(relate != null)
								{
									if(relate.enemy)
									{
										node.capPercent++;
										node.coreCountdown = 4;
									}
									else if(relate.ally)
									{
										node.capPercent--;
									}
								}
							}
						}
					}
				}
				node.coreCountdown--;
				if(node.coreCountdown == 0)
				{
					
				}
			}
			else
			{
				
			}
		}
	}
}
