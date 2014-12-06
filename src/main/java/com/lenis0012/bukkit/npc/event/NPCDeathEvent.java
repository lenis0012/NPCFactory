package com.lenis0012.bukkit.npc.event;

import com.lenis0012.bukkit.npc.NPC;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when an NPC is killed.
 *
 * @author lenis0012
 */
public class NPCDeathEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private boolean cancelled = false;

    private final NPC npc;

    public NPCDeathEvent(NPC npc) {
        this.npc = npc;
    }

    public NPC getNpc() {
        return npc;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
