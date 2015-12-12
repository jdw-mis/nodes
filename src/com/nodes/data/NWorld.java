package com.nodes.data;

public class NWorld
{
	NWorld(int imageWidth, int imageHeight, int centerWidth, int centerHeight)
	{
		c1X = -centerWidth;
		c1Z = -centerHeight;
		c2X = imageWidth-centerWidth;
		c2Z = imageHeight-centerHeight;
	}
	private int c1X;
	private int c1Z;
	private int c2X;
	private int c2Z;

	public boolean isInBounds(int x, int z)
	{
		return c1X < x && x < c2X && c1Z < z && z < c2Z;
	}
}