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

package com.github.lehjr.numina.util.capabilities.module.toggleable;

import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModule;
import com.github.lehjr.numina.util.nbt.MuseNBTUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import java.util.concurrent.Callable;

public class ToggleableModule extends PowerModule implements IToggleableModule, INBTSerializable<ByteNBT> {
    Boolean online;
    static final String TAG_ONLINE = "Active";
    static boolean defBool;
    public ToggleableModule(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> moduleConfigGetterIn, boolean defToggleVal) {
        super(module, category, target, moduleConfigGetterIn);
        defBool = defToggleVal;
    }

    @Override
    public void updateFromNBT() {
        final CompoundNBT nbt = MuseNBTUtils.getModuleTag(module);
        if (nbt != null && nbt.contains(TAG_ONLINE, Constants.NBT.TAG_BYTE)) {
            deserializeNBT((ByteNBT) nbt.get(TAG_ONLINE));
        } else {
            nbt.putBoolean(TAG_ONLINE, defBool);
            deserializeNBT(ByteNBT.valueOf((byte) (defBool ? 1 : 0)));
        }
    }

    @Override
    public ByteNBT serializeNBT() {
        return ByteNBT.valueOf((byte) (online ? 1 : 0));
    }

    @Override
    public void deserializeNBT(ByteNBT nbt) {
        online = nbt.getByte() == 1;
    }

    @Override
    public void toggleModule(boolean online) {
        this.online = online;
        MuseNBTUtils.setModuleBoolean(module, TAG_ONLINE, online);
    }

    @Override
    public boolean isModuleOnline() {
        if (online == null) {
            updateFromNBT();
        }
        return online;
    }
}