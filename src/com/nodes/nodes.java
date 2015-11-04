package com.nodes;

import java.util.ArrayList;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.nodes.data.NDataIO;
import com.nodes.data.NWorld;


public class nodes extends JavaPlugin implements Listener
{

	public static ArrayList<NWorld> worldList = new ArrayList<NWorld>();

    public static JavaPlugin plugin;

	public void onEnable()
	{
		plugin = this;
		getServer().getPluginManager().registerEvents(this, this);

		NDataIO.folder = getDataFolder();
	}
	
	/*public static boolean worldContains( String name )
	{
		Iterator<NWorld> iter = worldList.iterator();
		while(iter.hasNext())
		{
			if(iter.next().getName().equalsIgnoreCase(name));
				return true;
		}
		return false;
	}
	
	public static boolean worldContains( UUID ID )
	{
		Iterator<NWorld> iter = worldList.iterator();
		while(iter.hasNext())
		{
			if(iter.next().getID().equals(ID));
				return true;
		}
		return false;
	}
	
	public static boolean nodeContains( UUID ID )
	{
		Iterator<NFaction> iter = nodeList.iterator();
		while(iter.hasNext())
		{
			if(iter.next().getID().equals(ID));
				return true;
		}
		return false;
	}*/
}
