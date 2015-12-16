package com.nodes.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;

public class NWorld
{
	public UUID ID;
	NWorld(UUID world, int imageWidth, int imageHeight)
	{
		ID = world;
		c1X = 0;
		c1Z = 0;
		c2X = imageWidth;
		c2Z = imageHeight;
		mapWidth = imageWidth;
		mapHeight = imageHeight;
		map = new UUID[imageWidth][imageHeight];
		encode = new HashMap<Integer,Enum<ChatColor>>(17);
		encode.put(0xFF000000, ChatColor.BLACK);
		encode.put(0xFF0000AA, ChatColor.DARK_BLUE);
		encode.put(0xFF00AA00, ChatColor.DARK_GREEN);
		encode.put(0xFF00AAAA, ChatColor.DARK_AQUA);
		encode.put(0xFFAA0000, ChatColor.DARK_RED);
		encode.put(0xFFAA00AA, ChatColor.DARK_PURPLE);
		encode.put(0xFFFFAA00, ChatColor.GOLD);
		encode.put(0xFFAAAAAA, ChatColor.GRAY);
		encode.put(0xFF555555, ChatColor.DARK_GRAY);
		encode.put(0xFF5555FF, ChatColor.BLUE);
		encode.put(0xFF55FF55, ChatColor.GREEN);
		encode.put(0xFF55FFFF, ChatColor.AQUA);
		encode.put(0xFFFF5555, ChatColor.RED);
		encode.put(0xFFFF55FF, ChatColor.LIGHT_PURPLE);
		encode.put(0xFFFFFF55, ChatColor.YELLOW);
		encode.put(0xFFFFFFFF, ChatColor.WHITE);
		encode.put(0xFF000055, ChatColor.DARK_BLUE);
		encode.put(0xFF0000FF, ChatColor.BLUE);
		encode.put(0xFF005500, ChatColor.DARK_GREEN);
		encode.put(0xFF005555, ChatColor.DARK_AQUA);
		encode.put(0xFF0055AA, ChatColor.DARK_AQUA);
		encode.put(0xFF0055FF, ChatColor.BLUE);
		encode.put(0xFF00AA55, ChatColor.DARK_GREEN);
		encode.put(0xFF00AAFF, ChatColor.AQUA);
		encode.put(0xFF00FF00, ChatColor.GREEN);
		encode.put(0xFF00FF55, ChatColor.GREEN);
		encode.put(0xFF00FFAA, ChatColor.GREEN);
		encode.put(0xFF00FFFF, ChatColor.AQUA);
		encode.put(0xFF550000, ChatColor.DARK_RED);
		encode.put(0xFF550055, ChatColor.DARK_PURPLE);
		encode.put(0xFF5500AA, ChatColor.DARK_BLUE);
		encode.put(0xFF5500FF, ChatColor.BLUE);
		encode.put(0xFF555500, ChatColor.DARK_RED);
		encode.put(0xFF5555AA, ChatColor.BLUE);
		encode.put(0xFF55AA00, ChatColor.GREEN);
		encode.put(0xFF55AA55, ChatColor.GREEN);
		encode.put(0xFF55AAAA, ChatColor.DARK_AQUA);
		encode.put(0xFF55AAFF, ChatColor.AQUA);
		encode.put(0xFF55FF00, ChatColor.GREEN);
		encode.put(0xFF55FFAA, ChatColor.AQUA);
		encode.put(0xFFAA0055, ChatColor.DARK_PURPLE);
		encode.put(0xFFAA00FF, ChatColor.DARK_PURPLE);
		encode.put(0xFFAA5500, ChatColor.DARK_RED);
		encode.put(0xFFAA5555, ChatColor.RED);
		encode.put(0xFFAA55AA, ChatColor.LIGHT_PURPLE);
		encode.put(0xFFAA55FF, ChatColor.LIGHT_PURPLE);
		encode.put(0xFFAAAA00, ChatColor.GOLD);
		encode.put(0xFFAAAA55, ChatColor.GOLD);
		encode.put(0xFFAAAAFF, ChatColor.AQUA);
		encode.put(0xFFAAFF00, ChatColor.YELLOW);
		encode.put(0xFFAAFF55, ChatColor.YELLOW);
		encode.put(0xFFAAFFAA, ChatColor.GREEN);
		encode.put(0xFFAAFFFF, ChatColor.AQUA);
		encode.put(0xFFFF0000, ChatColor.RED);
		encode.put(0xFFFF0055, ChatColor.RED);
		encode.put(0xFFFF00AA, ChatColor.LIGHT_PURPLE);
		encode.put(0xFFFF00FF, ChatColor.LIGHT_PURPLE);
		encode.put(0xFFFF5500, ChatColor.RED);
		encode.put(0xFFFF55AA, ChatColor.LIGHT_PURPLE);
		encode.put(0xFFFFAA55, ChatColor.GOLD);
		encode.put(0xFFFFAAAA, ChatColor.RED);
		encode.put(0xFFFFAAFF, ChatColor.LIGHT_PURPLE);
		encode.put(0xFFFFFF00, ChatColor.YELLOW);
		encode.put(0xFFFFFFAA, ChatColor.YELLOW);
		decode = new HashMap<Enum<ChatColor>,Integer>(17);
		decode.put(ChatColor.BLACK,			0xFF000000);
		decode.put(ChatColor.DARK_BLUE,		0xFF0000AA);
		decode.put(ChatColor.DARK_GREEN,	0xFF00AA00);
		decode.put(ChatColor.DARK_AQUA,		0xFF00AAAA);
		decode.put(ChatColor.DARK_RED,		0xFFAA0000);
		decode.put(ChatColor.DARK_PURPLE,	0xFFAA00AA);
		decode.put(ChatColor.GOLD,			0xFFFFAA00);
		decode.put(ChatColor.GRAY,			0xFFAAAAAA);
		decode.put(ChatColor.DARK_GRAY,		0xFF555555);
		decode.put(ChatColor.BLUE,			0xFF5555FF);
		decode.put(ChatColor.GREEN,			0xFF55FF55);
		decode.put(ChatColor.AQUA,			0xFF55FFFF);
		decode.put(ChatColor.RED,			0xFFFF5555);
		decode.put(ChatColor.LIGHT_PURPLE,	0xFFFF55FF);
		decode.put(ChatColor.YELLOW,		0xFFFFFF55);
		decode.put(ChatColor.WHITE,			0xFFFFFFFF);
	}
	private int c1X;
	private int c1Z;
	private int c2X;
	private int c2Z;
	
