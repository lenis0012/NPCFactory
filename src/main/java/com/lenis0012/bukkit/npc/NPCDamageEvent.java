package com.lenis0012.bukkit.npc;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

/**
 * When an npc is attacked by a human.
 *
 * @author lenis0012
 */
public class NPCDamageEvent extends Event implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private boolean cancelled = false;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    private final NPC npc;
    private final Entity damager;
    private final DamageCause cause;
    private double damage;

    public NPCDamageEvent(NPC npc, Entity damager, DamageCause cause, double damage) {
        this.npc = npc;
        this.damager = damager;
        this.cause = cause;
        this.damage = damage;
    }

    public DamageCause getCause() {
        return cause;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public NPC getNpc() {
        return npc;
    }

    public Entity getDamager() {
        return damager;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
