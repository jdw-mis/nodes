package com.nodes.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class NResource
{
	public UUID ID;
	public String name;
	public int cycleActual;
	public int cycleTimeMinutes;
	public HashSet<UUID> nodeSet;
	public HashSet<NResourceID> resourceSet;

	NResource()
	{
		ID = UUID.randomUUID();
		name = ID.toString();
		cycleActual = 0;
		cycleTimeMinutes = 0;
		nodeSet = new HashSet<UUID>();
		resourceSet = new HashSet<NResourceID>();
	}

	public List<UUID> nodes()
	{
		List<UUID> sortNode = new ArrayList<UUID>(nodeSet);
		Collections.sort(sortNode,NNode.nodeNameComp);
		return sortNode;
	}
}
