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

package com.github.lehjr.powersuits.client.sound;

import com.github.lehjr.powersuits.constants.MPSConstants;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MPSConstants.MOD_ID, value = Dist.CLIENT)
public class MPSSoundDictionary {
    public static SoundEvent GLIDER = initSound("glider");
    public static SoundEvent JETBOOTS = initSound("jet_boots");
    public static SoundEvent JETPACK = initSound("jetpack");
    public static SoundEvent JUMP_ASSIST = initSound("jump_assist");
    public static SoundEvent SWIM_ASSIST = initSound("swim_assist");
    public static SoundEvent ELECTROLYZER = initSound("water_electrolyzer");

    static {
        new MPSSoundDictionary();
    }

    @SubscribeEvent
    public static void registerSoundEvent(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(
                GLIDER,
                JETBOOTS,
                JETPACK,
                JUMP_ASSIST,
                SWIM_ASSIST,
                ELECTROLYZER);
    }

    private static SoundEvent initSound(String soundName) {
        ResourceLocation location = new ResourceLocation(MPSConstants.MOD_ID, soundName);
        SoundEvent event = new SoundEvent(location).setRegistryName(location);
        return event;
    }
}
