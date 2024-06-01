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

package lehjr.numina.common.capabilities.inventory.modechanging;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.inventory.modularitem.ModularItem;
import lehjr.numina.common.capabilities.module.externalitems.IOtherModItemsAsModules;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.serverbound.ModeChangeRequestPacketServerBound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


/**
 * Note that loops starting with 1 instead of 0 are intentional to skip power storage module in the first slot
 */
public class ModeChangingModularItem extends ModularItem implements IModeChangingItem {

//    public static String uniqueID = "";

    protected static int activeMode = -1;

    public ModeChangingModularItem(@Nonnull ItemStack modularItem, int size) {
        this(modularItem, NonNullList.withSize(size, ItemStack.EMPTY));
    }

    public ModeChangingModularItem(@Nonnull ItemStack modularItem, NonNullList<ItemStack> stacks) {
        super(modularItem, stacks, true);
    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    @Override
    public BakedModel getInventoryModel() {
        return Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(getActiveModule());
    }

    @Override
    public List<Integer> getValidModes() {
        this.loadCapValues();
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
        if (module.isEmpty()) {
            return false;
        }

        return NuminaCapabilities.getCapability(module, NuminaCapabilities.POWER_MODULE)
                .map( m-> m.isAllowed() && (m instanceof IRightClickModule|| m instanceof IOtherModItemsAsModules)).orElse(false);
    }

    @Override
    public boolean isValidMode(ResourceLocation mode) {
        for(int i=1; i < getSlots();  i++) {
            ItemStack module = getStackInSlot(i);
            if (!module.isEmpty() && ItemUtils.getRegistryName(module).equals(mode) && isValidMode(module))
                return true;
        }
        return false;
    }

    @Override
    public ItemStack getActiveModule() {
        int activeModeIndex = getActiveMode();
        ItemStack module = activeModeIndex != -1 ? getStackInSlot(activeModeIndex) : ItemStack.EMPTY;
        return NuminaCapabilities.getCapability(module, NuminaCapabilities.POWER_MODULE).map(m->m.isAllowed() && (m instanceof IRightClickModule || m instanceof IOtherModItemsAsModules)).orElse(false)
                ? module : ItemStack.EMPTY;
    }

    @Override
    public boolean hasActiveModule(ResourceLocation regName) {
        final int activeModeIndex = getActiveMode();

        ItemStack module = activeModeIndex != -1 ? getStackInSlot(activeModeIndex) : ItemStack.EMPTY;

        if (regName == ItemUtils.getRegistryName(module)) {
            return isModuleOnline(module);
        }
        for (int i = 0; i < getSlots(); i++) {
            if (i != activeModeIndex) {
                module = getStackInSlot(i);
                if (!module.isEmpty() && ItemUtils.getRegistryName(module).equals(regName)) {
                    return isModuleOnline(module);
                }
            }
        }
        return false;
    }

    @Override
    public boolean isModuleOnline(ItemStack module) {
        return NuminaCapabilities.getCapability(module, NuminaCapabilities.POWER_MODULE).map(m-> {
            if(m.isAllowed() && m.isModuleOnline()) {
                if (m instanceof IRightClickModule) {
                    return ItemStack.isSameItem(m.getModule(), getActiveModule());
                }
                return true;
            }
            return false;
        }).orElse(false);
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

//    @Override
//    public void setActiveMode(ResourceLocation moduleName) {
//        for(int i=1; i < getSlots();  i++) {
//            ItemStack module = getStackInSlot(i);
//            if (!module.isEmpty() && ItemUtils.getRegistryName(module).equals(moduleName)
//                    && module.getCapability(NuminaCapabilities.POWER_MODULE).map(m-> m instanceof IRightClickModule).orElse(false)) {
//                setActiveMode(i, );
//                return;
//            }
//        }
//    }

   @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        ItemStack retStack = super.insertItem(slot, stack, simulate);
        if (retStack.isEmpty() && !simulate) {
            ItemStack stackInSlot = getStackInSlot(slot);
            CompoundTag tag = stackInSlot.getOrCreateTag();
//            tag.putString(UNIQUE_ID, getUniqueID());
            stackInSlot.setTag(tag);
            super.setStackInSlot(slot, stackInSlot);
        }
        return retStack;
    }

    @Override
    public void setActiveMode(int newMode) {
        activeMode = newMode;
        onContentsChanged(newMode);
    }

    @Override
    public void cycleMode(Player player, int dMode) {
        List<Integer> modes = this.getValidModes();
        if (!modes.isEmpty()) {
            int newindex = clampMode(modes.indexOf(this.getActiveMode()) + dMode, modes.size());
            int newmode = modes.get(newindex);
            NuminaPackets.sendToServer(new ModeChangeRequestPacketServerBound(newmode));
//            this.setActiveMode(newmode, player.getInventory());
        }
    }

    @Override
    public int nextMode() {
        List<Integer> modes = this.getValidModes();
        if (!modes.isEmpty()) {
            int newindex = clampMode(modes.indexOf(getActiveMode()) + 1, modes.size());
            return modes.get(newindex);
        }
        else return -1;
    }

    @Override
    public int prevMode() {
        List<Integer> modes = this.getValidModes();
        if (!modes.isEmpty()) {
            int newindex = clampMode(modes.indexOf(getActiveMode()) - 1, modes.size());
            return modes.get(newindex);
        }
        else return -1;
    }

    private static int clampMode(int selection, int modesSize) {
        return (selection > 0) ? (selection % modesSize) : ((selection + modesSize * -selection) % modesSize);
    }

    @Deprecated
    @Override
    public boolean isModuleActiveAndOnline(ResourceLocation moduleName) {
        if (hasActiveModule(moduleName)) {
            return NuminaCapabilities.getCapability(getActiveModule(), NuminaCapabilities.POWER_MODULE).map(IPowerModule::isModuleOnline).orElse(false);
        }
        return false;
   }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag =  super.serializeNBT();
        int mode = getActiveMode();
        if (mode != -1) {
            tag.putInt(NuminaConstants.MODE, mode);
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains(NuminaConstants.MODE, Tag.TAG_INT)) {
            activeMode = nbt.getInt(NuminaConstants.MODE);
        }
        super.deserializeNBT(nbt);
    }
}