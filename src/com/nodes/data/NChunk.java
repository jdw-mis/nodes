package com.nodes.data;

import java.util.UUID;

import org.json.JSONObject;

public class NChunk
{
	public NChunkID ID;
	public UUID node;
	
	public NChunk( NChunkID i, NNode input )
	{
		node = input.ID;
		ID = i;
	}

    public int		getX()				{ return ID.x; }
    public int		getZ()				{ return ID.z; }
    public UUID		getWorld()			{ return ID.world; }
    
    public boolean	isCore()			{ return NNodeList.get(node).coreChunk.equals(ID); }
    
	public JSONObject toJson()
	{
		JSONObject json = new JSONObject();
		json.put("node",node);
		json.put("CID",ID.toJson());
		return json;
	}
}
