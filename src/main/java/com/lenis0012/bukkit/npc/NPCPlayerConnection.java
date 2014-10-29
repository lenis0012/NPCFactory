package com.lenis0012.bukkit.npc;

import net.minecraft.server.v1_7_R4.*;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;

public class NPCPlayerConnection extends PlayerConnection {

	public NPCPlayerConnection(NetworkManager networkmanager, EntityPlayer entityplayer) {
		super(((CraftServer) Bukkit.getServer()).getServer(), networkmanager, entityplayer);
	}
	
		@Override
	public void disconnect(String s) {
	}

	@Override
	public void a(PacketPlayInSteerVehicle packetplayinsteervehicle) {

	}

	@Override
	public void a(PacketPlayInFlying packetplayinflying) {
	}

	@Override
	public void a(PacketPlayInBlockDig packetplayinblockdig) {
	}

	@Override
	public void a(PacketPlayInBlockPlace packetplayinblockplace) {
	}

	@Override
	public void a(IChatBaseComponent ichatbasecomponent) {
	}

	@Override
	public void sendPacket(Packet packet) {
	}

	@Override
	public void a(PacketPlayInChat packetplayinchat) {
	}

	@Override
	public void a(PacketPlayInArmAnimation packetplayinarmanimation) {
	}

	@Override
	public void a(PacketPlayInEntityAction packetplayinentityaction) {
	}

	@Override
	public void a(PacketPlayInUseEntity packetplayinuseentity) {
	}

	@Override
	public void a(PacketPlayInClientCommand packetplayinclientcommand) {
	}

	@Override
	public void a(PacketPlayInCloseWindow packetplayinclosewindow) {
	}

	@Override
	public void a(PacketPlayInWindowClick packetplayinwindowclick) {
	}

	@Override
	public void a(PacketPlayInEnchantItem packetplayinenchantitem) {
	}

	@Override
	public void a(PacketPlayInSetCreativeSlot packetplayinsetcreativeslot) {
	}

	@Override
	public void a(PacketPlayInTransaction packetplayintransaction) {
	}

	@Override
	public void a(PacketPlayInUpdateSign packetplayinupdatesign) {
	}

	@Override
	public void a(PacketPlayInKeepAlive packetplayinkeepalive) {

	}

	@Override
	public void a(PacketPlayInAbilities packetplayinabilities) {

	}

	@Override
	public void a(PacketPlayInTabComplete packetplayintabcomplete) {
	}

	@Override
	public void a(PacketPlayInSettings packetplayinsettings) {
	}

	@Override
	public void a(PacketPlayInCustomPayload packetplayincustompayload) {
	}

	@Override
	public void a(EnumProtocol enumprotocol, EnumProtocol enumprotocol1) {
	}
}
