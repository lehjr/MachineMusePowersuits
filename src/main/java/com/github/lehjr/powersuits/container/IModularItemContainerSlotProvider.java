package com.github.lehjr.powersuits.container;

import com.github.lehjr.numina.util.client.gui.slot.IHideableSlot;
import com.google.common.collect.HashBiMap;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public interface IModularItemContainerSlotProvider {
    Container getContainer();

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

    default List<Slot> getSlotsForEquipmentType(EquipmentSlotType type) {
        List<Slot> slots = new ArrayList<>();
        Pair<Integer, Integer> range = getRangeForEquipmentSlot(type);
        if (range != null) {
            IntStream.range(range.getLeft(), range.getRight()).forEach(n -> slots.add(getContainer().getSlot(n)));
        }
        return slots;
    }

    default void enableOrDisableSlotsForRange(int start, int end, boolean enable) {
        IntStream.range(start, end).forEach(n -> {
            Slot slot = getContainer().getSlot(n);
            if (slot instanceof IHideableSlot) {
                if (enable) {
                    ((IHideableSlot) slot).enable();
                } else {
                    ((IHideableSlot) slot).disable();
                    slot.x = -1000;
                    slot.y = -1000;
                }
            }
        });
    }

    default void disableSlotsForEquipmentSlot(EquipmentSlotType type) {
        Pair<Integer, Integer> range = getEquipmentSlotRangeMap().get(type);
        if (range != null) {
            enableOrDisableSlotsForRange(range.getLeft(), range.getRight(), false);
        }
    }

    @Nonnull
    default ItemStack stack(EquipmentSlotType type) {
        return getContainer().getSlot(getSlotForEquipmentType(type)).getItem();
    }
}
