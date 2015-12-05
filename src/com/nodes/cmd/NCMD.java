package com.nodes.cmd;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nodes.nodes;
import com.nodes.data.NConfig;
import com.nodes.data.NDataIO;
import com.nodes.data.NFaction;
import com.nodes.data.NFactionList;
import com.nodes.data.NNode;
import com.nodes.data.NNodeList;
import com.nodes.data.NPlayer;
import com.nodes.data.NPlayerList;
import com.nodes.data.NRelation;
import com.nodes.data.NRelationList;
import com.nodes.data.NResource;
import com.nodes.data.NResourceList;

public class NCMD implements CommandExecutor
{
	private final nodes plugin;
	
	public NCMD(nodes plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(!((cmd.getName().equalsIgnoreCase("no") || cmd.getName().equalsIgnoreCase("node"))));
		else if(!sender.hasPermission("nodes.no"))
			sender.sendMessage("§6Use /no help to receive no help.");
		else if(args.length<1)
			sender.sendMessage("§6Use /no help to receive no help.");
		else
		{
			String switcher = args[0].toLowerCase();
			String result = null;
			switch (switcher)
			{
				case "info":	result = info(sender, args); break;
				case "list":	result = list(sender, args); break;
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
				case "save":	result = save(sender, args); break;
				case "help":	return false;
				default: result = "§cNodes has received an invalid command input.";
			}
			if(sender instanceof Player)
				sender.sendMessage(result);
			else
				Bukkit.getLogger().info(result);
		}
		return true;
	}


	private String save(CommandSender sender, String[] args)
	{
		if(!(sender instanceof Player) || sender.isOp())
		{
			NDataIO.save();
			return "§6Saved";
		}
		else
			return "§cInvalid Permissions.";
	}


	private String desc(CommandSender sender, String[] args)
	{
		NFaction faction;
		int descIter = 0;
		if(sender instanceof Player)
		{
			NPlayer player = NPlayerList.i.get(((Player)sender).getUniqueId());
			if(player.faction == null)
				return "§cYou're not in a faction!";
			if(!player.getRank().name)
				return "§cNo Permissions to Rename";
			faction = player.getFaction();
		}
		else
		{
			if(args[1].length()<1)
				return "§cNo Faction Received";
			faction = NFactionList.i.get(args[1]);
			if(faction == null)
				return "§cFaction Does Not Exist!";
			descIter++;
		}
		if(args[descIter].length()<1)
			return "§cNo Description Received";

		String desc = "";
		for(++descIter; descIter < args.length ; descIter++)
			desc.concat(' ' + args[descIter]);
		faction.description = desc;
		NFactionList.i.add(faction);
		return "§6Faction Description changed";
	}



	private String name(CommandSender sender, String[] args)
	{
		NFaction faction;
		String name;

		if(sender instanceof Player)
		{
			if(args[1].length()<1)
				return "§cNo Name Received";
			name = args[1];
			NPlayer player = NPlayerList.i.get(((Player)sender).getUniqueId());
			if(player.faction == null)
				return "§cYou're not in a faction!";
			if(!player.getRank().name)
				return "§cNo Permissions to Rename";
			faction = player.getFaction();
		}
		else
		{
			if(args[1].length()<1)
				return "§cNo Faction Received";
			faction = NFactionList.i.get(args[1]);
			if(faction == null)
				return "§cFaction Does Not Exist!";

			if( args[2].length()<1)
				return "§cNo Name Received";
			name = args[2];
		}

		if(NFactionList.i.contains(name))
			return "§cFaction Name Taken!";

		faction.name = name;
		NFactionList.i.add(faction);
		return "§6Faction Renamed to " + args[1];
	}



	private String sethome(CommandSender sender, String[] args)
	{
		if(!(sender instanceof Player))
			return "§cNot a console available command";
		NPlayer player = NPlayerList.i.get(((Player)sender).getUniqueId());
		NFaction faction = player.getFaction();
		if( faction == null )
			return "§cYou're not in a faction!";
		if( !player.getRank().setHome )
			return "§cNo Permission to Set Home!";

		NNode node = player.getNode();
		if(!node.faction.equals(faction.ID))
			return "§cNode not Owned by your Faction!";
		if(!node.isEmbedded() && NConfig.i.HomeEmbeddedOnly)
			return "§cNode not Embedded!";

		faction.home = ((Player)sender).getLocation();
		NFactionList.i.add(faction);
		return "§6Home Set!";
	}



