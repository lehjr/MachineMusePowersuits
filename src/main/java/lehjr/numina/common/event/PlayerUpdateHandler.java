package lehjr.numina.common.event;

import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.player.keystates.IPlayerKeyStates;
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

                if (!stack.isEmpty() && shouldPreserveItem(stack)) {
                    CompoundTag itemTag = (CompoundTag) stack.saveOptional(player.registryAccess());
                    itemTag.putByte("Slot", (byte) slot);
                    savedItemsList.add(itemTag);

                    // Clear immediately so vanilla doesn't drop it on the ground
                    inventory.setItem(slot, ItemStack.EMPTY);
                }
            }

            if (!savedItemsList.isEmpty()) {
                CompoundTag data = new CompoundTag();
                data.put("FilteredInventory", savedItemsList);
                player.setData(NuminaCodecs.INVENTORY_BACKUP.get(), data);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        // Only trigger on real death respawns, ignoring standard dimension swaps
        if (event.isWasDeath() && event.getEntity() instanceof ServerPlayer newPlayer) {

            // Clean up unrelated capabilities on the alive player entity
            IPlayerKeyStates keyStates = newPlayer.getCapability(NuminaCapabilities.PLAYER_KEYSTATES);
            if (keyStates != null) {
                keyStates.setStates((byte) 0);
            }

            if (NuminaCommonConfig.keepModularItemsOnDeath) {
                ServerPlayer oldPlayer = (ServerPlayer) event.getOriginal();
                CompoundTag backupData = null;

                // 1. Check if copyOnDeath() successfully migrated it to the new player object
                if (newPlayer.hasData(NuminaCodecs.INVENTORY_BACKUP.get())) {
                    backupData = newPlayer.getData(NuminaCodecs.INVENTORY_BACKUP.get());
                }
                // 2. Fallback: Check if it remained stranded on the old loaded-from-disk dead entity
                else if (oldPlayer.hasData(NuminaCodecs.INVENTORY_BACKUP.get())) {
                    backupData = oldPlayer.getData(NuminaCodecs.INVENTORY_BACKUP.get());
                }

                // If backup data exists in either state, safely process the restoration
                if (backupData != null && backupData.contains("FilteredInventory", 9)) {
                    ListTag listTag = backupData.getList("FilteredInventory", 10);

                    for (int i = 0; i < listTag.size(); i++) {
                        CompoundTag itemTag = listTag.getCompound(i);
                        int slot = itemTag.getByte("Slot") & 255;

                        ItemStack restoredStack = ItemStack.parse(newPlayer.registryAccess(), itemTag).orElse(ItemStack.EMPTY);

                        if (!restoredStack.isEmpty() && slot < newPlayer.getInventory().getContainerSize()) {
                            newPlayer.getInventory().setItem(slot, restoredStack);
                        }
                    }
                }

                // Definitive cleanup to prevent duplication exploits across any subsequent cloning
                newPlayer.removeData(NuminaCodecs.INVENTORY_BACKUP.get());
                oldPlayer.removeData(NuminaCodecs.INVENTORY_BACKUP.get());
            }
        }
    }

    private static boolean shouldPreserveItem(ItemStack stack) {
        return NuminaCapabilities.getModularItemOrModeChangingCapability(stack) != null;
    }
}
