package lehjr.numina.common.capability.inventory.modularitem;

import lehjr.numina.common.capability.CapabilityUpdate;
import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.module.powermodule.IPowerModule;
import lehjr.numina.common.capability.module.powermodule.ModuleCategory;
import lehjr.numina.common.capability.module.powermodule.ModuleTarget;
import lehjr.numina.common.capability.module.tickable.IPlayerTickModule;
import lehjr.numina.common.capability.module.toggleable.IToggleableModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.tags.TagUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class ModularItem extends ItemStackHandler implements IModularItem, CapabilityUpdate {
    final boolean isTool;
    ItemStack modularItem;
    Map<ModuleCategory, NuminaRangedWrapper> rangedWrappers;
    ModuleTarget target;

    public ModularItem(@Nonnull ItemStack modularItem, int size) {
        this(modularItem, size, false);
    }

    public ModularItem(@Nonnull ItemStack modularItem, int size, boolean isTool) {
        this(modularItem, NonNullList.withSize(size, ItemStack.EMPTY), isTool);
    }

    public ModularItem(@Nonnull ItemStack modularItem, NonNullList<ItemStack> stacks) {
        super(stacks);
        this.modularItem = modularItem;
        this.rangedWrappers = new HashMap<>();
        this.isTool = false;
    }

    public ModularItem(@Nonnull ItemStack modularItem, NonNullList<ItemStack> stacks, boolean isTool) {
        super(stacks);
        this.modularItem = modularItem;
        this.rangedWrappers = new HashMap<>();
        this.isTool = isTool;
    }





























    /**
     * IItemHandler ------------------------------------------------------------------------------
     */
    @Override
    protected int getStackLimit(final int slot, @Nonnull final ItemStack module) {
        if (isModuleValid(module)) {
            // is module already installed?
            if (isModuleInstalled(BuiltInRegistries.ITEM.getKey(module.getItem()))) { // fixme.. use item check instead? maybe?
                return 0;
            }

            // get the module category... CATEGORY_NONE) is actually just a fallback
            ModuleCategory category = NuminaCapabilities.getCapability(module, NuminaCapabilities.POWER_MODULE).map(m -> m.getCategory())
                    .orElse(ModuleCategory.NONE);

            // Specfic module type for limited modules
            NuminaRangedWrapper wrapper = rangedWrappers.get(category);

            // fallback on generic type if null
            if (wrapper == null) {
                wrapper = rangedWrappers.get(ModuleCategory.NONE);
            }

            if (wrapper != null && wrapper.contains(slot)) {
                return wrapper.getStackInSlot(slot).isEmpty() ? 1: 0;
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
//        modularItem.addTagElement(NuminaConstants.TAG_MODULE_SETTINGS, serializeNBT());
        modularItem.addTagElement(NuminaConstants.TAG_ITEM_PREFIX, serializeNBT());
    }

    @Override
    public void loadCapValues() {
        final CompoundTag nbt = getModularItemStack().getOrCreateTag();
        if (nbt.contains(NuminaConstants.TAG_MODULE_SETTINGS, Tag.TAG_COMPOUND)) {
            nbt.put(NuminaConstants.TAG_ITEM_PREFIX, Objects.requireNonNull(nbt.get(NuminaConstants.TAG_MODULE_SETTINGS)));
            nbt.remove(NuminaConstants.TAG_MODULE_SETTINGS);
        }
        if(nbt.contains(NuminaConstants.TAG_ITEM_PREFIX)) {
            deserializeNBT((CompoundTag) nbt.get(NuminaConstants.TAG_ITEM_PREFIX));
        }
    }

    @Override
    public void onValueChanged() {
    }
}