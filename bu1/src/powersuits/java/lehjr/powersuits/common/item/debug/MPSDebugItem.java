package lehjr.powersuits.common.item.debug;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.base.NuminaObjects;
import com.lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.powersuits.common.registration.MPSItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;

public class MPSDebugItem extends Item {
    public MPSDebugItem() {
        super(new Item.Properties().setNoRepair());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        NuminaCapabilities.getCapability(helmet, NuminaCapabilities.Inventory.MODULAR_ITEM).ifPresent(iModularItem -> {
            ItemStack armorModule = new ItemStack(MPSItems.IRON_PLATING_MODULE.get());
            NuminaLogger.logDebug("trying to insert");

            if (iModularItem.getStackInSlot(0).isEmpty()) {
                NuminaLogger.logDebug("ranged wrapper size: " + iModularItem.getRangedWrappers().size());

                NuminaLogger.logDebug("is armor module valid? : " + iModularItem.isModuleValid(armorModule));

                ItemStack x =  iModularItem.insertItem(0, armorModule, false);

                NuminaLogger.logDebug("result: " + x);

                NuminaLogger.logDebug("stack in slot: " + iModularItem.getStackInSlot(0));
            }
        });

        NuminaCapabilities.getCapability(player.getItemInHand(hand), Capabilities.ItemHandler.ITEM).ifPresent(iItemHandler -> {

            NuminaLogger.logDebug("itemhandler size: " + iItemHandler.getSlots());
            for (int i = 0; i < iItemHandler.getSlots(); i++) {
                if(iItemHandler.getStackInSlot(i).isEmpty()) {
                    iItemHandler.insertItem(i, new ItemStack(NuminaObjects.BATTERY_4.get()), false);
                } else {
                    NuminaLogger.logDebug("item already in slot: " + iItemHandler.getStackInSlot(i));
                }
            }


        });


        ItemStack heldItem = player.getItemInHand(hand);
        if (heldItem.has(DataComponents.CONTAINER)) {
            NuminaLogger.logDebug("container stuff: " + heldItem.get(DataComponents.CONTAINER));
        } else {
            NuminaLogger.logDebug("not happening");
        }






        return super.use(level, player, hand);
    }
}
