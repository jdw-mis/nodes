package com.nodes.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class NWorldList
{
	public static NWorldList i = new NWorldList();
	NWorldList()
	{
		worldMap = new HashMap<UUID,NWorld>();
	}

	public HashMap<UUID,NWorld> worldMap;

	public boolean isInBounds(UUID world,int x,int z)
	{
		NWorld didneyworl = worldMap.get(world);
		if(didneyworl != null)
			return didneyworl.isInBounds(x, z);
		return false;
	}

	public void boilWorlds()
	{
		HashSet<NWorld> worldArr = new HashSet<NWorld>(worldMap.values());
		int[][] coordWrap = new int[8][2];
		UUID nodeID;
		int i,x,z;
		boolean cont;
		for(NWorld world : worldArr)
		{
			cont = true;
			while(cont == true)
			{
				cont = false;
				for(x = 0; x < world.mapHeight; x++ )
				{
					for(z = 0; z < world.mapWidth; z++ )
					{
						nodeID = world.getID(x, z);
						if(nodeID == null)
						{
							for(i = 0; i < 8; i++ )
							{
								coordWrap[i][0] = x;
								coordWrap[i][1] = z;
							}
							coordWrap[0][0]--;
							coordWrap[1][0]++;
							coordWrap[2][1]--;
							coordWrap[3][1]++;
							coordWrap[4][0]--;
							coordWrap[4][1]--;
							coordWrap[5][0]--;
							coordWrap[5][1]++;
							coordWrap[6][0]++;
							coordWrap[6][1]--;
							coordWrap[7][0]++;
							coordWrap[7][1]++;
							for(i = 0; i < 8; i++ )
							{
								if(!(coordWrap[i][0] < 0 || coordWrap[i][1] < 0 || coordWrap[i][0] >= world.mapHeight || coordWrap[i][0] >= world.mapWidth))
								{
									nodeID = world.getID(coordWrap[i][0], coordWrap[i][1]);
									if(nodeID != null)
									{
										world.putID(x, z, nodeID);
										cont = true;
									}
								}
							}
						}
					}
				}
			}
			worldMap.put(world.ID, world);
		}
	}
}
