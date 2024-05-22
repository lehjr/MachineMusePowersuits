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

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

/**
 * Ported to Java by lehjr on 10/22/16.
 */
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
        this.x = (float) this.player.getX();
        this.y = (float) this.player.getY();
        this.z = (float) this.player.getZ();
    }

    @Override
    public boolean canStartSilent() {
        return false;
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

    @Nonnull
    @Override
    public Attenuation getAttenuation() {
        return Attenuation.LINEAR;
    }

    public void stopPlaying() {
        this.stop();
    }
}