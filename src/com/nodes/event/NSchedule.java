package com.nodes.event;

import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import com.nodes.data.NNodeList;

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
	}
}
