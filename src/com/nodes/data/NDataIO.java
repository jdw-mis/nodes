package com.nodes.data;

import java.util.Iterator;
import java.util.UUID;

public class NDataIO
{
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