	private String home(CommandSender sender, String[] args)
	{
		if(!(sender instanceof Player))
			return "§cNot a console available command";
		NPlayer player = NPlayerList.i.get(((Player)sender).getUniqueId());
		NFaction faction = player.getFaction();
		if( faction == null )
			return "§cYou're not in a faction!";
		if( !player.getRank().home )
			return "§cNo Permission to Teleport Home!";
		if( faction.home == null )
			return "§cHome Never Set!";
		Location homeLoc = faction.home;

		NNode node = player.getNode();

		if(node.faction == null && !NConfig.i.HomeFromWild)
			return "§cCannot Teleport out of Wilderness";
		if(node.faction.equals(faction.ID))
			if(node.isEmbedded() && !NConfig.i.HomeFromEmbedded )
				return "§cCannot Teleport out of Embedded Nodes";
			else if(!node.isEmbedded() && !NConfig.i.HomeFromExposed)
				return "§cCannot Teleport out of Exposed Nodes";

		NRelation relate = player.getNode().getFaction().getRelation(player.faction);
		if(relate.ally && !NConfig.i.HomeFromAlly)
			return "§cCannot Teleport out of Allied Nodes";
		if(relate.neutral && !NConfig.i.HomeFromNeutral)
			return "§cCannot Teleport out of Neutral Nodes";
		if(relate.enemy && !NConfig.i.HomeFromEnemy)
			return "§cCannot Teleport out of Enemy Nodes";

		Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(nodes.plugin, new Runnable()
		{ public void run() { ((Player)sender).teleport(homeLoc); } }, NConfig.i.HomeTeleportDelay*20L );
		return "§6Teleporting in " + NConfig.i.HomeTeleportDelay + "...";
	}



	private String map(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}


	private String list(CommandSender sender, String[] args)
	{
		NFaction sendFaction = null;
		String assemble = "";
		String mode;
		Set<String> modifier = new HashSet<String>();
		if(args[1].length()<1)
			mode = "";
		else
		{
			mode = args[1].toLowerCase();
			for(int i = 2; i<args.length;i++)
				if(args[i].length()>=1)
					modifier.add(args[i].toLowerCase());
		}
		if(sender instanceof Player)
			sendFaction = NPlayerList.i.get(((Player)sender).getUniqueId()).getFaction();
		List<UUID> sorted;
		switch(mode)
		{
			case "players": case "player":
			{
				NPlayer nPlayer;
				Player player;
				
				sorted = NPlayerList.i.players();
				for(int i = 2; i<args.length;i++)
					if(args[i].length()>=1)
						modifier.add(args[i].toLowerCase());
				for(UUID PID : sorted)
				{
					nPlayer = NPlayerList.i.get(PID);
					if(nPlayer == null)
						continue;
					player = Bukkit.getPlayer(PID);
					if(modifier.contains("online") && !player.isOnline())
						sorted.remove(PID);
					else if(modifier.contains("offline") && player.isOnline())
						sorted.remove(PID);
					if(modifier.contains("wild") && nPlayer.getFaction() != null)
						sorted.remove(PID);
				}
				for(UUID PID : sorted)
				{
					nPlayer = NPlayerList.i.get(PID);
					if(nPlayer == null)
						continue;
					if(sendFaction != null)
						assemble += sendFaction.getRelationColor(nPlayer.faction);
					assemble += nPlayer.name + "§6, ";
				}
				break;
			}
			case "nodes": case "node":
			{
				break;
			}
			case "resources": case "resource":
			{

				break;
			}
			case "factions": case "faction":
			{

				break;
			}
			default:
		}
		return assemble;
	}


