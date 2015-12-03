package com.nodes.data;

import java.util.UUID;

public class NRelation
{
	public UUID ID;
	public UUID seniorID;
	public UUID juniorID;
	private boolean acceptedSenior;
	private boolean acceptedJunior;
	private NRelation pendingSenior;
	private NRelation pendingJunior;

	//Gov Ties
	public boolean merge;
	public boolean puppet;
	public boolean marriage;

	//Player Perms
	public boolean moveInner;
	public boolean moveCore;
	public boolean blockBreak;
	public boolean blockPlace;
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



	public NRelation(UUID senior, UUID junior)
	{
		seniorID = senior;
		juniorID = junior;
		ID = UUID.randomUUID();
		acceptedSenior = true;
		pendingSenior = null;
		pendingJunior = null;
	}

	public NRelation()
	{
		ID = null;
		seniorID = null;
		juniorID = null;
		pendingSenior = null;
		pendingJunior = null;
		acceptedSenior = false;
		acceptedJunior = false;
		merge = false;
		puppet = false;
		marriage = false;
		moveInner = false;
		moveCore = false;
		blockBreak = false;
		blockPlace = false;
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
	}

	private void xchg()		{ UUID temp = seniorID; seniorID = juniorID; juniorID = temp; }

	public void	addPending(NRelation pend){
		if(this.seniorID.equals(pend.seniorID))
			pendingSenior = pend;
		else if(pend.puppet)
			pendingJunior = pend;
		else
		{
			pend.xchg();
			pendingJunior = pend;
		}
	}

	public NFaction getSenior()
	{
		return NFactionList.get(seniorID);
	}
	
	public NFaction getJunior()
	{
		return NFactionList.get(juniorID);
	}
}
