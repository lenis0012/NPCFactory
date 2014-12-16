package com.lenis0012.bukkit.npcfactory.util;

import com.lenis0012.bukkit.npcfactory.NPCFactory;
import com.lenis0012.bukkit.npcfactory.reflection.MethodAccessor;

import java.lang.reflect.Method;
import java.util.logging.Level;

public class ReflectionUtil {
//    private static final String MC_PACKAGE_VERSION;
    private static final String NMS_ROOT;
    private static final String CB_ROOT;

    static {
        String version = "";
        StringBuilder builder = new StringBuilder();
        for (int a = 0; a < 10; a++) {
            for(int b = 0; b < 10; b++) {
                for(int c = 0; c < 10; c++) {
                    builder.setLength(0);
                    builder.append('v').append(a).append('_').append(b).append("_R").append(c);
                    version = builder.toString();
                    if(isCompatible(version)) {
                        a = b = c = 10;
                    }
                }
            }
        }

//        MC_PACKAGE_VERSION = version;
        NMS_ROOT = "net.minecraft.server." + version + ".";
        CB_ROOT = "org.bukkit.craftbukkit." + version + ".";
    }

    private static boolean isCompatible(String version) {
        try {
            Class.forName("net.minecraft.server." + version + ".World");
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public static Class<?> getNMSClass(String name) {
        return getClass(NMS_ROOT + name);
    }

    public static Class<?> getCBClass(String name) {
        return getClass(CB_ROOT + name);
    }

    public static MethodAccessor getAccessor(Method method) {
        return new MethodAccessor();
    }

    public static Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch(ClassNotFoundException e) {
            NPCFactory.getLogger().log(Level.SEVERE, "Failed to find class named " + name + "!");
            return null;
        }
    }
}
