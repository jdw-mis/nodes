package com.nodes.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.UUID;

import org.json.JSONObject;

public class NDataIO
{
	public static File folder;
	
	public static void checkDir()
	{
    	File dir = new File(folder + "/Data/");
    	if(!dir.exists())
    		dir.mkdir();
	}
	
    private static void jsonToDisk(JSONObject json)
    {
		try
		{
			FileWriter fw = new FileWriter(folder + "/Data/" + json.getString("ID") + ".json");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(json.toString(4));
			bw.close();
			fw.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
	
	public static void save()
	{
		checkDir();

		Iterator<UUID> iter = NPlayerList.saveIter();
		while(iter.hasNext())
			jsonToDisk(NPlayerList.get(iter.next()).toJson());
		
		iter = NFactionList.saveIter();
		while(iter.hasNext())
			jsonToDisk(NFactionList.get(iter.next()).toJson());
		
		iter = NNodeList.saveIter();
		while(iter.hasNext())
			jsonToDisk(NNodeList.get(iter.next()).toJson());
		
		iter = NRelationList.saveIter();
		while(iter.hasNext())
			jsonToDisk(NRelationList.get(iter.next()).toJson());
	}
	
	public static void saveAll()
	{
		Iterator<NPlayer> player = NPlayerList.saveAllIter();
		Iterator<NFaction> faction = NFactionList.saveAllIter();
		Iterator<NNode> node = NNodeList.saveAllIter();
		Iterator<NRelation> relation = NRelationList.saveAllIter();
		Iterator<NChunk> chunk = NChunkList.saveAllIter();
		Iterator<NWorld> world = NWorldList.saveAllIter();
		
		checkDir();
		
		while(player.hasNext())
			jsonToDisk(player.next().toJson());
		while(faction.hasNext())
			jsonToDisk(faction.next().toJson());
		while(node.hasNext())
			jsonToDisk(node.next().toJson());
		while(relation.hasNext())
			jsonToDisk(relation.next().toJson());
		while(chunk.hasNext())
			jsonToDisk(chunk.next().toJson());
		while(world.hasNext())
			jsonToDisk(world.next().toJson());
	}
	
	public static void load()
	{
		NPlayer player = null;
		NFaction faction = null;
		NNode node = null;
		NRelation relate = null;
		NChunk chunk = null;
		NWorld world = null;

		checkDir();
		
		while(true)//need condition from json lib
		{
			//tempPlayer = json read
			NPlayerList.add(player);
			break;
		}
		while(true)
		{
			NFactionList.add(faction);
			break;
		}
		while(true)
		{
			NRelationList.add(relate);
			break;
		}
		while(true)
		{
			NWorldList.add(world);
			break;
		}
		while(true)
		{
			NNodeList.add(node);
			break;
		}
		while(true)
		{
			NChunkList.add(chunk);
			break;
		}
	}
}