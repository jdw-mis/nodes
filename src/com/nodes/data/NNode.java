package com.nodes.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

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
	public boolean capital;
	public int coreCountdown;
	public HashSet<UUID> resources;
	public HashSet<NChunkID> borderChunk;
	public HashMap<UUID,Integer> borderNode;

	public NNode()
	{
		ID = UUID.randomUUID();
		name = "";
		faction = null;
		world = null;
		coreChunk = null;
		capPercent = 0;
		coreCountdown = 0;
		resources = new HashSet<UUID>();
		borderChunk = new HashSet<NChunkID>();
		borderNode = new HashMap<UUID,Integer>();
	}

	public boolean isEmbedded()
	{
		return getFaction().getNodeEmbed(ID) < NConfig.EmbeddedNodeDefine;
	}

	public NFaction getFaction(){ return NFactionList.get(faction); }

	//Get Block
	public UUID[]	playersAtCore()
	{
		ArrayList<UUID> playerArray = new ArrayList<UUID>();
		Entity[] entityArray = coreChunk.getChunk().getEntities();
	
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
		borderNode.put(ID, 0);
	}

	public Iterator<UUID> borderIter()
	{
		return borderNode.keySet().iterator();
	}

	public Iterator<NChunkID> chunkIter()
	{
		return borderChunk.iterator();
	}
}
