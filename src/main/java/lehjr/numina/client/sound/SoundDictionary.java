/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.numina.client.sound;

import lehjr.numina.common.constants.NuminaConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = NuminaConstants.MOD_ID, value = Dist.CLIENT)
public class SoundDictionary {
    static {
        new SoundDictionary();
    }
   public static final DeferredRegister<SoundEvent> NUMINA_SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, NuminaConstants.MOD_ID);
    public static final RegistryObject<SoundEvent> SOUND_EVENT_GUI_INSTALL = NUMINA_SOUND_EVENTS.register("gui_install",
            () -> new SoundEvent(new ResourceLocation(NuminaConstants.MOD_ID, "gui_install")));

    public static final RegistryObject<SoundEvent> SOUND_EVENT_GUI_SELECT = NUMINA_SOUND_EVENTS.register("gui_select",
            () -> new SoundEvent(new ResourceLocation(NuminaConstants.MOD_ID, "gui_select")));


    public static final RegistryObject<SoundEvent> SOUND_EVENT_BOOP = NUMINA_SOUND_EVENTS.register("boop",
            () -> new SoundEvent(new ResourceLocation(NuminaConstants.MOD_ID, "boop")));
}