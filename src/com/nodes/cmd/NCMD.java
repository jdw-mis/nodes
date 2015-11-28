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
						case "info":	result = info(sender, args); break;
    					case "map":		result = map(sender, args); break;
    					case "home":	result = home(sender, args); break;
    					case "promote":	result = promote(sender, args); break;
    					case "demote":	result = demote(sender, args); break;
    					case "modify":	result = modify(sender, args); break;
    					case "kick":	result = kick(sender, args); break;
    					case "desc":	result = desc(sender, args); break;
    					case "name":	result = name(sender, args); break;
    					case "invite":	result = invite(sender, args); break;
    					case "join":	result = join(sender, args); break;
    					case "ally":	result = ally(sender, args); break;
    					case "war":		result = war(sender, args); break;
    					case "close":	result = close(sender, args); break;
    					case "open":	result = open(sender, args); break;
    					case "sethome":	result = sethome(sender, args); break;
    					case "leave":	result = leave(sender, args); break;
    					case "create":	result = create(sender, args); break;
    					case "delete":	result = delete(sender, args); break;
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
	
	
	
	private String delete(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}



	private String desc(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}



	private String name(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}



	private String sethome(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}



	private String home(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}



	private String map(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		return null;
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
		NPlayer player;
		Boolean console = false;
		if(args[1].length()<1)
			return "§cNo Faction Received";
		NFaction faction = NFactionList.get(args[1]);
		if(faction == null)
			return "§cFaction Does Not Exist!";
		String complete = "§6Welcome to "+faction.name+"!";
		if(sender instanceof Player)
			player = NPlayerList.get(((Player)sender).getUniqueId());
		else
		{
			console = true;
			complete = "§6User added to "+faction.name+"!";
			if(args[2].length()<1)
				return "§cNo Player Received";
			player = NPlayerList.get(args[2]);
			if(player == null)
				return "§cPlayer Does Not Exist!";
		}
		if(!(faction.open || console || faction.isInvited(player.ID)))
		{
			return "§cFaction Is Closed!";
		}
		faction.addPlayer(player.ID);
		player.faction = faction.ID;
		if(faction.isInvited(player.ID))
			faction.deleteInvite(player.ID);
		NFactionList.add(faction);
		NPlayerList.add(player);
		return complete;
	}
	
	
	
	private String leave(CommandSender sender, String[] args)
	{
		String complete = null;
		Boolean console = false;
		NPlayer player;
		NFaction faction;
		if(sender instanceof Player)
		{
			player = NPlayerList.get(((Player)sender).getUniqueId());
			if(player.faction == null)
				return "§cYou're Not In A Faction!";
			faction = NFactionList.get(player.faction);
			complete = "§6Success!";
		}
		else
		{
			if(args[2].length()<1)
				return "§cNo Player Received";
			player = NPlayerList.get(args[2]);
			if(player == null)
				return "§cPlayer Does Not Exist!";
			if(player.faction == null)
				return "§cPlayer Not In A Faction!";
			faction = NFactionList.get(player.faction);
			complete = "§6User removed from "+faction.name+"!";
		}
		player.faction = null;
		NPlayerList.add(player);
		if(faction.isLastPlayer())
		{
			flushFaction(faction);
			return complete += "  The Empty Faction Has Been Deleted!";
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
				complete += "  "+NPlayerList.get(list.get(rand)).name+" is now leader!";
			}
		}
		faction.deletePlayer(player.ID);
		NFactionList.add(faction);
		return complete;
	}
	
	
	
	private String create(CommandSender sender, String[] args)
	{
		NPlayer player;
		NFaction faction;
		
		if(args[1].length()<1)
			return "§cNo Argument Received";
		if(NFactionList.contains(args[1]))
			return "§cFaction's Name Has Already Been Taken!";

		faction = new NFaction(args[1]);
		
		if(sender instanceof Player)
		{
			player = NPlayerList.get(((Player)sender).getUniqueId());
			if(player.getFaction() != null)
				return "§cYou're already in a Faction!";
			player.faction = faction.ID;
			NPlayerList.add(player);
		}
		
		NFactionList.add(faction);
		return "§6Faction Has Been Created!";
	}
	
	
	
	private String kick(CommandSender sender, String[] args)
	{
		if (args[1].length() < 1)
			return "§cNo Argument Received";
		NPlayer subject = NPlayerList.get(args[1]);
		if (subject == null)
			return "§cTarget Doesn't Exist!";
		if (subject.faction == null)
			return "§cTarget Isn't In A Faction!";
		NFaction faction = subject.getFaction();
		if (sender instanceof Player)
		{
			NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
			if(player.faction == null)
				return "§cYou're Not In A Faction!";
			if (player.ID.equals(subject.ID))
				return "§cYou Can't Kick Yourself!";
			if (player.faction.equals(subject.faction) == false)
				return "§cTarget is not in your faction!";
			if (player.getRank().kick == false)
				return "§cNo Kicking Permissions!";

			int playerRank = faction.getRankIndex(player.ID);
			int subjectRank = faction.getRankIndex(subject.ID);
			if (playerRank > subjectRank)
				return "§cTarget is of a higher rank!";
			if (playerRank == subjectRank && player.getRank().kickSameRank == false)
				return "§cNo Same Rank Kick Permissions!";
		}
		faction.deletePlayer(subject.ID);
		subject.faction = null;

		NFactionList.add(faction);
		NPlayerList.add(subject);
		return "§6Target has been Kicked!";
	}
	
	
	
	private String promote(CommandSender sender, String[] args)
	{
		if(args[1].length()<1)
			return "§cNo Argument Received";
		NPlayer subject = NPlayerList.get(args[1]);
		if(subject == null)
			return "§cTarget Doesn't Exist!";
		if(subject.faction == null)
			return "§cTarget Isn't In A Faction!";
		NFaction faction = subject.getFaction();
		if(sender instanceof Player)
		{
			NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
			if(player.faction == null)
				return "§cYou're Not In A Faction!";
			if(player.ID.equals(subject.ID))
				return "§cYou Can't Promote Yourself!";
			if(player.faction.equals(subject.faction) == false)
				return "§cTarget is not in your faction!";
			if( player.getRank().promote == false )
				return "§cNo Promotion Permissions!";
			int playerRank = faction.getRankIndex(player.ID);
			int subjectRank = faction.getRankIndex(subject.ID) - 1;
			if( playerRank > subjectRank )
				return "§cCannot Promote Above Yourself!";
			if( playerRank == subjectRank && player.getRank().promoteSameRank == false )
				return "§cCannot Promote To Your Rank!";
		}
		faction.addPlayer(subject.ID, faction.getLowerRank(subject.ID));
		NFactionList.add(faction);
		return "§6Target Promoted!";
	}
	
	private String demote(CommandSender sender, String[] args)
	{
		if(args[1].length()<1)
			return "§cNo Argument Received";
		NPlayer subject = NPlayerList.get(args[1]);
		if(subject == null)
			return "§cTarget Doesn't Exist!";
		if(subject.faction == null)
			return "§cTarget Isn't In A Faction!";
		NFaction faction = subject.getFaction();
		if(sender instanceof Player)
		{
			NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
			if(player.faction == null)
				return "§cYou're Not In A Faction!";
			if(player.ID.equals(subject.ID))
				return "§cYou Can't Demote Yourself!";
			if(player.faction.equals(subject.faction) == false)
				return "§cTarget is not in your faction!";
			if( player.getRank().demote == false )
				return "§cNo Demotion Permissions!";
			int playerRank = faction.getRankIndex(player.ID);
			int subjectRank = faction.getRankIndex(subject.ID);
			if( playerRank > subjectRank )
				return "§cCannot Demote Higher Ranks!";
			if( playerRank == subjectRank && player.getRank().demoteSameRank == false )
				return "§cCannot Demote From Your Rank!";
		}
		faction.addPlayer(subject.ID, faction.getLowerRank(subject.ID));
		NFactionList.add(faction);
		return "§6Target Demoted!";
	}
	
	private String ally(CommandSender sender, String[] args)
	{
		if(args[1].length()<1)
			return "§cNo Argument Received";
		NFaction subject = NFactionList.get(args[1]);
		if( subject == null )
			return "§cFaction Doesn't Exist!";
		NFaction faction;
		if(sender instanceof Player)
		{
			NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
			if( player.faction == null )
				return "§cYou're Not In A Faction!";
			if( !player.getRank().relate )
				return "§cNo Permissions to Relate!";
			faction = player.getFaction();
		}
		else
		{
			if(args[2].length()<1)
				return "§cNo Second Faction Received";
			faction = subject;
			subject = NFactionList.get(args[2]);
			if( subject == null )
				return "§cSecond Faction Doesn't Exist!";
		}
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
		return "§6Desired Relation Set To Ally!";
	}
	
	private String war(CommandSender sender, String[] args)
	{
		if(args[1].length()<1)
			return "§cNo Argument Received";
		NFaction subject = NFactionList.get(args[1]);
		if( subject == null )
			return "§cFaction Doesn't Exist!";
		NFaction faction;
		if(sender instanceof Player)
		{
			NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
			if( player.faction == null )
				return "§cYou're Not In A Faction!";
			if( !player.getRank().relate )
				return "§cNo Permissions to Relate!";
			faction = player.getFaction();
		}
		else
		{
			if(args[2].length()<1)
				return "§cNo Second Faction Received";
			faction = subject;
			subject = NFactionList.get(args[2]);
			if( subject == null )
				return "§cSecond Faction Doesn't Exist!";
		}
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
		return "§6Desired Relation Set To War!";
	}
	
	private String invite(CommandSender sender, String[] args)
	{

		if(args[1].length()<1)
			return "§cNo Argument Received";

		NPlayer subject = NPlayerList.get(args[1]);
		if(subject == null)
			return "§cTarget Doesn't Exist!";
		if(subject.faction != null)
			return "§cTarget Is Already In A Faction!";
		NFaction faction;
		if(sender instanceof Player)
		{
			NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
			if(player.faction == null)
				return "§cYou're Not In A Faction";
			if( !player.getRank().invite )
				return "§cNo Permissions to Invite!";
			faction = player.getFaction();
		}
		else
		{
			if(args[2].length()<1)
				return "§cNo Faction Received";
			faction = NFactionList.get(args[2]);
			if( faction == null )
				return "§cFaction Doesn't Exist!";
		}
		String complete = "§6Target Invited!";
		if(faction.isInvited(subject.ID))
		{
			faction.deleteInvite(subject.ID);
			complete = "§6Target Invite Deleted!";
		}
		else
			faction.addInvite(subject.ID);
		NFactionList.add(faction);
		return complete;
			
		
	}
	
	private String close(CommandSender sender, String[] args)
	{
		NFaction faction;
		if(sender instanceof Player)
		{
			NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
			if( player.faction == null )
				return "§cYou're Not In A Faction";
			if( !player.getRank().close )
				return "§cNo Permissions to Close!";
			faction = player.getFaction();
		}
		else
		{
			if(args[2].length()<1)
				return "§cNo Faction Received";
			faction = NFactionList.get(args[2]);
			if( faction == null )
				return "§cFaction Doesn't Exist!";
		}
		if(!faction.open)
			return "§6Faction Already Closed!";
		faction.open = false;
		NFactionList.add(faction);
		return "§6Faction Closed!";
	}
	
	private String open(CommandSender sender, String[] args)
	{
		NFaction faction;
		if(sender instanceof Player)
		{
			NPlayer player = NPlayerList.get(((Player)sender).getUniqueId());
			if( player.faction == null )
				return "§cYou're Not In A Faction";
			if( !player.getRank().open )
				return "§cNo Permissions to Open!";
			faction = player.getFaction();
		}
		else
		{
			if(args[2].length()<1)
				return "§cNo Faction Received";
			faction = NFactionList.get(args[2]);
			if( faction == null )
				return "§cFaction Doesn't Exist!";
		}
		if(faction.open)
			return "§6Faction Already Open!";
		faction.open = true;
		NFactionList.add(faction);
		return "§6Faction Opened!";
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
			NRelationList.delete(iter.next());
		
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