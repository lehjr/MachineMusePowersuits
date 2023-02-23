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

package lehjr.numina.common.capabilities.player;

import lehjr.numina.common.math.MathUtils;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityPlayerKeyStates implements ICapabilitySerializable<ByteNBT> {
    @CapabilityInject(IPlayerKeyStates.class)
    public static Capability<IPlayerKeyStates> PLAYER_KEYSTATES = null;
    private IPlayerKeyStates instance = PLAYER_KEYSTATES.getDefaultInstance();

    public static void register() {
        CapabilityManager.INSTANCE.register(IPlayerKeyStates.class, new Capability.IStorage<IPlayerKeyStates>() {
                    @Override
                    public INBT writeNBT(Capability<IPlayerKeyStates> capability, IPlayerKeyStates instance, Direction side) {
                        boolean[] boolArray = new boolean[]{
                                instance.getForwardKeyState(),
                                instance.getReverseKeyState(),
                                instance.getLeftStrafeKeyState(),
                                instance.getRightStrafeKeyState(),
                                instance.getDownKeyState(),
                                instance.getJumpKeyState(),
                                false,
                                false
                        };

                        byte byteOut = MathUtils.boolArrayToByte(boolArray);
                        return ByteNBT.valueOf(byteOut);
                    }

                    @Override
                    public void readNBT(Capability<IPlayerKeyStates> capability, IPlayerKeyStates instance, Direction side, INBT nbt) {
                        if (nbt instanceof ByteNBT) {
                            byte byteOut = ((ByteNBT) nbt).getAsByte();
                            boolean[] boolArray = MathUtils.byteToBooleanArray(byteOut);

                            instance.setForwardKeyState(boolArray[0]);
                            instance.setReverseKeyState(boolArray[1]);
                            instance.setLeftStrafeKeyState(boolArray[2]);
                            instance.setRightStrafeKeyState(boolArray[3]);
                            instance.setDownKeyState(boolArray[4]);
                            instance.setJumpKeyState(boolArray[5]);
                        }
                    }
                },
                () -> new PlayerKeyStateStorage());
    }

    @Override
    public ByteNBT serializeNBT() {
        return (ByteNBT) PLAYER_KEYSTATES.getStorage().writeNBT(PLAYER_KEYSTATES, this.instance, null);
    }

    @Override
    public void deserializeNBT(ByteNBT nbt) {
        PLAYER_KEYSTATES.getStorage().readNBT(PLAYER_KEYSTATES, this.instance, null, nbt);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return PLAYER_KEYSTATES.orEmpty(cap, LazyOptional.of(()-> instance));
    }
}