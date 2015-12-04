package com.nodes.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

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
		if(node.coreActive)
		{
			node.coreCountdown = 3;
			activeSet.add(node.ID);
		}
		nodeMap.put(node.ID,node);
		nodeNameMap.put(node.name.toLowerCase(),node.ID);
		modifySet.add(node.ID);
	}

	public void delete( UUID ID )
	{
		NNode node = nodeMap.get(ID);
		if(node != null)
		{
			if(nodeMap.get(ID).faction != null)
			{
				NFaction faction = nodeMap.get(ID).getFaction();
				faction.deleteNode(ID);
				NFactionList.i.add(faction);
			}
			for(UUID RID : nodeMap.get(ID).resourceSet())
			{
				NResource resource = NResourceList.i.get(RID);
				resource.nodeSet.remove(ID);
				NResourceList.i.add(resource);
			}
			for(NChunkID CID : node.borderChunk )
				NChunkList.i.delete(CID);
		}
		nodeNameMap.remove(nodeMap.get(ID).name);
		nodeMap.remove(ID);
		modifySet.remove(ID);
		activeSet.remove(ID);
	}

	public boolean contains( UUID ID )
	{
		return nodeMap.containsKey(ID);
	}

	public NNode get( UUID ID )
	{
		return nodeMap.get(ID);
	}

	public void delete( String name )
	{
		delete(nodeNameMap.get(name.toLowerCase()));
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

	public Iterator<UUID> saveIter()
	{
		return modifySet.iterator();
	}
	public void saveClear()
	{
		modifySet.clear();
	}

	public Iterator<UUID> activeIter()
	{
		return activeSet.iterator();
	}

	public Iterator<NNode> saveAllIter()
	{
		return nodeMap.values().iterator();
	}
	public void flush()
	{
		for(UUID NID : nodeMap.keySet())
			delete(NID);
	}

	public void buildNodeGraph()
	{
		NChunkID[] chunkIDArr = new NChunkID[8];
		NChunk chunk;
		NChunk chunkArr;
		int i;
		for(NNode node : nodeMap.values())
		{
			node.borderNode.clear();
			for(NChunkID CID : node.borderChunk)
			{
				chunk = NChunkList.i.get(CID);
				for(i = 7; i >= 0; i-- )
				{
					chunkIDArr[i] = CID;
				}
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
				for(i = 7; i >= 0; i-- )
				{
					chunkArr = NChunkList.i.get(chunkIDArr[i]);
					if(chunkArr != null && !chunkArr.node.equals(chunk.node))
					{
						node.addBorderNode(chunkArr.node);
					}
				}
			}
		}
	}

	public void removeActive(UUID ID)
	{
		activeSet.remove(ID);
	}
}
