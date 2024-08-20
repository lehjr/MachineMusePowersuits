package com.lehjr.numina.common.utils;

import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.capabilities.module.externalitems.IOtherModItemsAsModules;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemUtils {
    /**
     * ItemStacks are now more like items with a single instance for all. Data storage is now a separate concept
     * So remember to keep this in mind when working with tags
     */
    public static ResourceLocation getRegistryName(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    public static ResourceLocation getRegistryName(@Nonnull ItemStack itemStack) {
        return getRegistryName(itemStack.getItem());
    }

    @Nonnull
    public static ItemStack getItemFromEntitySlot(LivingEntity entity, EquipmentSlot slot) {
        return entity.getItemBySlot(slot);
    }

    @Nonnull
    public static ItemStack getItemFromEntityHand(LivingEntity entity, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            return getItemFromEntitySlot(entity, EquipmentSlot.MAINHAND);
        }
        return getItemFromEntitySlot(entity, EquipmentSlot.OFFHAND);
    }

    public static EquipmentSlot getEquipmentSlotForItem(ItemStack itemStack) {
        EquipmentSlot slot = itemStack.getEquipmentSlot();
        if (slot != null) {
            return slot;
        } else {
            Equipable equipable = Equipable.get(itemStack);
            if (equipable != null) {
                EquipmentSlot equipmentslot = equipable.getEquipmentSlot();
                if (equipmentslot != EquipmentSlot.BODY) {
                    return equipmentslot;
                }
            }

            return EquipmentSlot.MAINHAND;
        }
    }


    /**
     * @param player player holding the mode changing modular item
     * @param mode   new mode to set
     */
    public static boolean setModeAndSwapIfNeeded(Player player, int mode) {
        boolean updated = false;
        Level level = player.level();
        int selected = player.getInventory().selected;
        ItemStack itemStack = player.getInventory().getSelected();
        ItemStack host;
        ItemStack newModule;
        ItemStack stackToSet = ItemStack.EMPTY;

        @Nullable
        IOtherModItemsAsModules foreignModuleCap = itemStack.getCapability(NuminaCapabilities.Module.EXTERNAL_MOD_ITEMS_AS_MODULES);
        IModeChangingItem mcmi = NuminaCapabilities.getModeChangingModularItem(itemStack);
        // held item is item from another mod

        if (foreignModuleCap != null) {
            host = foreignModuleCap.retrieveHostStack(NuminaCapabilities.getProvider(level));
            mcmi = NuminaCapabilities.getModeChangingModularItem(host);
            if (mcmi != null) {
                if (mcmi.returnForeignModuleToModularItem(itemStack)) {
                    mcmi.setActiveMode(mode);
                    newModule = mcmi.getActiveModule();
                    IOtherModItemsAsModules foreignModuleCap1 = newModule.getCapability(NuminaCapabilities.Module.EXTERNAL_MOD_ITEMS_AS_MODULES);
                    if (foreignModuleCap1 != null) {
                        foreignModuleCap1.storeHostStack(NuminaCapabilities.getProvider(level), host.copy());
                        stackToSet = newModule.copy();
                    } else {
                        stackToSet = host;
                    }
                    updated = true;
                }
            }
        } else if (mcmi != null) {
            mcmi.setActiveMode(mode);
            newModule = mcmi.getActiveModule();
            IOtherModItemsAsModules foreignModuleCap1 = newModule.getCapability(NuminaCapabilities.Module.EXTERNAL_MOD_ITEMS_AS_MODULES);
            if (foreignModuleCap1 != null) {
                foreignModuleCap1.storeHostStack(NuminaCapabilities.getProvider(level), itemStack.copy());
                stackToSet = newModule;
            } else {
                stackToSet = mcmi.getModularItemStack();
            }
            updated = true;
        }
        if (updated && !level.isClientSide) {
            player.getInventory().setItem(selected, stackToSet);
            player.containerMenu.broadcastChanges();
        }
        return updated;
    }

    public static ArmorItem.Type getArmorType(ItemStack stack) {
        EquipmentSlot slot = getEquipmentSlotForItem(stack);
        if (slot.isArmor()) {
            switch (slot) {
                case HEAD -> {
                    return ArmorItem.Type.HELMET;
                }

                case CHEST -> {
                    return ArmorItem.Type.CHESTPLATE;
                }

                case LEGS -> {
                    return ArmorItem.Type.LEGGINGS;
                }

                case FEET -> {
                    return ArmorItem.Type.BOOTS;
                }
            }
        }
        throw new RuntimeException("Invalid ItemStack for armor capability");
    }
}
