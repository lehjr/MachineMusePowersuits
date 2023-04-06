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

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.render.NuminaRenderer;
import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.inventory.modularitem.ModularItem;
import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.item.ItemUtils;
import lehjr.numina.common.math.Color;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.ModeChangeRequestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
        super(modularItem, stacks, true);
    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    @Override
    public BakedModel getInventoryModel() {
        return Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(getActiveModule());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void drawModeChangeIcon(Player player, int hotbarIndex, Minecraft mc) {
        ItemStack module = getActiveModule();
        if (!module.isEmpty()) {
            Window screen = mc.getWindow();
            double currX;
            double currY;
            int sw = screen.getGuiScaledWidth();
            int baroffset = 22;
            if (!player.getAbilities().instabuild) {
                baroffset += 16;
                int totalArmorValue = player.getArmorValue();
                baroffset += 8 * (int) Math.ceil((double)totalArmorValue / 20); // 20 points per row @ 2 armor points per icon
            }
            baroffset = screen.getGuiScaledHeight() - baroffset;
            currX = sw / 2.0 - 89.0 + 20.0 * hotbarIndex;
            currY = baroffset - 18;
            Color.WHITE.setShaderColor();
            if (module.getCapability(NuminaCapabilities.POWER_MODULE).map(pm-> pm.isModuleOnline()).orElse(false)) {
                mc.getItemRenderer().renderGuiItem(module.getCapability(NuminaCapabilities.CHAMELEON).map(iChameleon -> iChameleon.getStackToRender()).orElse(module), (int)currX, (int)currY);
            } else {
                NuminaRenderer.drawModuleAt(new PoseStack(), currX, currY, module.getCapability(NuminaCapabilities.CHAMELEON).map(iChameleon -> iChameleon.getStackToRender()).orElse(module), false);
            }
        }
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
        return module.getCapability(NuminaCapabilities.POWER_MODULE)
                .map( m-> m.isAllowed() && m instanceof IRightClickModule).orElse(false);
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
        return module.getCapability(NuminaCapabilities.POWER_MODULE).map(m->m.isAllowed() && m instanceof IRightClickModule).orElse(false)
                ? module : ItemStack.EMPTY;
    }

    @Override
    public boolean hasActiveModule(ResourceLocation regName) {
        ItemStack module = getActiveModule();
        if (!module.isEmpty()) {
            return ItemUtils.getRegistryName(module).equals(regName);
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
            if (!module.isEmpty() && ItemUtils.getRegistryName(module).equals(moduleName)
                    && module.getCapability(NuminaCapabilities.POWER_MODULE).map(m-> m instanceof IRightClickModule).orElse(false)) {
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
    public void cycleMode(Player player, int dMode) {
        List<Integer> modes = this.getValidModes();
        if (modes.size() > 0) {
            int newindex = clampMode(modes.indexOf(this.getActiveMode()) + dMode, modes.size());
            int newmode = modes.get(newindex);
            this.setActiveMode(newmode);
            NuminaPackets.CHANNEL_INSTANCE.sendToServer(new ModeChangeRequestPacket(newmode, player.getInventory().selected));
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
    public boolean isModuleActiveAndOnline(ResourceLocation moduleName) {
        if (hasActiveModule(moduleName)) {
            return getActiveModule().getCapability(NuminaCapabilities.POWER_MODULE).map(pm-> pm.isModuleOnline()).orElse(false);
        }
        return false;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = super.serializeNBT();
        nbt.putInt(TAG_MODE, activeMode);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains(TAG_MODE))
            activeMode = nbt.getInt(TAG_MODE);
        else
            activeMode = -1;
        super.deserializeNBT(nbt);
    }
}