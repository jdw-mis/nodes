package com.nodes.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

public class NRelationList
{
	private static HashMap<UUID,NRelation> relationList = new HashMap<UUID,NRelation>();
	private static HashSet<UUID> modifyList = new HashSet<UUID>();
	
	public static void add( NRelation relation )
	{
		relationList.put(relation.ID,relation);
		modifyList.add(relation.ID);
	}
	
	public static void delete( UUID ID )
	{
		relationList.remove(ID);
	}

	public static boolean contains( UUID ID )
	{
		return relationList.containsKey(ID);
	}

	public static NRelation get( UUID ID )
	{
		return relationList.get(ID);
	}
	
	public static Iterator<UUID> saveIter()
	{
		return modifyList.iterator();
	}
	public static void saveClear()
	{
		modifyList.clear();
	}
	
	public static Iterator<NRelation> saveAllIter()
	{
		return relationList.values().iterator();
	}
	public static void flush()
	{
		relationList.clear();
	}
}
