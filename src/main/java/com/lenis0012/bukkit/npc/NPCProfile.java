package com.lenis0012.bukkit.npc;

import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.common.base.Charsets;

import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.properties.Property;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import net.minecraft.util.org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * NPCProfile represents GameProfile in a more easy-to-use way.
 * You mway want to construct this in async tasks.
 * 
 * @author lenis0012
 */
public class NPCProfile extends GameProfile {
	private UUID uuid;
	private String name;
	
	public NPCProfile() {
		super(UUID.randomUUID(), "internal");
	}
	
	/**
	 * Create NPCProfile based on game profile
	 * 
	 * @param profile Mojang's game profile
	 */
	public NPCProfile(GameProfile profile) {
		this();
		this.uuid = profile.getId();
		this.name = profile.getName();
		for(Entry<String, Collection<Property>> entry : profile.getProperties().asMap().entrySet()) {
			profile.getProperties().putAll(entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * Create a NPCProfile based on name.
	 * Note: this will allways have a steve skin.
	 * 
	 * @param name Name of steve npc.
	 */
	public NPCProfile(String name) {
		this();
		this.uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(Charsets.UTF_8));
		this.name = name;
	}
	
	/**
	 * Create a NPCProfile based on name and skin.
	 * 
	 * @param name Name of npc.
	 * @param skinOwner Name of skin owner the npc will use.
	 */
	public NPCProfile(final String name, final String skinOwner) {
		this(name, parseUUID(getUUID(name)), skinOwner);
	}
	
	/**
	 * Create NPCProfile based on name and UUID.
	 * Note: The skin and name will be based on the uuid passed in.
	 * 
	 * @param name Name of the npc
	 * @param uuid UUID of the npc name.
	 */
	public NPCProfile(String name, UUID uuid) {
		this(name, uuid, uuid);
	}
	
	/**
	 * Created NPCProfle based on name, uuid and skin owner name.
	 * 
	 * @param name Name of npc.
	 * @param uuid UUID of the npc name.
	 * @param skinOwner Name of skin owner the npc will use.
	 */
	public NPCProfile(final String name, final UUID uuid, final String skinOwner) {
		this(name, uuid, parseUUID(getUUID(skinOwner)));
	}
	
	/**
	 * Create NPCProfile based on name, uuid and skin uuid.
	 * 
	 * @param name Name of npc.
	 * @param uuid UUID of the npc name.
	 * @param skinUUID UUID of the name from the skin the npc will use.
	 */
	public NPCProfile(final String name, final UUID uuid, final UUID skinUUID) {
		this();
		this.uuid = uuid;
		this.name = name;
		addProperties(this, skinUUID);
	}
	
	@Override
	public UUID getId() {
		return uuid;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isComplete() {
		return (uuid != null) && (StringUtils.isNotBlank(getName()));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} if (!(o instanceof GameProfile)) {
			return false;
		}

		GameProfile that = (GameProfile) o;

		if (uuid != null ? !uuid.equals(that.getId()) : that.getId() != null) {
			return false;
		} if (name != null ? !name.equals(that.getName()) : that.getName() != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = uuid != null ? uuid.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", uuid)
				.append("name", name)
				.append("properties", getProperties())
				.append("legacy", isLegacy()).toString();
	}
	
	private static void addProperties(GameProfile profile, UUID id) {
		String uuid = id.toString().replaceAll("-", "");
		try {
			// Get the name from SwordPVP
			URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
			URLConnection uc = url.openConnection();
			uc.setUseCaches(false);
			uc.setDefaultUseCaches(false);
			uc.addRequestProperty("User-Agent", "Mozilla/5.0");
			uc.addRequestProperty("Cache-Control",
					"no-cache, no-store, must-revalidate");
			uc.addRequestProperty("Pragma", "no-cache");

			// Parse it
			Scanner scanner = new Scanner(uc.getInputStream(), "UTF-8");
			String json = scanner.useDelimiter("\\A").next();
			scanner.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(json);
			JSONArray properties = (JSONArray) ((JSONObject) obj)
					.get("properties");
			for (int i = 0; i < properties.size(); i++) {
				try {
					JSONObject property = (JSONObject) properties.get(i);
					String name = (String) property.get("name");
					String value = (String) property.get("value");
					String signature = property.containsKey("signature") ? (String) property
							.get("signature") : null;
					if (signature != null) {
						profile.getProperties().put(name,
								new Property(name, value, signature));
					} else {
						profile.getProperties().put(name,
								new Property(value, name));
					}
				} catch (Exception e) {
					Bukkit.getLogger().log(Level.WARNING, "Failed to apply auth property", e);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private static String getUUID(String name) {
		return Bukkit.getOfflinePlayer(name).getUniqueId().toString().replaceAll("-", "");
	}

	private static UUID parseUUID(String uuidStr) {
		// Split uuid in to 5 components
		String[] uuidComponents = new String[] { uuidStr.substring(0, 8),
				uuidStr.substring(8, 12), uuidStr.substring(12, 16),
				uuidStr.substring(16, 20),
				uuidStr.substring(20, uuidStr.length()) };

		// Combine components with a dash
		StringBuilder builder = new StringBuilder();
		for (String component : uuidComponents) {
			builder.append(component).append('-');
		}

		// Correct uuid length, remove last dash
		builder.setLength(builder.length() - 1);
		return UUID.fromString(builder.toString());
	}
}