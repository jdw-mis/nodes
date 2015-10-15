package com.nodes.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class NNode
{
	private UUID ID;
	private UUID faction;
	private UUID world;
	private UUID coreChunk;
	private double capPercent;
	private HashSet<UUID> borderChunkList;
	private HashMap<UUID,Boolean> borderNodeList; //gotta figure out how to calc this
	

	public NNode()
	{
		ID = UUID.randomUUID();
	}
	
	//Get Block
    public UUID		getID()				{ return ID; }
    public UUID		getOwner()			{ return faction; }
    public UUID		getWorld()			{ return world; }
    public UUID		getCore()			{ return coreChunk; }
    
    public UUID[]	playersAtCore()
    {
    	ArrayList<UUID> playerArray = new ArrayList<UUID>();
    	NChunk core = NChunkList.get(coreChunk);
    	Entity[] entityArray = Bukkit.getWorld(world).getChunkAt(core.getX(),core.getZ()).getEntities();
    	
    	for(Entity entity : entityArray)
    		if(entity instanceof Player)
    			playerArray.add(entity.getUniqueId());
    	
    	if(playerArray.isEmpty())
    		return null;
    	else
    		return playerArray.toArray(new UUID[playerArray.size()]);
    }
    
    public boolean	capture()
    {
    	return true;
    }
}
