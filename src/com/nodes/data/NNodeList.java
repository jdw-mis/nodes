package com.nodes.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

public class NNodeList
{
	private static HashMap<UUID,NNode> nodeList = new HashMap<UUID,NNode>();
	private static HashSet<UUID> modifyList = new HashSet<UUID>();
	private static HashSet<UUID> activeList = new HashSet<UUID>();
	
	public static void add( NNode node )
	{
		if(!nodeList.get(node.ID).equals(node))
		{
			modifyList.add(node.ID);
			nodeList.put(node.ID,node);
			if(node.coreActive)
				activeList.add(node.ID);
		}
	}
	
	public static void delete( UUID ID )
	{
		nodeList.remove(ID);
	}

	public static boolean contains( UUID ID )
	{
		return nodeList.containsKey(ID);
	}

	public static NNode get( UUID ID )
	{
		return nodeList.get(ID);
	}
	
	public static Iterator<UUID> saveIter()
	{
		return modifyList.iterator();
	}
	public static void saveClear()
	{
		modifyList.clear();
	}
	
	public static Iterator<UUID> activeIter()
	{
		return activeList.iterator();
	}
	
	public static Iterator<NNode> saveAllIter()
	{
		return nodeList.values().iterator();
	}
	public static void flush()
	{
		nodeList.clear();
	}
	
	public static void buildNodeGraph()
	{
		NNode node;
		NChunkID chunkID;
		NChunkID[] chunkIDArr = new NChunkID[8];
		NChunk chunk;
		NChunk chunkArr;
		int i;
		Iterator<NNode> iterNode = nodeList.values().iterator();
		Iterator<NChunkID> iterChunk;
		while(iterNode.hasNext())
		{
			node = iterNode.next();
			iterChunk = node.chunkIter();
			while(iterChunk.hasNext())
			{
				chunkID = iterChunk.next();
				chunk = NChunkList.get(chunkID);
				for(i = 7; i >= 0; i-- )
				{
					chunkIDArr[i] = chunkID;
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
					chunkArr = NChunkList.get(chunkIDArr[i]);
					if(chunkArr != null && !chunkArr.node.equals(chunk.node))
					{
						node.addBorderNode(chunkArr.node);
					}
				}
			}
		}
	}

	public static void removeActive(UUID ID)
	{
		activeList.remove(ID);
	}
}
