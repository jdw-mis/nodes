package com.nodes;


import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.nodes.data.NDataIO;
import com.nodes.data.NFactionList;
import com.nodes.data.NNodeList;
import com.nodes.data.NResourceList;
import com.nodes.event.NResourceSchedule;
import com.nodes.event.NSchedule;


public class nodes extends JavaPlugin implements Listener
{
	public static JavaPlugin plugin;
	public boolean firstRun;
	
	
	public void onEnable()
	{
		plugin = this;
		getServer().getPluginManager().registerEvents(this, this);

		NDataIO.folder = getDataFolder();
		
		detectFirstRun();
		
		if(firstRun)
		{
			NDataIO.PNGtoNodes();
			NResourceList.firstActiveMillis = System.currentTimeMillis();
		}
		else
		{
			NDataIO.load();
			NFactionList.boilAll();
		}
		NNodeList.buildNodeGraph();
		NResourceSchedule.resourceTimer();
		NSchedule.scheduleTasks();
	}
	
	private void detectFirstRun()
	{
		firstRun = true;
	}
}
