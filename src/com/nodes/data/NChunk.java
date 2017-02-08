package com.nodes.data;

import java.util.UUID;

/*
 * this entire class is likely obsolete, to be replaced with something I made in the NWorld class
 */
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

	public boolean	isCore()			{ return NNodeList.i.get(node).coreChunk.equals(ID); }
	public NNode getNode(){return NNodeList.i.get(node);}

	/*
	 * I forget why I needed to override equals and hashcode rip
	 */
	public boolean equals( Object obj )
	{
		if(!(obj instanceof NChunk))
			return false;
		NChunk chunk = (NChunk)obj;
		if(node == null && chunk.node == null)
			return CID.equals(chunk.CID) && ID.equals(chunk.ID);
		else if (node != null)
			return CID.equals(chunk.CID) && node.equals(chunk.node) && ID.equals(chunk.ID);
		else
			return false;
	}

	public int hashCode()
	{
		return ID.hashCode() + node.hashCode() + CID.hashCode();
	}
}
