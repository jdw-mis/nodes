package com.mis.nodes.data;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Chunk;

public class NNode extends NData
{
	public final UUID id;
	private NFaction faction;
	private HashSet<Chunk> zone;
	private NWorld world;
	private Chunk node;
	
	NNode(UUID i)
	{
		id = i;
	}
}
