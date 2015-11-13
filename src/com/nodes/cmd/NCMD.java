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
    				switch (args[0])
    				{
    					case "join":	return join(sender, args);
    					case "leave":	return leave(sender, args);
    					case "create":	return create(sender, args);
    					case "kick":	return kick(sender, args);
    					case "promote":	return promote(sender, args);
    					case "demote":	return demote(sender, args);
    					case "ally":	return ally(sender, args);
    					case "war":		return war(sender, args);
    					case "invite":	return invite(sender, args);
    					case "close":	return close(sender, args);
    					case "open":	return open(sender, args);
    					case "info":	return info(sender, args);
    				}
    				
    				if(args[0].equalsIgnoreCase("claim"))
    				{
    					//return tryClaim(sender);
    				}
    			}
    		}
    		else
    		{
    			sender.sendMessage("§cNodes has received an invalid command input.");
    		}
    	}  
    	return true; 
    }
	
	
	
	private boolean join(CommandSender sender, String[] args)
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
						return true;
					}
					else
					{
						//no perms
					}
				}
				else
				{
					//faction not exist
				}
			}
			else
			{
				//console
			}
		}
		else
		{
			//no faction
		}
		return false;
	}
	
	
	
	private boolean leave(CommandSender sender, String[] args)
	{
		if(sender instanceof Player)
		{
			NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
			if(player.faction != null)
			{
				NFaction faction = NFactionList.get(player.faction);
				player.faction = null;
				NPlayerList.add(player);
				if(faction.isLastPlayer())
					flushFaction(faction);
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
					    faction.addPlayer( list.get( (int)(Math.random()*list.size()-1)), faction.getRankID(pRank) );
					}
				}
				faction.deletePlayer(player.ID);
				NFactionList.add(faction);
				return true;
			}
			else
			{
				//not in faction
			}
		}
		else
		{
			//console
		}
		return false;
	}
	
	
	
	private boolean create(CommandSender sender, String[] args)
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
				}
			}
			else
			{
				//name taken
			}
		}
		else
		{
			//no faction
		}
		return false;
	}
	
	
	
	private boolean kick(CommandSender sender, String[] args)
	{
		if(args[1].length()>1)
		{
			NPlayer subject = NPlayerList.get(args[1]);
			if(subject != null)
			{
				if(subject.faction != null)
				{
					NFaction faction = NFactionList.get(subject.faction);
					if(sender instanceof Player)
					{
						NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
						if(player.faction.equals(subject.faction) == false)
						{
							//not same faction
							return false;
						}
						else
						{
							if( faction.getRank(player.ID).kick == false )
							{
								//no kick perms
								return false;
							}
							else
							{
								int playerRank = faction.getRankIndex(player.ID);
								int subjectRank = faction.getRankIndex(subject.ID);
								if( playerRank > subjectRank )
								{
									//can't kick higher rank
									return false;
								}
								else
								{
									if( playerRank == subjectRank && faction.getRank(player.ID).kickSameRank == false )
									{
										//can't kick same rank
										return false;
									}
								}
							}
						}
					}
					faction.deletePlayer(subject.ID);
					subject.faction = null;
					
					NFactionList.add(faction);
					NPlayerList.add(subject);
				}
				else
				{
					//player not in faction
				}
			}
			else
			{
				//player doesn't exist
			}
		}
		else
		{
			//no player
		}
		return false;
	}
	
	
	
	private boolean promote(CommandSender sender, String[] args)
	{
		if(args[1].length()>1)
		{
			NPlayer subject = NPlayerList.get(args[1]);
			if(subject != null)
			{
				if(subject.faction != null)
				{
					NFaction faction = NFactionList.get(subject.faction);
					if(sender instanceof Player)
					{
						NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
						if(player.faction.equals(subject.faction) == false)
						{
							//not same faction
							return false;
						}
						else
						{
							if( faction.getRank(player.ID).promote == false )
							{
								//no promote perms
								return false;
							}
							else
							{
								int playerRank = faction.getRankIndex(player.ID);
								int subjectRank = faction.getRankIndex(subject.ID) - 1;
								if( playerRank > subjectRank )
								{
									//can't promote to higher rank
									return false;
								}
								else
								{
									if( playerRank == subjectRank && faction.getRank(player.ID).promoteSameRank == false )
									{
										//can't promote to same rank
										return false;
									}
								}
							}
						}
					}
					faction.addPlayer(subject.ID, faction.getHigherRank(subject.ID));
					NFactionList.add(faction);
				}
				else
				{
					//player not in faction
				}
			}
			else
			{
				//player doesn't exist
			}
		}
		else
		{
			//no player
		}
		return false;
	}
	
	private boolean demote(CommandSender sender, String[] args)
	{
		if(args[1].length()>1)
		{
			NPlayer subject = NPlayerList.get(args[1]);
			if(subject != null)
			{
				if(subject.faction != null)
				{
					NFaction faction = NFactionList.get(subject.faction);
					if(sender instanceof Player)
					{
						NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
						if(player.faction.equals(subject.faction) == false)
						{
							//not same faction
							return false;
						}
						else
						{
							if( faction.getRank(player.ID).promote == false )
							{
								//no promote perms
								return false;
							}
							else
							{
								int playerRank = faction.getRankIndex(player.ID);
								int subjectRank = faction.getRankIndex(subject.ID);
								if( playerRank > subjectRank )
								{
									//can't demote higher rank
									return false;
								}
								else
								{
									if( playerRank == subjectRank && faction.getRank(player.ID).promoteSameRank == false )
									{
										//can't demote from same rank
										return false;
									}
								}
							}
						}
					}
					faction.addPlayer(subject.ID, faction.getLowerRank(subject.ID));
					NFactionList.add(faction);
				}
				else
				{
					//player not in faction
				}
			}
			else
			{
				//player doesn't exist
			}
		}
		else
		{
			//no player
		}
		return false;
	}
	
	private boolean ally(CommandSender sender, String[] args)
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
						NFaction faction = NFactionList.get(player.faction);
						if( faction.getRank(player.ID).relate )
						{
							NRelation relation = NRelationList.get(faction.getRelation(subject.ID));
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
						}
						else
						{
							//no perms
						}
					}
					else
					{
						//not in faction
					}
				}
				else
				{
					//not a player
				}
			}	
			else
			{
				//invalid args
			}
		}
		else
		{
			//no faction arg
		}
		return false;
	}
	
	private boolean war(CommandSender sender, String[] args)
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
						NFaction faction = NFactionList.get(player.faction);
						if( faction.getRank(player.ID).relate )
						{
							NRelation relation = NRelationList.get(faction.getRelation(subject.ID));
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
						}
						else
						{
							//no perms
						}
					}
					else
					{
						//not in faction
					}
				}
				else
				{
					//not a player
				}
			}	
			else
			{
				//invalid args
			}
		}
		else
		{
			//no faction arg
		}
		return false;
	}
	
	private boolean invite(CommandSender sender, String[] args)
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
						NFaction faction = NFactionList.get(subject.faction);
						NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
						if(player.faction.equals(subject.faction))
						{
							if( faction.getRank(player.ID).invite )
							{
								if(faction.isInvited(subject.ID))
									faction.deleteInvite(subject.ID);
								else
									faction.addInvite(subject.ID);
								NFactionList.add(faction);
							}
							else
							{
								//no invite perms
							}
						}
						else
						{
							//not same faction
						}
					}
					else
					{
						//not player
					}
				}
				else
				{
					//player in faction
				}
			}
			else
			{
				//player doesn't exist
			}
		}
		else
		{
			//no player
		}
		return false;
	}
	
	private boolean close(CommandSender sender, String[] args)
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
						NFaction faction = NFactionList.get(subject.faction);
						NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
						if(player.faction.equals(subject.faction))
						{
							if( faction.getRank(player.ID).close )
							{
								faction.open = false;
								NFactionList.add(faction);
							}
							else
							{
								//no close perms
							}
						}
						else
						{
							//not same faction
						}
					}
					else
					{
						//not player
					}
				}
				else
				{
					//player in faction
				}
			}
			else
			{
				//player doesn't exist
			}
		}
		else
		{
			//no player
		}
		return false;
	}
	
	private boolean open(CommandSender sender, String[] args)
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
						NFaction faction = NFactionList.get(subject.faction);
						NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
						if(player.faction.equals(subject.faction))
						{
							if( faction.getRank(player.ID).open )
							{
								faction.open = true;
								NFactionList.add(faction);
							}
							else
							{
								//no open perms
							}
						}
						else
						{
							//not same faction
						}
					}
					else
					{
						//not player
					}
				}
				else
				{
					//player in faction
				}
			}
			else
			{
				//player doesn't exist
			}
		}
		else
		{
			//no player
		}
		return false;
	}
	
	private boolean info(CommandSender sender, String[] args)
	{
		return false;
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