package com.github.lehjr.numina.event;

import com.github.lehjr.numina.basemod.MuseLogger;
import com.github.lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.heat.MuseHeatUtils;
import com.github.lehjr.numina.util.player.PlayerUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;

public class PlayerUpdateHandler {
    @SuppressWarnings("unchecked")
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();

            NonNullList<ItemStack> modularItems = NonNullList.create();
            for (EquipmentSlotType slot : EquipmentSlotType.values()) {
                if(player.getItemStackFromSlot(slot).isEmpty()) {
                    continue;
                }

                switch (slot.getSlotType()) {
                    case HAND:
                        player.getItemStackFromSlot(slot).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(i-> {
                            if (i instanceof IModeChangingItem) {
                                ((IModeChangingItem) i).tick(player);
                                modularItems.add(((IModeChangingItem) i).getModularItemStack());
                            }
                        });
                        break;

                    case ARMOR:

                        try {
                            player.getItemStackFromSlot(slot).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(i-> {
                                if (i instanceof IModularItem) {
                                    ((IModularItem) i).tick(player);
                                    modularItems.add(((IModularItem) i).getModularItemStack());
                                }
                            });
                        } catch (Exception exception) {
                            MuseLogger.logException(player.getItemStackFromSlot(slot).toString(), exception);
                        }
                        break;
                }
            }

            //  Done this way so players can let their stuff cool in their inventory without having to equip it,
            // allowing it to cool off enough to not take damage
            if (modularItems.size() > 0) {
                // Heat update
                double currHeat = MuseHeatUtils.getPlayerHeat(player);

                if (currHeat >= 0 && !player.world.isRemote) { // only apply serverside so change is not applied twice

                    // cooling value adjustment. Too much or too little cooling makes the heat system useless.
                    double coolPlayerAmount = (PlayerUtils.getPlayerCoolingBasedOnMaterial(player) * 0.55);  // cooling value adjustment. Too much or too little cooling makes the heat system useless.

                    if (coolPlayerAmount > 0) {
                        MuseHeatUtils.coolPlayer(player, coolPlayerAmount);
                    }

                    double maxHeat = MuseHeatUtils.getPlayerMaxHeat(player);

                    if (currHeat < maxHeat * 0.95) {
                        player.extinguish();
                    }
                }
            }
        }
    }

    /**
     * Use this instead of the above method.
     * * @param event
     */
    @SuppressWarnings("unchecked")
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void entityAttackEventHandler(LivingAttackEvent event) {
        // if damage is from overheat damage, just let it happen
        if (event.getSource() == MuseHeatUtils.overheatDamage) {
            return;
        }

        if (event.getSource().isFireDamage()) {
            MuseHeatUtils.heatEntity(event);
        }
    }
}
