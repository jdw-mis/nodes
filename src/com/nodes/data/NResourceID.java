package com.nodes.data;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/*
 * need to update this to support metadata rather than damage
 * enchantments and such too
 */
public class NResourceID
{
	public Material material;
	public int amount;
	public short damage;

	NResourceID(Material mat, int x, int y)
	{
		material = mat;
		amount = x;
		damage = (short)y;
	}

	NResourceID(Material mat, int x)
	{
		material = mat;
		amount = x;
		damage = 0;
	}

	public ItemStack getItem()
	{
		return new ItemStack(material,amount,damage);
	}

	public boolean equals( Object obj )
	{
		if(!(obj instanceof NResourceID))
			return false;
		NResourceID NRID = (NResourceID)obj;
		return this.damage == NRID.damage && material.equals(NRID.material);
	}

	public int hashCode()
	{
		return Integer.hashCode(damage) + material.hashCode();
	}
}
