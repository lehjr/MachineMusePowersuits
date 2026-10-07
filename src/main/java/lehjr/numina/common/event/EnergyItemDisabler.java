package lehjr.numina.common.event;

import lehjr.numina.common.capabilities.module.externalitems.IOtherModItemsAsModules;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class EnergyItemDisabler {

    // Helper to check if an item requires your system's energy and is empty
    private static boolean isOutofEnergy(ItemStack stack, Player player) {
        IOtherModItemsAsModules otherCap = stack.getCapability(NuminaCapabilities.Module.EXTERNAL_MOD_ITEMS_AS_MODULES);
        if(otherCap != null && otherCap.hasHostStack(stack)) {
            double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
            if (playerEnergy < 1000) { // fixme, arbitrary number... maybe set up a config value to fine tune this
                return true;
            }
        }
        return false;
    }

    // 1. Blocks right-clicking in the air / using items globally
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if(isOutofEnergy(event.getItemStack(), event.getEntity())) {
            event.setCanceled(true); // Completely stops the Item#use pipeline
        }
    }

    // 2. Blocks right-clicking blocks (e.g. custom tools, wrenches, blocks)
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if(isOutofEnergy(event.getItemStack(), event.getEntity())) {
            // Denies the item from executing, but allows players to still open chests/doors
            event.setUseItem(TriState.FALSE);
            // Or use event.setCanceled(true); to completely lock down the entire right-click
        }
    }
}
