package lehjr.numina.common.utils;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.inventory.modularitem.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.module.externalitems.IOtherModItemsAsModules;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.Optional;

public class ItemUtils {

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
     * @param mode new mode to set
     */
    public static void setModeAndSwapIfNeeded(Player player, int mode) {
        int selected = player.getInventory().selected;
        try (Level level = player.level()) {
            ItemStack itemStack = player.getInventory().getSelected();
            ItemStack host;
            ItemStack newModule;
            ItemStack stackToSet = ItemStack.EMPTY;
            Optional<IOtherModItemsAsModules> foreignModuleCap = NuminaCapabilities.getCapability(itemStack, NuminaCapabilities.EXTERNAL_MOD_ITEMS_AS_MODULES);
            Optional<IModeChangingItem> mciCap = NuminaCapabilities.getCapability(itemStack, NuminaCapabilities.MODE_CHANGING_MODULAR_ITEM);
            // held item is item from another mod
            if (foreignModuleCap.isPresent()) {
                host = foreignModuleCap.get().retrieveHostStack(TagUtils.getProvider(level));
                mciCap = NuminaCapabilities.getCapability(host, NuminaCapabilities.MODE_CHANGING_MODULAR_ITEM);
                if (mciCap.isPresent()) {
                    if (mciCap.get().returnForeignModuleToModularItem(itemStack)) {
                        mciCap.get().setActiveMode(mode);
                        newModule = mciCap.get().getActiveModule();
                        Optional<IOtherModItemsAsModules> foreignModuleCap1 = NuminaCapabilities.getCapability(newModule, NuminaCapabilities.EXTERNAL_MOD_ITEMS_AS_MODULES);
                        if (foreignModuleCap1.isPresent()) {
                            foreignModuleCap1.get().storeHostStack(TagUtils.getProvider(level), host.copy());
                            stackToSet = newModule.copy();
                        } else {
                            stackToSet = host;
                        }
                    }
                }
            } else if (mciCap.isPresent()) {
                mciCap.get().setActiveMode(mode);
                newModule = mciCap.get().getActiveModule();
                Optional<IOtherModItemsAsModules> foreignModuleCap1 = NuminaCapabilities.getCapability(newModule, NuminaCapabilities.EXTERNAL_MOD_ITEMS_AS_MODULES);
                if (foreignModuleCap1.isPresent()) {
                    foreignModuleCap1.get().storeHostStack(TagUtils.getProvider(level), itemStack.copy());
                    stackToSet = newModule;
                } else {
                    stackToSet = mciCap.get().getModularItemStack();
                }
            }
            player.getInventory().setItem(selected, stackToSet);
            player.containerMenu.broadcastChanges();
        } catch (Exception ignored) {

        }
    }


}
