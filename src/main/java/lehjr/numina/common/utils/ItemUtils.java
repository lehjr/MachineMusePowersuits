package lehjr.numina.common.utils;

import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capability.module.externalitems.IOtherModItemsAsModules;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.Optional;

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

    /**
     * @param player player holding the mode changing modular item
     * @param mode   new mode to set
     */
    public static void setModeAndSwapIfNeeded(Player player, int mode) {
        int selected = player.getInventory().selected;
        try (Level level = player.level()) {
            ItemStack itemStack = player.getInventory().getSelected();
            ItemStack host;
            ItemStack newModule;
            ItemStack stackToSet = ItemStack.EMPTY;
            IOtherModItemsAsModules foreignModuleCap = itemStack.getCapability(NuminaCapabilities.Module.EXTERNAL_MOD_ITEMS_AS_MODULES);
            IModeChangingItem mcmi = itemStack.getCapability(NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM);
            // held item is item from another mod
            if (foreignModuleCap != null) {
                host = foreignModuleCap.retrieveHostStack(NuminaCapabilities.getProvider(level));
                mcmi = host.getCapability(NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM);
                if (mcmi != null) {
                    if (mcmi.returnForeignModuleToModularItem(itemStack)) {
                        mcmi.setActiveMode(mode);
                        newModule = mcmi.getActiveModule();
                        Optional<IOtherModItemsAsModules> foreignModuleCap1 = NuminaCapabilities.getCapability(newModule, NuminaCapabilities.Module.EXTERNAL_MOD_ITEMS_AS_MODULES);
                        if (foreignModuleCap1.isPresent()) {
                            foreignModuleCap1.get().storeHostStack(NuminaCapabilities.getProvider(level), host.copy());
                            stackToSet = newModule.copy();
                        } else {
                            stackToSet = host;
                        }
                    }
                }
            } else if (mcmi != null) {
                mcmi.setActiveMode(mode);
                newModule = mcmi.getActiveModule();
                Optional<IOtherModItemsAsModules> foreignModuleCap1 = NuminaCapabilities.getCapability(newModule, NuminaCapabilities.Module.EXTERNAL_MOD_ITEMS_AS_MODULES);
                if (foreignModuleCap1.isPresent()) {
                    foreignModuleCap1.get().storeHostStack(NuminaCapabilities.getProvider(level), itemStack.copy());
                    stackToSet = newModule;
                } else {
                    stackToSet = mcmi.getModularItemStack();
                }
            }
            player.getInventory().setItem(selected, stackToSet);
            player.containerMenu.broadcastChanges();
        } catch (Exception ignored) {

        }
    }

    public static ArmorItem.Type getArmorType(ItemStack stack) {
        EquipmentSlot slot = Mob.getEquipmentSlotForItem(stack);
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