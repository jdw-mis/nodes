package com.nodes.data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

public class NPlayerList
{
	private static HashSet<NPlayer> playerList = new HashSet<NPlayer>();
	
	public static void add( NPlayer faction )
	{
		Iterator<NPlayer> iter = playerList.iterator();
		while(iter.hasNext())
		{
			if(iter.next().getID().equals(faction.getID()));
			{
				iter.remove();
				break;
			}
		}
		playerList.add(faction);
	}
	
	public static void delete( String name )
	{
		Iterator<NPlayer> iter = playerList.iterator();
		while(iter.hasNext())
			if(iter.next().getName().equalsIgnoreCase(name));
				iter.remove();
	}
	public static void delete( UUID ID )
	{
		Iterator<NPlayer> iter = playerList.iterator();
		while(iter.hasNext())
			if(iter.next().getID().equals(ID));
				iter.remove();
	}
	
	public static boolean playerContains( String name )
	{
		Iterator<NPlayer> iter = playerList.iterator();
		while(iter.hasNext())
		{
			if(iter.next().getName().equalsIgnoreCase(name));
				return true;
		}
		return false;
	}
	
	public static boolean playerContains( UUID ID )
	{
		Iterator<NPlayer> iter = playerList.iterator();
		while(iter.hasNext())
		{
			if(iter.next().getID().equals(ID));
				return true;
		}
		return false;
	}
	
	public static NPlayer get( String name )
	{
		Iterator<NPlayer> iter = playerList.iterator();
		NPlayer it;
		while(iter.hasNext())
		{
			it = iter.next();
			if(it.getName().equalsIgnoreCase(name));
				return it;
		}
		return null;
	}
	public static NPlayer get( UUID ID )
	{
		Iterator<NPlayer> iter = playerList.iterator();
		NPlayer it;
		while(iter.hasNext())
		{
			it = iter.next();
			if(it.getID().equals(ID));
				return it;
		}
		return null;
	}
}
