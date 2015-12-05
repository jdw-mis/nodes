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

	private static String sep = File.separator;
	
	public static void checkDir()
	{
		File dir = folder;
		if(!dir.exists())
			dir.mkdir();
		dir = new File(folder+sep+"player");
		if(!dir.exists())
			dir.mkdir();
		dir = new File(folder+sep+"faction");
		if(!dir.exists())
			dir.mkdir();
		dir = new File(folder+sep+"node");
		if(!dir.exists())
			dir.mkdir();
		dir = new File(folder+sep+"relation");
		if(!dir.exists())
			dir.mkdir();
	}

	public static Boolean detectFirstRun()
	{
		checkDir();
		if(new File(folder+sep+"defaultConfig.json").exists())
			return false;
		else if(folder.listFiles().length>4)
			return false;
		return true;
	}

	private static void toDisk(String print, String dir)
	{
		FileWriter fw;
		BufferedWriter bw;
		try
		{
			fw = new FileWriter(folder+sep+dir+".json");
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
		toDisk(json.toJson(NResourceList.i),"resources");
		Iterator<UUID> iter = NPlayerList.i.saveIter();
		while(iter.hasNext())
		{
			player = NPlayerList.i.get(iter.next());
			toDisk(json.toJson(player),"player"+sep+player.ID.toString());
		}

		iter = NFactionList.i.saveIter();
		while(iter.hasNext())
		{
			faction = NFactionList.i.get(iter.next());
			toDisk(json.toJson(faction),"faction"+sep+faction.ID.toString());
		}

		iter = NNodeList.i.saveIter();
		while(iter.hasNext())
		{
			node = NNodeList.i.get(iter.next());
			toDisk(json.toJson(node),"node"+sep+node.ID.toString());
		}

		iter = NRelationList.i.saveIter();
		while(iter.hasNext())
		{
			relation = NRelationList.i.get(iter.next());
			toDisk(json.toJson(relation),"relation"+sep+relation.ID.toString());
		}
	}

	public static void saveAll()
	{
		checkDir();

		Gson json = new GsonBuilder().setPrettyPrinting().create();
		toDisk(json.toJson(NResourceList.i),"resources");
		for(NPlayer player : NPlayerList.i.playerSet())
			toDisk(json.toJson(player),"player"+sep+player.ID.toString());
		for(NFaction faction : NFactionList.i.factionSet())
			toDisk(json.toJson(faction),"faction"+sep+faction.ID.toString());
		for(NNode node : NNodeList.i.nodeSet())
			toDisk(json.toJson(node),"node"+sep+node.ID.toString());
		for(NRelation relate : NRelationList.i.relateSet())
			toDisk(json.toJson(relate),"relation"+sep+relate.ID.toString());
	}

	//warning: this drops extant loaded data
	public static void load()
	{
		checkDir();

		NPlayerList.i.flush();
		NFactionList.i.flush();
		NNodeList.i.flush();
		NRelationList.i.flush();
		NChunkList.i.flush();

		Gson json = new GsonBuilder().setPrettyPrinting().create();
		toDisk(json.toJson(new NConfig()),"defaultConfig");

		File config = new File(folder+sep+"config.json");
		if(config.exists())
		{
			NConfig.i = json.fromJson(diskTo(config), NConfig.class);
		}
		else
		{
			toDisk(json.toJson(new NConfig()),"config");
		}

		config = new File(folder+sep+"resources.json");
		if(config.exists())
		{
			NResourceList.i = json.fromJson(diskTo(config), NResourceList.class);
		}

		File[] fileList = new File(folder+sep+"player"+sep).listFiles();
		for(File file : fileList)
			NPlayerList.i.add(json.fromJson(diskTo(file),NPlayer.class));
		fileList = new File(folder+sep+"faction"+sep).listFiles();
		for(File file : fileList)
			NFactionList.i.add(json.fromJson(diskTo(file),NFaction.class));
		fileList = new File(folder+sep+"node"+sep).listFiles();
		for(File file : fileList)
			NNodeList.i.add(json.fromJson(diskTo(file),NNode.class));
		fileList = new File(folder+sep+"relation"+sep).listFiles();
		for(File file : fileList)
			NRelationList.i.add(json.fromJson(diskTo(file),NRelation.class));
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

	public static String PNGtoNodes()
	{
		String error = "PNGtoNodes Failed on Following:";
		HashMap<Integer,NNode> nodeMap = new HashMap<Integer,NNode>();
		HashMap<Integer,NNode> nodeOut = new HashMap<Integer,NNode>();
		List<UUID> iterated = new LinkedList<UUID>();
		BufferedImage image = null;
		File imageFile;
		NNode node;
		NChunkID CID;
		byte[] raw;
		byte r,g,b;
		boolean hasAC,success;
		int argb,x,z,centerHeight,centerWidth,j,i,pixelSize = 3;

		WorldIter:
		for(World world : Bukkit.getWorlds())
		{
			imageFile = new File(folder+sep+"nodegraph"+world.getName()+".png");
			if(!imageFile.exists())
			{
				imageFile = new File(folder+sep+"nodegraph"+world.getName().toLowerCase()+".png");
				if(!imageFile.exists())
				{
					error += "\nWorld: "+world.getName()+" - nodegraph" + world.getName() + ".png file missing.";
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
					default: { error += "\nWorld: "+world.getName()+" - Invalid Image Type.";
						image.flush(); image.getGraphics().dispose(); continue WorldIter;}
				}
	
				hasAC = image.getAlphaRaster() != null;
				if(hasAC)
					pixelSize++;
	
				raw = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

				nodeMap.clear();
				for(NNode nodeA : NNodeList.i.nodeSet())
					if(nodeA.world.equals(world.getUID()))
						nodeMap.put(nodeA.argb, nodeA);

				centerHeight = image.getHeight()/2;
				centerWidth = image.getWidth()/2;
				success = true;
				i = 0;
				while(i < raw.length)
				{
					if(hasAC)
						argb = (raw[i++]&0x000000ff)<<24;
					else
						argb = 0xff000000;
					argb += (raw[i++]&0x000000ff)<<b;
					argb += (raw[i++]&0x000000ff)<<g;
					argb += (raw[i++]&0x000000ff)<<r;
					if(argb==0xff000000)
					{
						j = i-pixelSize-1;
						while(j > pixelSize-2 && ((j-pixelSize+1)/pixelSize)%image.getWidth() != image.getWidth()-1 )
						{
							argb ^= argb;
							argb += (raw[j--]&0x000000ff)<<r;
							argb += (raw[j--]&0x000000ff)<<g;
							argb += (raw[j--]&0x000000ff)<<b;
							if(hasAC)
								argb += (raw[j--]&0x000000ff)<<24;
							else
								argb += 0xff000000;
							if(argb==0xff000000)
							{
								x = (((i-pixelSize)/pixelSize) % image.getWidth());
								z = ((((i-pixelSize)/pixelSize)-x) / image.getHeight());
								error += "\nWorld: "+world.getName()+" - Broken Node at "+x+", "+z;
								x = (((j-pixelSize)/pixelSize) % image.getWidth());
								z = ((((j-pixelSize)/pixelSize)-x) / image.getHeight());
								error += "; breakage occurs at "+x+", "+z+".";
								success = false;
								j = -1;
								break;
							}
							else if(argb==0xff808080 || argb==0xffffffff || argb==0xffff0000 || argb==0xff00ff00 || argb==0xff0000ff || argb==0xffff00ff || argb==0xffffff00 || argb==0xff00ffff)
								continue;
							else
							{
								if(success)
								{
									node = nodeOut.get(argb);

									x = (((i-pixelSize)/pixelSize) % image.getWidth());
									z = ((((i-pixelSize)/pixelSize)-x) / image.getHeight());
									CID = new NChunkID(x,z,world.getUID());

									node.coreChunk = CID;
									nodeOut.put(argb,node);
								}
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
									argb = (raw[j++]&0x000000ff)<<24;
								else
									argb = 0xff000000;
								argb += (raw[j++]&0x000000ff)<<b;
								argb += (raw[j++]&0x000000ff)<<g;
								argb += (raw[j++]&0x000000ff)<<r;
								if(argb==0xff000000)
									j = raw.length;
								else if(argb==0xff808080 || argb==0xffffffff || argb==0xffff0000 || argb==0xff00ff00 || argb==0xff0000ff || argb==0xffff00ff || argb==0xffffff00 || argb==0xff00ffff)
									continue;
								else
								{
									if(success)
									{
										node = nodeOut.get(argb);
										if(node == null)
										{
											node = nodeMap.get(argb);
											if(node == null)
											{
												node = new NNode();
												node.argb = argb;
											}
											node.borderChunk.clear();
										}

										x = (((i-pixelSize)/pixelSize) % image.getWidth());
										z = ((((i-pixelSize)/pixelSize)-x) / image.getHeight());
										CID = new NChunkID(x,z,world.getUID());

										node.coreChunk = CID;
										nodeOut.put(argb,node);
									}
									j = -1;
									break;
								}
							}
							if( j == raw.length || j%image.getWidth() == 0)
							{
								x = (((i-pixelSize)/pixelSize) % image.getWidth());
								z = ((((i-pixelSize)/pixelSize)-x) / image.getHeight());
								error += "\nWorld: "+world.getName()+" - Broken Node at "+x+", "+z;
								x = (((j-pixelSize)/pixelSize) % image.getWidth());
								z = ((((j-pixelSize)/pixelSize)-x) / image.getHeight());
								error += "; breakage occurs at "+x+", "+z+".";
								success = false;
							}
						}
					}
					else if(success)
						if(argb==0xffffffff || argb==0xffff0000 || argb==0xff00ff00 || argb==0xff0000ff || argb==0xffff00ff || argb==0xffffff00 || argb==0xff00ffff)
							continue;
						else if(argb==0xff808080)
						{
							centerWidth = (i/pixelSize-1) % image.getWidth();
							centerHeight = (i/pixelSize-centerWidth-1) / image.getHeight();
						}
						else
						{
							node = nodeOut.get(argb);
							if(node == null)
							{
								node = nodeMap.get(argb);
								if(node == null)
								{
									node = new NNode();
									node.argb = argb;
								}
								node.borderChunk.clear();
								node.coreChunk = null;
							}

							x = (i/pixelSize-1) % image.getWidth();
							z = (i/pixelSize-x-1) / image.getHeight();
							CID = new NChunkID(x,z,world.getUID());

							node.borderChunk.add(CID);
							nodeOut.put(argb,node);
						}
				}
				if(success)
				{
					for(NNode nodeA : nodeOut.values())
					{
						CID = nodeA.coreChunk;
						if(CID == null)
							nodeA.filler = true;
						else
						{
							CID.x -= centerWidth;
							CID.z -= centerHeight;
							nodeA.coreChunk = CID;
						}
						for(NChunkID CIDA : nodeA.borderChunk)
						{
							nodeA.borderChunk.remove(CIDA);
							CIDA.x -= centerWidth;
							CIDA.z -= centerHeight;
							nodeA.borderChunk.add(CIDA);
							NChunkList.i.add(new NChunk(CIDA,nodeA));
						}
						NNodeList.i.add(nodeA);
					}
					for(NNode nodeA : NNodeList.i.nodeSet())
						if(nodeA.world.equals(world.getUID()) && !nodeOut.containsKey(nodeA.argb))
							NNodeList.i.remove(nodeA.ID);

					iterated.add(world.getUID());
				}
			}
			catch (IOException e)
			{
				error += "\nWorld: "+world.getName()+" - " + e.getMessage() + ".";
			}
			image.flush();
			image.getGraphics().dispose();
		}
		for(NNode nodeA : NNodeList.i.nodeSet())
			if(!iterated.contains(nodeA.ID))
				NNodeList.i.remove(nodeA.ID);
		return error.equals("PNGtoNodes Failed on Following:") ? "PNGtoNodes Successful" : error;
	}
}