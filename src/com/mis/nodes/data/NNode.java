package com.mis.nodes.data;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Chunk;

public class NNode extends NData
{
	private static final long serialVersionUID = 6295677511898695531L;

	public NFaction			faction;
	public NWorld			world;
	public HashSet<Chunk>	zone;
	public Chunk			node;

	NNode( UUID i )
	{
		id = i;
	}

	public int getProtection()
	{
		if ( faction == null )
			return -1;
		return faction.territory.get( this );
	}
}
