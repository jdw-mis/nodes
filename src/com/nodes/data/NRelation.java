package com.nodes.data;

import java.util.UUID;

import org.json.JSONObject;

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
	public boolean capture;
	
	
	
	public NRelation(UUID senior, UUID junior)
	{
		seniorID = senior;
		juniorID = junior;
		ID = UUID.randomUUID();
		acceptedSenior = true;
	}
	
    public void xchg()		{ UUID temp = seniorID; seniorID = juniorID; juniorID = temp; }
    
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
    
	public JSONObject toJson()
	{
		JSONObject json = new JSONObject();
		json.put("ID",ID);
		json.put("seniorID",seniorID);
		json.put("juniorID",juniorID);
		json.put("acceptedSenior",acceptedSenior);
		json.put("acceptedJunior",acceptedJunior);
		json.put("merge",merge);
		json.put("puppet",puppet);
		json.put("marriage",marriage);
		json.put("move",move);
		json.put("moveCore",moveCore);
		json.put("blockBreak",blockBreak);
		json.put("blockPlace",blockPlace);
		json.put("attack",attack);
		json.put("openInv",openInv);
		json.put("useWood",useWood);
		json.put("useStone",useStone);
		json.put("water",water);
		json.put("lava",lava);
		json.put("cartPlace",cartPlace);
		json.put("tnt",tnt);
		json.put("fire",fire);
		json.put("home",home);
		json.put("capture",capture);
		json.put("pendingSenior",pendingSenior.toJson());
		json.put("pendingJunior",pendingJunior.toJson());
		return json;
	}
}
