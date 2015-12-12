package com.nodes.data;

import java.util.UUID;

public class NRank
{
	//
	public UUID ID;
	public String rankName;
	public String rankDesc;
	// Interaction based
	public Boolean blockEdit;
	public Boolean walkCore;
	public Boolean walkEmbedded;
	public Boolean walkExposed;
	public Boolean chest;
	// CMD based
	public Boolean invite;
	public Boolean kick;
	public Boolean kickSameRank;
	public Boolean open;
	public Boolean close;
	public Boolean home;
	public Boolean setHome;
	public Boolean promote;
	public Boolean promoteSameRank;
	public Boolean demote;
	public Boolean demoteSameRank;
	public Boolean name;
	public Boolean desc;
	public Boolean relate;
	public Boolean delete;

	public NRank()
	{
		ID = UUID.randomUUID();
		rankName = "Default Desc";
		rankDesc = "Default Name";
		blockEdit = false;
		walkCore = false;
		walkEmbedded = false;
		walkExposed = false;
		chest = false;

		invite = false;
		kick = false;
		kickSameRank = false;
		open = false;
		close = false;
		home = false;
		setHome = false;
		promote = false;
		promoteSameRank = false;
		demote = false;
		demoteSameRank = false;
		name = false;
		desc = false;
		relate = false;
		delete = false;
	}
}
