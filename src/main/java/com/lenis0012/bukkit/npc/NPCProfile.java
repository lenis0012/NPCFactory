package com.lenis0012.bukkit.npc;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lenny on 8/8/2014.
 * You may use this code if permitted by the legal owner only.
 * Copyright 2014 Lennart ten Wolde
 */
public class NPCProfile {

    private final GameProfile handle;

    /**
     * Return a profile with a steve skin.
     *
     * @param name Display name of profile
     */
    public NPCProfile(String name) {
        this(new GameProfile(UUID.randomUUID(), name));
    }

    private NPCProfile(GameProfile handle) {
        this.handle = handle;
    }

    private static final Cache<String, Property> TEXTURE_CACHE = CacheBuilder.newBuilder()
            .expireAfterWrite(30L, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Property>() {
                @Override
                public Property load(String key) throws Exception {
                    return loadTextures(key);
                }
            });

    private static Property loadTextures(String name) {
        GameProfile profile = new GameProfile(Bukkit.getOfflinePlayer(name).getUniqueId(), name);
        MinecraftServer.getServer().aB().fillProfileProperties(profile, true);
        return Iterables.getFirst(profile.getProperties().get("textures"), null);
    }

    /**
     * Loads a profile with a custom skin.
     *
     * @param name      the display name of profile
     * @param skinOwner the owner of profile skin
     * @return the NPCProfile with the name and skin owner
     */
    public static NPCProfile loadProfile(String name, String skinOwner) {
        try {
            final GameProfile profile = new GameProfile(UUID.randomUUID(), name);
            //TODO: Needs fixing.
            //profile.getProperties().put("textures", TEXTURE_CACHE.get(skinOwner));
            return new NPCProfile(profile);
        } catch (Exception ex) {
            //Make sure that we don't return any exceptions
            return new NPCProfile(name);
        }
    }

    /**
     * Gets the profile unique ID.
     *
     * @return the profile UUID
     */
    public UUID getUUID() {
        return handle.getId();
    }

    /**
     * Gets the profile display name.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return handle.getName();
    }

    /**
     * Gets the original game profile.
     *
     * @return the original game profile
     */
    public GameProfile getHandle() {
        return handle;
    }
}
