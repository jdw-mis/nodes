package com.nodes.data;

import java.util.UUID;

import org.json.JSONObject;

public class NRank
{
	//
	public String rankName;
	public String rankDesc;
	// Interaction based
	public Boolean edit;
	public Boolean walkCore;
	public Boolean chest;
	// CMD based
	public Boolean invite;
	public Boolean kick;
	public Boolean kickSameRank;
	public Boolean open;
	public Boolean close;
	public Boolean promote;
	public Boolean promoteSameRank;
	public Boolean demote;
	public Boolean demoteSameRank;
	public Boolean desc;
	public Boolean relate;
	
	public JSONObject toJson()
	{
		return null;
	}
}
