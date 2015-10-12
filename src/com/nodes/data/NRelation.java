package com.nodes.data;

import java.util.UUID;

public class NRelation
{
	private UUID ID;
	private UUID seniorID;
	private UUID juniorID;
	private boolean accepted;
	
	private boolean pMove;
	private boolean pBreak;
	private boolean pPlace;
	private boolean pDamage;
	

    public UUID		getID()				{ return ID; }
}
