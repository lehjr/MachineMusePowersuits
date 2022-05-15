package com.lehjr.numina.common.entity;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.base.NuminaObjects;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NuminaConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class EntityAttributes {
    @SubscribeEvent
    public static void EntityAttributeCreationEvent(EntityAttributeCreationEvent event) {
        event.put(NuminaObjects.ARMOR_STAND__ENTITY_TYPE.get(), NuminaArmorStand.createAttributes().build());
    }
}
