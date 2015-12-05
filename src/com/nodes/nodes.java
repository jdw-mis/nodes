package com.nodes;


import java.io.File;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.nodes.cmd.NCMD;
import com.nodes.data.NConfig;
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

		NDataIO.folder = new File(getDataFolder().getAbsolutePath());
		
		firstRun = NDataIO.detectFirstRun();

		if(firstRun)
		{
			getLogger().info("First Run Detected");
			NConfig.i.firstActiveMillis = System.currentTimeMillis();
		}
		NDataIO.load();
		getLogger().info("Data Loaded");
		getLogger().info( NDataIO.PNGtoNodes() );
		NNodeList.i.buildNodeGraph();
		getLogger().info("NodeGraph Built");
		NResourceList.i.cycleActual();
		getLogger().info("Resource Cyclical Rate: "+NResourceList.i.cycleBase+"m");
		NFactionList.i.boilAll();
		NResourceSchedule.resourceTimer();
		NSchedule.scheduleTasks();
		getCommand("node").setExecutor(new NCMD(this));
		getServer().getPluginManager().registerEvents(new NEvent(), plugin);
	}


}
