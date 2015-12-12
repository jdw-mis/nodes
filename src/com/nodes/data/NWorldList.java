package com.nodes.data;

import java.util.HashMap;
import java.util.UUID;

public class NWorldList
{
	public static NWorldList i = new NWorldList();
	NWorldList()
	{
		worldMap = new HashMap<UUID,NWorld>();
	}

	public HashMap<UUID,NWorld> worldMap;

	public boolean isInBounds(UUID world,int x,int z)
	{
		NWorld didneyworl = worldMap.get(world);
		if(didneyworl != null)
			return didneyworl.isInBounds(x, z);
		return false;
	}
}
