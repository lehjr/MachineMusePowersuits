package com.github.lehjr.powersuits.dev.crafting.container;

import com.google.common.collect.HashBiMap;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Slot;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public interface IModularItemContainerSlotProvider {
    Map<EquipmentSlotType, Pair<Integer, Integer>> getEquipmentSlotRangeMap();

    HashBiMap<EquipmentSlotType, Integer> getEquipmentSlotTypeMap();

    default Pair<Integer, Integer> getRangeForEquipmentSlot(EquipmentSlotType type) {
        return getEquipmentSlotRangeMap().get(type);
    }

    default Integer getSlotForEquipmentType(EquipmentSlotType type) {
        return getEquipmentSlotTypeMap().get(type);
    }

    default void addRangeForEquipmentSlot(EquipmentSlotType type, int start, int end) {
        getEquipmentSlotRangeMap().put(type, Pair.of(start, end));
    }

    default void addSlotForEquipment(EquipmentSlotType type, Integer containerIndex) {
        getEquipmentSlotTypeMap().put(type, containerIndex);
    }


}
