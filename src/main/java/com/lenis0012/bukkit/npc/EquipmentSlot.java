package com.lenis0012.bukkit.npc;

public enum EquipmentSlot {
    HELMET(4),
    CHESTPLATE(3),
    LEGGINGS(2),
    BOOTS(1),
    HAND(0);

    private final int id;

    private EquipmentSlot(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
