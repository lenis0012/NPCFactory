package com.lenis0012.bukkit.npc;

/**
 * Animation types that can be displayed on an npc.
 * Removed LEAVE_BED animation as it didn't do
 * what lenis0012 thought it did.
 * Redundant animation, therefore removed.
 *
 * @author lenis0012
 */
public enum NPCAnimation {
    /**
     * Makes npc swing his arm.
     */
    SWING_ARM(0),
    /**
     * Highlights the npc in red to mark that it has been damaged.
     */
    DAMAGE(1),
    /**
     * Moves npc's arm towards his mouth to eat food.
     */
    EAT_FOOD(3),
    /**
     * Displays criticial hit
     */
    CRITICAL_HIT(4),
    /**
     * Display magic critical hit
     */
    MAGIC_CRITICAL_HIT(5),
    /**
     * Makes npc crouch.
     */
    CROUCH(104),
    /**
     * Stops npc from crouching.
     */
    UNCROUCH(105);

    private final int id;

    private NPCAnimation(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