	public void setCenter(int centerWidth, int centerHeight)
	{
		c1X = -centerWidth;
		c1Z = -centerHeight;
		c2X -= centerWidth;
		c2Z -= centerHeight;
	}
	
	public boolean isInBounds(int x, int z)
	{
		return c1X <= x && x < c2X && c1Z <= z && z < c2Z;
	}

	private static final int FULLTEXTWIDTH = 35;
	private static final int FULLTEXTHEIGHT = 20;
	private static final int FULLHALFTEXTWIDTH = 17;
	private static final int FULLHALFTEXTHEIGHT = 9;
	private static final int MEDIUMTEXTWIDTH = 21;
	private static final int MEDIUMTEXTHEIGHT = 20;
	private static final int MEDIUMHALFTEXTWIDTH = 10;
	private static final int MEDIUMHALFTEXTHEIGHT = 9;
	private static final int SMALLTEXTWIDTH = 19;
	private static final int SMALLTEXTHEIGHT = 10;
	private static final int SMALLHALFTEXTWIDTH = 9;
	private static final int SMALLHALFTEXTHEIGHT = 4;
	//private static int[] bitMasks = new int[]{0xFF0000, 0xFF00, 0xFF, 0xFF000000};
	private static HashMap<Enum<ChatColor>,Integer> decode;
	private static HashMap<Integer,Enum<ChatColor>> encode;
	
	private UUID[][] map;
	public int mapWidth;
	public int mapHeight;

	public UUID getID(int x, int z) { return map[x][z]; }
	public void putID(int x, int z, UUID ID) { map[x][z] = ID; }
	public UUID getIDIG(int x, int z) { return map[x-c1X][z-c1Z]; }
	public void putIDIG(int x, int z, UUID ID) { map[x-c1X][z-c1Z] = ID; }
	
