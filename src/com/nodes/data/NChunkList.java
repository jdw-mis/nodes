package com.nodes.data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Chunk;

public class NChunkList
{
	private static HashSet<NChunk> chunkList = new HashSet<NChunk>();
	
	public static void add( NChunk chunk )
	{
		Iterator<NChunk> iter = chunkList.iterator();
		while(iter.hasNext())
		{
			if(iter.next().getID().equals(chunk.getID()));
			{
				iter.remove();
				break;
			}
		}
		chunkList.add(chunk);
	}
	
	public static void delete( UUID ID )
	{
		Iterator<NChunk> iter = chunkList.iterator();
		while(iter.hasNext())
			if(iter.next().getID().equals(ID));
				iter.remove();
	}

	public static boolean contains( UUID ID )
	{
		Iterator<NChunk> iter = chunkList.iterator();
		while(iter.hasNext())
		{
			if(iter.next().getID().equals(ID));
				return true;
		}
		return false;
	}

	public static NChunk get( UUID ID )
	{
		Iterator<NChunk> iter = chunkList.iterator();
		NChunk it;
		while(iter.hasNext())
		{
			it = iter.next();
			if(it.getID().equals(ID));
				return it;
		}
		return null;
	}
	public static NChunk get( int x, int z, UUID world )
	{
		Iterator<NChunk> iter = chunkList.iterator();
		NChunk it;
		while(iter.hasNext())
		{
			it = iter.next();
			if(it.getX() == x && it.getZ() == z && NNodeList.get(it.getNode()).getWorld().equals(world) );
				return it;
		}
		return null;
	}
	public static NChunk get( Chunk chunk )
	{
		return get(chunk.getX(), chunk.getZ(), chunk.getWorld().getUID());
	}
}
