package com.lenis0012.bukkit.npc;

import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.NetworkManager;
import net.minecraft.server.v1_7_R4.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;

public class NPCPlayerConnection extends PlayerConnection {

	public NPCPlayerConnection(NetworkManager networkmanager, EntityPlayer entityplayer) {
		super(((CraftServer) Bukkit.getServer()).getServer(), networkmanager, entityplayer);
	}
}