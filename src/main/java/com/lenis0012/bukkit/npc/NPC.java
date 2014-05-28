package com.lenis0012.bukkit.npc;

import net.minecraft.server.v1_7_R3.Packet;
import net.minecraft.server.v1_7_R3.PacketPlayOutAnimation;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Class that contains all api functions from npc's in NPCFactory
 * 
 * @author lenis0012
 */
public class NPC {
	private NPCEntity entity;
	private NPCPath path;
	
	public NPC(NPCEntity entity) {
		this.entity = entity;
	}
	
	/**
	 * Get an org.bukkit.entity.Player instance from the npc.
	 * 
	 * @return NPC's player instance.
	 */
	public Player getBukkitEntity() {
		return entity.getBukkitEntity();
	}
	
	/**
	 * Check wether or not an npc can get damaged
	 * 
	 * @return NPC can be damaged?
	 */
	public boolean isInvulnerable() {
		return entity.isInvulnerable();
	}
	
	/**
	 * Set wether or not an npc can get damaged
	 * 
	 * @param invulnerable NPC can be damaged?
	 */
	public void setInvulnerable(boolean invulnerable) {
		entity.setInvulnerable(invulnerable);
	}
	
	/**
	 * Pathfinding methods
	 */
	
	/**
	 * Walk to a location
	 * 
	 * @param location Location to walk to
	 * @return True if path created and applied correctly.
	 */
	public boolean pathfindTo(Location location) {
		return pathfindTo(location, 0.2);
	}
	
	/**
	 * Walk to a location with custom speed
	 * 
	 * @param location Location to walk to
	 * @param speed Speed to walk with (max 1.0, 0.2 default)
	 * @return True if path created and applied correctly.
	 */
	public boolean pathfindTo(Location location, double speed) {
		return pathfindTo(location, speed, 30.0D);
	}
	
	/**
	 * Walk to a location with custom speed and range.
	 * 
	 * @param location Location to walk to
	 * @param speed Speed to walk with (max 1.0, 0.2 default)
	 * @param range Block radius limit for path finding (30 default)
	 * @return True if path created and applied correctly.
	 */
	public boolean pathfindTo(Location location, double speed, double range) {
		NPCPath path = NPCPath.find(entity, location, range, speed);
		return (this.path = path) != null;
	}
	
	protected void onTick() {
		if(target != null && path == null) {
			if(target.isDead() || (target instanceof Player && !((Player) target).isOnline())) {
				this.target = null;
			} else if(entity.getBukkitEntity().getLocation().getWorld().equals(target.getWorld()) && entity.getBukkitEntity().getLocation().distanceSquared(target.getLocation()) <= 32 * 32) {
				lookAt(target.getLocation());
			}
		} if(path != null) {
			if(!path.update()) {
				this.path = null;
			}
		}
	}
	
	/**
	 * Direction related methods
	 */
	private LivingEntity target;
	
	/**
	 * Make NPC look at an entity.
	 * 
	 * @param target Entity to look at
	 */
	public void setTarget(LivingEntity target) {
		this.target = target;
		lookAt(target.getLocation());
	}
	
	/**
	 * Get the entity the ncp is looking at
	 * 
	 * @return Entity npc is looking at (null if not found)
	 */
	public LivingEntity getTarget() {
		return target;
	}
	
	/**
	 * Make npc look at a certain location
	 * 
	 * @param location Location to look at
	 */
	public void lookAt(Location location) {
		setYaw(getAngle(new Vector(entity.locX, 0, entity.locZ), location.toVector()));
	}
	
	/**
	 * Change npc's yaw the proper way
	 * 
	 * @param yaw New npc yaw
	 */
	public void setYaw(float yaw) {
		entity.yaw = yaw;
		entity.aP = yaw;
		entity.aO = yaw;
	}
	
	private final float getAngle(Vector point1, Vector point2) {
		double dx = point2.getX() - point1.getX();
		double dz = point2.getZ() - point1.getZ();
		float angle = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90;
		if(angle < 0) { angle += 360.0F; }
		return angle;
	}
	
	/**
	 * Packet related methods
	 */
	
	/**
	 * Play an animcation on the npc
	 * 
	 * @param animcation Animcation type to display
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