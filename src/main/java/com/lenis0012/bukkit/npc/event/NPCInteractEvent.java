package com.lenis0012.bukkit.npc.event;

import com.lenis0012.bukkit.npc.NPC;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when a player right clicks an NPC.
 *
 * @author lenis0012
 */
public class NPCInteractEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();
    private boolean cancelled = false;

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
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}