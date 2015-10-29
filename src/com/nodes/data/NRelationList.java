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
		relationList.put(relation.getID(),relation);
		modifyList.add(relation.getID());
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
	
	public static Iterator<UUID> saveIter( UUID ID )
	{
		return modifyList.iterator();
	}
	
	public static void saveClear()
	{
		modifyList.clear();
	}
}
