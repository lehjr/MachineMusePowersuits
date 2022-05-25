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

package com.lehjr.numina.common.capabilities.heat;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.constants.TagConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class HeatItemWrapper extends HeatStorage {
    ItemStack stack;

//    @Deprecated
//    public HeatItemWrapper(@Nonnull ItemStack stack, int baseMax, LazyOptional<IPowerModule> moduleCap) {
//        super(baseMax + moduleCap.map(cap->cap.applyPropertyModifiers(TagConstants.MAXIMUM_HEAT)).orElse(0D));
//        this.stack = stack;
//    }
//    public HeatItemWrapper(@Nonnull ItemStack stack, double baseMax, LazyOptional<IPowerModule> moduleCap) {
//        super(baseMax + moduleCap.map(cap->cap.applyPropertyModifiers(TagConstants.MAXIMUM_HEAT)).orElse(0D));
//        NuminaLogger.logDebug("stack here in HeatItemWrapper: " + stack);
//
//        this.stack = stack;
//    }

    public HeatItemWrapper(@Nonnull ItemStack stackIn, double capacity, double maxReceive, double maxExtract) {
        super(capacity, maxReceive, maxExtract, 0);
        this.stack = stackIn;
    }

    public HeatItemWrapper(@Nonnull ItemStack stackIn, double capacity) {
        this(stackIn, capacity, capacity, capacity);
    }

    public HeatItemWrapper(@Nonnull ItemStack stack, double capacity, double maxTransfer) {
        this(stack, capacity, maxTransfer, maxTransfer);
    }



    @Override
    public void loadCapValues() {
        final CompoundTag tag = this.stack.getOrCreateTag();
        if (tag != null && tag.contains(TagConstants.HEAT, Tag.TAG_DOUBLE)) {
            deserializeNBT((DoubleTag) tag.get(TagConstants.HEAT));
        }
    }

    @Override
    public void onValueChanged() {
        this.stack.addTagElement(TagConstants.HEAT, serializeNBT());
    }
}