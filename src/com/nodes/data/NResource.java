package com.nodes.data;


import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Material;

public class NResource
{
	public UUID ID;
	public int cycleTimeMinutes;
	public int cycleActual;
	public HashMap<Material,Integer> resourceMap;
	public HashSet<UUID> nodeSet;
}
