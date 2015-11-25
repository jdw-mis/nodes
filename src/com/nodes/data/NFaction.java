package com.nodes.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;

public class NFaction
{
	public UUID ID;
	public boolean peaceful;
	public boolean warzone;
	public boolean safezone;
	public boolean open;
	public double money;
	public long lastOnline;
	public Location home;
	public String name;
	public String description;
	private HashSet<UUID> invites;
	private HashMap<UUID,UUID> relations;	//first is target faction, second is relation UUID
	private HashMap<UUID,UUID> players;	//first is playerID, second is rank
	private HashMap<UUID,Integer> nodes;		//first is nodeID, second is "embeddedness"
	private HashMap<UUID,NRank> customRanks;	//they can define their own rank names and such
	private LinkedList<UUID> customRankOrder;
	
	
	public NFaction( String faction, UUID player )
	{
		name = faction;
		ID = UUID.randomUUID();
		lastOnline = System.currentTimeMillis();
		invites = new HashSet<UUID>();
		relations = new HashMap<UUID,UUID>();
		players = new HashMap<UUID,UUID>();
		nodes = new HashMap<UUID,Integer>();
		customRanks = new HashMap<UUID,NRank>();
		customRankOrder = new LinkedList<UUID>();
		peaceful = false;
		warzone = false;
		safezone = false;
		money = 0.0;
		//TODO default shit
		players.put(player, customRankOrder.getFirst());
	}
	
	//Get Block
    public NRank	getRank(UUID i)			{ return customRanks.get(players.get(i)); }
    public UUID		getRankID(int i)		{ return customRankOrder.get(i); }
    public int		getRankIndex(UUID i)	{ return customRankOrder.indexOf(players.get(i)); }
    public UUID	    getRelation(UUID i)		{ return relations.get(i); }
    public int		getNodeEmbed(UUID i)	{ return nodes.get(i); }
    public UUID		getHigherRank(UUID i)	{ UUID ID = null; try{ID = customRankOrder.get(customRankOrder.indexOf(players.get(i))+1);}catch(IndexOutOfBoundsException e){} return ID; } //hue hue hue
    public UUID		getLowerRank(UUID i)	{ UUID ID = null; try{ID = customRankOrder.get(customRankOrder.indexOf(players.get(i))-1);}catch(IndexOutOfBoundsException e){} return ID; }
    
    //Check Block
    public boolean	isInvited(UUID i)	{ return invites.contains(i); }
    public boolean	isLastPlayer()		{ return players.size() == 1; }
    public boolean	hasPlayer(UUID i)	{ return players.containsKey(i); }
    public boolean	hasPlayer(NPlayer i){ return players.containsKey(i.ID); }
    public boolean	hasNode(UUID i)		{ return nodes.containsKey(i); }
    public boolean	hasNode(NNode i)	{ return nodes.containsKey(i.ID); }
    
    //Set Block
    public void		addInvite(UUID i)			{ invites.add(i); }
    public void		addPlayer(UUID i)			{ players.put(i, customRankOrder.getFirst()); }
    public void		addPlayer(UUID i, UUID j)	{ players.put(i, j); }
    public void		addRelation(NRelation i)	{ relations.put(i.juniorID,i.ID); }
    public void		addNode(UUID node)			{ nodes.put(node,0); }
    
    //Delete Block
    public void		deleteInvite(UUID i)			{ invites.remove(i); }
    public void		deletePlayer(UUID player)		{ players.remove(player); }
    public void		deleteRelation(UUID faction)	{ relations.remove(faction); }
    public void		deleteNode(UUID node)			{ nodes.remove(node); }
    
    public Iterator<UUID>				getPlayerIter()			{ return players.keySet().iterator(); }
    public Iterator<UUID> 				getRelationIter() 		{ return relations.values().iterator(); }
    public Iterator<UUID> 				getRelateFactionIter() 	{ return relations.keySet().iterator(); }
    public Iterator<UUID> 				getNodeIter()			{ return nodes.keySet().iterator(); }
    public Set<Entry<UUID, UUID>>		getPlayerEntrySet()		{ return players.entrySet(); }
    
