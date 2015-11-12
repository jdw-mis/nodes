package com.nodes.data;

import java.util.UUID;

public class NRank
{
	//
	public UUID ID;
	public String rankName;
	public String rankDesc;
	// Interaction based
	public Boolean edit;
	public Boolean walkCore;
	public Boolean walkInner;
	public Boolean chest;
	// CMD based
	public Boolean invite;
	public Boolean kick;
	public Boolean kickSameRank;
	public Boolean open;
	public Boolean close;
	public Boolean promote;
	public Boolean promoteSameRank;
	public Boolean demote;
	public Boolean demoteSameRank;
	public Boolean desc;
	public Boolean relate;
	
	public NRank()
	{
		ID = UUID.randomUUID();
		rankName = "Default Desc";
		rankDesc = "Default Name";
		edit = false;
		walkCore = false;
		chest = false;
		invite = false;
		kick = false;
		kickSameRank = false;
		open = false;
		close = false;
		promote = false;
		promoteSameRank = false;
		demote = false;
		demoteSameRank = false;
		desc = false;
		relate = false;
	}
}
