package com.lenis0012.bukkit.npc;

import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_7_R4.metadata.EntityMetadataStore;
import org.bukkit.craftbukkit.v1_7_R4.metadata.PlayerMetadataStore;

/**
 * Implementation based on {@link EntityMetadataStore} using the uuid rather than name of the player. 
 * <br /> <b>See #19</b>
 */
public class NPCMetadataStore extends PlayerMetadataStore {

  @Override
  protected String disambiguate(OfflinePlayer player, String metadataKey) {
    return player.getUniqueId().toString() + ":" + metadataKey;
  }
}
