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

package lehjr.numina.common.capabilities.inventory.modularitem;

import com.google.common.util.concurrent.AtomicDouble;
import lehjr.numina.common.capabilities.CapabilityUpdate;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.tickable.IPlayerTickModule;
import lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.item.ItemUtils;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.tags.TagUtils;
import lehjr.numina.imixin.common.item.IMixinRangedWrapper;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import com.mojang.datafixers.util.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class ModularItem extends ItemStackHandler implements IModularItem, CapabilityUpdate {
    final boolean isTool;
    ItemStack modularItem;
    final int tier;
    Map<ModuleCategory, RangedWrapper> rangedWrappers;
    ModuleTarget target;

    public ModularItem(@Nonnull ItemStack modularItem, int tier, int size) {
        this(modularItem, tier, size, false);
    }

    public ModularItem(@Nonnull ItemStack modularItem, int tier, int size, boolean isTool) {
        this(modularItem, tier, NonNullList.withSize(size, ItemStack.EMPTY), isTool);
    }

    public ModularItem(@Nonnull ItemStack modularItem, int tier, NonNullList<ItemStack> stacks) {
        super(stacks);
        this.modularItem = modularItem;
        this.rangedWrappers = new HashMap<>();
        this.isTool = false;
        this.tier = tier;
    }

    public ModularItem(@Nonnull ItemStack modularItem, int tier, NonNullList<ItemStack> stacks, boolean isTool) {
        super(stacks);
        this.modularItem = modularItem;
        this.rangedWrappers = new HashMap<>();
        this.isTool = isTool;
        this.tier = tier;
    }

    @Override
    public ItemStack getModularItemStack() {
        return modularItem;
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public Map<ModuleCategory, RangedWrapper> getRangedWrappers() {
        return rangedWrappers;
    }

    @Override
    public void setRangedWrapperMap(Map<ModuleCategory, RangedWrapper> rangedWrappers) {
        this.rangedWrappers = rangedWrappers;
    }

    @Override
    public boolean isModuleValid (LazyOptional<IPowerModule> cap) {
        // follow tier requirements
        return cap.map(pm->{
            if(pm.isAllowed() && pm.getTier() <= getTier()) {
                // check module target against item stack type
                switch (pm.getTarget()) {
                    case ALLITEMS-> {
                        return true;
                    }
                    case TOOLONLY-> {
                        return isTool();
                    }
                    // this was meant for a shield but never quite got there
                    case HANDHELD -> {
                        return isHandHeld();
                    }
                    case ARMORONLY-> {
                        return getModularItemStack().getItem() instanceof ArmorItem;
                    }
                    case HEADONLY-> {
                        return getModularItemStack().getItem() instanceof ArmorItem
                                && Mob.getEquipmentSlotForItem(getModularItemStack()) == EquipmentSlot.HEAD;
                    }
                    case TORSOONLY-> {
                        return getModularItemStack().getItem() instanceof ArmorItem
                                && Mob.getEquipmentSlotForItem(getModularItemStack()) == EquipmentSlot.CHEST;
                    }
                    case LEGSONLY-> {
                        return getModularItemStack().getItem() instanceof ArmorItem
                                && Mob.getEquipmentSlotForItem(getModularItemStack()) == EquipmentSlot.LEGS;
                    }
                    case FEETONLY-> {
                        return getModularItemStack().getItem() instanceof ArmorItem
                                && Mob.getEquipmentSlotForItem(getModularItemStack()) == EquipmentSlot.FEET;
                    }
                    default-> {
                        return false;
                    }
                }
            }
            return false;
        }).orElse(false);


    }

    @Override
    public boolean isModuleValid(ItemStack module) {
        // filter out empty or damaged items
        if (module.isEmpty() || module.isDamaged()) {
            return false;
        }

        LazyOptional<IPowerModule> cap = getModuleCapability(module);

        if (!cap.isPresent()) {
            return false;
        }
        return isModuleValid(cap);
    }




    @Override
    @Nullable
    public Pair<Integer, Integer> getRangeForCategory(ModuleCategory category) {
        RangedWrapper rangedWrapper = getRangedWrappers().get(category);
        if (rangedWrapper != null){
            return ((IMixinRangedWrapper)rangedWrapper).numina$getRange();
        }
        return null;
    }



    /**
     * An alternative to isItemValid since the requirements are different WILL cause issues
     * @param slot
     * @param module
     * @return
     */
    @Override
    public boolean isModuleValidForPlacement(int slot, ItemStack module) {
        if (!isModuleValid(module)) {
            IPowerModule cap = getModuleCapability(module);
            if (cap == null) {
//                NuminaLogger.logDebug("no capability for module");
                return false;
            }
        }
//        NuminaLogger.logDebug("==================================================================");
//        NuminaLogger.logDebug("isValidForPlacement: <slot, module: <" + slot +", " +module +">"  );
//        NuminaLogger.logDebug("isSlotEmpty: " + getStackInSlot(slot).isEmpty());
//        NuminaLogger.logDebug("slotLimit: " + getStackLimit(slot, module));
//        NuminaLogger.logDebug("isItemValid: " + isItemValid(slot, module));
//        NuminaLogger.logDebug("------------------------------------------------------------------");
        return getStackInSlot(slot).isEmpty() && getStackLimit(slot, module) != 0 && isItemValid(slot, module);
    }

    @Override
    public boolean isModuleOnline(int slot) {
        if(isSlotValid(slot)) {
            return isModuleOnline(getStackInSlot(slot));
        }
        return false;
    }

    @Override
    public boolean isModuleOnline(ResourceLocation moduleName) {
        int slot = findInstalledModule(moduleName);
        if (isSlotValid(slot)) {
            return isModuleOnline(getStackInSlot(slot));
        }
        return false;
    }

    /**
     * return true for a module is allowed and either can't be turned off or is on, otherwise return false
     */
    public boolean isModuleOnline(ItemStack module) {
        return module.getCapability(NuminaCapabilities.POWER_MODULE)
                .map(m -> m.isAllowed() && m.isModuleOnline()).orElse(false);
    }

    @Override
    public ItemStack getOnlineModuleOrEmpty(ResourceLocation moduleName) {
        int i = findInstalledModule(moduleName);
        if (isSlotValid(i)) {
            ItemStack module = getStackInSlot(i);
            if(isModuleOnline(module)) {
                return module;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void toggleModule(ResourceLocation moduleName, boolean online) {
        int slot = findInstalledModule(moduleName);
        toggleModule(online, slot);
    }

    @Override
    public void toggleModule(boolean online, int slot) {
        if (isSlotValid(slot)) {
            ItemStack module = getStackInSlot(slot);
            module.getCapability(NuminaCapabilities.POWER_MODULE).ifPresent(m -> {
                if (m instanceof IToggleableModule) {
                    ((IToggleableModule) m).toggleModule(online);
                    // not enough to update the module, the tag for the item in the slot has to be updated too
                    onContentsChanged(slot);
                }
            });
        }
    }


    @Override
    public void setModuleBoolean(ResourceLocation moduleName, String key, boolean value) {
        int slot = findInstalledModule(moduleName);
        if (isSlotValid(slot)) {
            ItemStack module = getStackInSlot(slot);
            module.getCapability(NuminaCapabilities.POWER_MODULE).ifPresent(m -> {
                TagUtils.setModuleBoolean(module, key, value);
                // not enough to update the module, the tag for the item in the slot has to be updated too
                onContentsChanged(slot);
            });
        }
    }

    @Override
    public void setModuleDouble(int index, String key, double value) {
        if(isSlotValid(index)) {
            ItemStack module = getStackInSlot(index);
            module.getCapability(NuminaCapabilities.POWER_MODULE).ifPresent(m -> {
                TagUtils.setModuleDoubleOrRemove(module, key, value);
                // not enough to update the module, the tag for the item in the slot has to be updated too
                onContentsChanged(index);
            });
        }
    }

    @Override
    public void setModuleDouble(ResourceLocation moduleName, String key, double value) {
        setModuleDouble(findInstalledModule(moduleName), key, value);
    }

    @Override
    public double getModuleDouble(int index, String key) {
        ItemStack module = getStackInSlot(index);
        LazyOptional<IPowerModule> cap = getModuleCapability(module);
        if(cap.isPresent()) {
            return TagUtils.getModuleDouble(module, key);
        }
        return 0;
    }

    @Override
    public double getModuleDouble(ResourceLocation moduleName, String key) {
        return getModuleDouble(findInstalledModule(moduleName), key);
    }

    @Override
    public void setModuleFloat(ResourceLocation moduleName, String key, float value) {
        int index = findInstalledModule(moduleName);
        if(isSlotValid(index)) {
            ItemStack module = getStackInSlot(index);
            module.getCapability(NuminaCapabilities.POWER_MODULE).ifPresent(m -> {
                TagUtils.setModuleFloatOrRemove(module, key, value);
                // not enough to update the module, the tag for the item in the slot has to be updated too
                onContentsChanged(index);
            });
        }
    }

    @Override
    public void setModuleBlockState(ResourceLocation moduleName, BlockState state) {
        int index = findInstalledModule(moduleName);
        if(isSlotValid(index)) {
            ItemStack module = getStackInSlot(index);
            module.getCapability(NuminaCapabilities.POWER_MODULE).ifPresent(m -> {
                TagUtils.setModuleBlockState(module,state);
                // not enough to update the module, the tag for the item in the slot has to be updated too
                onContentsChanged(index);
            });
        }
    }

    @Override
    public void setModuleString(ResourceLocation moduleName, String key, String value) {
        int index = findInstalledModule(moduleName);
        if(isSlotValid(index)) {
            ItemStack module = getStackInSlot(index);
            module.getCapability(NuminaCapabilities.POWER_MODULE).ifPresent(m -> {
                TagUtils.setModuleString(module, key, value);
                // not enough to update the module, the tag for the item in the slot has to be updated too
                onContentsChanged(index);
            });
        }
    }

    @Override
    public boolean isModuleInstalled(ResourceLocation regName) {
        return findInstalledModule(regName) > -1;
    }

    @Override
    public boolean isModuleInstalled(ItemStack module) {
        return findInstalledModule(module.getItem()) > -1;
    }

    @Override
    public boolean isModuleInstalled(Item item) {
        return findInstalledModule(item) > -1;
    }

    @Override
    public int findInstalledModule(Item item) {
        if(item != Items.AIR) {
            for (int i = 0; i < getSlots(); i++) {
                if(getStackInSlot(i).is(item)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * returns the module capability only if the module is present and online
     * @param moduleName
     * @return
     */
    @Nullable
    @Override
    public Optional<IPowerModule> getModuleCapabilityOnlineOrNull(ResourceLocation moduleName) {
        return getModuleCapability(moduleName).filter(IPowerModule::isAllowed)
                .filter(IPowerModule::isModuleOnline);
    }





    @Override
    public NonNullList<ItemStack> getInstalledModules() {
        NonNullList<ItemStack> modules = NonNullList.create();

        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            if (!module.isEmpty()) {
                modules.add(module);
            }
        }
        return modules;
    }



    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack module) {
        return getStackLimit(slot, module) > 0;
    }





    @Nonnull
    @Override
    public ItemStack getOnlineModuleOrEmpty(ResourceLocation moduleName) {
        int slot = findInstalledModule(moduleName);
        if (slot > -1) {
            ItemStack module = getStackInSlot(slot);
            if (module.getCapability(NuminaCapabilities.POWER_MODULE).map(m -> m.isAllowed() && m.isModuleOnline()).orElse(false)) {
                return module;
            }
        }
        return ItemStack.EMPTY;
    }



    /**
     * returns the module capability if the module is present even if offline
     *
     * @param moduleName
     * @return
     */
    @Nullable
    @Override
    public LazyOptional<IPowerModule> getModuleCapability(ResourceLocation moduleName) {
        int slot = findInstalledModule(moduleName);
        if(isSlotValid(slot)) {
            return getModuleCapability(getStackInSlot(slot));
        }
        return LazyOptional.empty();
    }

    @Override
    public int findInstalledModule(ItemStack module) {
        return findInstalledModule(module.getItem());
    }

    @Override
    public int findInstalledModule(ResourceLocation registryName) {
        return findInstalledModule(BuiltInRegistries.ITEM.get(registryName));
    }

    @Override
    public void tick(Player player, Level level, ItemStack host) {
        for (int i = 0; i < getSlots(); i++) {
            final int index = i;

            getStackInSlot(i).getCapability(NuminaCapabilities.POWER_MODULE)
                    .filter(IPlayerTickModule.class::isInstance)
                    .map(IPlayerTickModule.class::cast)
                    .filter(IPlayerTickModule::isAllowed).ifPresent(m -> {
                        if (m.isModuleOnline()) {
                            if (m.onPlayerTickActive(player, level, this.getModularItemStack(), index)) {
                                onContentsChanged(index);
                            }
                        } else {
                            if(m instanceof IThruster thruster && !m.isModuleOnline()) {
                                thruster.stopSound(player, level);
                            }
                            if(m.onPlayerTickInactive(player, level, this.getModularItemStack(), index)) {
                                onContentsChanged(index);
                            }
                        }
                    });
        }
    }

    @Override
    public double getMovementResistance() {
        AtomicDouble movementResistance = new  AtomicDouble(0);
        for(int i = 0; i < getSlots(); i++) {
            getModuleCapability(getStackInSlot(i)).ifPresent(m -> {
                if(m.isAllowed() && m.isModuleOnline()) {
                    movementResistance.getAndAdd(m.applyPropertyModifiers(NuminaConstants.MOVEMENT_RESISTANCE));
                }
            });


        }
        return movementResistance.get();
    }

    @Override
    public boolean setModuleTweakDouble(ResourceLocation moduleName, String key, double value) {
        boolean handled = false;
        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            if (!module.isEmpty() && ItemUtils.getRegistryName(module).equals(moduleName)) {
                if (module.getCapability(NuminaCapabilities.POWER_MODULE).map(m -> {
                    TagUtils.setModuleDoubleOrRemove(module, key, value);
                    return true;
                }).orElse(false)) {
                    onContentsChanged(i);
                    handled = true;
                    break;
                }
            }
        }
        return handled;
    }

    /**
     * IItemHandler ------------------------------------------------------------------------------
     */
    @Override
    public int getStackLimit(final int slot, @Nonnull final ItemStack module) {
        if (isModuleValid(module)) {
            // is module already installed?
            if (isModuleInstalled(ForgeRegistries.ITEMS.getKey(module.getItem()))) { // fixme.. use item check instead? maybe?
                return 0;
            }

            // get the module category... CATEGORY_NONE) is actually just a fallback
            ModuleCategory category = module.getCapability(NuminaCapabilities.POWER_MODULE).map(m -> m.getCategory())
                    .orElse(ModuleCategory.NONE);

            // Specfic module type for limited modules
            RangedWrapper wrapper = rangedWrappers.get(category);

            // fallback on generic type if null
            if (wrapper == null) {
                wrapper = rangedWrappers.get(ModuleCategory.NONE);
            }

            // Note, not using the actual wrapper mechanics, just slot ranges
            if (wrapper != null && ((IMixinRangedWrapper)wrapper).numina$contains(slot)) {
                if (getStackInSlot(slot).is(module.getItem())) {
                    return 1;
                }
                return getStackInSlot(slot).isEmpty() ? 1: 0;
            }
        }
        return 0;
    }

    /**
     * ItemStackHandler --------------------------------------------------------------------------
     */
    @Override
    protected void onContentsChanged(final int slot) {
        super.onContentsChanged(slot);
//        modularItem.addTagElement(TagConstants.TAG_MODULE_SETTINGS, serializeNBT());
        modularItem.addTagElement(TagConstants.TAG_ITEM_PREFIX, serializeNBT());
    }

    @Override
    public void loadCapValues() {
        final CompoundTag nbt = getModularItemStack().getOrCreateTag();
        if (nbt.contains(TagConstants.TAG_MODULE_SETTINGS, Tag.TAG_COMPOUND)) {
            nbt.put(TagConstants.TAG_ITEM_PREFIX, Objects.requireNonNull(nbt.get(TagConstants.TAG_MODULE_SETTINGS)));
            nbt.remove(TagConstants.TAG_MODULE_SETTINGS);
        }
        if(nbt.contains(TagConstants.TAG_ITEM_PREFIX)) {
            deserializeNBT((CompoundTag) nbt.get(TagConstants.TAG_ITEM_PREFIX));
        }
    }

    @Override
    public void onValueChanged() {
    }
}