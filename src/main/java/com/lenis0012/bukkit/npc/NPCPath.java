package com.lenis0012.bukkit.npc;

import net.minecraft.server.v1_8_R1.*;
import org.bukkit.Location;

public class NPCPath {
    private final NPCEntity entity;
    private final PathEntity nmsPath;
    private final double speed;
    private double progress;
    private Vec3D currentPoint;

    protected NPCPath(NPCEntity entity, PathEntity nmsPath, double speed) {
        this.entity = entity;
        this.nmsPath = nmsPath;
        this.speed = speed;
        this.progress = 0.0;
        this.currentPoint = nmsPath.a(entity);
    }

    public boolean update() {
        int current = MathHelper.floor(progress);
        double d = progress - current;
        double d1 = 1 - d;
        if(d + speed < 1) {
            double dx = (currentPoint.a - entity.locX) * speed;
            double dz = (currentPoint.c - entity.locZ) * speed;

            entity.move(dx, 0, dz);
            entity.checkMovement(dx, 0, dz);
            progress += speed;
        } else {
            //First complete old point.
            double bx = (currentPoint.a - entity.locX) * d1;
            double bz = (currentPoint.c - entity.locZ) * d1;

            //Check if new point exists
            nmsPath.a();
            if(!nmsPath.b()) {
                //Append new movement
                this.currentPoint = nmsPath.a(entity);
                double d2 = speed - d1;

                double dx = bx + ((currentPoint.a - entity.locX) * d2);
                double dy = currentPoint.b - entity.locY; //Jump if needed to reach next block.
                double dz = bz + ((currentPoint.c - entity.locZ) * d2);

                entity.move(dx, dy, dz);
                entity.checkMovement(dx, dy, dz);
                progress += speed;
            } else {
                //Complete final movement
                entity.move(bx, 0, bz);
                entity.checkMovement(bx, 0, bz);
                return false;
            }
        }

        return true;
    }

    public static NPCPath find(NPCEntity entity, Location to, double range, double speed) {
        if(speed > 1) {
            throw new IllegalArgumentException("Speed cannot be higher than 1!");
        }

        try {
            BlockPosition posFrom = new BlockPosition(entity);
            BlockPosition posTo = new BlockPosition(to.getX(), to.getY(), to.getZ());
            int k = (int) (range + 8.0);

            ChunkCache chunkCache = new ChunkCache(entity.world, posFrom.a(-k, -k, -k), posFrom.a(k, k, k), 0);
            PathEntity path = entity.getPathfinder().a(chunkCache, entity, posTo, (float) range);
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
