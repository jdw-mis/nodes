package com.nodes.data;

import java.util.UUID;

import org.bukkit.Chunk;
import org.json.JSONObject;

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
	
	public NChunkID(JSONObject json)
	{
		x = json.getInt("x");
		z = json.getInt("z");
		world = UUID.fromString(json.getString("world"));
	}
	
	public JSONObject toJson()
	{
		JSONObject json = new JSONObject();
		json.put("x",x);
		json.put("z",z);
		json.put("world",world);
		return json;
	}
}
