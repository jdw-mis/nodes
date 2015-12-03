package com.nodes;


import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.nodes.cmd.NCMD;
import com.nodes.data.NDataIO;
import com.nodes.data.NFactionList;
import com.nodes.data.NNodeList;
import com.nodes.data.NResourceList;
import com.nodes.event.NEvent;
import com.nodes.event.NResourceSchedule;
import com.nodes.event.NSchedule;


public class nodes extends JavaPlugin implements Listener
{
	public static JavaPlugin plugin;
	public Boolean firstRun;


	public void onEnable()
	{
		getLogger().info("Nodes Initializing");
		plugin = this;

		NDataIO.folder = getDataFolder();
		getLogger().info("Nodes Initializing");


		firstRun = NDataIO.detectFirstRun(); //true = first ; false = no ; null = first but data

		if(firstRun)
		{
			getLogger().info("First Run Detected");
			NResourceList.firstActiveMillis = System.currentTimeMillis();
		}
		NDataIO.load();
		getLogger().info("Data Loaded");
		getLogger().info( NDataIO.PNGtoNodes() );
		NNodeList.buildNodeGraph();
		getLogger().info("NodeGraph Built");
		NResourceList.cycleActual();
		getLogger().info("Resource Cyclical Rate: "+NResourceList.cycleBase+"m");
		NFactionList.boilAll();
		NResourceSchedule.resourceTimer();
		NSchedule.scheduleTasks();
		getServer().getPluginManager().registerEvents(new NCMD(), plugin);
		getServer().getPluginManager().registerEvents(new NEvent(), plugin);
	}


}
