package lehjr.numina.common.event;

import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.capability.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capability.inventory.modularitem.IModularItem;
import lehjr.numina.common.utils.HeatUtils;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.utils.PlayerUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class PlayerUpdateHandler {

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onPlayerUpdate(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        NonNullList<ItemStack> modularItems = NonNullList.create();
        Level level = player.level();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack modularItem = ItemUtils.getItemFromEntitySlot(player, slot);
            if(modularItem.isEmpty()) {
                continue;
            }

            switch(slot.getType()) {
                case HAND -> {
                    IModeChangingItem mci = modularItem.getCapability(NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM);
                    if (mci != null) {
                        mci.tick(player, level, modularItem);
                        modularItems.add(modularItem);
                    }
                }
                case ARMOR, BODY -> {
                    IModularItem mi = modularItem.getCapability(NuminaCapabilities.Inventory.MODULAR_ITEM);
                    if (mi != null) {
                        mi.tick(player, level, modularItem);
                        modularItems.add(modularItem);
                    }
                }
            }
        }
        //  Done this way so players can let their stuff cool in their inventory without having to equip it,
        // allowing it to cool off enough to not take damage
        if (!modularItems.isEmpty()) {
            // Heat update
            double currHeat = HeatUtils.getPlayerHeat(player);

            if (currHeat >= 0 && !level.isClientSide) { // only apply serverside so change is not applied twice

                // cooling value adjustment. Too much or too little cooling makes the heat system useless.
                double coolPlayerAmount = (PlayerUtils.getPlayerCoolingBasedOnMaterial(player) * 0.55);  // cooling value adjustment. Too much or too little cooling makes the heat system useless.

                if (coolPlayerAmount > 0) {
                    HeatUtils.coolPlayer(player, coolPlayerAmount);
                }

                double maxHeat = HeatUtils.getPlayerMaxHeat(player);

                if (currHeat < maxHeat * 0.95) {
                    player.clearFire();
                }
            }
        }
    }
}
