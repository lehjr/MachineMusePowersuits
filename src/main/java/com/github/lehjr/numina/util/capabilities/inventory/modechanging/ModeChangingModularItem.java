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

package com.github.lehjr.numina.util.capabilities.inventory.modechanging;

import com.github.lehjr.numina.network.NuminaPackets;
import com.github.lehjr.numina.network.packets.ModeChangeRequestPacket;
import com.github.lehjr.numina.util.capabilities.inventory.modularitem.ModularItem;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.rightclick.IRightClickModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


/**
 * Note that loops starting with 1 instead of 0 are intentional to skip power storage module in the first slot
 */
public class ModeChangingModularItem extends ModularItem implements IModeChangingItem {
    public static final String TAG_MODE = "mode";
    protected static int activeMode;

    public ModeChangingModularItem(@Nonnull ItemStack modularItem, int size) {
        this(modularItem, NonNullList.withSize(size, ItemStack.EMPTY));
    }

    public ModeChangingModularItem(@Nonnull ItemStack modularItem, NonNullList<ItemStack> stacks) {
        super(modularItem, stacks);
    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    @Override
    public IBakedModel getInventoryModel() {
        return Minecraft.getInstance().getItemRenderer().getItemModelMesher().getItemModel(getActiveModule());
    }

    @Override
    public List<Integer> getValidModes() {
        this.updateFromNBT();
        List<Integer>moduleIndexes = new ArrayList<>();

        // note: starting at 1 skips the power storage there
        for(int i=1; i < getSlots();  i++) {
            ItemStack module = getStackInSlot(i);
            if (isValidMode(module)) {
                moduleIndexes.add(i);
            }
        }
        return moduleIndexes;
    }

    boolean isValidMode(@Nonnull ItemStack module) {
        return module.getCapability(PowerModuleCapability.POWER_MODULE)
                .map( m-> m.isAllowed() && m instanceof IRightClickModule).orElse(false);
    }

    @Override
    public boolean isValidMode(ResourceLocation mode) {
        for(int i=1; i < getSlots();  i++) {
            ItemStack module = getStackInSlot(i);
            if (!module.isEmpty() && module.getItem().getRegistryName().equals(mode) && isValidMode(module))
                return true;
        }
        return false;
    }

    @Override
    public ItemStack getActiveModule() {
        int activeModeIndex = getActiveMode();
        ItemStack module = activeModeIndex != -1 ? getStackInSlot(activeModeIndex) : ItemStack.EMPTY;
        return module.getCapability(PowerModuleCapability.POWER_MODULE).map(m->m.isAllowed() && m instanceof IRightClickModule).orElse(false)
                ? module : ItemStack.EMPTY;
    }

    @Override
    public boolean hasActiveModule(ResourceLocation regName) {
        ItemStack module = getActiveModule();
        if (!module.isEmpty()) {
            return module.getItem().getRegistryName().equals(regName);
        }
        return false;
    }

    @Override
    public int getActiveMode() {
        if (activeMode == -1) {
            List<Integer> validModes = getValidModes();
            if (!validModes.isEmpty())
                activeMode = validModes.get(0);
        }
        return activeMode;
    }

    @Override
    public void setActiveMode(ResourceLocation moduleName) {
        for(int i=1; i < getSlots();  i++) {
            ItemStack module = getStackInSlot(i);
            if (!module.isEmpty() && module.getItem().getRegistryName().equals(moduleName)
                    && module.getCapability(PowerModuleCapability.POWER_MODULE).map(m-> m instanceof IRightClickModule).orElse(false)) {
                setActiveMode(i);
                return;
            }
        }
    }

    @Override
    public void setActiveMode(int newMode) {
        activeMode = newMode;
        onContentsChanged(newMode);
    }

    @Override
    public void cycleMode(PlayerEntity player, int dMode) {
        List<Integer> modes = this.getValidModes();
        if (modes.size() > 0) {
            int newindex = clampMode(modes.indexOf(this.getActiveMode()) + dMode, modes.size());
            int newmode = modes.get(newindex);
            this.setActiveMode(newmode);
            NuminaPackets.CHANNEL_INSTANCE.sendToServer(new ModeChangeRequestPacket(newmode, player.inventory.currentItem));
        }
    }

    @Override
    public int nextMode() {
        List<Integer> modes = this.getValidModes();
        if (modes.size() > 0) {
            int newindex = clampMode(modes.indexOf(getActiveMode()) + 1, modes.size());
            return modes.get(newindex);
        }
        else return -1;
    }

    @Override
    public int prevMode() {
        List<Integer> modes = this.getValidModes();
        if (modes.size() > 0) {
            int newindex = clampMode(modes.indexOf(getActiveMode()) - 1, modes.size());
            return modes.get(newindex);
        }
        else return -1;
    }

    private static int clampMode(int selection, int modesSize) {
        return (selection > 0) ? (selection % modesSize) : ((selection + modesSize * -selection) % modesSize);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = super.serializeNBT();
        nbt.putInt(TAG_MODE, this.activeMode);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (nbt.contains(TAG_MODE))
            this.activeMode = nbt.getInt(TAG_MODE);
        else
            this.activeMode = -1;
        super.deserializeNBT(nbt);
    }
}