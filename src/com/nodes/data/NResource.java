package com.nodes.data;


import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Material;

public class NResource
{
	public UUID ID;
	public String name;
	public int cycleActual;
	public int cycleTimeMinutes;
	public HashSet<UUID> nodeSet;
	public HashMap<Material,Integer> materialMap;
}
