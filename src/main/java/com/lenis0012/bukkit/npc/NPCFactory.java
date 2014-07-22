package com.lenis0012.bukkit.npc;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_7_R4.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

/**
 * NPCFactory main class, intializes and creates npcs.
 * 
 * @author lenis0012
 */
public class NPCFactory implements Listener {
	private final Plugin plugin;
	private final NPCNetworkManager networkManager;
	
	public NPCFactory(Plugin plugin) {
		this.plugin = plugin;
		this.networkManager = new NPCNetworkManager();
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	/**
	 * Spawn a new npc at a speciafied location.
	 * 
	 * @param location Location to spawn npc at.
	 * @param profile NPCProfile to use for npc
	 * @return New npc instance.
	 */
	public NPC spawnHumanNPC(Location location, NPCProfile profile) {
		World world = location.getWorld();
		WorldServer worldServer = ((CraftWorld) world).getHandle();
		NPCEntity entity = new NPCEntity(world, profile, networkManager);
		entity.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		worldServer.addEntity(entity);
		worldServer.players.remove(entity);
		entity.getBukkitEntity().setMetadata("NPC", new FixedMetadataValue(plugin, true));
		return entity;
	}
	
	/**
	 * Get npc from entity.
	 * 
	 * @param entity Entity to get npc from.
	 * @return NPC instance, null if entity is not an npc.
	 */
	public NPC getNPC(Entity entity) {
		if(!isNPC(entity)) {
			return null;
		}
		
		NPCEntity npcEntity = (NPCEntity) ((CraftEntity) entity).getHandle();
		return npcEntity;
	}
	
	/**
	 * Get all npc's from all worlds.
	 * 
	 * @return A list of all npc's
	 */
	public List<NPC> getNPCs() {
		List<NPC> npcList = new ArrayList<NPC>();
		for (World world : Bukkit.getWorlds()) {
			npcList.addAll(getNPCs(world));
		}
		
		return npcList;
	}
	
	/**
	 * Get all npc's from a specific world
	 * 
	 * @param world World to get npc's from
	 * @return A list of all npc's in the world
	 */
	public List<NPC> getNPCs(World world) {
		List<NPC> npcList = new ArrayList<NPC>();
		for (Entity entity : world.getEntities()) {
			if (isNPC(entity)) {
				npcList.add(getNPC(entity));
			}
		}
		
		return npcList;
	}
	
	/**
	 * Check if an entity is a NPC.
	 * 
	 * @param entity Entity to check.
	 * @return Entity is a npc?
	 */
	public boolean isNPC(Entity entity) {
		return entity.hasMetadata("NPC");
	}
	
	/**
	 * Despawn all npc's on all worlds.
	 */
	public void despawnAll() {
		for(World world : Bukkit.getWorlds()) {
			despawnAll(world);
		}
	}
	
	/**
	 * Despawn all npc's on a single world.
	 * 
	 * @param world World to despawn npc's on.
	 */
	public void despawnAll(World world) {
		for(Entity entity : world.getEntities()) {
			if(entity.hasMetadata("NPC")) {
				entity.remove();
			}
		}
	}
	
	@EventHandler
	public void onPluginDisable(PluginDisableEvent event) {
		if(event.getPlugin().equals(plugin)) {
			despawnAll();
		}
	}
}
