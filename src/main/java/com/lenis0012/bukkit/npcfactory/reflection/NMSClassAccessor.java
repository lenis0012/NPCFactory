package com.lenis0012.bukkit.npcfactory.reflection;

import com.lenis0012.bukkit.npcfactory.util.ReflectionUtil;

public class NMSClassAccessor extends ClassAccessor {

    public NMSClassAccessor(String name) {
        super(ReflectionUtil.getNMSClass(name));
    }
}
