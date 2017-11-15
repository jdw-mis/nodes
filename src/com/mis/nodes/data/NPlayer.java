package com.mis.nodes.data;

import java.util.UUID;

public class NPlayer extends NData
{
	private static final long serialVersionUID = 6762424847314022509L;

	public NFaction faction;

	public NPlayer( UUID playerId )
	{
		this.id = playerId;
	}

	@Override
	public String toString()
	{
		return "[Player] " + id.toString();
	}
}
