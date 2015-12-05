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
		if(chunk != null)
		{
			chunkMap.put(chunk.CID,chunk);
		}
	}

	public void remove( NChunkID ID )
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
		return get(new NChunkID(chunk));
	}

	public void flush()
	{
		for(NChunkID CID : chunkMap.keySet())
			remove(CID);
	}
}