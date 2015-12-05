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
			return NNodeList.i.get(o1).name.compareToIgnoreCase(NNodeList.i.get(o2).name);
		}
	};
	
	public boolean isEmbedded()
	{
		if(getFaction() != null)
			return getFaction().getNodeEmbed(ID) < NConfig.i.EmbeddedNodeDefine;
		return false;
	}

	public NFaction getFaction(){ return NFactionList.i.get(faction); }
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
	
	public void delete()
	{
		NFaction faction = getFaction();
		if(faction != null)
		{
			faction.deleteNode(ID);
			NFactionList.i.add(faction);
		}
		for(UUID RID : resourceSet())
		{
			NResource resource = NResourceList.i.get(RID);
			if(resource != null)
			{
				resource.nodeSet.remove(ID);
				NResourceList.i.add(resource);
			}
		}
		for(NChunkID CID : borderChunk)
			NChunkList.i.remove(CID);
		NNodeList.i.remove(ID);
	}
}
