

package com.lehjr.numina.client.sound;

import com.lehjr.numina.client.config.NuminaClientConfig;
import com.lehjr.numina.common.base.NuminaLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

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
        if (NuminaClientConfig.useSounds) {
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
        if (NuminaClientConfig.useSounds && soundEvt != null) {
            String soundID = makeSoundString(player, soundEvt);
            MovingSoundPlayer sound = soundMap.get(soundID);

            if (sound != null && (sound.isStopped() || !sound.isLooping())) {
                stopPlayerSound(soundID);
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

    static void stopPlayerSound(String soundID) {
        if (NuminaClientConfig.useSounds) {
            MovingSoundPlayer sound = soundMap.get(soundID);
            if (sound != null) {
                sound.stopPlaying();
                mcsound().stop(sound);
            }
            soundMap.remove(soundID);
        }
//             NuminaLogger.logDebug("Sound stopped: " + soundEvt.getLocation());
    }


    public static void stopPlayerSound(Player player, SoundEvent soundEvt) {
        if (NuminaClientConfig.useSounds) {
            String soundID = makeSoundString(player, soundEvt);
            stopPlayerSound(soundID);
//             NuminaLogger.logDebug("Sound stopped: " + soundEvt.getLocation());
        }
    }
}