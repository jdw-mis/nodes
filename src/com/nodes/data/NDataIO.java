package com.nodes.data;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NDataIO
{
	public static File folder;

	public static void checkDir()
	{
		File dir = new File(folder + "/data");
		if(!dir.exists())
			dir.mkdir();
		dir = new File(folder + "/data/player");
		if(!dir.exists())
			dir.mkdir();
		dir = new File(folder + "/data/faction");
		if(!dir.exists())
			dir.mkdir();
		dir = new File(folder + "/data/node");
		if(!dir.exists())
			dir.mkdir();
		dir = new File(folder + "/data/relation");
		if(!dir.exists())
			dir.mkdir();
		dir = new File(folder + "/data/chunk");
		if(!dir.exists())
			dir.mkdir();
		dir = new File(folder + "/data/world");
		if(!dir.exists())
			dir.mkdir();
	}

	private static void toDisk(String print, String dir)
	{
		FileWriter fw;
		BufferedWriter bw;
		try
		{
			fw = new FileWriter(folder + "/data/" + dir + ".json");
			bw = new BufferedWriter(fw);
			bw.write(print);
			bw.flush();
			fw.flush();
			bw.close();
			fw.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void save()
	{
		checkDir();
	
		NPlayer player;
		NFaction faction;
		NNode node;
		NRelation relation;
	
		Gson json = new GsonBuilder().setPrettyPrinting().create();
		Iterator<UUID> iter = NPlayerList.saveIter();
		while(iter.hasNext())
		{
			player = NPlayerList.get(iter.next());
			toDisk(json.toJson(player),"player/"+player.ID.toString());
		}
		
		iter = NFactionList.saveIter();
		while(iter.hasNext())
		{
			faction = NFactionList.get(iter.next());
			toDisk(json.toJson(faction),"faction/"+faction.ID.toString());
		}
	
		iter = NNodeList.saveIter();
		while(iter.hasNext())
		{
			node = NNodeList.get(iter.next());
			toDisk(json.toJson(node),"node/"+node.ID.toString());
		}
	
		iter = NRelationList.saveIter();
		while(iter.hasNext())
		{
			relation = NRelationList.get(iter.next());
			toDisk(json.toJson(relation),"relation/"+relation.ID.toString());
		}
	}

	public static void saveAll()
	{
		Iterator<NPlayer> player = NPlayerList.saveAllIter();
		Iterator<NFaction> faction = NFactionList.saveAllIter();
		Iterator<NNode> node = NNodeList.saveAllIter();
		Iterator<NRelation> relation = NRelationList.saveAllIter();
		Iterator<NChunk> chunk = NChunkList.saveAllIter();
		Iterator<NWorld> world = NWorldList.saveAllIter();
	
		NPlayer pl;
		NFaction fa;
		NNode no;
		NRelation re;
		NChunk ch;
		NWorld wo;
	
		checkDir();

		Gson json = new GsonBuilder().setPrettyPrinting().create();
		while(player.hasNext())
		{
			pl = player.next();
			toDisk(json.toJson(pl),"player/"+pl.ID.toString());
		}
		while(faction.hasNext())
		{
			fa = faction.next();
			toDisk(json.toJson(fa),"faction/"+fa.ID.toString());
		}
		while(node.hasNext())
		{
			no = node.next();
			toDisk(json.toJson(no),"node/"+no.ID.toString());
		}
		while(relation.hasNext())
		{
			re = relation.next();
			toDisk(json.toJson(re),"relation/"+re.ID.toString());
		}
		while(chunk.hasNext())
		{
			ch = chunk.next();
			toDisk(json.toJson(ch),"chunk/"+ch.ID.toString());
		}
		while(world.hasNext())
		{
			wo = world.next();
			toDisk(json.toJson(wo),"world/"+wo.ID.toString());
		}
	}

	//warning: this drops extant loaded data
	public static void load()
	{
		checkDir();
	
		NPlayerList.flush();
		NFactionList.flush();
		NNodeList.flush();
		NRelationList.flush();
		NChunkList.flush();
		NWorldList.flush();
		NConfig conf = new NConfig();
	
		Gson json = new GsonBuilder().setPrettyPrinting().create();
		toDisk(json.toJson(conf),"defaultConfig");

		File config = new File(folder+"/data/config.json");
		if(config.exists())
		{
			conf = json.fromJson(diskTo(config), NConfig.class);
		}
		else
		{
			toDisk(json.toJson(conf),"config");
		}
	
		File[] fileList = new File(folder+"/data/player/").listFiles();
		for(File file : fileList)
			NPlayerList.add(json.fromJson(diskTo(file),NPlayer.class));
		fileList = new File(folder+"/data/faction/").listFiles();
		for(File file : fileList)
			NFactionList.add(json.fromJson(diskTo(file),NFaction.class));
		fileList = new File(folder+"/data/node/").listFiles();
		for(File file : fileList)
			NNodeList.add(json.fromJson(diskTo(file),NNode.class));
		fileList = new File(folder+"/data/relation/").listFiles();
		for(File file : fileList)
			NRelationList.add(json.fromJson(diskTo(file),NRelation.class));
		fileList = new File(folder+"/data/chunk/").listFiles();
		for(File file : fileList)
			NChunkList.add(json.fromJson(diskTo(file),NChunk.class));
		fileList = new File(folder+"/data/world/").listFiles();
		for(File file : fileList)
			NWorldList.add(json.fromJson(diskTo(file),NWorld.class));
	}

	private static String diskTo(File file)
	{
		FileReader fr;
		BufferedReader br;
		String read = null;
		String temp = null;
		try
		{
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			do
			{
				temp = br.readLine();
				read += temp;
			}
			while(temp != null);
			br.close();
			fr.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return read;
	}
	
	public static void PNGtoNodes()
	{
        HashMap<Integer,NNode> nodeMap = new HashMap<Integer,NNode>();
        List<UUID> skipped = new LinkedList<UUID>();
		BufferedImage image = null;
		File imageFile;
		NNode node;
        byte[] raw;
        byte r,g,b,a = 24;
        boolean hasAC;
        int argb,x,z,halfHeight,halfWidth,j,i = 0,pixelSize = 3;
        
        WorldIter:
        for(World world : Bukkit.getWorlds())
        {
        	nodeMap.clear();
        	imageFile = new File(folder + "nodegraph"+world.getName()+".png");
        	if(!imageFile.exists())
        	{
        		imageFile = new File(folder + "nodegraph"+world.getName().toLowerCase()+".png");
            	if(!imageFile.exists())
            	{
            		skipped.add(world.getUID());
            		continue WorldIter;
            	}
        	}
     		try
    		{
     			image = ImageIO.read(imageFile);
     			
     			argb = image.getType();
    	        switch(argb)
    	        {
	        		case BufferedImage.TYPE_4BYTE_ABGR:
	        		case BufferedImage.TYPE_4BYTE_ABGR_PRE:
    	        	case BufferedImage.TYPE_3BYTE_BGR:
    	        	case BufferedImage.TYPE_INT_BGR:	{ r = 16; g = 8; b = 0; break; }
    	        	case BufferedImage.TYPE_INT_ARGB:	
    	        	case BufferedImage.TYPE_INT_ARGB_PRE:
    	        	case BufferedImage.TYPE_INT_RGB:
    	        	case BufferedImage.TYPE_USHORT_555_RGB:
    	        	case BufferedImage.TYPE_USHORT_565_RGB: { r = 0; g = 8; b = 16; break; }
    	        	default: {skipped.add(world.getUID()); image.flush(); image.getGraphics().dispose(); continue WorldIter;}
    	        }
    	        
    	        hasAC = image.getAlphaRaster() != null;
    	        if(hasAC)
    	            pixelSize++;
    	        
    	        halfHeight = image.getHeight()/2;
    	        halfWidth = image.getWidth()/2;
    	        
    			raw = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    	        
    	        while(i < raw.length)
    	        {
    	        	if(hasAC)
    	        		argb = (raw[i++]&255)<<a;
    	        	else
    		        	argb = -16777216;
    	        	argb += (raw[i++]&255)<<b;
            		argb += (raw[i++]&255)<<g;
            		argb += (raw[i++]&255)<<r;

            		if(argb==-16777216)
            		{
            			j = i-pixelSize-1;
            			while(j > pixelSize-2 && ((j-pixelSize+1)/pixelSize)%image.getWidth() != image.getWidth()-1 )
            			{
        		        	argb ^= argb;
                    		argb += (raw[j--]&255)<<r;
                    		argb += (raw[j--]&255)<<g;
            	        	argb += (raw[j--]&255)<<b;
            	        	if(hasAC)
            	        		argb += (raw[j--]&255)<<a;
            	        	else
            		        	argb += -16777216;
            	        	argb = argb&16777215;
                    		if(argb==-16777216)
                    		{
                				skipped.add(world.getUID());
                				image.flush();
                				image.getGraphics().dispose();
                				continue WorldIter;
                    		}
                    		else if(argb==-1 || argb==-65536 || argb==-16711936 || argb==-16776961 || argb==-65281 || argb==-256 || argb==-16711681)
                    			continue;
                    		else
                    		{
                        		x = (((i-pixelSize)/pixelSize) % image.getWidth()) - halfHeight;
                        		z = ((((i-pixelSize)/pixelSize)-x) / image.getHeight()) - halfWidth;

                    			node = nodeMap.get(argb);
                    			node.coreChunk = (new NChunkID(x,z,world.getUID()));
                    			nodeMap.put(argb,node);
                    			j = -1;
                    			break;
                    		}
            			}
            			if(j == 0)
            			{
            				j = i;
            				while(j < raw.length && j%image.getWidth() != 0)
                			{
                	        	if(hasAC)
                	        		argb = (raw[j++]&255)<<a;
                	        	else
                		        	argb = -16777216;
                	        	argb += (raw[j++]&255)<<b;
                        		argb += (raw[j++]&255)<<g;
                        		argb += (raw[j++]&255)<<r;
                        		
                        		argb = argb&16777215;
                        		if(argb==-16777216)
                        			j = raw.length;
                        		else if(argb==-1 || argb==-65536 || argb==-16711936 || argb==-16776961 || argb==-65281 || argb==-256 || argb==-16711681)
                        			continue;
                        		else
                        		{
                            		x = (((i-pixelSize)/pixelSize) % image.getWidth()) - halfHeight;
                            		z = ((((i-pixelSize)/pixelSize)-x) / image.getHeight()) - halfWidth;

                        			node = nodeMap.get(argb);
                        			node.coreChunk = (new NChunkID(x,z,world.getUID()));
                        			nodeMap.put(argb,node);
                        			j = -1;
                        			break;
                        		}
                			}
            				if( j == raw.length || j%image.getWidth() == 0)
            				{
                				skipped.add(world.getUID());
                				image.flush();
                				image.getGraphics().dispose();
                				continue WorldIter;
            				}
            			}
            		}
            		else if(argb==-1 || argb==-65536 || argb==-16711936 || argb==-16776961 || argb==-65281 || argb==-256 || argb==-16711681)
            			continue;
            		else
            		{
            			node = nodeMap.get(argb);
                		if(node == null)
                		{
                			node = new NNode();
                			node.argb = argb;
                		}
                		
                		x = (((i-pixelSize)/pixelSize) % image.getWidth()) - halfHeight;
                		z = ((((i-pixelSize)/pixelSize)-x) / image.getHeight()) - halfWidth;
                		
            			node.borderChunk.add(new NChunkID(x,z,world.getUID()));
            			nodeMap.put(argb,node);
            		}
    	        }
    	        for(NNode nodeA : nodeMap.values())
    	        	NNodeList.add(nodeA);
    		}
    		catch (IOException e)
    		{
    			
    		}
	        image.flush();
	        image.getGraphics().dispose();
        }
	}
}