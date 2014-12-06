package com.lenis0012.bukkit.npc;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Class that contains all API functions from NPC's in NPCFactory
 *
 * @author lenis0012
 */
public interface NPC {

    /**
     * Get an org.bukkit.entity.Player instance from the npc.
     *
     * @return the NPC player instance.
     */
    public Player getBukkitEntity();

    /**
     * Checks whether or not the NPC can be damaged.
     *
     * @return false if NPC can be damaged?
     */
    public boolean isGodmode();

    /**
     * Sets whether or not the NPC can be damaged.
     *
     * @param godmode if NPC should be in godmode
     */
    public void setGodmode(boolean godmode);

    /**
     * Checks whether or not the NPC has gravity enabled.
     *
     * @return true if NPC has gravity
     */
    public boolean isGravity();

    /**
     * Sets whether or not an npc has gravity enabled.
     *
     * @param gravity if NPC should have gravity
     */
    public void setGravity(boolean gravity);

    /**
     * Sets the player lying down or standing up.
     * Proper use of NPCAnimation.LEAVE_BED;
     *
     * @param x the body's x co-ordinate
     *          (Set to null to make entity stand up)
     * @param y the body's y co-ordinate
     *          (Set to null to make entity stand up)
     * @param z the body's z co-ordinate
     *          (Set to null to make entity stand up)
     *          <p/>
     *          Will only make NPC stand up if all
     *          3 co-ordinates are set to null and
     *          NPC is lying down.
     */
    public void setLying(double x, double y, double z);

    /**
     * Checks whether the entity is lying down.
     *
     * @return true if the entity is lying down
     */
    public boolean isLying();

    /**
     * Walks to a location.
     *
     * @param location location to walk to
     * @return true if path created and applied correctly.
     */
    public boolean pathfindTo(Location location);

    /**
     * Walk to a location with custom speed.
     *
     * @param location location to walk to
     * @param speed    speed to walk with (max 1.0, 0.2 default)
     * @return true if path created and applied correctly.
     */
    public boolean pathfindTo(Location location, double speed);

    /**
     * Walk to a location with custom speed and range.
     *
     * @param location the location to walk to
     * @param speed    the speed to walk with (max 1.0, 0.2 default)
     * @param range    the block radius limit for path finding (30 default)
     * @return true if path created and applied correctly.
     */
    public boolean pathfindTo(Location location, double speed, double range);

    /**
     * Makes the NPC look at an entity.
     *
     * @param target the entity to look at
     */
    public void setTarget(Entity target);

    /**
     * Gets the entity the NPC is looking at
     *
     * @return the entity NPC is looking at (null if not found)
     */
    public Entity getTarget();

    /**
     * Make npc look at a certain location
     *
     * @param location Location to look at
     */
    public void lookAt(Location location);

    /**
     * Changes the yaw of NPC the proper way.
     *
     * @param yaw the new NPC yaw
     */
    public void setYaw(float yaw);

    /**
     * Plays an animation on the NPC.
     *
     * @param animation the animation type to display
     */
    public void playAnimation(NPCAnimation animation);

    /**
     * Sets the equipment at a slot for the NPC.
     *
     * @param slot the slot type for the equipment
     * @param item the item to put on slot
     */
    public void setEquipment(EquipmentSlot slot, ItemStack item);

    /**
     * Gets if the NPC will collide with other entities.
     *
     * @return true if entity collision is enabled
     */
    public boolean getEntityCollision();

    /**
     * Sets if the NPC will collide with other entities.
     *
     * @param collide if entity should collide
     */
    public void setEntityCollision(boolean collide);
}
