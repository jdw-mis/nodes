package com.nodes.data;

import java.util.Comparator;
import java.util.UUID;

public class NRelation
{
	public UUID ID;
	public UUID seniorID;
	public UUID juniorID;
	private NRelation pendingSenior;
	private NRelation pendingJunior;
	//Gov Ties
	public boolean merge;
	public boolean puppet;
	public boolean marriage;
	//Player Perms
	public boolean walkEmbedded;
	public boolean walkExposed;
	public boolean walkCore;
	public boolean blockBreak;
	public boolean blockPlace;
	public boolean blockInteract;
	public boolean attack;
	public boolean openInv;
	public boolean useWood;
	public boolean useStone;
	public boolean water;
	public boolean lava;
	public boolean cartPlace;
	public boolean tnt;
	public boolean fire;
	public boolean home;
	//
	public boolean enemy;
	public boolean ally;
	public boolean neutral;
	public boolean undef;

	public NRelation(UUID senior, UUID junior)
	{
		ID = UUID.randomUUID();
		seniorID = senior;
		juniorID = junior;
		pendingSenior = null;
		pendingJunior = null;
		merge = false;
		puppet = false;
		marriage = false;
		walkEmbedded = false;
		walkExposed = false;
		walkCore = false;
		blockBreak = false;
		blockPlace = false;
		blockInteract = false;
		attack = false;
		openInv = false;
		useWood = false;
		useStone = false;
		water = false;
		lava = false;
		cartPlace = false;
		tnt = false;
		fire = false;
		home = false;
		enemy = false;
		ally = false;
		neutral = false;
		undef = true;
	}

	public NRelation()
	{
		ID = UUID.randomUUID();
		seniorID = null;
		juniorID = null;
		pendingSenior = null;
		pendingJunior = null;
		merge = false;
		puppet = false;
		marriage = false;
		walkEmbedded = false;
		walkExposed = false;
		walkCore = false;
		blockBreak = false;
		blockPlace = false;
		blockInteract = false;
		attack = false;
		openInv = false;
		useWood = false;
		useStone = false;
		water = false;
		lava = false;
		cartPlace = false;
		tnt = false;
		fire = false;
		home = false;
		enemy = false;
		ally = false;
		neutral = false;
		undef = true;
	}

	public boolean acceptRelation(UUID sender)
	{
		if(this.seniorID.equals(sender))
		{
			if(pendingJunior != null && !pendingJunior.undef)
				return this.copyF(pendingJunior);
		}
		else
		{
			if(pendingSenior != null && !pendingSenior.undef)
				return this.copyF(pendingSenior);
		}
		return false;
	}
	public boolean setRelation(UUID sender, UUID subject, NRelation relate)
	{
		if(this.seniorID.equals(sender))
			if(relate.equals(pendingJunior) || relate.enemy)
				return this.copy(relate);
			else
				return !this.addPending(sender, relate);
		else
			if(relate.equals(pendingSenior) || relate.enemy)
				return this.copy(relate);
			else
				return !this.addPending(sender, relate);
	}
	private boolean addPending(UUID sender, NRelation relate)
	{
		if(this.seniorID.equals(sender))
		{
			pendingSenior = new NRelation(seniorID,juniorID);
			pendingSenior.ID = ID;
			return pendingSenior.copy(relate);
		}
		else
		{
			pendingJunior = new NRelation(juniorID,seniorID);
			pendingJunior.ID = ID;
			return pendingJunior.copy(relate);
		}
	}

	public boolean equals(Object obj)
	{
		if(!(obj instanceof NRelation))
			return false;
		NRelation relate = (NRelation)obj;
		return this.merge==relate.merge
				&&this.puppet==relate.puppet
				&&this.marriage==relate.marriage
				&&this.walkEmbedded==relate.walkEmbedded
				&&this.walkExposed==relate.walkExposed
				&&this.walkCore==relate.walkCore
				&&this.blockBreak==relate.blockBreak
				&&this.blockPlace==relate.blockPlace
				&&this.blockInteract==relate.blockInteract
				&&this.attack==relate.attack
				&&this.openInv==relate.openInv
				&&this.useWood==relate.useWood
				&&this.useStone==relate.useStone
				&&this.water==relate.water
				&&this.lava==relate.lava
				&&this.cartPlace==relate.cartPlace
				&&this.tnt==relate.tnt
				&&this.fire==relate.fire
				&&this.home==relate.home
				&&this.enemy==relate.enemy
				&&this.ally==relate.ally
				&&this.neutral==relate.neutral
				&&this.undef==relate.undef;
	}
	public boolean copyF(NRelation relate)
	{
		this.seniorID = relate.seniorID;
		this.juniorID = relate.juniorID;
		return this.copy(relate);
	}
	public boolean copy(NRelation relate)
	{
		this.merge=relate.merge;
		this.puppet=relate.puppet;
		this.marriage=relate.marriage;
		this.walkEmbedded=relate.walkEmbedded;
		this.walkExposed=relate.walkExposed;
		this.walkCore=relate.walkCore;
		this.blockBreak=relate.blockBreak;
		this.blockPlace=relate.blockPlace;
		this.blockInteract=relate.blockInteract;
		this.attack=relate.attack;
		this.openInv=relate.openInv;
		this.useWood=relate.useWood;
		this.useStone=relate.useStone;
		this.water=relate.water;
		this.lava=relate.lava;
		this.cartPlace=relate.cartPlace;
		this.tnt=relate.tnt;
		this.fire=relate.fire;
		this.home=relate.home;
		this.enemy=relate.enemy;
		this.ally=relate.ally;
		this.neutral=relate.neutral;
		this.undef=relate.undef;
		return true;
	}

	public NFaction getSenior()
	{
		return NFactionList.i.get(seniorID);
	}

	public NFaction getJunior()
	{
		return NFactionList.i.get(juniorID);
	}

	public void delete()
	{
		NFaction faction = getJunior();
		if(faction != null)
		{
			faction.deleteRelation(seniorID);
			NFactionList.i.add(faction);
		}
		faction = getSenior();
		if(faction != null)
		{
			faction.deleteRelation(juniorID);
			NFactionList.i.add(faction);
		}
		NRelationList.i.remove(ID);
	}
	public void clearPending()
	{
		pendingSenior = null;
		pendingJunior = null;
	}

	public static Comparator<UUID> relationTypeComp = new Comparator<UUID>()
	{
		public int compare(UUID o1, UUID o2)
		{
			NRelation r1 = NRelationList.i.get(o1);
			NRelation r2 = NRelationList.i.get(o2);
			if(r1.ally == r2.ally || r1.neutral == r2.neutral || r1.enemy == r2.enemy)
			{
				NFaction f1 = NFactionList.i.get(r1.juniorID);
				NFaction f2 = NFactionList.i.get(r2.juniorID);
				if(f1.relList)
					f1 = NFactionList.i.get(r1.seniorID);
				if(f2.relList)
					f2 = NFactionList.i.get(r2.seniorID);
				return f1.name.compareToIgnoreCase(f2.name);
			}
			else if(r1.ally)
				return -1;
			else if(r2.ally)
				return 1;
			else if(r1.neutral)
				return -1;
			else if(r2.neutral)
				return 1;
			else if(r1.enemy)
				return -1;
			else
				return 1;
		}
	};
}
