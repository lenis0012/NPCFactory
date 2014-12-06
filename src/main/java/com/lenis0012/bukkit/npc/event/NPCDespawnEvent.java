package com.lenis0012.bukkit.npc.event;

import com.lenis0012.bukkit.npc.NPC;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when an NPC is de-spawned.
 *
 * @author lenis0012
 */
public class NPCDespawnEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private final NPC npc;
    private final DespawnCause cause;

    public NPCDespawnEvent(NPC npc, DespawnCause cause) {
        this.npc = npc;
        this.cause = cause;
    }

    public NPC getNpc() {
        return npc;
    }

    public DespawnCause getCause() {
        return cause;
    }

    public enum DespawnCause {
        DEATH, PLUGIN
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
