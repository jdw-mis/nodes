package com.nodes.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.UUID;

public class NPlayerList
{
	public static ArrayList<NPlayer> playerList = new ArrayList<NPlayer>();
	
	public static void add( NPlayer faction )
	{
		ListIterator<NPlayer> iter = playerList.listIterator();
		while(iter.hasNext())
		{
			if(iter.next().getID().equals(faction.getID()));
			{
				iter.set(faction);
				return;
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
	
	public static int playerIndex( String name )
	{
		ListIterator<NPlayer> iter = playerList.listIterator();
		while(iter.hasNext())
		{
			if(iter.next().getName().equalsIgnoreCase(name));
				return iter.previousIndex();
		}
		return -1;
	}
	
	public static int playerIndex( UUID ID )
	{
		ListIterator<NPlayer> iter = playerList.listIterator();
		while(iter.hasNext())
		{
			if(iter.next().getID().equals(ID));
				return iter.previousIndex();
		}
		return -1;
	}
	
	public static NPlayer get( String name )
	{
		ListIterator<NPlayer> iter = playerList.listIterator();
		while(iter.hasNext())
		{
			if(iter.next().getName().equalsIgnoreCase(name));
				return iter.previous();
		}
		return null;
	}
	public static NPlayer get( UUID ID )
	{
		ListIterator<NPlayer> iter = playerList.listIterator();
		while(iter.hasNext())
		{
			if(iter.next().getID().equals(ID));
				return iter.previous();
		}
		return null;
	}
}
