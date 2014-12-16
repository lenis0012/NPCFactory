package com.lenis0012.bukkit.npcfactory.entity;

import com.google.common.collect.Maps;
import com.lenis0012.bukkit.npc.EquipmentSlot;
import com.lenis0012.bukkit.npc.NPCAnimation;
import com.lenis0012.bukkit.npcfactory.NPCFactory;
import com.lenis0012.bukkit.npcfactory.reflection.ClassAccessor;
import com.lenis0012.bukkit.npcfactory.reflection.MethodAccessor;
import com.lenis0012.bukkit.npcfactory.reflection.NMSClassAccessor;
import com.lenis0012.bukkit.npcfactory.util.ReflectionUtil;
import lombok.Getter;
import lombok.Setter;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;

public class NPCEntity implements MethodInterceptor {
    private static final Map<String, MethodAccessor> methods = Maps.newHashMap();

    static {
        for(Method method : NPCEntity.class.getMethods()) {
            String name = method.getName();
            MethodAccessor methodAccessor = ReflectionUtil.getAccessor(method);
            if(methods.containsKey(name)) {
                NPCFactory.getLogger().log(Level.SEVERE, "Duplicate method in NPCEntity, method " + method.getName() + " exists more than once!");
                continue;
            }

            methods.put(name, methodAccessor);
        }
    }

    private static final ClassAccessor accessor = new NMSClassAccessor("EntityPlayer");
    private final Object SUPER = new Object();

    // Proxy information (cahce)
    private Object instance;
    private MethodProxy proxy;

    @Getter @Setter
    private boolean godMode;

    @Getter @Setter
    private boolean gravity;

    @Getter @Setter
    private Entity target;

    @Override
    public Object intercept(Object instance, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        this.instance = instance;
        this.proxy = proxy;

        String name = method.getName();
        MethodAccessor methodAccessor = methods.get(name);
        if(methodAccessor != null) {
            // TODO: Invoke, but needs code in MethodAccessor
        }

        return proxy.invokeSuper(instance, args);
    }

    public Player getBukkitEntity() {
        // TODO: Everything...
        throw new UnsupportedOperationException("This method is not done yet");
    }

    public void setLying(double x, double y, double z) {
        // TODO: Everything...
        throw new UnsupportedOperationException("This method is not done yet");
    }

    public boolean isLying() {
        // TODO: Everything...
        throw new UnsupportedOperationException("This method is not done yet");
    }

    public boolean pathfindTo(Location location) {
        // TODO: Everything...
        throw new UnsupportedOperationException("This method is not done yet");
    }

    public boolean pathfindTo(Location location, double speed) {
        // TODO: Everything...
        throw new UnsupportedOperationException("This method is not done yet");
    }

    public boolean pathfindTo(Location location, double speed, double range) {
        // TODO: Everything...
        throw new UnsupportedOperationException("This method is not done yet");
    }

    public void lookAt(Location location) {
        // TODO: Everything...
        throw new UnsupportedOperationException("This method is not done yet");
    }

    public void setYaw(float yaw) {
        // TODO: Everything...
        throw new UnsupportedOperationException("This method is not done yet");
    }

    public void playAnimation(NPCAnimation animation) {
        // TODO: Everything...
        throw new UnsupportedOperationException("This method is not done yet");
    }

    public void setEquipment(EquipmentSlot slot, ItemStack item) {
        // TODO: Everything...
        throw new UnsupportedOperationException("This method is not done yet");
    }

    public boolean getEntityCollision() {
        return false;
    }

    public void setEntityCollision(boolean entityCollision) {
    }

    public boolean isCollisionEnabled() {
        // TODO: Everything...
        throw new UnsupportedOperationException("This method is not done yet");
    }

    public void setCollisionEnabled(boolean collisionEnabled) {
        // TODO: Everything...
        throw new UnsupportedOperationException("This method is not done yet");
    }
}
