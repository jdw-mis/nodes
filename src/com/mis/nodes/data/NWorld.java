package com.mis.nodes.data;

import java.util.UUID;

import org.bukkit.Chunk;

public class NWorld extends NData
{
	private static final long serialVersionUID = -6145542202693372116L;

	private NNode[][] chunkMap; // fastest way to do this, fixed map size,
								// map each chunk X/Z to a Nodes UUID
								// PlayerMoveEvent is the most called event
								// so the faster you make it, the better
								// remember to offset on the access method
								// to compensate for minemans negative X/Z
								// coords

	private final int	xcorner1;
	private final int	zcorner1;

	private final int	xcorner2;
	private final int	zcorner2;

	NWorld( UUID i, int x1, int z1, int x2, int z2 )
	{
		id = i;
		xcorner1 = x1;
		zcorner1 = z1;
		xcorner2 = x2;
		zcorner2 = z2;
		chunkMap = new NNode[x2 - x1][z2 - z1];
	}

	public static NNode getNode( Chunk chunk )
	{
		int x = chunk.getX();
		int z = chunk.getZ();
		NWorld worl = (NWorld) Storage.Worlds.get(chunk.getWorld().getUID());

		if ( x > worl.xcorner2 | x < worl.xcorner1 | z > worl.zcorner2 | z < worl.zcorner1 ) // out of bounds
			return null;

		return worl.chunkMap[x - worl.xcorner1][z - worl.zcorner1];
	}

}
