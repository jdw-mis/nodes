package com.mis.nodes;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.mis.nodes.cmd.NCMD;
import com.mis.nodes.data.Storage;
import com.mis.nodes.event.Defaults;

public class Nodes extends JavaPlugin implements Listener
{
	public static JavaPlugin	plugin;
	public static Logger		log;

	public Nodes()
	{
		plugin = this;
		log = this.getLogger();
	}

	@Override
	public void onEnable()
	{
		Storage.init_storage( getDataFolder().toString() );
		new Defaults( this );
		// init all data
		// do precalc shit (build nodegraph)
		this.getCommand( "node" ).setExecutor( new NCMD() );
	}

	@Override
	public void onDisable()
	{
		// save all data
		log.info( "Goodbye" );
		Storage.dump_storage();
		log.info( Storage.Players.toString() );
	}

}
