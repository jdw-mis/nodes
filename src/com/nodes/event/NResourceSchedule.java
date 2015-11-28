package com.nodes.event;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Item;

import com.nodes.data.NChunkID;
import com.nodes.data.NConfig;
import com.nodes.data.NNodeList;
import com.nodes.data.NResourceList;


public class NResourceSchedule
{
	private static final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
	
	public static void resourceTimer()
	{
		if(NConfig.OfflineResourceDumps)
		{
			long timeDiff = System.currentTimeMillis() - NResourceList.firstActiveMillis;
			timeDiff/=1000;
			timeDiff/=60;
			timeDiff/=NResourceList.cycleBase;
			
			if(timeDiff > NConfig.OfflineResourceDumpMax)
				timeDiff = NConfig.OfflineResourceDumpMax;
			//TODO: offline resource gibs
		}
		
		final Runnable resourceSpawn = new Runnable()
		{
			public void run()
			{
				spawnResources();
			}
		};
		schedule.scheduleAtFixedRate(resourceSpawn, 1, NResourceList.cycleBase, TimeUnit.MINUTES);
	}
	
	private static void spawnResources()
	{
		ItemStack resStack;
		int resCount;
		boolean dropped;
		ArrayList<Chest> chestArr = new ArrayList<Chest>();
		
		long cycleCount = (System.currentTimeMillis()-NResourceList.firstActiveMillis)/(60000*NResourceList.cycleBase);
		for(Integer cycle : NResourceList.getTimeKeySet())
			if(cycleCount % cycle == 0)
				for(UUID RID : NResourceList.getTimeSet(cycle))
					for(UUID NID : NResourceList.get(RID).nodeSet)
						if(NNodeList.get(NID).faction != null)
						{
							chestArr.clear();
							for(BlockState block : NNodeList.get(NID).coreChunk.getChunk().getTileEntities())
								if(block instanceof Chest)
									chestArr.add((Chest)block);
							for(Entry<Material,Integer> res : NResourceList.get(RID).resourceMap.entrySet())
							{
								dropped = false;
								resStack = new ItemStack(res.getKey(),res.getValue());
								if(chestArr.size() != 0)
									for(Chest chest : chestArr)
									{
										resCount = resStack.getAmount();
										for (ItemStack stack : chest.getInventory().getContents())
										{
											if (stack == null)
												resCount -= resStack.getMaxStackSize();
											if (stack.getType() == resStack.getType())
												if (stack.getDurability() == resStack.getDurability())
													resCount -= resStack.getMaxStackSize() - stack.getAmount();
										}
										if(resCount <= 0);
										{
											chest.getInventory().addItem(resStack);
											dropped = true;
											break;
										}
									}
								if(!dropped)
								{
									NChunkID CID = NNodeList.get(NID).coreChunk;
									Bukkit.getWorld(CID.world).dropItem(new Location(Bukkit.getWorld(CID.world),(CID.x>>4)+8,255,(CID.z>>4)+8),resStack);
								}
							}
						}
	}
}
