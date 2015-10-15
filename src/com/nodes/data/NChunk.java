package com.nodes.data;

import java.util.UUID;

import org.bukkit.Chunk;

public class NChunk
{
	private UUID ID;
	private UUID node;
	private int x;
	private int z;
	
	public NChunk( Chunk chunk, NNode input )
	{
		ID = UUID.randomUUID();
		node = input.getID();
		x = chunk.getX();
		z = chunk.getZ();
	}

    public UUID		getID()				{ return ID; }
    public UUID		getNode()			{ return node; }
    public int		getX()				{ return x; }
    public int		getZ()				{ return z; }
    
    
    public boolean	isCore()			{ return NNodeList.get(node).getCore().equals(ID); }
}
