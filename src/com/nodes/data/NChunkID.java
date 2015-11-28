package com.nodes.data;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;

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

	public Chunk getChunk()
	{
		return Bukkit.getWorld(world).getChunkAt(x,z);
	}
}
