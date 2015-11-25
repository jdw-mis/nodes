package com.nodes.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

public class NRelationList
{
	private static HashMap<UUID,NRelation> relationMap = new HashMap<UUID,NRelation>();
	private static HashSet<UUID> modifySet = new HashSet<UUID>();
	
	public static void add( NRelation relation )
	{
		relationMap.put(relation.ID,relation);
		modifySet.add(relation.ID);
	}
	
	public static void delete( UUID ID )
	{
		relationMap.remove(ID);
	}

	public static boolean contains( UUID ID )
	{
		return relationMap.containsKey(ID);
	}

	public static NRelation get( UUID ID )
	{
		return relationMap.get(ID);
	}
	
	public static Iterator<UUID> saveIter()
	{
		return modifySet.iterator();
	}
	public static void saveClear()
	{
		modifySet.clear();
	}
	
	public static Iterator<NRelation> saveAllIter()
	{
		return relationMap.values().iterator();
	}
	public static void flush()
	{
		relationMap.clear();
	}
}
