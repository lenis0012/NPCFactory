package com.lenis0012.bukkit.npc;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * When a player right clicks a npc.
 *
 * @author lenis0012
 */
public class NPCInteractEvent extends Event implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private boolean canceled = false;

    @Override
    public boolean isCancelled() {
        return canceled;
    }

    @Override
    public void setCancelled(boolean canceled) {
        this.canceled = canceled;
    }

    private final NPC npc;
    private final HumanEntity entity;

    public NPCInteractEvent(NPC npc, HumanEntity entity) {
        this.npc = npc;
        this.entity = entity;
    }

    public NPC getNpc() {
        return npc;
    }

    public HumanEntity getEntity() {
        return entity;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
