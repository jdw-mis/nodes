package com.nodes.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class NResourceList
{
	private static HashMap<UUID,NResource> resourceMap = new HashMap<UUID,NResource>();
	private static HashMap<Integer,HashSet<UUID>> resourceTime = new HashMap<Integer,HashSet<UUID>>();
	private static HashMap<String,UUID> resourceNameMap = new HashMap<String,UUID>();
	private static HashSet<UUID> modifySet = new HashSet<UUID>();
	public static long firstActiveMillis;
	public static int cycleBase;

	public static void add( NResource resource )
	{
		resourceMap.put(resource.ID,resource);
		resourceNameMap.put(resource.name.toLowerCase(),resource.ID);
		modifySet.add(resource.ID);
	}

	public static void delete( UUID ID )
	{
		resourceNameMap.remove(resourceMap.get(ID).name);
		resourceMap.remove(ID);
		modifySet.remove(ID);
	}

	public static boolean contains( UUID ID )
	{
		return resourceMap.containsKey(ID);
	}

	public static NResource get( UUID ID )
	{
		return resourceMap.get(ID);
	}

	public static void delete( String name )
	{
		delete(resourceNameMap.get(name.toLowerCase()));
	}

	public static boolean contains( String name )
	{
		return resourceNameMap.containsKey(name.toLowerCase());
	}

	public static NResource get( String name )
	{
		return get(resourceNameMap.get(name.toLowerCase()));
	}

	public static Integer[] getTimeKeySet()
	{
		return resourceTime.keySet().toArray(new Integer[resourceTime.size()]);
	}

	public static UUID[] getTimeSet( Integer cycle )
	{
		return resourceTime.get(cycle).toArray(new UUID[resourceTime.get(cycle).size()]);
	}

	public static void cycleActual()
	{
		resourceTime.clear();
		HashSet<Integer> cycleMinutes=new HashSet<Integer>();
		for(NResource resource:resourceMap.values())
			cycleMinutes.add(resource.cycleTimeMinutes);
		Integer[] cycleArr=cycleMinutes.toArray(new Integer[cycleMinutes.size()]);
		cycleBase=cycleArr[0];
		for(int i=1;i<cycleArr.length;i++)
			cycleBase=cycle(cycleBase,cycleArr[i]);
		for(Integer i:cycleMinutes)
			resourceTime.put(i/cycleBase,new HashSet<UUID>());
		for(NResource resource:resourceMap.values()){
			resource.cycleActual = resource.cycleTimeMinutes/cycleBase;
			add(resource);
			resourceTime.get(resource.cycleActual).add(resource.ID);}
	}
	private static Integer cycle(Integer x,Integer y){return y==0?x:cycle(y,x%y);}
}
