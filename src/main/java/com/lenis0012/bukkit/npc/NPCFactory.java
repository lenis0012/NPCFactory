package com.lenis0012.bukkit.npc;

import net.minecraft.server.v1_7_R3.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class NPCFactory implements Listener {
	private final Plugin plugin;
	private final NPCNetworkManager networkManager;
	
	public NPCFactory(Plugin plugin) {
		this.plugin = plugin;
		this.networkManager = new NPCNetworkManager();
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	public NPC spawnHumanNPC(Location location, NPCProfile profile) {
		World world = location.getWorld();
		WorldServer worldServer = ((CraftWorld) world).getHandle();
		NPCEntity entity = new NPCEntity(world, profile, networkManager);
		entity.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
		worldServer.addEntity(entity);
		worldServer.players.remove(entity);
		entity.getBukkitEntity().setMetadata("NPC", new FixedMetadataValue(plugin, true));
		return entity.getNPC();
	}
	
	public void despawnAll() {
		for(World world : Bukkit.getWorlds()) {
			despawnAll(world);
		}
	}
	
	public void despawnAll(World world) {
		for(Entity entity : world.getEntities()) {
			if(entity.hasMetadata("NPC")) {
				entity.remove();
			}
		}
	}
}