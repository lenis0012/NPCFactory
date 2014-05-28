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
		nmsPath.a();
		
		double dx = vec.a - entity.locX;
		double dy = vec.b - entity.locY;
		double dz = vec.c - entity.locZ;
		
		entity.getNPC().lookAt(new Location(entity.getBukkitEntity().getWorld(), vec.a, 0, vec.c));
		entity.move(dx, dy, dz);
		entity.checkMovement(dx, dy, dz);
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