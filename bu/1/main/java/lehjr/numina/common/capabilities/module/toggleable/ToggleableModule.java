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

package lehjr.numina.common.capabilities.module.toggleable;

import lehjr.numina.common.capabilities.module.powermodule.IConfig;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.powermodule.PowerModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.tags.TagUtils;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import java.util.concurrent.Callable;

public class ToggleableModule extends PowerModule implements IToggleableModule, INBTSerializable<ByteTag> {
    Boolean online;
    static boolean defBool;
    public ToggleableModule(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> moduleConfigGetterIn, boolean defToggleVal) {
        super(module, category, target, moduleConfigGetterIn);
        defBool = defToggleVal;
    }

    @Override
    public void loadCapValues() {
        final CompoundTag tag = TagUtils.getModuleTag(module);
        if (tag.contains(NuminaConstants.TAG_ONLINE, Tag.TAG_BYTE)) {
            deserializeNBT((ByteTag) tag.get(NuminaConstants.TAG_ONLINE));
        } else {
            tag.putBoolean(NuminaConstants.TAG_ONLINE, defBool);
            deserializeNBT(ByteTag.valueOf((byte) (defBool ? 1 : 0)));
        }
    }

    @Override
    public ByteTag serializeNBT() {
        return ByteTag.valueOf((byte) (online ? 1 : 0));
    }

    @Override
    public void deserializeNBT(ByteTag nbt) {
        online = nbt.getAsByte() == 1;
    }

    @Override
    public void toggleModule(boolean online) {
        this.online = online;
        onValueChanged();
    }

    @Override
    public void onValueChanged() {
        TagUtils.setModuleBoolean(module, NuminaConstants.TAG_ONLINE, online);
    }

    @Override
    public boolean isModuleOnline() {
        if (online == null) {
            loadCapValues();
        }
        return online !=null ? online : defBool;
    }
}