	private String info(CommandSender sender, String[] args)
	{
		if(args[1].length()<1)
			return "§cNo Argument Received";

		if(args[2].length()>=1)
		{
			switch (args[1])
			{
				case "faction":	return infoFaction(sender,args,2);
				case "player":	return infoPlayer(sender,args,2);
				case "resource":return infoResource(sender,args,2);
				case "node":	return infoNode(sender,args,2);
				case "relation":return infoRelation(sender,args,2);
				case "rank":	return infoRank(sender,args,2);
			}
		}

		if(NFactionList.i.contains(args[1]))
			return infoFaction(sender,args,1);
		if(NPlayerList.i.contains(args[1]))
			return infoPlayer(sender,args,1);
		if(NNodeList.i.contains(args[1]))
			return infoNode(sender,args,1);
		if(NResourceList.i.contains(args[1]))
			return infoResource(sender,args,1);
		NFaction faction = NPlayerList.i.get(((Player)sender).getUniqueId()).getFaction();
		if(sender instanceof Player	&& faction != null && faction.hasRank(args[1]))
			return infoRank(sender,args,1);

		return "§cInvalid Argument";
	}


	private String infoFaction(CommandSender sender, String[] args, int entry)
	{
		int i = entry;
		NFaction faction = NFactionList.i.get(args[i]);

		if(faction == null)
			return "§cFaction Doesn't Exist";

		ChatColor pRel;
		if(sender instanceof Player)
			pRel = faction.getRelationColor(NPlayerList.i.get(((Player)sender).getUniqueId()).faction);
		else
			pRel = NConfig.i.UnrelateColor;

		String assemble = "§6---- "+pRel+faction.name+"§6 ----\n§6" + faction.description + "\n";
		if(faction.peaceful)
			assemble += "§6This faction is a safezone.\n";
		if(faction.safezone)
			assemble += "§6This faction is peaceful.\n";
		if(faction.warzone)
			assemble += "§cThis faction is a warzone.\n";

		NNode node = faction.getCapital();
		if(node == null)
			assemble += "§6Faction has no Capital";
		else
			assemble += "§6Capital in Node " + node.name;
		
		assemble += ".\n§6Nodes Owned: " + faction.nodes.size() + "\n"+NConfig.i.AlliedColor;
		for(UUID FID : faction.allySort())
			assemble += NFactionList.i.get(FID).name + ", ";
		assemble += "\n"+NConfig.i.NeutralColor;
		for(UUID FID : faction.neutralSort())
			assemble += NFactionList.i.get(FID).name + ", ";
		assemble += "\n"+NConfig.i.EnemyColor;
		for(UUID FID : faction.enemySort())
			assemble += NFactionList.i.get(FID).name + ", ";

		assemble += "\n"+NConfig.i.AlliedColor + "Online: ";
		for(UUID PID : faction.playersOnline())
			assemble += NPlayerList.i.get(PID).name + ", ";
		assemble += "\n"+NConfig.i.EnemyColor + "Offline: ";
		for(UUID PID : faction.playersOffline())
			assemble += NPlayerList.i.get(PID).name + ", ";
		assemble += "\n§6Nodes: " + node != null ? node.name + " " : "";
		for(UUID NID : faction.nodeSort())
			assemble += NNodeList.i.get(NID).name + ", ";
		return assemble;
	}


	private String infoPlayer(CommandSender sender, String[] args, int entry)
	{
		NPlayer player = NPlayerList.i.get(args[entry]);
		if(player == null)
			return "§cPlayer Doesn't Exist";
		String assemble = "";
		ChatColor pRel;
		if(sender instanceof Player && player.getFaction() != null)
			pRel = player.getFaction().getRelationColor(NPlayerList.i.get(((Player)sender).getUniqueId()).faction);
		else
			pRel = NConfig.i.UnrelateColor;
		if(player.getFaction() == null)
			assemble += "§6---- "+pRel+player.name+"§6 ----\n§6Not in a Faction.\n";
		else
			assemble += "§6---- "+pRel+player.title+" "+player.name+"§6 ----\n§6" + player.getRank().rankName + " of " + player.getFaction().name + ".\n";
		assemble += "§6Currently ";
		if(Bukkit.getPlayer(player.ID).isOnline())
			assemble += NConfig.i.AlliedColor + "Online\n";
		else
			assemble += NConfig.i.EnemyColor + "Offline\n";
		assemble += "§6" + player.kills + " players killed; " + player.deaths + " deaths.";

		return assemble;
	}


