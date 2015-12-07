package com.nodes.data;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;

public class NChunkID
{
	public int x;
	public int z;
	public UUID world;

	public NChunkID(Chunk chunk)
	{
		x = chunk.getX();
		z = chunk.getZ();
		world = chunk.getWorld().getUID();
	}

	public NChunkID(int i, int j, UUID k)
	{
		x = i;
		z = j;
		world = k;
	}

	public Chunk getChunk()
	{
		return Bukkit.getWorld(world).getChunkAt(x,z);
	}

	public Location getLoc(int y)
	{
		return new Location(Bukkit.getWorld(world),(x<<4)+8,y,(z<<4)+8);
	}
	
	public boolean equals( Object obj )
	{
		if(!(obj instanceof NChunkID))
			return false;
		NChunkID CID = (NChunkID)obj;
		return this.x == CID.x && this.z == CID.z && world.equals(CID.world);
	}
	
	public int hashCode()
	{
		return Integer.hashCode(x) + Integer.hashCode(z) + world.hashCode();
	}
}
