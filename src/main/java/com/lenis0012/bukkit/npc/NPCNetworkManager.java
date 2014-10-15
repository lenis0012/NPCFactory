package com.lenis0012.bukkit.npc;

import java.lang.reflect.Field;
import java.net.SocketAddress;

import net.minecraft.server.v1_7_R4.NetworkManager;
import net.minecraft.util.io.netty.channel.Channel;

public class NPCNetworkManager extends NetworkManager {

	public NPCNetworkManager() {
		super(false);
		
		try {
			Field channel = NetworkManager.class.getDeclaredField("m");
			Field address = NetworkManager.class.getDeclaredField("n");
			
			channel.setAccessible(true);
			address.setAccessible(true);
			
			Channel parent = new NPCChannel(null);
			parent.attr(new AttributeKey<Integer>("protocol_version")).set(17); // The value is not very important.

			channel.set(this, parent);
			address.set(this, new SocketAddress() {
				private static final long serialVersionUID = 6994835504305404545L;
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
