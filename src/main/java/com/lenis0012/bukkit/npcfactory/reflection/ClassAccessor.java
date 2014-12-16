package com.lenis0012.bukkit.npcfactory.reflection;

import com.google.common.collect.Maps;
import com.lenis0012.bukkit.npcfactory.NPCFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;

public class ClassAccessor {
    private final Class<?> handle;
    private final Map<String, FieldAccessor> fieldAccessorMap = Maps.newHashMap();
    private final Map<String, MethodAccessor> methodAccessorMap = Maps.newHashMap();

    public ClassAccessor(Class<?> clazz) {
        this.handle = clazz;
        scanFields(clazz);
    }

    private void scanFields(Class<?> fromClass) {
        if(fromClass == null) {
            return;
        }

        // Scan for all private & non-private fields in class
        for(Field field : fromClass.getDeclaredFields()) {
            if(fieldAccessorMap.containsKey(field.getName())) {
                NPCFactory.getLogger().log(Level.WARNING, "Found a duplicate field named " + field.getName() + " for class " + handle.getName() + ", ignoring...");
                continue; // Skip if already in list, we want top-classes first
            }

            // Construct accessor and add to list
            FieldAccessor fieldAccessor = new FieldAccessor(field);
            fieldAccessorMap.put(field.getName(), fieldAccessor);
        }

        scanFields(fromClass.getSuperclass());
    }

    private void scanMethods(Class<?> fromClass) {
        if(fromClass == null) {
            return;
        }

        for(Method method : fromClass.getDeclaredMethods()) {
            if(methodAccessorMap.containsKey(method.getName())) {
                // Duplicates are expected here
                continue;
            }

            // Construct accessor and add to list
            MethodAccessor methodAccessor = new MethodAccessor();
            methodAccessorMap.put(method.getName(), methodAccessor);
        }

        scanFields(fromClass.getSuperclass());
    }

    /**
     * Get a field accessor of a required field.
     *
     * @param name of the field
     * @return The field accessor, IllegalArgumentException if not found
     */
    public FieldAccessor getField(String name) {
        FieldAccessor fieldAccessor = fieldAccessorMap.get(name.toLowerCase());
        if(fieldAccessor == null) {
            throw new IllegalArgumentException("Field " + name + " in class " + handle.getName() + " could not be found!");
        }

        return fieldAccessor;
    }

    /**
     * Get field accessor for all fields in the class.
     *
     * @return A list of field accessor for all fields
     */
    public Collection<FieldAccessor> getFields() {
        return fieldAccessorMap.values();
    }

    /**
     * Get a field value from a requested field and cast it.
     *
     * @param instance of the class
     * @param field name of the actual field
     * @param type to cast the outcome with
     * @param <T>
     * @return Field value of requested field, null if not found
     */
    public <T> T getFieldValue(Object instance, String field, Class<T> type) {
        return getField(field).get(instance, type);
    }

    /**
     * Get a raw field value from a requested field
     *
     * @param instance of the class
     * @param field name of the the actual field
     * @return Field value as an instance of Object, null if not found
     */
    public Object getFieldValue(Object instance, String field) {
        return getField(field).get(instance);
    }

    /**
     * Set the value of a requested field to a custom value.
     *
     * @param instance of the class
     * @param field name of the actual field
     * @param value to set the field to
     */
    public void setFieldValue(Object instance, String field, Object value) {
        getField(field).set(instance, value);
    }
}
