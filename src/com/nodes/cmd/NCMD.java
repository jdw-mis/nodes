package com.nodes.cmd;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
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
import com.nodes.data.NResourceID;
import com.nodes.data.NResourceList;
import com.nodes.data.NWorld;
import com.nodes.data.NWorldList;

public class NCMD implements CommandExecutor
{

	public NCMD(){}

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
				case "relate":
				case "ally":
				case "neutral":
				case "war":		result = relate(sender, args); break;
				case "close":	result = close(sender, args); break;
				case "open":	result = open(sender, args); break;
				case "sethome":	result = sethome(sender, args); break;
				case "leave":	result = leave(sender, args); break;
				case "create":	result = create(sender, args); break;
				case "delete":	result = delete(sender, args); break;
				case "save":	result = save(sender, args); break;
				case "memes":	result = "§6THE DNA OF THE SOUL"; break;
				case "help":	return false;
				default: result = "§cNodes has received an invalid command input.";
			}
			if(sender instanceof Player)
			{
				String[] outArr = result.split("\n");
			    for (String out : outArr)
			    	sender.sendMessage(out);
			}
			else
				Bukkit.getLogger().info(result);
		}
		return true;
	}


	private String save(CommandSender sender, String[] args)
	{
		if(!(sender instanceof Player) || sender.isOp())
		{
			if(args.length>1 && args[1].equalsIgnoreCase("all"))
				NDataIO.save(true);
			else
				NDataIO.save(false);
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
			if(args.length<2)
				return "§cNo Faction Received";
			faction = NFactionList.i.get(args[1]);
			if(faction == null)
				return "§cFaction Does Not Exist!";
			descIter++;
		}
		if(args.length<descIter+2)
			return "§cNo Description Received";
		String desc = "";
		for(++descIter; descIter < args.length ; descIter++)
			desc = desc.concat(args[descIter]+' ');
		desc = desc.trim();
		faction.description = desc;
		NFactionList.i.add(faction);
		return "§6Faction Description changed";
	}



	private String name(CommandSender sender, String[] args)
	{
		NFaction faction;
		int nameIter = 0;
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
			if(args.length<2)
				return "§cNo Faction Received";
			faction = NFactionList.i.get(args[1]);
			if(faction == null)
				return "§cFaction Does Not Exist!";
			nameIter++;
		}
		if(args.length<nameIter+2)
			return "§cNo Description Received";
		String name = args[nameIter+1];
		name = name.trim();
		if(name.length()<1)
			return "§cInvalid Name";
		if(NFactionList.i.contains(name))
			return "§cFaction's Name Has Already Been Taken!";
		faction.name = name;
		NFactionList.i.add(faction);
		return "§6Faction Description changed";
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

		if(node.faction == null && !NConfig.i.HomeFromUndef)
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



	private String map(CommandSender sender, String[] args)
	{
		String output = null;
		if(sender instanceof Player)
		{
			String zoomStr = "";
			int zoom,mode,size;
			Set<String> modifier = new HashSet<String>();
			if(args.length>=2)
			{
				zoomStr = args[1].toLowerCase();
				for(int i = 2; i<args.length;i++)
					if(args[i].length()>=1)
						modifier.add(args[i].toLowerCase());
			}
			switch(zoomStr)
			{
				case "high":
				case "1":
				case "0": zoom = 1; break;
				case "medium":
				case "2": zoom = 2; break;
				case "low":
				case "3": zoom = 3; break;
				case "verylow":
				case "4": zoom = 4; break;
				case "absolute":
				case "5": zoom = 5; break;
				default: zoom = 1; modifier.add(zoomStr);
			}
			
			if(modifier.contains("faction") || modifier.contains("factions"))
				mode = 1;
			else
				mode = 0;
			if(modifier.contains("full") || modifier.contains("large"))
				size = 2;
			else if(modifier.contains("square") || modifier.contains("medium"))
				size = 1;
			else
				size = 0;
			
			Player actual = (Player)sender;
			NPlayer player = NPlayerList.i.get(actual.getUniqueId());
			NWorld world = NWorldList.i.worldMap.get(actual.getWorld().getUID());
			output = world.getMap(actual.getLocation().getChunk(), player.faction, zoom, mode, size);
		}
		else
			output = "Unsupported for Console!";
		return output;
	}


	private String list(CommandSender sender, String[] args)
	{
		NFaction sendFaction = null;
		String assemble = "";
		String mode;
		String pRel;
		Set<String> modifier = new HashSet<String>();
		if(args.length<2)
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
				NFaction faction;
				NPlayer nPlayer;
				OfflinePlayer player;

				sorted = new ArrayList<UUID>(NPlayerList.i.idSet());
				for(UUID PID : sorted)
				{
					nPlayer = NPlayerList.i.get(PID);
					if(nPlayer == null)
						sorted.remove(PID);
					player = Bukkit.getOfflinePlayer(PID);
					if(modifier.contains("offline") && player.isOnline())
						sorted.remove(PID);
					else if(modifier.contains("all"));
					else if(!player.isOnline())
						sorted.remove(PID);
					if(modifier.contains("wild") && nPlayer.getFaction() != null)
						sorted.remove(PID);
				}
				Collections.sort(sorted, NPlayer.playNameComp);
				for(UUID PID : sorted)
				{
					nPlayer = NPlayerList.i.get(PID);
					if(nPlayer == null)
						continue;
					pRel = NConfig.i.UnrelateColor.toString();
					if(sendFaction != null)
						pRel = sendFaction.getRelationColor(nPlayer.faction).toString();
					assemble += pRel + nPlayer.name;
					faction = nPlayer.getFaction();
					if(faction != null)
						assemble += ", "+nPlayer.getRank().rankName+" of "+faction.name;
					assemble += "\n";
				}
				break;
			}
			case "nodes": case "node":
			{
				NNode node;
				NRelation relate;
				NFaction target = null;
				for(String name : modifier)
					if(NFactionList.i.contains(name))
						target = NFactionList.i.get(name);
				if(target != null)
					sorted = new ArrayList<UUID>(target.nodeSet());
				else
					sorted = new ArrayList<UUID>(NNodeList.i.idSet());
				
				for(UUID NID : sorted)
				{
					node = NNodeList.i.get(NID);
					if(node == null)
						sorted.remove(NID);
					if(sendFaction != null && target == null)
					{
						if((modifier.contains("owned") || modifier.contains("owned")) && !sendFaction.ID.equals(node.ID))
							sorted.remove(NID);
						else
						{
							relate = sendFaction.getRelation(node.faction);
							if(relate == null)
							{	
								if(modifier.contains("relate") || modifier.contains("related"))
									sorted.remove(NID);
							}
							else if(!relate.ally && (modifier.contains("ally") || modifier.contains("allied") || modifier.contains("allies")))
								sorted.remove(NID);
							else if(!relate.enemy && (modifier.contains("enemy") || modifier.contains("enemied") || modifier.contains("enemies")))
								sorted.remove(NID);
							else if(!relate.neutral && (modifier.contains("neutral") || modifier.contains("neutraled") || modifier.contains("neutrals")))
								sorted.remove(NID);
						}
					}
					if(!node.coreActive && modifier.contains("active"))
						sorted.remove(NID);
				}
				Collections.sort(sorted, NNode.nodeNameComp);
				for(UUID NID : sorted)
				{
					node = NNodeList.i.get(NID);
					if(node == null)
						continue;
					pRel = NConfig.i.UnrelateColor.toString();
					if(sendFaction != null)
						pRel = sendFaction.getRelationColor(node.faction).toString();
					assemble += pRel + node.name;
					target = node.getFaction();
					if(target != null)
						assemble += ", owned by "+target.name;
					assemble += "\n";
					
				}
				break;
			}
			case "resources": case "resource":
			{
				int size;
				pRel = NConfig.i.UnrelateColor.toString();
				NResource resource;
				sorted = new ArrayList<UUID>(NResourceList.i.idSet());
				for(UUID RID : sorted)
				{
					resource = NResourceList.i.get(RID);
					if(resource == null)
						sorted.remove(RID);
				}
				for(UUID RID : sorted)
				{
					resource = NResourceList.i.get(RID);
					if(resource == null)
						sorted.remove(RID);
					size = resource.nodeSet.size();
					assemble += pRel + resource.name + ", ";
					if(size == 0)
						assemble += "No Nodes";
					else if(size == 0)
						assemble += "1 Node";
					else
						assemble += size+" Nodes";
					assemble += ", Every "+resource.cycleTimeMinutes+"m\n";
				}
				break;
			}
			case "factions": case "faction":
			default:
			{
				int online;
				int offline;
				NFaction faction;
				NRelation relate;
				if(sendFaction != null && (modifier.contains("relate") || modifier.contains("related") || modifier.contains("ally") || modifier.contains("allied") || modifier.contains("allies") || modifier.contains("enemy") || modifier.contains("enemied") || modifier.contains("enemies") || modifier.contains("neutral") || modifier.contains("neutraled") || modifier.contains("neutrals")))
				{
					sorted = new ArrayList<UUID>(sendFaction.relateFactionSet());
					for(UUID FID : sorted)
					{
						faction = NFactionList.i.get(FID);
						if(faction == null)
							sorted.remove(FID);
						relate = sendFaction.getRelation(FID);
						if(relate == null)
						{	
							if(modifier.contains("relate") || modifier.contains("related"))
								sorted.remove(FID);
						}
						else if(!relate.ally && (modifier.contains("ally") || modifier.contains("allied") || modifier.contains("allies")))
							sorted.remove(FID);
						else if(!relate.enemy && (modifier.contains("enemy") || modifier.contains("enemied") || modifier.contains("enemies")))
							sorted.remove(FID);
						else if(!relate.neutral && (modifier.contains("neutral") || modifier.contains("neutraled") || modifier.contains("neutrals")))
							sorted.remove(FID);
					}
				}
				else
					sorted = new ArrayList<UUID>(NFactionList.i.idSet());
				for(UUID FID : sorted)
				{
					faction = NFactionList.i.get(FID);
					if(faction == null)
						sorted.remove(FID);
				}
				if(modifier.contains("online"))
					Collections.sort(sorted, NFaction.factCountOnlineNameComp);
				else if(modifier.contains("node") || modifier.contains("nodes"))
					Collections.sort(sorted, NFaction.factNodeNameComp);
				else if(modifier.contains("name") || modifier.contains("names"))
					Collections.sort(sorted, NFaction.factNameComp);
				else
					Collections.sort(sorted, NFaction.factCountNameComp);
				for(UUID FID : sorted)
				{
					faction = NFactionList.i.get(FID);
					if(faction == null)
						continue;
					online = 0;
					offline = 0;
					pRel = NConfig.i.UnrelateColor.toString();
					if(sendFaction != null)
						pRel = sendFaction.getRelationColor(faction.ID).toString();
					for(UUID PID : faction.players.keySet())
						if(Bukkit.getOfflinePlayer(PID).isOnline())
							online++;
						else
							offline++;
					assemble += pRel + faction.name + " (§6"+online+pRel+"/§c"+offline+pRel+")\n";
				}
				break;
			}
		}
		return assemble;
	}


	private String info(CommandSender sender, String[] args)
	{
		if(args.length<2)
			return "§cNo Argument Received";

		if(args.length<3)
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

		assemble += ".\n§6Nodes Owned: " + faction.getNodesSize() + "\n"+NConfig.i.AlliedColor;
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
		assemble += "\n§6Nodes: ";
		if(node != null)
		{
			assemble += node.name+" ";
		}
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
		for(NResourceID NRID : resource.resourceSet)
			if(NRID != null)
				assemble += "§6Material: "+ NRID.material + "; Type: "+ NRID.damage + "; Amount: " + NRID.amount + "\n";

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
		if(args.length<2)
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
		if(args.length<2 || !args[1].equalsIgnoreCase("yes"))
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
			if(args.length<3)
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
		if(args.length<2)
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
			if(args.length<3)
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
			if(args.length<2)
				return "§cNo Player Received";
			player = NPlayerList.i.get(args[1]);
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
		NFaction faction;
		NPlayer player;
		int creaIter = 0;
		if(sender instanceof Player)
		{
			player = NPlayerList.i.get(((Player)sender).getUniqueId());
			if(player.getFaction() != null)
				return "§cYou're already in a Faction!";
		}
		else
		{
			if(args.length<2)
				return "§cNo Player Received";
			player = NPlayerList.i.get(args[1]);
			if(player == null)
				return "§cPlayer Does Not Exist!";
			creaIter++;
		}
		if(args.length<creaIter+2)
			return "§cNo Name Received";
		String name = args[creaIter+1];
		name = name.trim();
		if(name.length()<1)
			return "§cInvalid Name";
		if(NFactionList.i.contains(name))
			return "§cFaction's Name Has Already Been Taken!";
		faction = new NFaction(name);
		faction.addPlayer(player.ID, faction.getHighestRank());
		player.faction = faction.ID;
		NPlayerList.i.add(player);
		NFactionList.i.add(faction);
		return "§6Faction "+name+" Has Been Created!";
	}

	private String kick(CommandSender sender, String[] args)
	{
		if(args.length<2)
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
		if(args.length<2)
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
		if(args.length<2)
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

	private String relate(CommandSender sender, String[] args)
	{
		boolean clear = false;
		boolean accept = false;
		int offset = 0;
		boolean set;
		NRelation relation;
		NFaction faction, subject;
		if("relate".equalsIgnoreCase(args[0]))
		{
			offset++;
			if(args.length<2)
				return "§cNo Argument Received";
			clear = "delete".equalsIgnoreCase(args[1]);
			accept = "accept".equalsIgnoreCase(args[1]);
		}
		if(args.length<offset+2)
			return "§cNo Argument Received";
		subject = NFactionList.i.get(args[offset+1]);
		if( subject == null )
			return "§cFaction Doesn't Exist!";
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
			if(args.length<offset+3)
				return "§cNo Second Faction Received";
			faction = subject;
			subject = NFactionList.i.get(args[offset+2]);
			if( subject == null )
				return "§cSecond Faction Doesn't Exist!";
		}
		relation = faction.getRelationAbsolute(subject.ID);
		if( relation == null )
			relation = new NRelation(faction.ID,subject.ID);
		if(accept)
		{
			set = relation.acceptRelation(faction.ID);
			NRelationList.i.add(relation);
			if(set)
				return "§6Relation With "+subject.name+" Has Been Accepted!";
			else
				return "§6No Pending Relation With "+subject.name+" To Accept!";
		}
		else if(clear)
		{
			relation.delete();
			return "§6Relation With "+subject.name+" Has Been Deleted!";
		}
		else
		{
			NRelation desired = NConfig.i.StandardRelations.get(args[offset].toLowerCase());
			if(desired == null)
				return "§cInvalid Relation Type";
			set = relation.setRelation(faction.ID, subject.ID, desired);
			NRelationList.i.add(relation);
			if(desired.ally && !NConfig.i.AllySurroundingNodesAlwaysExposed)
			{
				faction.boilNodes();
				subject.boilNodes();
			}
			if(set)
				return "§6Relation Set To "+args[offset]+" With "+subject.name+"!";
			else
				return "§6Pending Relation Set To "+args[offset]+" With "+subject.name+"!";
		}
	}

	private String invite(CommandSender sender, String[] args)
	{
		if(args.length<2)
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
			if(args.length<3)
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
			if(args.length<2)
				return "§cNo Faction Received";
			faction = NFactionList.i.get(args[1]);
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
			if(args.length<2)
				return "§cNo Faction Received";
			faction = NFactionList.i.get(args[1]);
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