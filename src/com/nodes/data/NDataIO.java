package com.nodes.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.JSONObject;

public class NDataIO
{
	public static File folder;
	
	public static void checkDir()
	{
    	File dir = new File(folder + "/data/");
    	if(!dir.exists())
    		dir.mkdir();
    	dir = new File(folder + "/data/player");
    	if(!dir.exists())
    		dir.mkdir();
    	dir = new File(folder + "/data/faction");
    	if(!dir.exists())
    		dir.mkdir();
    	dir = new File(folder + "/data/node");
    	if(!dir.exists())
    		dir.mkdir();
    	dir = new File(folder + "/data/relation");
    	if(!dir.exists())
    		dir.mkdir();
    	dir = new File(folder + "/data/chunk");
    	if(!dir.exists())
    		dir.mkdir();
    	dir = new File(folder + "/data/world");
    	if(!dir.exists())
    		dir.mkdir();
	}
	
    private static void jsonToDisk(JSONObject json, String dirAdd)
    {
		try
		{
			FileWriter fw = new FileWriter(folder + "/data/" + dirAdd + "/" + json.getString("ID") + ".json");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(json.toString(4));
			bw.flush();
			fw.flush();
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
			jsonToDisk(NPlayerList.get(iter.next()).toJson(),"player");
		
		iter = NFactionList.saveIter();
		while(iter.hasNext())
			jsonToDisk(NFactionList.get(iter.next()).toJson(),"faction");
		
		iter = NNodeList.saveIter();
		while(iter.hasNext())
			jsonToDisk(NNodeList.get(iter.next()).toJson(),"node");
		
		iter = NRelationList.saveIter();
		while(iter.hasNext())
			jsonToDisk(NRelationList.get(iter.next()).toJson(),"relation");
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
			jsonToDisk(player.next().toJson(),"player");
		while(faction.hasNext())
			jsonToDisk(faction.next().toJson(),"faction");
		while(node.hasNext())
			jsonToDisk(node.next().toJson(),"node");
		while(relation.hasNext())
			jsonToDisk(relation.next().toJson(),"relation");
		while(chunk.hasNext())
			jsonToDisk(chunk.next().toJson(),"chunk");
		while(world.hasNext())
			jsonToDisk(world.next().toJson(),"world");
	}
	
	public static void load()
	{
		JSONObject[] jsonList;

		checkDir();
		
		jsonList = diskToJson("player");
		for(JSONObject json : jsonList)
			NPlayerList.add(new NPlayer(json));
		
		jsonList = diskToJson("faction");
		for(JSONObject json : jsonList)
			NFactionList.add(new NFaction(json));
		
		jsonList = diskToJson("node");
		for(JSONObject json : jsonList)
			NNodeList.add(new NNode(json));
		
		jsonList = diskToJson("relation");
		for(JSONObject json : jsonList)
			NRelationList.add(new NRelation(json));
		
		jsonList = diskToJson("chunk");
		for(JSONObject json : jsonList)
			NChunkList.add(new NChunk(json));
		
		jsonList = diskToJson("world");
		for(JSONObject json : jsonList)
			NWorldList.add(new NWorld(json));
	}
	
	private static JSONObject[] diskToJson(String dirAdd)
	{
		File[] fileList = new File(folder+"/data/"+dirAdd+"/").listFiles();
		JSONObject[] json = new JSONObject[fileList.length];
		FileReader fr;
		BufferedReader br;
		JSONParser jsonParse = new JSONParser();
		
		for(int i = fileList.length; i >= 0; --i)
		{
			try
			{
				fr = new FileReader(fileList[i]);
				br = new BufferedReader(fr);
				json[i] = (JSONObject)jsonParse.parse(br);
				br.close();
				fr.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}
		}
		return json;
	}
}