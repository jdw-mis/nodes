package com.nodes.data;

import java.util.UUID;

import org.json.JSONObject;

public class NRank
{
	//
	public UUID ID;
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
	
	public NRank()
	{
		ID = UUID.randomUUID();
		rankName = "Default Desc";
		rankDesc = "Default Name";
		edit = false;
		walkCore = false;
		chest = false;
		invite = false;
		kick = false;
		kickSameRank = false;
		open = false;
		close = false;
		promote = false;
		promoteSameRank = false;
		demote = false;
		demoteSameRank = false;
		desc = false;
		relate = false;
	}
	
	public NRank(JSONObject json)
	{
		ID = UUID.fromString(json.getString("ID"));
		rankName = json.getString("rankName");
		rankDesc = json.getString("rankDesc");
		edit = json.getBoolean("edit");
		walkCore = json.getBoolean("walkCore");
		chest = json.getBoolean("chest");
		invite = json.getBoolean("invite");
		kick = json.getBoolean("kick");
		kickSameRank = json.getBoolean("kickSameRank");
		open = json.getBoolean("open");
		close = json.getBoolean("close");
		promote = json.getBoolean("promote");
		promoteSameRank = json.getBoolean("promoteSameRank");
		demote = json.getBoolean("demote");
		demoteSameRank = json.getBoolean("demoteSameRank");
		desc = json.getBoolean("desc");
		relate = json.getBoolean("relate");
	}
	
	public JSONObject toJson()
	{
		JSONObject json = new JSONObject();
		json.put("ID",ID);
		json.put("rankName",rankName);
		json.put("rankDesc",rankDesc);
		json.put("edit",edit);
		json.put("walkCore",walkCore);
		json.put("chest",chest);
		json.put("invite",invite);
		json.put("kick",kick);
		json.put("kickSameRank",kickSameRank);
		json.put("open",open);
		json.put("close",close);
		json.put("promote",promote);
		json.put("promoteSameRank",promoteSameRank);
		json.put("demote",demote);
		json.put("demoteSameRank",demoteSameRank);
		json.put("desc",desc);
		json.put("relate",relate);
		return json;
	}
}
