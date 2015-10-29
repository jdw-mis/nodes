package com.nodes.data;

import java.util.UUID;

public class NChunk
{
	private NChunkID ID;
	private UUID node;
	
	public NChunk( NChunkID i, NNode input )
	{
		node = input.getID();
		ID = i;
	}

    public UUID		getNode()			{ return node; }
    public int		getX()				{ return ID.x; }
    public int		getZ()				{ return ID.z; }
    public UUID		getWorld()			{ return ID.world; }
    public NChunkID	getID()				{ return ID; }
    
    public boolean	isCore()			{ return NNodeList.get(node).getCore().equals(ID); }
}
