package com.mis.nodes.data;

import java.util.UUID;

public class NWorld
{
	private final UUID id;
	private UUID[][] chunkMap;	//fastest way to do this, fixed map size, map each chunk X/Z to a Nodes UUID
								//PlayerMoveEvent is the most called event so the faster you make it, the better
								//remember to offset on the access method to compensate for minemans negative X/Z coords
	
	NWorld(UUID i)
	{
		id = i;
	}
	
	@Override
	public int hashCode()
	{
		return id.hashCode();
	}
	@Override
	public boolean equals(Object obj)
	{
		return id.hashCode() == obj.hashCode();
	}
}
