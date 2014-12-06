package com.lenis0012.bukkit.npc;

import com.lenis0012.bukkit.npc.event.NPCSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R1.metadata.PlayerMetadataStore;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * NPCFactory main class, initializes and creates NPC's.
 *
 * @author lenis0012
 */
public class NPCFactory implements Listener {

    private final Plugin plugin;
    private final NPCNetworkManager networkManager;
    private Method removePlayerInfo;

    public NPCFactory(Plugin plugin) {
        this.plugin = plugin;
        this.networkManager = new NPCNetworkManager();

        // See #19
        CraftServer server = (CraftServer) Bukkit.getServer();

        try {
            Field field = server.getClass().getDeclaredField("playerMetadata");
            field.setAccessible(true);
            PlayerMetadataStore metadata = (PlayerMetadataStore) field.get(server);
            if (!(metadata instanceof NPCMetadataStore)) {
                field.set(server, new NPCMetadataStore());
            }
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Spawns a new NPC at a specified location.
     *
     * @param location the location to spawn npc at.
     * @param profile the NPCProfile to use for npc
     * @return the new npc instance.
     */
    public NPC spawnHumanNPC(Location location, NPCProfile profile) {
        World world = location.getWorld();
        NPCEntity entity = new NPCEntity(world, location, profile, networkManager);
        entity.getBukkitEntity().setMetadata("NPC", new FixedMetadataValue(plugin, true));

        NPCSpawnEvent spawnEvent = new NPCSpawnEvent(entity);
        Bukkit.getServer().getPluginManager().callEvent(spawnEvent);

        return spawnEvent.isCancelled() ? null : entity;
    }

    /**
     * Gets an NPC object from an entity.
     *
     * @param entity the entity to get npc from.
     * @return the NPC instance, null if entity is not an npc.
     */
    public NPC getNPC(Entity entity) {
        if (!isNPC(entity)) {
            return null;
        }

        try {
            return (NPCEntity) ((CraftEntity) entity).getHandle();
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * Gets all NPC's from all worlds.
     *
     * @return list of all npc's
     */
    public List<NPC> getNPCs() {
        List<NPC> npcList = new ArrayList<NPC>();
        for (World world : Bukkit.getWorlds()) {
            npcList.addAll(getNPCs(world));
        }

        return npcList;
    }

    /**
     * Gets all NPC's from a specific world
     *
     * @param world the world to get npc's from
     * @return list of all npc's in the world
     */
    public List<NPC> getNPCs(World world) {
        List<NPC> npcList = new ArrayList<NPC>();
        for (Entity entity : world.getEntities()) {
            if (isNPC(entity)) {
                npcList.add(getNPC(entity));
            }
        }

        return npcList;
    }

    /**
     * Checks if an entity is a NPC.
     *
     * @param entity the entity to check.
     * @return true if entity is an NPC
     */
    public boolean isNPC(Entity entity) {
        return entity.hasMetadata("NPC");
    }

    /**
     * Despawn all NPC's in all worlds.
     */
    public void despawnAll() {
        Collection<World> worlds = Bukkit.getServer().getWorlds();

        for (World world : worlds) {
            despawnAll(world);
        }
    }

    /**
     * Despawn all NPC's in a single world.
     *
     * @param world the world to despawn NPC's on.
     */
    public void despawnAll(World world) {
        for (Entity entity : world.getEntities()) {
            if (entity.hasMetadata("NPC")) {
                entity.remove();
            }
        }
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin().equals(plugin)) {
            despawnAll();
        }
    }
}
