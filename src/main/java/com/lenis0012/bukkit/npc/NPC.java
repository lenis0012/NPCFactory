package hardcore.npc.lib;

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
	 * Get an org.bukkit.entity.Player instance from the BOC.
	 * 
	 * @return NPC's player instance.
	 */
	public Player getBukkitEntity();

	/**
	 * Check whether or not an npc can get damaged
	 * 
	 * @return NPC can be damaged?
	 */
	public boolean isInvulnerable();

	/**
	 * Set whether or not an npc can get damaged
	 * 
	 * @param invulnerable NPC can be damaged?
	 */
	public void setInvulnerable(boolean invulnerable);

	/**
	 * Check whether or not an npc has gravity enabled.
	 * 
	 * @return NPC had gravity?
	 */
	public boolean isGravity();

	/**
	 * Set whether or not an npc has gravity enabled.
	 * 
	 * @param gravity NPC has gravity?
	 */
	public void setGravity(boolean gravity);
	
	/**
	 * Set an NPC's armour contents.
	 * 
	 * @param Set an npc's armour contents.
	 */
	public boolean setArmorContents(ItemStack[] armor);
	
	/**
	 * Set an NPC's held item.
	 * 
	 * @param Set an npc's held item.
	 */
	public boolean setHeldItem(ItemStack hand);
	
	/**
	 * Set an NPC's helmet.
	 * 
	 * @param Set an npc's helmet.
	 */
	public boolean setHelmet(ItemStack helmet);
	
	/**
	 * Set an NPC's chestplate.
	 * 
	 * @param Set an npc's chestplate.
	 */
	public boolean setChestplate(ItemStack chestplate);
	
	/**
	 * Set an NPC's leggings.
	 * 
	 * @param Set an npc's leggings.
	 */
	public boolean setLeggings(ItemStack leggings);
	
	/**
	 * Set an NPC's boots.
	 * 
	 * @param Set an npc's boots.
	 */
	public boolean setBoots(ItemStack boots);

	/**
	 * Walk to a location.
	 * 
	 * @param location Location to walk to.
	 * @return True if path created and applied correctly.
	 */
	public boolean pathfindTo(Location location);

	/**
	 * Walk to a location with custom speed.
	 * 
	 * @param location Location to walk to.
	 * @param speed Speed to walk with (max 1.0, 0.2 default).
	 * @return True if path created and applied correctly.
	 */
	public boolean pathfindTo(Location location, double speed);

	/**
	 * Walk to a location with custom speed and range.
	 * 
	 * @param location Location to walk to.
	 * @param speed Speed to walk with (max 1.0, 0.2 default).
	 * @param range Block radius limit for path finding (30 default).
	 * @return True if path created and applied correctly.
	 */
	public boolean pathfindTo(Location location, double speed, double range);

	/**
	 * Make NPC look at an entity..
	 * 
	 * @param entity Entity to look at.
	 */
	public void setTarget(Entity entity);

	/**
	 * Get the entity the NPC is looking at.
	 * 
	 * @return Entity NPC is looking at (null if not found).
	 */
	public Entity getTarget();

	/**
	 * Make NPC look at a certain location.
	 * 
	 * @param location Location to look at.
	 */
	public void lookAt(Location location);

	/**
	 * Change NPC's yaw the proper way.
	 * 
	 * @param yaw New NPC yaw.
	 */
	public void setYaw(float yaw);

	/**
	 * Play an animation on the NPC.
	 * 
	 * @param animation Animcation type to display.
	 */
	public void playAnimation(NPCAnimation animation);
	
	/**
	 * Set if an npc with collide with other entities.
	 * 
	 * @param location Set entity collision for npc.
	 * @return
	 */
	public void setEntityCollision(boolean entityCollision);
	
}
