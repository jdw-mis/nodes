package com.nodes.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
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
		if(relation != null)
		{
			relationMap.put(relation.ID,relation);
			modifySet.add(relation.ID);
		}
	}

	public void remove( UUID ID )
	{
		relationMap.remove(ID);
		modifySet.add(ID);
	}

	public boolean contains( UUID ID )
	{
		return relationMap.containsKey(ID);
	}

	public NRelation get( UUID ID )
	{
		NRelation relate = relationMap.get(ID);
		if(relate != null && !relate.undef)
			return relate;
		return null;
	}

	public NRelation getAbsolute( UUID ID )
	{
		return relationMap.get(ID);
	}

	public HashSet<UUID> modifySet()
	{
		return modifySet;
	}
	public void modifyClear()
	{
		modifySet.clear();
	}

	public Set<UUID> idSet()
	{
		return relationMap.keySet();
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
