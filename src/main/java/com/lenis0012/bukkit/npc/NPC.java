package com.lenis0012.bukkit.npc;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Class that contains all api functions from npc's in NPCFactory
 * 
 * @author lenis0012
 */
public interface NPC {
	
	/**
	 * Get an org.bukkit.entity.Player instance from the npc.
	 * 
	 * @return NPC's player instance.
	 */
	public Player getBukkitEntity();
	
	/**
	 * Check wether or not an npc can get damaged
	 * 
	 * @return NPC can be damaged?
	 */
	public boolean isInvulnerable();
	
	/**
	 * Set wether or not an npc can get damaged
	 * 
	 * @param invulnerable NPC can be damaged?
	 */
	public void setInvulnerable(boolean invulnerable);
	
	/**
	 * Check wether or not an npc has gravity enabled.
	 * 
	 * @return NPC had grvity?
	 */
	public boolean isGravity();
	
	/**
	 * Set wether or not an npc has gravity enabled.
	 * 
	 * @param gravity NPC has gravity?
	 */
	public void setGravity(boolean gravity);
	
	/**
	 * Walk to a location
	 * 
	 * @param location Location to walk to
	 * @return True if path created and applied correctly.
	 */
	public boolean pathfindTo(Location location);
	
	/**
	 * Walk to a location with custom speed
	 * 
	 * @param location Location to walk to
	 * @param speed Speed to walk with (max 1.0, 0.2 default)
	 * @return True if path created and applied correctly.
	 */
	public boolean pathfindTo(Location location, double speed);
	
	/**
	 * Walk to a location with custom speed and range.
	 * 
	 * @param location Location to walk to
	 * @param speed Speed to walk with (max 1.0, 0.2 default)
	 * @param range Block radius limit for path finding (30 default)
	 * @return True if path created and applied correctly.
	 */
	public boolean pathfindTo(Location location, double speed, double range);
	
	/**
	 * Make NPC look at an entity.
	 * 
	 * @param target Entity to look at
	 */
	public void setTarget(Entity target);
	
	/**
	 * Get the entity the ncp is looking at
	 * 
	 * @return Entity npc is looking at (null if not found)
	 */
	public Entity getTarget();
	
	/**
	 * Make npc look at a certain location
	 * 
	 * @param location Location to look at
	 */
	public void lookAt(Location location);
	
	/**
	 * Change npc's yaw the proper way
	 * 
	 * @param yaw New npc yaw
	 */
	public void setYaw(float yaw);
	
	/**
	 * Play an animcation on the npc
	 * 
	 * @param animcation Animcation type to display
	 */
	public void playAnimation(NPCAnimation animcation);
	
	/**
	 * Set npc equipment.
	 * 
	 * @param slot Slot type for the equipment
	 * @param item Item to put on slot
	 */
	public void setEquipment(EquipmentSlot slot, ItemStack item);
	
		/**
	 * Get if an npc with collide with other entities.
	 * 
	 * @param location Get entity collision for npc.
	 * @return
	 */
	public boolean getEntityCollision();
	
	/**
	 * Set if an npc with collide with other entities.
	 * 
	 * @param location Set entity collision for npc.
	 * @return
	 */
	public void setEntityCollision(boolean entityCollision);
	
}
