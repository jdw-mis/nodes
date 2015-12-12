package com.nodes.event;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import com.nodes.data.NChunkID;
import com.nodes.data.NConfig;
import com.nodes.data.NNode;
import com.nodes.data.NNodeList;
import com.nodes.data.NResource;
import com.nodes.data.NResourceID;
import com.nodes.data.NResourceList;


public class NResourceSchedule
{
	private static final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);

	public static void resourceTimer()
	{
		/*if(NConfig.i.OfflineResourceDumps)
		{
			long timeDiff = System.currentTimeMillis() - NConfig.i.firstActiveMillis;
			timeDiff/=1000;
			timeDiff/=60;
			timeDiff/=NResourceList.i.cycleBase;

			if(timeDiff > NConfig.i.OfflineResourceDumpMax)
				timeDiff = NConfig.i.OfflineResourceDumpMax;
			//TODO: offline resource gibs
		}*/

		final Runnable resourceSpawn = new Runnable()
		{
			public void run()
			{
				spawnResources();
			}
		};

		if(NResourceList.i.cycleBase > 0)
			schedule.scheduleAtFixedRate(resourceSpawn, 1, NResourceList.i.cycleBase, TimeUnit.MINUTES);
	}

	private static void spawnResources()
	{
		ItemStack resStack;
		int resCount;
		boolean dropped;
		NNode node;
		NResource resource;
		NChunkID CID;
		ArrayList<Chest> chestArr = new ArrayList<Chest>();

		for(Integer cycle : NResourceList.i.getTimeKeySet())
			if( cycle != null && ((System.currentTimeMillis()-NConfig.i.firstActiveMillis)/(60000*NResourceList.i.cycleBase)) % cycle == 0)
				for(UUID RID : NResourceList.i.getTimeSet(cycle))
					for(UUID NID : NResourceList.i.get(RID).nodeSet)
					{
						node = NNodeList.i.get(NID);
						if(node != null && node.faction != null && node.coreChunk != null && !node.filler)
						{
							chestArr.clear();
							for(BlockState block : node.coreChunk.getChunk().getTileEntities())
								if(block instanceof Chest)
									chestArr.add((Chest)block);
							resource = NResourceList.i.get(RID);
							if(resource == null)
								continue;
							for(NResourceID res : resource.resourceSet)
							{
								if(res == null)
									continue;
								dropped = false;
								resStack = res.getItem();
								if( resStack != null && chestArr.size() != 0)
									ChestLoop:
									for(Chest chest : chestArr)
									{
										resCount = resStack.getAmount();
										for (ItemStack stack : chest.getInventory().getContents())
											if (stack == null)
											{
												resCount -= resStack.getMaxStackSize();
												if(resCount <= 0);
												{
													chest.getInventory().addItem(resStack);
													dropped = true;
													break ChestLoop;
												}
											}
											else if (stack.getType() == resStack.getType() && stack.getDurability() == resStack.getDurability())
											{
												resCount -= resStack.getMaxStackSize() - stack.getAmount();
												if(resCount <= 0);
												{
													chest.getInventory().addItem(resStack);
													dropped = true;
													break ChestLoop;
												}
											}
									}
								if(!dropped)
								{
									CID = node.coreChunk;
									Bukkit.getWorld(CID.world).dropItem(CID.getLoc(255),resStack);
								}
							}
						}
					}
	}
}
