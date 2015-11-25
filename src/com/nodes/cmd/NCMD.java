package com.nodes.cmd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nodes.data.NFaction;
import com.nodes.data.NFactionList;
import com.nodes.data.NNode;
import com.nodes.data.NNodeList;
import com.nodes.data.NPlayer;
import com.nodes.data.NPlayerList;
import com.nodes.data.NRelation;
import com.nodes.data.NRelationList;

public class NCMD
{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
    	String command = cmd.getName().toLowerCase();
    	
    	if(command.equalsIgnoreCase("no") || command.equalsIgnoreCase("node"))
    	{ 
    		if(sender instanceof Player)
    		{
    			if(args.length<1)
    			{
    				sender.sendMessage("§6Use /no help to receive no help.");
    				return true;
    			}
    			else
    			{
    				args[0] = args[0].toLowerCase();
    				String result = null;
    				switch (args[0])
    				{
    					case "join":	result = join(sender, args); break;
    					case "leave":	result = leave(sender, args); break;
    					case "create":	result = create(sender, args); break;
    					case "kick":	result = kick(sender, args); break;
    					case "promote":	result = promote(sender, args); break;
    					case "demote":	result = demote(sender, args); break;
    					case "ally":	result = ally(sender, args); break;
    					case "war":		result = war(sender, args); break;
    					case "invite":	result = invite(sender, args); break;
    					case "close":	result = close(sender, args); break;
    					case "open":	result = open(sender, args); break;
    					case "info":	result = info(sender, args); break;
    					case "modify":	result = modify(sender, args); break;
    				}
    				
    				//get shit from result string
    				//§c starter = failure
    				//print to players
    			}
    		}
    		else
    		{
    			sender.sendMessage("§cNodes has received an invalid command input.");
    		}
    	}  
    	return true; 
    }
	
	
	
	private String info(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}



	private String modify(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}


	private String join(CommandSender sender, String[] args)
	{	
		if(args[1].length()>1)
		{
			if(sender instanceof Player)
			{
				NFaction faction = NFactionList.get(args[1]);
				NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
				if(faction != null)
				{
					if(faction.isInvited(player.ID) || faction.open)
					{
						faction.addPlayer(player.ID);
						player.faction = faction.ID;
						if(faction.isInvited(player.ID))
							faction.deleteInvite(player.ID);
						
						NFactionList.add(faction);
						NPlayerList.add(player);
						return "§6Welcome to "+faction.name+"!";
					}
					else
					{
						return "§cFaction Is Closed!";
					}
				}
				else
				{
					return "§cFaction Does Not Exist!";
				}
			}
			else
			{
				//console
				return "";
			}
		}
		else
		{
			return "§cNo Argument Received";
		}
	}
	
	
	
	private String leave(CommandSender sender, String[] args)
	{
		String output = null;
		if(sender instanceof Player)
		{
			NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
			if(player.faction != null)
			{
				NFaction faction = NFactionList.get(player.faction);
				player.faction = null;
				NPlayerList.add(player);
				if(faction.isLastPlayer())
				{
					flushFaction(faction);
					output = "§6Success!  The Empty Faction Has Been Deleted!";
				}
				else if(faction.getHigherRank(player.ID) == null)
				{
					Iterator<UUID> iter;
					UUID PID;
				    boolean bool = false;
			    	iter = faction.getPlayerIter();
			    	while(iter.hasNext())
			    	{
			    		PID = iter.next();
			    		if( !PID.equals(player.ID) && faction.getRankIndex(player.ID) == faction.getRankIndex(PID) )
			    		{
			    			bool = true;
			    			break;
			    		}
			    	}
			    	
					if(bool)
					{
					    
					    ArrayList<UUID> list = new ArrayList<UUID>();
					    int pRank = faction.getRankIndex(player.ID);
					    int rand;
					    bool = true;
					    while(pRank>=0 || bool)
					    {
					    	pRank--;
					    	iter = faction.getPlayerIter();
					    	while(iter.hasNext())
					    	{
					    		PID = iter.next();
					    		if(faction.getRankIndex(PID) == pRank)
					    		{
					    			list.add(PID);
					    			bool=false;
					    		}
					    	}
					    }
					    rand = (int)(Math.random()*list.size()-1);
					    faction.addPlayer( list.get(rand), faction.getRankID(pRank) );
						output = "§6Success!  "+NPlayerList.get(list.get(rand)).name+" is now leader!";
					}
					else
						output = "§6Success!";
				}
				faction.deletePlayer(player.ID);
				NFactionList.add(faction);
				return output;
			}
			else
				return "§cYou're Not In A Faction!";
		}
		else
		{
			//consoleshit
			return "";
		}
	}
	
	
	
	private String create(CommandSender sender, String[] args)
	{
		if(args[1].length()>1)
		{
			if(!NFactionList.contains(args[1]))
			{
				if(sender instanceof Player)
				{
					NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
					NFaction faction = new NFaction(args[1], player.ID);
					player.faction = faction.ID;
					NPlayerList.add(player);
					NFactionList.add(faction);
					return "§6Faction Has Been Created!";
				}
				else
				{
					//consoleshit
					return "";
				}
			}
			else
				return "§cFaction's Name Has Already Been Taken!";
		}
		else
			return "§cNo Argument Received";
	}
	
	
	
	private String kick(CommandSender sender, String[] args)
	{
		if(args[1].length()>1)
		{
			NPlayer subject = NPlayerList.get(args[1]);
			if(subject != null)
			{
				if(subject.faction != null)
				{
					NFaction faction = subject.getFaction();
					if(sender instanceof Player)
					{
						NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
						if(player.ID.equals(subject.ID))
							return "§cYou Can't Kick Yourself!";
						if(player.faction.equals(subject.faction) == false)
							return "§cTarget is not in your faction!";
						else if( player.getRank().kick == false )
							return "§cNo Kicking Permissions!";
						else
						{
							int playerRank = faction.getRankIndex(player.ID);
							int subjectRank = faction.getRankIndex(subject.ID);
							if( playerRank > subjectRank )
								return "§cTarget is of a higher rank!";
							else if( playerRank == subjectRank && player.getRank().kickSameRank == false )
								return "§cNo Same Rank Kick Permissions!";
						}
					}
					faction.deletePlayer(subject.ID);
					subject.faction = null;
					
					NFactionList.add(faction);
					NPlayerList.add(subject);
					return "§6Target has been Kicked!";
				}
				else
					return "§cTarget Isn't In A Faction!";
			}
			else
				return "§cTarget Doesn't Exist!";
		}
		else
			return "§cNo Argument Received";
	}
	
	
	
	private String promote(CommandSender sender, String[] args)
	{
		if(args[1].length()>1)
		{
			NPlayer subject = NPlayerList.get(args[1]);
			if(subject != null)
			{
				if(subject.faction != null)
				{
					NFaction faction = subject.getFaction();
					if(sender instanceof Player)
					{
						NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
						if(player.ID.equals(subject.ID))
							return "§cYou Can't Promote Yourself!";
						else if(player.faction.equals(subject.faction) == false)
							return "§cTarget is not in your faction!";
						else if( player.getRank().promote == false )
							return "§cNo Promotion Permissions!";
						else
						{
							int playerRank = faction.getRankIndex(player.ID);
							int subjectRank = faction.getRankIndex(subject.ID) - 1;
							if( playerRank > subjectRank )
								return "§cCannot Promote Above Yourself!";
							else if( playerRank == subjectRank && player.getRank().promoteSameRank == false )
								return "§cCannot Promote To Your Rank!";
						}
					}
					faction.addPlayer(subject.ID, faction.getHigherRank(subject.ID));
					NFactionList.add(faction);
					return "§cTarget Promoted!";
				}
				else
					return "§cTarget Isn't In A Faction!";
			}
			else
				return "§cTarget Doesn't Exist!";
		}
		else
			return "§cNo Argument Received";
	}
	
	private String demote(CommandSender sender, String[] args)
	{
		if(args[1].length()>1)
		{
			NPlayer subject = NPlayerList.get(args[1]);
			if(subject != null)
			{
				if(subject.faction != null)
				{
					NFaction faction = subject.getFaction();
					if(sender instanceof Player)
					{
						NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
						if(player.ID.equals(subject.ID))
							return "§cYou Can't Demote Yourself!";
						else if(player.faction.equals(subject.faction) == false)
							return "§cTarget is not in your faction!";
						else if( player.getRank().demote == false )
							return "§cNo Demotion Permissions!";
						else
						{
							int playerRank = faction.getRankIndex(player.ID);
							int subjectRank = faction.getRankIndex(subject.ID);
							if( playerRank > subjectRank )
								return "§cCannot Demote Higher Ranks!";
							else if( playerRank == subjectRank && player.getRank().demoteSameRank == false )
								return "§cCannot Demote From Your Rank!";
						}
					}
					faction.addPlayer(subject.ID, faction.getLowerRank(subject.ID));
					NFactionList.add(faction);
					return "§cTarget Demoted!";
				}
				else
					return "§cTarget Isn't In A Faction!";
			}
			else
				return "§cTarget Doesn't Exist!";
		}
		else
			return "§cNo Argument Received";
	}
	
	private String ally(CommandSender sender, String[] args)
	{
		if(args[1].length()>1)
		{
			NFaction subject = NFactionList.get(args[1]);
			if( subject != null )
			{
				if(sender instanceof Player)
				{
					NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
					if(player.faction != null)
					{
						NFaction faction = player.getFaction();
						if( player.getRank().relate )
						{
							NRelation relation = faction.getRelation(subject.ID);
							if( relation == null )
							{
								relation = new NRelation(faction.ID,subject.ID);
								//TODO allyshit
							}
							else
							{
								NRelation pend = new NRelation(faction.ID,subject.ID);
								//TODO allyshit
								relation.addPending(pend);
							}
							NRelationList.add(relation);
							return "§cDesired Relation Set To Ally!";
						}
						else
							return "§cNo Permissions to Relate!";
					}
					else
						return "§cYou're Not In A Faction!";
				}
				else
					//consoleshit
					return "";
			}	
			else
				return "§cFaction Doesn't Exist!";
		}
		else
			return "§cNo Argument Received";
	}
	
	private String war(CommandSender sender, String[] args)
	{
		if(args[1].length()>1)
		{
			NFaction subject = NFactionList.get(args[1]);
			if( subject != null )
			{
				if(sender instanceof Player)
				{
					NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
					if(player.faction != null)
					{
						NFaction faction = player.getFaction();
						if( player.getRank().relate )
						{
							NRelation relation = faction.getRelation(subject.ID);
							if( relation == null )
							{
								relation = new NRelation(faction.ID,subject.ID);
								//TODO warshit
							}
							else
							{
								NRelation pend = new NRelation(faction.ID,subject.ID);
								//TODO warshit
								relation.addPending(pend);
							}
							NRelationList.add(relation);
							return "§cDesired Relation Set To War!";
						}
						else
							return "§cNo Permissions to Relate!";
					}
					else
						return "§cYou're Not In A Faction!";
				}
				else
					//consoleshit
					return "";
			}	
			else
				return "§cFaction Doesn't Exist!";
		}
		else
			return "§cNo Argument Received";
	}
	
	private String invite(CommandSender sender, String[] args)
	{
		if(args[1].length()>1)
		{
			NPlayer subject = NPlayerList.get(args[1]);
			if(subject != null)
			{
				if(subject.faction == null)
				{
					if(sender instanceof Player)
					{
						NFaction faction = subject.getFaction();
						NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
						if(player.faction != null)
						{
							if( player.getRank().invite )
							{
								if(faction.isInvited(subject.ID))
									faction.deleteInvite(subject.ID);
								else
									faction.addInvite(subject.ID);
								NFactionList.add(faction);
								return "§cTarget Invited!";
							}
							else
								return "§cNo Permissions to Invite!";
						}
						else
							return "§cYou're Not In A Faction";
					}
					else
						//consoleshit
						return "";
				}
				else
					return "§cTarget Is Already In A Faction!";
			}
			else
				return "§cTarget Doesn't Exist!";
		}
		else
			return "§cNo Argument Received";
	}
	
	private String close(CommandSender sender, String[] args)
	{
		if(sender instanceof Player)
		{
			NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
			NFaction faction = player.getFaction();
			if(player.faction != null)
			{
				if(!faction.open)
					return "§6Faction Already Closed!";
				else if( player.getRank().close )
				{
					faction.open = false;
					NFactionList.add(faction);
					return "§6Faction Closed!";
				}
				else
					return "§cNo Permissions to Close!";
			}
			else
				return "§cYou're Not In A Faction";
		}
		else
			//consoleshit
			return "";
	}
	
	private String open(CommandSender sender, String[] args)
	{
		if(sender instanceof Player)
		{
			NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
			NFaction faction = player.getFaction();
			if(player.faction != null)
			{
				if(faction.open)
					return "§6Faction Already Opened!";
				else if( player.getRank().open )
				{
					faction.open = true;
					NFactionList.add(faction);
					return "§6Faction Opened!";
				}
				else
					return "§cNo Permissions to Open!";
			}
			else
				return "§cYou're Not In A Faction";
		}
		else
			//consoleshit
			return "";
	}
	
	private void flushFaction( NFaction doomed )
	{
		NFaction faction;
		NPlayer player;
		NNode node;
		
		Iterator<UUID> iter = doomed.getNodeIter();
		while(iter.hasNext())
		{
			node = NNodeList.get(iter.next());
			node.faction = null;
			NNodeList.add(node);
		}
		
		iter = doomed.getPlayerIter();
		while(iter.hasNext())
		{
			player = NPlayerList.get(iter.next());
			player.faction = null;
			NPlayerList.add(player);
		}
		
		iter = doomed.getRelateFactionIter();
		while(iter.hasNext())
		{
			faction = NFactionList.get(iter.next());
			faction.deleteRelation(doomed.ID);
			NFactionList.add(faction);
		}
		
		iter = doomed.getRelationIter();
		while(iter.hasNext())
		{
			NRelationList.delete(iter.next());
		}
		
		NFactionList.delete(doomed.ID);
	}
	
	private void flushRelation( UUID ID )
	{
		NRelation doomed = NRelationList.get(ID);
		NFaction faction = NFactionList.get(doomed.juniorID);
		faction.deleteRelation(doomed.seniorID);
		NFactionList.add(faction);
		faction = NFactionList.get(doomed.seniorID);
		faction.deleteRelation(doomed.juniorID);
		NFactionList.add(faction);
		NRelationList.delete(ID);
	}
}