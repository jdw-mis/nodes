package com.mis.nodes.data;

import java.util.UUID;

public class NPlayer
{
	private final UUID id;	//same as mineman UUID
	private NFaction faction;
	
	NPlayer(UUID i)
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
