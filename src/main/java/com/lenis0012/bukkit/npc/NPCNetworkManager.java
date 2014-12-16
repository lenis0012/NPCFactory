package com.lenis0012.bukkit.npc;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import net.minecraft.server.v1_8_R1.EnumProtocolDirection;
import net.minecraft.server.v1_8_R1.NetworkManager;

import java.lang.reflect.Field;
import java.net.SocketAddress;

public class NPCNetworkManager extends NetworkManager {

    public NPCNetworkManager() {
        super(EnumProtocolDirection.SERVERBOUND);

        try {
            Field channel = NetworkManager.class.getDeclaredField("i");
            Field address = NetworkManager.class.getDeclaredField("j");

            channel.setAccessible(true);
            address.setAccessible(true);

            Channel parent = new NPCChannel(null);
            try {
                Field protocolVersion = NetworkManager.class.getDeclaredField("protocolVersion");
                parent.attr(((AttributeKey<Integer>) protocolVersion.get(null))).set(5);
            } catch(NoSuchFieldException ignored) { // This server isn't spigot, we're good.
            }
            channel.set(this, parent);
            address.set(this, new SocketAddress() {
                private static final long serialVersionUID = 6994835504305404545L;
            });
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
