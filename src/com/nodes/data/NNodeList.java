package com.nodes.data;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Chunk;

import com.nodes.nodes;

public class NNodeList
{
	public static NNodeList i = new NNodeList();

	private HashMap<UUID,NNode> nodeMap;
	private HashMap<String,UUID> nodeNameMap;
	private HashSet<UUID> modifySet;
	private HashSet<UUID> activeSet;

	private NNodeList()
	{
		nodeMap = new HashMap<UUID,NNode>();
		nodeNameMap = new HashMap<String,UUID>();
		modifySet = new HashSet<UUID>();
		activeSet = new HashSet<UUID>();
	}

	public void add( NNode node )
	{
		if(node != null)
		{
			if(node.coreActive && !activeSet.contains(node.ID))
			{
				node.coreCountdown = NConfig.i.NodeCaptureCountdownMax;
				activeSet.add(node.ID);
				nodes.plugin.getLogger().info("Active Node Added: "+node.name);
			}
			nodeMap.put(node.ID,node);
			nodeNameMap.put(node.name.toLowerCase(),node.ID);
			modifySet.add(node.ID);
		}
	}

	public void remove( UUID ID )
	{
		NNode node = nodeMap.get(ID);
		if(node != null)
			nodeNameMap.remove(node.name);
		else
			nodeNameMap.values().removeAll(Collections.singleton(ID));
		nodeMap.remove(ID);
		activeSet.remove(ID);
		modifySet.add(ID);
	}

	public boolean contains( UUID ID )
	{
		return nodeMap.containsKey(ID);
	}

	public NNode get( UUID ID )
	{
		return nodeMap.get(ID);
	}

	public NNode get( Chunk c )
	{
		NChunk chunk = NChunkList.i.get(c);
		NWorld world = NWorldList.i.worldMap.get(c.getWorld().getUID());
		if(world == null || !world.isInBounds(c.getX(), c.getZ()))
			return null;
		if((chunk == null || chunk.getNode() == null))
		{
			NChunkID ID = new NChunkID(c);
			while((chunk == null || chunk.getNode() == null) && world.isInBounds(ID.x,ID.z))
			{
				ID.x++;
				chunk = NChunkList.i.get(ID);
			}
			if(!world.isInBounds(ID.x,ID.z))
				return null;
		}
		return chunk.getNode();
	}

	public void remove( String name )
	{
		remove(nodeNameMap.get(name.toLowerCase()));
	}

	public boolean contains( String name )
	{
		return nodeNameMap.containsKey(name.toLowerCase());
	}

	public NNode get( String name )
	{
		return get(nodeNameMap.get(name.toLowerCase()));
	}

	public Collection<NNode> nodeSet()
	{
		return nodeMap.values();
	}

	public HashSet<UUID> modifySet()
	{
		return modifySet;
	}
	public void modifyClear()
	{
		modifySet.clear();
	}

	public Set<UUID> idSet()
	{
		return nodeMap.keySet();
	}

	public HashSet<UUID> activeSet()
	{
		return activeSet;
	}

	public void flush()
	{
		for(UUID NID : nodeMap.keySet())
			remove(NID);
	}

	public void buildNodeGraph()
	{
		HashSet<NNode> nodeArr = new HashSet<NNode>(nodeMap.values());
		NChunkID[] chunkIDArr = new NChunkID[8];
		NChunk chunkArr;
		int i;
		for(NNode node : nodeArr)
		{
			for(NChunkID CID : node.borderChunk)
			{
				for(i = 0; i < 8; i++ )
					chunkIDArr[i] = new NChunkID(CID.x,CID.z,CID.world);
				chunkIDArr[0].x--;
				chunkIDArr[1].x++;
				chunkIDArr[2].z--;
				chunkIDArr[3].z++;
				chunkIDArr[4].x--;
				chunkIDArr[4].z--;
				chunkIDArr[5].x--;
				chunkIDArr[5].z++;
				chunkIDArr[6].x++;
				chunkIDArr[6].z--;
				chunkIDArr[7].x++;
				chunkIDArr[7].z++;
				for(i = 0; i < 8; i++ )
				{
					chunkArr = NChunkList.i.get(chunkIDArr[i]);
					if(chunkArr != null && !chunkArr.node.equals(node.ID))
						node.borderNode.add(chunkArr.node);
				}
			}
			NNodeList.i.add(node);
		}
	}

	public void removeActive(UUID ID)
	{
		activeSet.remove(ID);
	}
}
