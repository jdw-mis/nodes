package com.nodes.data;

import java.util.Collection;
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
		NRelation relate = relationMap.get(ID);
		NFaction faction = relate.getJunior();
		faction.deleteRelation(relate.seniorID);
		NFactionList.add(faction);
		faction = relationMap.get(ID).getSenior();
		faction.deleteRelation(relate.juniorID);
		NFactionList.add(faction);

		relationMap.remove(ID);
		modifySet.remove(ID);
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

	public static void flush()
	{
		relationMap.clear();
	}

	public static Collection<NRelation> relateSet()
	{
		return relationMap.values();
	}
}