	private String infoNode(CommandSender sender, String[] args, int entry)
	{
		NNode node = NNodeList.i.get(args[entry]);
		if(node == null)
			return "§cNode Doesn't Exist";

		ChatColor pRel;
		if(sender instanceof Player && node.getFaction() != null)
			pRel = node.getFaction().getRelationColor(NPlayerList.i.get(((Player)sender).getUniqueId()).faction);
		else
			pRel = NConfig.i.UnrelateColor;

		String assemble = "§6---- "+pRel+node.name+"§6 ----\n§6";
		if( node.getFaction() == null )
			assemble += "Currently Unowned.\n";
		else if( node.capital )
			assemble += "Capital of " + pRel + node.getFaction().name + "\n";
		else
			assemble += "Owned by " + pRel + node.getFaction().name + "\n";
		if( node.coreActive )
			assemble += "§c"+new DecimalFormat("0.#").format(node.capPercent)+"% captured!\n";

		NResource resource;
		for(UUID RID : node.resources)
		{
			resource = NResourceList.i.get(RID);
			if(resource != null)
				assemble+="§6"+resource.name+" ; Time Remaining Until Next Cycle: "+((System.currentTimeMillis()-NConfig.i.firstActiveMillis)/60000)%resource.cycleTimeMinutes+"m";

		}
		return assemble;
	}


	private String infoResource(CommandSender sender, String[] args, int entry)
	{
		NResource resource = NResourceList.i.get(args[entry]);
		NNode node;
		if(resource == null)
			return "§cResource Doesn't Exist";

		String assemble="§6---- "+resource.name+"§6 ----\n§6Cycle Length: " + resource.cycleTimeMinutes + "m\n§6Time Remaining Until Next Cycle: "+((System.currentTimeMillis()-NConfig.i.firstActiveMillis)/60000)%resource.cycleTimeMinutes+"m\n";
		for(Entry<Material,Integer> set : resource.materialMap.entrySet())
			assemble += "§6"+ set.getValue() + " " + set.getKey().toString() + "'s\n";

		assemble += "§6Nodes: ";
		for(UUID NID : resource.nodeSet)
		{
			node = NNodeList.i.get(NID);
			if(node != null)
				assemble += node.name + ", ";
		}
		return assemble;
	}


	private String infoRank(CommandSender sender, String[] args, int entry)
	{
		return null;
	}


	private String infoRelation(CommandSender sender, String[] args, int entry)
	{
		return null;
	}


	private String modify(CommandSender sender, String[] args)
	{
		if(args[1].length()<1)
			return "§cNo Argument Received";
		if(args[1].equalsIgnoreCase("relation"));
		{

		}
		if(args[1].equalsIgnoreCase("rank"));
		{

		}

		return "§cInvalid Argument";
	}


	private String delete(CommandSender sender, String[] args)
	{
		NFaction faction;
		if(args[1].length()<1 || !args[1].equalsIgnoreCase("yes"))
			return "§cPlease Enter Confirmation: '/no delete yes'";
		if(sender instanceof Player)
		{
			NPlayer player = NPlayerList.i.get(((Player)sender).getUniqueId());
			faction = player.getFaction();
			if(faction == null)
				return "§cYou're not in a faction!";
			if(!player.getRank().delete)
				return "§cNo Permissions to Delete";
		}
		else
		{
			if(args[2].length()<1)
				return "§cNo Faction Received";
			faction = NFactionList.i.get(args[2]);
			if(faction == null)
				return "§cFaction Does Not Exist!";
		}
		faction.delete();
		return "§6Faction Deleted!";
	}


	private String join(CommandSender sender, String[] args)
	{
		NPlayer player;
		Boolean console = false;
		if(args[1].length()<1)
			return "§cNo Faction Received";
		NFaction faction = NFactionList.i.get(args[1]);
		if(faction == null)
			return "§cFaction Does Not Exist!";
		String complete = "§6Welcome to "+faction.name+"!";
		if(sender instanceof Player)
			player = NPlayerList.i.get(((Player)sender).getUniqueId());
		else
		{
			console = true;
			complete = "§6User added to "+faction.name+"!";
			if(args[2].length()<1)
				return "§cNo Player Received";
			player = NPlayerList.i.get(args[2]);
			if(player == null)
				return "§cPlayer Does Not Exist!";
		}
		if(player.faction != null)
			return "§cYou're already in a faction!";
		if(!(faction.open || console || faction.isInvited(player.ID)))
		{
			return "§cFaction Is Closed!";
		}
		faction.addPlayer(player.ID);
		player.faction = faction.ID;
		if(faction.isInvited(player.ID))
			faction.deleteInvite(player.ID);
		NFactionList.i.add(faction);
		NPlayerList.i.add(player);
		return complete;
	}


