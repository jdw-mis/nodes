package com.mis.nodes.event;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

import com.mis.nodes.data.NFaction;
import com.mis.nodes.data.NNode;
import com.mis.nodes.data.NPlayer;
import com.mis.nodes.data.NWorld;

/*
 * BlockBreakEvent		
 * BlockBurnEvent		
 * BlockDamageEvent		may not need, runs every punch, break is nicer?
 * BlockExplodeEvent	only calls when explosion source is unknown
 * BlockFromToEvent		blocks water/lava spread
 * BlockPistonExtendEvent
 * BlockPistonRetractEvent		
 * SignChangeEvent		
 */

public class NBlockEvent
{
	// move these to a defines class, editable by user later
	private static HashMap<Material, Integer>	breakWhitelist	= new HashMap<Material, Integer>();
	private static HashMap<Material, Integer>	placeWhitelist	= new HashMap<Material, Integer>();
	private static Integer						burnWhitelist;
	private static Integer						pistonWhitelist;
	private static Integer						breakRank;
	private static Integer						breakRelate;
	private static Integer						placeRank;
	private static Integer						placeRelate;
	private static Integer						editRank;
	private static Integer						editRelate;

	public NBlockEvent()
	{
		// whitelist integer is 0 most permissive, 9 least permissive
		// rank and relate are opposite
		breakWhitelist.put( Material.TORCH, 9 );
		placeWhitelist.put( Material.TORCH, 9 );
		burnWhitelist = 6;
		pistonWhitelist = 3;
		breakRank = 1;
		breakRelate = 4;
		placeRank = 1;
		placeRelate = 4;
		editRank = 1;
		editRelate = 4;
	}

	@EventHandler
	public void onBlockBreak( BlockBreakEvent event )
	{
		Block blocc = event.getBlock();
		NNode node = NWorld.getNode( blocc.getChunk() );
		Integer blocprot = breakWhitelist.get( blocc.getType() );
		if ( node.getProtection() < (blocprot == null ? blocprot = 0 : blocprot) )
			return;

		NPlayer breaker = NPlayer.getPlayer( event.getPlayer() );
		NFaction blocFaction = node.faction;

		if ( (blocFaction.equals( breaker.faction )
				|| (breaker.faction != null && blocFaction.relations.get( breaker.faction ).flag > breakRelate)
						&& breaker.getRank().flag > breakRank) )
			return;
		event.setCancelled( true );
	}

	@EventHandler
	public void onBlockPlace( BlockPlaceEvent event )
	{
		Block blocc = event.getBlock();
		NNode node = NWorld.getNode( blocc.getChunk() );
		Integer blocprot = placeWhitelist.get( blocc.getType() );
		if ( node.getProtection() < (blocprot == null ? blocprot = 0 : blocprot) )
			return;

		NPlayer placer = NPlayer.getPlayer( event.getPlayer() );
		NFaction blocFaction = node.faction;

		if ( (blocFaction.equals( placer.faction )
				|| (placer.faction != null && blocFaction.relations.get( placer.faction ).flag > placeRelate)
						&& placer.getRank().flag > placeRank) )
			return;
		event.setCancelled( true );
	}

	@EventHandler
	public void onBlockBurn( BlockBurnEvent event )
	{
		NNode node = NWorld.getNode( event.getBlock().getChunk() );
		if ( node.getProtection() < burnWhitelist )
			return;
		event.setCancelled( true );
	}

	@EventHandler
	public void onBlockFromTo( BlockFromToEvent event )
	{
		Chunk from = event.getBlock().getChunk();
		Chunk to = event.getToBlock().getChunk();
		if ( from.equals( to ) )
			return;
		NNode toN = NWorld.getNode( to );
		Integer blocprot = placeWhitelist.get( event.getBlock().getType() );
		if ( toN.getProtection() < (blocprot == null ? blocprot = 0 : blocprot) )
			return;
		event.setCancelled( true );
	}

	@EventHandler
	public void onBlockPistonExtend( BlockPistonExtendEvent event )
	{
		HashSet<Chunk> chunkset = new HashSet<Chunk>();
		HashSet<NNode> nodeset = new HashSet<NNode>();
		event.getBlocks().forEach( block -> chunkset.add( block.getChunk() ) );
		chunkset.forEach( chunk -> nodeset.add( NWorld.getNode( chunk ) ) );
		nodeset.forEach( node -> { if ( node.getProtection() >= pistonWhitelist ) { event.setCancelled( true ); return; } } );
	}

	@EventHandler
	public void onBlockPistonRetract( BlockPistonRetractEvent event )
	{
		HashSet<Chunk> chunkset = new HashSet<Chunk>();
		HashSet<NNode> nodeset = new HashSet<NNode>();
		event.getBlocks().forEach( block -> chunkset.add( block.getChunk() ) );
		chunkset.forEach( chunk -> nodeset.add( NWorld.getNode( chunk ) ) );
		nodeset.forEach( node -> { if ( node.getProtection() >= pistonWhitelist ) { event.setCancelled( true ); return; } } );
	}

	@EventHandler
	public void onSignChange( SignChangeEvent event )
	{
		NNode node = NWorld.getNode( event.getBlock().getChunk() );
		if ( node.faction == null )
			return;

		NPlayer editor = NPlayer.getPlayer( event.getPlayer() );
		NFaction blocFaction = node.faction;

		if ( (blocFaction.equals( editor.faction )
				|| (editor.faction != null && blocFaction.relations.get( editor.faction ).flag > editRelate)
						&& editor.getRank().flag > editRank) )
			return;
		event.setCancelled( true );
	}
}
