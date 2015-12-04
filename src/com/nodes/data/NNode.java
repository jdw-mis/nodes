package com.nodes.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class NNode
{
	public UUID ID;
	public String name;
	public int argb;
	public UUID faction;
	public UUID world;
	public NChunkID coreChunk;
	public double capPercent;
	public boolean coreActive;
	public boolean capital;
	public boolean filler;
	public int coreCountdown;
	public HashSet<UUID> resources;
	public HashSet<NChunkID> borderChunk;
	public HashSet<UUID> borderNode;

	public NNode()
	{
		ID = UUID.randomUUID();
		name = ID.toString();
		argb = 0;
		faction = null;
		world = null;
		coreChunk = null;
		capPercent = 0;
		coreActive = false;
		capital = false;
		filler = false;
		coreCountdown = 0;
		resources = new HashSet<UUID>();
		borderChunk = new HashSet<NChunkID>();
		borderNode = new HashSet<UUID>();
	}

	public static Comparator<UUID> nodeNameComp = new Comparator<UUID>()
	{
		public int compare(UUID o1, UUID o2)
		{
			return NNodeList.get(o1).name.compareToIgnoreCase(NNodeList.get(o2).name);
		}
	};
	
	public boolean isEmbedded()
	{
		return getFaction().getNodeEmbed(ID) < NConfig.EmbeddedNodeDefine;
	}

	public NFaction getFaction(){ return NFactionList.get(faction); }
	public HashSet<UUID> resourceSet(){ return resources; }

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
		borderNode.add(ID);
	}

	public Iterator<UUID> borderIter()
	{
		return borderNode.iterator();
	}

	public Iterator<NChunkID> chunkIter()
	{
		return borderChunk.iterator();
	}
}