	private String leave(CommandSender sender, String[] args)
	{
		String complete = null;
		NPlayer player;
		NFaction faction;
		if(sender instanceof Player)
		{
			player = NPlayerList.i.get(((Player)sender).getUniqueId());
			if(player.faction == null)
				return "§cYou're Not In A Faction!";
			faction = NFactionList.i.get(player.faction);
			complete = "§6Success!";
		}
		else
		{
			if(args[2].length()<1)
				return "§cNo Player Received";
			player = NPlayerList.i.get(args[2]);
			if(player == null)
				return "§cPlayer Does Not Exist!";
			if(player.faction == null)
				return "§cPlayer Not In A Faction!";
			faction = NFactionList.i.get(player.faction);
			complete = "§6User removed from "+faction.name+"!";
		}
		player.faction = null;
		NPlayerList.i.add(player);
		if(faction.isLastPlayer())
		{
			faction.delete();
			return complete += " The Empty Faction Has Been Deleted!";
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
				complete += " "+NPlayerList.i.get(list.get(rand)).name+" is now leader!";
			}
		}
		faction.deletePlayer(player.ID);
		NFactionList.i.add(faction);
		return complete;
	}



	private String create(CommandSender sender, String[] args)
	{
		NPlayer player;
		NFaction faction;

		if(args[1].length()<1)
			return "§cNo Argument Received";
		if(NFactionList.i.contains(args[1]))
			return "§cFaction's Name Has Already Been Taken!";

		faction = new NFaction(args[1]);

		if(sender instanceof Player)
		{
			player = NPlayerList.i.get(((Player)sender).getUniqueId());
			if(player.getFaction() != null)
				return "§cYou're already in a Faction!";
			player.faction = faction.ID;
			NPlayerList.i.add(player);
		}

		NFactionList.i.add(faction);
		return "§6Faction Has Been Created!";
	}



	private String kick(CommandSender sender, String[] args)
	{
		if (args[1].length() < 1)
			return "§cNo Argument Received";
		NPlayer subject = NPlayerList.i.get(args[1]);
		if (subject == null)
			return "§cTarget Doesn't Exist!";
		NFaction faction = subject.getFaction();
		if (faction == null)
			return "§cTarget Isn't In A Faction!";
		if (sender instanceof Player)
		{
			NPlayer player = NPlayerList.i.get(((Player)sender).getUniqueId());
			if(player.getFaction() == null)
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

		NFactionList.i.add(faction);
		NPlayerList.i.add(subject);
		return "§6Target has been Kicked!";
	}



	private String promote(CommandSender sender, String[] args)
	{
		if(args[1].length()<1)
			return "§cNo Argument Received";
		NPlayer subject = NPlayerList.i.get(args[1]);
		if(subject == null)
			return "§cTarget Doesn't Exist!";
		NFaction faction = subject.getFaction();
		if(faction == null)
			return "§cTarget Isn't In A Faction!";
		if(sender instanceof Player)
		{
			NPlayer player = NPlayerList.i.get(((Player)sender).getUniqueId());
			if(player.getFaction() == null)
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
		NFactionList.i.add(faction);
		return "§6Target Promoted!";
	}

	private String demote(CommandSender sender, String[] args)
	{
		if(args[1].length()<1)
			return "§cNo Argument Received";
		NPlayer subject = NPlayerList.i.get(args[1]);
		if(subject == null)
			return "§cTarget Doesn't Exist!";
		NFaction faction = subject.getFaction();
		if(faction == null)
			return "§cTarget Isn't In A Faction!";
		if(sender instanceof Player)
		{
			NPlayer player = NPlayerList.i.get(((Player)sender).getUniqueId());
			if(player.getFaction() == null)
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
		NFactionList.i.add(faction);
		return "§6Target Demoted!";
	}

	private String ally(CommandSender sender, String[] args)
	{
		if(args[1].length()<1)
			return "§cNo Argument Received";
		NFaction subject = NFactionList.i.get(args[1]);
		if( subject == null )
			return "§cFaction Doesn't Exist!";
		NFaction faction;
		if(sender instanceof Player)
		{
			NPlayer player = NPlayerList.i.get(((Player)sender).getUniqueId());
			faction = player.getFaction();
			if( faction == null )
				return "§cYou're Not In A Faction!";
			if( !player.getRank().relate )
				return "§cNo Permissions to Relate!";
		}
		else
		{
			if(args[2].length()<1)
				return "§cNo Second Faction Received";
			faction = subject;
			subject = NFactionList.i.get(args[2]);
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
		NRelationList.i.add(relation);
		return "§6Desired Relation Set To Ally!";
	}

	private String war(CommandSender sender, String[] args)
	{
		if(args[1].length()<1)
			return "§cNo Argument Received";
		NFaction subject = NFactionList.i.get(args[1]);
		if( subject == null )
			return "§cFaction Doesn't Exist!";
		NFaction faction;
		if(sender instanceof Player)
		{
			NPlayer player = NPlayerList.i.get(((Player)sender).getUniqueId());
			faction = player.getFaction();
			if( faction == null )
				return "§cYou're Not In A Faction!";
			if( !player.getRank().relate )
				return "§cNo Permissions to Relate!";
		}
		else
		{
			if(args[2].length()<1)
				return "§cNo Second Faction Received";
			faction = subject;
			subject = NFactionList.i.get(args[2]);
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
		NRelationList.i.add(relation);
		return "§6Desired Relation Set To War!";
	}

	private String invite(CommandSender sender, String[] args)
	{

		if(args[1].length()<1)
			return "§cNo Argument Received";

		NPlayer subject = NPlayerList.i.get(args[1]);
		if(subject == null)
			return "§cTarget Doesn't Exist!";
		if(subject.getFaction() != null)
			return "§cTarget Is Already In A Faction!";
		NFaction faction;
		if(sender instanceof Player)
		{
			NPlayer player = NPlayerList.i.get(((Player)sender).getUniqueId());
			faction = player.getFaction();
			if(faction == null)
				return "§cYou're Not In A Faction";
			if( !player.getRank().invite )
				return "§cNo Permissions to Invite!";
		}
		else
		{
			if(args[2].length()<1)
				return "§cNo Faction Received";
			faction = NFactionList.i.get(args[2]);
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
		NFactionList.i.add(faction);
		return complete;


	}

	private String close(CommandSender sender, String[] args)
	{
		NFaction faction;
		if(sender instanceof Player)
		{
			NPlayer player = NPlayerList.i.get(((Player)sender).getUniqueId());
			faction = player.getFaction();
			if( faction == null )
				return "§cYou're Not In A Faction";
			if( !player.getRank().close )
				return "§cNo Permissions to Close!";
		}
		else
		{
			if(args[2].length()<1)
				return "§cNo Faction Received";
			faction = NFactionList.i.get(args[2]);
			if( faction == null )
				return "§cFaction Doesn't Exist!";
		}
		if(!faction.open)
			return "§6Faction Already Closed!";
		faction.open = false;
		NFactionList.i.add(faction);
		return "§6Faction Closed!";
	}

	private String open(CommandSender sender, String[] args)
	{
		NFaction faction;
		if(sender instanceof Player)
		{
			NPlayer player = NPlayerList.i.get(((Player)sender).getUniqueId());
			faction = player.getFaction();
			if( faction == null )
				return "§cYou're Not In A Faction";
			if( !player.getRank().open )
				return "§cNo Permissions to Open!";
		}
		else
		{
			if(args[2].length()<1)
				return "§cNo Faction Received";
			faction = NFactionList.i.get(args[2]);
			if( faction == null )
				return "§cFaction Doesn't Exist!";
		}
		if(faction.open)
			return "§6Faction Already Open!";
		faction.open = true;
		NFactionList.i.add(faction);
		return "§6Faction Opened!";
	}
}