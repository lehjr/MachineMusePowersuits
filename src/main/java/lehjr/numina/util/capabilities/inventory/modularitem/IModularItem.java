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

package lehjr.numina.util.capabilities.inventory.modularitem;

import lehjr.numina.util.capabilities.IItemStackUpdate;
import lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import lehjr.numina.util.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.numina.util.string.MuseStringUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 *
 *
 */
public interface IModularItem extends IItemHandler, IItemHandlerModifiable, IItemStackUpdate, INBTSerializable<CompoundNBT> {
    void setRangedWrapperMap(Map<EnumModuleCategory, NuminaRangedWrapper> rangedWrappers);

    boolean isModuleValid(@Nonnull ItemStack module);

    boolean isModuleInstalled(ResourceLocation regName);

    default boolean isModuleInstalled(@Nonnull ItemStack module) {
        return module.getCapability(PowerModuleCapability.POWER_MODULE).isPresent() && isModuleInstalled(module.getItem().getRegistryName());
    }

    boolean isModuleOnline(ResourceLocation moduleName);

    void toggleModule(ResourceLocation moduleName, boolean online);

    @Nullable
    Pair<Integer, Integer> getRangeForCategory(EnumModuleCategory category);

    List<ResourceLocation> getInstalledModuleNames();

    NonNullList<ItemStack> getInstalledModules();

    NonNullList<ItemStack> getInstalledModulesOfType(Class<? extends IPowerModule> type);

    @Nonnull
    ItemStack getOnlineModuleOrEmpty(ResourceLocation regName);

    @Nonnull
    ItemStack getModularItemStack();

    void tick(PlayerEntity player);

    default String formatInfo(String string, double value) {
        return string + '\t' + MuseStringUtils.formatNumberShort(value);
    }

    boolean setModuleTweakDouble(ResourceLocation moduleName, String key, double value);
}