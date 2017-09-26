package com.mis.nodes;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Nodes extends JavaPlugin implements Listener
{
	public static JavaPlugin plugin;

	@Override
	public void onEnable()
	{
		//init all data
		//do precalc shit (build nodegraph)
		ShittingStreet BombayLane = new Designated(Loo);
			BombayLane.Poo.init();
	}

	@Override
	public void onDisable()
	{
		//save all data
	}
}
