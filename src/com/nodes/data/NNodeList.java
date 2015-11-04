package com.nodes.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

public class NNodeList
{
	private static HashMap<UUID,NNode> nodeList = new HashMap<UUID,NNode>();
	private static HashSet<UUID> modifyList = new HashSet<UUID>();
	
	public static void add( NNode node )
	{
		nodeList.put(node.ID,node);
		modifyList.add(node.ID);
	}
	
	public static void delete( UUID ID )
	{
		nodeList.remove(ID);
	}

	public static boolean contains( UUID ID )
	{
		return nodeList.containsKey(ID);
	}

	public static NNode get( UUID ID )
	{
		return nodeList.get(ID);
	}
	
	public static Iterator<UUID> saveIter()
	{
		return modifyList.iterator();
	}
	
	public static void saveClear()
	{
		modifyList.clear();
	}
}
