package lehjr.numina.common.capability.inventory.modularitem;

import com.mojang.datafixers.util.Pair;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.module.powermodule.IPowerModule;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.toggleable.IToggleableModule;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.utils.StringUtils;
import lehjr.numina.common.utils.TagUtils;
import lehjr.numina.imixin.common.item.IMixinRangedWrapper;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IModularItem extends IItemHandlerModifiable, IItemHandler {
    /**
     * This is the "Modular Item" (weapon, armor, shield?) that has the inventory for modules.
     * @return
     */
    @Nonnull
    ItemStack getModularItemStack();

    /**
     * Any handheld item, tool, shield, crayon, whatever
     * @return
     */
    default boolean isHandHeld() {
        if(isTool()) {
            return true;
        }
        return false;
    }

    /**
     * A handheld item that is specifically a tool or weapon
     * @return
     */
    default boolean isTool() {
        return false;
    }

    /**
     * Tiers help improve a sense of progression.
     * @return actual tier
     */
    int getTier();

    /**
     * Ranged wrappers are used as a way of partitioning the storage so that some special purpose modules have a specific slot.
     * This is done as a way of mitigating performance issues of parsing the entire storage while having to perform related calculations
     * based on the presence of certain modules.
     * @return
     */
    Map<ModuleCategory, RangedWrapper> getRangedWrappers();

    void setRangedWrapperMap(Map<ModuleCategory, RangedWrapper> rangedWrappers);

    boolean isModuleValidForPlacement(int slot, @Nonnull ItemStack module);


    default boolean isModuleValid (@Nonnull IPowerModule pm) {
        if(pm.isAllowed() && pm.getTier() <= getTier()) {
            // check module target against item stack type
            switch (pm.getTarget()) {
                case ALLITEMS-> {
                    return true;
                }
                case HANDHELD -> {
                    return isHandHeld();
                }
                case TOOLONLY-> {
                    return isTool();
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
    }

    default boolean isModuleValid(@Nonnull ItemStack module) {
        // empty item is valid
        if (module.isEmpty()) {
            return false;
        }

        IPowerModule cap = getModuleCapability(module);
        if (cap == null) {
            return false;
        }

        return isModuleValid(cap);
    }

    void updateModuleInSlot(int slot, @Nonnull ItemStack module);

    /**
     * Returns a range of slots (partition) for a given module category, or null if there is none
     * @param category
     * @return
     */
    @Nullable
    default Pair<Integer, Integer> getRangeForCategory(ModuleCategory category) {
        RangedWrapper rangedWrapper = getRangedWrappers().get(category);
        if (rangedWrapper != null){
            return ((IMixinRangedWrapper)rangedWrapper).numina$getRange();
        }
        return null;
    }

    default int getStackLimit(final int slot, @Nonnull final ItemStack module) {
        // allow empty slots, else empty slots will crash on opening container
        if (module.isEmpty()) {
            return 1;
        }

        if (isModuleValid(module)) {
            NuminaLogger.logDebug("module here: " + module);

            //  Check if the module is already in this slot instead of just blindly
            // checking if module is already installed. This is because this method will be called
            // on loading to check if the module is valid for the given slot.
            int isInstalled = findInstalledModule(module);
            if (isInstalled != -1 && isInstalled != slot) {
                return 0;
            }

            // get the module category... CATEGORY_NONE) is actually just a fallback
            ModuleCategory category = NuminaCapabilities.getCapability(module, NuminaCapabilities.Module.POWER_MODULE).map(IPowerModule::getCategory)
                    .orElse(ModuleCategory.NONE);

            // Specfic module type for limited modules
            RangedWrapper wrapper = getRangedWrappers().get(category);

            // fallback on generic type if null
            if (wrapper == null) {
                wrapper = getRangedWrappers().get(ModuleCategory.NONE);
            }

            if (wrapper != null && ((IMixinRangedWrapper)wrapper).numina$contains(slot)) {
                if (wrapper.getStackInSlot(slot).is(module.getItem())) {
                    return 1;
                }
                return wrapper.getStackInSlot(slot).isEmpty() ? 1: 0;
            }
        }
        NuminaLogger.logDebug("module here 9: " + module);
        return 0;
    }

    @Override
    default int getSlotLimit(int slot) {
        return 1;
    }

    default int findInstalledModule(@Nonnull ItemStack module) {
        ResourceLocation registryName = ItemUtils.getRegistryName(module);
        return findInstalledModule(registryName);
    }

    default int findInstalledModule(ResourceLocation registryName) {
        for (int i = 0; i < getSlots(); i++) {
            ItemStack testStack = getStackInSlot(i);
            if (!testStack.isEmpty()) {
                if (ItemUtils.getRegistryName(testStack).equals(registryName)) {
                    return i;
                }
            }
        }
        return -1;
    }

    default boolean isModuleInstalled(ResourceLocation regName) {
        return findInstalledModule(regName) > -1;
    }

    default boolean isModuleInstalled(@Nonnull ItemStack module) {
        return NuminaCapabilities.getCapability(module, NuminaCapabilities.Module.POWER_MODULE).isPresent() && isModuleInstalled(ItemUtils.getRegistryName(module));
    }

    default boolean isModuleInstalled(Item item) {
        return isModuleInstalled(ItemUtils.getRegistryName(item));
    }

    default boolean isModuleOnline(ResourceLocation moduleName) {
        int slot = findInstalledModule(moduleName);
        if (slot > -1) {
            return isModuleOnline(getStackInSlot(slot));
        }
        return false;
    }

    /**
     * return true for a module is allowed and either can't be turned off or is on, otherwise return false
     */
    default boolean isModuleOnline(ItemStack module) {
        return NuminaCapabilities.getCapability(module, NuminaCapabilities.Module.POWER_MODULE)
                .map(m -> m.isAllowed() && m.isModuleOnline()).orElse(false);
    }

    default ItemStack getOnlineModuleOrEmpty(ResourceLocation moduleName) {
        int slot = findInstalledModule(moduleName);
        if (slot > -1) {
            ItemStack module = getStackInSlot(slot);
            if (NuminaCapabilities.getCapability(module, NuminaCapabilities.Module.POWER_MODULE).map(m -> m.isAllowed() && m.isModuleOnline()).orElse(false)) {
                return module;
            }
        }
        return ItemStack.EMPTY;
    }

    default void toggleModule(ResourceLocation moduleName, boolean online) {
        int slot = findInstalledModule(moduleName);
        if (slot > -1) {
            ItemStack module = getStackInSlot(slot);
            IPowerModule pm = getModuleCapability(module);
            if (pm instanceof IToggleableModule tm) {
                ItemStack newModule = tm.toggleModule(online);
                updateModuleInSlot(slot, newModule);
            }
        }
    }

    /**
     * TODO: WIP!! Still assessing what changes actually need to be made for things to work
     * @param moduleName
     * @param key
     * @param value
     * @return
     */
    default void setModuleDouble(ResourceLocation moduleName, String key, double value) {
        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            if (!module.isEmpty() && ItemUtils.getRegistryName(module).equals(moduleName)) {
                if (!module.isEmpty() && ItemUtils.getRegistryName(module).equals(moduleName)) {
                    IPowerModule pm = getModuleCapability(module);
                    if (pm != null) {
                        NuminaLogger.logDebug("Setting module tweak " + key + ": " + value);
                        NuminaLogger.logDebug("module value before: " + (TagUtils.getModuleDouble(module, key)));

                        ItemStack module1 = TagUtils.setModuleDouble(module, key, value);

                        NuminaLogger.logDebug("module value after: " + (TagUtils.getModuleDouble(module, key)));
                        NuminaLogger.logDebug("module1 value before " + (TagUtils.getModuleDouble(module1, key)));

                        updateModuleInSlot(i, module);
                        break;
                    }
                }
            }
        }
    }

    default void setModuleFloat(ResourceLocation moduleName, String key, float value) {
        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            if (!module.isEmpty() && ItemUtils.getRegistryName(module).equals(moduleName)) {
                IPowerModule pm = getModuleCapability(module);
                if (pm != null) {
                    NuminaLogger.logDebug("Setting module tweak " + key + ": " + value);
                    NuminaLogger.logDebug("module value before: " + (TagUtils.getModuleFloat(module, key)));

                    ItemStack module1 = TagUtils.setModuleFloat(module, key, value);

                    NuminaLogger.logDebug("module value after: " + (TagUtils.getModuleFloat(module, key)));
                    NuminaLogger.logDebug("module1 value before " + (TagUtils.getModuleFloat(module1, key)));
                    updateModuleInSlot(i, module);
                    break;
                }
            }
        }
    }

    default void setModuleString(ResourceLocation moduleName, String key, String value) {
        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            if (!module.isEmpty() && ItemUtils.getRegistryName(module).equals(moduleName)) {
                IPowerModule pm = getModuleCapability(module);
                if (pm != null) {
                    NuminaLogger.logDebug("Setting module tweak " + key + ": " + value);
                    NuminaLogger.logDebug("module value before: " + (TagUtils.getModuleString(module, key)));

                    ItemStack module1 = TagUtils.setModuleString(module, key, value);

                    NuminaLogger.logDebug("module value after: " + (TagUtils.getModuleString(module, key)));
                    NuminaLogger.logDebug("module1 value before " + (TagUtils.getModuleString(module1, key)));
                    updateModuleInSlot(i, module);
                    break;
                }
            }
        }
    }

    default void setModuleBlockState(ResourceLocation moduleName, BlockState state) {
        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            if (!module.isEmpty() && ItemUtils.getRegistryName(module).equals(moduleName)) {
                IPowerModule pm = getModuleCapability(module);
                if (pm != null) {
                    NuminaLogger.logDebug("Setting module state " + state.toString());
                    NuminaLogger.logDebug("module value before: " + (TagUtils.getModuleBlockState(module)));
                    NuminaLogger.logDebug("module tag before: " + TagUtils.getModuleTag(module));

                    ItemStack module1 = TagUtils.setModuleBlockState(module,state);

                    NuminaLogger.logDebug("module tag after: " + TagUtils.getModuleTag(module));
                    NuminaLogger.logDebug("module1 tag after: " + TagUtils.getModuleTag(module1));

                    NuminaLogger.logDebug("module value after: " + (TagUtils.getModuleBlockState(module)));
                    NuminaLogger.logDebug("module1 value before " + (TagUtils.getModuleBlockState(module1)));
                    updateModuleInSlot(i, module);
                    break;
                }
            }
        }
    }



    default List<ResourceLocation> getInstalledModuleNames() {
        List<ResourceLocation> locations = new ArrayList<>();

        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            if (!module.isEmpty()) {
                locations.add(ItemUtils.getRegistryName(module));
            }
        }
        return locations;
    }

    default NonNullList<ItemStack> getInstalledModules() {
        NonNullList<ItemStack> modules = NonNullList.create();

        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            if (!module.isEmpty()) {
                modules.add(module);
            }
        }
        return modules;
    }

    default NonNullList<ItemStack> getInstalledModulesOfType(Class<? extends IPowerModule> type) {
        NonNullList<ItemStack> modules = NonNullList.create();

        for (int i = 0; i < getSlots(); i++) {
            ItemStack module = getStackInSlot(i);
            NuminaCapabilities.getPowerModuleCapability(module).filter(type::isInstance).ifPresent(pm -> modules.add(module));
        }
        return modules;
    }

    void tick(Player player);

    @Nullable
    default IPowerModule getModuleCapability(@Nonnull ItemStack module) {
        return module.getCapability(NuminaCapabilities.Module.POWER_MODULE);
    }

    default String formatInfo(String string, double value) {
        return string + '\t' + StringUtils.formatNumberShort(value);
    }
}