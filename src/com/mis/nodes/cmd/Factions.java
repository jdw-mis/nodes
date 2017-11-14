package com.mis.nodes.cmd;

import java.util.UUID;

public class Factions{
	
  public static Result createFaction(UUID playerId, String fname) {
	return new Result(false,fname);
}
  
}
