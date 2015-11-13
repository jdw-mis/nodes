package com.nodes.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class NNode
{
	public UUID ID;
	public String name;
	public UUID faction;
	public UUID world;
	public NChunkID coreChunk;
	public double capPercent;
	public boolean coreActive;
	public int coreCountdown;
	private HashSet<NResource> resources;
	private HashSet<NChunkID> borderChunkList;
	private HashMap<UUID,Integer> borderNodeList;

	public NNode()
	{
		ID = UUID.randomUUID();
	}
	
	//Get Block
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
    
    public void addBorderNode( UUID ID )
    {
    	borderNodeList.put(ID, 0);
    }
    
    public Iterator<NChunkID> chunkIter()
    {
    	return borderChunkList.iterator();
    }
}
