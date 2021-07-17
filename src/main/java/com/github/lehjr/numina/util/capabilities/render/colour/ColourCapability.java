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

package com.github.lehjr.numina.util.capabilities.render.colour;

import com.github.lehjr.numina.util.math.Colour;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ColourCapability {
        @CapabilityInject(IColourNBT.class)
        public static Capability<IColourNBT> COLOUR = null;

        public static void register() {
            CapabilityManager.INSTANCE.register(IColourNBT.class, new Capability.IStorage<IColourNBT>() {
                        @Override
                        public INBT writeNBT(Capability<IColourNBT> capability, IColourNBT instance, Direction side) {
                            return instance instanceof ColourNBT ? ((ColourNBT) instance).serializeNBT() : null;
                        }

                        @Override
                        public void readNBT(Capability<IColourNBT> capability, IColourNBT instance, Direction side, INBT nbt) {
                            if (!(instance instanceof ColourNBT)) {
                                throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
                            }
                            ((ColourNBT) instance).deserializeNBT((IntNBT) nbt);
                        }
                    },
                    () -> new ColourNBT(Colour.WHITE));
        }
}