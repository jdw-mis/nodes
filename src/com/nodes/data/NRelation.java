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
	public boolean move;
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
}
