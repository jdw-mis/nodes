package com.nodes.data;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.json.JSONObject;

public class NPlayer
{
	public UUID ID;
	public String name;
	public String title;
	public UUID faction;
	public UUID currentNode;
	public double money;
	public int kills;
	public int deaths;
	public long lastOnline;
	public long timeOnline;
	public boolean autoclaim;
	public boolean unautoclaim;
	public UUID chatChannel;
	
	public NPlayer( Player player )
	{
		ID = player.getUniqueId();
		name = player.getName();
		lastOnline = System.currentTimeMillis();
		title = "";
		faction = null;
		currentNode = null;
		chatChannel = null;
		autoclaim = false;
		unautoclaim = false;
		money = 0;
		kills = 0;
		deaths = 0;
		timeOnline = 0;
	}
	
	public NPlayer( JSONObject json )
	{
		ID = UUID.fromString(json.getString("ID"));
		faction = UUID.fromString(json.getString("faction"));
		name = json.getString("name");
		title = json.getString("title");
		money = json.getDouble("money");
		kills = json.getInt("kills");
		deaths = json.getInt("deaths");
		lastOnline = json.getLong("lastOnline");
		timeOnline = json.getLong("timeOnline");
		autoclaim = json.getBoolean("autoclaim");
		unautoclaim = json.getBoolean("unautoclaim");
		chatChannel = UUID.fromString(json.getString("chatChannel"));
		currentNode = null;
	}

	public JSONObject toJson()
	{
		JSONObject json = new JSONObject();
		json.put("ID",ID);
		json.put("name",name);
		json.put("title",title);
		json.put("faction",faction);
		json.put("money",money);
		json.put("kills",kills);
		json.put("deaths",deaths);
		json.put("lastOnline",lastOnline);
		json.put("timeOnline",timeOnline);
		json.put("autoclaim",autoclaim);
		json.put("unautoclaim",unautoclaim);
		json.put("chatChannel",chatChannel.toString());
		return json;
	}
}
