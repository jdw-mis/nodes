package com.nodes.data;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Chunk;

public class NChunkList
{
	private static HashMap<NChunkID,NChunk> chunkMap = new HashMap<NChunkID,NChunk>();
	
	public static void add( NChunk chunk )
	{
		chunkMap.put(chunk.CID,chunk);
	}
	
	public static void delete( NChunkID ID )
	{
		chunkMap.remove(ID);
	}

	public static boolean contains( NChunkID ID )
	{
		return chunkMap.containsKey(ID);
	}
	
	public static NChunk get( NChunkID ID )
	{
		return chunkMap.get(ID);
	}
	public static NChunk get( Chunk chunk )
	{
		NChunkID ID = new NChunkID(chunk);
		return get(ID);
	}
	
	public static Iterator<NChunk> saveAllIter()
	{
		return chunkMap.values().iterator();
	}
	public static void flush()
	{
		chunkMap.clear();
	}
}