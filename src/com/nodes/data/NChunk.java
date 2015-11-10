package com.nodes.data;

import java.util.UUID;

public class NChunk
{
	public UUID ID;
	public NChunkID CID;
	public UUID node;
	
	public NChunk( NChunkID i, NNode input )
	{
		ID = UUID.randomUUID();
		node = input.ID;
		CID = i;
	}

    public int		getX()				{ return CID.x; }
    public int		getZ()				{ return CID.z; }
    public UUID		getWorld()			{ return CID.world; }
    
    public boolean	isCore()			{ return NNodeList.get(node).coreChunk.equals(ID); }
}
