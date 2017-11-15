package com.mis.nodes.cmd;

import java.util.UUID;

import com.mis.nodes.data.NData;
import com.mis.nodes.data.NFaction;
import com.mis.nodes.data.Storage;

public class Factions{
	
  public static Result createFaction(UUID playerId, String fname) {
	if(factionExist(fname)) new Result(false,
		"This faction already exists you fucking mong.");
	return new Result(false,fname);
}
  
  public static boolean factionExist(String fname) {
	  fname = fname.toLowerCase();
	  for(NData d : Storage.Factions.values()) {
			String hname = ((NFaction)d).name;
			if(hname==fname) return true;
		} return false;
  }
}
