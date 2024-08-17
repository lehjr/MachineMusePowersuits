package com.lehjr.numina.client.sound;

import com.lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SoundDictionary {
   public static final DeferredRegister<SoundEvent> NUMINA_SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, NuminaConstants.MOD_ID);
    public static final Supplier<SoundEvent> SOUND_EVENT_GUI_INSTALL = NUMINA_SOUND_EVENTS.register("gui_install",
            () -> SoundEvent.createFixedRangeEvent(ResourceLocation.fromNamespaceAndPath(NuminaConstants.MOD_ID, "gui_install"), 10));

    public static final Supplier<SoundEvent> SOUND_EVENT_GUI_SELECT = NUMINA_SOUND_EVENTS.register("gui_select",
            () -> SoundEvent.createFixedRangeEvent(ResourceLocation.fromNamespaceAndPath(NuminaConstants.MOD_ID, "gui_select"), 10));

    public static final Supplier<SoundEvent> SOUND_EVENT_BOOP = NUMINA_SOUND_EVENTS.register("boop",
            () -> SoundEvent.createFixedRangeEvent(ResourceLocation.fromNamespaceAndPath(NuminaConstants.MOD_ID, "boop"), 10));
}