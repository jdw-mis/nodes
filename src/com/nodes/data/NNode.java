package com.nodes.data;

import java.util.ArrayList;
import java.util.UUID;

public class NNode
{
	private UUID ID;
	private UUID faction;
	private UUID world;
	private UUID coreChunk;
	private ArrayList<UUID> borderChunkList;
	

	public NNode()
	{
		ID = UUID.randomUUID();
	}
	
	//Get Block
    public UUID		getID()				{ return ID; }
    public UUID		getOwner()			{ return faction; }
    public UUID		getWorld()			{ return world; }
    public UUID		getCore()			{ return coreChunk; }
}
