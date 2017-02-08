package com.nodes.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;

/*
 * collection class for resources
 */
public class NResourceList
{
	public static NResourceList i = new NResourceList();

	private HashMap<UUID,NResource> resourceMap;
	private HashMap<Integer,HashSet<UUID>> resourceTime;
	private HashMap<String,UUID> resourceNameMap;
	public int cycleBase;

	NResourceList()
	{
		cycleBase = 0;
		resourceMap = new HashMap<UUID,NResource>();
		resourceTime = new HashMap<Integer,HashSet<UUID>>();
		resourceNameMap = new HashMap<String,UUID>();

		//test resources
		NResource resource = new NResource();
		resource.name = "Mining";
		resource.cycleTimeMinutes = 1;
		resource.resourceSet.add(new NResourceID(Material.IRON_ORE, 64));
		resource.resourceSet.add(new NResourceID(Material.GOLD_ORE, 16));
		resource.resourceSet.add(new NResourceID(Material.REDSTONE_ORE, 8));
		resource.resourceSet.add(new NResourceID(Material.LAPIS_ORE, 8));
		resource.resourceSet.add(new NResourceID(Material.EMERALD_ORE, 1));
		resource.resourceSet.add(new NResourceID(Material.DIAMOND_ORE, 1));
		add(resource);
		resource = new NResource();
		resource.name = "Lumber";
		resource.cycleTimeMinutes = 1;
		resource.resourceSet.add(new NResourceID(Material.LOG, 32, 0));
		resource.resourceSet.add(new NResourceID(Material.LOG, 8, 1));
		resource.resourceSet.add(new NResourceID(Material.LOG, 8, 2));
		resource.resourceSet.add(new NResourceID(Material.LOG, 8, 3));
		resource.resourceSet.add(new NResourceID(Material.LOG_2, 8, 0));
		resource.resourceSet.add(new NResourceID(Material.LOG_2, 8, 1));
		resource.resourceSet.add(new NResourceID(Material.LOG_2, 8, 2));
		resource.resourceSet.add(new NResourceID(Material.LOG_2, 8, 3));
		add(resource);
		resource = new NResource();
		resource.name = "Quarry";
		resource.cycleTimeMinutes = 1;
		resource.resourceSet.add(new NResourceID(Material.STONE, 256,0));
		resource.resourceSet.add(new NResourceID(Material.STONE, 64,1));
		resource.resourceSet.add(new NResourceID(Material.STONE, 64,3));
		resource.resourceSet.add(new NResourceID(Material.STONE, 64,5));
		resource.resourceSet.add(new NResourceID(Material.COBBLESTONE, 128));
		resource.resourceSet.add(new NResourceID(Material.MOSSY_COBBLESTONE, 64));
		add(resource);
	}

	public void add( NResource resource )
	{
		if(resource != null)
		{
			resourceMap.put(resource.ID,resource);
			resourceNameMap.put(resource.name.toLowerCase(),resource.ID);
		}
	}

	public void remove( UUID ID )
	{
		resourceTime.get(resourceMap.get(ID).cycleActual).remove(ID);
		resourceNameMap.remove(resourceMap.get(ID).name);
		resourceMap.remove(ID);
	}

	public boolean contains( UUID ID )
	{
		return resourceMap.containsKey(ID);
	}

	public NResource get( UUID ID )
	{
		return resourceMap.get(ID);
	}

	public boolean contains( String name )
	{
		return resourceNameMap.containsKey(name.toLowerCase());
	}

	public Collection<NResource> resourceSet()
	{
		return resourceMap.values();
	}

	public Set<UUID> idSet()
	{
		return resourceMap.keySet();
	}

	public NResource get( String name )
	{
		return get(resourceNameMap.get(name.toLowerCase()));
	}

	public Integer[] getTimeKeySet()
	{
		return resourceTime.keySet().toArray(new Integer[resourceTime.size()]);
	}

	public UUID[] getTimeSet( Integer cycle )
	{
		return resourceTime.get(cycle).toArray(new UUID[resourceTime.get(cycle).size()]);
	}

	/*
	 * the idea behind cycleactual is to get the greatest common denominator of all the resources cycle times
	 * ie if you have two resources, X spawns every 10 minutes, Y every 5 minutes, then the GCD is 5
	 * thus the cycleactual of X would be 2, and Y would be 1
	 * then the server would only run the resource spawn event every 5 minutes, to cut down on processing time
	 * 
	 * runs at startup
	 */
	public void cycleActual()
	{
		resourceTime.clear();
		HashSet<Integer> cycleMinutes=new HashSet<Integer>();
		for(NResource resource:resourceMap.values())
			cycleMinutes.add(resource.cycleTimeMinutes);
		Integer[] cycleArr=cycleMinutes.toArray(new Integer[cycleMinutes.size()]);
		if(cycleArr.length > 0)
		{
			cycleBase=cycleArr[0];
			for(int i=1;i<cycleArr.length;i++)
				cycleBase=cycle(cycleBase,cycleArr[i]);
			for(Integer i:cycleMinutes)
				resourceTime.put(i/cycleBase,new HashSet<UUID>());
			for(NResource resource:resourceMap.values())
			{
				resource.cycleActual = resource.cycleTimeMinutes/cycleBase;
				add(resource);
				resourceTime.get(resource.cycleActual).add(resource.ID);
			}
		}
	}
	private Integer cycle(Integer x,Integer y){return y==0?x:cycle(y,x%y);} //recursive functions make me happy
}
