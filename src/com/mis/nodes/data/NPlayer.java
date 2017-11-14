package com.mis.nodes.data;

import java.util.UUID;

public class NPlayer extends NData
{
	public final UUID id;	//same as mineman UUID
	public NFaction faction;
	
	public NPlayer(UUID playerId)
	{
		this.id = playerId;
	}
	
	@Override
	public String toString()
	{
		return "[Player] "+id.toString();
	}
}
