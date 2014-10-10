package com.lenis0012.bukkit.npc;

import net.minecraft.server.v1_7_R4.NetworkManager;
import net.minecraft.util.io.netty.channel.AbstractChannel;
import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.io.netty.channel.ChannelConfig;
import net.minecraft.util.io.netty.channel.ChannelMetadata;
import net.minecraft.util.io.netty.channel.ChannelOutboundBuffer;
import net.minecraft.util.io.netty.channel.DefaultChannelConfig;
import net.minecraft.util.io.netty.channel.EventLoop;

import java.net.SocketAddress;

public class NPCChannel extends AbstractChannel {
	private final ChannelConfig config = new DefaultChannelConfig(this);
	
	protected NPCChannel(Channel parent) {
		super(parent);
                attr(NetworkManager.protocolVersion).set(5); // 1.7
	}

	@Override
	public ChannelConfig config() {
		config.setAutoRead(true);
		return config;
	}

	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public boolean isOpen() {
		return false;
	}

	@Override
	public ChannelMetadata metadata() {
		return null;
	}

	@Override
	protected void doBeginRead() throws Exception {
	}

	@Override
	protected void doBind(SocketAddress arg0) throws Exception {
	}

	@Override
	protected void doClose() throws Exception {
	}

	@Override
	protected void doDisconnect() throws Exception {
	}

	@Override
	protected void doWrite(ChannelOutboundBuffer arg0) throws Exception {
	}

	@Override
	protected boolean isCompatible(EventLoop arg0) {
		return true;
	}

	@Override
	protected SocketAddress localAddress0() {
		return null;
	}

	@Override
	protected AbstractUnsafe newUnsafe() {
		return null;
	}

	@Override
	protected SocketAddress remoteAddress0() {
		return null;
	}
}
