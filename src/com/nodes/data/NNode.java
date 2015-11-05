package com.nodes.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;

public class NNode
{
	public UUID ID;
	public String name;
	public UUID faction;
	public UUID world;
	public NChunkID coreChunk;
	public double capPercent;
	private HashSet<NChunkID> borderChunkList;
	private HashMap<UUID,Boolean> borderNodeList; //TODO: gotta figure out how to calc this
	

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
    
    public boolean capture()
    {
    	return true;
    }
    
    public JSONObject toJson()
	{
		JSONObject json = new JSONObject();
		json.put("ID",ID);
		json.put("name",name);
		json.put("name",faction);
		json.put("name",world);
		json.put("name",capPercent);
		json.put("borderNodeList", borderNodeList);
		json.put("name",coreChunk.toJson());
		
		JSONArray temp = new JSONArray();
		Iterator<NChunkID> iterChunk = borderChunkList.iterator();
		while(iterChunk.hasNext())
			temp.put(iterChunk.next().toJson());
		json.put("borderChunkList", temp);
		
		return json;
	}
}