	public String getMap(Chunk center, UUID factionID, int zoom, int mode, int size)
	{
		int TEXTWIDTH, TEXTHEIGHT, HALFTEXTWIDTH, HALFTEXTHEIGHT, ZWIDTH, ZHEIGHT, WIDTH, HEIGHT, x1, z1, x2, z2;

		if(size == 2)
		{
			TEXTWIDTH = FULLTEXTWIDTH;
			TEXTHEIGHT = FULLTEXTHEIGHT;
			HALFTEXTWIDTH = FULLHALFTEXTWIDTH;
			HALFTEXTHEIGHT = FULLHALFTEXTHEIGHT;
		}
		else if(size == 1)
		{
			TEXTWIDTH = MEDIUMTEXTWIDTH;
			TEXTHEIGHT = MEDIUMTEXTHEIGHT;
			HALFTEXTWIDTH = MEDIUMHALFTEXTWIDTH;
			HALFTEXTHEIGHT = MEDIUMHALFTEXTHEIGHT;
		}
		else
		{
			TEXTWIDTH = SMALLTEXTWIDTH;
			TEXTHEIGHT = SMALLTEXTHEIGHT;
			HALFTEXTWIDTH = SMALLHALFTEXTWIDTH;
			HALFTEXTHEIGHT = SMALLHALFTEXTHEIGHT;
		}
		
		int cx = center.getX()-c1X;
		int cz = center.getZ()-c1Z;
		if(mapWidth < TEXTWIDTH*zoom)
		{
			x1 = 0;
			WIDTH = Math.floorDiv(mapWidth, zoom);
			ZWIDTH = WIDTH*zoom;
			x2 = ZWIDTH;
		}
		else
		{
			WIDTH = TEXTWIDTH;
			ZWIDTH = TEXTWIDTH*zoom;
			x1 = (cx-HALFTEXTWIDTH-1)*zoom;
			x2 = ((cx+HALFTEXTWIDTH+1)*zoom);
			if(x1 < 0)
			{
				x2 -= x1;
				x1 = 0;
			}
			if(x2 > mapWidth)
			{
				x1 -= x2-mapWidth-1;
				x2 = mapWidth;
			}
		}
		if(mapHeight < TEXTHEIGHT*zoom)
		{
			z1 = 0;
			HEIGHT = Math.floorDiv(mapHeight, zoom);
			ZHEIGHT = HEIGHT*zoom;
			z2 = ZHEIGHT;
		}
		else
		{
			HEIGHT = TEXTHEIGHT;
			ZHEIGHT = TEXTHEIGHT*zoom;
			z1 = (cz-HALFTEXTHEIGHT-1)*zoom;
			z2 = ((cz+HALFTEXTHEIGHT+1)*zoom);
			if(z1 < 0)
			{
				z2 -= z1;
				z1 = 0;
			}
			if(z2 > mapHeight)
			{
				z1 -= z2-mapHeight;
				z2 = mapHeight;
			}
		}
		
		UUID[][] IDARR = getSector(x1,z1,x2,z2);
		int[][] colorArr = new int[ZWIDTH][ZHEIGHT];
		NNode node;
		NChunkID core;
		NFaction faction;
		String output = "";
		
		if(mode == 1)
		{
	    	for (int i = 0; i < ZWIDTH; i++)
	            for (int j = 0; j < ZHEIGHT; j++)
	            {
	            	node = NNodeList.i.get(IDARR[i][j]);
	            	if(node == null)
	            		colorArr[i][j] = decode.get(NConfig.i.NullColor);
	            	else
	            	{
	            		if(cx-x1 == i && cz-z1 == j)
		            		colorArr[i][j] = decode.get(ChatColor.WHITE);
	            		else
	            		{
		            		core = node.coreChunk;
		            		if(core != null && core.x-x1-c1X == i && core.z-z1-c1Z == j)
			            		colorArr[i][j] = decode.get(ChatColor.BLACK);
		            		else
		            		{
				            	faction = node.getFaction();
				            	if(faction != null)
				            		if(faction.ID.equals(factionID))
					            		colorArr[i][j] = decode.get(NConfig.i.SelfColor);
				            		else
				            			colorArr[i][j] = decode.get(faction.getRelationColor(factionID));
				            	else
				            		colorArr[i][j] = decode.get(NConfig.i.UnrelateColor);
		            		}
	            		}
	            	}
	            }
		}
		else
	    	for (int i = 0; i < ZWIDTH; i++)
	            for (int j = 0; j < ZHEIGHT; j++)
	            {
	            	node = NNodeList.i.get(IDARR[i][j]);
	            	if(node == null)
	            		colorArr[i][j] = decode.get(NConfig.i.NullColor);
	            	else
	            		if(cx-x1 == i && cz-z1 == j)
		            		colorArr[i][j] = decode.get(ChatColor.WHITE);
	            		else
	            		{
		            		core = node.coreChunk;
		            		if(core != null && core.x-x1-c1X == i && core.z-z1-c1Z == j)
			            		colorArr[i][j] = decode.get(ChatColor.BLACK);
		            		else
		            			colorArr[i][j] = node.argb;
	            		}
	            }
		
		int[][] procArr = zoomOut(colorArr, zoom);
				
    	for (int i = 0; i < procArr[0].length; i++)
    	{
            for (int j = 0; j < procArr.length; j++)
            	output += encode.get(procArr[j][i]).toString() + '█';
            output+='\n';
    	}
		return output;
	}
	
