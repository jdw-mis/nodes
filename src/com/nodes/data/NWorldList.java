package com.nodes.data;

import java.util.HashMap;
import java.util.UUID;

public class NWorldList
{
	private static HashMap<UUID,NWorld> worldList = new HashMap<UUID,NWorld>();
	
	public static void add( NWorld world )
	{
		worldList.put(world.ID,world);
	}
	
	public static void delete( UUID ID )
	{
		worldList.remove(ID);
	}

	public static boolean contains( UUID ID )
	{
		return worldList.containsKey(ID);
	}

	public static NWorld get( UUID ID )
	{
		return worldList.get(ID);
	}
}
