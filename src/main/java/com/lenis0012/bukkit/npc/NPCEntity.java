package com.lenis0012.bukkit.npc;

import com.lenis0012.bukkit.npc.event.NPCDamageEvent;
import com.lenis0012.bukkit.npc.event.NPCDespawnEvent;
import com.lenis0012.bukkit.npc.event.NPCInteractEvent;
import net.minecraft.server.v1_8_R1.*;
import net.minecraft.server.v1_8_R1.Entity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R1.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class NPCEntity extends EntityPlayer implements NPC {

    private boolean entityCollision = true;
    private boolean godmode = true;
    private boolean gravity = true;
    private boolean lying = false;

    private org.bukkit.entity.Entity target;
    private NPCPath path;

    public NPCEntity(World world, Location location, NPCProfile profile, NPCNetworkManager networkManager) {
        super(((CraftServer) Bukkit.getServer()).getServer(), ((CraftWorld) world).getHandle(), profile.getHandle(), new PlayerInteractManager(((CraftWorld) world).getHandle()));
        playerInteractManager.b(EnumGamemode.SURVIVAL);
        this.playerConnection = new NPCPlayerConnection(networkManager, this);
        this.fauxSleeping = true;
        this.bukkitEntity = new CraftPlayer((CraftServer) Bukkit.getServer(), this);

        WorldServer worldServer = ((CraftWorld) world).getHandle();
        setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        worldServer.addEntity(this);
        worldServer.players.remove(this);
    }

    @Override
    public CraftPlayer getBukkitEntity() {
        return (CraftPlayer) bukkitEntity;
    }

    @Override
    public boolean isGravity() {
        return gravity;
    }

    @Override
    public void setGravity(boolean gravity) {
        this.gravity = gravity;
    }

    @Override
    public boolean isGodmode() {
        return godmode;
    }

    @Override
    public void setGodmode(boolean godmode) {
        this.godmode = godmode;
    }

    @Override
    public boolean isLying() {
        return lying;
    }

    /**
     * Path-finding methods.
     */
    @Override
    public boolean pathfindTo(Location location) {
        return pathfindTo(location, 0.2);
    }

    @Override
    public boolean pathfindTo(Location location, double speed) {
        return pathfindTo(location, speed, 30.0);
    }

    @Override
    public boolean pathfindTo(Location location, double speed, double range) {
        NPCPath path = NPCPath.find(this, location, range, speed);
        return (this.path = path) != null;
    }

    /**
     * Look at functions.
     */
    @Override
    public void setTarget(org.bukkit.entity.Entity target) {
        this.target = target;
        lookAt(target.getLocation());
    }

    @Override
    public org.bukkit.entity.Entity getTarget() {
        return target;
    }

    @Override
    public void lookAt(Location location) {
        setYaw(getLocalAngle(new Vector(locX, 0, locZ), location.toVector()));
    }

    @Override
    public void setYaw(float yaw) {
        this.yaw = yaw;
        this.aP = yaw;
        this.aI = yaw;
    }

    private float getLocalAngle(Vector point1, Vector point2) {
        double dx = point2.getX() - point1.getX();
        double dz = point2.getZ() - point1.getZ();
        float angle = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90;
        if (angle < 0) {
            angle += 360.0F;
        }
        return angle;
    }

    /**
     * Packet methods
     */
    @Override
    public void setLying(double x, double y, double z) {
        if (!lying) {
            broadcastLocalPacket(new PacketPlayOutBed(getBukkitEntity().getHandle(), new BlockPosition((int) x, (int) y, (int) z)));
            lying = true;
        } else if (((Double) x == null && (Double) y == null && (Double) z == null)) {
            broadcastLocalPacket(new PacketPlayOutAnimation(this, 2));
            lying = false;
        }
    }

    @Override
    public void playAnimation(NPCAnimation animation) {
        broadcastLocalPacket(new PacketPlayOutAnimation(this, animation.getId()));
    }

    @Override
    public void setEquipment(EquipmentSlot slot, ItemStack item) {
        broadcastLocalPacket(new PacketPlayOutEntityEquipment(getId(), slot.getId(), CraftItemStack.asNMSCopy(item)));
    }

    private final int RADIUS = Bukkit.getViewDistance() * 16;

    private void broadcastLocalPacket(Packet packet) {
        for (Player p : getBukkitEntity().getWorld().getPlayers()) {
            if (getBukkitEntity().getLocation().distanceSquared(p.getLocation()) <= RADIUS * RADIUS) {
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    /**
     * Internal methods.
     */
    @Override
    public void h() {
        super.h();
        this.C();

        if (target != null && path == null) {
            if (target.isDead() || (target instanceof Player && !((Player) target).isOnline())) {
                this.target = null;
            } else {
                Location bukkitEntityLoc = bukkitEntity.getLocation();
                Location targetLoc = target.getLocation();

                if (bukkitEntityLoc.getWorld().equals(target.getWorld()) && bukkitEntityLoc.distanceSquared(targetLoc) <= 32 * 32) {
                    lookAt(targetLoc);
                }
            }
        }

        if (path != null && !path.update()) {
            this.path = null;
        }

        BlockPosition blockPosition = new BlockPosition(MathHelper.floor(locX), MathHelper.floor(locY), MathHelper.floor(locZ));

        if (world.getType(blockPosition).getBlock().getMaterial() == Material.FIRE) {
            setOnFire(15);
        }

        // Apply velocity etc.
        this.motY = onGround ? Math.max(0.0, motY) : motY;
        move(motX, motY, motZ);
        this.motX *= 0.800000011920929;
        this.motY *= 0.800000011920929;
        this.motZ *= 0.800000011920929;

        if (gravity && !this.onGround) {
            this.motY -= 0.1; //Most random value, don't judge.
        }
    }

    @Override
    public boolean a(EntityHuman entity) {
        NPCInteractEvent event = new NPCInteractEvent(this, getBukkitEntity());
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled() && super.a(entity);
    }

    @Override
    public boolean damageEntity(DamageSource source, float damage) {
        if (godmode || noDamageTicks > 0) {
            return false;
        }

        DamageCause cause = null;
        org.bukkit.entity.Entity bEntity = null;
        if (source instanceof EntityDamageSource) {
            Entity damager = source.getEntity();
            cause = DamageCause.ENTITY_ATTACK;
            if (source instanceof EntityDamageSourceIndirect) {
                damager = ((EntityDamageSourceIndirect) source).getProximateDamageSource();
                if (damager.getBukkitEntity() instanceof ThrownPotion) {
                    cause = DamageCause.MAGIC;
                } else if (damager.getBukkitEntity() instanceof Projectile) {
                    cause = DamageCause.PROJECTILE;
                }
            }

            bEntity = damager.getBukkitEntity();
        } else if (source == DamageSource.FIRE)
            cause = DamageCause.FIRE;
        else if (source == DamageSource.STARVE)
            cause = DamageCause.STARVATION;
        else if (source == DamageSource.WITHER)
            cause = DamageCause.WITHER;
        else if (source == DamageSource.STUCK)
            cause = DamageCause.SUFFOCATION;
        else if (source == DamageSource.DROWN)
            cause = DamageCause.DROWNING;
        else if (source == DamageSource.BURN)
            cause = DamageCause.FIRE_TICK;
        else if (source == CraftEventFactory.MELTING)
            cause = DamageCause.MELTING;
        else if (source == CraftEventFactory.POISON)
            cause = DamageCause.POISON;
        else if (source == DamageSource.MAGIC) {
            cause = DamageCause.MAGIC;
        } else if (source == DamageSource.OUT_OF_WORLD) {
            cause = DamageCause.VOID;
        }

        if (cause != null) {
            NPCDamageEvent event = new NPCDamageEvent(this, bEntity, cause, (double) damage);
            Bukkit.getPluginManager().callEvent(event);
            return !event.isCancelled() && super.damageEntity(source, (float) event.getDamage());
        }

        if (bEntity == null || !super.damageEntity(source, damage)) {
            return false;
        }

        Entity e = ((CraftEntity) bEntity).getHandle();
        double d0 = e.locX - this.locX;

        double d1;
        for (d1 = e.locZ - this.locZ; d0 * d0 + d1 * d1 < 0.0001D; d1 = (Math.random() - Math.random()) * 0.01D) {
            d0 = (Math.random() - Math.random()) * 0.01D;
        }

        a(e, damage, d0, d1);
        return true;
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);

        NPCDespawnEvent deathEvent = new NPCDespawnEvent(this, NPCDespawnEvent.DespawnCause.DEATH);
        Bukkit.getPluginManager().callEvent(deathEvent);
    }

    @Override
    public boolean getEntityCollision() {
        return this.entityCollision;
    }

    @Override
    public void setEntityCollision(boolean entityCollision) {
        this.entityCollision = entityCollision;
    }

    @Override
    public void g(double x, double y, double z) {
        if (getBukkitEntity() != null && getEntityCollision()) {
            super.g(x, y, z);
        }
    }
}
