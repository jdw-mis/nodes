package com.nodes.data;

import java.util.HashSet;
import java.util.UUID;

import org.json.JSONObject;

public class NWorld
{
	public UUID ID;
	private HashSet<UUID> nodeList;
	
	public NWorld( JSONObject json )
	{
		ID = UUID.fromString(json.getString("ID"));
	}
	
	public JSONObject toJson()
	{
		JSONObject json = new JSONObject();
		json.put("ID",ID);
		json.put("nodeList",nodeList);
		return json;
	}
}
