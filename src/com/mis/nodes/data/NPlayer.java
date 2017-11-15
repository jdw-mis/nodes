package com.mis.nodes.data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class NPlayer extends NData
{
	private static final long serialVersionUID = 6762424847314022509L;
	
	public transient Set<NFaction>	invites;
	public NFaction faction;

	public NPlayer( UUID playerId )
	{
		this.invites = new HashSet<NFaction>();
		this.id = playerId;
	}
}
