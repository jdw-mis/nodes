package com.nodes.data;

import java.util.HashMap;

import org.bukkit.Chunk;

public class NChunkList
{
	private static HashMap<NChunkID,NChunk> chunkList = new HashMap<NChunkID,NChunk>();
	
	public static void add( NChunk chunk )
	{
		chunkList.put(chunk.ID,chunk);
	}
	
	public static void delete( NChunkID ID )
	{
		chunkList.remove(ID);
	}

	public static boolean contains( NChunkID ID )
	{
		return chunkList.containsKey(ID);
	}
	
	public static NChunk get( NChunkID ID )
	{
		return chunkList.get(ID);
	}
	public static NChunk get( Chunk chunk )
	{
		NChunkID ID = new NChunkID(chunk);
		return get(ID);
	}
}