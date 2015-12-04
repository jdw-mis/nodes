package com.nodes.data;

import java.util.HashMap;

import org.bukkit.Chunk;

public class NChunkList
{
	public static NChunkList i = new NChunkList();

	private HashMap<NChunkID,NChunk> chunkMap;
	
	private NChunkList()
	{
		chunkMap = new HashMap<NChunkID,NChunk>();
	}

	public void add( NChunk chunk )
	{
		chunkMap.put(chunk.CID,chunk);
	}

	public void delete( NChunkID ID )
	{
		chunkMap.remove(ID);
	}

	public boolean contains( NChunkID ID )
	{
		return chunkMap.containsKey(ID);
	}

	public NChunk get( NChunkID ID )
	{
		return chunkMap.get(ID);
	}
	public NChunk get( Chunk chunk )
	{
		NChunkID ID = new NChunkID(chunk);
		return get(ID);
	}

	public void flush()
	{
		for(NChunkID CID : chunkMap.keySet())
			delete(CID);
	}
}