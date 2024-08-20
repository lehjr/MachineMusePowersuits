package com.lehjr.powersuits.common.registration;

import com.lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class MPSArmorMaterial {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, MPSConstants.MOD_ID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> NOTHING = ARMOR_MATERIALS.register("nothing", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 0);
        map.put(ArmorItem.Type.LEGGINGS, 0);
        map.put(ArmorItem.Type.CHESTPLATE, 0);
        map.put(ArmorItem.Type.HELMET, 0);
        map.put(ArmorItem.Type.BODY, 0);
    }), 15, SoundEvents.ARMOR_EQUIP_GENERIC, () -> Ingredient.of(Items.AIR),
            List.of(new ArmorMaterial.Layer(ResourceLocation.parse("leather"), "", true),
                    new ArmorMaterial.Layer(ResourceLocation.parse("leather"), "_overlay", false)),
            0F, 0.0F));
}