    public void boilNodes()
    {
    	boolean continueBoil = true;
    	int change;
    	Integer temp;
    	
    	while(continueBoil)
    	{
    		continueBoil = false;
        	for(UUID boiler : nodes.keySet())
        	{
        		change = 0;
            	for(UUID tempNode : NNodeList.get(boiler).borderNode.keySet())
        		{
        			temp = nodes.get(tempNode);
        			if( temp == null )
        			{
        				change += nodes.get(boiler);
        				break;
        			}
        			else
        			{
        				temp = nodes.get(boiler) - temp;
        				if( temp > 1 )
        				{
        					change = temp-1;
        					break;
        				}
        				else if( temp < -1 )
        				{
        					change = temp+1;
        					break;
        				}
        			}
        		}
        		if(change != 0)
        		{
        			nodes.put(boiler, nodes.get(boiler)-change);
        			continueBoil = true;
        		}
        	}
    	}
    }
    /* some test fuckery
     * 	public static void main(String[] args)
	{
		int[][] arr = {
		{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
		{-1,0,0,0,0,0,0,0,0,-1,0,0,0,0,0,0,0,-1,0,0,-1},
		{-1,0,0,0,0,0,0,0,-1,0,0,0,0,0,0,0,-1,0,0,0,-1},
		{-1,0,0,0,0,0,0,-1,0,0,0,0,0,0,0,-1,0,0,0,0,-1},
		{-1,0,0,0,0,0,-1,0,0,0,0,0,0,0,-1,0,0,0,0,0,-1},
		{-1,0,0,0,0,0,-1,0,0,0,0,0,0,-1,0,0,0,0,0,0,-1},
		{-1,0,0,0,0,0,0,-1,0,0,0,0,0,0,0,0,0,0,0,0,-1},
		{-1,0,0,0,0,0,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,-1},
		{-1,0,0,0,0,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1},
		{-1,0,0,0,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1},
		{-1,0,0,0,0,0,0,0,0,0,-1,0,0,0,0,0,0,0,0,0,-1},
		{-1,0,0,0,0,0,0,0,0,0,0,0,-1,0,0,0,0,0,0,0,-1},
		{-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1},
		{-1,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,0,0,0,0,0,-1},
		{-1,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,0,0,0,0,0,-1},
		{-1,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,0,0,0,0,0,-1},
		{-1,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,0,0,0,0,0,-1},
		{-1,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,0,0,0,0,0,-1},
		{-1,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,0,0,0,0,0,-1},
		{-1,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,0,0,0,0,0,-1},
		{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}
		
		};

		for(int x = 0; x<21; x++)
		{
			for(int y = 0; y<21; y++)
			{
				System.out.print((arr[x][y]+1)+",");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
		
		arr = boilTest(arr);
		
		arr[16][5] = -1;
		arr = boilTest(arr);
	}
	
	public static int[][] boilTest( int[][] arr )
	{
		boolean continueBoil = true;
		int change;
		ArrayList<Integer> temp = new ArrayList();
		int t = 0;
		while(continueBoil)
		{
			t++;
			continueBoil = false;
			for(int x = 1; x<20; x++)
			{
				for(int y = 1; y<20; y++)
				{
					if(arr[x][y] != -1)
					{
						change = 0;
						temp.clear();
						temp.add(arr[x-1][y-1]);
						temp.add(arr[x][y-1]);
						temp.add(arr[x+1][y-1]);
						temp.add(arr[x+1][y]);
						temp.add(arr[x+1][y+1]);
						temp.add(arr[x][y+1]);
						temp.add(arr[x-1][y+1]);
						temp.add(arr[x-1][y]);
						
						boolean exposed = false;
						boolean over = true;
						boolean same = true;
						boolean under = true;
						boolean stasis = false;
						
						int min = 0;
						int max = 0;
						
						//System.out.print(x + ", " + y + " : ");
						
						for(int z : temp)
						{
							if(z == -1)
							{
								exposed = true;
							}
							else
							{
								z -= arr[x][y];  // z is the difference between the surrounding and inner
								//if positive, z is larger than arr
								//if negative, z is smaller than arr
								//System.out.print(z + " : ");
								if( z == -1 )
								{
									stasis = true;
								}
								
								if( !(z == 0 || z == 1) )
								{
									same = false;
								}
								
								if( !(z > 1) )
								{
									under = false;
								}
								
								if( !(z < -1) )
								{
									over = false;
								}
								
								if(min > z)
									min = z;
							}
						}

						//if any diff is -1; do nothing
						//if all are 0 or 1, same; do a ++
						//if all are over 1, do a += min diff
						//if all are under -1, do a -= min diff
						
						if(!stasis)
						{
							if(exposed)
								change -= arr[x][y];
							else if(same)
								change++;
							else if(under)
								change+=min; 
							else if(over)
								change-=min; 
						}
						//System.out.print(exposed+", "+same+", "+under+", "+over);
						
						if(change != 0)
						{
							arr[x][y] += change;
							continueBoil = true;
						}
						//System.out.println("\n");
					}
					else
					{
						//System.out.println("neg");
					}
				}
			}
			for(int x = 0; x<21; x++)
			{
				for(int y = 0; y<21; y++)
				{
					System.out.print((arr[x][y]+1)+",");
				}
				System.out.print("\n");
			}
			System.out.print("\n");
		}
		return arr;
	}
     */
}