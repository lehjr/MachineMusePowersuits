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

package lehjr.powersuits.client.sound;

import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.neoforged.api.distmarker.Dist;

@Mod.EventBusSubscriber(modid = MPSConstants.MOD_ID, value = Dist.CLIENT)
public class MPSSoundDictionary {
    static {
        new MPSSoundDictionary();
    }

    public static final DeferredRegister<SoundEvent> MPS_SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MPSConstants.MOD_ID);
    public static final RegistryObject<SoundEvent> SOUND_EVENT_GLIDER = MPS_SOUND_EVENTS.register("glider",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MPSConstants.MOD_ID, "glider")));

    public static final RegistryObject<SoundEvent> SOUND_EVENT_JETBOOTS = MPS_SOUND_EVENTS.register("jet_boots",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MPSConstants.MOD_ID, "jet_boots")));

    public static final RegistryObject<SoundEvent> SOUND_EVENT_JETPACK = MPS_SOUND_EVENTS.register("jetpack",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MPSConstants.MOD_ID, "jetpack")));

    public static final RegistryObject<SoundEvent> SOUND_EVENT_JUMP_ASSIST = MPS_SOUND_EVENTS.register("jump_assist",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MPSConstants.MOD_ID, "jump_assist")));

    public static final RegistryObject<SoundEvent> SOUND_EVENT_SWIM_ASSIST = MPS_SOUND_EVENTS.register("swim_assist",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MPSConstants.MOD_ID, "swim_assist")));

    public static final RegistryObject<SoundEvent> SOUND_EVENT_ELECTROLYZER = MPS_SOUND_EVENTS.register("water_electrolyzer",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MPSConstants.MOD_ID, "water_electrolyzer")));
}