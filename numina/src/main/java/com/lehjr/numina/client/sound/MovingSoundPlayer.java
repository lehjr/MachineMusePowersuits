package com.lehjr.numina.client.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MovingSoundPlayer extends AbstractTickableSoundInstance {
    private final Player player;

    /*
     * Important porting note:
     * Sounds are now event based instead of resource location.
     */
    public MovingSoundPlayer(SoundEvent soundIn,
                             SoundSource categoryIn,
                             Player playerIn,
                             float newvolume,
                             float pitchIn,
                             boolean loopingIn) {

        super(soundIn, categoryIn, RandomSource.create());
        this.player = playerIn;
        this.pitch = pitchIn;
        this.volume = newvolume;
        this.looping = loopingIn;
    }

    @Override
    public void tick() {
        this.x = (float) this.player().getX();
        this.y = (float) this.player().getY();
        this.z = (float) this.player().getZ();
    }

    public Player player() {
        return this.player;
    }

    public MovingSoundPlayer updatePitch(float newPitch) {
        this.pitch = newPitch;
        return this;
    }

    public MovingSoundPlayer updateVolume(float newVolume) {
        this.volume = (4.0f * this.volume + newVolume) / 5.0f;
        return this;
    }

    public MovingSoundPlayer updateRepeat(boolean newLooping) {
        this.looping = newLooping;
        return this;
    }

    @Override
    public Attenuation getAttenuation() {
        return Attenuation.LINEAR;
    }

    public void stopPlaying() {
        this.stop();
    }
}
