package com.nodes.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

public class NNodeList
{
	private static HashMap<UUID,NNode> nodeMap = new HashMap<UUID,NNode>();
	private static HashMap<String,UUID> nodeNameMap = new HashMap<String,UUID>();
	private static HashSet<UUID> modifySet = new HashSet<UUID>();
	private static HashSet<UUID> activeSet = new HashSet<UUID>();

	public static void add( NNode node )
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

	public static void delete( UUID ID )
	{
		nodeNameMap.remove(nodeMap.get(ID).name);
		nodeMap.remove(ID);
		modifySet.remove(ID);
	}

	public static boolean contains( UUID ID )
	{
		return nodeMap.containsKey(ID);
	}

	public static NNode get( UUID ID )
	{
		return nodeMap.get(ID);
	}

	public static void delete( String name )
	{
		delete(nodeNameMap.get(name.toLowerCase()));
	}

	public static boolean contains( String name )
	{
		return nodeNameMap.containsKey(name.toLowerCase());
	}

	public static NNode get( String name )
	{
		return get(nodeNameMap.get(name.toLowerCase()));
	}

	public static Iterator<UUID> saveIter()
	{
		return modifySet.iterator();
	}
	public static void saveClear()
	{
		modifySet.clear();
	}

	public static Iterator<UUID> activeIter()
	{
		return activeSet.iterator();
	}

	public static Iterator<NNode> saveAllIter()
	{
		return nodeMap.values().iterator();
	}
	public static void flush()
	{
		nodeMap.clear();
	}

	public static void buildNodeGraph()
	{
		NNode node;
		NChunkID chunkID;
		NChunkID[] chunkIDArr = new NChunkID[8];
		NChunk chunk;
		NChunk chunkArr;
		int i;
		Iterator<NNode> iterNode = nodeMap.values().iterator();
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
		activeSet.remove(ID);
	}
}
