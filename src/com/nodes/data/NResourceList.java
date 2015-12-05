package com.nodes.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class NResourceList
{
	public static NResourceList i = new NResourceList();

	private HashMap<UUID,NResource> resourceMap;
	private HashMap<Integer,HashSet<UUID>> resourceTime;
	private HashMap<String,UUID> resourceNameMap;
	public int cycleBase;
	
	private NResourceList()
	{
		resourceMap = new HashMap<UUID,NResource>();
		resourceTime = new HashMap<Integer,HashSet<UUID>>();
		resourceNameMap = new HashMap<String,UUID>();
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
	private Integer cycle(Integer x,Integer y){return y==0?x:cycle(y,x%y);}
}
