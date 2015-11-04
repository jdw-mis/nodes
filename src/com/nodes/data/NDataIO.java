package com.nodes.data;

import java.io.File;
import java.util.Iterator;
import java.util.UUID;

import org.json.JSONArray;

public class NDataIO
{
	public static File folder;
	
	public static void checkDir()
	{
    	File dir = new File(folder + "/");
    	if(!dir.exists())
    		dir.mkdir();
    	dir = new File(folder + "/faction");
    	if(!dir.exists())
    		dir.mkdir();
    	dir = new File(folder + "/player");
    	if(!dir.exists())
    		dir.mkdir();
    	dir = new File(folder + "/node");
    	if(!dir.exists())
    		dir.mkdir();
    	dir = new File(folder + "/relation");
    	if(!dir.exists())
    		dir.mkdir();
    	dir = new File(folder + "/chunk");
    	if(!dir.exists())
    		dir.mkdir();
    	dir = new File(folder + "/world");
    	if(!dir.exists())
    		dir.mkdir();
    	dir = new File(folder + "/node");
    	if(!dir.exists())
    		dir.mkdir();
	}
	
	public static void save()
	{
		Iterator<UUID> player = NPlayerList.saveIter();
		Iterator<UUID> faction = NFactionList.saveIter();
		Iterator<UUID> node = NNodeList.saveIter();
		Iterator<UUID> relation = NRelationList.saveIter();
		
		NPlayer tempPlayer;
		NFaction tempFaction;
		NNode tempNode;
		NRelation tempRelate;
		
		checkDir();
		
		while(player.hasNext())
		{
			tempPlayer = NPlayerList.get(player.next());
		}
		while(faction.hasNext())
		{
			tempFaction = NFactionList.get(faction.next());
		}
		while(node.hasNext())
		{
			tempNode = NNodeList.get(node.next());
		}
		while(relation.hasNext())
		{
			tempRelate = NRelationList.get(relation.next());
		}
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