package com.lenis0012.bukkit.npc;

import net.minecraft.server.v1_7_R3.PathEntity;
import net.minecraft.server.v1_7_R3.Vec3D;

import org.bukkit.Location;

public class NPCPath {
	private final NPCEntity entity;
	private final PathEntity nmsPath;
	private final float speed;
	
	protected NPCPath(NPCEntity entity, PathEntity nmsPath, double speed) {
		this.entity = entity;
		this.nmsPath = nmsPath;
		this.speed = (float) speed;
	}
	
	public boolean update() {
		if(nmsPath.b()) {
			return false;
		}
		
		Vec3D vec = nmsPath.a(entity);
		Vec3D endVec = nmsPath.a(entity, nmsPath.e() - 1);
		entity.getNPC().lookAt(new Location(entity.getBukkitEntity().getWorld(), endVec.a, endVec.b, endVec.c));
		entity.i(speed);
		entity.e((float) vec.a, (float) vec.c);
		return true;
	}
	
	public static NPCPath find(NPCEntity entity, Location to, double range, double speed) {
		try {
			PathEntity path = entity.world.a(entity, to.getBlockX(), to.getBlockY(), to.getBlockZ(), (float) range, true, false, false, true);
			if(path != null) {
				return new NPCPath(entity, path, speed);
			} else {
				return null;
			}
		} catch(Exception e) {
			return null;
		}
	}
}