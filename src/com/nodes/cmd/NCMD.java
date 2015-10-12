package com.nodes.cmd;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nodes.nodes;
import com.nodes.data.NFaction;
import com.nodes.data.NFactionList;
import com.nodes.data.NPlayer;
import com.nodes.data.NPlayerList;

import net.minecraft.server.v1_8_R3.PlayerList;

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
    					case "join": return join(sender, args);
    					case "leave": return leave(sender, args);
    					case "create": return create(sender, args);
    					case "kick": return kick(sender, args);
    					case "promote": return promote(sender, args);
    					case "demote": return demote(sender, args);
    					case "ally": return ally(sender, args);
    					case "war": return war(sender, args);
    					case "invite": return invite(sender, args);
    					case "close": return close(sender, args);
    					case "open": return open(sender, args);
    					case "info": return info(sender, args);
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
					if(faction.isInvited(player.getID()) || faction.getOpen())
					{
						faction.addPlayer(player.getID());
						player.setFaction(faction.getID());
						if(faction.isInvited(player.getID()))
							faction.deleteInvite(player.getID());
						
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
			if(player.getFaction() != null)
			{
				NFaction faction = NFactionList.get(player.getFaction());
				faction.deletePlayer(player.getID());
				player.setFaction(null);
						
				NFactionList.add(faction);
				NPlayerList.add(player);
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
				NFaction faction = new NFaction(args[1]);
				if(sender instanceof Player)
				{
					NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
					player.setFaction(faction.getID());
					faction.addPlayer(player.getID());
					NPlayerList.add(player);
				}
				NFactionList.add(faction);
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
				if(subject.getFaction() != null)
				{
					NFaction faction = NFactionList.get(subject.getFaction());
					if(sender instanceof Player)
					{
						NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
						if(player.getFaction().equals(subject.getFaction()) == false)
						{
							//not same faction
							return false;
						}
						else //if() TODO: RANKCHECK
						{
							//return false;
						}
					}
					faction.deletePlayer(subject.getID());
					subject.setFaction(null);
					
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
		return false;
	}
	
	private boolean demote(CommandSender sender, String[] args)
	{
		return false;
	}
	
	private boolean ally(CommandSender sender, String[] args)
	{
		return false;
	}
	
	private boolean war(CommandSender sender, String[] args)
	{
		return false;
	}
	
	private boolean invite(CommandSender sender, String[] args)
	{
		return false;
	}
	
	private boolean close(CommandSender sender, String[] args)
	{
		return false;
	}
	
	private boolean open(CommandSender sender, String[] args)
	{
		return false;
	}
	
	private boolean info(CommandSender sender, String[] args)
	{
		return false;
	}
}