	private UUID[][] getSector(int x1, int z1, int x2, int z2)
	{
		UUID[][] output = new UUID[x2-x1][z2-z1];
		for(int i = x1; i < x2; i++)
			output[i-x1] = Arrays.copyOfRange(map[i], z1, z2);
		return output;
	}
	
	private int[][] zoomOut(int[][] original, int zoom)
    {
		int i,j,x,z;
		int scaleWidth = original.length;
		int scaleHeight = original[0].length;
    	int[][] output;
		if(zoom > 1)
		{
			scaleWidth /= zoom;
			scaleHeight /= zoom;

			scaleWidth -= scaleWidth%zoom;
			scaleHeight -= scaleHeight%zoom;
			
			int orgWidth = scaleWidth*zoom;
			int orgHeight = scaleHeight*zoom;
			
			output = new int[scaleWidth][scaleHeight];
			HashMap<Integer,Integer> intCount = new HashMap<Integer,Integer>(25);
			int argb;
	    	for (i = 0; i < orgWidth; i += zoom)
		    	for (j = 0; j < orgHeight; j += zoom)
		    	{
		    		intCount.clear();
		    		for(x = i; x < i+zoom; x++)
		    			for(z = j; z < j+zoom; z++)
		    			{
		    				argb = palette(original[x][z]);
		    				if(intCount.containsKey(argb))
		    					intCount.put(argb,intCount.get(argb)+1);
		    				else
		    					intCount.put(argb,0);
		    			}
		    		if(zoom < NConfig.i.MapZoomShowCoreUntil && intCount.containsKey(0xFFFFFFFF))
		    			output[i/zoom][j/zoom] = 0xFFFFFFFF;
		    		else if(zoom < NConfig.i.MapZoomShowPlayerUntil && intCount.containsKey(0xFF000000))
			    		output[i/zoom][j/zoom] = 0xFF000000;
		    		else
		    		{
		    			argb = intCount.keySet().iterator().next();
		    			for(int key : intCount.keySet())
		    				if(intCount.get(argb) < intCount.get(key))
		    					argb = key;
			    		output[i/zoom][j/zoom] = argb;
		    		}
		    	}
			
			
			/*scaleWidth /= zoom;
			scaleHeight /= zoom;
			output = new int[scaleWidth][scaleHeight];
			int[] flat = Arrays.stream(original).flatMapToInt(Arrays::stream).toArray();
			SinglePixelPackedSampleModel pixelModel = new SinglePixelPackedSampleModel(DataBuffer.TYPE_INT, original.length, original[0].length, bitMasks);
			DataBufferInt intBuffer = new DataBufferInt(flat, flat.length);
			WritableRaster raster = Raster.createWritableRaster(pixelModel, intBuffer, new Point());
			BufferedImage org = new BufferedImage(ColorModel.getRGBdefault(), raster, false, null);
	    	BufferedImage scale = new BufferedImage(scaleWidth, scaleHeight, BufferedImage.TYPE_INT_RGB);
	    	Graphics2D g = scale.createGraphics();
	    	g.rotate(Math.toRadians(270), scaleWidth/2, scaleHeight/2);
	    	g.drawImage(org, 0, 0, scaleWidth, scaleHeight, original.length, 0, 0, original[0].length, null);  
	    	int[] raw = ((DataBufferInt) scale.getRaster().getDataBuffer()).getData();
	    	for (int i = 0; i < raw.length; i++)
	    	{
				x = i % scaleWidth;
				z = (i-x) / scaleWidth;
				output[x][z] = palette(raw[i]);
	    	}
	    	g.dispose();*/
		}
		else
		{
			output = new int[scaleWidth][scaleHeight];
	    	for (i = 0; i < scaleWidth; i++)
		    	for (j = 0; j < scaleHeight; j++)
					output[i][j] = palette(original[i][j]);
		    		
		}
    	return output;
    }

	private int palette(int argb)
	{
		int r = bstD(argb&0x000000FF);
		int g = bstD((argb&0x0000FF00)>>8);
		int b = bstD((argb&0x00FF0000)>>16);
		return r|(g<<8)|(b<<16)|0xFF000000;
	}
	private int bstD(int c)
	{
		int cm = Arrays.binarySearch(channelModel, c);
		if(cm<0)
		{
			cm = -(++cm);
		    int x = channelModel[cm-1];
		    int y = channelModel[cm];
		    if(x - c > c - y)
		       return x;
		    else
		       return y;
		}
		return c;
	}
	private static int[] channelModel = new int[]{
			0x00000000,
			0x00000055,
			0x000000AA,
			0x000000FF};
}