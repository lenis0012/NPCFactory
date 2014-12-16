package com.lenis0012.bukkit.npcfactory.util;

import lombok.AccessLevel;
import lombok.Getter;

public class MCVersion {
	private static int COMPATIBLE_VERSION = 1000;
	public static final MCVersion UNKOWN = new MCVersion("", 1000);
	public static final MCVersion v1_7 = new MCVersion("1_7_R1", 0);
	public static final MCVersion v1_8_HACK = new MCVersion("1_7_R4", 1);
	public static final MCVersion v1_8 = new MCVersion("1_8_R1", 2);

	private final String name;
	@Getter(AccessLevel.PRIVATE)
	private final int version;
	@Getter(AccessLevel.PRIVATE)
	private boolean compatible;
	
	private MCVersion(String name, int version) {
		this.name = name;
		this.version = version;
		try {
			if(!name.isEmpty()) {
				Class.forName("net.minecraft.server." + name + ".World");
			}

			this.compatible = true;
			COMPATIBLE_VERSION = version;
		} catch(Exception e) {
			this.compatible = false;
		}
	}

	public static boolean isCompatible(MCVersion version) {
		return version.isCompatible();
	}

	public static boolean lt(MCVersion version) {
		return COMPATIBLE_VERSION < version.getVersion();
	}

	public static boolean gt(MCVersion version) {
		return COMPATIBLE_VERSION > version.getVersion();
	}
}
