package com.nodes.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

public class NRelationList
{
	public static NRelationList i = new NRelationList();
	
	private HashMap<UUID,NRelation> relationMap;
	private HashSet<UUID> modifySet;

	private NRelationList()
	{
		relationMap = new HashMap<UUID,NRelation>();
		modifySet = new HashSet<UUID>();
	}
	
	public void add( NRelation relation )
	{
		relationMap.put(relation.ID,relation);
		modifySet.add(relation.ID);
	}

	public void delete( UUID ID )
	{
		NRelation relate = relationMap.get(ID);
		NFaction faction = relate.getJunior();
		faction.deleteRelation(relate.seniorID);
		NFactionList.i.add(faction);
		faction = relationMap.get(ID).getSenior();
		faction.deleteRelation(relate.juniorID);
		NFactionList.i.add(faction);

		relationMap.remove(ID);
		modifySet.remove(ID);
	}

	public boolean contains( UUID ID )
	{
		return relationMap.containsKey(ID);
	}

	public NRelation get( UUID ID )
	{
		return relationMap.get(ID);
	}

	public Iterator<UUID> saveIter()
	{
		return modifySet.iterator();
	}
	public void saveClear()
	{
		modifySet.clear();
	}

	public void flush()
	{
		relationMap.clear();
	}

	public Collection<NRelation> relateSet()
	{
		return relationMap.values();
	}
}
