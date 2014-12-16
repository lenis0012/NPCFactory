package com.lenis0012.bukkit.npc;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
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
    private static final LoadingCache<String, Property> TEXTURE_CACHE = CacheBuilder.newBuilder()
            .expireAfterWrite(30L, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Property>() {
                @Override
                public Property load(String key) throws Exception {
                    return loadTextures(key);
                }
            });

    @SuppressWarnings("deprecation")
    private static final Property loadTextures(String name) {
        GameProfile profile = new GameProfile(Bukkit.getOfflinePlayer(name).getUniqueId(), name);
        MinecraftServer.getServer().aB().fillProfileProperties(profile, true);
        return Iterables.getFirst(profile.getProperties().get("textures"), null);
    }

    /**
     * Load a profile with a custom skin
     *
     * @param name      Display name of profile
     * @param skinOwner Owner of profile skin
     * @return NPCProfile
     */
    public static NPCProfile loadProfile(String name, String skinOwner) {
        try {
            final GameProfile profile = new GameProfile(UUID.randomUUID(), name);
            profile.getProperties().put("textures", TEXTURE_CACHE.get(skinOwner));
            return new NPCProfile(profile);
        } catch(Exception e) {
            //Make sure that we don't return any exceptions
            return new NPCProfile(name);
        }
    }

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

    /**
     * Get the profile UUID
     *
     * @return Profile UUID
     */
    public UUID getUUID() {
        return handle.getId();
    }

    /**
     * Get the profile display name
     *
     * @return Display name
     */
    public String getDisplayName() {
        return handle.getName();
    }

    /**
     * Get the original game profile
     *
     * @return Original game profile
     */
    public GameProfile getHandle() {
        return handle;
    }
}
