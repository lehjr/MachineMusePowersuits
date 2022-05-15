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

package com.lehjr.numina.common.capabilities.player;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class PlayerKeyStateStorage implements IPlayerKeyStates, ICapabilitySerializable<CompoundTag> {
//    private final IPlayerKeyStates instance = CapabilityPlayerKeyStates.PLAYER_KEYSTATES.getDefaultInstance();

    private boolean forwardKeyState = false;
    private byte strafeKeyState = 0; // left=-1, none=0, right = 1
    private boolean downKeyState = false;
    private boolean jumpKeyState = false;
//    private final boolean sneakKeyState = false;

    @Override
    public void setForwardKeyState(boolean state) {
        this.forwardKeyState = state;
    }

    @Override
    public boolean getForwardKeyState() {
        return this.forwardKeyState;
    }

    @Override
    public void setStrafeKeyState(byte state) {
        this.strafeKeyState = state;
    }

    @Override
    public byte getStrafeKeyState() {
        return this.strafeKeyState;
    }

    @Override
    public void setDownKeyState(boolean state) {
        this.downKeyState = state;
    }

    @Override
    public boolean getDownKeyState() {
        return this.downKeyState;
    }

    @Override
    public void setJumpKeyState(boolean state) {
        this.jumpKeyState = state;
    }

    @Override
    public boolean getJumpKeyState() {
        return this.jumpKeyState;
    }


//
//
//    public static void register() {
//        CapabilityManager.INSTANCE.register(IPlayerKeyStates.class, new Capability.IStorage<IPlayerKeyStates>() {
//                    @Override
//                    public INBT writeNBT(Capability<IPlayerKeyStates> capability, IPlayerKeyStates instance, Direction side) {
//
//                    }
//
//                    @Override
//                    public void readNBT(Capability<IPlayerKeyStates> capability, IPlayerKeyStates instance, Direction side, INBT nbt) {
//
//                    }
//                },
//                () -> new PlayerKeyStateStorage());
//    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("forward", forwardKeyState);
        nbt.putByte("strafe", strafeKeyState);
        nbt.putBoolean("jumpKey", jumpKeyState);
        nbt.putBoolean("downKey", downKeyState);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt instanceof CompoundTag) {
            forwardKeyState = nbt.getBoolean("forward");
            strafeKeyState = nbt.getByte("strafe");
            jumpKeyState = nbt.getBoolean("jumpKey");
            downKeyState = nbt.getBoolean("downKey");
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return null;
    }

//    @Nonnull
//    @Override
//    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//        return PLAYER_KEYSTATES.orEmpty(cap, LazyOptional.of(()-> instance));
//    }
}