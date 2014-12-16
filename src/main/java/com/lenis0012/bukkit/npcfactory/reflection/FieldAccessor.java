package com.lenis0012.bukkit.npcfactory.reflection;

import com.lenis0012.bukkit.npcfactory.NPCFactory;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.logging.Level;

public class FieldAccessor {
    @Getter
    private final Field field;

    protected FieldAccessor(Field field) {
        this.field = field;
        field.setAccessible(true);
    }

    public void set(Object instance, Object value) {
        try {
            field.set(this, value);
        } catch(IllegalAccessException e) {
            NPCFactory.getLogger().log(Level.SEVERE, "Failed to access field", e);
        }
    }

    public <T> T get(Object instance, Class<T> type) {
        return type.cast(get(instance));
    }

    public Object get(Object instance) {
        try {
            return field.get(instance);
        } catch(IllegalAccessException e) {
            NPCFactory.getLogger().log(Level.SEVERE, "Failed to access field", e);
            return null;
        }
    }
}
