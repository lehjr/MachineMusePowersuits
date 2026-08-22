package lehjr.numina.common.event;

import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.config.NuminaCommonConfig;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.registration.NuminaCodecs;
import lehjr.numina.common.utils.HeatUtils;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.utils.PlayerUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
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

            IModularItem iModularItemCap = NuminaCapabilities.getModularItemOrModeChangingCapability(modularItem);
            if(iModularItemCap != null) {
                modularItems.add(modularItem);
                if(slot.isArmor() || iModularItemCap instanceof IModeChangingItem) {
                    iModularItemCap.tick(player, level, modularItem);
                }
            }
        }
        //  Done this way so players can let their stuff cool in their inventory without having to equip it,
        // allowing it to cool off enough to not take damage
        if (!modularItems.isEmpty()) {
            // Heat update
            HeatUtils.PlayerHeat playerHeat = HeatUtils.getPlayerHeat(player);

            if (playerHeat.currentHeat() >= 0 && !level.isClientSide) { // only apply serverside so change is not applied twice

                // cooling value adjustment. Too much or too little cooling makes the heat system useless.
                double coolPlayerAmount = (PlayerUtils.getPlayerCoolingBasedOnMaterial(player) * 0.55);  // cooling value adjustment. Too much or too little cooling makes the heat system useless.

                if (coolPlayerAmount > 0) {
                    HeatUtils.coolPlayer(player, coolPlayerAmount);
                }

                if (playerHeat.currentHeat() < playerHeat.maxHeat() * 0.95) {
                    player.clearFire();
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && NuminaCommonConfig.keepModularItemsOnDeath) {
            if (player.serverLevel().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
                return;
            }

            ListTag savedItemsList = new ListTag();
            Inventory inventory = player.getInventory();

            for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
                ItemStack stack = inventory.getItem(slot);

                if (!stack.isEmpty()) {
                    if (shouldPreserveItem(stack)) {
                        // CRITICAL FIX: stack.saveOptional() creates and returns the valid 1.21 NBT data structure
                        // Tag is returned as a generic Tag interface, so we cast it to CompoundTag
                        CompoundTag itemTag = (CompoundTag) stack.saveOptional(player.registryAccess());

                        // Inject the explicit slot tracker into the newly generated item NBT structure
                        itemTag.putByte("Slot", (byte) slot);

                        savedItemsList.add(itemTag);

                        // Clear the stack from the player inventory right here so that vanilla
                        // Minecraft doesn't drop it into the world when player death drops process next!
                        inventory.setItem(slot, ItemStack.EMPTY);
                    }
                }
            }

            if (!savedItemsList.isEmpty()) {
                CompoundTag data = new CompoundTag();
                data.put("FilteredInventory", savedItemsList);
                player.setData(NuminaCodecs.INVENTORY_BACKUP.get(), data);
            }
        }
    }

    // Example Filtering Logic
    private static boolean shouldPreserveItem(ItemStack stack) {
        return NuminaCapabilities.getModularItemOrModeChangingCapability(stack) != null;
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath() && event.getEntity() instanceof ServerPlayer newPlayer && NuminaCommonConfig.keepModularItemsOnDeath) {
            ServerPlayer oldPlayer = (ServerPlayer) event.getOriginal();
            if (oldPlayer.hasData(NuminaCodecs.INVENTORY_BACKUP.get())) {
                CompoundTag data = oldPlayer.getData(NuminaCodecs.INVENTORY_BACKUP.get());
                if (data.contains("FilteredInventory", 9)) { // 9 is ListTag ID
                    ListTag listTag = data.getList("FilteredInventory", 10); // 10 is CompoundTag ID
                    for (int i = 0; i < listTag.size(); i++) {
                        CompoundTag itemTag = listTag.getCompound(i);
                        int slot = itemTag.getByte("Slot") & 255; // Decode raw byte slot index

                        // Reconstruct the ItemStack using the server's registry context
                        ItemStack restoredStack = ItemStack.parse(newPlayer.registryAccess(), itemTag).orElse(ItemStack.EMPTY);

                        if (!restoredStack.isEmpty() && slot < newPlayer.getInventory().getContainerSize()) {
                            newPlayer.getInventory().setItem(slot, restoredStack);
                        }
                    }
                }
            }
        }
    }
}
