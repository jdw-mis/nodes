package com.nodes.data;

import java.util.UUID;

import org.json.JSONObject;

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
    
	public JSONObject toJson()
	{
		JSONObject json = new JSONObject();
		json.put("ID", ID);
		json.put("node",node);
		json.put("CID",CID.toJson());
		return json;
	}
}
