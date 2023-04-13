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

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.config.NuminaSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;

/**
 * Created by Claire on 8/27/2015.
 * <p>
 * Ported to Java by lehjr on 10/22/16.
 */
@OnlyIn(Dist.CLIENT)
public class Musique {
    private static HashMap<String, MovingSoundPlayer> soundMap = new HashMap<>();

    public static SoundManager mcsound() {
        return Minecraft.getInstance().getSoundManager();
    }

    public static void playClientSound(SoundEvent soundEvt, float volumeIn) {
        if (NuminaSettings.useSounds()) {
            mcsound().play(SimpleSoundInstance.forUI(soundEvt, volumeIn));
        }
    }

    public static String makeSoundString(Player player, SoundEvent soundEvt) {
        return makeSoundString(player, soundEvt.getLocation());
    }

    public static String makeSoundString(Player player, ResourceLocation soundname) {
        return player.getUUID().toString() + soundname;
    }

    public static void playerSound(Player player, SoundEvent soundEvt, SoundSource categoryIn, float volume, Float pitch, Boolean continuous) {
        pitch = (pitch != null) ? pitch : 1.0F;
        continuous = (continuous != null) ? continuous : true;
        if (NuminaSettings.useSounds() && soundEvt != null) {
            String soundID = makeSoundString(player, soundEvt);
            MovingSoundPlayer sound = soundMap.get(soundID);

            if (sound != null && (sound.isStopped() || !sound.isLooping())) {
                stopPlayerSound(player, soundEvt);
                sound = null;
            }
            if (sound != null) {
                sound.updateVolume(volume).updatePitch(pitch).updateRepeat(continuous);
            } else {
                NuminaLogger.logDebug("New sound: " + soundEvt.getLocation());
                MovingSoundPlayer newsound = new MovingSoundPlayer(soundEvt, categoryIn, player, volume * 2.0f, pitch, continuous);
                mcsound().play(newsound);
                soundMap.put(soundID, newsound);
            }
        }
    }

    public static void stopPlayerSound(Player player, SoundEvent soundEvt) {
        if (NuminaSettings.useSounds()) {
            String soundID = makeSoundString(player, soundEvt);
            System.out.println("soundId: " + soundID);

            MovingSoundPlayer sound = soundMap.get(soundID);
            if (sound != null) {
                sound.stopPlaying();
                mcsound().stop(sound);
            }
            soundMap.remove(soundID);
             NuminaLogger.logDebug("Sound stopped: " + soundEvt.getLocation());
        }
    }
}