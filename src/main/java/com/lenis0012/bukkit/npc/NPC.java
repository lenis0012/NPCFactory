package com.lenis0012.bukkit.npc;

import net.minecraft.server.v1_7_R3.Packet;
import net.minecraft.server.v1_7_R3.PacketPlayOutAnimation;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Class that contains all api functions from npc's in NPCFactory
 * 
 * @author lenis0012
 */
public class NPC {
	private NPCEntity entity;
	
	public NPC(NPCEntity entity) {
		this.entity = entity;
	}
	
	public Player getBukkitEntity() {
		return entity.getBukkitEntity();
	}
	
	public boolean isInvulnerable() {
		return entity.isInvulnerable();
	}
	
	public void setInvulnerable(boolean invulnerable) {
		entity.setInvulnerable(invulnerable);
	}
	
	public void setYaw(float yaw) {
		entity.yaw = yaw;
		entity.aP = yaw;
		entity.aO = yaw;
	}
	
	/**
	 * Packet related methods
	 */
	public void playAnimation(NPCAnimation animcation) {
		broadcastPacket(new PacketPlayOutAnimation(entity, animcation.getId()));
	}
	
	private final int RADIUS = Bukkit.getViewDistance() * 16;
	
	private final void broadcastPacket(Packet packet) {
		for(Player p : entity.getBukkitEntity().getWorld().getPlayers()) {
			if(entity.getBukkitEntity().getLocation().distanceSquared(p.getLocation()) <= RADIUS * RADIUS) {
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
			}
		}
	}
}