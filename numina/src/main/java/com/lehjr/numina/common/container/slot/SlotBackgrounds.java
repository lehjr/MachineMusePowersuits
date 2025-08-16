package com.lehjr.numina.common.container.slot;

import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.InventoryMenu;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class SlotBackgrounds {
    public static final ResourceLocation EMPTY_SLOT_HOE = ResourceLocation.withDefaultNamespace("item/empty_slot_hoe");
    public static final ResourceLocation EMPTY_SLOT_AXE = ResourceLocation.withDefaultNamespace("item/empty_slot_axe");
    public static final ResourceLocation EMPTY_SLOT_SWORD = ResourceLocation.withDefaultNamespace("item/empty_slot_sword");
    public static final ResourceLocation EMPTY_SLOT_SHOVEL = ResourceLocation.withDefaultNamespace("item/empty_slot_shovel");
    public static final ResourceLocation EMPTY_SLOT_PICKAXE = ResourceLocation.withDefaultNamespace("item/empty_slot_pickaxe");

    public static final Map<EquipmentSlot, ResourceLocation> ARMOR_SLOT_TEXTURES = new HashMap<>(){{
        put(EquipmentSlot.HEAD, InventoryMenu.EMPTY_ARMOR_SLOT_HELMET);
        put(EquipmentSlot.CHEST, InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE);
        put(EquipmentSlot.LEGS, InventoryMenu.EMPTY_ARMOR_SLOT_LEGGINGS);
        put(EquipmentSlot.FEET, InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS);
        put(EquipmentSlot.OFFHAND, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
        put(EquipmentSlot.MAINHAND, EMPTY_SLOT_SWORD);
    }};

    public static final Pair<ResourceLocation, ResourceLocation> getSlotBackground(EquipmentSlot slotType) {
        return Pair.of(InventoryMenu.BLOCK_ATLAS, ARMOR_SLOT_TEXTURES.get(slotType));
    }

    static final Map<ModuleCategory, Pair<ResourceLocation, ResourceLocation>> slotBackgrounds = new HashMap<>(){{
        put(ModuleCategory.PICKAXE, Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_SLOT_PICKAXE));
        put(ModuleCategory.SHOVEL, Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_SLOT_SHOVEL));
        put(ModuleCategory.AXE, Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_SLOT_AXE));
        put(ModuleCategory.HOE, Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_SLOT_HOE));

        put(ModuleCategory.ARMOR, Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD));

        put(ModuleCategory.ENERGY_STORAGE, Pair.of(NuminaConstants.LOCATION_NUMINA_GUI_TEXTURE_ATLAS, NuminaConstants.ENERGY_STORAGE_ICON));
        put(ModuleCategory.ENERGY_GENERATION, Pair.of(NuminaConstants.LOCATION_NUMINA_GUI_TEXTURE_ATLAS, NuminaConstants.LOCATION_NUMINA_GUI_TEXTURE_ATLAS));
    }};

    @Nullable
    public static Pair<ResourceLocation, ResourceLocation> getIconLocationPairForCategory(ModuleCategory category) {
        if(slotBackgrounds.containsKey(category)) {
            return slotBackgrounds.get(category);
        }
        return null;
    }
}
