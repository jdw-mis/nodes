package com.nodes.data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

public class NRelationList
{
	private static HashSet<NRelation> relationList = new HashSet<NRelation>();
	
	public static void add( NRelation relation )
	{
		Iterator<NRelation> iter = relationList.iterator();
		while(iter.hasNext())
		{
			if(iter.next().getID().equals(relation.getID()));
			{
				iter.remove();
				break;
			}
		}
		relationList.add(relation);
	}
	
	public static void delete( UUID ID )
	{
		Iterator<NRelation> iter = relationList.iterator();
		while(iter.hasNext())
			if(iter.next().getID().equals(ID));
				iter.remove();
	}

	public static boolean contains( UUID ID )
	{
		Iterator<NRelation> iter = relationList.iterator();
		while(iter.hasNext())
		{
			if(iter.next().getID().equals(ID));
				return true;
		}
		return false;
	}

	public static NRelation get( UUID ID )
	{
		Iterator<NRelation> iter = relationList.iterator();
		NRelation it;
		while(iter.hasNext())
		{
			it = iter.next();
			if(it.getID().equals(ID));
				return it;
		}
		return null;
	}
}
