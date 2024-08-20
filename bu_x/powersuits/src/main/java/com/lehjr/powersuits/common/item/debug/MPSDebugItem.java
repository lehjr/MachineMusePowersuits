package com.lehjr.powersuits.common.item.debug;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.registration.NuminaItems;
import com.lehjr.powersuits.common.registration.MPSItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MPSDebugItem extends Item {
    public MPSDebugItem() {
        super(new Item.Properties().setNoRepair());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        IModularItem iModularItem = NuminaCapabilities.getModularItem(helmet);
        if(iModularItem != null) {
            ItemStack armorModule = new ItemStack(MPSItems.IRON_PLATING_MODULE.get());
            NuminaLogger.logDebug("trying to insert");

            if (iModularItem.getStackInSlot(0).isEmpty()) {
                NuminaLogger.logDebug("ranged wrapper size: " + iModularItem.getRangedWrappers().size());

                NuminaLogger.logDebug("is armor module valid? : " + iModularItem.isModuleValid(armorModule));

                ItemStack x =  iModularItem.insertItem(0, armorModule, false);

                NuminaLogger.logDebug("result: " + x);

                NuminaLogger.logDebug("stack in slot: " + iModularItem.getStackInSlot(0));
            }
        }
        IModeChangingItem mci = NuminaCapabilities.getModeChangingModularItem(player.getItemInHand(hand));
        if(mci!= null) {
            NuminaLogger.logDebug("itemhandler size: " + mci.getSlots());
            for (int i = 0; i < mci.getSlots(); i++) {
                if(mci.getStackInSlot(i).isEmpty()) {
                    mci.insertItem(i, new ItemStack(NuminaItems.BATTERY_4.get()), false);
                } else {
                    NuminaLogger.logDebug("item already in slot: " + mci.getStackInSlot(i));
                }
            }
        }

        ItemStack heldItem = player.getItemInHand(hand);
        if (heldItem.has(DataComponents.CONTAINER)) {
            NuminaLogger.logDebug("container stuff: " + heldItem.get(DataComponents.CONTAINER));
        } else {
            NuminaLogger.logDebug("not happening");
        }

        return super.use(level, player, hand);
    }
}
